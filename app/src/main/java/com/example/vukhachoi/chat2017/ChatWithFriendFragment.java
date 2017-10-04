package com.example.vukhachoi.chat2017;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Vu Khac Hoi on 9/25/2017.
 */

public class ChatWithFriendFragment extends android.support.v4.app.Fragment{
    public   ChatWithFriendFragment(){

    }
    String EmailNguoiNhan="";
    private EditText edtInput;
    private DatabaseReference mDatabase;
    FirebaseUser user;
    ImageView imgicon,imgStatus;
    TextView txtName;
    NestedScrollView scrollView;
    String email;
    LinearLayout linearLayout ;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.chat_with_friend, container, false);
        imgStatus=view.findViewById(R.id.imgStatus);
        linearLayout=view.findViewById(R.id.chatlayout);
        edtInput=view.findViewById(R.id.edtInput);
        imgicon=view.findViewById(R.id.imgicon);
        txtName=view.findViewById(R.id.txtName);
        user = FirebaseAuth.getInstance().getCurrentUser();
        final   Bundle bundle=getArguments();
        scrollView =  view.findViewById (R.id.nest1);
        scrollView.setFillViewport (true);



        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss_SSSZ");
        email=user.getEmail().toString();
        email=email.replaceAll("[.]","");
        EmailNguoiNhan=(bundle.getString("Email")).replaceAll("[.]","");
        Button btnsent=view.findViewById(R.id.btnsent);

        if(bundle!=null)
        {
            txtName.setText(bundle.getString("UserName"));
            Picasso.with(getContext()).load(Uri.parse(bundle.getString("Avatar"))).into(imgicon);


        }
        btnsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(bundle!=null)
                {

                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    Date currentTime = java.util.Calendar.getInstance().getTime();
                    mDatabase.child(email).child("Chat").child(EmailNguoiNhan).child(sdf.format(currentTime)).setValue("M"+edtInput.getText().toString());
                    mDatabase.child(EmailNguoiNhan).child("Chat").child(email).child(sdf.format(currentTime)).setValue("T"+edtInput.getText().toString());
                    scrollView.fullScroll(View.FOCUS_DOWN);

                }


            }
        });

        NhanThu();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(EmailNguoiNhan).child("Setting").child("Status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Picasso.with(getContext()).load(dataSnapshot.getValue().toString()).into(imgStatus);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        scrollView.fullScroll(View.FOCUS_DOWN);
        return view;
    }
int i=0;
    @SuppressLint("ResourceType")
    private  void NhanThu()
    {
        mDatabase = FirebaseDatabase.getInstance().getReference().child(email).child("Chat").child(EmailNguoiNhan);
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {



try {

//    String str_date=s.substring(0,s.length()-2);
//    DateFormat formatter ;
//    Date date=null ;
//    formatter = new SimpleDateFormat("yyyyMMdd_HHmmss_SSSZ");
//    try {
//        date = formatter.parse(str_date);
//    } catch (ParseException e) {
//        e.printStackTrace();
//    }



    scrollView.fullScroll(View.FOCUS_DOWN);
    if (((dataSnapshot.getValue().toString()).charAt(0)) == 'T') {

        TextView valueTV = new TextView(getActivity());
        valueTV.setPadding(20, 20, 0, 0);
        valueTV.setText((dataSnapshot.getValue().toString()).substring(1,(dataSnapshot.getValue().toString()).length()));
        valueTV.setId(6);
        valueTV.setGravity(Gravity.LEFT);
        valueTV.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.addView(valueTV);
    } else if (((dataSnapshot.getValue().toString()).charAt(0)) == 'M') {
        TextView valueTV1 = new TextView(getActivity());
        valueTV1.setGravity(Gravity.RIGHT);
        valueTV1.setText((dataSnapshot.getValue().toString()).substring(1,(dataSnapshot.getValue().toString()).length()));
        valueTV1.setPadding(0, 20, 20, 0);
        valueTV1.setId(5);
        valueTV1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.addView(valueTV1);

    }
    scrollView.fullScroll(View.FOCUS_DOWN);

}catch (Exception e){}


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("XXam1",s+dataSnapshot.getValue());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
