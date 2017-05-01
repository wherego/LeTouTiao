package com.tfn.letoutiao.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tfn.letoutiao.Constants;
import com.tfn.letoutiao.R;
import com.tfn.letoutiao.adapter.GifAdapter;
import com.tfn.letoutiao.bean.GifBean;
import com.tfn.letoutiao.net.QClient;
import com.tfn.letoutiao.net.QNewsService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tf on 2017/5/1.
 */

public class GifFragment extends Fragment{

    private RecyclerView rv;

    private GifAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gif, null);

        rv = (RecyclerView) view.findViewById(R.id.rv_gif);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        QClient.getInstance()
                .create(QNewsService.class, Constants.BASE_JUHE)
                .getGifData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GifBean>() {
                    @Override
                    public void accept(GifBean gifBean) throws Exception {
                        List<GifBean.ResultBean> result = gifBean.getResult();
                        mAdapter = new GifAdapter(getActivity(), result);
                        rv.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT).show();
                    }
                });

        return view;
    }




}
