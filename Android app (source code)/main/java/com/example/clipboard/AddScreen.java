package com.example.clipboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddScreen extends AppCompatActivity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_screen);
        editText = findViewById(R.id.edit_data);
    }

    public void add_data(View view) {
        if (TextUtils.isEmpty(editText.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Edittext cant be empty", Toast.LENGTH_SHORT).show();
        } else {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("/Clipboard/");
            myRef.push().setValue(editText.getText().toString()+"");
            Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
        }

    }
}