package edu.rose_hulman.greenewp.motorheadwatsongroup;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends Activity implements View.OnClickListener, TextToSpeech.OnInitListener {

    private SpeechRecognizer sr;
    private EditText mText;
    private static final String TAG = "MyStt3Activity";
    private Button askButton;
    private Button readButton;
    public String myQuestion = "";
    private TextToSpeech speaker;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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
        speaker = new TextToSpeech(this, this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = speaker.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("error", "This Language is not supported");
            } else {
                convertTextToSpeech();
            }
        }
    }

    private void convertTextToSpeech() {
        String text = mText.getText().toString();
        if (text == null || "".equals(text)) {
            text = "Content not available";
        } else {
            speaker.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://edu.rose_hulman.greenewp.motorheadwatsongroup/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://edu.rose_hulman.greenewp.motorheadwatsongroup/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    class listener implements RecognitionListener {
        public void onReadyForSpeech(Bundle params) {
            Log.d(TAG, "onReadyForSpeech");
        }

        public void onBeginningOfSpeech() {
            Log.d(TAG, "onBeginningOfSpeech");
        }

        public void onRmsChanged(float rmsdB) {
            Log.d(TAG, "onRmsChanged");
        }

        public void onBufferReceived(byte[] buffer) {
            Log.d(TAG, "onBufferReceived");
        }

        public void onEndOfSpeech() {
            Log.d(TAG, "onEndofSpeech");
        }

        public void onError(int error) {
            Log.d(TAG, "error " + error);
            mText.setText("error " + error);
        }

        public void onResults(Bundle results) {
            String str = new String();
            Log.d(TAG, "onResults " + results);
            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (int i = 0; i < data.size(); i++) {
                Log.d(TAG, "result " + data.get(i));
                str += data.get(i);
            }
            mText.setText(data.get(0) + "");//String.valueOf(data.size()));
            myQuestion = data.get(0) + "";


        }

        public void onPartialResults(Bundle partialResults) {
            Log.d(TAG, "onPartialResults");
        }

        public void onEvent(int eventType, Bundle params) {
            Log.d(TAG, "onEvent " + eventType);
        }
    }

    public void retreiveAndRank() {
//        String question = "How do I change my flat tire?";
//        question = myQuestion;
//        RetrieveAndRank service = new RetrieveAndRank();
//        service.setUsernameAndPassword("19117133-7b89-408a-bf12-aca66ec239e3", " xlt65RJ4m0U0");
//        HttpSolrServer myFactory = new HttpSolrServer();
//        HttpSolrClient solrClient = myFactory.getSolrClient(service.getSolrUrl("scf9cfb62c_d55e_4683_bb2c_e4e4126154be"), " 19117133-7b89-408a-bf12-aca66ec239e3", "xlt65RJ4m0U0");
//        SolrQuery query = new SolrQuery(question);
//        QueryResponse response = null;
//        try {
//            response = solrClient.query("motorhead_rr_collection_updated", query);
//        } catch (SolrServerException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Ranking ranking = service.rank("3b140ax15-rank-1760", response);
//        System.out.println(ranking);


    }
    public static JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {

        HttpURLConnection urlConnection = null;

        URL url = new URL(urlString);

        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */);
        urlConnection.setConnectTimeout(15000 /* milliseconds */);

        urlConnection.setDoOutput(true);

        urlConnection.connect();

        BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));

        char[] buffer = new char[1024];

        String jsonString = new String();

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line+"\n");
        }
        br.close();

        jsonString = sb.toString();

        System.out.println("JSON: " + jsonString);

        return new JSONObject(jsonString);
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.Ask_Button) {
            //Toast.makeText(this,"Clicked Mission Complete!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "voice.recognition.test");

            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
            sr.startListening(intent);
            Log.i("111111", "11111111");

            Log.d("TTT", "here");
            try{
                JSONObject jsonObject = getJSONObjectFromURL("https://19117133-7b89-408a-bf12-aca66ec239e3:xlt65RJ4m0U0@gateway.watsonplatform.net/retrieve-and-rank/api/v1/solr_clusters/scf9cfb62c_d55e_4683_bb2c_e4e4126154be/solr/motorhead_rr_collection_updated/fcselect?ranker_id=3b140ax15-rank-1760&q=" + "Why is my car bouncing?" + "&wt=json&fl=id,title,body");

                // Parse your json here
                Log.d("TTT",""+ jsonObject.toString());
                mText.setText(jsonObject.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        if (v.getId() == R.id.Read_Button) {
            this.onInit(TextToSpeech.SUCCESS);

        }
    }


}


