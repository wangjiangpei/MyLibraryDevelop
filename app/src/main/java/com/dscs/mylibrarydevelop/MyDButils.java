package com.dscs.mylibrarydevelop;

import android.os.Environment;
import android.util.Log;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;

/**
 *
 */

public class MyDButils {
    public static DbManager getManager(){
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("neiquan.db")    //设置数据库名称
                .setDbDir(new File(Environment.getExternalStorageDirectory() + "/App/"))  //数据库路径
                .setDbVersion(1)  //数据库版本
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        Log.i("show", "创建数据库成功");
                    }
                }).setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        Log.i("show", "数据库更新了");
                    }
                });
        return  x.getDb(daoConfig);
    }
}
