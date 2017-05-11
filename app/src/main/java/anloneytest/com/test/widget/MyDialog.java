package anloneytest.com.test.widget;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;

import java.lang.ref.WeakReference;

import anloneytest.com.test.R;

/**
 * Created by Anloney on 2016/3/30.
 */
public class MyDialog {

    private static ProgressDialog dialog;
    private static CommonDialog commonDialog; // 默认的对话框
    private static Window window = null;

    private static CommitConfiListener confiListen;// 确认取消提示的接口参数
    private static SelectTypeListener mSelectTypeListener;//选择类型
    private static CommitNegativeConfiListener negativeListen;// 拒绝的操作提示的接口参数

    private static WeakReference<Activity> mActivityRefer; //防止对话框显示异常 ，Unable to add window

    // 提交确定或者删除的接口
    public interface CommitConfiListener {
        void onClik();
    }

    // 选择类型的接口
    public interface SelectTypeListener {
        void onClik(int posi);
    }

    // 提取消或者拒绝的接口
    public interface CommitNegativeConfiListener {
        void onClik();
    }

    // 支付点击确定的接口 返回支付密码
    public interface CommitPayListener {
        void onClik(String mPwd);
    }

    private static void showDialog(Context mContext, String message, boolean mCancelable, boolean outCancel) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        //弱引用
        mActivityRefer = new WeakReference<Activity>((Activity) mContext);
        if (mActivityRefer.get() != null && !mActivityRefer.get().isFinishing()) {
            dialog = new ProgressDialog(mContext);
            dialog.setMessage(message);
            dialog.setCanceledOnTouchOutside(outCancel);// 设置点击Dialog外部任意区域不可以关闭
            dialog.setCancelable(mCancelable);//可以取消
            dialog.show();
        }
    }

    /**
     * 创建正在加载对话框
     */
    public static void onCreateDialog(Context context, String message) {
        showDialog(context, message, true, true);
    }

    /**
     * 创建正在加载对话框 当页面是viewpager左右滑动的时候 加载这个对话框
     */
    public static void onCreateViewPagerDialog(Context context, String message) {
        if (dialog == null) {
            dialog = new ProgressDialog(context);
            dialog.setMessage(message);
            dialog.setIndeterminate(true);
            dialog.setCancelable(true);
            dialog.show();
        }
    }

    /**
     * 关闭当前提示对话框
     */
    public static void onfinishDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    /**
     * 弹出是否确定删除或取消某种操作 取消操作也有回调 背景是白色的对话框
     */
    public static void confirmAndCancleNegativeCommonDialog(final Context context,
                                                            final String titleStr, final String message,
                                                            boolean isOutsideCancel, CommitConfiListener confirmListen, CommitNegativeConfiListener negaListener) {
        if (commonDialog != null && commonDialog.isShowing()) {
            commonDialog.dismiss();
        }
        confiListen = confirmListen;
        negativeListen = negaListener;
        commonDialog = new CommonDialog(context, R.style.add_dialog, titleStr,
                message, new CommonDialog.MyDialogListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.cancleBtn:
                        negativeListen.onClik();
                        commonDialog.dismiss();
                        break;
                    case R.id.okBtn:
                        confiListen.onClik();
                        commonDialog.dismiss();
                        break;
                }
            }
        }, false);// 创建Dialog并设置样式主题
        showCommonDialog(commonDialog, isOutsideCancel);
    }

    /**
     * 弹出是否退出考试的对话框
     */
    public static void confirmAndCancleSetText(final Context context,
                                               final String titleStr, final String message, final String okStr, final String cancelStr,
                                               boolean isOutsideCancel, CommitConfiListener confirmListen, CommitNegativeConfiListener negaListener) {
        if (commonDialog != null && commonDialog.isShowing()) {
            commonDialog.dismiss();
        }
        confiListen = confirmListen;
        negativeListen = negaListener;
        commonDialog = new CommonDialog(context, R.style.add_dialog, titleStr,
                message, okStr, cancelStr, new CommonDialog.MyDialogListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.cancleBtn:
                        commonDialog.dismiss();
                        negativeListen.onClik();
                        break;
                    case R.id.close_img:
                        commonDialog.dismiss();
                        break;
                    case R.id.okBtn:
                        commonDialog.dismiss();
                        confiListen.onClik();
                        break;
                }
            }
        }, true);// 创建Dialog并设置样式主题
        showCommonDialog(commonDialog, isOutsideCancel);
    }

    /**
     * 弹出是否确定删除或取消某种操作 只有确定点击有回调 背景是白色的对话框
     */
    public static void confirmCommonDialog(final Context context,
                                           final String titleStr, final String message,
                                           boolean isOutsideCancel, CommitConfiListener confirmListen) {
        if (commonDialog != null && commonDialog.isShowing()) {
            commonDialog.dismiss();
        }
        confiListen = confirmListen;
        commonDialog = new CommonDialog(context, R.style.add_dialog, titleStr,
                message, new CommonDialog.MyDialogListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.cancleBtn:
                        commonDialog.dismiss();
                        break;
                    case R.id.okBtn:
                        confiListen.onClik();
                        commonDialog.dismiss();
                        break;
                }
            }
        }, false);// 创建Dialog并设置样式主题
        showCommonDialog(commonDialog, isOutsideCancel);
    }

    private static void showCommonDialog(Dialog dialog, boolean mCancelable) {
        // 创建Dialog并设置样式主题
        dialog.setCanceledOnTouchOutside(mCancelable);// 设置点击Dialog外部任意区域不可以关闭Dialog
        dialog.setCancelable(mCancelable);
        window = dialog.getWindow();
        window.setWindowAnimations(R.style.dialogWindowAnim);
        dialog.show();
    }
}
