package assignments.rm.wikisearch.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(indices = {@Index(value={"query"},unique = true)})
public class SearchQuery {
    @PrimaryKey(autoGenerate = true)
    public int qId;
    @ColumnInfo(name = "query")
    private String query;

    public SearchQuery() {
    }

    public SearchQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getqId() {
        return qId;
    }
}
