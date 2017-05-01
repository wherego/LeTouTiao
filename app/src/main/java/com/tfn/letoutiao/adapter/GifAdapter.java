package com.tfn.letoutiao.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tfn.letoutiao.R;
import com.tfn.letoutiao.bean.GifBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;


/**
 * Created by tf on 2017/5/1.
 */

public class GifAdapter extends CommonAdapter<GifBean.ResultBean> {

    private Context context;

    public GifAdapter(Context context, List<GifBean.ResultBean> datas){
        super(context, R.layout.item_gif, datas);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder holder, GifBean.ResultBean resultBean, int position) {
        holder.setText(R.id.tv_gif_title, resultBean.getContent());
        String url = resultBean.getUrl();
        if(url.endsWith("f")){
            Glide.with(context)
                    .load(resultBean.getUrl())
                    .asGif()
                    .placeholder(R.mipmap.ic_error)
                    .into((ImageView) holder.getView(R.id.iv_gif));
        } else {
            Glide.with(context)
                    .load(url)
                    .placeholder(R.mipmap.ic_error)
                    .into((ImageView) holder.getView(R.id.iv_gif));
        }
    }
}
