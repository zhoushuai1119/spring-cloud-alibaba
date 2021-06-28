package com.cloud.common.beans.response;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.common.beans.exception.BaseExceptionCode;

import java.util.List;

/**
 * @Description:
 * @Author: ZhouShuai
 * @Date: 2021-06-27 16:26
 */
public class PageQueryResponse<T> extends BaseResponse<List<T>> {
    private static final long serialVersionUID = -8937008601803151631L;
    private long pageIndex;
    private long totalCount;
    private long pageSize;

    public PageQueryResponse() {
    }

    public PageQueryResponse<T> successPage(List<T> model, long pageIndex, long totalCount, long pageSize) {
        this.setSuccess(true);
        this.setModel(model);
        this.setPageIndex(pageIndex);
        this.setTotalCount(totalCount);
        this.setPageSize(pageSize);
        return this;
    }

    public PageQueryResponse<T> successPage(Page<T> page) {
        this.setSuccess(true);
        this.setModel(page.getRecords());
        this.setPageIndex(page.getCurrent());
        this.setTotalCount(page.getTotal());
        this.setPageSize(page.getSize());
        return this;
    }

    public static <T> PageQueryResponse<T> createSuccessResult(List<T> model) {
        PageQueryResponse<T> rt = new PageQueryResponse();
        return (PageQueryResponse)rt.success(model);
    }

    public static <T> PageQueryResponse<T> createSuccessPageResult(Page<T> page) {
        PageQueryResponse<T> rt = new PageQueryResponse();
        return rt.successPage(page);
    }

    public static <T> PageQueryResponse<T> createSuccessResult(List<T> model, int pageIndex, int totalCount, int pageSize) {
        PageQueryResponse<T> rt = new PageQueryResponse();
        return rt.successPage(model, pageIndex, totalCount, pageSize);
    }

    public static <T> PageQueryResponse<T> createPageFailResult(List<T> model, BaseExceptionCode errorCode, String errorMsg) {
        PageQueryResponse<T> rt = new PageQueryResponse();
        rt.setModel(model);
        rt.fail(errorCode, errorMsg);
        return rt;
    }

    public static <T> PageQueryResponse<T> createPageFailResult(BaseExceptionCode errorCode, boolean isRetry) {
        PageQueryResponse<T> rt = new PageQueryResponse();
        return (PageQueryResponse)rt.fail(errorCode);
    }

    public static <T> PageQueryResponse<T> createPageFailResult(BaseExceptionCode errorCode, String errorMsg, boolean isRetry) {
        PageQueryResponse<T> rt = new PageQueryResponse();
        rt.setModel(null);
        return (PageQueryResponse)rt.fail(errorCode, errorMsg);
    }

    public long getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(long pageIndex) {
        this.pageIndex = pageIndex;
    }

    public long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getCurrentPage() {
        return this.pageIndex < 1 ? 1 : this.pageIndex;
    }

    public boolean hasNext() {
        long useCount = (this.getCurrentPage() - 1) * this.getPageSize() + this.getSize();
        return this.totalCount > useCount;
    }

    public long getTotalPage() {
        return this.pageSize == 0 ? 0 : (this.totalCount - 1) / this.pageSize + 1;
    }

    private long getSize() {
        List<T> page = (List)this.getModel();
        return page == null ? 0 : page.size();
    }
}
