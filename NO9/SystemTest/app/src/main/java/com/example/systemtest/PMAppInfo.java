package com.example.systemtest;

import android.graphics.drawable.Drawable;

/**
 * Created by 25400 on 2019/12/9.
 */

public class PMAppInfo {
    private String appLable;
    private Drawable appIcon;
    private String pkgName;

    public PMAppInfo() {
    }

    public String getAppLable() {
        return appLable;
    }

    public void setAppLable(String appLable) {
        this.appLable = appLable;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }
}
