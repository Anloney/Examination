package anloneytest.com.test.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
 * 考试试题的adatper
 */
public class TestAdapter extends PagerAdapter {

    private Context mContext;

    private ViewPager mViewPager;

    private int mPosition;//当前题目的位置

    private ArrayList<SubjectBean> mSubjectList;
    private OnPageSwitchListener mListener;//滑动时的listener

    private TestOptionAdater mOptionAdapter;//选项的adapter

    public TestAdapter(Context mContext, ArrayList<SubjectBean> list) {
        this.mContext = mContext;
        mSubjectList = list;
    }

    public void setView(ViewPager viewPager, OnPageSwitchListener listener) {
        this.mViewPager = viewPager;
        this.mListener = listener;
        setPagerAdatper();
    }

    //设置适配器
    private void setPagerAdatper() {
        mViewPager.setAdapter(this);
        // 默认在第一道题
        mPosition = 0;
        mViewPager.setCurrentItem(mPosition);
        // 切换试题
        mViewPager.addOnPageChangeListener(new MyOnPagerChangeListener());
    }

    //设置到当前选择的题号对应的某个位置
    public void setPositionCurrent(int posi) {
        if (mViewPager != null) {
            mPosition = posi;
            mViewPager.setCurrentItem(posi);
        }
    }

    //左右按钮点击事件
    public void onClickView(View view) {
        switch (view.getId()) {
            //上一题目
            case R.id.test_last_subject_text:
                if (mPosition != 0) {
                    mPosition = mPosition - 1;
                    if (mViewPager != null) {
                        mViewPager.setCurrentItem(mPosition);
                    }
                }
                break;
            //下一题
            case R.id.test_next_subject_text:
                if (mPosition < mSubjectList.size() - 1) {
                    mPosition = mPosition + 1;
                    if (mViewPager != null) {
                        mViewPager.setCurrentItem(mPosition);
                    }
                }
                break;
        }
    }

    @Override
    public int getCount() {
        return mSubjectList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //加载题目
        View view = View.inflate(mContext, R.layout.adapter_subject_switch_item, null);
        TextView adapterTestTitleText = (TextView) view.findViewById(R.id.adapter_test_title_text);
        ImageView adapterTestTitleImg = (ImageView) view.findViewById(R.id.adapter_test_pic_img);
        ListView adapterSelectListview = (ListView) view.findViewById(R.id.adapter_select_listview);
        adapterTestTitleText.setText(mSubjectList.get(position).getQuestion());

        //判断是否有图片
        if (!StringHelper.isEmpty(mSubjectList.get(position).getPic())) {
            adapterTestTitleImg.setVisibility(View.VISIBLE);
            //根据名字获取图片的本地id
            int resId = UtilOperation.getImageResourceId(mSubjectList.get(position).getPic());
            adapterTestTitleImg.setBackgroundResource(resId);
        } else {
            adapterTestTitleImg.setVisibility(View.GONE);
        }
        //设置adapter
        mOptionAdapter = new TestOptionAdater(adapterSelectListview, position);
        adapterSelectListview.setAdapter(mOptionAdapter);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public interface OnPageSwitchListener {
        //是否是最后一个
        void onSelect(int position);
    }

    private class MyOnPagerChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mPosition = position;
            //获取对应的当前题目的位置
            mListener.onSelect(mPosition);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    public class TestOptionAdater extends BaseAdapter implements AdapterView.OnItemClickListener {
        ListView mlistView;
        int mCurrentPosition;

        //构造方法, ArrayList<String> list
        public TestOptionAdater(ListView listView, int posi) {
            mlistView = listView;
            mCurrentPosition = posi;
            mlistView.setOnItemClickListener(this);
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
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.adapter_test_option_item, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //判断当前选择的选项是那个 并且显示选中状态
            String selectOption = mSubjectList.get(mCurrentPosition).getSelectedOption();
            //如果是当前用户选择的 则设为选中状态
            if (StringHelper.isEmpty(selectOption)) {
                //设置未选中状态
                viewHolder.adapterSelectImg.setBackgroundResource(R.drawable.icon_unselected_circle_img);
            } else {
                if ((selectOption.equals("A") && position == 0) || (selectOption.equals("B") && position == 1) ||
                        (selectOption.equals("C") && position == 2) || (selectOption.equals("D") && position == 3)) {
                    viewHolder.adapterSelectImg.setBackgroundResource(R.drawable.icon_selected_circle_img);
                } else {
                    //设置未选中状态
                    viewHolder.adapterSelectImg.setBackgroundResource(R.drawable.icon_unselected_circle_img);
                }
            }
            //选项对应的内容
            String optionText = "";
            if (position == 0) {
                optionText = "[A] " + mSubjectList.get(mCurrentPosition).getOptionA();
            } else if (position == 1) {
                optionText = "[B] " + mSubjectList.get(mCurrentPosition).getOptionB();
            } else if (position == 2) {
                optionText = "[C] " + mSubjectList.get(mCurrentPosition).getOptionC();
            } else if (position == 3) {
                optionText = "[D] " + mSubjectList.get(mCurrentPosition).getOptionD();
            }
            viewHolder.adapterOptionText.setText(optionText);
            return convertView;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //设置源数据选中状态
            if (position == 0) {
                mSubjectList.get(mCurrentPosition).setSelectedOption("A");
            } else if (position == 1) {
                mSubjectList.get(mCurrentPosition).setSelectedOption("B");
            } else if (position == 2) {
                mSubjectList.get(mCurrentPosition).setSelectedOption("C");
            } else if (position == 3) {
                mSubjectList.get(mCurrentPosition).setSelectedOption("D");
            }
            //刷新数据
            notifyDataSetChanged();
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
