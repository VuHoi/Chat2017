package com.example.vukhachoi.chat2017;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import adapter.FriendAdapter;
import model.Friend;

/**
 * Created by Vu Khac Hoi on 9/24/2017.
 */

public class FriendFragment extends Fragment {
    ListView lsvFriend;
    List<Friend>friendList;
    FriendAdapter adapter;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    public  FriendFragment()
    {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.friend, container, false);
lsvFriend=view.findViewById(R.id.lsvFriend);

//firebase goi toi nhanh chinh
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        // anh xa
        friendList=new ArrayList<>();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
      NestedScrollView scrollView =  view.findViewById (R.id.nest2);
        scrollView.setFillViewport (true);
        String email=user.getEmail().toString();
        email=email.replaceAll("[.]","");
     adapter = new FriendAdapter(getActivity(), R.layout.item_fiend, friendList);
        lsvFriend.setAdapter(adapter);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String Avatar;
               try{
                   Avatar=dataSnapshot.child("Setting").child("avatar").getValue().toString();
               }catch (Exception e){
              Avatar="https://cdn.iconscout.com/public/images/icon/free/png-64/user-avatar-person-account-profile-people-business-man-employee-3b85f3b32cb9e664-64x64.png";

               }
               String Name=dataSnapshot.child("Setting").child("UserName").getValue().toString();
                String LastComment;
                try{
                    LastComment=dataSnapshot.child("Chat").child("Lastcomment").getValue().toString();
                }catch (Exception e){
                    LastComment=" ... ";

                }

                String Status=dataSnapshot.child("Setting").child("Status").getValue().toString();
String Email=dataSnapshot.child("Setting").child("Email").getValue().toString();
                Friend friend=new Friend(Avatar,Name,LastComment,Email,Status);

               friendList.add(friend);



                    adapter.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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

        lsvFriend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
                ChatWithFriendFragment  chatWithFriendFragment=new ChatWithFriendFragment();
                Bundle bundle=new Bundle();
                bundle.putString("Email",friendList.get(i).getEmail());
                bundle.putString("UserName",friendList.get(i).getName());
                bundle.putString("Avatar",friendList.get(i).getAvatar());
                chatWithFriendFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.frlayoutfriend,chatWithFriendFragment);
                fragmentTransaction.addToBackStack("haha");
                fragmentTransaction.commit();
            }
        });


       return view;
    }
}