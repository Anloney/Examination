package anloneytest.com.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import anloneytest.com.test.R;
import anloneytest.com.test.utils.UtilOperation;

/*
进入的导航页
* */
public class GuideActivity extends Activity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_guide);
        //进入首页
        toNextActivity();
    }

    //进入首页
    private void toNextActivity() {
        // 显示3秒的开机动画
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // 进入 主页面
                UtilOperation.toNewActivity(mContext, MainActivity.class);
                finish();
            }
        }, 3000);
    }


}