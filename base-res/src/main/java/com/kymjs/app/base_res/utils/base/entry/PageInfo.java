package com.kymjs.app.base_res.utils.base.entry;

/**
 * Created by 16486 on 2020/3/31.
 */

public class PageInfo {
    private int PageCurrent;            //��ǰҳ
    private int PageSize;               //ÿҳ����
    private int RowsCount;              //������
    private int PageCount;              //��ҳ��

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
