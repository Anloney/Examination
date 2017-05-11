package anloneytest.com.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

import anloneytest.com.test.R;
import anloneytest.com.test.bean.SubjectBean;
import anloneytest.com.test.testactivity.AllSimulationTestSubjectActivity;
import anloneytest.com.test.utils.StringHelper;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Anloney on 2016/8/3.
 * 所有考题序号列表的gridview 的adatper
 */
public class AllSubjectAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

    private Context mContext;

    private GridView mGridView;
    private ArrayList<SubjectBean> mSubjectList;

    public AllSubjectAdapter(Context context, GridView gridView,
                             ArrayList<SubjectBean> subjectList) {
        this.mContext = context;
        this.mGridView = gridView;
        mSubjectList = subjectList;

        setView();
    }

    //设置适配器
    private void setView() {
        mGridView.setAdapter(this);
        mGridView.setOnItemClickListener(this);
    }

    @Override
    public int getCount() {
        return mSubjectList.size();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_all_subject_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.adapterSelectText.setText(String.valueOf(position + 1));
        //判断当前选择的选项是那个 并且显示选中状态
        String selectOption = mSubjectList.get(position).getSelectedOption();
        //如果是当前用户选择的 则设为选中状态
        if (!StringHelper.isEmpty(selectOption)) {
            //设置选中状态
            viewHolder.adapterSelectText.setBackgroundResource(R.drawable.icon_selected_circle_img);
            viewHolder.adapterSelectText.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            //设置未选中状态
            viewHolder.adapterSelectText.setBackgroundResource(R.drawable.icon_unselected_circle_img);
            viewHolder.adapterSelectText.setTextColor(mContext.getResources().getColor(R.color.system));
        }
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //设置源数据选中状态
        ((AllSimulationTestSubjectActivity) mContext).setSelectPositionCurrent(position);
    }

    class ViewHolder {
        @Bind(R.id.adapter_title_answered_text)
        TextView adapterSelectText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
