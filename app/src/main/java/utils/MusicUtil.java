package utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore.Audio.Media;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import bean.Music;

/**
 * Created by Administrator on 2016/11/16.
 */

public class MusicUtil {
    private static ContentResolver resolver;

    public static List<Music> getMusic(Context context) {
        List<Music> oList = null;
        resolver = context.getContentResolver();
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            oList = new ArrayList<Music>();
            Cursor cursor = resolver.query(Media.EXTERNAL_CONTENT_URI, null,
                    null, null, Media.DEFAULT_SORT_ORDER);
            Log.e("%%%%%%%", Media.EXTERNAL_CONTENT_URI.toString());
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor
                        .getColumnIndex(Media.TITLE));
                String author = cursor.getString(cursor
                        .getColumnIndex(Media.ARTIST));
                String path = cursor.getString(cursor
                        .getColumnIndex(Media.DATA));
                Log.e("%%%%%%%", path.toString());

                Music music = new Music(name, author, path);
                oList.add(music);

            }
        }
        return oList;
    }
}
