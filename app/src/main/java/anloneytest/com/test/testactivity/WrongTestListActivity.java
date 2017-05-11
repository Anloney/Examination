package anloneytest.com.test.testactivity;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import anloneytest.com.test.R;
import anloneytest.com.test.adapter.WrongTestAdapter;
import anloneytest.com.test.base.BaseActivity;
import anloneytest.com.test.bean.SubjectBean;
import butterknife.Bind;
import butterknife.ButterKnife;

/*
错题列表详细页
* */
public class WrongTestListActivity extends BaseActivity {

    @Bind(R.id.title_name_text)
    TextView titleText;
    @Bind(R.id.wrong_test_listview)
    ListView mylistview;

    private Context mContext;
    private ArrayList<SubjectBean> mWrongSubjectList;//交卷后错题试题的数组

    private int mSelectPosi;//当前选择的位置
    private WrongTestAdapter mWrongAdapter;//错误问题的adapter

    @Override
    protected void initView() {
        mContext = this;
        setContentView(R.layout.activity_wrong_test_list);
        ButterKnife.bind(this);
    }

    @Override
    protected void initTitleBar() {
        titleText.setText(getString(R.string.wrong_test_list));
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void viewSetClickListener() {

    }

    @Override
    protected void getData() {
        mSelectPosi = getIntent().getIntExtra("selectposi", 0);
        mWrongSubjectList = (ArrayList<SubjectBean>) getIntent().getSerializableExtra("wrongtest");

        mWrongAdapter = new WrongTestAdapter(mContext, mWrongSubjectList);
        mylistview.setAdapter(mWrongAdapter);
        mylistview.setSelection(mSelectPosi);
    }

    @Override
    public void onClick(View v) {

    }
}