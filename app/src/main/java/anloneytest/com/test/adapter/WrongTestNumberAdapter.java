package anloneytest.com.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import anloneytest.com.test.R;
import anloneytest.com.test.bean.SubjectBean;
import anloneytest.com.test.utils.StringHelper;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Anloney on 2016/8/3.
 * 错题列表题号的adatper
 */
public class WrongTestNumberAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<SubjectBean> mSubjectWrongList;

    public WrongTestNumberAdapter(Context mContext, ArrayList<SubjectBean> list) {
        this.mContext = mContext;
        this.mSubjectWrongList = list;
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
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_wrong_test_number_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.adapterNumberText.setText(String.valueOf(mSubjectWrongList.get(position).getTestNo()));
        String status;//答错或者是未答
        if (StringHelper.isEmpty(mSubjectWrongList.get(position).getSelectedOption())) {
            status = mContext.getString(R.string.un_selected);
        } else {
            status = mContext.getString(R.string.answer_error);
        }
        viewHolder.adapterStatusText.setText(mContext.getString(R.string.test_status) + status);
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.adapter_number_text)
        TextView adapterNumberText;
        @Bind(R.id.adapter_status_text)
        TextView adapterStatusText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
