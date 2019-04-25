package com.rx.mvp.zyzj.entity.req;

/**
 * Created by huangtuo on 2018/7/18.
 */

public class TopNewReq extends BaseReq {
    private String ver;
    private String key;
    private String plat;

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPlat() {
        return plat;
    }

    public void setPlat(String plat) {
        this.plat = plat;
    }
}
