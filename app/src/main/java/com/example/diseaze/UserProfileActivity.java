package com.example.diseaze;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {

    private Button btn;
    private RecyclerView recyclerView;
    private List<ScanResult> scanResultList;
    private ScanResultAdapter adapter;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        TextView tvUserName = findViewById(R.id.tvUserName);
        TextView tvUserId = findViewById(R.id.tvUserId);
        TextView tvUserGender = findViewById(R.id.tvUserGender);
        TextView tvUserMail = findViewById(R.id.tvUserMail);
        TextView tvUserPhone = findViewById(R.id.tvUserPhone);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userId = getIntent().getStringExtra("USER_ID");
        String userName = getIntent().getStringExtra("USER_NAME");
        String userPhone = getIntent().getStringExtra("USER_PHONE");
        String userMail = getIntent().getStringExtra("USER_EMAIL");
        String userGender = getIntent().getStringExtra("USER_GENDER");

        tvUserName.setText("Name: " + userName);
        tvUserId.setText("User ID: " + userId);
        tvUserGender.setText("Gender: " + userGender);
        tvUserMail.setText("Email: " + userMail);
        tvUserPhone.setText("Phone: " + userPhone);

        btn = findViewById(R.id.button3);
        btn.setBackgroundColor(Color.parseColor("#008080"));
        btn.setTextColor(Color.parseColor("#FFFFFF"));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFeedback();
            }
        });

        scanResultList = new ArrayList<>();
        adapter = new ScanResultAdapter(this, scanResultList);
        recyclerView.setAdapter(adapter);

        // Fetch scan results from database
        fetchScanResults(userId);
    }

    private void fetchScanResults(String userId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("history").child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                scanResultList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ScanResult scanResult = snapshot.getValue(ScanResult.class);
                    scanResultList.add(scanResult);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }

    private void goToFeedback() {
        Intent intent = new Intent(this, FeedbackActivity.class);
        intent.putExtra("USER_ID",userId);
        startActivity(intent);
    }
}
