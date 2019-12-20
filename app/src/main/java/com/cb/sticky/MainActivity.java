package com.cb.sticky;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cb on 2019/12/16.
 */
public class MainActivity extends Activity {
    RecyclerView mRecyclerView;
    List<MyData> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        MyAdapter adapter = new MyAdapter(this, getData());
        mRecyclerView.addItemDecoration(new DividerDecoration(this));
        // 添加sticky view 管理
        mRecyclerView.addItemDecoration(new LinearStickyDecoration(adapter,
                new LinearStickyDecoration.IStickyItem<TextView, StickyData>() {
                    @Override
                    public TextView createStickyView() {
                        TextView textView = new TextView(MainActivity.this);
                        textView.setTextSize(20);
                        textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        textView.setTextColor(Color.parseColor("#000000"));
                        textView.setGravity(Gravity.CENTER_VERTICAL);
//                        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                                getResources().getDimensionPixelOffset(R.dimen.sticky_height)));
                        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                        textView.setPadding(40, 0, 0, 40);
                        return textView;
                    }

                    @Override
                    public void bindView(TextView view, StickyData data) {
                        view.setText(data.stickyText);
                    }

                    @Override
                    public StickyData obtainInsideDataByPosition(int position) {
                        if (data.get(position).type == 1) {
                            StickyData stickyData = new StickyData();
                            stickyData.stickyText = "sticky position " + data.get(position).stickyText;
                            return stickyData;
                        }
                        return null;
                    }

                    @Override
                    public StickyData obtainHoverData(int headPosition) {
                        for (int index = headPosition; index >= 0; index--) {
                            if (data.get(index).type == 1) {
                                StickyData stickyData = new StickyData();
                                stickyData.stickyText = "sticky position " + data.get(index).stickyText;
                                return stickyData;
                            }
                        }
                        return null;
                    }
                }));
        mRecyclerView.setAdapter(adapter);
    }

    List<MyData> getData() {
        data.add(new MyData("星期四", 4));
        data.add(new MyData("星期五", 5));
        data.add(new MyData("星期六", 6));
        data.add(new MyData("星期日", 7));

        data.add(new MyData("星期一", 1, "第一周"));
        data.add(new MyData("星期二", 2));
        data.add(new MyData("星期三", 3));
        data.add(new MyData("星期四", 4));
        data.add(new MyData("星期五", 5));
        data.add(new MyData("星期六", 6));
        data.add(new MyData("星期日", 7));

        data.add(new MyData("星期一", 1, "第二周\n第二周"));
        data.add(new MyData("星期二", 2));
        data.add(new MyData("星期三", 3));
        data.add(new MyData("星期四", 4));
        data.add(new MyData("星期五", 5));
        data.add(new MyData("星期六", 6));
        data.add(new MyData("星期日", 7));

        data.add(new MyData("星期一", 1, "第三周\n第三周\n第三周"));
        data.add(new MyData("星期二", 2));
        data.add(new MyData("星期三", 3));
        data.add(new MyData("星期四", 4));
        data.add(new MyData("星期五", 5));
        data.add(new MyData("星期六", 6));
        data.add(new MyData("星期日", 7));
        data.add(new MyData("星期一", 1, "第四周\n第四周\n第四周"));
        data.add(new MyData("星期二", 2));
        data.add(new MyData("星期三", 3));
        data.add(new MyData("星期四", 4));
        data.add(new MyData("星期五", 5));
        return data;
    }


    public class StickyData extends LinearStickyDecoration.BaseStickyData {
        String stickyText;
    }
}
