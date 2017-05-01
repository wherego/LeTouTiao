package com.tfn.letoutiao.ui.fragment;

import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tfn.letoutiao.R;
import com.tfn.letoutiao.ui.activity.WebViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tf on 2017/4/30.
 */

public class AboutFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.about_img)
    ImageView img;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about,container,false);

        ButterKnife.bind(this, view);
        Glide.with(this)
                .load("http://scimg.jb51.net/allimg/160404/14-160404103545501.jpg")
                .centerCrop()
                .into(img);


        return view;
    }

    @OnClick({R.id.tv_about_github, R.id.tv_about_jianshu, R.id.tv_about_blog})
    public void onClick(View v){
        String text = ((TextView) v).getText().toString();
        String[] split = text.split(":");
        String   url   = split[1].trim() + ":" + split[2].trim();
        WebViewActivity.startUrl(getActivity(), url);
    }
}
