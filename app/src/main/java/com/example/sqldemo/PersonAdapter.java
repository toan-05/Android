package com.example.sqldemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

public class PersonAdapter extends BaseAdapter {

    private MainActivity activity;
    private ArrayList<Person> personList;

    public PersonAdapter(MainActivity activity, ArrayList<Person> personList) {
        this.activity = activity;
        this.personList = personList;
    }

    @Override
    public int getCount() {
        return personList.size();
    }

    @Override
    public Object getItem(int position) {
        return personList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return personList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item, parent, false);
        }
        Person person = personList.get(position);
        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvAge = convertView.findViewById(R.id.tvAge);
        TextView tvAddress = convertView.findViewById(R.id.tvAddress);

        tvName.setText(person.getName());
        tvAge.setText("Tuoi: " + person.getAge());
        tvAddress.setText("Dia chi: " + person.getAddress());

        convertView.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(activity, v);
            popup.inflate(R.menu.menu_item);
            popup.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.menu_update) {
                    activity.showDialogUpdate(person);
                    return true;
                } else if (id == R.id.menu_delete) {
                    activity.showDialogDelete(person);
                    return true;
                }
                return false;
            });
            popup.show();
        });

        return convertView;
    }
}