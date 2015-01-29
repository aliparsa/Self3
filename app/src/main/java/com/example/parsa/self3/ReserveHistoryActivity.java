package com.example.parsa.self3;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.parsa.self3.Adapter.ListViewObjectAdapter;
import com.example.parsa.self3.DataModel.GlobalData;
import com.example.parsa.self3.DataModel.NoItem;
import com.example.parsa.self3.DataModel.Personnel;
import com.example.parsa.self3.DataModel.ReserveHistory;
import com.example.parsa.self3.DataModel.YearMonthItem;
import com.example.parsa.self3.Helper.DateHelper;
import com.example.parsa.self3.Helper.FontHelper;
import com.example.parsa.self3.Helper.PersianCalendar;
import com.example.parsa.self3.Helper.Webservice;
import com.example.parsa.self3.Interface.CallBack;
import com.example.parsa.self3.R;

import java.util.ArrayList;
import java.util.List;

public class ReserveHistoryActivity extends ActionBarActivity {
    private Context context;
    ListView yearListview;
    ListView mountListview;
    private LinearLayout monthll;
    ListView reserveHistotyListview;
    private ListViewObjectAdapter yearAdapter;
    private ImageView back;


    private TextView title;
    private ImageView reload;
    private PersianCalendar selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_history);
        context= this;



        // init
        yearListview = (ListView) findViewById(R.id.sallistView);
        mountListview = (ListView) findViewById(R.id.mahListview);
        monthll = (LinearLayout) findViewById(R.id.monthll);
        reserveHistotyListview= (ListView) findViewById(R.id.HistoryListView);


        // lets start
        fillYearListVIew();

        prepareActionBar();
    }

    private void prepareActionBar() {

        View customActionBar = getLayoutInflater().inflate(R.layout.actionbar_view, null);
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

        title.setText("تاریخچه رزرو");
        //ImageView back = (ImageView) customActionBar.findViewById(R.id.ac_back);
        LinearLayout back = (LinearLayout) customActionBar.findViewById(R.id.ac_back_layout);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        reload = (ImageView) customActionBar.findViewById(R.id.ac_action1);

        reload.setImageResource(R.drawable.ac_refresh);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            if (selectedDate!=null){


                final ProgressDialog progDialog = ProgressDialog.show(context, "تبادل داده با سرور", "کمی صبر کنید", true);
                progDialog.show();
                Webservice.GetHistory(context,true, selectedDate.getGregorianDate(), GlobalData.getPersonnel().getUid(), new CallBack<ArrayList<ReserveHistory>>() {
                    @Override
                    public void onSuccess(ArrayList<ReserveHistory> result) {
                        progDialog.dismiss();

                        title.setText(" سابقه رزرو " + selectedDate.getPersianMonthNameStr() + " ماه سال " + selectedDate.getIranianYear());

                        if (result.size() < 1) {
                            title.setText(" سابقه رزرو ");

                            ArrayList<NoItem> noItems = new ArrayList<NoItem>();
                            noItems.add(new NoItem());
                            ListViewObjectAdapter adapter = new ListViewObjectAdapter(context, noItems);
                            reserveHistotyListview.setAdapter(adapter);
                            //   Animation animation = AnimationUtils.loadAnimation(context, R.anim.view_not_valid);
                            //  reserveHistotyListview.startAnimation(animation);
                            return;
                        }


                        ListViewObjectAdapter adapter1 = new ListViewObjectAdapter(context, result);
                        reserveHistotyListview.setAdapter(adapter1);

                    }

                    @Override
                    public void onError(String errorMessage) {
                        progDialog.dismiss();
                    }
                });

            }

            }
        });


    }


    private void fillYearListVIew(){

        List<YearMonthItem> years = DateHelper.getYearsBefore(new PersianCalendar(), 5);
        yearAdapter = new ListViewObjectAdapter(context,years);
        yearListview.setAdapter(yearAdapter);

        yearListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PersianCalendar persianCalendar = ((YearMonthItem.Holder) view.getTag()).getYearmonthitem().getDate();
                fillMonthListVIew(persianCalendar);

                yearAdapter.setSelectedItem(i);
                yearAdapter.notifyDataSetChanged();
            }
        });

    }




    private void fillMonthListVIew(final PersianCalendar date){

        List<YearMonthItem> months = DateHelper.getMonthsOfYear(date);
        final ListViewObjectAdapter adapter = new ListViewObjectAdapter(context,months);
        mountListview.setAdapter(adapter);



        mountListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                monthll.setVisibility(View.GONE);
                final PersianCalendar persianCalendar = ((YearMonthItem.Holder) view.getTag()).getYearmonthitem().getDate();

                selectedDate=persianCalendar;
                final ProgressDialog progDialog = ProgressDialog.show(context, "تبادل داده با سرور", "کمی صبر کنید", true);
                progDialog.show();

                Webservice.GetHistory(context,false, persianCalendar.getGregorianDate(), GlobalData.getPersonnel().getUid(), new CallBack<ArrayList<ReserveHistory>>() {
                    @Override
                    public void onSuccess(ArrayList<ReserveHistory> result) {
                        progDialog.dismiss();

                        title.setText(" سابقه رزرو " + persianCalendar.getPersianMonthNameStr() + " ماه سال " + persianCalendar.getIranianYear());

                        if (result.size() < 1) {
                            title.setText(" سابقه رزرو ");

                            ArrayList<NoItem> noItems = new ArrayList<NoItem>();
                            noItems.add(new NoItem());
                            ListViewObjectAdapter adapter = new ListViewObjectAdapter(context, noItems);
                            reserveHistotyListview.setAdapter(adapter);
                         //   Animation animation = AnimationUtils.loadAnimation(context, R.anim.view_not_valid);
                         //  reserveHistotyListview.startAnimation(animation);
                            return;
                        }


                        ListViewObjectAdapter adapter1 = new ListViewObjectAdapter(context, result);
                        reserveHistotyListview.setAdapter(adapter1);

                    }

                    @Override
                    public void onError(String errorMessage) {
                        progDialog.dismiss();
                    }
                });

            }
        });

        monthll.setVisibility(View.VISIBLE);


    }



}
