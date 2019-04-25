package com.rx.mvp.zyzj.entity.resp;

import java.io.Serializable;

/**
 * Created by huangtuo on 2018/8/3.
 */

public class UpdateEntity implements Serializable {

    private String appVersion; //返回空 不需要升级
    private String forceUpdate;// 强制升级标志 0 非强制 1 强制
    private String url;
    private String updateContent;

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(String forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    @Override
    public String toString() {
        return "UpdateEntity{" +
                "appVersion='" + appVersion + '\'' +
                ", forceUpdate='" + forceUpdate + '\'' +
                ", url='" + url + '\'' +
                ", updateContent='" + updateContent + '\'' +
                '}';
    }
}
