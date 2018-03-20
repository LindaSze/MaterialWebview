package com.lynnsze.materialwebview.base.net;

public abstract class BaseParser<T> {
    public Class<T> mFameClass = null;
    public Class<T> mClass = null;

    public abstract T parse(String var1);

    public BaseParser(Class<T> cls) {
        this.mFameClass = cls;
    }

}