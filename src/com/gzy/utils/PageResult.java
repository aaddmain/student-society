package com.gzy.utils;

import com.gzy.entiy.UserInfo;

import java.util.List;

public class PageResult {
    private List<UserInfo> data;
    private int totalCount;

    public PageResult(List<UserInfo> data, int totalCount) {
        this.data = data;
        this.totalCount = totalCount;
    }

    public List<UserInfo> getData() {
        return data;
    }

    public  int getTotalCount() {
        return totalCount;
    }

}
