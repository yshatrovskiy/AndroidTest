package com.example.yevgeniyshatrovskiy.crashcourse2018.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yevgeniyshatrovskiy.crashcourse2018.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Login_Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String userName;
    StringBuilder text = new StringBuilder();
    EditText password;

    public void readAndWriteFileOnInternalStorage(Context mcoContext, String sFileName, String sBody){
        File file = new File(mcoContext.getFilesDir(),"mydir");
        if(!file.exists()){
            file.mkdir();

            try{
                File gpxfile = new File(file, sFileName);
                FileWriter writer = new FileWriter(gpxfile);
                writer.append(sBody);
                writer.flush();
                writer.close();
                Log.v("WR", "Writing");

            }catch (Exception e){
                Log.v("WR", "Writing ERROR");
                e.printStackTrace();

            }
        }else{
            try{
                File username = new File(file, "Username");
                BufferedReader br = new BufferedReader(new FileReader(username));
                String line;

                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                Log.v("WR", "READING");
                br.close();
            }catch (IOException e){
                Log.v("WR", "READING ERROR");
                e.printStackTrace();
            }
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        password = findViewById(R.id.loginPassword);

        readAndWriteFileOnInternalStorage(this, "Username", "Test");


        TextView loginText = findViewById(R.id.loginText);
        loginText.setText(text);


        final EditText userName = findViewById(R.id.loginName);
        userName.setHint(text);

//        password.setHint(R.string.password);
        final Button loginButton = findViewById(R.id.loginButton);
        loginButton.setText(R.string.submit);


        mAuth = FirebaseAuth.getInstance();

        View.OnClickListener login = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(userName.getText().toString(), password.getText().toString());
            }
        };

        loginButton.setOnClickListener(login);
    }

    private void createAccount(String nname, String pword) {
        mAuth.createUserWithEmailAndPassword(nname, pword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            accessApp();
                        }else{
                            Log.w("Login", "createUserWithEmail:failure", task.getException());
                        }
                    }
                });
    }

    private void accessApp(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
