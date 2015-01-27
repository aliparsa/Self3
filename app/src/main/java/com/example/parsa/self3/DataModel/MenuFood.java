package com.example.parsa.self3.DataModel;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.parsa.self3.Helper.AsyncLoadImage;
import com.example.parsa.self3.Helper.FontHelper;
import com.example.parsa.self3.Helper.SettingHelper;
import com.example.parsa.self3.Helper.StringHelper;
import com.example.parsa.self3.Interface.IListViewItem;
import com.example.parsa.self3.R;

import java.util.ArrayList;

/**
 * Created by parsa on 2015-01-21.
 */
public class MenuFood implements IListViewItem {

    private String mealCaption;
    private int mealId;
    private int planningId;
    private String planningCaption;
    private String foodCaption;
    private int foodId;
    private String restaurant;
    private double freePrice;
    private double SubsidiesPrice;
    private double PayedPrice;
    private boolean ShowReserveButton;
    private String image;
    private int maxReserveCount;
    private int count;
    private String Date;

    public MenuFood(String mealCaption, int mealId, int planningId, String planningCaption, String foodCaption, int foodId, String restaurant, double freePrice, double subsidiesPrice, double payedPrice, boolean showReserveButton, String image, int maxReserveCount, int count, String date) {
        this.mealCaption = mealCaption;
        this.mealId = mealId;
        this.planningId = planningId;
        this.planningCaption = planningCaption;
        this.foodCaption = foodCaption;
        this.foodId = foodId;
        this.restaurant = restaurant;
        this.freePrice = freePrice;
        SubsidiesPrice = subsidiesPrice;
        PayedPrice = payedPrice;
        ShowReserveButton = showReserveButton;
        this.image = image;
        this.maxReserveCount = maxReserveCount;
        this.count = count;
        this.Date = date;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPlanningId() {
        return planningId;
    }

    public void setPlanningId(int planningId) {
        this.planningId = planningId;
    }

    public String getPlanningCaption() {
        return planningCaption;
    }

    public void setPlanningCaption(String planningCaption) {
        this.planningCaption = planningCaption;
    }

    public String getFoodCaption() {
        return foodCaption;
    }

    public void setFoodCaption(String foodCaption) {
        this.foodCaption = foodCaption;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public double getFreePrice() {
        return freePrice;
    }

    public void setFreePrice(double freePrice) {
        this.freePrice = freePrice;
    }

    public double getSubsidiesPrice() {
        return SubsidiesPrice;
    }

    public void setSubsidiesPrice(double subsidiesPrice) {
        SubsidiesPrice = subsidiesPrice;
    }

    public double getPayedPrice() {
        return PayedPrice;
    }

    public void setPayedPrice(double payedPrice) {
        PayedPrice = payedPrice;
    }

    public boolean isShowReserveButton() {
        return ShowReserveButton;
    }

    public void setShowReserveButton(boolean showReserveButton) {
        ShowReserveButton = showReserveButton;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getMaxReserveCount() {
        return maxReserveCount;
    }

    public void setMaxReserveCount(int maxReserveCount) {
        this.maxReserveCount = maxReserveCount;
    }

    public String getMealCaption() {
        return mealCaption;
    }

    public void setMealCaption(String mealCaption) {
        this.mealCaption = mealCaption;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public double calculatePrice(){
        return getPayedPrice();
    }

    @Override
    public boolean equals(Object o) {

        if(o instanceof MenuFood)
            return (this.getFoodId() == ((MenuFood) o).getFoodId() &&
                    this.getPlanningId() == ((MenuFood) o).getPlanningId() &&
                    this.getDate().equals(((MenuFood) o).getDate()));
        else
            return false;
    }

    public View getView(Context context, View oldView) {
        if (oldView == null || !(oldView.getTag() instanceof MenuFood)) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            oldView = inflater.inflate(R.layout.item_menufood, null);
            final Holder holder = new Holder();
            oldView.setTag(holder);
            getItem(context, holder, oldView);
            return oldView;
        } else {
            Holder holder = (Holder) oldView.getTag();
            holder.image.setImageBitmap(null);
            getItem(context, holder, oldView);
            return oldView;      }
    }

    @Override
    public void setSelected(boolean flag) {

    }

    private void getItem(final Context context, final Holder holder, View view) {
        holder.menufood = this;

        //if (holder.mealcaption == null)
       //     holder.mealcaption = (TextView) view.findViewById(R.id.mealcaption);

        if (holder.planningcaption == null)
            holder.planningcaption = (TextView) view.findViewById(R.id.planningcaption);

        if (holder.foodcaption == null)
            holder.foodcaption = (TextView) view.findViewById(R.id.foodcaption);

        if (holder.restaurant == null)
            holder.restaurant = (TextView) view.findViewById(R.id.restaurant);

        if (holder.freeprice == null)
            holder.freeprice = (TextView) view.findViewById(R.id.freeprice);

        if (holder.subsidiesprice == null)
            holder.subsidiesprice = (TextView) view.findViewById(R.id.subsidiesprice);

//        if (holder.payedprice == null)
//            holder.payedprice = (TextView) view.findViewById(R.id.payedprice);

        if (holder.image == null)
            holder.image = (ImageView) view.findViewById(R.id.image);


        //holder.mealcaption.setText(this.getMealCaption());
        holder.planningcaption.setText(this.getPlanningCaption());
        holder.foodcaption.setText(this.getFoodCaption() + " (" + this.getMealCaption() + ")");
        holder.restaurant.setText(this.getRestaurant());
        holder.freeprice.setText(StringHelper.commaSeparator(this.getFreePrice() + ""));
        holder.subsidiesprice.setText(StringHelper.commaSeparator(this.getSubsidiesPrice()+""));

        if (isShowReserveButton()==false){
            holder.foodcaption.setBackgroundResource(R.drawable.food_not_available);
        }else{
            holder.foodcaption.setBackgroundResource(0);
        }

       // FontHelper.SetFontNormal(context, FontHelper.Fonts.MAIN_FONT, holder.mealcaption);
        FontHelper.SetFontNormal(context, FontHelper.Fonts.MAIN_FONT, holder.planningcaption);
        FontHelper.SetFontNormal(context, FontHelper.Fonts.MAIN_FONT, holder.foodcaption);
        FontHelper.SetFontNormal(context, FontHelper.Fonts.MAIN_FONT, holder.restaurant);
        FontHelper.SetFontNormal(context, FontHelper.Fonts.MAIN_FONT, holder.freeprice);
        FontHelper.SetFontNormal(context, FontHelper.Fonts.MAIN_FONT, holder.subsidiesprice);
//        holder.payedprice.setText(this.getPayedPrice()+"");
        //holder.image.setText(this.getImage());

        if(getImage() != null && !getImage().equals("null") && !getImage().equals("")){

            AsyncLoadImage loader = new AsyncLoadImage(context, new SettingHelper(context).getOption("serverAddress")+ "/" +  getImage(), new AsyncLoadImage.ProgressCallBack<Bitmap>() {
                @Override
                public void onSuccess(Bitmap result, String imageUrl) {

                    if((new SettingHelper(context).getOption("serverAddress")+ "/" + getImage()).equals(imageUrl))
                        holder.image.setImageBitmap(result);
                    else
                        holder.image.setImageBitmap(null);
                }

                @Override
                public void onError(String errorMessage) {
                    holder.image.setImageResource(R.drawable.ic_steak);
                }

                @Override
                public void onProgress(int done, int total, Bitmap result) {

                }
            });


            loader.execute();
        }else{

            holder.image.setImageResource(R.drawable.ic_steak);
        }
    }


    public class Holder {
        //TextView mealcaption;
        TextView planningcaption;
        TextView foodcaption;
        TextView restaurant;
        TextView freeprice;
        TextView subsidiesprice;
        TextView payedprice;
        ImageView image;

        public MenuFood menufood;
    }


    public static String getJsonFromArrayList(ArrayList<MenuFood> menuFoods){
        String json="[";

        for(int i =0;i<menuFoods.size();i++){
            MenuFood menuFood = menuFoods.get(i);
            json+="{";
            json+="\"PlanningId\":"+menuFood.getPlanningId()+ ",";
            json+="\"Date\":\""+menuFood.getDate()+ "\",";
            json+="\"FoodId\":"+menuFood.getFoodId();
            json+="}";
            if (i!=menuFoods.size()-1){
                json+=",";
            }
        }
        json+="]";
        return json;
    }
}
