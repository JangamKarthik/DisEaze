package com.example.diseaze;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class HomeActivity extends AppCompatActivity {
    private TextView tv1;
    private Button imgbtn;
    private ImageView ipv;
    private Button btnsubmit;
    private Button btnUserProfile;
    private Bitmap selectedImageBitmap;
    private String userId;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String userGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tv1 = findViewById(R.id.tvUserName);
        imgbtn = findViewById(R.id.btnSelectImage);
        ipv = findViewById(R.id.IVPreviewImage);
        btnsubmit = findViewById(R.id.btnSubmit);
        btnUserProfile = findViewById(R.id.btnGoToUserProfile);

        userId = getIntent().getStringExtra("keyUserID");
        userName = getIntent().getStringExtra("keyString");
        userPhone = getIntent().getStringExtra("keyUserPhone");
        userEmail = getIntent().getStringExtra("keyUserEmail");
        userGender = getIntent().getStringExtra("keyUserGender");
        tv1.setText("Hey " + userName);

        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        btnUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToUserProfileActivity();
            }
        });
    }

    private void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(i);
    }

    private void uploadImage() {
        if (selectedImageBitmap != null) {
            // Convert the selected bitmap to a byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            selectedImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageData = baos.toByteArray();

            // Your API endpoint
            String apiUrl = "http://127.0.0.1:5000/";

            callApiForClassification(apiUrl, imageData);
        } else {
            showToast("No image selected");
        }
    }

    private void callApiForClassification(String apiUrl, byte[] imageData) {
        JSONObject jsonBody = new JSONObject();
        try {
            // Put your image data in the JSON body
            // Here, assuming your API accepts 'image' as the key for image data
            jsonBody.put("image", imageData);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, apiUrl, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Extract class name and confidence level from API response
                            String className = response.getString("class");
                            double confidence = response.getDouble("confidence");
                            String clevel = String.valueOf(confidence);
                            // Start ResultActivity and pass class name and confidence level
                            Intent intent = new Intent(HomeActivity.this, ResultActivity.class);
                            intent.putExtra("CLASS_NAME", className);
                            intent.putExtra("CONFIDENCE", clevel);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            showToast("Error parsing API response");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showToast("Error calling API");
                    }
                });

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    private void navigateToUserProfileActivity() {
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra("USER_ID", userId);
        intent.putExtra("USER_NAME", userName);
        intent.putExtra("USER_EMAIL", userEmail);
        intent.putExtra("USER_PHONE", userPhone);
        intent.putExtra("USER_GENDER", userGender);
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        try {
                            selectedImageBitmap = MediaStore.Images.Media.getBitmap(
                                    getContentResolver(),
                                    selectedImageUri);
                            ipv.setImageBitmap(selectedImageBitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
}
