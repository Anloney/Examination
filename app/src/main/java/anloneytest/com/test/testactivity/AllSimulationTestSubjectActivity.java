package anloneytest.com.test.testactivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import anloneytest.com.test.R;
import anloneytest.com.test.adapter.AllSubjectAdapter;
import anloneytest.com.test.base.BaseActivity;
import anloneytest.com.test.bean.SubjectBean;
import anloneytest.com.test.utils.StatusCode;
import anloneytest.com.test.widget.MyDialog;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Anloney on 2016/8/3.
 * 模拟考试所有题目页面
 */
public class AllSimulationTestSubjectActivity extends BaseActivity {

    @Bind(R.id.title_name_text)
    TextView titleNameText;
    @Bind(R.id.btn_back)
    ImageView titleBackImg;
    @Bind(R.id.title_right_opera_text)
    TextView titleRightOperaText;
    @Bind(R.id.test_title_gridview)
    GridView allGridView;

    private Context mContext;

    private int mGrade;//考试分数 作对了多少道题

    private AllSubjectAdapter mAllSubjectAdapter;//所有题目的adapter
    private ArrayList<SubjectBean> mSubjectList;//试题的数组
    private ArrayList<SubjectBean> mWrongSubjectList;//错题列表

    @Override
    protected void initView() {
        mContext = this;
        setContentView(R.layout.activity_all_subject);
        ButterKnife.bind(this);
    }

    @Override
    protected void initTitleBar() {
        titleNameText.setText(getString(R.string.all_subjects));
    }

    @Override
    protected void findViews() {
        titleBackImg.setVisibility(View.VISIBLE);
        titleRightOperaText.setVisibility(View.VISIBLE);
        titleRightOperaText.setText(getString(R.string.continue_test));
    }

    @Override
    protected void viewSetClickListener() {

    }

    @Override
    protected void getData() {
        mWrongSubjectList = new ArrayList<>();
        mSubjectList = (ArrayList<SubjectBean>) getIntent().getSerializableExtra("subjelist");

        //设置题目适配器
        mAllSubjectAdapter = new AllSubjectAdapter(mContext, allGridView, mSubjectList);
    }

    @OnClick({R.id.test_hand_in_text, R.id.title_right_opera_text, R.id.btn_back})
    public void onClick(View view) {
        switch (view.getId()) {
            //交卷
            case R.id.test_hand_in_text:
                handinSubject();
                break;
            //继续考试
            case R.id.btn_back:
            case R.id.title_right_opera_text:
                finish();
                break;
        }
    }

    //设置到当前选择的题号对应的某个位置
    public void setSelectPositionCurrent(int posi) {
        Intent intent = new Intent();
        intent.putExtra("selectposi", posi);
        setResult(StatusCode.SELECT_SUBJECT_RESULT_CODE, intent);
        finish();
    }

    //交卷
    private void handinSubject() {
        MyDialog.confirmCommonDialog(mContext, getString(R.string.reform), getString(R.string.hand_in_reform),
                true, new MyDialog.CommitConfiListener() {
                    @Override
                    public void onClik() {
                        //提交试卷计算分数
                        commitSubject();
                    }
                });
    }

    //提交试卷计算分数 提交成功跳转到结果页面
    private void commitSubject() {
        //弹出正在加载对话框
        MyDialog.onCreateDialog(mContext, getString(R.string.handing_in_subject));
        for (int i = 0; i < mSubjectList.size(); i++) {
            //计算作对了多少题
            if (mSubjectList.get(i).getAnswer().equals(mSubjectList.get(i).getSelectedOption())) {
                mGrade++;
            } else {
                //保存错题
                mWrongSubjectList.add(mSubjectList.get(i));
            }
        }
        MyDialog.onfinishDialog();
        //提交试卷
        toTestResult();
    }

    //跳转到结果页面
    private void toTestResult() {
        Intent intent = new Intent(mContext, TestResultActivity.class);
        intent.putExtra("rightCount", String.valueOf(mGrade));
        intent.putExtra("wrongtest", mWrongSubjectList);
        startActivity(intent);
        finish();
    }
}
