package com.example;

import javax.lang.model.type.TypeMirror;

/**
 * 类描述:
 * 创建日期:2018/2/7 on 17:59
 * 作者:JiaoPeiRong
 */

public class FieldViewBinding {
    private String name;//成员变量名 textview
    private TypeMirror type;//TextView
    private int resId;//R.id.textview

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeMirror getType() {
        return type;
    }

    public void setType(TypeMirror type) {
        this.type = type;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
