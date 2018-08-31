package assignments.rm.wikisearch.util;

import android.util.Log;

import assignments.rm.wikisearch.BuildConfig;

/**
 * Created by reetmondal on 01/09/18.
 */

public class LogTracker {
    public static void trackException(Class className,Exception e){
        if(BuildConfig.DEBUG){
            Log.e(className.getSimpleName(),e.getMessage(),e);
        }
        else{
            //Handle for production
        }
    }

    public static void debugLog(Class className,String data){
        if(BuildConfig.DEBUG){
            Log.d(className.getSimpleName(),data);
        }
    }
}
