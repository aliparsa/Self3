package com.example.parsa.self3;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.parsa.self3.Helper.FontHelper;
import com.example.parsa.self3.R;

public class PersonnelInfoActivity extends ActionBarActivity {

    private TextView title;
    private TextView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnel_info);



        prepareActionBar();
    }

    private void prepareActionBar() {

        View customActionBar = getLayoutInflater().inflate(R.layout.actionbar_basket_view, null);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setCustomView(customActionBar);

        actionBar.setLogo(null); // forgot why this one but it helped
        actionBar.setIcon(null);

        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(false);

        View homeIcon = findViewById(android.R.id.home);

        if (homeIcon != null) {
            homeIcon.setVisibility(View.GONE);
        }
        if (homeIcon.getParent() != null) {
            ((View) homeIcon.getParent()).setVisibility(View.GONE);
        }


        title = (TextView) customActionBar.findViewById(R.id.ac_title);
        FontHelper.SetFontBold(this, FontHelper.Fonts.MAIN_FONT, title);

        title.setText("بازگشت");


        LinearLayout back = (LinearLayout) customActionBar.findViewById(R.id.ac_back_layout);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        logout = (TextView) customActionBar.findViewById(R.id.logout);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO logot user
            }
        });



    }


}
