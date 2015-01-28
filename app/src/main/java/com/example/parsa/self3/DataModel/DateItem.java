package com.example.parsa.self3.DataModel;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.parsa.self3.Helper.FontHelper;
import com.example.parsa.self3.Helper.PersianCalendar;
import com.example.parsa.self3.Interface.IListViewItem;
import com.example.parsa.self3.R;

import java.io.Serializable;


/**
 * Created by parsa on 2015-01-20.
 */
public class DateItem implements IListViewItem,Serializable {

    private PersianCalendar date;
    private boolean isSelected = false;

    public DateItem(PersianCalendar date) {
        this.date = date;
    }

    public PersianCalendar getDate() {
        return date;
    }

    public void setDate(PersianCalendar date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return date.toString();
    }

    public View getView(Context context, View oldView) {

        if (oldView == null || !(oldView.getTag() instanceof DateItem)) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            oldView = inflater.inflate(R.layout.item_date, null);
            Holder holder = new Holder();
            oldView.setTag(holder);
            getItem(holder, oldView);
            holder.setFont(context);
            return oldView;
        } else {

            Holder holder = (Holder) oldView.getTag();
            getItem(holder, oldView);
            return oldView;
        }
    }

    @Override
    public void setSelected(boolean flag) {
        this.isSelected = flag;
    }

    private void getItem(Holder holder, View view) {

        holder.dateItem = this;

        if (holder.dateName == null)
            holder.dateName = (TextView) view.findViewById(R.id.dateName);

        if (holder.dateNumber == null)
            holder.dateNumber = (TextView) view.findViewById(R.id.dateNumber);

        if (holder.today == null)
            holder.today = (TextView) view.findViewById(R.id.todayTxt);

        holder.dateName.setText(date.getPersianWeekDayStr());
        holder.dateNumber.setText(date.getIranianDate());
        holder.today.setText("");

        if (!isSelected)
            view.setBackgroundColor(Color.TRANSPARENT);
        else
            view.setBackgroundResource(R.drawable.date_item_selected);

        if (new PersianCalendar().getGregorianDate().equals(date.getGregorianDate())) {
            holder.today.setText("((امروز))");
            holder.dateName.setTextColor(Color.parseColor("#FF06880D"));
            holder.dateNumber.setTextColor(Color.parseColor("#FF06880D"));
            holder.today.setTextColor(Color.parseColor("#FF06880D"));
        }


    }


    public class Holder {
        TextView dateName;
        TextView dateNumber;
        TextView today;

        private DateItem dateItem;

        public void setFont(Context context) {
            FontHelper.SetFont(context, FontHelper.Fonts.MAIN_FONT, dateName, Typeface.NORMAL);
            FontHelper.SetFont(context, FontHelper.Fonts.MAIN_FONT, dateNumber, Typeface.NORMAL);
        }

        public DateItem getDateItem() {
            return dateItem;
        }
    }
}
