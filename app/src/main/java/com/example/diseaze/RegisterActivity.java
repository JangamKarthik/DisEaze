package com.example.diseaze;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName, etNumber, etEmail, etPassword;
    private RadioGroup radioGroupGender;
    private Button btnRegister;
    private Button btnlogin;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase components
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Initialize views
        etName = findViewById(R.id.etName);
        etNumber = findViewById(R.id.etNumber);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        btnRegister = findViewById(R.id.btnRegister);
        btnlogin = findViewById(R.id.btnLoginInstead);
        btnRegister.setBackgroundColor(Color.parseColor("#008080"));
        btnlogin.setBackgroundColor(Color.parseColor("#008080"));
        btnRegister.setTextColor(Color.parseColor("#FFFFFF"));
        btnlogin.setTextColor(Color.parseColor("#FFFFFF"));
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate and register user
                if (validateFields()) {
                    registerUser();
                }
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginInsteadClick();
            }
        });
    }

    private boolean validateFields() {
        // Check if all fields are filled
        if (TextUtils.isEmpty(etName.getText().toString())
                || TextUtils.isEmpty(etNumber.getText().toString())
                || TextUtils.isEmpty(etEmail.getText().toString())
                || radioGroupGender.getCheckedRadioButtonId() == -1
                || TextUtils.isEmpty(etPassword.getText().toString())) {
            showToast("All fields must be filled");
            return false;
        }

        // Perform basic email validation
        if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
            showToast("Invalid email address");
            return false;
        }

        // Validate phone number format
        String phoneNumber = etNumber.getText().toString().trim();
        if (!TextUtils.isDigitsOnly(phoneNumber) || phoneNumber.length() != 10) {
            showToast("Invalid phone number");
            return false;
        }

        // Validate password complexity
        String password = etPassword.getText().toString();
        if (!isValidPassword(password)) {
            showToast("Invalid password. Password should be at least 8 characters and include one uppercase, one digit, one special character, and one lowercase.");
            return false;
        }

        return true;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void registerUser() {
        // Get user input
        String name = etName.getText().toString().trim();
        String number = etNumber.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();

        // Get selected gender from RadioGroup
        int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
        RadioButton selectedGenderRadioButton = findViewById(selectedGenderId);
        String gender = selectedGenderRadioButton.getText().toString().trim();

        String password = etPassword.getText().toString();
        String userId = UUID.randomUUID().toString();
        // Create user with email and password
       User user = new User(userId,name,number,email,gender,pass);
        databaseReference.child(userId).setValue(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Registration successful, show a success message
                        showToast("Registration successful");

                        navigateToHomeActivity(userId, name,email, gender, number);
                    } else {
                        // Registration failed, show an error message
                        showToast("Registration failed: " + task.getException().getMessage());
                    }
                });
    }
    private boolean isValidPassword(String password) {
        // Implement password complexity validation logic
        // Example: Password should be at least 8 characters and include one uppercase, one digit, one special character, and one lowercase.
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        return password.matches(passwordRegex);
    }
    public void onLoginInsteadClick() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToHomeActivity(String userId, String userName, String userMail, String userGender, String userPhone) {
        Intent intent = new Intent(this, HomeActivity.class);
        // Pass user information to the next activity
        intent.putExtra("keyUserID", userId);
        intent.putExtra("keyString", userName);
        intent.putExtra("keyUserEmail",userMail);
        intent.putExtra("keyUserGender",userGender);
        intent.putExtra("keyUserPhone",userPhone);
        startActivity(intent);
        finish();
    }
}



