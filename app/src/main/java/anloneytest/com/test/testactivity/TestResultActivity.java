package anloneytest.com.test.testactivity;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import anloneytest.com.test.R;
import anloneytest.com.test.activity.MainActivity;
import anloneytest.com.test.base.BaseActivity;
import anloneytest.com.test.bean.SubjectBean;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Anloney on 2016/8/3.
 * 考试结果页面
 */
public class TestResultActivity extends BaseActivity {

    @Bind(R.id.title_name_text)
    TextView titleNameText;
    @Bind(R.id.btn_back)
    ImageView titleBackImg;
    @Bind(R.id.result_introduce_text)
    TextView resultIntroduceText;
    @Bind(R.id.test_result_right_text)
    TextView testResultRightText;
    @Bind(R.id.wrong_test_list_btn)
    Button wronglistBtn;

    private Context mContext;

    private String mRightCounts;//答对的题目数量

    private ArrayList<SubjectBean> mWrongSubjectList;//交卷后错题试题的数组

    @Override
    protected void initView() {
        mContext = this;
        setContentView(R.layout.activity_test_result);
        ButterKnife.bind(this);
    }

    @Override
    protected void initTitleBar() {
        titleNameText.setText(getString(R.string.subject_result));
    }

    @Override
    protected void findViews() {
    }

    @Override
    protected void viewSetClickListener() {
        titleBackImg.setOnClickListener(this);
    }

    @Override
    protected void getData() {
        mRightCounts = getIntent().getStringExtra("rightCount");
        mWrongSubjectList = (ArrayList<SubjectBean>) getIntent().getSerializableExtra("wrongtest");
        //显示答对多少道题
        testResultRightText.setText(String.format(getString(R.string.answer_right_total),
                mRightCounts));
//        //如果全对 不显示错题列表
//        if (Integer.parseInt(mRightCounts) == Constants.TEST_COUNT) {
//            wronglistBtn.setVisibility(View.GONE);
//        }
    }

    @OnClick({R.id.signup_confirm_btn, R.id.btn_back, R.id.wrong_test_list_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup_confirm_btn:
            case R.id.btn_back:
                //进入到首页
                toMainHome();
                break;
            case R.id.wrong_test_list_btn:
                //进入错题列表
                toWrongTestList();
                break;
        }
    }

    //进入错题列表
    private void toWrongTestList() {
        Intent intent = new Intent(mContext, WrongTestNumberListActivity.class);
        intent.putExtra("wrongtest", mWrongSubjectList);
        startActivity(intent);
    }

    private void toMainHome() {
        Intent intent = new Intent(mContext, MainActivity.class);
        //会直接跳到首页 并且是重新进入 执行oncreate
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //如果加上这句话 就会重用之前的MyGroupListActivity，同时调用它的onNewIntent()方法。
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            //进入首页
            toMainHome();
        }
        return super.onKeyDown(keyCode, event);
    }
}
