package anloneytest.com.test.testactivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import anloneytest.com.test.R;
import anloneytest.com.test.adapter.WrongTestNumberAdapter;
import anloneytest.com.test.base.BaseActivity;
import anloneytest.com.test.bean.SubjectBean;
import butterknife.Bind;
import butterknife.ButterKnife;

/*
错题列表题号页
* */
public class WrongTestNumberListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.title_name_text)
    TextView titleText;
    @Bind(R.id.title_right_opera_text)
    TextView viewWrongListText;
    @Bind(R.id.wrong_test_listview)
    ListView mylistview;

    private Context mContext;
    private ArrayList<SubjectBean> mWrongSubjectList;//交卷后错题试题的数组

    private WrongTestNumberAdapter mWrongNumberAdapter;//错误问题题号的adapter

    @Override
    protected void initView() {
        mContext = this;
        setContentView(R.layout.activity_wrong_test_list);
        ButterKnife.bind(this);
    }

    @Override
    protected void initTitleBar() {
        viewWrongListText.setText(getString(R.string.view_wrog_subject));
        titleText.setText(getString(R.string.wrong_test_list));
        viewWrongListText.setVisibility(View.VISIBLE);
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void viewSetClickListener() {
        viewWrongListText.setOnClickListener(this);
    }

    @Override
    protected void getData() {
        mWrongSubjectList = (ArrayList<SubjectBean>) getIntent().getSerializableExtra("wrongtest");

        mWrongNumberAdapter = new WrongTestNumberAdapter(mContext, mWrongSubjectList);
        mylistview.setAdapter(mWrongNumberAdapter);
        mylistview.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //进入错题页面  从第一条开始
        Intent intent = new Intent(mContext, WrongTestListActivity.class);
        intent.putExtra("wrongtest", mWrongSubjectList);
        intent.putExtra("selectposi", 0);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //进入错题页面  从当前选择的位置开始
        Intent intent = new Intent(mContext, WrongTestListActivity.class);
        intent.putExtra("wrongtest", mWrongSubjectList);
        intent.putExtra("selectposi", position);
        startActivity(intent);
    }
}