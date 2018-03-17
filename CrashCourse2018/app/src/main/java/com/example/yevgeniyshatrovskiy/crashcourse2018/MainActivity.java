package com.example.yevgeniyshatrovskiy.crashcourse2018;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message").child("users");


    TextView timerText;

    Person john, dan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        final EditText fName   = (EditText)findViewById(R.id.firstName);
        final EditText sName   = (EditText)findViewById(R.id.secondName);



        john = new Person("John", "Smith");
        dan = new Person("Dan", "Jones");

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");



        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child(fName.getText().toString()).setValue(new Person(fName.getText().toString(), sName.getText().toString()));
                Snackbar.make(view, "Hello again Yevgeniy", Snackbar.LENGTH_LONG).show();
                Log.v("Test", "Toast Message");

            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child("Dan").setValue(dan);
                myRef.child("Phil").setValue(john);
                Toast t = Toast.makeText(getApplicationContext(), "Wrote to JSON", Toast.LENGTH_SHORT);
                t.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void startTimer(){
        new CountDownTimer(100,100){
            @Override
            public void onTick(long millisUntilFinished) {
                timerText.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                timerText.setText("done!");
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(item.getItemId()){

            case R.id.action_settings:
                Toast toast = Toast.makeText(this, "Action Settings", Toast.LENGTH_LONG);
                toast.show();
                break;

            case R.id.recent_settings:
                Toast toast2 = Toast.makeText(this, "Recent Settings", Toast.LENGTH_LONG);
                toast2.show();
                break;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
