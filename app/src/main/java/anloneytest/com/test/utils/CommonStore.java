package anloneytest.com.test.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * sharepreference 保存获取的工具类
 *
 * @author xueting
 */
public class CommonStore {

    private static final String STORE_NAME = "TestStore";

    private static final String STORE_KEY_PREFIX = "TESY_STORE_";

    public static void storeString(Context context, String key, String value) {
        SharedPreferences pre = context.getSharedPreferences(STORE_NAME,
                Context.MODE_PRIVATE);
        pre.edit().putString(STORE_KEY_PREFIX + key, value).commit();
    }

    public static String readString(Context context, String key) {
        SharedPreferences pre = context.getSharedPreferences(STORE_NAME,
                Context.MODE_PRIVATE);
        return pre.getString(STORE_KEY_PREFIX + key, "");
    }

    public static void storeBoolean(Context context, String key, boolean value) {
        SharedPreferences pre = context.getSharedPreferences(STORE_NAME,
                Context.MODE_PRIVATE);
        pre.edit().putBoolean(STORE_KEY_PREFIX + key, value).commit();
    }

    public static boolean readBoolean(Context context, String key,
                                      boolean defaultValue) {
        SharedPreferences pre = context.getSharedPreferences(STORE_NAME,
                Context.MODE_PRIVATE);
        return pre.getBoolean(STORE_KEY_PREFIX + key, defaultValue);
    }

    public static void storeFloat(Context context, String key, float value) {
        SharedPreferences pre = context.getSharedPreferences(STORE_NAME,
                Context.MODE_PRIVATE);
        pre.edit().putFloat(STORE_KEY_PREFIX + key, value).commit();
    }

    public static float readFloat(Context context, String key,
                                  float defaultValue) {
        SharedPreferences pre = context.getSharedPreferences(STORE_NAME,
                Context.MODE_PRIVATE);
        return pre.getFloat(STORE_KEY_PREFIX + key, defaultValue);
    }

    public static void storeLong(Context context, String key, long value) {
        SharedPreferences pre = context.getSharedPreferences(STORE_NAME,
                Context.MODE_PRIVATE);
        pre.edit().putLong(STORE_KEY_PREFIX + key, value).commit();
    }

    public static long readLong(Context context, String key, long defaultValue) {
        SharedPreferences pre = context.getSharedPreferences(STORE_NAME,
                Context.MODE_PRIVATE);
        return pre.getLong(STORE_KEY_PREFIX + key, defaultValue);
    }

    /**
     * 存int类型
     */
    public static void storeInteger(Context context, String key, int value) {
        SharedPreferences pre = context.getSharedPreferences(STORE_NAME,
                Context.MODE_APPEND);
        pre.edit().putInt(STORE_KEY_PREFIX + key, value).commit();

    }

    /**
     * 取int类型
     */
    public static int readInteger(Context context, String key, int defaultValue) {
        SharedPreferences pre = context.getSharedPreferences(STORE_NAME,
                Context.MODE_PRIVATE);
        return pre.getInt(STORE_KEY_PREFIX + key, defaultValue);
    }

}
