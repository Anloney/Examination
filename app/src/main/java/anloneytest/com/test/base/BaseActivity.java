package anloneytest.com.test.base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import anloneytest.com.test.R;
import anloneytest.com.test.utils.UtilOperation;

/**
 * <pre>
 * 功能说明: Activity的基本类BaseActivity
 * 日期:	2016-03-29
 * 开发者:
 * </pre>
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener {

    private ImageView backImg; // 返回上一页面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //添加到linkactivity列表中
        addToLinkedActivity();
        //初始化
        initView();
        backImg = (ImageView) findViewById(R.id.btn_back);
        if (backImg != null) {
            backImg.setVisibility(View.VISIBLE);
            backImg.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        //控件初始化
        findViews();
        // 初始化标题
        initTitleBar();
        //控件点击事件
        viewSetClickListener();
        //赋值操作
        getData();
//        // 初始化状态栏
//        initWindow();
    }

    //添加activity
    private void addToLinkedActivity() {
        UtilOperation.addActivity(this);
    }

    protected abstract void initView();

    protected abstract void initTitleBar();

    protected abstract void findViews();

    protected abstract void viewSetClickListener();

    protected abstract void getData();

    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setStatusBarTintColor(getResources().getColor(
                R.color.system));
    }

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
