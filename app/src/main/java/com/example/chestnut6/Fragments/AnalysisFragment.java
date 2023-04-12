package com.example.chestnut6.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.chestnut6.R;


public class AnalysisFragment extends Fragment {

    TextView inputVoltage;
    Button showVoltage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_analysis, container, false);

        inputVoltage = view.findViewById(R.id.inputVoltage);
        showVoltage = view.findViewById(R.id.showVoltage);


        Bundle bundle = this.getArguments();

//
//
//
//        showVoltage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String data = "";
//                data = bundle.getString("key");
//                inputVoltage.setText(data);
//
//
//            }
//        });

    return view;
    }
}