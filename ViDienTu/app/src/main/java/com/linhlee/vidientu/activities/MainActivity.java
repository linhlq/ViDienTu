package com.linhlee.vidientu.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.linhlee.vidientu.MyApplication;
import com.linhlee.vidientu.R;
import com.linhlee.vidientu.adapters.ListMenuAdapter;
import com.linhlee.vidientu.adapters.ListNotiAdapter;
import com.linhlee.vidientu.adapters.TabsPagerAdapter;
import com.linhlee.vidientu.fragments.mainfragments.HomeFragment;
import com.linhlee.vidientu.fragments.mainfragments.PaymentFragment;
import com.linhlee.vidientu.fragments.mainfragments.TransferFragment;
import com.linhlee.vidientu.fragments.mainfragments.WalletFragment;
import com.linhlee.vidientu.models.MenuObject;
import com.linhlee.vidientu.models.OtherRequest;
import com.linhlee.vidientu.models.User;
import com.linhlee.vidientu.retrofit.IRetrofitAPI;
import com.linhlee.vidientu.utils.Constant;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private MyApplication app;
    private Gson mGson;
    private Retrofit mRetrofit;
    private IRetrofitAPI mRetrofitAPI;
    private SharedPreferences sharedPreferences;
    private User user;
    private boolean isLogin;

    private DrawerLayout drawer;
    private RelativeLayout accountLayout;
    private TextView loginText;
    private LinearLayout infoLayout;
    private TextView balance;
    private TextView infoText;
    private LinearLayout footLayout;
    private RelativeLayout loginButton;
    private RelativeLayout registerButton;
    private ListView listMenuView;
    private ListMenuAdapter listMenuAdapter;
    private ListView listNotiView;
    private ListNotiAdapter listNotiAdapter;
    private ArrayList<MenuObject> listMenu;
    private ImageView menuButton;
    private ImageView notiButton;

    private TabLayout tabs;
    private ViewPager pager;
    private TabsPagerAdapter pagerAdapter;
    private ArrayList<Fragment> listFragment;
    private TextView titleText;
    private View shadowView;

    private BroadcastReceiver gotoTransferReceiver, loginSuccessReceiver;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        app = (MyApplication) getApplication();
        mGson = app.getGson();
        mRetrofit = app.getRetrofit();
        mRetrofitAPI = mRetrofit.create(IRetrofitAPI.class);
        sharedPreferences = app.getSharedPreferences();
        user = mGson.fromJson(sharedPreferences.getString(Constant.USER_INFO, ""), User.class);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        accountLayout = (RelativeLayout) findViewById(R.id.account_layout);
        loginText = (TextView) findViewById(R.id.login_text);
        infoLayout = (LinearLayout) findViewById(R.id.info_layout);
        balance = (TextView) findViewById(R.id.balance);
        infoText = (TextView) findViewById(R.id.info_text);
        footLayout = (LinearLayout) findViewById(R.id.foot_layout);
        loginButton = (RelativeLayout) findViewById(R.id.login_button);
        registerButton = (RelativeLayout) findViewById(R.id.register_button);
        listMenuView = (ListView) findViewById(R.id.list_menu);
        listNotiView = (ListView) findViewById(R.id.list_noti);

        titleText = (TextView) findViewById(R.id.title_text);
        shadowView = findViewById(R.id.shadow_view);
        menuButton = (ImageView) findViewById(R.id.menu_button);
        notiButton = (ImageView) findViewById(R.id.noti_button);
        tabs = (TabLayout) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        isLogin = sharedPreferences.getBoolean(Constant.IS_LOGIN, false);
        if (isLogin) {
            footLayout.setVisibility(View.GONE);
            loginText.setVisibility(View.GONE);
            infoLayout.setVisibility(View.VISIBLE);

            balance.setText(user.getBalance() + "");
            infoText.setText(user.getFullname() + " - " + user.getMobile());
        } else {
            footLayout.setVisibility(View.VISIBLE);
            loginText.setVisibility(View.VISIBLE);
            infoLayout.setVisibility(View.GONE);
        }

        createMainLayout();
        createDrawerLayout();

        menuButton.setOnClickListener(this);
        notiButton.setOnClickListener(this);
        accountLayout.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

        gotoTransferReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                pager.setCurrentItem(1);
            }
        };
        registerReceiver(gotoTransferReceiver, new IntentFilter(Constant.GOTO_TRANSFER));

        loginSuccessReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Intent i = getIntent();
                finish();
                startActivity(i);
            }
        };
        registerReceiver(loginSuccessReceiver, new IntentFilter(Constant.LOGIN_SUCCESS));
    }

    public void createMainLayout() {
        listFragment = new ArrayList<>();
        listFragment.add(HomeFragment.newInstance());
        listFragment.add(TransferFragment.newInstance());
        listFragment.add(PaymentFragment.newInstance());
        listFragment.add(WalletFragment.newInstance());

        pagerAdapter = new TabsPagerAdapter(getSupportFragmentManager(), listFragment);
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(4);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        titleText.setText(getResources().getString(R.string.home_page));
                        shadowView.setVisibility(View.GONE);
                        break;
                    case 1:
                        titleText.setText(getResources().getString(R.string.transfer));
                        shadowView.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        titleText.setText(getResources().getString(R.string.payment));
                        shadowView.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        titleText.setText(getResources().getString(R.string.wallet));
                        shadowView.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabs.setupWithViewPager(pager);

        TabLayout.Tab tab1 = tabs.getTabAt(0);
        tab1.setCustomView(createTabView(R.drawable.img_home));
        TabLayout.Tab tab2 = tabs.getTabAt(1);
        tab2.setCustomView(createTabView(R.drawable.img_transfer));
        TabLayout.Tab tab3 = tabs.getTabAt(2);
        tab3.setCustomView(createTabView(R.drawable.img_payment));
        TabLayout.Tab tab4 = tabs.getTabAt(3);
        tab4.setCustomView(createTabView(R.drawable.img_wallet));
    }

    public void createDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // Create Navigation Drawer layout left
        listMenu = new ArrayList<>();
        listMenu.add(new MenuObject(R.mipmap.ic_home, getResources().getString(R.string.home_page)));
        listMenu.add(new MenuObject(R.mipmap.ic_chuyen_tien, getResources().getString(R.string.chuyen_tien)));
        listMenu.add(new MenuObject(R.mipmap.ic_nap_dt, getResources().getString(R.string.nap_dien_thoai)));
        listMenu.add(new MenuObject(R.mipmap.ic_mua_the_dt, getResources().getString(R.string.mua_the_dien_thoai)));
        listMenu.add(new MenuObject(R.mipmap.ic_doi_the_cao, getResources().getString(R.string.doi_the_cao)));
        listMenu.add(new MenuObject(R.mipmap.ic_mua_game, getResources().getString(R.string.mua_the_game)));
        listMenu.add(new MenuObject(R.mipmap.ic_nap_tien, getResources().getString(R.string.nap_tien)));
        listMenu.add(new MenuObject(R.mipmap.ic_rut_tien, getResources().getString(R.string.rut_tien)));
        listMenu.add(new MenuObject(R.mipmap.ic_hoa_don, getResources().getString(R.string.thanh_toan_hoa_don)));
        listMenu.add(new MenuObject(R.mipmap.ic_diem_thanh_toan, getResources().getString(R.string.diem_thanh_toan)));
        listMenu.add(new MenuObject(R.mipmap.ic_lien_he, getResources().getString(R.string.lien_he)));
        if (isLogin) {
            listMenu.add(new MenuObject(R.mipmap.ic_logout, getResources().getString(R.string.dang_xuat)));
        }

        listMenuAdapter = new ListMenuAdapter(this, listMenu);
        listMenuView.setAdapter(listMenuAdapter);
        listMenuView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        pager.setCurrentItem(0);
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        pager.setCurrentItem(1);
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        startActivity(PhoneRechargeActivity.class);
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        startActivity(BuyPhoneCardActivity.class);
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
                        startActivity(BuyGameCardActivity.class);
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 6:
                        startActivity(DepositActivity.class);
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 7:
                        startActivity(WithdrawActivity.class);
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 8:
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 9:
                        startActivity(TraGopActivity.class);
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 10:
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 11:
                        Call<OtherRequest> logout = mRetrofitAPI.logout(user.getToken());
                        logout.enqueue(new Callback<OtherRequest>() {
                            @Override
                            public void onResponse(Call<OtherRequest> call, Response<OtherRequest> response) {
                                int errorCode = response.body().getErrorCode();
                                String msg = response.body().getMsg();
                                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                                drawer.closeDrawer(GravityCompat.START);

                                sharedPreferences.edit().putBoolean(Constant.IS_LOGIN, false).apply();
                                sharedPreferences.edit().putString(Constant.USER_INFO, "").apply();

                                Intent i = getIntent();
                                finish();
                                startActivity(i);
                            }

                            @Override
                            public void onFailure(Call<OtherRequest> call, Throwable t) {
                                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        break;
                }
            }
        });

        // Create Navigation Drawer layout right
        listNotiAdapter = new ListNotiAdapter(this);
        listNotiView.setAdapter(listNotiAdapter);
    }

    private View createTabView(int imgRes) {
        View itemView = LayoutInflater.from(this).inflate(R.layout.tabs_main_layout, null);

        ImageView tabsIcon = (ImageView) itemView.findViewById(R.id.tabs_icon);
        tabsIcon.setImageResource(imgRes);

        return itemView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_button:
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.noti_button:
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else {
                    drawer.openDrawer(GravityCompat.END);
                }
                break;
            case R.id.account_layout:
                if (isLogin) {
                    startActivity(PersonalActivity.class);
                } else {
                    startActivity(LoginActivity.class);
                }

                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }
                break;
            case R.id.login_button:
                startActivity(LoginActivity.class);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.register_button:
                startActivity(RegisterActivity.class);
                drawer.closeDrawer(GravityCompat.START);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (gotoTransferReceiver != null) {
            unregisterReceiver(gotoTransferReceiver);
        }
    }
}
