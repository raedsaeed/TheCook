package com.example.raed.thecook;

import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by raed on 5/25/18.
 */

public class SimpleIdlingResource implements IdlingResource {

    private volatile ResourceCallback callback;
    private AtomicBoolean isIdle = new AtomicBoolean(true);


    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdle.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }

    public void setIsIdle (boolean value) {
        isIdle.set(value);
        if (value && callback != null) {
            callback.onTransitionToIdle();
        }
    }
}
