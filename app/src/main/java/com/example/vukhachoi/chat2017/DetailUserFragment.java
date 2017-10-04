package com.example.vukhachoi.chat2017;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Vu Khac Hoi on 9/26/2017.
 */

public class DetailUserFragment  extends Fragment {
    private DatabaseReference mDatabase;



    ListView lsvStatus;
    FirebaseUser user;

    public  DetailUserFragment()
    {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
View view =inflater.inflate(R.layout.detail_user, container, false);



        lsvStatus=view.findViewById(R.id.lsvStatus);

        user = FirebaseAuth.getInstance().getCurrentUser();
        //khoi tao


//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        String email = user.getEmail().toString();
//        email = email.replaceAll("[.]", "");
//        mDatabase.child(email).child("Setting").child("Status").child("Friend").setValue("")
//  mDatabase = FirebaseDatabase.getInstance().getReference().child("Setting").child("");



        AddEvent();
        return view;
    }

    private void AddEvent() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String email = user.getEmail().toString();
       email = email.replaceAll("[.]", "");
     //   mDatabase.child("Status").child();

    }
}
