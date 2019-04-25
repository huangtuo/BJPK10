package com.rx.mvp.zyzj.entity.resp;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangtuo on 2018/7/19.
 */

public class LotteryGoupsEntity implements Serializable {
    private List<LotteryGoupsItemEntity> CPNames;
    private String CPType;

    public List<LotteryGoupsItemEntity> getCPNames()
    {
        return this.CPNames;
    }

    public String getCPType()
    {
        return this.CPType;
    }

    public void setCPNames(List<LotteryGoupsItemEntity> paramList)
    {
        this.CPNames = paramList;
    }

    public void setCPType(String paramString)
    {
        this.CPType = paramString;
    }
}
