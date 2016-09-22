package com.example.hp.myslamer;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class StartActivity extends Activity {
    Button b1,b2;
    EditText editEmail,editPasswd,signup_mail,signup_passwd;
    final Context context=this;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.start_activity);

        Firebase.setAndroidContext(this);

        final Firebase myref=new Firebase("https://myslam1.firebaseio.com");

        b1=(Button)findViewById(R.id.signIn);

        b2=(Button)findViewById(R.id.signup);

        editEmail=(EditText)findViewById(R.id.email);

        signup_mail=(EditText)findViewById(R.id.signUpmail);

        signup_passwd=(EditText)findViewById(R.id.signUppasswd);

        editPasswd=(EditText)findViewById(R.id.password);

        //textView=(TextView)findViewById(R.id.textView4);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager
                        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if(activeNetworkInfo!=null&& activeNetworkInfo.isConnected()) {

                    final String mail = editEmail.getText().toString();

                    String paswd = editPasswd.getText().toString();

                    myref.authWithPassword(mail, paswd, new Firebase.AuthResultHandler() {
                        @Override
                        public void onAuthenticated(AuthData authData) {
                            System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                            Intent newintent = new Intent(StartActivity.this, MainActivity.class);

                            Bundle b = new Bundle();

                            b.putString("Uid", authData.getUid());

                            b.putString("UserMail",mail);

                            newintent.putExtras(b);
                            //textView.setText(authData.getUid());

                            startActivity(newintent);
                        }

                        @Override
                        public void onAuthenticationError(FirebaseError firebaseError) {
                            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();

                            // there was an error
                        }
                    });
                }else{

                    AlertDialog.Builder alertdialog=new AlertDialog.Builder(context);
                    alertdialog.setTitle("Internet Connection not available");
                    alertdialog.setMessage("Choose ").setCancelable(false).setPositiveButton("Connect Now", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent newintent = new Intent(new Intent(Settings.ACTION_WIFI_SETTINGS));
                            startActivity(newintent);
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog1= alertdialog.create();
                    alertDialog1.show();

                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    final String mail=signup_mail.getText().toString();
                    String passwd=signup_passwd.getText().toString();

                    myref.createUser(mail, passwd, new Firebase.ValueResultHandler<Map<String, Object>>() {
                        @Override
                        public void onSuccess(Map<String, Object> result) {

                            System.out.println("Successfully created user account with uid: " + result.get("uid"));

                            Intent newintent = new Intent(StartActivity.this, MainActivity.class);

                            Bundle b = new Bundle();

                            b.putString("Uid", result.get("uid").toString());
                            b.putString("UserMail",mail);

                            newintent.putExtras(b);

                            startActivity(newintent);

                        }
                        @Override
                        public void onError(FirebaseError firebaseError) {
                            // there was an error
                        }
                    });
                }catch(Exception e){

                }
            }
        });
        super.onCreate(savedInstanceState);
    }
}
