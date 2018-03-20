package com.lynnsze.materialwebview.base;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

public abstract class CustomHandler<T> extends Handler {

    protected WeakReference<T> weakReference = null;

    protected   CustomHandler(T t) {
        this.weakReference = new WeakReference<T>(t);
    }

    @Override
    public final void handleMessage(Message msg) {
        message(msg);
    }

    public abstract void message(Message msg);
}