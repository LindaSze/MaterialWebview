package com.lynnsze.materialwebview.base.net;

public abstract class BaseThread extends Thread {
    protected boolean mCancel = false;
    protected boolean mStop = false;

    public BaseThread() {
    }

    final void cancel() {
        this.mCancel = true;
    }

    final boolean isCancel() {
        return this.mCancel;
    }

    final void finish() {
        this.mStop = true;
    }

    final boolean isFinish() {
        return this.mStop;
    }


    @Override
    public void run() {
        if (isFinish())
            return;

        if (isCancel())
            return;

        runThread();
    }

    public abstract void runThread();
}