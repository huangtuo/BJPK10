package com.rx.mvp.zyzj.entity.resp;

import java.io.Serializable;

/**
 * Created by huangtuo on 2018/7/19.
 */

public class LotteryGoupsItemEntity implements Serializable{
    private String CPName;
    private String NumFormat;
    private int SID;

    public String getCPName()
    {
        return this.CPName;
    }

    public String getNumFormat()
    {
        return this.NumFormat;
    }

    public int getSID()
    {
        return this.SID;
    }

    public void setCPName(String paramString)
    {
        this.CPName = paramString;
    }

    public void setNumFormat(String paramString)
    {
        this.NumFormat = paramString;
    }

    public void setSID(int paramInt)
    {
        this.SID = paramInt;
    }
}
