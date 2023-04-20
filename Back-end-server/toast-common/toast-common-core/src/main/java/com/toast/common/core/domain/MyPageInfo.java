package com.toast.common.core.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 自定义分页
 * @param <T>
 */
public class MyPageInfo<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int pageNum;
    private int pageSize;
    private int size;
    private List<T> list;

    public MyPageInfo() {
    }

    public MyPageInfo(List<T> list) {
        this.list = list;
    }

    public MyPageInfo(int pageNum, int pageSize, int size, List<T> list) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.size = size;
        this.list = list;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
