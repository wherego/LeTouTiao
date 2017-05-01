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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.tfn.letoutiao.Constants;
import com.tfn.letoutiao.R;
import com.tfn.letoutiao.adapter.JokeAdapter;
import com.tfn.letoutiao.bean.JokeBean;
import com.tfn.letoutiao.net.QClient;
import com.tfn.letoutiao.net.QNewsService;
import com.tfn.letoutiao.utils.ShareUtil;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tf on 2017/4/30.
 */

public class JokeFragment extends Fragment{

    private SwipeRefreshLayout srl;
    private RecyclerView rv;

    private JokeAdapter mAdapter;

    private int mCurrentCounter;
    private int mTotalCounter = 5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_joke, container, false);

        mAdapter = new JokeAdapter();
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);

        rv = (RecyclerView) view.findViewById(R.id.frag_joke_rv);

        //设置下拉刷新
        srl = (SwipeRefreshLayout) view.findViewById(R.id.frag_joke_srl);

        srl.setColorSchemeColors(Color.RED, Color.RED);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateData();
            }
        });

        //RecyclerView初始化
        rv.setAdapter(mAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.addOnItemTouchListener(new OnItemLongClickListener() {
            @Override
            public void onSimpleItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                String content = mAdapter.getItem(position).getContent();
                ShareUtil.share(getActivity(), content);
            }
        });


        //RecyclerView底部上拉加载更多 监听器
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                rv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //加载完成
                        if(mCurrentCounter >= mTotalCounter){
                            mAdapter.loadMoreEnd();
                        }else {

                            if(mAdapter.getItem(0) == null){
                                return;
                            }

                            long unixtime = mAdapter.getItem(mAdapter.getItemCount() - 2)
                                    .getUnixtime();

                            QClient.getInstance()
                                    .create(QNewsService.class, Constants.JOKE_URL)
                                    .getAssignJokeData(unixtime, 1, 5, QNewsService.DESC)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeOn(Schedulers.io())
                                    .subscribe(new Consumer<JokeBean>() {
                                        @Override
                                        public void accept(JokeBean jokeBean) throws Exception {
                                            List<JokeBean.ResultBean.DataBean> data
                                                    = jokeBean.getResult().getData();
                                            mAdapter.addData(data);
                                            mCurrentCounter = mTotalCounter;
                                            mTotalCounter += 5;
                                            mAdapter.loadMoreComplete();
                                        }
                                    }, new Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) throws Exception {
                                            mAdapter.loadMoreFail();
                                        }
                                    });
                        }
                    }
                }, 1000);
            }
        });

        updateData();
        return view;
    }

    private void updateData() {
        srl.setRefreshing(true);

        QClient.getInstance()
                .create(QNewsService.class, Constants.JOKE_URL)
                .getCurrentJokeData(1,8)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<JokeBean>() {
                    @Override
                    public void accept(JokeBean jokeBean) throws Exception {
                        mAdapter.setNewData(jokeBean.getResult().getData());
                        srl.setRefreshing(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getActivity(), "获取数据失败!" + "访问次数上限", Toast.LENGTH_SHORT)
                                .show();
                        srl.setRefreshing(false);
                    }
                });
    }
}
