package com.tfn.letoutiao.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.tfn.letoutiao.Constants;
import com.tfn.letoutiao.R;
import com.tfn.letoutiao.adapter.NewsDataAdapter;
import com.tfn.letoutiao.bean.NewsDataBean;
import com.tfn.letoutiao.net.QClient;
import com.tfn.letoutiao.net.QNewsService;
import com.tfn.letoutiao.ui.activity.WebViewActivity;
import com.tfn.letoutiao.utils.ShareUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tf on 2017/4/30.
 */

public class NewsBaseFragment extends Fragment{

    private RecyclerView recyclerView;
    private SwipeRefreshLayout srl;

    private NewsDataAdapter mAdapter;

    //新闻类型
    private String type;

    public NewsBaseFragment(){

    }

    @SuppressLint("ValidFragment")
    public NewsBaseFragment(String type){
        this.type = type;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_base, container, false);

        mAdapter = new NewsDataAdapter();
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);

        //设置下拉刷新
        srl = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        updateData();

        srl.setColorSchemeColors(Color.RED, Color.RED);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateData();
            }
        });

        //RecyclerView初始化数据
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_new_detail);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                String text = mAdapter.getItem(position).getTitle();
                String url = mAdapter.getItem(position).getUrl();
                ShareUtil.share(getActivity(), text + "/n" + url);
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                WebViewActivity.startUrl(getActivity(), mAdapter.getItem(position).getUrl());
            }

            @Override
            public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });



        return view;
    }

    private void updateData() {
        srl.setRefreshing(true);
        QClient.getInstance()
                .create(QNewsService.class, Constants.NEWS_DATA_URL)
                .getNewsData(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NewsDataBean>() {
                    @Override
                    public void accept(NewsDataBean newsDataBean) throws Exception {
                        mAdapter.setNewData(newsDataBean.getResult().getData());
                        srl.setRefreshing(false);
                    }
                }, new Consumer<Throwable>(){
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        srl.setRefreshing(false);
                    }
                });

    }
}
