package assignments.rm.wikisearch;

import java.util.ArrayList;

import assignments.rm.wikisearch.model.Page;

public class SearchResultModel {
    private ArrayList<Page> pageList;

    public SearchResultModel(ArrayList<Page> pageList) {
        this.pageList = pageList;
    }

    public SearchResultModel() {
        this.pageList=new ArrayList<Page>();
    }

    public ArrayList<Page> getPageList() {
        return pageList;
    }

    public void setPageList(ArrayList<Page> pageList) {
        this.pageList = pageList;
    }
}
