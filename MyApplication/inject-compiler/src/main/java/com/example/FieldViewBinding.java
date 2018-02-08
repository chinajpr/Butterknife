package com.example;

import javax.lang.model.type.TypeMirror;

/**
 * date:2018/2/7 on 17:59
 * author:JiaoPeiRong
 */

public class FieldViewBinding {
    private String name;// textview
    private TypeMirror type;//TextView
    private int resId;//R.id.textview

    public FieldViewBinding(String name, TypeMirror type, int resId) {
        this.name = name;
        this.type = type;
        this.resId = resId;
    }

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
