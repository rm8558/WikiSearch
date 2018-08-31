package assignments.rm.wikisearch.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Page implements Parcelable{
    private long pageId;
    private int ns;
    private String title;
    private long index;
    private String thumbnailURL;
    private int thumbnailHeight,
            thumbnailWidth;
    private String description;

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
}
