package assignments.rm.wikisearch.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkHelper {
    public static interface HttpGetCallback{
        void handleSuccess(Object object);
        void handleFailure(Object object);
    }

    private static class Constants{
        private static final int CONNECT_TIMEOUT=30;
        private static final int READ_TIMEOUT=30;
        private static final int WRITE_TIMEOUT=30;
    }

    public static boolean isOnline(Context context){
        boolean online=false;

        try{
            ConnectivityManager connectivityManager=(ConnectivityManager)context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            if(connectivityManager!=null) {
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null
                        && networkInfo.isConnected()) {
                    online = true;
                }
            }
        }catch(Exception e){
            LogTracker.trackException(NetworkHelper.class,e);
        }

        return online;
    }

    public static void callApiGet(String url,
                           final HttpGetCallback callback){
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(Constants.CONNECT_TIMEOUT,TimeUnit.SECONDS)
                .readTimeout(Constants.READ_TIMEOUT,TimeUnit.SECONDS)
                .writeTimeout(Constants.WRITE_TIMEOUT,TimeUnit.SECONDS)
                .build();

        Request request=new Request.Builder()
                .url(url)
                .build();

        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogTracker.trackException(NetworkHelper.class,e);
                callback.handleFailure(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respStr=response.body().string();
                if(response.isSuccessful()) {
                    try{
                        JSONObject respObj=new JSONObject(respStr);
                        callback.handleSuccess(respObj);
                    }catch(JSONException jsonX){
                        LogTracker.trackException(NetworkHelper.class, jsonX);
                        callback.handleFailure(null);
                    }catch (Exception e){
                        LogTracker.trackException(NetworkHelper.class, e);
                        callback.handleFailure(null);
                    }
                }
                else{
                    callback.handleFailure(null);
                }
            }
        });
    }
}
