package assignments.rm.wikisearch.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(primaryKeys = {"pageId","query_id"},foreignKeys = @ForeignKey(entity = SearchQuery.class,
        parentColumns = "qId",
        childColumns = "query_id"))
public class Page implements Parcelable{
    private long pageId;
    @ColumnInfo(name="ns")
    private int ns;
    @ColumnInfo(name="title")
    private String title;
    @ColumnInfo(name="index")
    private long index;
    @ColumnInfo(name="thumbnail_url")
    private String thumbnailURL;
    @ColumnInfo(name="thumbnail_height")
    private int thumbnailHeight;
    @ColumnInfo(name="thumbnail_width")
    private int thumbnailWidth;
    @ColumnInfo(name="description")
    private String description;

    @ColumnInfo(name = "query_id")
    private long queryId;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(pageId);
        dest.writeInt(ns);
        dest.writeString(title);
        dest.writeLong(index);
        dest.writeString(thumbnailURL);
        dest.writeInt(thumbnailHeight);
        dest.writeInt(thumbnailWidth);
        dest.writeString(description);
        dest.writeLong(queryId);
    }

    public Page(Parcel in){
        pageId=in.readLong();
        ns=in.readInt();
        title=in.readString();
        index=in.readLong();
        thumbnailURL=in.readString();
        thumbnailHeight=in.readInt();
        thumbnailWidth=in.readInt();
        description=in.readString();
        queryId=in.readLong();
    }

    private static final Creator<Page> CREATOR=new Creator<Page>() {
        @Override
        public Page createFromParcel(Parcel source) {
            return new Page(source);
        }

        @Override
        public Page[] newArray(int size) {
            return new Page[size];
        }
    };

    public Page() {
    }

    public Page(long pageId, int ns, String title, long index, String thumbnailURL, int thumbnailHeight, int thumbnailWidth, String description) {
        this.pageId = pageId;
        this.ns = ns;
        this.title = title;
        this.index = index;
        this.thumbnailURL = thumbnailURL;
        this.thumbnailHeight = thumbnailHeight;
        this.thumbnailWidth = thumbnailWidth;
        this.description = description;
    }

    public long getPageId() {
        return pageId;
    }

    public void setPageId(long pageId) {
        this.pageId = pageId;
    }

    public int getNs() {
        return ns;
    }

    public void setNs(int ns) {
        this.ns = ns;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public int getThumbnailHeight() {
        return thumbnailHeight;
    }

    public void setThumbnailHeight(int thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
    }

    public int getThumbnailWidth() {
        return thumbnailWidth;
    }

    public void setThumbnailWidth(int thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getQueryId() {
        return queryId;
    }

    public void setQueryId(long queryId) {
        this.queryId = queryId;
    }
}
