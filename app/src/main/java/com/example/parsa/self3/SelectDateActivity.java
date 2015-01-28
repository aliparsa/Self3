package com.example.parsa.self3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.parsa.self3.Adapter.ListViewObjectAdapter;
import com.example.parsa.self3.DataModel.DateItem;
import com.example.parsa.self3.Helper.DateHelper;
import com.example.parsa.self3.Helper.PersianCalendar;
import com.example.parsa.self3.R;

import java.util.List;

public class SelectDateActivity extends Activity {

    private Context context;
    ListViewObjectAdapter dateAdapter;
    ListView datelv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        context=this;

        // init
        datelv = (ListView) findViewById(R.id.dateListview);

        fillDateListView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.date_picker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                startActivity(intent);

            }
        });

    }
}
