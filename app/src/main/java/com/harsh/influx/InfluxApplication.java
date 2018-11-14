package com.harsh.influx;

import android.app.Application;

import com.instabug.library.Feature;
import com.instabug.library.Instabug;
import com.instabug.library.invocation.InstabugInvocationEvent;

public class InfluxApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        new Instabug.Builder(this, "db27876d34f6fb1f921f52e8de38e911")
                .setInvocationEvents(InstabugInvocationEvent.SHAKE, InstabugInvocationEvent.FLOATING_BUTTON)
                .setCrashReportingState(Feature.State.ENABLED)
                .build();
    }
}
