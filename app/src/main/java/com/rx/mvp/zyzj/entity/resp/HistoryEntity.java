package com.rx.mvp.zyzj.entity.resp;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangtuo on 2018/7/20.
 */

public class HistoryEntity implements Serializable {
    private String id;
    private String issue;
    private String openTime;
    private List<String> results;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "HistoryEntity{" +
                "id='" + id + '\'' +
                ", issue='" + issue + '\'' +
                ", openTime='" + openTime + '\'' +
                ", results=" + results +
                '}';
    }
}
