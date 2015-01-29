package com.example.parsa.self3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parsa.self3.Adapter.ListViewObjectAdapter;
import com.example.parsa.self3.DataModel.Basket;
import com.example.parsa.self3.DataModel.MenuFood;
import com.example.parsa.self3.DataModel.Shopping;
import com.example.parsa.self3.Helper.FontHelper;
import com.example.parsa.self3.R;

import java.util.ArrayList;

public class BasketActivity extends ActionBarActivity {

    private ListView lvBasket;
    Context context;
    private LinearLayout payment;
    private TextView price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        context = this;

        prepareActionBar();

        lvBasket = (ListView) findViewById(R.id.basketLV);
        ListViewObjectAdapter adapter = new ListViewObjectAdapter(context, Shopping.getShoppingBasket());

        lvBasket.setAdapter(adapter);

        price.setText(Shopping.getBasketPrice() + "");

        lvBasket.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                MenuFood food = ((Basket.Holder) view.getTag()).basket.getMenuFood();

                Shopping.selectedFoods.remove(food);

                ListViewObjectAdapter adapter = new ListViewObjectAdapter(context, Shopping.getShoppingBasket());

                lvBasket.setAdapter(adapter);

                price.setText(Shopping.getBasketPrice() + "");
            }
        });


    }

    private void prepareActionBar() {

        View customActionBar = getLayoutInflater().inflate(R.layout.actionbar_basket_view, null);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS);
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


        TextView title = (TextView) customActionBar.findViewById(R.id.ac_title);
        FontHelper.SetFontBold(this, FontHelper.Fonts.MAIN_FONT, title);

        title.setText("منو اصلی");
        //ImageView back = (ImageView) customActionBar.findViewById(R.id.ac_back);
        LinearLayout back = (LinearLayout) customActionBar.findViewById(R.id.ac_back_layout);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        payment = (LinearLayout) customActionBar.findViewById(R.id.ac_action1);

        price = (TextView) customActionBar.findViewById(R.id.txt_price);

//        shopping = (ImageView) customActionBar.findViewById(R.id.ac_action2);
//
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO reserve basket
                ArrayList<MenuFood> menuFoods = Shopping.selectedFoods;

            }
        });
//
//        shopping.setImageResource(R.drawable.ic_shopping_cart);
//        shopping.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
//            }
//        });

    }




}
