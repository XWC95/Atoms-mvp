package com.vea.atoms.mvp.app;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * AppManager
 *
 * @author lishide
 * @date 2018/12/12
 */
public class AppManager {

    private Application mApplication;

    private List<ILogout> mLogouts;

    public static class AppManagerHolder {
        private static AppManager sAppManager = new AppManager();
    }

    public static AppManager getInstance() {
        return AppManagerHolder.sAppManager;
    }

    private AppManager() {
        mApplication = initApplication();
    }

    public Application getApplication() {
        return mApplication;
    }

    static Application initApplication() {
        try {
            //通过反射的方式来获取当前进程的application对象
            Application app = (Application) Class.forName("android.app.ActivityThread")
                    .getMethod("currentApplication").invoke(null);
            if (app != null) {
                return app;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Application app = (Application) Class.forName("android.app.AppGlobals")
                    .getMethod("getInitialApplication").invoke(null);
            if (app != null) {
                return app;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addLogout(ILogout logout) {
        if (mLogouts == null) {
            mLogouts = new ArrayList<>();
        }

        mLogouts.add(logout);
    }

    public void logout(Context context) {
        if (mLogouts != null) {
            for (ILogout info : mLogouts) {
                info.logout(context);
            }
        }
    }
}
