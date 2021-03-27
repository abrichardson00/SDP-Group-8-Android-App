package com.example.theostore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private TrayDatabase trayDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        trayDatabase = new TrayDatabase(this);

        setContentView(R.layout.activity_main);
    }
        // set up the voice recognition things ----------------------------------------------
        // this might be a really ugly place to put it idk

//        checkPermission();

        //final EditText editText = findViewById(R.id.editText);
//        final SpeechRecognizer mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);


        // TRY GETTING OTHER PREDICTED TEXTS WITH LOWER CONFIDENCE - IF SOME LOWER CONFIDENCE TEXTS HAVE 'BRING' ETC IN THEM, USE THEM INSTEAD?
        //System.out.println(Locale.getDefault());

//        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            // we have to define these apparently ...
//            @Override
//            public void onReadyForSpeech(Bundle bundle) {
//
//            }
//
//            @Override
//            public void onBeginningOfSpeech() {
//
//            }
//
//            @Override
//            public void onRmsChanged(float v) {
//
//            }
//
//            @Override
//            public void onBufferReceived(byte[] bytes) {
//
//            }
//
//            @Override
//            public void onEndOfSpeech() {
//
//            }
//
//            @Override
//            public void onError(int i) {
//
//            }
//            @Override

//            @Override
//            public void onPartialResults(Bundle bundle) {
//
//            }
//
//            @Override
//            public void onEvent(int i, Bundle bundle) {
//
//            }
//        });
//
//        Button voiceButton = findViewById(R.id.Top4);
//        findViewById(R.id.Top4).setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                // any animation / view thing when the voice button is pressed could be done here
//                switch (motionEvent.getAction()) {
//                    case MotionEvent.ACTION_UP:
//                        mSpeechRecognizer.stopListening();
//                        //editText.setHint("You will see input here");
//                        break;
//
//                    case MotionEvent.ACTION_DOWN:
//                        System.out.println("down");
//                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
//                        //editText.setText("");
//                        //editText.setHint("Listening...");
//                        break;
//                }
//                return false;
//            }
//
//        });
//
//    }

    // this is for checking permissions to allow the app to access the microphone
//    private void checkPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
//                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
//                startActivity(intent);
//                finish();
//            }
//        }
//    }


    public void executeVoiceCommand(Command command) {

        System.out.println("trying to execute voice command");

        if (command.type == CommandType.BRING) {
            System.out.println("bring tray " +  String.valueOf(command.tray));


            Tray targetTray = trayDatabase.getTray(command.tray);

            System.out.println(targetTray);

            if (targetTray != null && targetTray.getStatus().equals("STORED")) {
                Intent intent = new Intent(this, BringingRandom.class);
                intent.putExtra("TRAY", targetTray);
                startActivity(intent);
            } else {
                System.out.println("Failed");
            }



        } else if (command.type == CommandType.BRING_ANY) {
            List<Tray> emptyTrays = trayDatabase.getEmptyTrays();
            if (!emptyTrays.isEmpty()){
                Intent intent = new Intent(this, BringingRandom.class);
                intent.putExtra("TRAY", emptyTrays.get(0));
                startActivity(intent);
            }
            System.out.println("bring random tray");
        } else if (command.type == CommandType.STORE) {
            Intent intent = new Intent(this, StoringTray.class);
            startActivity(intent);
            System.out.println("storing the tray");
        } else if (command.type == CommandType.FIND_ITEM) {
            // do thing
            // we have command.searchWords
            System.out.println("finding tray for the search: " + command.searchWords.toString());
        } else if (command.type == CommandType.ERROR){
            // do thing
            System.out.println("Error - command not understood");
        } else {
            // something went super wrong, not even identified as an 'ERROR'
            System.out.println("Error - no command");
        }
    }

    public void getVoiceInput(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.UK);
//        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "extra");
//        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE,true);


        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            System.out.println("Fuck you");
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {

                    System.out.println("got here");

                    List<String> userInput = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Command parsedCommand = VoiceInputParser.parseInputText(userInput.get(0));
                    executeVoiceCommand(parsedCommand);


                }
        }



    }

    public void bringEmpty(View view) {
        List<Tray> emptyTrays = trayDatabase.getEmptyTrays();
        if (!emptyTrays.isEmpty()){
            Intent intent = new Intent(this, BringingRandom.class);
            intent.putExtra("TRAY", emptyTrays.get(0));
            startActivity(intent);
        }
    }

    public void storeTray(View view) {
        Intent intent = new Intent(this, StoringTray.class);
        intent.putExtra("TRAY", new Tray());
        startActivity(intent);
    }

    public void browseTrays(View view)
    {
        Intent intent = new Intent(this, RecyclerActivity.class);
        view.performHapticFeedback(HapticFeedbackConstants.CONFIRM);
        startActivity(intent);
    }

    public void help(View view)
    {
//        Intent intent = new Intent(this, HelpSupport.class);
//        startActivity(intent);
        view.setEnabled(false);
        Button button = (Button) view;
        button.setText("fuck you");
    }

    public void logout(View view)
    {
        mFirebaseAuth.signOut();
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        trayDatabase.close();
//    }
}