package edu.rose_hulman.greenewp.motorheadwatsongroup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends Activity implements View.OnClickListener, TextToSpeech.OnInitListener {

    private SpeechRecognizer sr;
    private EditText mText;
    private static final String TAG = "MyStt3Activity";
    private Button askButton;
    private Button readButton;
    private TextToSpeech speaker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (EditText) findViewById(R.id.Question_Text);
        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new listener());
        askButton = (Button) findViewById(R.id.Ask_Button);
        askButton.setOnClickListener(this);
        readButton = (Button) findViewById(R.id.Read_Button);
        readButton.setOnClickListener(this);
        speaker = new TextToSpeech(this,this);
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            int result = speaker.setLanguage(Locale.US);
            if(result ==TextToSpeech.LANG_MISSING_DATA ||
                result ==TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("error", "This Language is not supported");
                }
            else{
                convertTextToSpeech();
            }
        }
    }

    private void convertTextToSpeech(){
        String text = mText.getText().toString();
        if(text ==null || "".equals(text)){
            text = "Content not available";
        }
        else{
            speaker.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    class listener implements RecognitionListener
    {
        public void onReadyForSpeech(Bundle params)
        {
            Log.d(TAG, "onReadyForSpeech");
        }
        public void onBeginningOfSpeech()
        {
            Log.d(TAG, "onBeginningOfSpeech");
        }
        public void onRmsChanged(float rmsdB)
        {
            Log.d(TAG, "onRmsChanged");
        }
        public void onBufferReceived(byte[] buffer)
        {
            Log.d(TAG, "onBufferReceived");
        }
        public void onEndOfSpeech()
        {
            Log.d(TAG, "onEndofSpeech");
        }
        public void onError(int error)
        {
            Log.d(TAG,  "error " +  error);
            mText.setText("error " + error);
        }
        public void onResults(Bundle results)
        {
            String str = new String();
            Log.d(TAG, "onResults " + results);
            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (int i = 0; i < data.size(); i++)
            {
                Log.d(TAG, "result " + data.get(i));
                str += data.get(i);
            }
            mText.setText(data.get(0)+"");//String.valueOf(data.size()));
        }
        public void onPartialResults(Bundle partialResults)
        {
            Log.d(TAG, "onPartialResults");
        }
        public void onEvent(int eventType, Bundle params)
        {
            Log.d(TAG, "onEvent " + eventType);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.Ask_Button)
        {
            //Toast.makeText(this,"Clicked Mission Complete!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");

            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
            sr.startListening(intent);
            Log.i("111111","11111111");


        }
        if (v.getId() == R.id.Read_Button)
        {
            this.onInit(TextToSpeech.SUCCESS);

        }
    }


}


