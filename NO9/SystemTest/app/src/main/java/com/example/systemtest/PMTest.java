package com.example.systemtest;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class PMTest extends Activity {

    public static final int ALL_APP = 0;
    public static final int SYSTEM_APP = 1;
    public static final int THIRD_APP = 2;
    public static final int SDCARD_APP = 3;

    private ListView mListView;
    private PackageManager pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pm_main);
        mListView = (ListView) findViewById(R.id.listView_pm);
    }

    private List<PMAppInfo> getAppInfo(int flag){
        pm = this.getPackageManager();
        List<ApplicationInfo> listAppcations = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        List<PMAppInfo> appInfos = new ArrayList<PMAppInfo>();
        //判断应用类型
        switch (flag){
            case ALL_APP:
                appInfos.clear();
                for (ApplicationInfo app: listAppcations){
                    appInfos.add(makeAppInfo(app));
                }
                break;
            case SYSTEM_APP:
                appInfos.clear();
                for (ApplicationInfo app : listAppcations){
                    if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0){
                        appInfos.add(makeAppInfo(app));
                    }
                }
                break;
            case THIRD_APP:
                appInfos.clear();
                for (ApplicationInfo app : listAppcations){
                    if ((app.flags & ApplicationInfo.FLAG_SYSTEM) <= 0){
                        appInfos.add(makeAppInfo(app));
                    }else if ((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0){
                        appInfos.add(makeAppInfo(app));
                    }
                }
                break;
            case SDCARD_APP:
                appInfos.clear();
                for (ApplicationInfo app : listAppcations){
                    if ((app.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0){
                        appInfos.add(makeAppInfo(app));
                    }
                }
                break;
            default:
                return null;
        }
        return appInfos;
    }

    private PMAppInfo makeAppInfo(ApplicationInfo app){
        PMAppInfo appInfo = new PMAppInfo();
        appInfo.setAppLable((String) app.loadLabel(pm));
        appInfo.setAppIcon(app.loadIcon(pm));
        appInfo.setPkgName(app.packageName);
        return appInfo;
    }

    private void setListData(int flag){
        PMAdapter adapter = new PMAdapter(this, getAppInfo(flag));
        mListView.setAdapter(adapter);
    }

    public void btnAllApp(View view){
        setListData(ALL_APP);
    }

    public void btnSystemApp(View view) {
        setListData(SYSTEM_APP);
    }

    public void btn3rdApp(View view) {
        setListData(THIRD_APP);
    }

    public void btnSdcardApp(View view) {
        setListData(SDCARD_APP);
    }
}
