package com.catsruletheworld.cat;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<ListViewItem> listViewItems = new ArrayList<ListViewItem>();

    // 리스트 뷰 어댑터 생성자
    public ListViewAdapter() {
    }

    // 어댑터에 사용되는 데이터 수 리턴
    @Override
    public int getCount() {
        return listViewItems.size();
    }

    // located 에 위치한 데이터 화면에 출력하는 view 리턴
    @Override
    public View getView(int located, View convertView, ViewGroup parent) {
        final int loc = located;
        final Context context = parent.getContext();

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1);
        TextView descTextView = (TextView) convertView.findViewById(R.id.textView2);

        ListViewItem listViewItem = listViewItems.get(located);

        iconImageView.setImageDrawable(listViewItem.getImgDrawable());
        titleTextView.setText(listViewItem.getTitleStr());
        descTextView.setText(listViewItem.getDetailStr());

        return convertView;
    }

    @Override
    public Object getItem(int located) {
        return listViewItems.get(located);
    }

    @Override
    public long getItemId(int located) {
        return located;
    }

    public void addItem(Drawable img, String title, String detail) {
        ListViewItem listViewItem = new ListViewItem();

        listViewItem.setImgDrawable(img);
        listViewItem.setTitleStr(title);
        listViewItem.setDetailStr(detail);

        listViewItems.add(listViewItem);
    }
}
