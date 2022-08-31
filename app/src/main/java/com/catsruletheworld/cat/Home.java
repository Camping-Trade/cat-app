package com.catsruletheworld.cat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Home extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);

        ListViewAdapter listViewAdapter = new ListViewAdapter();
        //ListViewAdapter listViewAdapter = new ListViewAdapter(getActivity(), android.R.layout.listview_item, LIST_MENU);

        ListView listView = (ListView) view.findViewById(R.id.listview1);
        listView.setAdapter(listViewAdapter);

        // 아이템 추가
        listViewAdapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_launcher_background), "Title1", "Description1");
        listViewAdapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_launcher_foreground), "Title2", "Description2");

        // 리스트뷰 클릭 이벤트
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                ListViewItem listViewItem = (ListViewItem) parent.getItemAtPosition(position);

                String titleStr = listViewItem.getTitleStr();
                String detailStr = listViewItem.getDetailStr();
                Drawable imgDrawable = listViewItem.getImgDrawable();

                // 가져온 데이터 사용하기

            }
        });

        return view;
        //return inflater.inflate(R.layout.fragment_home, container, false);
    }
}