package anloneytest.com.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import anloneytest.com.test.R;
import anloneytest.com.test.bean.SubjectBean;
import anloneytest.com.test.utils.StringHelper;
import anloneytest.com.test.utils.UtilOperation;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Anloney on 2016/8/3.
 * 错题列表的adatper
 */
public class WrongTestAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<SubjectBean> mSubjectWrongList;
    private TestOptionAdater mOptionAdapter;//选项的adapter

    public WrongTestAdapter(Context mContext, ArrayList<SubjectBean> list) {
        this.mContext = mContext;
        mSubjectWrongList = list;
    }

    @Override
    public int getCount() {
        return mSubjectWrongList.size();
    }

    @Override
    public Object getItem(int position) {
        return mSubjectWrongList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_wrong_test_item,
                    parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        //设置第几题 和用户选择的答案   如果用户未答此题 则显示未答
        String selectAnsewer = mSubjectWrongList.get(position).getSelectedOption();
        if (StringHelper.isEmpty(selectAnsewer)) {
            mViewHolder.adapterTestNoText.setText(String.format(mContext.getString(R.string.test_number_text),
                    mSubjectWrongList.get(position).getTestNo()) + "：" + mContext.getString(R.string.un_selected));
        } else {
            mViewHolder.adapterTestNoText.setText(String.format(mContext.getString(R.string.test_number_text),
                    mSubjectWrongList.get(position).getTestNo()) + "：" + selectAnsewer);
        }
        //设置正确答案
        mViewHolder.adapterTestRightAnswerText.setText(String.format(mContext.getString(R.string.right_answer),
                mSubjectWrongList.get(position).getAnswer()));
        //设置题目问题
        mViewHolder.adapterTestTitleText.setText(mSubjectWrongList.get(position).getQuestion());
        //判断是否有图片
        if (!StringHelper.isEmpty(mSubjectWrongList.get(position).getPic())) {
            mViewHolder.adapterTestTitleImg.setVisibility(View.VISIBLE);
            //根据名字获取图片的本地id
            int resId = UtilOperation.getImageResourceId(mSubjectWrongList.get(position).getPic());
            mViewHolder.adapterTestTitleImg.setBackgroundResource(resId);
        } else {
            mViewHolder.adapterTestTitleImg.setVisibility(View.GONE);
        }
        //设置选项adapter
        mOptionAdapter = new TestOptionAdater(mViewHolder.adapterSelectListview, position);
        mViewHolder.adapterSelectListview.setAdapter(mOptionAdapter);
        mViewHolder.adapterSelectListview.setDivider(null);

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.adapter_test_no_text)
        TextView adapterTestNoText;
        @Bind(R.id.adapter_test_right_answer_text)
        TextView adapterTestRightAnswerText;
        @Bind(R.id.adapter_test_title_text)
        TextView adapterTestTitleText;
        @Bind(R.id.adapter_test_pic_img)
        ImageView adapterTestTitleImg;
        @Bind(R.id.adapter_select_listview)
        ListView adapterSelectListview;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public class TestOptionAdater extends BaseAdapter {
        ListView mlistView;
        int mCurrentPosition;

        //构造方法, ArrayList<String> list
        public TestOptionAdater(ListView listView, int posi) {
            mlistView = listView;
            mCurrentPosition = posi;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_test_option_item, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.adapterSelectImg.setVisibility(View.GONE);
            //选项对应的内容
            String optionText = "";
            if (position == 0) {
                optionText = "[A] " + mSubjectWrongList.get(mCurrentPosition).getOptionA();
            } else if (position == 1) {
                optionText = "[B] " + mSubjectWrongList.get(mCurrentPosition).getOptionB();
            } else if (position == 2) {
                optionText = "[C] " + mSubjectWrongList.get(mCurrentPosition).getOptionC();
            } else if (position == 3) {
                optionText = "[D] " + mSubjectWrongList.get(mCurrentPosition).getOptionD();
            }
            viewHolder.adapterOptionText.setText(optionText);
            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.adapter_select_img)
            ImageView adapterSelectImg;
            @Bind(R.id.adapter_option_text)
            TextView adapterOptionText;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
