package com.example.parsa.self3;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
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
import com.example.parsa.self3.DataModel.NoItem;
import com.example.parsa.self3.DataModel.Personnel;
import com.example.parsa.self3.DataModel.ReserveHistory;
import com.example.parsa.self3.DataModel.YearMonthItem;
import com.example.parsa.self3.Helper.DateHelper;
import com.example.parsa.self3.Helper.PersianCalendar;
import com.example.parsa.self3.Helper.Webservice;
import com.example.parsa.self3.Interface.CallBack;
import com.example.parsa.self3.R;

import java.util.ArrayList;
import java.util.List;

public class ReserveHistoryActivity extends Activity {
    private Context context;
    ListView yearListview;
    ListView mountListview;
    private LinearLayout monthll;
    ListView reserveHistotyListview;
    private ListViewObjectAdapter yearAdapter;
    private ImageView back;
    TextView txtSabegheReserve;

    Personnel personnel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_history);
        context= this;


        personnel= (Personnel) getIntent().getSerializableExtra("personnel");

        // init
        yearListview = (ListView) findViewById(R.id.sallistView);
        mountListview = (ListView) findViewById(R.id.mahListview);
        monthll = (LinearLayout) findViewById(R.id.monthll);
        reserveHistotyListview= (ListView) findViewById(R.id.HistoryListView);
        back = (ImageView) findViewById(R.id.back);
        txtSabegheReserve = (TextView) findViewById(R.id.txtSabegheReserve);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // lets start
        fillYearListVIew();

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

                final ProgressDialog progDialog = ProgressDialog.show(context, "تبادل داده با سرور", "کمی صبر کنید", true);
                progDialog.show();

                Webservice.GetHistory(context, persianCalendar.getGregorianDate(), personnel.getUid(), new CallBack<ArrayList<ReserveHistory>>() {
                    @Override
                    public void onSuccess(ArrayList<ReserveHistory> result) {
                        progDialog.dismiss();

                        txtSabegheReserve.setText(" سابقه رزرو " + persianCalendar.getPersianMonthNameStr() + " ماه سال " + persianCalendar.getIranianYear());

                        if (result.size() < 1) {
                            txtSabegheReserve.setText(" سابقه رزرو ");

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.reserve_history, menu);
        return true;
    }
}
