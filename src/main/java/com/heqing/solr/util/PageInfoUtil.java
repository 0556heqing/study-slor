package com.heqing.solr.util;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页帮助类
 * @author heqing
 */
@Data
public class PageInfoUtil<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * pageNum 当前页
     */
    private int pageNum;

    /**
     * pageSize 每页的数量
     */
    private int pageSize;

    /**
     * total 总记录数
     */
    private long total;

    /**
     * pages 总页数
     */
    private int pages;

    /**
     * list 结果集
     */
    private List<T> list;

    /**
     * isFirstPage 是否为第一页
     */
    private boolean isFirstPage = false;

    /**
     * isLastPage 是否为最后一页
     */
    private boolean isLastPage = false;

    /**
     * 包装Page对象
     *
     * @param list 数据
     * @param pageNum 第几页
     * @param pageSize 每页数量
     * @param total 总数
     */
    public PageInfoUtil(List list, int pageNum, int pageSize, long total) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list;
        this.pages = ((int)total/pageSize) + (total%pageSize==0 ? 0 : 1);

        judgePageBoudary();
    }

    /**
     * 判定页面边界
     */
    private void judgePageBoudary() {
        isFirstPage = pageNum == 1;
        isLastPage = pageNum == pages;
    }
}
