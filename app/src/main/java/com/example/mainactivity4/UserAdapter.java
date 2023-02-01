package com.example.mainactivity4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UserAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater linflater;
    private TextView txt_1, txt_2;
    private String[] str1;
    private String[] str2;

    public UserAdapter(Context context, String[] s1, String[] s2)
    {
        mContext = context;
        str1 = s1;
        str2 = s2;
        linflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return str1.length;
    }

    @Override
    public Object getItem(int i) {
        return str1[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {

            view = linflater.inflate(R.layout.activity_list, null);

        }
        txt_1 = (TextView) view.findViewById(R.id.itemName1);
        txt_2 = (TextView) view.findViewById(R.id.textView);
        txt_1.setText(str1[i]);
        txt_2.setText(str2[i]);

        return view;
    }
}
