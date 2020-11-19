package com.kymjs.app.base_res.utils.base.entry;

/**
 * Created by 16486 on 2020/3/31.
 */

public class PageInfo {
    private int PageCurrent;            //当前页
    private int PageSize;               //每页数量
    private int RowsCount;              //总数量
    private int PageCount;              //总页数

    public int getPageCurrent() {
        return PageCurrent;
    }

    public void setPageCurrent(int pageCurrent) {
        PageCurrent = pageCurrent;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }

    public int getRowsCount() {
        return RowsCount;
    }

    public void setRowsCount(int rowsCount) {
        RowsCount = rowsCount;
    }

    public int getPageCount() {
        return PageCount;
    }

    public void setPageCount(int pageCount) {
        PageCount = pageCount;
    }
}
