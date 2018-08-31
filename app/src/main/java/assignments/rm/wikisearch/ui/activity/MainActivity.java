package assignments.rm.wikisearch.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import assignments.rm.wikisearch.R;
import assignments.rm.wikisearch.util.NetworkHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(NetworkHelper.isOnline(this)){
            Toast.makeText(this,"Online",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"Offline",Toast.LENGTH_SHORT).show();
        }
    }
}
