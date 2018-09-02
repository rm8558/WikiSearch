package assignments.rm.wikisearch.db;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseHelper {
    private static DatabaseHelper instance;

    private AppDatabase db;
    private DatabaseHelper(Context context){
        db=Room.databaseBuilder(context,
                AppDatabase.class,
                "wiki_search").build();
    }

    public static DatabaseHelper getInstance(Context context){
        if(instance==null){
            instance=new DatabaseHelper(context);
        }
        return instance;
    }

    public AppDatabase getDb() {
        return db;
    }
}
