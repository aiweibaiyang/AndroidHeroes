package com.example.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 25400 on 2019/12/16.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<String> mData;

    public RecyclerAdapter(List<String> mData) {
        this.mData = mData;
    }

    public AdapterView.OnItemClickListener itemClickListener;

    public void setOnItemClickListener(AdapterView.OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView textView;

        public ViewHolder(View itemView){
            super(itemView);
            textView = (TextView) itemView;
            textView.setOnClickListener(this);
        }

        // 通过接口回调来实现RecyclerView的点击事件
        @Override
        public void onClick(View v) {
            if (itemClickListener != null){
                itemClickListener.onItemClick(v, getPosition());
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 将布局转化为View并传递给RecyclerView封装好的ViewHolder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        // 建立起ViewHolder中视图与数据的关联
        viewHolder.textView.setText(mData.get(i) + i);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
