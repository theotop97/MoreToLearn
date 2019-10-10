package com.wangtao.moretolearn.Function;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.wangtao.moretolearn.R;
import com.wangtao.moretolearn.navgationview.NavSettingActivity;
import com.wangtao.moretolearn.util.ActivityCollector;
import com.wangtao.moretolearn.util.BaseActivity;

import java.util.Objects;

public class FunctionMainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar tb_Toolbar;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, FunctionMainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_main);

        ActivityCollector.addActivity(this);
        initUI();
    }

    private void initUI() {
        tb_Toolbar = findViewById(R.id.tb_toolbar);
        swipeRefreshLayout = findViewById(R.id.srl_refresh);
        swipeRefreshLayout.setProgressViewEndTarget(true, 300);
        //开启一个线程模拟耗时刷新操作
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(FunctionMainActivity.this, "刷新成功!", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                }).start();

            }
        });
        setSupportActionBar(tb_Toolbar);
        //去掉ToolBar的Title
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        drawerLayout = findViewById(R.id.dl_DrawerLayout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
        }
        navigationView = findViewById(R.id.nv_NavigationView);
        navigationView.setNavigationItemSelectedListener(this);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.more_function:
                Toast.makeText(this, "111111", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    //NavigationView的监听
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_search:
                break;
            case R.id.nav_favorite:
                break;
            case R.id.nav_information:
                break;
            case R.id.nav_service:
                break;
            case R.id.nav_account:
                break;
            case R.id.nav_set:
                NavSettingActivity.actionStart(this);

                break;
            default:
                break;
        }
//        menuItem.setChecked(false);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
