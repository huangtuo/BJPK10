package com.rx.mvp.zyzj.entity.resp;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangtuo on 2018/7/30.
 */

public class MyFeedbackEntity implements Serializable {
    private String id;
    private String topic;
    private String breviaryContent; //反馈内容缩率
    private String userId;
    private String status; //状态 1:已处理
    private String userName;
    private String createTime; //反馈时间
    private String content; //反馈内容
    private String base64String;
    private List<SuggestionEntity> detaiList;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getBreviaryContent() {
        return breviaryContent;
    }

    public void setBreviaryContent(String breviaryContent) {
        this.breviaryContent = breviaryContent;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public List<SuggestionEntity> getDetaiList() {
        return detaiList;
    }

    public void setDetaiList(List<SuggestionEntity> detaiList) {
        this.detaiList = detaiList;
    }

    @Override
    public String toString() {
        return "MyFeedbackEntity{" +
                "id='" + id + '\'' +
                ", topic='" + topic + '\'' +
                ", breviaryContent='" + breviaryContent + '\'' +
                ", userId='" + userId + '\'' +
                ", status='" + status + '\'' +
                ", userName='" + userName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", content='" + content + '\'' +
                ", base64String='" + base64String + '\'' +
                ", detaiList=" + detaiList +
                '}';
    }
}
