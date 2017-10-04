package com.example.vukhachoi.chat2017;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Vu Khac Hoi on 9/25/2017.
 */

public class LayoutFriendfragment  extends android.support.v4.app.Fragment{
    public   LayoutFriendfragment(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.layout_friend, container, false);



        return view;
    }
}