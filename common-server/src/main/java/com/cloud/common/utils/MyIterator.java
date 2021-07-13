package com.cloud.common.utils;

import org.apache.commons.collections.CollectionUtils;

import java.util.Iterator;
import java.util.List;

/**
 * @Description:
 * @Author: ZhouShuai
 * @Date: 2021-06-04 10:49
 */
public abstract  class MyIterator<T> implements Iterator<T> {

    private int start = 1;
    private int size = 50;

    private int currentIndex;
    private boolean hasMore = true;
    private List<T> list;

    public MyIterator() {

    }

    @Override
    public boolean hasNext() {
        //当前的数据已经加载完毕，尝试加载下一批
        if (!hasMore) {
            return false;
        }
        list = load(start, size);
        if (CollectionUtils.isEmpty(list)) {
            // 没有加载到数据，结束
            return false;
        }
        if (list.size() < size) {
            // 返回条数小于限制条数，表示还有更多的数据可以加载
            hasMore = false;
        }
        currentIndex = 0;
        this.start ++;
        return true;
    }

    @Override
    public T next() {
        return list.get(currentIndex++);
    }

    public List<T> getData() {
        return list;
    }

    public abstract List<T> load(int start, int size);

}
