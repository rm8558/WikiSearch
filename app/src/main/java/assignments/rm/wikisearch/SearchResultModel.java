package assignments.rm.wikisearch;

import java.util.ArrayList;
import java.util.List;

import assignments.rm.wikisearch.model.Page;

public class SearchResultModel {
    private List<Page> pageList;

    public SearchResultModel(ArrayList<Page> pageList) {
        this.pageList = pageList;
    }

    public SearchResultModel() {
        this.pageList=new ArrayList<>();
    }

    public List<Page> getPageList() {
        return pageList;
    }

    public void setPageList(List<Page> pageList) {
        this.pageList = pageList;
    }
}
