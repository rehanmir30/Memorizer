package com.example.memorizer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AddTask extends AppCompatActivity {

    Switch mySwitch;
    TextInputEditText titleField, description;
    LinearLayout reminder;
    TextView timer;
    FloatingActionButton saveTask;
    ImageView close;
    RadioGroup radioGroup;
    RadioButton radioButton;
    String dataBase_url = "https://memorizer-8936d-default-rtdb.firebaseio.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        mySwitch = findViewById(R.id.switch1);
        reminder = findViewById(R.id.reminder);
        timer = findViewById(R.id.timer);
        saveTask = findViewById(R.id.savetask);
        titleField = findViewById(R.id.titleField);
        description = findViewById(R.id.description);
        close = findViewById(R.id.close);
        radioGroup=findViewById(R.id.radioGroup);

        reminder.setVisibility(View.GONE);

        // Write a message to the database

        final int[] new_task = new int[1];

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddTask.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        });

        saveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleField.getText().toString().trim();
                String descrip = description.getText().toString().trim();
                String time = timer.getText().toString().trim();

                int selectedId=radioGroup.getCheckedRadioButtonId();
                radioButton=(RadioButton)findViewById(selectedId);

                if (title.isEmpty()) {
                    titleField.setError("Title required");
                    return;
                } else {
                    //save data to database
                    FirebaseDatabase database = FirebaseDatabase.getInstance(dataBase_url);
                    DatabaseReference myRef = database.getReference("Tasks");

                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            int tasks_count = (int) dataSnapshot.getChildrenCount();
                            new_task[0] = tasks_count + 1;

                            Log.d("******", String.valueOf(new_task[0]));

                            myRef.child(String.valueOf(tasks_count + 1)).child("Title").setValue(title);
                            myRef.child(String.valueOf(tasks_count + 1)).child("Description").setValue(descrip);
                            myRef.child(String.valueOf(tasks_count + 1)).child("Time").setValue(time);
                            myRef.child(String.valueOf(tasks_count + 1)).child("Priority").setValue(radioButton.getText().toString().trim());

                            Intent i = new Intent(AddTask.this, HomeActivity.class);
                            startActivity(i);
                            finish();
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddTask.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timer.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    reminder.setVisibility(View.VISIBLE);

                } else {
                    reminder.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(i);
        finish();
    }
}