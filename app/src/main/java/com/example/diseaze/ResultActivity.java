package com.example.diseaze;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class ResultActivity extends AppCompatActivity {

    private TextView dtv;
    private TextView ctv;
    private TextView descriptionTextView;
    private TextView medicationTextView;
    private ImageButton chat;

    // HashMaps to store disease descriptions and medication information
    private HashMap<String, String> diseaseDescriptions = new HashMap<>();
    private HashMap<String, String> medicationInformation = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initializeDiseaseDescriptions();
        initializeMedicationInformation();

        String className = getIntent().getStringExtra("CLASS_NAME");
        String confidence = getIntent().getStringExtra("CONFIDENCE");

        dtv = findViewById(R.id.textViewDiseaseValue);
        ctv = findViewById(R.id.textViewConfidenceValue);
        descriptionTextView = findViewById(R.id.textView3);
        medicationTextView = findViewById(R.id.textViewMedicationValue);
        chat = findViewById(R.id.btnChatbot);

        dtv.setText(className);
        ctv.setText(confidence);

        // Get and set disease description
        String description = diseaseDescriptions.get(className);
        descriptionTextView.setText(description);

        // Get and set medication information
        String medicationInfo = medicationInformation.get(className);
        medicationTextView.setText(medicationInfo);

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiatechat();
            }
        });

    }

    private void initiatechat() {
        Intent intent = new Intent(this,MessageActivity.class);
        startActivity(intent);
    }

    // Initialize disease descriptions
    private void initializeDiseaseDescriptions() {
        diseaseDescriptions.put("Actinic keratosis", "A rough, scaly patch on the skin caused by years of sun exposure.\n" +
                "Actinic keratoses usually affects older adults. Reducing sun exposure can help reduce risk.\n" +
                "It is most common on the face, lips, ears, back of hands, forearms, scalp and neck. The rough, scaly skin patch enlarges slowly and usually causes no other signs or symptoms. A lesion may take years to develop.\n");
        diseaseDescriptions.put("Basal cell carcinoma", "A type of skin cancer that begins in the basal cells.\n" +
                "Basal cells produce new skin cells as old ones die. Limiting sun exposure can help prevent these cells from becoming cancerous.\n" +
                "This cancer typically appears as a white, waxy lump or a brown, scaly patch on sun-exposed areas, such as the face and neck.");
        diseaseDescriptions.put("Benign keratosis","A non-cancerous skin condition that appears as a waxy brown, black or tan growth.\n" +
                "A Benign keratosis is one of the most common non-cancerous skin growths in older adults. While it's possible for one to appear on its own, multiple growths are more common.\n" +
                "Benign keratosis often appears on the face, chest, shoulders or back. It has a waxy, scaly, slightly elevated appearance.");
        diseaseDescriptions.put("Dermatofibroma","Dermatofibroma is a commonly occurring cutaneous entity usually centered within the skin's dermis. Dermatofibromas are referred to as benign fibrous histiocytomas of the skin, superficial/cutaneous benign fibrous histiocytomas, or common fibrous histiocytoma. These mesenchymal cell lesions of the dermis clinically are firm subcutaneous nodules that occur on the extremities in the vast majority of cases and may or may not be associated with overlying skin changes. They are most commonly asymptomatic and usually relatively small, less than or equal to 1 centimeter in diameter. ");
        diseaseDescriptions.put("Melanocytic nevus","A usually non-cancerous disorder of pigment-producing skin cells commonly called birthmarks or moles.\n" +
                "This type of mole is often large and caused by a disorder involving melanocytes, cells that produce pigment (melanin).\n" +
                "Melanocytic nevi can be rough, flat, or raised. They can exist at birth or appear later. They can have thick hair on them. Rarely, melanocytic nevi can become cancerous.");
        diseaseDescriptions.put("Melanoma","The most serious type of skin cancer.\n" +
                "Melanoma occurs when the pigment-producing cells that give colour to the skin become cancerous.\n" +
                "Symptoms might include a new, unusual growth or a change in an existing mole. Melanomas can occur anywhere on the body.");
        diseaseDescriptions.put("Squamous cell carcinoma","Squamous cell carcinoma, the second most common form of skin cancer, is caused by the cumulative exposure of skin to UV light. This condition has precursor lesions called actinic keratosis, exhibits tumor progression and has the potential to metastasize in the body. Surgical excision is the preferred mode of treatment with Mohs micrographic surgery technique used for squamous cell carcinoma of the head and neck and in other areas of high risk or squamous cell carcinoma with high-risk characteristics. Radiation therapy is used in cases of squamous cell carcinoma in older patients or those who will not tolerate surgery, or when it has not been possible to obtain clear margins surgically.");
        diseaseDescriptions.put("Vascular lesion","Vascular lesions are abnormal growths or malformations in the blood vessels, which can occur in various parts of the body. They can be congenital or acquired and may result from injury, infection, or other underlying medical conditions. Vascular lesions can range from harmless to potentially life-threatening, depending on their location and severity.");
    }

    // Initialize medication information
    private void initializeMedicationInformation() {
        medicationInformation.put("Actinic keratosis", "Recommended medications for actinic keratosis include:\n1. Fluorouracil cream (Efudex)\n2. Imiquimod cream (Aldara, Zyclara)\n3. Diclofenac gel (Solaraze)");
        medicationInformation.put("Basal cell carcinoma", "Recommended medications for basal cell carcinoma include:\n1. Imiquimod cream (Aldara, Zyclara)\n2. 5-fluorouracil cream (Efudex, Fluoroplex)\n3. Photodynamic therapy (PDT)");
        medicationInformation.put("Benign keratosis", "No specific medication is required for benign keratosis. Treatment may involve observation, cryotherapy, or surgical removal for cosmetic reasons.");
        medicationInformation.put("Dermatofibroma", "No specific medication is required for dermatofibroma. Treatment is usually not necessary, but if desired, surgical excision can be performed for cosmetic reasons.");
        medicationInformation.put("Melanocytic nevus", "No specific medication is required for melanocytic nevus. Regular skin examinations are recommended to monitor changes in moles.");
        medicationInformation.put("Melanoma", "Recommended medications for melanoma include:\n1. Immunotherapy (e.g., pembrolizumab, nivolumab)\n2. Targeted therapy (e.g., vemurafenib, dabrafenib)\n3. Chemotherapy (e.g., dacarbazine, temozolomide)");
        medicationInformation.put("Squamous cell carcinoma", "Recommended medications for squamous cell carcinoma include:\n1. Surgical excision\n2. Mohs micrographic surgery\n3. Radiation therapy\n4. Chemotherapy (for advanced cases)");
        medicationInformation.put("Vascular lesion", "No specific medication is required for vascular lesions. Treatment options may include laser therapy, sclerotherapy, or surgical removal, depending on the type and severity of the lesion.");
    }
}
