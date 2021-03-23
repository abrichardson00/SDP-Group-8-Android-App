package com.example.theostore;

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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_main);

        // set up the voice recognition things ----------------------------------------------
        // this might be a really ugly place to put it idk

        checkPermission();

        //final EditText editText = findViewById(R.id.editText);
        final SpeechRecognizer mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);


        final Intent mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault());
        // TRY GETTING OTHER PREDICTED TEXTS WITH LOWER CONFIDENCE - IF SOME LOWER CONFIDENCE TEXTS HAVE 'BRING' ETC IN THEM, USE THEM INSTEAD?
        //System.out.println(Locale.getDefault());

        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            // we have to define these apparently ...
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }
            @Override
            public void onResults(Bundle bundle) {
                System.out.println("reee!");
                //getting all the matches
                ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                System.out.println(matches.toString());
                System.out.println(matches.get(0));
                //displaying the first match
                //if (matches != null) editText.setText(matches.get(0));

                if (matches == null) return;
                //System.out.println(matches.get(0));
                Command command = VoiceInputParser.parseInputText(matches.get(0));
                if (command.type == CommandType.BRING) {
                    // do thing, new Intent() or whatever
                    // we have the integer command.tray
                    System.out.println("bring tray " +  String.valueOf(command.tray));
                } else if (command.type == CommandType.BRING_ANY) {
                    // do thing!
                    System.out.println("bring random tray");
                } else if (command.type == CommandType.STORE) {
                    // do thing!
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
            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        Button voiceButton = findViewById(R.id.Top4);
        findViewById(R.id.Top4).setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // any animation / view thing when the voice button is pressed could be done here
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        mSpeechRecognizer.stopListening();
                        //editText.setHint("You will see input here");
                        break;

                    case MotionEvent.ACTION_DOWN:
                        System.out.println("down");
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        //editText.setText("");
                        //editText.setHint("Listening...");
                        break;
                }
                return false;
            }

        });

    }

    // this is for checking permissions to allow the app to access the microphone
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                finish();
            }
        }
    }



    public void browseTrays(View view)
    {
        Intent intent = new Intent(this, RecyclerActivity.class);
        startActivity(intent);
    }

    public void help(View view)
    {
        Intent intent = new Intent(this, HelpSupport.class);
        startActivity(intent);
    }

    public void logout(View view)
    {
        mFirebaseAuth.signOut();
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

}