package DBUtil;

import android.os.Environment;

import org.xutils.DbManager;

import java.io.File;

/**
 * Created by 陈师表 on 2016/11/15.
 */

public class DatabaseOpenHelper {
    static DbManager.DaoConfig daoConfig;
    public static DbManager.DaoConfig getDaoConfig(){
        File file=new File(Environment.getExternalStorageDirectory().getPath());
        if(daoConfig==null){
            daoConfig=new DbManager.DaoConfig()
                    .setDbName("history.db")
                    .setDbDir(file)
                    .setDbVersion(1)
                    .setAllowTransaction(true)
                    .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                        @Override
                        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        }
                    });
        }
        return daoConfig;
    }
}
