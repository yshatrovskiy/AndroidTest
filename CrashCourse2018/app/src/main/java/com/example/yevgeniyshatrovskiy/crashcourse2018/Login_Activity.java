package com.example.yevgeniyshatrovskiy.crashcourse2018;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final TextView welcomeText = findViewById(R.id.loginText);
        welcomeText.setText(R.string.Welcome);
        final EditText userName = findViewById(R.id.loginName);
        final EditText password = findViewById(R.id.loginPassword);
        final Button loginButton = findViewById(R.id.loginButton);


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
