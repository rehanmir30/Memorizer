package com.example.memorizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditTask extends AppCompatActivity {

    TextInputEditText titl, descriptio;
    TextView timer;
    Switch aSwitch;
    ImageView close;
    FloatingActionButton saveEdit;
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        titl = findViewById(R.id.titleField);
        descriptio = findViewById(R.id.description);
        timer = findViewById(R.id.timer);
        aSwitch = findViewById(R.id.switch1);
        close = findViewById(R.id.close);
        saveEdit = findViewById(R.id.saveedit);
        radioGroup = findViewById(R.id.radioGroup);

        String dataBase_url = "https://memorizer-8936d-default-rtdb.firebaseio.com/";

        Intent i = getIntent();
        String title = i.getStringExtra("title");
        String description = i.getStringExtra("description");
        String time = i.getStringExtra("time");

        titl.setText(title);
        descriptio.setText(description);
        if (!time.equals("No time selected")) {
            aSwitch.setChecked(true);
            timer.setText(time);
        }

        final boolean[] status = {false};

        saveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance(dataBase_url);
                DatabaseReference myRef = database.getReference("Tasks");
                String des = descriptio.getText().toString().trim();
                String tit = titl.getText().toString().trim();
                String t = timer.getText().toString().trim();

                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (int i = 1; i <= dataSnapshot.getChildrenCount(); i++) {
                            if (dataSnapshot.child(String.valueOf(i)).child("Title").getValue().toString().equals(tit)) {
                                myRef.child(String.valueOf(i)).child("Description").setValue(des);
                                myRef.child(String.valueOf(i)).child("Time").setValue(t);
                                myRef.child(String.valueOf(i)).child("Priority").setValue(radioButton.getText().toString().trim());
                                status[0] = true;
                            }
                        }
                        if (status[0] == true) {
                            Toast.makeText(getApplicationContext(), "Task updated", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "No such title found in Databse", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(i);
        finish();
    }
}