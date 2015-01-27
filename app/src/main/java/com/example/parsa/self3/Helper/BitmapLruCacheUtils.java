package com.example.parsa.self3.Helper;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.File;

/**
 * Created by ashkan on 8/27/2014.
 */
public class BitmapLruCacheUtils {
    public static final int IO_BUFFER_SIZE = 8 * 1024;

    private BitmapLruCacheUtils() {};

    public static boolean isExternalStorageRemovable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return Environment.isExternalStorageRemovable();
        }
        return true;
    }

    public static File getExternalCacheDir(Context context) {
        if (hasExternalCacheDir()) {
            return context.getExternalCacheDir();
        }

        // Before Froyo we need to construct the external cache dir ourselves
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }

    public static boolean hasExternalCacheDir() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

}