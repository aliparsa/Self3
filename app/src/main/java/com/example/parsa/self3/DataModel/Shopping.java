package com.example.parsa.self3.DataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashkan on 1/28/2015.
 */
public class Shopping {

    private Shopping(){
        //no instance
    }


    public static ArrayList<MenuFood> selectedFoods = new ArrayList<MenuFood>();;


    public static boolean menuFoodCanBeReserved(MenuFood menuFood){

        List<Basket> baskets = new ArrayList<Basket>();

        for(MenuFood menufood:selectedFoods) {

            Basket newBasket = new Basket(menufood);
            boolean flag = false;

            for(Basket basket:baskets){

                if(basket.getMenuFood().equals(newBasket.getMenuFood())) {
                    basket.setCount(basket.getCount() + 1);
                    flag = true;
                    break;
                }
            }

            if(!flag)
                baskets.add(newBasket);

        }

        for(Basket basket:baskets){

            if(basket.getMenuFood().equals(menuFood)){
                return basket.canBeReserved();
            }
        }

        return true;
    }

    public static List<Basket> getShoppingBasket() {

        List<Basket> baskets = new ArrayList<Basket>();


        double price = 0;

        for(MenuFood menufood:selectedFoods){

            Basket newBasket = new Basket(menufood);
            boolean flag = false;

            price += menufood.getPayedPrice();

            for(Basket basket:baskets){

                if(basket.getMenuFood().equals(newBasket.getMenuFood())) {
                    basket.setCount(basket.getCount() + 1);
                    flag = true;
                    break;
                }
            }

            if(!flag)
                baskets.add(newBasket);
        }

        return baskets;
    }

    public static double getBasketPrice() {

        double price = 0;

        for (MenuFood menufood : selectedFoods) {

            price += menufood.getPayedPrice();

        }


        return price;
    }
}
