package com.catsruletheworld.cat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonalPage extends Fragment {
    TextView name_view;
    ImageView image_view;

    String name_str;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_page, null);

        name_view = (TextView) view.findViewById(R.id.user_name);
        image_view = (ImageView) view.findViewById(R.id.user_image);

        //name_str = String.valueOf(((MainActivity)getActivity()).user_info.getProfile().getNickname());

        //name_view.setText(name_str);

        return inflater.inflate(R.layout.fragment_personal_page, container, false);
    }
}