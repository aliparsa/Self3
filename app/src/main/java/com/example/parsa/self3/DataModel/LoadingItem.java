package com.example.parsa.self3.DataModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.parsa.self3.Interface.IListViewItem;
import com.example.parsa.self3.R;


/**
 * Created by parsa on 2015-01-22.
 */
public class LoadingItem implements IListViewItem {


    public View getView(Context context, View oldView) {

        if (oldView == null || !(oldView.getTag() instanceof LoadingItem)) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            oldView = inflater.inflate(R.layout.item_loading, null);
            Holder holder = new Holder();
            oldView.setTag(holder);
            getItem(holder, oldView, context);
        //    holder.setFont(context);
            return oldView;
        } else {
            Holder holder = (Holder) oldView.getTag();
            getItem(holder, oldView, context);
            return oldView;
        }
    }

    @Override
    public void setSelected(boolean flag) {

    }

    private void getItem(final Holder holder, View view, Context context) {


    }


    public class Holder {


    }
}


