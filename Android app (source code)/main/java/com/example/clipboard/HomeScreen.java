package com.example.clipboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class HomeScreen extends AppCompatActivity {
    private ListView listView;
    ArrayList<String> arrayList;
    private ProgressBar progressBar;
    ArrayList<String> arrayList_key;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        listView = findViewById(R.id.list_view);
        progressBar = findViewById(R.id.progress);
        arrayList = new ArrayList<>();
        arrayList_key = new ArrayList<>();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),position+"",Toast.LENGTH_SHORT).show();
                Collections.reverse(arrayList_key);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("/Clipboard/" + arrayList_key.remove(position));
                myRef.removeValue();
                arrayAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    public void add_screen(View view) {
        startActivity(new Intent(getApplicationContext(), AddScreen.class));
//        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
//        Toast.makeText(getApplicationContext(),"Welcome",Toast.LENGTH_SHORT).show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("/Clipboard/");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
                arrayList.clear();
                arrayList_key.clear();
                for (DataSnapshot i : dataSnapshot.getChildren()) {
//                    Toast.makeText(getApplicationContext(),i.getValue()+"", Toast.LENGTH_SHORT).show();
                    arrayList.add(i.getValue() + "");
                    arrayList_key.add(i.getKey() + "");
                }
                Collections.reverse(arrayList);
                arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.list_item, arrayList);

                listView.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        progressBar.setVisibility(View.GONE);
        Log.d("Dataset", "" + arrayList_key);
    }
}