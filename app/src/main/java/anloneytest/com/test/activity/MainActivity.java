package anloneytest.com.test.activity;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import anloneytest.com.test.R;
import anloneytest.com.test.base.BaseActivity;
import anloneytest.com.test.testactivity.SimulationTestActivity;
import anloneytest.com.test.utils.CommonUtil;
import anloneytest.com.test.utils.StatusCode;
import anloneytest.com.test.utils.UtilOperation;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
首页面
* */
public class MainActivity extends BaseActivity {

    @Bind(R.id.title_name_text)
    TextView titleNameText;
    @Bind(R.id.btn_back)
    ImageView btnBack;

    private Context mContext;

    @Override
    protected void initView() {
        mContext = this;
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void initTitleBar() {
        titleNameText.setText(getString(R.string.main_title));
    }

    @Override
    protected void findViews() {
        btnBack.setVisibility(View.GONE);
    }

    @Override
    protected void viewSetClickListener() {

    }

    @Override
    protected void getData() {
    }

    @OnClick({R.id.random_test_text, R.id.real_test_text})
    public void onClick(View view) {
        switch (view.getId()) {
            //随机题目
            case R.id.random_test_text:
                //生成试题
                generateSubject(StatusCode.IS_COMMON_PARAMS);
                break;
            //所有题目
            case R.id.real_test_text:
                //生成试题
                generateSubject(StatusCode.NO_COMMON_PARAMS);
                break;
        }
    }

    //跳转到考试页面
    private void generateSubject(String type) {
        UtilOperation.toNewActivityWithStringExtra(mContext, SimulationTestActivity.class,
                "type", type);
    }

    // 监听返回键点击事件，执行是否退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 拦截back按键
        CommonUtil.isExit(keyCode, event, mContext);
        return true;
    }
}