package com.tfn.letoutiao.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tfn.letoutiao.R;
import com.tfn.letoutiao.bean.NewsDataBean;
import com.bumptech.glide.Glide;

/**
 * Created by tf on 2017/4/29.
 */

public class NewsDataAdapter extends BaseQuickAdapter<
        NewsDataBean.ResultBean.DataBean, BaseViewHolder>{


    public NewsDataAdapter(){
        super(R.layout.item_news_data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsDataBean.ResultBean.DataBean item) {
        helper.setText(R.id.news_detail_title, item.getTitle());
        helper.setText(R.id.news_detail_author, item.getAuthor_name());
        helper.setText(R.id.news_detail_date, item.getDate());
        helper.addOnClickListener(R.id.ll_news_detail);
        Glide.with(mContext)
                .load(item.getThumbnail_pic_s())
                .placeholder(R.mipmap.ic_error)
                .error(R.mipmap.ic_error)
                .crossFade()
                .centerCrop()
                .into((ImageView) helper.getView(R.id.news_detail_pic));
    }
}
