package com.hyh.prettyskin;

import android.app.Application;
import android.content.Context;


//@SuppressWarnings("all")
public class PrettySkin extends BasePrettySkin {

    private static PrettySkin sPrettySkin;

    public static PrettySkin getInstance() {
        if (sPrettySkin != null) {
            return sPrettySkin;
        }
        synchronized (PrettySkin.class) {
            if (sPrettySkin == null) {
                sPrettySkin = new PrettySkin();
            }
            return sPrettySkin;
        }
    }

    private Context mContext;

    private PrettySkin() {
    }

    public synchronized void init(Context context) {
        if (mContext != null) {
            return;
        }
        mContext = context.getApplicationContext();
        installViewFactory(context, false);
        Application application = getApplication(context);
        if (application != null) {
            if (application != context) {
                installViewFactory(application, false);
            }
            application.registerActivityLifecycleCallbacks(mPrettySkinActivityLifecycle);
        }
    }

    private Application getApplication(Context context) {
        Application application = null;
        if (context instanceof Application) {
            application = (Application) context;
        } else {
            Context applicationContext = context.getApplicationContext();
            if (applicationContext instanceof Application) {
                application = (Application) applicationContext;
            }
        }
        return application;
    }
}