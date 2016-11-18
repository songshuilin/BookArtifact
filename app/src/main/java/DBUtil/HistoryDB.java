package DBUtil;

import android.database.Cursor;
import android.util.Log;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

import bean.HistoryBean;


/**
 * Created by 陈师表 on 2016/11/15.
 */

public class HistoryDB {
    private static HistoryDB historyDB;
    private static DbManager db;

    //接收构造方法初始化的DbManager对象
    private HistoryDB() {
        db = x.getDb(DatabaseOpenHelper.getDaoConfig());
        Log.e("TAGGGG","@@@@@@@@@@@@@@@");
    }

    //构造方法私有化,拿到DbManager对象
    public synchronized static HistoryDB getIntance() {
        if (historyDB == null) {
            historyDB = new HistoryDB();
        }
        return historyDB;
    }
    //获取PersonDB实例

    /****************************************************************************************/
    //将HistoryBean实例存进数据库
    public void saveHistory(HistoryBean historyBean) {
        try {
            db.save(historyBean);
            Log.d("xyz", "save succeed!");
        } catch (DbException e) {
            Log.d("xyz", e.toString());
        }
    }

    //读取所有历史信息
    public List<HistoryBean> loadHistory() {
        List<HistoryBean> list = null;
        try {
//            list = db.selector(HistoryBean.class).orderBy("date", true).findAll();
            list =db.selector(HistoryBean.class).where("date","!=",null).and("content","!=",null).and("content","!=","").orderBy("date", true).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return list;
    }

    //删除数据
    public void cleanHistory() {
        try {
            db.delete(HistoryBean.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
    //删除数据库
    public void dropHistory(){
        try {
            db.dropTable(HistoryBean.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    //删除空的数据
    public void deleteNull(){
        db.getDatabase().delete("history","date=?",new String[]{null});
        Log.e("TAG","########");
    }

    //查询是否有内容重复的
    public boolean searchIsRepet(String cont) {

        Cursor cursor = db.getDatabase().rawQuery("select * from history where content=?", new String[]{cont});
        if (cursor.getCount()>0) {
            Log.e("TAGG","::"+cursor.getCount()+"--"+cursor.toString());
            return true;
        }
        return false;
    }

    //更新数据库
    public void updateDB(String cont,String date){
        db.getDatabase().execSQL("update history set date =? where content=?",new String[]{date,cont});
    }


}