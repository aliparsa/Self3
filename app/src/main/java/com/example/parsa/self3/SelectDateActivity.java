package com.example.parsa.self3;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.parsa.self3.DataModel.DateItem;
import com.example.parsa.self3.DataModel.Personnel;
import com.example.parsa.self3.Helper.DateHelper;
import com.example.parsa.self3.Helper.FontHelper;
import com.example.parsa.self3.Helper.PersianCalendar;
import com.example.parsa.self3.R;

import java.util.List;

public class SelectDateActivity extends ActionBarActivity {

    private Context context;
    ListViewObjectAdapter dateAdapter;
    ListView datelv;
    Personnel personnel;
    private ImageView user;
    private ImageView history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        context=this;

        personnel = (Personnel) getIntent().getSerializableExtra("personnel");



        // init
        datelv = (ListView) findViewById(R.id.dateListview);

        Toast.makeText(context,personnel.getName()+" " + personnel.getFamily(),Toast.LENGTH_SHORT).show();

        fillDateListView();
    }



    private void fillDateListView() {

        List<DateItem> dates = DateHelper.getDatesBeforeAndAfter(new PersianCalendar(), 10);
        dateAdapter = new ListViewObjectAdapter(context,dates);
        datelv.setAdapter(dateAdapter);

        datelv.setSelection(9);

        datelv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//                lastSelectedDayIndex = i;
                DateItem item = ((DateItem.Holder) view.getTag()).getDateItem();
                Intent intent = new Intent(context,MainActivity.class);

                intent.putExtra("dateItem",item);
                intent.putExtra("personnel",personnel);
                
                startActivity(intent);

            }
        });

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


        TextView title = (TextView) customActionBar.findViewById(R.id.ac_title);
        FontHelper.SetFontBold(this, FontHelper.Fonts.MAIN_FONT, title);

        title.setText("مدیریت سریع");
        //ImageView back = (ImageView) customActionBar.findViewById(R.id.ac_back);
        LinearLayout back = (LinearLayout) customActionBar.findViewById(R.id.ac_back_layout);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        user = (ImageView) customActionBar.findViewById(R.id.ac_action1);
        history = (ImageView) customActionBar.findViewById(R.id.ac_action2);

        user.setImageResource(R.drawable.ic_user);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                new AlertDialog.Builder(context)
                        .setTitle("اطلاعات پرسنل")
                        //.setView(personnel.getView(context,null))
                        .setMessage(
                                "نام کاربر"+" : "+personnel.getName() + " "+personnel.getFamily()+"\n\n"+
                                "اعتبار فعلی"+" : "+personnel.getFinalCridit()
                        )
                        .setNegativeButton("تایید", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)

                        .show();



            }
        });

        history.setImageResource(R.drawable.ic_history);
        context = this;
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(context, ReserveHistoryActivity.class);
                intent.putExtra("personnel",personnel);
                startActivity(intent);

            }
        });

    }
}
