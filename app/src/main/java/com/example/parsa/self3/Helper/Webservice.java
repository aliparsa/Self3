package com.example.parsa.self3.Helper;

import android.content.Context;


import com.example.parsa.self3.DataModel.AddReserveResponse;
import com.example.parsa.self3.DataModel.Food;
import com.example.parsa.self3.DataModel.MenuFood;
import com.example.parsa.self3.DataModel.Personnel;
import com.example.parsa.self3.DataModel.Reserve;
import com.example.parsa.self3.DataModel.ReserveHistory;
import com.example.parsa.self3.Interface.CallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by aliparsa on 8/9/2014.
 */


public class Webservice {

    private static final int RESULT_OK =100;
    private static final int RESULT_ERROR =101;


    //-login-----------------------------------------------------
    public static void Login(Context context,final String username, final String password, final CallBack<Personnel> callback) {

        try {
            SettingHelper setting = new SettingHelper(context);
            String SERVER_ADDRESS = setting.getOption("serverAddress");

            final String NAMESPACE = SERVER_ADDRESS+"/Areas/Buffet/Service/";
            final String METHOD_NAME = "Self3_Login";
            final String URL = SERVER_ADDRESS+"/areas/buffet/service/webserviceAndroid.asmx?op=Self3_Login";
            final String SOAP_ACTION =SERVER_ADDRESS+ "/Areas/Buffet/Service/Self3_Login";

            SoapHelper soapHelper = new SoapHelper(context,NAMESPACE, METHOD_NAME, URL, SOAP_ACTION);

            ArrayList<String> names = new ArrayList<String>();
            ArrayList<String> values = new ArrayList<String>();

            names.add("username");
            values.add(username);

            names.add("password");
            values.add(password);

            soapHelper.SendRequestToServer(names,values, new CallBack<JSONObject>() {
                @Override
                public void onSuccess(JSONObject result) {
                    try {

                        int resultCode  = result.getInt("ResultCode");

                        if(resultCode != RESULT_OK){

                            result = new JSONObject("");
                        }

                        switch (resultCode) {
                            case RESULT_OK: {

                                String uid = result.getString("uid");
                                int id = result.getInt("PersonelId");
                                String firstName = result.getString("FName");
                                String lastName = result.getString("LName");
                                double finalCredit = result.getDouble("finalcredit");
                                String code = result.getString("Code");
                                callback.onSuccess(new Personnel(uid,id,firstName,lastName,code,finalCredit));

                                break;
                            }
                            case RESULT_ERROR: {
                                callback.onError(result.getString("ErrorMessage"));
                                break;
                            }
                            default: {
                                callback.onError("server response is not valid ");
                                break;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    callback.onError(errorMessage);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //-----------------------------------------------------------------------------
    public static void GetMenuFoods(Context context,final String date, final String uid, final CallBack<ArrayList<MenuFood>> callback) {

        try {
            SettingHelper setting = new SettingHelper(context);
            String SERVER_ADDRESS = setting.getOption("serverAddress");
            if (SERVER_ADDRESS==null)
                SERVER_ADDRESS="http://192.168.0.11:6061";

            final String NAMESPACE = SERVER_ADDRESS+"/Areas/Buffet/Service/";
            final String METHOD_NAME = "Self3_GetMenuFoods";
            final String URL = SERVER_ADDRESS+"/areas/buffet/service/webserviceAndroid.asmx?op=Self3_GetMenuFoods";
            final String SOAP_ACTION =SERVER_ADDRESS+ "/Areas/Buffet/Service/Self3_GetMenuFoods";

            SoapHelper soapHelper = new SoapHelper(context,NAMESPACE, METHOD_NAME, URL, SOAP_ACTION);

            ArrayList<String> names = new ArrayList<String>();
            ArrayList<String> values = new ArrayList<String>();

            names.add("date");
            values.add(date);

            names.add("uid");
            values.add(uid);


            soapHelper.SendRequestToServer(names,values, new CallBack<JSONObject>() {
                @Override
                public void onSuccess(JSONObject res) {
                    try {

                        int resultCode = resultCode = res.getInt("ResultCode");


                        if(resultCode != RESULT_OK){

                            res = new JSONObject("");
                        }


                        switch (resultCode) {
                            case RESULT_OK: {
                               /* String token = result.getString("token");
                                String name = result.getString("name");
                                int resturantId = result.getInt("restaurantId");
                                String resturantName = result.getString("restaurantName");
                                String deliverPersonel = result.getString("deliverPersonel");
                                String meal = result.getString("meal");
                                ArrayList<Meal> meals= Meal.getArrayFromJson(meal);
                                callback.onSuccess(new LoginInfo(token, name, resturantId, resturantName, deliverPersonel,meals));
                                */

                                ArrayList<MenuFood> menufoods = new ArrayList<MenuFood>();

                                JSONArray result = res.getJSONArray("foodTemplateList");

                                for (int i = 0;i<result.length();i++) {

                                    JSONObject object = result.getJSONObject(i);
                                    String mealCaption = object.getString("MealCaption");

                                    int mealId = object.getInt("MealId");
                                    int planningId = object.getInt("PlanningId");
                                    String planningCaption = object.getString("PlanningCaption");
                                    String foodCaption = object.getString("FoodCaption");
                                    int foodId = object.getInt("FoodId");
                                    String restaurant = object.getString("Restaurant");
                                    double freePrice = object.getDouble("FreePrice");
                                    double subsidiesPrice = object.getDouble("SubsidiesPrice");
                                    double payedPrice = object.getDouble("PayedPrice");
                                    boolean showReserveButton = object.getBoolean("ShowReserveButton");
                                    String image = object.getString("Image");
                                    int maxReserveCount = object.getInt("MaxReserveCount");
                                    String date = object.getString("Date");

                                    MenuFood menufood = new MenuFood(mealCaption, mealId, planningId, planningCaption, foodCaption, foodId, restaurant, freePrice, subsidiesPrice, payedPrice, showReserveButton, image, maxReserveCount, 0, date);

                                    menufoods.add(menufood);
                                }
                                callback.onSuccess(menufoods);

                                break;
                            }
                            case RESULT_ERROR: {
                                callback.onError(res.getString("ErrorMessage"));
                                break;
                            }
                            default: {
                                callback.onError("server response is not valid ");
                                break;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    callback.onError(errorMessage);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    //-----------------------------------------------------------------------------
    public static void GetReserves(Context context,final String date, final String uid, final CallBack<ArrayList<Reserve>> callback) {

        try {
            SettingHelper setting = new SettingHelper(context);
            String SERVER_ADDRESS = setting.getOption("serverAddress");
            if (SERVER_ADDRESS==null)
                SERVER_ADDRESS="http://192.168.0.11:6061";

            final String NAMESPACE = SERVER_ADDRESS+"/Areas/Buffet/Service/";
            final String METHOD_NAME = "Self3_GetReserves";
            final String URL = SERVER_ADDRESS+"/areas/buffet/service/webserviceAndroid.asmx?op=Self3_GetReserves";
            final String SOAP_ACTION =SERVER_ADDRESS+ "/Areas/Buffet/Service/Self3_GetReserves";

            SoapHelper soapHelper = new SoapHelper(context,NAMESPACE, METHOD_NAME, URL, SOAP_ACTION);

            ArrayList<String> names = new ArrayList<String>();
            ArrayList<String> values = new ArrayList<String>();

            names.add("date");
            values.add(date);

            names.add("uid");
            values.add(uid);

            soapHelper.SendRequestToServer(names,values, new CallBack<JSONObject>() {
                @Override
                public void onSuccess(JSONObject res) {
                    try {

                        int resultCode = resultCode = res.getInt("ResultCode");



                        switch (resultCode) {
                            case RESULT_OK: {

                                ArrayList<Reserve> reserves = new ArrayList<Reserve>();

                                JSONArray result = res.getJSONArray("Reserves");

                                for (int i = 0;i<result.length();i++) {

                                    JSONObject object = result.getJSONObject(i);


                                    boolean       showPoll=object.getBoolean("ShowPoll");
                                    String        planningCaption = object.getString("PlanningCaption");
                                    String        restaurant= object.getString("Restaurant");
                                    JSONArray       jsonFood=object.getJSONArray("FoodList");
                                    boolean       showCancel=object.getBoolean("ShowCancel");
                                    String        meal= object.getString("Meal");
                                    int        id=object.getInt("Id");
                                    boolean       showPrint=object.getBoolean("ShowPrint");
                                    boolean      delivered=object.getBoolean("Delivered");


                                    ArrayList<Food> foods = new ArrayList<Food>();

                                    for (int j = 0; j < jsonFood.length(); j++) {

                                        JSONObject obj = jsonFood.getJSONObject(j);

                                        foods.add(new Food(obj.getString("Caption"), obj.getInt("Count")));
                                    }


                                    Reserve reserve = new Reserve(id,planningCaption,meal,restaurant,foods,delivered,showCancel,showPrint,showPoll);

                                    reserves.add(reserve);
                                }
                                callback.onSuccess(reserves);
                                break;
                            }
                            case RESULT_ERROR: {
                                callback.onError("نام و یا کلمه عبور اشتباه است");
                                break;
                            }
                            default: {
                                callback.onError("server response is not valid ");
                                break;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    callback.onError(errorMessage);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//----------------------------------------------------------------------------------
public static void CancelReserve(Context context,String reserveId, final CallBack<String> callback) {

    try {
        SettingHelper setting = new SettingHelper(context);
        String SERVER_ADDRESS = setting.getOption("serverAddress");
        if (SERVER_ADDRESS == null)
            SERVER_ADDRESS = "http://192.168.0.11:6061";

        final String NAMESPACE = SERVER_ADDRESS + "/Areas/Buffet/Service/";
        final String METHOD_NAME = "CancelReserves";
        final String URL = SERVER_ADDRESS + "/areas/buffet/service/webserviceAndroid.asmx?op=CancelReserves";
        final String SOAP_ACTION = SERVER_ADDRESS + "/Areas/Buffet/Service/CancelReserves";

        SoapHelper soapHelper = new SoapHelper(context, NAMESPACE, METHOD_NAME, URL, SOAP_ACTION);

        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> values = new ArrayList<String>();

        names.add("reserves");
        values.add(reserveId);


        soapHelper.SendRequestToServer(names, values, new CallBack<JSONObject>() {
            @Override
            public void onSuccess(JSONObject res) {
                try {

                    int resultCode = res.getInt("ResultCode");

                    switch (resultCode) {
                        case RESULT_OK: {
                            callback.onSuccess(res.getDouble("FinalCredit")+"");
                            break;
                        }
                        case RESULT_ERROR: {
                            callback.onError("نام و یا کلمه عبور اشتباه است");
                            break;
                        }
                        default: {
                            callback.onError("server response is not valid ");
                            break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });


    } catch (Exception e) {
        e.printStackTrace();
    }
}
    //-------------------------------------------------------------------------------
    public static void AddReserve(Context context,String reserveJson,String cardNo , final CallBack<AddReserveResponse> callback) {

        try {
            SettingHelper setting = new SettingHelper(context);
            String SERVER_ADDRESS = setting.getOption("serverAddress");
            if (SERVER_ADDRESS==null)
                SERVER_ADDRESS="http://192.168.0.11:6061";

            final String NAMESPACE = SERVER_ADDRESS+"/Areas/Buffet/Service/";
            final String METHOD_NAME = "AddReserve";
            final String URL = SERVER_ADDRESS+"/areas/buffet/service/webserviceAndroid.asmx?op=AddReserve";
            final String SOAP_ACTION =SERVER_ADDRESS+ "/Areas/Buffet/Service/AddReserve";

            SoapHelper soapHelper = new SoapHelper(context,NAMESPACE, METHOD_NAME, URL, SOAP_ACTION);

            ArrayList<String> names = new ArrayList<String>();
            ArrayList<String> values = new ArrayList<String>();

            names.add("basket");
            values.add(reserveJson);

            names.add("cardNo");
            values.add(cardNo);



            soapHelper.SendRequestToServer(names,values, new CallBack<JSONObject>() {
                @Override
                public void onSuccess(JSONObject res) {
                    try {

                        int resultCode = res.getInt("ResultCode");

                        switch (resultCode) {
                            case RESULT_OK: {
                                AddReserveResponse response = new AddReserveResponse();
                                response.setFinalCredit(res.getDouble("FinalCredit")+"");
                                response.setReserveIds(res.getString("ReserveIds")+"");
                                callback.onSuccess(response);
                                break;
                            }
                            case RESULT_ERROR: {
                                callback.onError("نام و یا کلمه عبور اشتباه است");
                                break;
                            }
                            default: {
                                callback.onError("server response is not valid ");
                                break;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    callback.onError(errorMessage);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
}

    //------------------------------------------------------------------------------------------
    public static void printRequest(Context context,String reserveIdes , final CallBack<String> callback) {

        try {
            SettingHelper setting = new SettingHelper(context);
            String SERVER_ADDRESS = setting.getOption("serverAddress");
            if (SERVER_ADDRESS==null)
                SERVER_ADDRESS="http://192.168.0.11:6061";

            final String NAMESPACE = SERVER_ADDRESS+"/Areas/Buffet/Service/";
            final String METHOD_NAME = "Print";
            final String URL = SERVER_ADDRESS+"/areas/buffet/service/webserviceAndroid.asmx?op=Print";
            final String SOAP_ACTION =SERVER_ADDRESS+ "/Areas/Buffet/Service/Print";

            SoapHelper soapHelper = new SoapHelper(context,NAMESPACE, METHOD_NAME, URL, SOAP_ACTION);

            ArrayList<String> names = new ArrayList<String>();
            ArrayList<String> values = new ArrayList<String>();

            names.add("data");
            values.add(reserveIdes);


            soapHelper.SendRequestToServer(names,values, new CallBack<JSONObject>() {
                @Override
                public void onSuccess(JSONObject res) {
                    try {

                        int resultCode = res.getInt("ResultCode");

                        switch (resultCode) {
                            case RESULT_OK: {
                                callback.onSuccess(null);
                                break;
                            }
                            case RESULT_ERROR: {
                                callback.onError("نام و یا کلمه عبور اشتباه است");
                                break;
                            }
                            default: {
                                callback.onError("server response is not valid ");
                                break;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    callback.onError(errorMessage);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//-----------------------------------------------------------------------------------------------------
public static void GetHistory(Context context,final String date, final String uid, final CallBack<ArrayList<ReserveHistory>> callback) {

    try {
        SettingHelper setting = new SettingHelper(context);
        String SERVER_ADDRESS = setting.getOption("serverAddress");
        if (SERVER_ADDRESS==null)
            SERVER_ADDRESS="http://192.168.0.11:6061";

        final String NAMESPACE = SERVER_ADDRESS+"/Areas/Buffet/Service/";
        final String METHOD_NAME = "Self3_GetHistory";
        final String URL = SERVER_ADDRESS+"/areas/buffet/service/webserviceAndroid.asmx?op=Self3_GetHistory";
        final String SOAP_ACTION =SERVER_ADDRESS+ "/Areas/Buffet/Service/Self3_GetHistory";

        SoapHelper soapHelper = new SoapHelper(context,NAMESPACE, METHOD_NAME, URL, SOAP_ACTION);

        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> values = new ArrayList<String>();


        names.add("uid");
        values.add(uid);

        names.add("strStartDate");
        values.add(date);


        soapHelper.SendRequestToServer(names,values, new CallBack<JSONObject>() {
            @Override
            public void onSuccess(JSONObject res) {
                try {

                    int resultCode = resultCode = res.getInt("ResultCode");

                    switch (resultCode) {
                        case RESULT_OK: {

                            ArrayList<ReserveHistory> reserveHistories = new ArrayList<ReserveHistory>();

                            JSONArray result = res.getJSONArray("Reserves");

                            for (int i = 0;i<result.length();i++) {

                                JSONObject object = result.getJSONObject(i);


                                String restaurant= object.getString("Restaurant");
                                String planning= object.getString("Planning");
                                String meal= object.getString("Meal");
                                String date= object.getString("Date");
                                String paymentType= object.getString("PaymentType");
                                String foods= object.getString("Foods");
                                String deliveryStatus= object.getString("DeliveryStatus");


                                ReserveHistory reserveHistory = new ReserveHistory(i+1,restaurant,planning,meal,date,paymentType,foods,deliveryStatus);

                                reserveHistories.add(reserveHistory);
                            }
                            callback.onSuccess(reserveHistories);
                            break;
                        }
                        case RESULT_ERROR: {
                            callback.onError("نام و یا کلمه عبور اشتباه است");
                            break;
                        }
                        default: {
                            callback.onError("server response is not valid ");
                            break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });


    } catch (Exception e) {
        e.printStackTrace();
    }
}
//----------------------------------------------------------------------------------
}