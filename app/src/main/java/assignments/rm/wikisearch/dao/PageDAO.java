package assignments.rm.wikisearch.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import assignments.rm.wikisearch.model.Page;

@Dao
public interface PageDAO {
    @Query("SELECT * FROM Page")
    List<Page> getAllPages();

    @Query("SELECT * FROM Page,SearchQuery WHERE Page.query_id=SearchQuery.qId AND SearchQuery.`query`=:searchQuery")
    List<Page> getAllPagesBySearchQuery(String searchQuery);

    @Query("SELECT * FROM Page WHERE LOWER(title) like :searchQuery ")
    List<Page> getAllPagesBySearchQueryOffline(String searchQuery);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(Page... pages);

    @Delete
    void delete(Page page);
}
