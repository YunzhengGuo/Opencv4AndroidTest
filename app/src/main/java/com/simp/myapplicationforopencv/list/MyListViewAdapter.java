package com.simp.myapplicationforopencv.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.simp.myapplicationforopencv.R;

import java.util.ArrayList;
import java.util.List;

public class MyListViewAdapter extends BaseAdapter {
    private List<CommandData> commandDataList;
    private Context context;

    public MyListViewAdapter( Context appcontext) {
        this.commandDataList = new ArrayList<>();
        this.context = appcontext;
    }

    public List<CommandData> getModel(){
        return this.commandDataList;
    }
    @Override
    public int getCount() {
        return commandDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return commandDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return  commandDataList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowview=inflater.inflate(R.layout.rowitem,parent,false);
       TextView txtitem= (TextView)rowview.findViewById(R.id.Row_textView);
        rowview.setTag(commandDataList.get(position));
        txtitem.setText(commandDataList.get(position).getCommand());
        return rowview;
    }
}
