package com.example.parsa.self3;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.parsa.self3.Adapter.ListViewObjectAdapter;
import com.example.parsa.self3.DataModel.LoadingItem;
import com.example.parsa.self3.DataModel.NoItem;
import com.example.parsa.self3.DataModel.Reserve;
import com.example.parsa.self3.Helper.Webservice;
import com.example.parsa.self3.Interface.CallBack;

import java.util.ArrayList;

/**
 * Created by parsa on 2015-01-28.
 */
public  class ReserveFragment extends Fragment {
    public static final String ARG_OBJECT = "object";
    ListView reserveLV;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_one, container, false);
        Bundle args = getArguments();
//        ((TextView) rootView.findViewById(android.R.id.text1)).setText(
//                Integer.toString(args.getInt(ARG_OBJECT)));

        reserveLV = (ListView) rootView.findViewById(R.id.reserveLV);

        MainActivity mainActivity = (MainActivity) getActivity();


        // set loading to list view
        ArrayList<LoadingItem> loadingItems = new ArrayList<LoadingItem>();
        loadingItems.add(new LoadingItem());
        ListViewObjectAdapter adapter = new ListViewObjectAdapter(getActivity(),loadingItems);
        reserveLV.setAdapter(adapter);



        Webservice.GetReserves(getActivity(),mainActivity.dateItem.getDate().getGregorianDate(),mainActivity.personnel.getUid(),new CallBack<ArrayList<Reserve>>() {
            @Override
            public void onSuccess(ArrayList<Reserve> result) {

                if (result.size()<1){
                    ArrayList<NoItem> noItems = new ArrayList<NoItem>();
                    noItems.add(new NoItem());
                    ListViewObjectAdapter adapter = new ListViewObjectAdapter(getActivity(),noItems);
                    reserveLV.setAdapter(adapter);
                    return;
                }

                ListViewObjectAdapter adapter = new ListViewObjectAdapter(getActivity(),result);
                reserveLV.setAdapter(adapter);

            }

            @Override
            public void onError(String errorMessage) {

            }
        });

        reserveLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (view.getTag() instanceof Reserve.Holder) {
                    final Reserve reserve = (Reserve) ((Reserve.Holder) view.getTag()).reserve;
                    if (reserve.isShowCancel() == false) {
                        //   Toast.makeText(context,"لغو این رزرو امکان پذیر نیست",Toast.LENGTH_LONG).show();
                        return;
                    }

                    new AlertDialog.Builder(getActivity())
                            .setTitle("توجه")
                            .setMessage("آیا این رزرو لغو شود ؟")
                            .setNegativeButton("بله", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete

                                    final ProgressDialog progDialog = ProgressDialog.show(getActivity(), "تبادل داده با سرور", "کمی صبر کنید", true);
                                    progDialog.show();
                                    Webservice.CancelReserve(getActivity(), reserve.getId() + "", new CallBack<String>() {
                                        @Override
                                        public void onSuccess(String result) {
                                            progDialog.dismiss();
                                            Toast.makeText(getActivity(), "لغو رزرو با موفقیت انجام شد", Toast.LENGTH_SHORT).show();

                                            // setCredit(result);

                                            if (reserveLV != null && reserveLV.getAdapter() instanceof ListViewObjectAdapter) {

                                                ((ListViewObjectAdapter) reserveLV.getAdapter()).removeItem(reserve);

                                                ((ListViewObjectAdapter) reserveLV.getAdapter()).notifyDataSetChanged();
                                            }
                                        }

                                        @Override
                                        public void onError(String errorMessage) {
                                            progDialog.dismiss();
                                            Toast.makeText(getActivity(), "لغو موفقیت آمیز نبود", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            })
                            .setPositiveButton("خیر", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });


        return rootView;
    }
}