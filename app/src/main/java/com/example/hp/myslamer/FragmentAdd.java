package com.example.hp.myslamer;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;




public class FragmentAdd  extends android.support.v4.app.Fragment{

    String value;
    Integer value1;
    String valueques;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v= inflater.inflate(R.layout.fragment_add,container,false);

        final TextView textView=(TextView) v.findViewById(R.id.textView2);

        final EditText editText =(EditText)v.findViewById(R.id.editText1);

        Button button=(Button)v.findViewById(R.id.button3);

        SharedPreferences sharedPreferences=getActivity().getApplicationContext().getSharedPreferences("addpreference", Context.MODE_PRIVATE);

        final String umail=sharedPreferences.getString("UserMail", "");

        final String fmail=sharedPreferences.getString("FriendMail", "");

        final FriendDataBase friendDataBase= new FriendDataBase(getActivity().getApplicationContext());

        final Firebase myFirebaseRef = new Firebase("https://myslam1.firebaseio.com/users/" + umail.replace(".",",")+
                "/" +
                fmail.replace(".",","));

        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    if( dataSnapshot.child("quesNumber").getValue().toString().isEmpty()){
                        System.out.println(dataSnapshot.child("quesNumber").getValue().toString());
                    }else{
                        valueques = dataSnapshot.child("quesNumber").getValue().toString();

                        System.out.println("Value question" + valueques);

                        TextView textView = (TextView) v.findViewById(R.id.textView2);

                        textView.setText(dataSnapshot.child("ques").getValue().toString());

                        myFirebaseRef.child("quesNumber").removeValue();

                        myFirebaseRef.child("ques").removeValue();
                    }

                } catch (Exception e) {

                }
                try{
                    System.out.println("Ans AAyo");
                    if( dataSnapshot.child("ans").getValue().toString()!= null){

                        value1=Integer.parseInt(dataSnapshot.child("ansnumb").getValue().toString());

                        System.out.println(value1 + "ansNumb");

                        value =dataSnapshot.child("ans").getValue().toString();

                        Toast.makeText(getActivity().getApplicationContext(),value,Toast.LENGTH_LONG).show();

                        try {
                            value1=value1+1;
                            friendDataBase.addAnswer(value1, fmail, dataSnapshot.child("ans").getValue().toString());

                            myFirebaseRef.child("ans").removeValue();

                            myFirebaseRef.child("ansnumb").removeValue();
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getActivity().getApplicationContext(),"Answer not added in DataBase",Toast.LENGTH_LONG).show();
                        }
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ans=editText.getText().toString();
                if(! ans.isEmpty()){
                    Firebase myFirebaseRef = new Firebase("https://myslam1.firebaseio.com/users/" + fmail.replace(".", ",") + "/" + umail.replace(".", ","));

                    myFirebaseRef.child("ansnumb").setValue(valueques);

                    myFirebaseRef.child("ans").setValue(ans);
                }
            }
        });

        return v;
    }
}
