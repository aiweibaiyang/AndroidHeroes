package com.example.systemtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 25400 on 2019/12/9.
 */

public class AMProcessAdapter extends BaseAdapter {

    private List<AMProcessInfo> mListData = null;
    private LayoutInflater mInflater;

    public AMProcessAdapter(Context context, List<AMProcessInfo> mListData) {
        mInflater = (LayoutInflater)  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mListData = mListData;
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.am_process_item, null);
            holder = new ViewHolder();
            holder.tvPID = (TextView) convertView.findViewById(R.id.tv_pid);
            holder.tvUID = (TextView) convertView.findViewById(R.id.tv_uid);
            holder.tvMemorySize = (TextView) convertView.findViewById(R.id.tv_size);
            holder.tvProcessName = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        AMProcessInfo processInfo = (AMProcessInfo) getItem(position);
        holder.tvPID.setText("Pid:"+ processInfo.getPid());
        holder.tvUID.setText("Uid:"+processInfo.getUid());
        holder.tvMemorySize.setText(processInfo.getMemorySize()+ "KB");
        holder.tvProcessName.setText(processInfo.getProcessName());
        return convertView;
    }

    class ViewHolder {
        TextView tvPID;
        TextView tvUID;
        TextView tvMemorySize;
        TextView tvProcessName;
    }
}
