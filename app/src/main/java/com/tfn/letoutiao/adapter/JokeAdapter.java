package com.tfn.letoutiao.adapter;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tfn.letoutiao.R;
import com.tfn.letoutiao.bean.JokeBean;

/**
 * Created by tf on 2017/4/30.
 */

public class JokeAdapter extends BaseQuickAdapter<
        JokeBean.ResultBean.DataBean, BaseViewHolder> {

    public JokeAdapter(){
        super(R.layout.item_joke);
    }

    @Override
    protected void convert(BaseViewHolder helper, JokeBean.ResultBean.DataBean item) {
        helper.setText(R.id.joke_content, item.getContent());
        helper.setText(R.id.joke_date, item.getUpdatetime());
    }
}
