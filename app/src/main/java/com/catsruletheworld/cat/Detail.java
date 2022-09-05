package com.catsruletheworld.cat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Detail extends Fragment {
    ImageView uploaded_image;
    TextView uploaded_title;
    TextView uploaded_desc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, null);

        uploaded_image = (ImageView) view.findViewById(R.id.uploaded_image);
        uploaded_title = (TextView) view.findViewById(R.id.uploaded_title);
        uploaded_desc = (TextView) view.findViewById(R.id.uploaded_desc);

        String title = this.getArguments().getString("title");
        String desc = this.getArguments().getString("desc");

        uploaded_title.setText(title);
        uploaded_desc.setText(desc);

        return view;
    }
}