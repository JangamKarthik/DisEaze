package com.example.diseaze;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;


public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private String loggedUserId;
    private String loggedUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
    }

    public void onLoginClick(View view) {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean userFound = false;

                // Loop through all users
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);

                    // Check if entered email and password match any user in the database
                    if (user != null && user.getEmail().equals(email) && user.getPassword().equals(password)) {
                        userFound = true;
                        loggedUserId = user.getUserId();
                        loggedUserName = user.getName();
                        break;
                    }
                }

                if (userFound) {
                    // User found, proceed to the next activity or perform further actions
                    showToast("Login successful");
                    toHome();
                } else {
                    // User not found or password incorrect
                    showToast("Login failed: User not found or password incorrect");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error if needed
                showToast("Error fetching user data: " + databaseError.getMessage());
            }
        });
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void toHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("keyString", loggedUserName);
        startActivity(intent);
        finish();
    }
}
