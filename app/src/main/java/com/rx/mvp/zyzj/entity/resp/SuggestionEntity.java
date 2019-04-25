package com.rx.mvp.zyzj.entity.resp;

import java.io.Serializable;

/**
 * Created by huangtuo on 2018/8/15.
 */

public class SuggestionEntity implements Serializable{
    private String id;
    private String suggestionId;
    private String content;
    private String userId;
    private String userName;
    private String status;
    private String createTime;
    private String base64String;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSuggestionId() {
        return suggestionId;
    }

    public void setSuggestionId(String suggestionId) {
        this.suggestionId = suggestionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBase64String() {
        return base64String;
    }

    public void setBase64String(String base64String) {
        this.base64String = base64String;
    }

    @Override
    public String toString() {
        return "SuggestionEntity{" +
                "id='" + id + '\'' +
                ", suggestionId='" + suggestionId + '\'' +
                ", content='" + content + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", status='" + status + '\'' +
                ", createTime='" + createTime + '\'' +
                ", base64String='" + base64String + '\'' +
                '}';
    }
}
