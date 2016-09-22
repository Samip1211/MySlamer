package com.example.hp.myslamer;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;


public class SlamActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewPager pager;
    ViewPageAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Home", "Events"};
    int Numboftabs = 2;
    public String umail;
    public String fmail;
    TextView t1, t2;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_slam);

        Bundle bundle = getIntent().getExtras();

        umail = bundle.getString("UserMail");

        fmail = bundle.getString("FriendMail");

        SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("addpreference",MODE_PRIVATE);

        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putString("UserMail",umail);
        editor.putString("FriendMail",fmail);

        editor.apply();
        /*
        t1=(TextView)findViewById(R.id.textView2);

        t2=(TextView)findViewById(R.id.textView3);

        t1.setText(umail);

        t2.setText(fmail);*/

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new ViewPageAdapter(getSupportFragmentManager(), Titles, Numboftabs);

        pager = (ViewPager) findViewById(R.id.pager);

        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);

        tabs.setDistributeEvenly(true);

        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        tabs.setViewPager(pager);



    }
}
