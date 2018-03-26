package com.example.yevgeniyshatrovskiy.crashcourse2018.Activities;


import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yevgeniyshatrovskiy.crashcourse2018.Objects.Person;
import com.example.yevgeniyshatrovskiy.crashcourse2018.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message").child("users");

    TextView timerText;
    Person john, dan;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText fName   = findViewById(R.id.firstName);
        fName.setHint(R.string.firstName);
        final EditText sName   = findViewById(R.id.secondName);
        sName.setHint(R.string.lastName);
        final EditText age = findViewById(R.id.ageInput);
        age.setHint(R.string.age);
        TextView welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText(R.string.Welcome);


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");

        ArrayList<String> peopleList = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, peopleList);
        final ListView listView = findViewById(R.id.peopleListView);
        listView.setAdapter(adapter);

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!fName.getText().toString().isEmpty() && !sName.getText().toString().isEmpty() && !age.getText().toString().isEmpty()){
                    myRef.child(fName.getText().toString()).setValue(new Person(fName.getText().toString(), sName.getText().toString(), Integer.parseInt(age.getText().toString())));
                    fName.setText("");
                    sName.setText("");
                    age.setText("");
                }
            }
        });


        ChildEventListener personChildListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                listView.invalidateViews();
                Person person = dataSnapshot.getValue(Person.class);
                adapter.add(person.firstName + " " + person.lastName + " age: " + person.age);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                listView.invalidateViews();
                Person person = dataSnapshot.getValue(Person.class);
                adapter.add(person.firstName + " " + person.lastName + " age: " + person.getAge());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                listView.invalidateViews();
                Person person = dataSnapshot.getValue(Person.class);
                adapter.remove(person.firstName + " " + person.lastName + " age: " + person.getAge());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                listView.invalidateViews();
                Person person = dataSnapshot.getValue(Person.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };

        myRef.addChildEventListener(personChildListener);
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
