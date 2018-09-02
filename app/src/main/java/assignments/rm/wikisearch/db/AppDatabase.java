package assignments.rm.wikisearch.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import assignments.rm.wikisearch.dao.PageDAO;
import assignments.rm.wikisearch.dao.SearchQueryDAO;
import assignments.rm.wikisearch.model.Page;
import assignments.rm.wikisearch.model.SearchQuery;

@Database(entities = {SearchQuery.class, Page.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    public abstract PageDAO pageDAO();
    public abstract SearchQueryDAO searchQueryDAO();
}
