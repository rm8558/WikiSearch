package assignments.rm.wikisearch.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import assignments.rm.wikisearch.model.SearchQuery;

@Dao
public interface SearchQueryDAO {
    @Query("SELECT * FROM SearchQuery")
    List<SearchQuery> getAllQueries();

    @Query("SELECT * FROM SearchQuery WHERE LOWER(`query`) like :criteria")
    List<SearchQuery> getAllMatchingQueries(String criteria);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(SearchQuery... searchQueries);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertOne(SearchQuery searchQuery);

    @Delete
    void delete(SearchQuery searchQuery);
}
