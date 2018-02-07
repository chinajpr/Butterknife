package com.jpr.inject;

/**
 * 类描述:
 * 创建日期:2018/2/7 on 17:07
 * 作者:JiaoPeiRong
 */

public interface ViewBinder<T> {
    void bind(T target);
}
