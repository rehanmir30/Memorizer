package com.example.memorizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.memorizer.model.SavedTasks;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    FloatingActionButton addtaskbtn;
    LinearLayout noTaskLayout;

    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ArrayList<SavedTasks> tasksList = new ArrayList<>();


        addtaskbtn = findViewById(R.id.addtaskbtn);
        noTaskLayout = findViewById(R.id.notasklayout);
        mRecyclerView = findViewById(R.id.recyclerView);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        String dataBase_url = "https://memorizer-8936d-default-rtdb.firebaseio.com/";
        FirebaseDatabase database = FirebaseDatabase.getInstance(dataBase_url);
        DatabaseReference myRef = database.getReference("Tasks");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int total_tasks = (int) dataSnapshot.getChildrenCount();
                if (total_tasks < 1) {
                    noTaskLayout.setVisibility(View.VISIBLE);
                } else {
                    noTaskLayout.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);

                    // recyclerView.setVisibility(View.VISIBLE);
                    //show tasks list here

                    for (int i = 0; i <= dataSnapshot.getChildrenCount(); i++) {
                        if (dataSnapshot.child(String.valueOf(i)).exists()) {
                            String title1 = (String) dataSnapshot.child(String.valueOf(i)).child("Title").getValue();
                            String descrip = (String) dataSnapshot.child(String.valueOf(i)).child("Description").getValue();
                            String time = (String) dataSnapshot.child(String.valueOf(i)).child("Time").getValue();
                            String priority=(String) dataSnapshot.child(String.valueOf(i)).child("Priority").getValue();
                            if (time.equals("")) {
                                time = "No time selected";
                            }
                            SavedTasks task = new SavedTasks(descrip, title1, time,priority);

                            tasksList.add(task);
                        }

                    }
                    Collections.sort(tasksList, new Comparator<SavedTasks>(){
                        public int compare(SavedTasks obj1, SavedTasks obj2) {
                            // ## Ascending order
                            return obj1.getColor().compareToIgnoreCase(obj2.getColor()); // To compare string values
                            // return Integer.valueOf(obj1.empId).compareTo(Integer.valueOf(obj2.empId)); // To compare integer values

                            // ## Descending order
                            // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
                            // return Integer.valueOf(obj2.empId).compareTo(Integer.valueOf(obj1.empId)); // To compare integer values
                        }
                    });

                    mRecyclerView.setHasFixedSize(true);
                    mLayoutManager = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.VERTICAL, false);
                    mAdapter = new ExampleAdapter(tasksList);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);

                    mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Intent i=new Intent(getApplicationContext(),EditTask.class);
                            i.putExtra("title",tasksList.get(position).getTitle().toString());
                            i.putExtra("description",tasksList.get(position).getDescription().toString());
                            i.putExtra("time",tasksList.get(position).getTime().toString());
                            startActivity(i);
                            finish();
                           // Toast.makeText(getApplicationContext(),tasksList.get(position).getTitle().toString(),Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        });

        addtaskbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, AddTask.class);
                startActivity(i);
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings) {
            Intent i = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(i);
        }
        if (id == R.id.about) {
            Toast.makeText(getApplicationContext(), "About is not available at the moment", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}