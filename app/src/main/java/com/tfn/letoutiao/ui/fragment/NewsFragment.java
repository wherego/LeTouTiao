package com.tfn.letoutiao.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.tfn.letoutiao.net.QClient;
import com.tfn.letoutiao.Constants;
import com.tfn.letoutiao.R;
import com.tfn.letoutiao.adapter.NewsDataAdapter;
import com.tfn.letoutiao.bean.NewsDataBean;
import com.tfn.letoutiao.net.QNewsService;
import com.tfn.letoutiao.ui.activity.MainActivity;
import com.tfn.letoutiao.utils.ShareUtil;
import com.tfn.letoutiao.ui.activity.WebViewActivity;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tf on 2017/4/29.
 */

public class NewsFragment extends Fragment {

    private String[] types;         //顶部 tab 英文内容数组
    private String[] typesCN;       //顶部 tab 中文内容数组

    private ViewPager pager;
    private MagicIndicator magicIndicator;

    private NewsViewPagerAdapter adapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);

        types = getResources().getStringArray(R.array.news_type_en);
        typesCN = getResources().getStringArray(R.array.news_type_cn);

        //viewpager初始化，设置适配器
        pager = (ViewPager) view.findViewById(R.id.main_viewpager);
        adapter = new NewsViewPagerAdapter(getActivity().getSupportFragmentManager());
        pager.setAdapter(adapter);

        //顶部加载器Indicator
        magicIndicator = (MagicIndicator) view .findViewById(R.id.magic_indicator);
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return typesCN == null ? 0:typesCN.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int i) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView
                        = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.BLACK);
                colorTransitionPagerTitleView.setSelectedColor(Color.RED);
                colorTransitionPagerTitleView.setText(typesCN[i]);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pager.setCurrentItem(i);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                return indicator;
            }
        });

        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, pager);

        return view;
    }


    private class NewsViewPagerAdapter extends FragmentStatePagerAdapter {

        public NewsViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //根据位置创建相应的fragment
            return new NewsBaseFragment(types[position]);
        }

        @Override
        public int getCount() {
            return types.length;
        }

    }
}
