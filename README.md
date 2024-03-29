# DisEaze

DisEaze is a mobile application designed to assist users in identifying various skin conditions through image classification. The app utilizes machine learning algorithms to analyze images uploaded by users and provides insights into potential skin diseases or conditions.

## Features

- **Image Selection**: Users can select images from their device gallery to upload for analysis.
- **Image Classification**: The uploaded images are sent to a server for classification using pre-trained machine learning models.
- **Result Display**: The app displays the classification result, including the predicted skin condition and confidence level.
- **User Authentication**: Secure user authentication ensures data privacy and personalized user experiences.
- **User Profile Management**: Users can manage their profiles, including personal information and preferences.
- **Feedback System**: Users can provide feedback on the app's performance or report issues.

## Dependencies

- **OkHttp**: Used for making network calls to communicate with the server.
- **Firebase**: Utilized for real-time database functionality and cloud storage.

## Getting Started

To get started with DisEaze, follow these steps:

1. **Clone the Repository**: Clone the DisEaze repository to your local machine using the following command:

    ```bash
    git clone https://github.com/JangamKarthik/DisEaze.git
    ```

2. **Open in Android Studio**: Open the project in Android Studio IDE.

3. **Configure API Endpoint**: Set up the API endpoint URL in the `HomeActivity.java` file to match your EC2 server's address.

4. **Set Up Firebase**: Configure Firebase by adding your Firebase project's configuration files (`google-services.json` for Android) to the project.

5. **Run the Application**: Build and run the application on an Android emulator or physical device.

6. **Test with Images**: Test the application by selecting images from the device gallery and observing the classification.

7. **Provide Feedback**: Use the feedback feature to share your thoughts or report any issues encountered while using the app.

8. **API deployment on Amazon's EC2 Instance**: The detailed steps for the instance deployment and the required API is in the following repo

  ```bash
  https://github.com/JangamKarthik/APIS
  ```
follow the steps in the repo's readme.md and deploy-flask-on-aws-ec2.md
