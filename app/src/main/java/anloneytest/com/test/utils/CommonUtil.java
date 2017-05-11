package anloneytest.com.test.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import anloneytest.com.test.R;


/**
 * <pre>
 * 功能说明:一些通用的处理方法类
 * 日期:	2015-03-19
 * 开发者:anjingshuai
 * </pre>
 */
public class CommonUtil {
    private static Boolean isExit = false;// 退出标志位

    /**
     * 返回两个数中的随机数
     */
    public static String randomNumber(int fitstNum, int secondNum) {

        return String
                .valueOf((int) (Math.random() * (secondNum + 1 - fitstNum) + fitstNum));// 生成随机数
    }

    /**
     * 判断存储卡是否存在
     *
     * @return
     */
    public static boolean existSDcard() {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            return true;
        } else
            return false;
    }

    /**
     * dp转成px
     *
     * @param dipValue
     * @return
     */
    public static int dip2px(float dipValue, Context mContext) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px转成dp
     *
     * @param pxValue
     * @return
     */
    public static int px2dip(float pxValue, Context mContext) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 判断list是否是空
     */
    public static boolean isListEmpty(List list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 连续按下两次返回键 退出
     */
    public static boolean isExit(int keyCode, KeyEvent event, Context context) {
        // 拦截back按键
        if ((keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)) {
            if (!isExit) {// 第一次按下返回键
                isExit = true;// 退出标志位设置成true
                ToastUtil.makeShortText(context,
                        R.string.exit_app);
                // 一个Timer()对象，如果用户在第一次按返回键两秒后没有再按一次返回键退出，表示用户取消了操作，则重新将标志位设置成false
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                }, 1000);
            } else {// 退出程序
                UtilOperation.exitApplication();
            }
            return true;
        }
        return true;
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(Context context) {
        if (((Activity) context).getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (((Activity) context).getCurrentFocus() != null) {
                ((InputMethodManager) context
                        .getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(((Activity) context).getCurrentFocus()
                                .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
    /**
     * post请求前要先转码
     * @return
     */
    public static String str2Utf8(String strOld) {
        try {
            return URLEncoder.encode(strOld, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * 获取手机状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c;
        Object obj;
        java.lang.reflect.Field field;
        int x;
        int statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            if (context != null) {
                statusBarHeight = context.getResources().getDimensionPixelSize(
                        x);
            }
            return statusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

}