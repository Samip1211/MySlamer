package com.example.hp.myslamer;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FragmentShowAll extends android.support.v4.app.Fragment {
   ImageButton button;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_show_all,container,false);

        ListView showQues=(ListView) v.findViewById(R.id.questionList);

        List<String> adques= new ArrayList<>();

        final ArrayAdapter<String> qlist=new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1,adques);

        showQues.setAdapter(qlist);

        final FriendDataBase friendDataBase= new FriendDataBase(getActivity().getApplicationContext());

        SharedPreferences sharedPreferences=getActivity().getApplicationContext().getSharedPreferences("addpreference", Context.MODE_PRIVATE);

        String fmail=sharedPreferences.getString("FriendMail", "Abc");

        friendDataBase.onCreate1(fmail);

        try {
            fmail=fmail.replace("@", "a");

            fmail=fmail.replace(".", "a");

            Cursor populateques = friendDataBase.getQuestion(fmail);

            if(populateques.moveToFirst()){

                do {
                    String num = populateques.getString(populateques.getColumnIndex("NUM"));

                    qlist.add(num);

                }while (populateques.moveToNext());
            }
        }catch (Exception e){

        }

        button = (ImageButton) v.findViewById(R.id.fab_image_button_activity_slam);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(getActivity());

                dialog.setContentView(R.layout.add_question);

                dialog.setTitle("Add Question");

                final EditText newques=(EditText) dialog.findViewById(R.id.newquestion);

                Button b2 = (Button) dialog.findViewById(R.id.addQuestion);

                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        SharedPreferences sharedPreferences=getActivity().getApplicationContext().getSharedPreferences("addpreference", Context.MODE_PRIVATE);

                        String umail=sharedPreferences.getString("UserMail","");

                        String fmail=sharedPreferences.getString("FriendMail", "Abc");

                        final String newquestion=newques.getText().toString();

                        int a = 0;

                        Cursor cursor=friendDataBase.getQuestion(fmail);

                        while (cursor.moveToNext()){
                            a=cursor.getInt(cursor.getColumnIndex("NUM"));
                        }

                        Firebase myFirebaseRef = new Firebase("https://myslam1.firebaseio.com/users/" + fmail.replace(".", ",") + "/" + umail.replace(".", ","));

                        try {
                            myFirebaseRef.child("quesNumber").setValue(a);

                            myFirebaseRef.child("ques").setValue(newquestion);

                            a = a++;

                            fmail=fmail.replace("@","a");

                            fmail = fmail.replace(".", "a");

                            friendDataBase.addQuestion(newquestion,fmail);

                            //textView.setText(uid);
                            dialog.cancel();
                        } catch (Exception e) {
                            e.printStackTrace();

                            Toast.makeText(getActivity().getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                            //textView.setText("Error");
                        }

                    }
                });

                // frnd = edit.getText().toString();
                dialog.show();

            }
        });

        final String finalFmail = fmail;

        showQues.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dialog dialog=new Dialog(getActivity());
                dialog.setContentView(R.layout.showinfo);

                dialog.setTitle("LE Joie le Hu lakhyu");

                Cursor getresponse=friendDataBase.getSpecific(position+1, finalFmail);

                if(getresponse.moveToFirst()) {

                    TextView textView1=(TextView) dialog.findViewById(R.id.slamans);

                    TextView textView = (TextView) dialog.findViewById(R.id.slamques);

                    textView.setText(getresponse.getString(getresponse.getColumnIndex("QUESTION")));

                   try{
                       textView1.setText(getresponse.getString(getresponse.getColumnIndex("ANSWER")));

                   }catch (Exception e){

                   }
                }
                else{
                    TextView textView = (TextView) dialog.findViewById(R.id.slamques);
                    textView.setText("Beta Problem thayo");
                }
                dialog.show();
            }

        });

        return v;
    }
}
