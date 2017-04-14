package com.linhlee.vidientu.activities;

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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linhlee.vidientu.R;
import com.linhlee.vidientu.adapters.ListMenuAdapter;
import com.linhlee.vidientu.adapters.ListNotiAdapter;
import com.linhlee.vidientu.adapters.TabsPagerAdapter;
import com.linhlee.vidientu.fragments.mainfragments.HomeFragment;
import com.linhlee.vidientu.fragments.mainfragments.PaymentFragment;
import com.linhlee.vidientu.fragments.mainfragments.TransferFragment;
import com.linhlee.vidientu.fragments.mainfragments.WalletFragment;
import com.linhlee.vidientu.models.MenuObject;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private DrawerLayout drawer;
    private RelativeLayout accountLayout;
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

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        accountLayout = (RelativeLayout) findViewById(R.id.account_layout);
        loginButton = (RelativeLayout) findViewById(R.id.login_button);
        registerButton = (RelativeLayout) findViewById(R.id.register_button);
        listMenuView = (ListView) findViewById(R.id.list_menu);
        listNotiView = (ListView) findViewById(R.id.list_noti);

        titleText = (TextView) findViewById(R.id.title_text);
        menuButton = (ImageView) findViewById(R.id.menu_button);
        notiButton = (ImageView) findViewById(R.id.noti_button);
        tabs = (TabLayout) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        createMainLayout();
        createDrawerLayout();

        menuButton.setOnClickListener(this);
        notiButton.setOnClickListener(this);
        accountLayout.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
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
                        break;
                    case 1:
                        titleText.setText(getResources().getString(R.string.transfer));
                        break;
                    case 2:
                        titleText.setText(getResources().getString(R.string.payment));
                        break;
                    case 3:
                        titleText.setText(getResources().getString(R.string.wallet));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabs.setupWithViewPager(pager);

        TabLayout.Tab tab1 = tabs.getTabAt(0);
        tab1.setCustomView(createTabView(R.mipmap.ic_home));
        TabLayout.Tab tab2 = tabs.getTabAt(1);
        tab2.setCustomView(createTabView(R.mipmap.ic_transfer));
        TabLayout.Tab tab3 = tabs.getTabAt(2);
        tab3.setCustomView(createTabView(R.mipmap.ic_payment));
        TabLayout.Tab tab4 = tabs.getTabAt(3);
        tab4.setCustomView(createTabView(R.mipmap.ic_wallet));
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
        listMenu.add(new MenuObject(R.mipmap.ic_logout, getResources().getString(R.string.dang_xuat)));

        listMenuAdapter = new ListMenuAdapter(this, listMenu);
        listMenuView.setAdapter(listMenuAdapter);
        listMenuView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
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
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 10:
                        drawer.closeDrawer(GravityCompat.START);
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
                startActivity(PersonalActivity.class);

                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }
                break;
            case R.id.login_button:
                startActivity(LoginActivity.class);
                break;
            case R.id.register_button:
                startActivity(RegisterActivity.class);
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
}
