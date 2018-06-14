package org.tensorflow.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Camera;
import android.media.ImageReader;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.widget.Toast;

import org.tensorflow.demo.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SelectLocationActivity extends Activity implements  TextToSpeech.OnInitListener {

    private TextToSpeech tts;

    // This callback is invoked when the Speech Recognizer returns.
// This is where you process the intent and extract the speech text from the intent.
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            Toast toast = Toast.makeText(getApplicationContext(), "Ok, remember that you can always call for 'Be My Eyes'", Toast.LENGTH_LONG);
            toast.show();
            tts.speak("Ok, remember that you can always call for 'Be My Eyes'", TextToSpeech.QUEUE_FLUSH, null, "");


            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            tts.speak("You want to go to "+ spokenText +" Is this correct?", TextToSpeech.QUEUE_FLUSH, null, "");

            switchActivityOnClick dialogClickListener = new switchActivityOnClick(this);
            AlertDialog.Builder builder = new AlertDialog.Builder(SelectLocationActivity.this);
            builder.setMessage("You want to go to "+ spokenText +" Is this correct?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private static final int SPEECH_REQUEST_CODE = 0;
    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer() {
        tts.speak("Hello, where do you want to go today?", TextToSpeech.QUEUE_FLUSH, null, "");

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
// Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        tts = new TextToSpeech(getApplicationContext(), this);

        displaySpeechRecognizer();
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.UK);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                tts.speak("Welcome", TextToSpeech.QUEUE_FLUSH, null, "");
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }


}
