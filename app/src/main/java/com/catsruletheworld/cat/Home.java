package com.catsruletheworld.cat;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.catsruletheworld.cat.listview.ListViewAdapter;
import com.catsruletheworld.cat.listview.ListViewItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Home extends Fragment {
    public static Context context_home;
    String new_user_title;
    String new_user_desc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);

        new_user_title = this.getArguments().getString("title");
        new_user_desc = this.getArguments().getString("desc");

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent floatIntent = new Intent(getActivity(), NewPost.class);
                floatIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(floatIntent);
            }
        });

        ListViewAdapter listViewAdapter = new ListViewAdapter();
        //ListViewAdapter listViewAdapter = new ListViewAdapter(getActivity(), android.R.layout.listview_item, LIST_MENU);

        ListView listView = (ListView) view.findViewById(R.id.listview1);
        listView.setAdapter(listViewAdapter);

        // 아이템 추가
        listViewAdapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_launcher_foreground), new_user_title, new_user_desc);

        // 리스트뷰 클릭 이벤트
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                ListViewItem listViewItem = (ListViewItem) parent.getItemAtPosition(position);

                String titleStr = listViewItem.getTitleStr();
                String detailStr = listViewItem.getDetailStr();
                Drawable imgDrawable = listViewItem.getImgDrawable();

                // 가져온 데이터 사용하기
                Intent listIntent = new Intent(getActivity(), NewPost.class);
                startActivity(listIntent);
                //https://m.blog.naver.com/PostView.nhn?isHttpsRedirect=true&blogId=since201109&logNo=221314321996
            }
        });

        return view;
    }
}