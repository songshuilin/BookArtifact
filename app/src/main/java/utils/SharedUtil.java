package utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedUtil {
	private static SharedPreferences preferences;
	private static Editor editor;

	private static SharedUtil sharedUtil;
	private static Context context;

	public static SharedUtil getInstance(Context context) {
		if (sharedUtil == null) {
			sharedUtil = new SharedUtil();
			SharedUtil.context = context;
		}
		return sharedUtil;
	}

	public static SharedPreferences getSharedPreferences(Context context) {
		if (preferences == null) {
			preferences = context.getSharedPreferences("setting",context.MODE_PRIVATE);
		}
		return preferences;
	}

	public static Editor getEditor(Context context) {

		if (preferences == null) {
			getSharedPreferences(context);
		}
		if (editor == null) {
			editor = preferences.edit();
		}
		return editor;
	}

	//排序设置
	public SharedUtil put_sort(String values) {
		getEditor(context).putString("sort", values).commit();
		return sharedUtil;
	};

	//省流量设置
	public SharedUtil put_saveflow(String values) {
		getEditor(context).putString("save", values).commit();
		return sharedUtil;
	};

	//获取排序信息
	public String get_sort(){
		return getSharedPreferences(context).getString("sort","0");
	}
	//获取 是否为省流量模式
	public String get_save(){
		return getSharedPreferences(context).getString("save","0");
	}



}
