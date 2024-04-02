package com.example.diseaze;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
            // Your API endpoint
            String apiUrl = "http://3.110.196.103/";

            callApiForClassification(apiUrl, selectedImageBitmap);
        } else {
            showToast("No image selected");
        }
    }

    private void callApiForClassification(String apiUrl, Bitmap imageBitmap) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();

                // Creating MultipartBody for sending form data
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("image", "image.jpg", createPartFromFile(imageBitmap))
                        .build();

                // Creating request
                Request request = new Request.Builder()
                        .url(apiUrl)
                        .post(requestBody)
                        .build();

                // Executing request and handling response
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    // Handle response if call was successfull
                    String responseData = response.body().string();
                    Log.d("API_RESPONSE", responseData);
                    JSONObject jsonResponse = new JSONObject(responseData);
                    String className = jsonResponse.getString("class");
                    double confidence = jsonResponse.getDouble("Probability");
                    double truncatedConfidence = Math.round(confidence * 100.0) / 100.0;


                    // Starting ResultActivity and passing class name and confidence level
                    Intent intent = new Intent(HomeActivity.this, ResultActivity.class);
                    intent.putExtra("CLASS_NAME", className);
                    intent.putExtra("CONFIDENCE", String.valueOf(truncatedConfidence));
                    startActivity(intent);
                } else {
                    // Handling error response
                    showToast("Error response from server: " + response.code());
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                runOnUiThread(() -> showToastOnUiThread("Error calling API"));
            }
        }).start();
    }

    private RequestBody createPartFromFile(Bitmap imageBitmap) {
        // Converting Bitmap to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageData = baos.toByteArray();

        // Creating RequestBody from byte array
        return RequestBody.create(MediaType.parse("image/jpeg"), imageData);
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

    private void showToastOnUiThread(String message) {
        runOnUiThread(() -> showToast(message));
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
