package com.example.parsa.self3;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.parsa.self3.DataModel.Personnel;
import com.example.parsa.self3.R;

public class PersonnelInfoActivity extends Activity {

    TextView personnel_name;
    TextView personnel_national_code;
    TextView personnel_balance;
    Personnel personnel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnel_info);


        personnel = (Personnel) getIntent().getSerializableExtra("personnel");

        // init
        personnel_name = (TextView) findViewById(R.id.txt_personnel_name);
        personnel_national_code = (TextView) findViewById(R.id.txt_personnel_national_code);
        personnel_balance = (TextView) findViewById(R.id.txt_personnel_balance);


        personnel_name.setText(personnel.getName()+ " "+ personnel.getFamily());
        personnel_national_code.setText(personnel.getNationalNo()+"");
        personnel_balance.setText(personnel.getFinalCridit()+"");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.personnel_info, menu);
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
}
