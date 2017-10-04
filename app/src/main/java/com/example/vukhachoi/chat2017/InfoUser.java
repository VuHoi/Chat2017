package com.example.vukhachoi.chat2017;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class InfoUser extends AppCompatActivity {
private DatabaseReference mDatabase;
    private TabLayout tabLayout;
    private ViewPager viewPager;
SearchView searchView;
ImageView profile_image;
TextView txtUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtUserName=findViewById(R.id.txtUserName);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.searchview);
setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NestedScrollView scrollView =  findViewById (R.id.nest);
        scrollView.setFillViewport (true);
        viewPager =  findViewById(R.id.viewpager);
        createViewPager(viewPager);
        tabLayout =  findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        createTabIcons();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        txtUserName.setText(user.getDisplayName().toString());
        profile_image=findViewById(R.id.profile_image);

        Picasso.with(InfoUser.this).load(user.getPhotoUrl()).into(profile_image);
    }

    private void createTabIcons() {

        ImageView tabOne = (ImageView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
tabOne.setImageResource(R.drawable.home);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        ImageView tabtwo = (ImageView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabtwo.setImageResource(R.drawable.firiend);
        tabLayout.getTabAt(1).setCustomView(tabtwo);
        ImageView tabthree = (ImageView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabthree.setImageResource(R.drawable.explore);
        tabLayout.getTabAt(2).setCustomView(tabthree);
        ImageView tabfour = (ImageView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabfour.setImageResource(R.drawable.account);
        tabLayout.getTabAt(3).setCustomView(tabfour);
    }

    private void createViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new HomeFragment(), "Tab 1");
        adapter.addFrag(new LayoutFriendfragment(), "Tab 2");
        adapter.addFrag(new HomeFragment(), "Tab 3");
        adapter.addFrag(new DetailUserFragment(), "Tab 4");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchview,menu);
        MenuItem item=menu.findItem(R.id.itsearch);
        searchView= (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // adaptet.getFilter().filter(newText)
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String email = user.getEmail().toString();
        email = email.replaceAll("[.]", "");
        mDatabase.child(email).child("Setting").child("Status").setValue("https://eyotdofe.files.wordpress.com/2016/09/red-dot.png?w=228");

    }
}