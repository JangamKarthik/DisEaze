package com.example.diseaze;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MessageActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView welcomeTextView;
    EditText messageEditText;
    ImageButton sendButton;
    List<Message> messageList;
    MessageAdapter messageAdapter;
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        messageList = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view);
        welcomeTextView = findViewById(R.id.welcome_text);
        messageEditText = findViewById(R.id.message_edit_text);
        sendButton = findViewById(R.id.send_btn);

        //setup recycler view
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

        sendButton.setOnClickListener((v)->{
            String question = messageEditText.getText().toString().trim();
            if (!question.isEmpty()) {
                addToChat(question, Message.SENT_BY_ME); // Add this line
                messageEditText.setText("");
                callAPI(question);
                welcomeTextView.setVisibility(View.GONE);
            } else {
                Toast.makeText(MessageActivity.this, "Please enter a message", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void addToChat(String message, String sentBy){
        runOnUiThread(() -> {
            messageList.add(new Message(message, sentBy));
            messageAdapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
        });
    }

    void addResponse(String response){
        messageList.remove(messageList.size()-1);
        addToChat(response, Message.SENT_BY_BOT);
    }

    void callAPI(String question){
        // Add user's message to the chat
        addToChat(question, Message.SENT_BY_ME);

        // Hardcoded responses based on the user's input
        String response;
        switch (question.toLowerCase()) {
            case "hello":
            case "hi":
            case "hey":
                response = "Hi there!";
                break;
            case "what skin diseases can your app classify?":
            case "what diseases does your app identify?":
            case "which skin conditions can your app detect?":
                response = "Our app can classify various skin diseases including actinic keratosis, basal cell carcinoma, benign keratosis, dermatofibroma, melanocytic nevus, melanoma, squamous cell carcinoma, and vascular lesion.";
                break;
            case "how does your app classify skin diseases?":
            case "what technology does your app use to identify diseases?":
            case "what method does your app employ for disease classification?":
                response = "Our app uses deep learning technology to classify skin diseases. It utilizes a pre-trained model to analyze images of skin lesions and determine the most likely disease based on patterns and features.";
                break;
            case "how accurate is your app's classification?":
            case "what is the accuracy rate of your app's disease identification?":
            case "how reliable is your app's disease classification?":
                response = "Our app has been tested for accuracy and has shown high reliability in identifying skin diseases. However, it's important to consult with a medical professional for confirmation and further diagnosis.";
                break;
            case "how can I submit feedback about the app?":
            case "where can I provide feedback for your app?":
            case "how do I give feedback for your app?":
                response = "You can submit feedback about our app through the Feedback section in the app's menu. We appreciate your input!";
                break;
            case "what are the recommended medications for skin diseases?":
            case "how can I treat skin conditions?":
            case "what medications should I use for my skin disease?":
                response = "The recommended medications for skin diseases vary depending on the specific condition. It's best to consult with a dermatologist who can prescribe appropriate treatments tailored to your needs.";
                break;
            case "what are the symptoms of melanoma?":
            case "how can I recognize melanoma?":
            case "what are the signs of melanoma?":
                response = "Melanoma symptoms include changes in the shape, color, or size of moles, the appearance of new moles, irregular borders, asymmetry, and itching or bleeding of moles.";
                break;
            case "how can I protect my skin from sun damage?":
            case "what are some sun protection tips?":
            case "how do I prevent sunburn?":
                response = "To protect your skin from sun damage, it's important to wear sunscreen with SPF 30 or higher, seek shade during peak sun hours, wear protective clothing, and avoid indoor tanning.";
                break;
            case "where can I find more information about skin diseases?":
            case "how can I learn more about skin conditions?":
            case "what resources are available for skin disease information?":
                response = "You can find more information about skin diseases from reputable sources such as the American Academy of Dermatology, the Mayo Clinic, and the National Institutes of Health.";
                break;
            case "what is the best way to perform a skin self-exam?":
            case "how do I check my skin for abnormalities?":
            case "what are the steps for a skin self-check?":
                response = "To perform a skin self-exam, examine your skin from head to toe, looking for any new or changing moles, growths, or other abnormalities. Use a mirror to check hard-to-see areas.";
                break;
            case "what are the risk factors for developing skin cancer?":
            case "how can I reduce my risk of skin cancer?":
            case "what factors increase the chances of getting skin cancer?":
                response = "Risk factors for skin cancer include prolonged sun exposure, tanning bed use, fair skin, a history of sunburns, family history of skin cancer, and certain medical conditions or medications.";
                break;
            case "what should I do if I notice changes in my skin?":
            case "how should I respond to skin abnormalities?":
            case "what actions should I take if I detect changes in my skin?":
                response = "If you notice changes in your skin, such as new or changing moles, unusual growths, or persistent itching or bleeding, it's important to consult with a dermatologist for evaluation and possible biopsy.";
                break;
            case "how can I maintain healthy skin?":
            case "what are some tips for keeping my skin healthy?":
            case "how do I care for my skin?":
                response = "To maintain healthy skin, practice good sun protection habits, moisturize regularly, eat a balanced diet rich in fruits and vegetables, stay hydrated, avoid smoking, and manage stress levels.";
                break;
            case "what is the prognosis for melanoma?":
            case "how serious is melanoma?":
            case "what is the outlook for melanoma patients?":
                response = "The prognosis for melanoma depends on various factors including the stage of the cancer, the depth of the tumor, and whether it has spread to other parts of the body. Early detection and treatment offer the best chance of a favorable outcome.";
                break;
            case "what are the different types of skin cancer?":
            case "how many types of skin cancer are there?":
            case "what are the classifications of skin cancer?":
                response = "The main types of skin cancer are basal cell carcinoma, squamous cell carcinoma, and melanoma. Other less common types include Merkel cell carcinoma, dermatofibrosarcoma protuberans, and cutaneous lymphoma.";
                break;
            case "what are the warning signs of skin cancer?":
            case "how can I recognize the early signs of skin cancer?":
            case "what symptoms should I watch out for in skin cancer?":
                response = "Warning signs of skin cancer include changes in the size, shape, color, or texture of moles or other skin lesions, the appearance of new growths, and persistent itching, tenderness, or bleeding.";
                break;
            case "what factors contribute to the development of actinic keratosis?":
            case "how does actinic keratosis form?":
            case "what causes actinic keratosis to develop?":
                response = "Actinic keratosis develops due to prolonged exposure to ultraviolet (UV) radiation from sunlight or artificial sources such as tanning beds. Fair-skinned individuals and those with a history of sunburns are at higher risk.";
                break;
            case "how common is basal cell carcinoma?":
            case "what is the prevalence of basal cell carcinoma?":
            case "how frequently does basal cell carcinoma occur?":
                response = "Basal cell carcinoma is the most common type of skin cancer, accounting for about 80% of all skin cancer cases. It is more prevalent in fair-skinned individuals and those with a history of sun exposure.";
                break;
            case "what are the treatment options for squamous cell carcinoma?":
            case "how is squamous cell carcinoma treated?":
            case "what are the available therapies for squamous cell carcinoma?":
                response = "Treatment options for squamous cell carcinoma include surgical excision, Mohs micrographic surgery, radiation therapy, topical medications, cryotherapy, and photodynamic therapy. The choice of treatment depends on the size, location, and stage of the cancer.";
                break;
            case "how does dermatofibroma differ from other skin lesions?":
            case "what distinguishes dermatofibroma from other skin growths?":
            case "how is dermatofibroma unique compared to other skin conditions?":
                response = "Dermatofibroma is a benign skin lesion characterized by a firm, raised nodule that often has a dimpled or puckered appearance. Unlike other skin growths, dermatofibroma typically does not change significantly over time and is not associated with cancer.";
                break;
            case "what is the role of genetics in the development of skin diseases?":
            case "how does genetics influence the occurrence of skin conditions?":
            case "what genetic factors contribute to skin disease susceptibility?":
                response = "Genetic factors play a role in the development of some skin diseases, such as melanoma and certain types of ichthyosis. Mutations in specific genes can increase susceptibility to these conditions or affect their severity.";
                break;
            case "what are the risk factors for developing actinic keratosis?":
            case "how can I reduce my risk of actinic keratosis?":
            case "what factors increase the chances of getting actinic keratosis?":
                response = "Risk factors for actinic keratosis include prolonged sun exposure, fair skin, a history of sunburns, older age, male gender, and living in sunny or high-altitude areas. Sun protection measures can help reduce the risk.";
                break;
            case "what are the potential complications of melanoma?":
            case "what problems can arise from melanoma?":
            case "what are the adverse outcomes associated with melanoma?":
                response = "Complications of melanoma can include metastasis to other organs, recurrence of the cancer, and the development of secondary skin cancers. Early detection and treatment are crucial for preventing these complications.";
                break;
            case "what is the best treatment for benign keratosis?":
            case "how should I treat benign keratosis?":
            case "what is the recommended therapy for benign keratosis?":
                response = "Treatment for benign keratosis depends on the specific lesion and may include cryotherapy, topical medications, laser therapy, or surgical removal. A dermatologist can recommend the most appropriate treatment.";
                break;
            case "what are the potential side effects of skin cancer treatments?":
            case "what adverse effects can occur from skin cancer therapy?":
            case "what are the risks associated with skin cancer treatments?":
                response = "Common side effects of skin cancer treatments may include pain, swelling, redness, blistering, itching, and scarring. More serious complications such as infection or nerve damage are rare but possible.";
                break;
            case "how does melanocytic nevus differ from melanoma?":
            case "what distinguishes melanocytic nevus from melanoma?":
            case "how is melanocytic nevus different from melanoma?":
                response = "Melanocytic nevus, commonly known as a mole, is a benign skin growth composed of melanocytes, whereas melanoma is a malignant skin cancer arising from melanocytes. While moles are usually harmless, melanoma can be life-threatening if not detected and treated early.";
                break;
            case "what are the treatment options for melanoma?":
            case "how is melanoma treated?":
            case "what therapies are available for melanoma?":
                response = "Treatment options for melanoma include surgical excision, immunotherapy, targeted therapy, radiation therapy, chemotherapy, and clinical trials. The choice of treatment depends on the stage of the cancer, its location, and other factors.";
                break;
            case "what are the potential complications of actinic keratosis?":
            case "what problems can arise from actinic keratosis?":
            case "what are the adverse outcomes associated with actinic keratosis?":
                response = "Complications of actinic keratosis can include the development of squamous cell carcinoma, the spread of the lesions, and cosmetic concerns due to scarring or disfigurement. Early treatment can help prevent these complications.";
                break;
            case "what is the difference between basal cell carcinoma and squamous cell carcinoma?":
            case "how do basal cell carcinoma and squamous cell carcinoma differ?":
            case "what sets basal cell carcinoma apart from squamous cell carcinoma?":
                response = "Basal cell carcinoma arises from basal cells in the epidermis and typically appears as a shiny, pearly bump or a pinkish patch, while squamous cell carcinoma originates from squamous cells and often presents as a red, scaly lesion or a firm, elevated nodule.";
                break;
            case "what are the risk factors for developing benign keratosis?":
            case "how can I reduce my risk of benign keratosis?":
            case "what factors increase the chances of getting benign keratosis?":
                response = "Risk factors for benign keratosis include prolonged sun exposure, fair skin, a history of sunburns, older age, male gender, and living in sunny or high-altitude areas. Sun protection measures can help reduce the risk.";
                break;
            case "what are the potential complications of squamous cell carcinoma?":
            case "what problems can arise from squamous cell carcinoma?":
            case "what are the adverse outcomes associated with squamous cell carcinoma?":
                response = "Complications of squamous cell carcinoma can include metastasis to other organs, recurrence of the cancer, and the development of secondary skin cancers. Early detection and treatment are essential for preventing these complications.";
                break;
            case "what is the role of UV radiation in the development of skin cancer?":
            case "how does UV radiation contribute to skin cancer?":
            case "what effect does UV radiation have on skin cancer risk?":
                response = "UV radiation from sunlight or artificial sources such as tanning beds can damage the DNA in skin cells, leading to mutations that can cause cancer. Prolonged exposure to UV radiation increases the risk of developing skin cancer.";
                break;
            case "what are the treatment options for vascular lesions?":
            case "how are vascular lesions treated?":
            case "what therapies are available for vascular lesions?":
                response = "Treatment options for vascular lesions depend on the type, size, and location of the lesion. They may include laser therapy, sclerotherapy, topical medications, or surgical removal. A dermatologist can recommend the most appropriate treatment for your specific condition.";
                break;
            case "what are the potential complications of dermatofibroma?":
            case "what problems can arise from dermatofibroma?":
            case "what are the adverse outcomes associated with dermatofibroma?":
                response = "Complications of dermatofibroma are rare but may include pain, itching, bleeding, infection, scarring, or cosmetic concerns due to the appearance of the lesion. Most dermatofibromas are benign and do not require treatment unless they cause symptoms or cosmetic issues.";
                break;
            default:
                response = "Sorry, I'm not sure how to respond to that.";
                break;
        }

        addResponse(response);
    }


}
