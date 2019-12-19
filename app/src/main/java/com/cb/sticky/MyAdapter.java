package com.cb.sticky;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by cb on 2019/12/16.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    /**
     * context
     */
    public Context mContext;

    /**
     * 数据
     */
    public List<MyData> mDatas;

    public MyAdapter(Context context, List<MyData> data) {
        this.mContext = context;
        this.mDatas = data;
    }

    public void setDatas(List<MyData> data) {
        this.mDatas = data;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, final int position) {
        MyData data = mDatas.get(position);
        holder.mTv.setText(data.text);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点击了" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTv;

        public ViewHolder(View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.text);
            itemView.setTag(false);
        }
    }
}
