package anloneytest.com.test.testactivity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.Collections;

import anloneytest.com.test.R;
import anloneytest.com.test.adapter.TestAdapter;
import anloneytest.com.test.base.BaseActivity;
import anloneytest.com.test.bean.SubjectBean;
import anloneytest.com.test.utils.Constants;
import anloneytest.com.test.utils.StatusCode;
import anloneytest.com.test.utils.StringHelper;
import anloneytest.com.test.utils.UtilOperation;
import anloneytest.com.test.widget.MyDialog;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Anloney on 2016/8/3.
 * 模拟考试答题考试页面
 */
public class SimulationTestActivity extends BaseActivity {

    @Bind(R.id.title_name_text)
    TextView titleNameText;
    @Bind(R.id.btn_back)
    ImageView titleBackImg;
    @Bind(R.id.title_right_opera_text)
    TextView titleRightOperaText;
    @Bind(R.id.test_number_text)
    TextView testNumberText;
    @Bind(R.id.test_subject_viewpager)
    ViewPager testSubjectViewpager;

    private Context mContext;

    private TestAdapter mTestAdapter;//测试的adapter

    private int mGrade;//考试分数 作对了多少道题
    private int mTotalGrade;//一共做了多少道题

    private ArrayList<Integer> mSubjectNumlist;//选中的随机题目列表
    private ArrayList<SubjectBean> mSubjectList;//最后获得的试题的数组
    private ArrayList<SubjectBean> mAllSubjectList;//试题源所有的试题的数组

    private ArrayList<SubjectBean> mWrongSubjectList;//交卷后错题试题的数组
    /*答案*/
    private String resultAnswer = "1.A  2.A  3.A  4.B  5.A  6.A  7.C  8.B  9.C  10.D  11.D  12.D  13.C  14.C  15.A  16.D  17.A  18.C  19.A  20.A  21.D  22.A  23.C  24.D  25.C  26.D  27.C  28.B  29.B  30.C  31.D  32.B  33.D  34.B  35.A  36.D  37.B  38.C  39.B  40.C  41.A  42.A  43.C  44.A  45.C  46.C  47.D  48.D  49.A  50.B  51.B  52.B  53.A  54.B  55.B  56.A  57.D  58.A  59.B  60.A  61.B  62.A  63.C  64.C  65.C  66.C  67.C  68.B  69.C  70.C  71.D  72.D  73.A  74.D  75.B  76.D  77.A  78.A  79.D  80.C  81.D  82.A  83.D  84.D  85.D  86.C  87.A  88.B  89.B  90.B  91.B  92.C  93.A  94.C  95.A  96.C  97.A  98.D  99.B  100.C  101.A  102.A  103.D  104.A  105.D  106.A  107.B  108.D  109.B  110.A  111.C  112.A  113.C  114.C  115.D  116.A  117.A  118.A  119.A  120.A  121.A  122.D  123.C  124.A  125.C  126.A  127.A  128.A  129.A  130.B  131.B  132.B  133.D  134.B  135.A  136.D  137.C  138.A  139.D  140.A  141.B  142.A  143.A  144.B  145.D  146.B  147.A  148.D  149.C  150.D  151.A  152.C  153.B  154.D  155.D  156.A  157.D  158.B  159.C  160.C  161.B  162.B  163.C";
    private ArrayList<String> mAnswerList;//试题源所有的试题的答案数组


    private String mType;//考试类型

    @Override
    protected void initView() {
        mContext = this;
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
    }

    @Override
    protected void initTitleBar() {
        titleNameText.setText(getString(R.string.type_test));
    }

    @Override
    protected void findViews() {
        titleRightOperaText.setVisibility(View.VISIBLE);
    }

    @Override
    protected void viewSetClickListener() {
        titleBackImg.setOnClickListener(this);
    }

    @Override
    protected void getData() {
        mType = getIntent().getStringExtra("type");
        //正在加载
        MyDialog.onCreateDialog(mContext, getString(R.string.loading));
        //初始化试题
        mSubjectList = new ArrayList<>();
        mAllSubjectList = new ArrayList<>();
        mSubjectNumlist = new ArrayList<>();
        mWrongSubjectList = new ArrayList<>();
        mAnswerList = new ArrayList<>();
        //初始化本地题库
        initLocalSubject();
    }

    //加载本地题库
    private void initLocalSubject() {
        //读取json的考试试题
        try {
            mAllSubjectList = UtilOperation.readAssetsText(mContext, Constants.TEST_SOURCE_NAME);
            //获取答案list
            getAnswerList();
            //随机
            if (StatusCode.IS_COMMON_PARAMS.equals(mType)) {
                //获取随机选取的数
                mSubjectNumlist = getTest();
                //从小到大排序
                Collections.sort(mSubjectNumlist);
                //循环选取随机到的数据 并插入到试题list里边
                for (int i = 0; i < mSubjectNumlist.size(); i++) {
                    int posi = mSubjectNumlist.get(i);
                    //设置题号 在查看错题列表的时候用
                    mAllSubjectList.get(posi).setTestNo(i + 1);
                    //添加试题到考试试题列表
                    mSubjectList.add(mAllSubjectList.get(posi));
                }
            } else {
                //设置题号
                for (int i = 0; i < mAllSubjectList.size(); i++) {
                    mAllSubjectList.get(i).setTestNo(i + 1);
                }
                //所有题目
                mSubjectList.addAll(mAllSubjectList);
            }
            //显示试题 开始考试
            showTestStartExam();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            //如果解析有异常则对话框消失
            MyDialog.onfinishDialog();
        }
    }

    /*获取答案的list*/
    private void getAnswerList() {
        String[] answerArray = resultAnswer.split("  ");
        //获取答案
        if (answerArray.length > 0) {
            for (String answer : answerArray) {
                if (answer.contains(".")) {
                    String testAnswer = answer.split("\\.")[1];
                    mAnswerList.add(testAnswer);
                }
            }
        }
        //循环设置答案
        for (int i = 0; i < mAllSubjectList.size(); i++) {
            mAllSubjectList.get(i).setAnswer(mAnswerList.get(i));
        }
    }

    //获取随机选取的试题
    private ArrayList<Integer> getTest() {
        ArrayList<Integer> mTatalList = new ArrayList<>();
        ArrayList<Integer> mList = UtilOperation.getRangeRandom(0,
                Constants.ALL_TEST_COUNT, Constants.TEST_COUNT);
        mTatalList.addAll(mList);
        return mTatalList;
    }

    //显示试题并开始考试
    private void showTestStartExam() {
        //考试试题的adapter
        mTestAdapter = new TestAdapter(mContext, mSubjectList);
        mTestAdapter.setView(testSubjectViewpager, new TestAdapter.OnPageSwitchListener() {
            @Override
            public void onSelect(int position) {
                testNumberText.setText(String.format(getString(R.string.test_number_text),
                        String.valueOf(position + 1)));
            }
        });
        //加载完毕对话框消失
        MyDialog.onfinishDialog();
    }

    @OnClick({R.id.test_last_subject_text, R.id.test_hand_in_text, R.id.test_next_subject_text,
            R.id.btn_back, R.id.title_right_opera_text})
    public void onClick(View view) {
        switch (view.getId()) {
            //上一题
            case R.id.test_last_subject_text:
                mTestAdapter.onClickView(view);
                break;
            //交卷
            case R.id.test_hand_in_text:
                handInSubject();
                break;
            //下一题
            case R.id.test_next_subject_text:
                mTestAdapter.onClickView(view);
                break;
            //所有题目
            case R.id.title_right_opera_text:
                //查看所有题目
                viewAllSubject();
                break;
            //返回上一个页面
            case R.id.btn_back:
                onClickBack();
                break;
        }
    }

    //查看所有题目
    private void viewAllSubject() {
        Intent intent = new Intent(mContext, AllSimulationTestSubjectActivity.class);
        intent.putExtra("subjelist", mSubjectList);
        startActivityForResult(intent, StatusCode.COMMON_REQUEST_CODE);
    }

    //交卷
    private void handInSubject() {
        //获取总的做题数目
        mTotalGrade = 0;
        for (int i = 0; i < mSubjectList.size(); i++) {
            //如果选择的答案不是空
            if (!StringHelper.isEmpty(mSubjectList.get(i).getSelectedOption())) {
                //循环获取用户作答的题目总数
                mTotalGrade++;
            }
        }
        //如果没有做完所有题目
        if (mTotalGrade < mSubjectList.size()) {
            MyDialog.confirmAndCancleSetText(mContext, getString(R.string.reform),
                    getString(R.string.unanswer_all_subject), getString(R.string.view_subject), getString(R.string.hand_in_subject),
                    true, new MyDialog.CommitConfiListener() {
                        @Override
                        public void onClik() {
                            //查看所有题目
                            viewAllSubject();
                        }
                    }, new MyDialog.CommitNegativeConfiListener() {
                        @Override
                        public void onClik() {
                            //提交试卷计算分数
                            commitSubject();
                        }
                    });
        } else {
            MyDialog.confirmCommonDialog(mContext, getString(R.string.reform), getString(R.string.hand_in_reform),
                    true, new MyDialog.CommitConfiListener() {
                        @Override
                        public void onClik() {
                            //提交试卷计算分数
                            commitSubject();
                        }
                    });
        }
    }

    //提交试卷计算分数 提交成功跳转到结果页面
    private void commitSubject() {
        //弹出正在加载对话框
        MyDialog.onCreateDialog(mContext, getString(R.string.handing_in_subject));
        //用户试题作答的所有答案
        for (int i = 0; i < mSubjectList.size(); i++) {
            //如果选择的答案不是空
            if (!StringHelper.isEmpty(mSubjectList.get(i).getSelectedOption())) {
                //计算作对了多少题
                if (mSubjectList.get(i).getAnswer().equals(mSubjectList.get(i).getSelectedOption())) {
                    mGrade++;
                } else {
                    //保存错题
                    mWrongSubjectList.add(mSubjectList.get(i));
                }
            } else {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (StatusCode.SELECT_SUBJECT_RESULT_CODE == resultCode) {
            int selectposi = data.getIntExtra("selectposi", 0);
            mTestAdapter.setPositionCurrent(selectposi);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            onClickBack();
        }
        return super.onKeyDown(keyCode, event); // 不会回到 home 页面
    }

    //点击返回键
    private void onClickBack() {
        MyDialog.confirmAndCancleSetText(mContext, getString(R.string.reform),
                getString(R.string.back_forbidden_hand), getString(R.string.view_subject), getString(R.string.hand_in_subject),
                true, new MyDialog.CommitConfiListener() {
                    @Override
                    public void onClik() {
                        //查看所有题目
                        viewAllSubject();
                    }
                }, new MyDialog.CommitNegativeConfiListener() {
                    @Override
                    public void onClik() {
                        //提交试卷计算分数
                        commitSubject();
                    }
                });
    }
}
