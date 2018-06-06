package com.yhy.widgetdemo.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.yhy.widget.core.dialog.InputDialogView;
import com.yhy.widget.core.recycler.div.RvDivider;
import com.yhy.widgetdemo.R;
import com.yhy.widgetdemo.activity.base.BaseActivity;
import com.yhy.widgetdemo.adapter.RvTestAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author  : 颜洪毅
 * e-mail  : yhyzgn@gmail.com
 * time    : 2018-06-06 23:45
 * version : 1.0.0
 * desc    :
 */
public class InputDialogActivity extends BaseActivity {

    private RecyclerView rvContent;

    private List<String> mDataList;
    private RvTestAdapter mAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_input_dialog;
    }

    @Override
    protected void initView() {
        rvContent = $(R.id.rv_content);
        rvContent.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        mDataList = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            mDataList.add("第" + (i + 1) + "条数据");
        }

        mAdapter = new RvTestAdapter(this, mDataList);
        rvContent.setAdapter(mAdapter);
        rvContent.addItemDecoration(new RvDivider.Builder(this).color(getResources().getColor(R.color.colorLine)).widthDp(0.1f).build());
    }

    @Override
    protected void initEvent() {
        mAdapter.setOnItemClickListener(new RvTestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final View itemView, RecyclerView.Adapter adapter, int position) {
                // 显示输入框弹窗
                InputDialogView.Builder builder = new InputDialogView.Builder(InputDialogActivity.this);
                builder
                        .anchor(itemView)
                        .listener(new InputDialogView.OnInputDialogListener() {
                            @Override
                            public void onPublish(InputDialogView dialog, CharSequence content) {
                                dialog.dismiss();
                                toast(content);
                            }

                            @Override
                            public void onShow(int offsetX, int offsetY, int[] position) {
                                // 点击某条评论则这条评论刚好在输入框上面，点击评论按钮则输入框刚好挡住按钮
                                rvContent.smoothScrollBy(0, offsetY, new AccelerateDecelerateInterpolator());
                            }

                            @Override
                            public void onDismiss() {
                            }
                        });
                builder.build().show();
            }
        });
    }
}
