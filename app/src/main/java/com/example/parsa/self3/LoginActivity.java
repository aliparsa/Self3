package com.example.parsa.self3;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.parsa.self3.DataModel.GlobalData;
import com.example.parsa.self3.DataModel.Personnel;
import com.example.parsa.self3.Helper.FontHelper;
import com.example.parsa.self3.Helper.SettingHelper;
import com.example.parsa.self3.Helper.ValidationMessage;
import com.example.parsa.self3.Helper.Webservice;
import com.example.parsa.self3.Interface.CallBack;

import java.util.Calendar;


public class LoginActivity extends Activity {


    private Context context;

    //login
    EditText txtUsername;
    EditText txtPassword;
    Button btnLogin;
    ProgressBar loaderBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);




        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        context=this;
        final String cardId = getIntent().getStringExtra("cardId");


        SettingHelper settingHelper = new SettingHelper(context);
        if (settingHelper.getOption("serverAddress")==null)
            settingHelper.setOption("serverAddress","http://192.168.0.11:6061");





        //login
        txtUsername = (EditText) findViewById(R.id.etxt_fragmentLogin_username);
        txtPassword = (EditText) findViewById(R.id.etxt_fragmentLogin_password);
        loaderBar = (ProgressBar) findViewById(R.id.pgb_fragmentLogin_loader);
        btnLogin = (Button) findViewById(R.id.btn_fragmentLogin_login);
        FontHelper.SetFont(context, FontHelper.Fonts.MAIN_FONT, btnLogin, Typeface.NORMAL);


        String localUid = settingHelper.getOption("uid");
        if (localUid!=null){

//            final ProgressDialog dialog = ProgressDialog.show(context, "دریافت اطلاعات",
//                    "کمی صبر کنید", true);
//            dialog.show();

            Webservice.GetPersonnelInfo(context,localUid,new CallBack<Personnel>() {
                @Override
                public void onSuccess(Personnel result) {
                 //   dialog.dismiss();

                    GlobalData.setPersonnel(result);
                    Intent intent = new Intent(context,SelectDateActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onError(String errorMessage) {
                 //   dialog.dismiss();
                    Toast.makeText(context,"ورود خودکار موفقیت آمیز نبود",Toast.LENGTH_SHORT).show();
                }
            });

        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            public ValidationMessage validationMessage;

            @Override
            public void onClick(View view) {

                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                if (LoginValidation(username, password, validationMessage))
                    loginClicked(username, password);
            }
        });


    }


    private void loginClicked(final String username, final String password) {

        btnLogin.setVisibility(View.GONE);
        loaderBar.setVisibility(View.VISIBLE);

        Webservice.Login(context, username, password, new CallBack<Personnel>() {
            @Override
            public void onSuccess(Personnel result) {

                SettingHelper settingHelper = new SettingHelper(context);
                settingHelper.setOption("username",username);
                settingHelper.setOption("password",password);
                settingHelper.setOption("uid",result.getUid());

                Intent intent = new Intent(context,SelectDateActivity.class);
                GlobalData.setPersonnel(result);
                startActivity(intent);
                finish();

                //TODO login here
            }

            @Override
            public void onError(String errorMessage) {
                btnLogin.setVisibility(View.VISIBLE);
                loaderBar.setVisibility(View.GONE);
                msgUser(errorMessage);
            }
        });

    }

    private boolean LoginValidation(String username, String password, ValidationMessage validationMessage) {

        return true;
    }

    private void msgUser(String errMessage) {
        Toast.makeText(context, errMessage, Toast.LENGTH_SHORT).show();
    }


}
