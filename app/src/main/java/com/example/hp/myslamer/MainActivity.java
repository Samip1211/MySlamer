package com.example.hp.myslamer;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;

    private CharSequence mTitle;

    public static String uid;

    public static  String userMail;

    public static int count=0;

    public static  String frndmail="";

    Toolbar toolbar;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      Bundle bundle=getIntent().getExtras();

        uid=bundle.getString("Uid");

        userMail=bundle.getString("UserMail");

       // userMail="sam.kakarot@gmail.com";

        setContentView(R.layout.activity_main);

        toolbar =(Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("My Slamer");

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        //mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);


    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch(position)
        {
            case 0:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                        .commit();
                break;
            case 1:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, AboutFragment.newInstance(position + 1)).commit();
                break;
            case 2:
                fragmentManager.beginTransaction().replace(R.id.container,LogoutFragment.newInstance(position+1)).commit();
                break;

        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

        //actionBar.setDisplayShowTitleEnabled(true);
        //actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
          //  restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment  {

        EditText edit;
        TextView textView;
        ImageButton b1;
        String name,emailadd;

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            //Firebase.setAndroidContext(this.getActivity());

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            b1=(ImageButton)rootView.findViewById(R.id.fab_image_button);

            final ListView frndlistview= (ListView) rootView.findViewById(R.id.listviewfrnds);

            List<String> frnds= new ArrayList<>();

            final ArrayList<String> maillist= new ArrayList<String>();

            final ArrayAdapter<String> list = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1,frnds);

            frndlistview.setAdapter(list);

            final MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(getActivity().getApplicationContext());

            Cursor populatefrnds= dataBaseHelper.getFrnds();

            if(populatefrnds.moveToFirst()){
                do{
                    String addName=populatefrnds.getString(populatefrnds.getColumnIndex("NAME"));

                    String addEmail=populatefrnds.getString(populatefrnds.getColumnIndex("EMAIL"));

                    list.add(addName);

                    maillist.add(addEmail);

                }while (populatefrnds.moveToNext());
            }

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.dialog);
                    dialog.setTitle("Add Friend");
                   // dialog.setCancelable(false);
                    Button b2 = (Button) dialog.findViewById(R.id.button2);

                    b2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            EditText e1 = (EditText) dialog.findViewById(R.id.frndName);


                            EditText e2 = (EditText) dialog.findViewById(R.id.frndMail);
                            name = e1.getText().toString();

                            emailadd = e2.getText().toString();
                            try {

                                if (name.equals("delete")) {

                                } else if (name.isEmpty() || emailadd.isEmpty()) {

                                    Toast.makeText(getActivity().getApplicationContext(), "Please enter Data", Toast.LENGTH_LONG).show();
                                } else {
                                    try {

                                        dataBaseHelper.addFrnds(emailadd, name);

                                        Toast.makeText(getActivity().getApplicationContext(), "Friend Added", Toast.LENGTH_LONG).show();

                                        Cursor AllFriends = dataBaseHelper.getFrnds();

                                        AllFriends.moveToFirst();

                                        AllFriends.moveToNext();

                                        String Email = AllFriends.getString(AllFriends.getColumnIndex("EMAIL"));

                                        String Name = AllFriends.getString(AllFriends.getColumnIndex("NAME"));

                                        System.out.println("Here Here" + Email + " " + Name);

                                        list.add(Name);

                                        maillist.add(Email);



                                        //frndlistview.setAdapter(list);

                                    } catch (Exception e) {
                                        Toast.makeText(getActivity().getApplicationContext(), "Try Again", Toast.LENGTH_LONG).show();
                                    }
                                    dialog.cancel();
                                }

                            }catch (Exception e) {
                                Toast.makeText(getActivity().getApplicationContext(), "Not Done", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                    dialog.show();
                }

            });


            frndlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent newintent = new Intent("com.example.hp.myslamer.SLAMACTIVITY");

                    String frndmail=maillist.get(position);

                    Bundle b1= new Bundle();

                    b1.putString("UserMail",userMail);

                    b1.putString("FriendMail",frndmail);

                    newintent.putExtras(b1);

                    startActivity(newintent);
                }
            });
            /*
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String frnd;

                    frnd=edit.getText().toString();

                    Firebase myFirebaseRef = new Firebase("https://myslam1.firebaseio.com/users/"+  frnd.replace(".",",")+"/"+userMail.replace(".",",") );

                    try {
                       Map<String,String> map1=new HashMap<String, String>();
                        map1.put("A","Abc");
                        myFirebaseRef.push().setValue(map1);

                        //myFirebaseRef.child("1").setValue("Bol");

                        //textView.setText(uid);
                    }catch(Exception e)
                    {
                        e.printStackTrace();
                        textView.setText("Error");
                    }


                }
            });*/
            return rootView;
        }



        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public static class AboutFragment extends android.support.v4.app.Fragment {

        TextView textView;

        EditText editText;

        Button b1;
        private static final String ARG_SECTION_NUMBER = "section_number";

        public static AboutFragment newInstance(int sectionNumber) {
            AboutFragment fragment = new AboutFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public AboutFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_about1, container, false);
            textView=(TextView)rootView.findViewById(R.id.frndText);
            editText=(EditText)rootView.findViewById(R.id.frndmail);
            b1=(Button)rootView.findViewById(R.id.getData);

            textView.setText(uid);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Firebase myFirebaseRef = new Firebase("https://myslam1.firebaseio.com/users/" + userMail.replace(".",",")+
                            "/" +
                            editText.getText().toString().replace(".",","));
                   // myFirebaseRef.child("2").setValue("Bol");

                    myFirebaseRef.addChildEventListener(new ChildEventListener() {


                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Map<String,String> post=  (Map<String,String>) dataSnapshot.getValue();
                            System.out.println(post.get("A"));
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
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });

                }
            });
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }


}
