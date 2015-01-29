package com.example.parsa.self3;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Choreographer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.parsa.self3.Adapter.ListViewObjectAdapter;
import com.example.parsa.self3.DataModel.LoadingItem;
import com.example.parsa.self3.DataModel.MenuFood;
import com.example.parsa.self3.DataModel.NoItem;
import com.example.parsa.self3.DataModel.Reserve;
import com.example.parsa.self3.DataModel.Shopping;
import com.example.parsa.self3.Helper.Webservice;
import com.example.parsa.self3.Interface.CallBack;
import com.example.parsa.self3.Interface.FragmentCallback;

import java.util.ArrayList;

/**
 * Created by parsa on 2015-01-28.
 */
public  class MenuFoodFragment extends Fragment {
    public static final String ARG_OBJECT = "object";
    private ListView foodMenuLV;
    private Context context;
    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        context = getActivity();
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_two, container, false);
        Bundle args = getArguments();
//        ((TextView) rootView.findViewById(android.R.id.text1)).setText(
//                Integer.toString(args.getInt(ARG_OBJECT)));

        foodMenuLV = (ListView) rootView.findViewById(R.id.foodMenuLV);

        foodMenuLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                MenuFood food = ((MenuFood.Holder) view.getTag()).menufood;

                ((MainActivity) context).addNewFood(food);

                Shopping.selectedFoods.add(food);

                Toast.makeText(context, food.getFoodCaption() + " به سبد خرید افزوده شد! ", Toast.LENGTH_SHORT).show();
            }
        });

        mainActivity = (MainActivity) getActivity();



        // set loading to list view
        ArrayList<LoadingItem> loadingItems = new ArrayList<LoadingItem>();
        loadingItems.add(new LoadingItem());
        ListViewObjectAdapter adapter = new ListViewObjectAdapter(getActivity(),loadingItems);
        foodMenuLV.setAdapter(adapter);







        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();


        Webservice.GetMenuFoods(getActivity(), mainActivity.dateItem.getDate().getGregorianDate(), mainActivity.personnel.getUid(), new CallBack<ArrayList<MenuFood>>() {
            @Override
            public void onSuccess(ArrayList<MenuFood> result) {

                if (result.size()<1){
                    ArrayList<NoItem> noItems = new ArrayList<NoItem>();
                    noItems.add(new NoItem());
                    ListViewObjectAdapter adapter = new ListViewObjectAdapter(getActivity(),noItems);
                    foodMenuLV.setAdapter(adapter);
                    return;
                }

                ListViewObjectAdapter adapter = new ListViewObjectAdapter(getActivity(),result);
                foodMenuLV.setAdapter(adapter);

            }

            @Override
            public void onError(String errorMessage) {

            }
        });

    }
}