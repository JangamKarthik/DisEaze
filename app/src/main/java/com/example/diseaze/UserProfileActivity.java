package com.example.diseaze;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import android.widget.Button;

public class UserProfileActivity extends AppCompatActivity {

    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        TextView tvUserName = findViewById(R.id.tvUserName);
        TextView tvUserId = findViewById(R.id.tvUserId);
        TextView tvUserGender = findViewById(R.id.tvUserGender);
        TextView tvUserMail = findViewById(R.id.tvUserMail);
        TextView tvUserPhone = findViewById(R.id.tvUserPhone);
        String UserId = getIntent().getStringExtra("USER_ID");
        String UserName = getIntent().getStringExtra("USER_NAME");
        String UserPhone = getIntent().getStringExtra("USER_PHONE");
        String UserMail = getIntent().getStringExtra("USER_EMAIL");
        String UserGender = getIntent().getStringExtra("USER_GENDER");
        tvUserName.setText("Name: "+UserName);
        tvUserId.setText("User ID: "+UserId);
        tvUserGender.setText("Gender: "+UserGender);
        tvUserMail.setText("Email: "+UserMail);
        tvUserPhone.setText("Phone: "+UserPhone);
        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotofeedback();
            }
        });
    }

    private void gotofeedback() {
        Intent intent =new Intent(this, FeedbackActivity.class);
        startActivity(intent);
    }

}