package com.tfn.letoutiao.ui.activity;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.tfn.letoutiao.ui.fragment.AboutFragment;
import com.tfn.letoutiao.ui.fragment.GifFragment;
import com.tfn.letoutiao.ui.fragment.JokeFragment;
import com.tfn.letoutiao.ui.fragment.NewsFragment;
import com.tfn.letoutiao.R;

public class MainActivity extends AppCompatActivity {

    long lastTime = 0;

    private BottomBar bottomBar;
    private NavigationView nv;
    private DrawerLayout drawerLayout;

    private Fragment currentFragment;
    private JokeFragment jokeFragment;
    private NewsFragment newsFragment;
    private GifFragment gifFragment;
    private AboutFragment aboutFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        nv = (NavigationView) findViewById(R.id.nav_view);

        //底部tab点击事件
        initTabBottom();

        //侧滑菜单点击事件
        initDrawerItem();

    }


    /**
     * 侧滑菜单点击事件
     */
    private void initDrawerItem() {
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                nv.setCheckedItem(item.getItemId());
                drawerLayout.closeDrawers();
                switch (item.getItemId()){
                    case R.id.nav_news:
                        bottomBar.selectTabAtPosition(0, true);
                        break;
                    case R.id.nav_joke:
                        bottomBar.selectTabAtPosition(1, true);
                        break;
                    case R.id.nav_pic:
                        bottomBar.selectTabAtPosition(2, true);
                        break;
                    case R.id.nav_other:
                        bottomBar.selectTabAtPosition(3, true);
                        break;
                }
                return false;
            }
        });

    }

    /**
     * 底部tab点击事件监听
     */
    private void initTabBottom() {
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId){
                    case R.id.tab_news:
                        nv.setCheckedItem(R.id.nav_news);
                        if(newsFragment == null){
                            newsFragment = new NewsFragment();
                        }
                        switchFragment(newsFragment);
                        closeDrawerLayout();
                        break;
                    case R.id.tab_joke:
                        nv.setCheckedItem(R.id.nav_joke);
                        if(jokeFragment == null){
                            jokeFragment = new JokeFragment();
                        }
                        switchFragment(jokeFragment);
                        closeDrawerLayout();
                        break;
                    case R.id.tab_pic:
                        if(gifFragment == null){
                            gifFragment = new GifFragment();
                        }
                        switchFragment(gifFragment);
                        closeDrawerLayout();
                        break;
                    case R.id.tab_about:
                        nv.setCheckedItem(R.id.nav_other);
                        if(aboutFragment == null){
                            aboutFragment = new AboutFragment();
                        }
                        switchFragment(aboutFragment);
                        closeDrawerLayout();
                        break;
                }
            }
        });

    }

    /**
     *切换显示Fragment
     * @param jokeFragment
     */
    private void switchFragment(Fragment targetFragment) {
        //如果当前显示的Fragment就是目标targetFragment，直接return
        if (currentFragment == targetFragment) return;

        //获得Fragment事务
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        //如果当前显示的Fragment不为空， 就隐藏当前的Fragment
        if(currentFragment != null){
            transaction.hide(currentFragment);
        }

        //如果要显示的Fragment已经存在，直接show,  不存在，就添加
        if(targetFragment.isAdded()){
            transaction.show(targetFragment);
        } else {
            transaction.add(R.id.fl_content, targetFragment, targetFragment.getClass().getName());
        }

        //提交事务
        transaction.commit();

        //将要显示的Fragment显示
        currentFragment = targetFragment;

    }

    /**
     * 关闭侧滑菜单
     */
    private void closeDrawerLayout() {
        if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
            drawerLayout.closeDrawers();
        }
    }


    /**
     * 点击两次返回键退出应用
     */
    @Override
    public void onBackPressed() {
        //判断侧滑菜单是否开启 开启，则先关闭
        if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
            drawerLayout.closeDrawers();
            return;
        }
        //判断当前fragment是不是newsFragment,是就退出，不是先回到newsFragment
        if(currentFragment != newsFragment){
            bottomBar.selectTabAtPosition(0);
            return;
        }

        //点击两次退出应用
        long curTime = System.currentTimeMillis();
        if(curTime - lastTime > 2000){
            Toast.makeText(MainActivity.this, "再点击一次退出应用", Toast.LENGTH_LONG)
                    .show();
            lastTime = curTime;
        } else {
            moveTaskToBack(true);
        }
    }
}
