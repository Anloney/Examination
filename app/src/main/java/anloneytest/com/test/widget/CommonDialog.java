package anloneytest.com.test.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import anloneytest.com.test.R;
import anloneytest.com.test.utils.StringHelper;


/**
 * <pre>
 * 功能说明: 自定义dialog
 * 日期:	2014-01-06
 * 开发者:anjingshuai
 * </pre>
 */
public class CommonDialog extends Dialog implements OnClickListener {

    private TextView detailText;
    private TextView titleText;

    private Button okBtn;
    private Button cancleBtn;

    private ImageView mCloseImg;//关闭的图片

    private String detailStr;//描述
    private String titleStr = "";// 标题
    private String okString;//确定按钮的内容
    private String cancelStr = "";// 取消的内容

    private View customView;
    private Activity mContext;

    private boolean isShowClose;//是否显示关闭按钮
    private boolean isOneButton;//是否只有一个确定按钮

    private MyDialogListener listener;

    // 对话框的确定或者取消的接口
    public interface MyDialogListener {
        void onClick(View view);
    }

    /**
     * 对话框退出 只有白色背景
     */
    public CommonDialog(Context context, int theme, String titleStr, String detailStr,
                        MyDialogListener listener, boolean isOneBtn, boolean isShowClose) {
        super(context, theme);
        this.listener = listener;
        this.titleStr = titleStr;
        this.detailStr = detailStr;
        this.mContext = (Activity) context;
        this.isOneButton = isOneBtn;
        this.isShowClose = isShowClose;
        customView = getLayoutInflater().inflate(
                R.layout.activity_common_dialog, null);

    }

    /**
     * 对话框退出 确定取消对话框
     */
    public CommonDialog(Context context, int theme, String titleStr, String detailStr,
                        MyDialogListener listener, boolean isShowClose) {
        super(context, theme);
        this.listener = listener;
        this.titleStr = titleStr;
        this.detailStr = detailStr;
        this.mContext = (Activity) context;
        this.isShowClose = isShowClose;
        customView = getLayoutInflater().inflate(
                R.layout.activity_common_dialog, null);

    }

    /**
     * 对话框退出 确定取消按钮重新设置显示内容
     */
    public CommonDialog(Context context, int theme, String titleStr, String detailStr, String okText,
                        String cancelText, MyDialogListener listener, boolean isShowClose) {
        super(context, theme);
        this.listener = listener;
        this.titleStr = titleStr;
        this.detailStr = detailStr;
        this.okString = okText;
        this.cancelStr = cancelText;
        this.mContext = (Activity) context;
        this.isShowClose = isShowClose;
        customView = getLayoutInflater().inflate(
                R.layout.activity_common_dialog, null);

    }

    public View getCustomView() {
        return customView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置页面布局
        setContentView(customView);
        initCommontView();
        //初始化对话框大小
        initDialogView();
    }

    private void initDialogView() {
        Window dialogWindow = this.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager m = mContext.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth() - 80); // 宽度设置为屏幕两边减80
        dialogWindow.setAttributes(p);
    }

    // 初始化白色背景的对话框
    private void initCommontView() {
        detailText = (TextView) findViewById(R.id.detailText);
        detailText.setText(detailStr);
        mCloseImg = (ImageView) findViewById(R.id.close_img);
        okBtn = (Button) findViewById(R.id.okBtn);
        cancleBtn = (Button) findViewById(R.id.cancleBtn);
        okBtn.setOnClickListener(this);
        cancleBtn.setOnClickListener(this);
        mCloseImg.setOnClickListener(this);
        titleText = (TextView) findViewById(R.id.titleText);
        titleText.setText(titleStr);
        if (isShowClose) {
            mCloseImg.setVisibility(View.VISIBLE);
            if (!StringHelper.isEmpty(okString)) {
                okBtn.setText(okString);
            }
            cancleBtn.setText(cancelStr);
        } else {
            mCloseImg.setVisibility(View.INVISIBLE);
        }
        //如果是一个按钮
        if (isOneButton) {
            cancleBtn.setVisibility(View.GONE);
        } else {
            cancleBtn.setBackgroundResource(R.drawable.dialog_btn_left);
            okBtn.setBackgroundResource(R.drawable.dialog_btn_right);
        }
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view);
    }
}
