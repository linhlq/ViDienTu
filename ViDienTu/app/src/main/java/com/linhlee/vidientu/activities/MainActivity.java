package com.linhlee.vidientu.activities;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.linhlee.vidientu.R;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private DrawerLayout drawer;
    private ListView listMenuView;
    private ImageView menuButton;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        listMenuView = (ListView) findViewById(R.id.list_menu);
        menuButton = (ImageView) findViewById(R.id.menu_button);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        createDrawerLayout();

        menuButton.setOnClickListener(this);
    }

    public void createDrawerLayout() {
        // Create Navigation Drawer layout
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_button:
                drawer.openDrawer(Gravity.LEFT);
                break;
        }
    }
}
