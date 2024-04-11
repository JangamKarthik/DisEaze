package com.example.diseaze;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private EditText commentEditText, nameEditText, emailEditText, mobileEditText;
    private Button submitButton;
    private String userId;
    private DatabaseReference feedbacksRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        ratingBar = findViewById(R.id.ratingBar);
        commentEditText = findViewById(R.id.commentEditText);
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        mobileEditText = findViewById(R.id.mobileEditText);
        submitButton = findViewById(R.id.submitButton);
        userId = getIntent().getStringExtra("USER_ID");
        submitButton.setBackgroundColor(Color.parseColor("#008080"));
        submitButton.setTextColor(Color.parseColor("#FFFFFF"));

        feedbacksRef = FirebaseDatabase.getInstance().getReference().child("feedbacks");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitFeedback();
            }
        });
    }

    private void submitFeedback() {
        float rating = ratingBar.getRating();
        String comment = commentEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String mobile = mobileEditText.getText().toString().trim();

        if (TextUtils.isEmpty(comment) || TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(mobile)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> feedbackMap = new HashMap<>();
        feedbackMap.put("rating", rating);
        feedbackMap.put("comment", comment);
        feedbackMap.put("name", name);
        feedbackMap.put("email", email);
        feedbackMap.put("mobile", mobile);

        feedbacksRef.child(userId).push().setValue(feedbackMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(FeedbackActivity.this, "Feedback submitted successfully", Toast.LENGTH_SHORT).show();
                        // Clear input fields after successful submission
                        clearFields();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FeedbackActivity.this, "Failed to submit feedback. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearFields() {
        ratingBar.setRating(0);
        commentEditText.setText("");
        nameEditText.setText("");
        emailEditText.setText("");
        mobileEditText.setText("");
    }
}
