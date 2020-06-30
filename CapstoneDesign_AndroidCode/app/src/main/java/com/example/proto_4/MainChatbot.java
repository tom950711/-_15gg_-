package com.example.proto_4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.cloud.dialogflow.v2.TextInput;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.UUID;

public class MainChatbot extends AppCompatActivity {

    private static final String TAG = MainChatbot.class.getSimpleName();
    private static final int USER = 10001;
    private static final int BOT = 10002;
    private static final int Button5 = 10003;
    private static final String EXTRA_MESSAGE = "10004";
    static String[] s_array;
    static Button[] buttons;

    private String uuid = UUID.randomUUID().toString();
    private LinearLayout chatLayout;
    private EditText queryEditText;

    // Java V2
    private SessionsClient sessionsClient;
    private SessionName session;
    //    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);
        final ScrollView scrollview = findViewById(R.id.chatScrollView);
        scrollview.post(() -> scrollview.fullScroll(ScrollView.FOCUS_DOWN));
//        button = (Button)findViewById(R.id.button1);


        chatLayout = findViewById(R.id.chatLayout);
//        findViewById(R.id.button);

        ImageView sendBtn = findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(this::sendMessage);

        queryEditText = findViewById(R.id.queryEditText);
        queryEditText.setOnKeyListener((view, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DPAD_CENTER:
                    case KeyEvent.KEYCODE_ENTER:
                        sendMessage(sendBtn);
                        return true;
                    default:
                        break;
                }
            }
            return false;
        });
        // Java V2
        initV2Chatbot();

//        String msg = "안녕하세요";
        QueryInput queryInput = QueryInput.newBuilder().setText(TextInput.newBuilder().setText("안녕").setLanguageCode("ko")).build();
        new RequestJavaV2Task(MainChatbot.this, session, sessionsClient, queryInput).execute();

    }

    // Java V2
    private void initV2Chatbot() {
//        String projectId = "newagent-cipdkd";
//        File credentialsPath = new File("C:/Users/john1/AndroidStudioProjects/cap/dialogflow/newagent-cipdkd-b9ad30b060f2.json");
//        GoogleCredentials credentials;
        try {
//            FileInputStream serviceAccountStream = new FileInputStream(credentialsPath);
//            credentials = ServiceAccountCredentials.fromStream(serviceAccountStream);
            InputStream stream = getResources().openRawResource(R.raw.test_agent_credentials);
            GoogleCredentials credentials = GoogleCredentials.fromStream(stream);
            String projectId = ((ServiceAccountCredentials)credentials).getProjectId();

            SessionsSettings.Builder settingsBuilder = SessionsSettings.newBuilder();
            SessionsSettings sessionsSettings = settingsBuilder.setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();
            sessionsClient = SessionsClient.create(sessionsSettings);
            session = SessionName.of(projectId, uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(View view) {
        String msg = queryEditText.getText().toString();
        if (msg.trim().isEmpty()) {
            Toast.makeText(MainChatbot.this, "Please enter your query!", Toast.LENGTH_LONG).show();
        } else {
            showTextView(msg, USER);
            queryEditText.setText("");
            // Android client for older V1 --- recommend not to use this
//            aiRequest.setQuery(msg);
//            RequestTask requestTask = new RequestTask(MainActivity.this, aiDataService, customAIServiceContext);
//            requestTask.execute(aiRequest);

            // Java V2
            QueryInput queryInput = QueryInput.newBuilder().setText(TextInput.newBuilder().setText(msg).setLanguageCode("ko")).build();
            new RequestJavaV2Task(MainChatbot.this, session, sessionsClient, queryInput).execute();
        }
    }

    public void callbackV2(DetectIntentResponse response) {
        if (response != null) {
            // process aiResponse here
//            startToast(response.toString());

            String botReply = response.getQueryResult().getFulfillmentText();
            s_array = botReply.split("/");
            Log.d(TAG, "V2 Bot Reply: " + botReply);
            if (s_array.length != 1) {
                Log.d(TAG, "V2 Bot Reply: " + s_array[0]);
                showButton(s_array,Button5);
            }
            else {
                showTextView(botReply, BOT);
            }
        } else {
            Log.d(TAG, "Bot Reply: Null");
            showTextView("There was some communication issue. Please Try again!", BOT);
            showTextView("오류발생", BOT);
        }
    }

    public void showTextView(String message, int type) {
        FrameLayout layout;
        switch (type) {
            case USER:
                layout = getUserLayout();
                break;
            case BOT:
                layout = getBotLayout();
                break;
            default:
                layout = getBotLayout();
                break;
        }
        layout.setFocusableInTouchMode(true);
        chatLayout.addView(layout); // move focus to text view to automatically make it scroll up if softfocus
        TextView tv = layout.findViewById(R.id.chatMsg);
        tv.setText(message);
        layout.requestFocus();
        queryEditText.requestFocus(); // change focus back to edit text to continue typing
    }

    public void showButton(String[] message, int type) {
        FrameLayout layout;
        switch (type) {
            case Button5:
                layout = getButtonLayout();
                break;
            default:
                layout = getBotLayout();
                break;
        }
        layout.setFocusableInTouchMode(true);
        chatLayout.addView(layout); // move focus to text view to automatically make it scroll up if softfocus
        if (type == Button5) {
//            TextView tv = layout.findViewById(R.id.chatMsg);
//            tv.setText(message);
            if (message.length == 1) {
                Button bt1 = layout.findViewById(R.id.button3);
                bt1.setText(message[0]);
                bt1.setVisibility(View.VISIBLE);
                findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainChatbot.this, DetailActivicy.class);
                        Button button3 = (Button) findViewById(R.id.button3);
                        String name = button3.getText().toString();
                        intent.putExtra("name", name) ;
                        Log.d(TAG, name);
                        startActivity(intent) ;
                    }
                });
            }
            else if (message.length == 2) {
                Button bt1 = layout.findViewById(R.id.button3);
                Button bt2 = layout.findViewById(R.id.button4);
                bt1.setText(message[0]);
                bt2.setText(message[1]);
                bt1.setVisibility(View.VISIBLE);
                bt2.setVisibility(View.VISIBLE);
                findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainChatbot.this, DetailActivicy.class);
                        Button button3 = (Button) findViewById(R.id.button3);
                        String name = button3.getText().toString();

                        intent.putExtra("name", name) ;

                        startActivity(intent) ;
                    }
                });
                findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainChatbot.this, DetailActivicy.class);
                        Button button4 = (Button) findViewById(R.id.button4);
                        String name = button4.getText().toString();
                        intent.putExtra("name", name) ;

                        startActivity(intent) ;
                    }
                });
            }
            else {
                Button bt1 = layout.findViewById(R.id.button3);
                Button bt2 = layout.findViewById(R.id.button4);
                Button bt3 = layout.findViewById(R.id.button5);
                bt1.setText(message[0]);
                bt2.setText(message[1]);
                bt3.setText(message[2]);
                bt1.setVisibility(View.VISIBLE);
                bt2.setVisibility(View.VISIBLE);
                bt3.setVisibility(View.VISIBLE);
                findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainChatbot.this, DetailActivicy.class);
                        Button button3 = (Button) findViewById(R.id.button3);
                        String name = button3.getText().toString();

                        intent.putExtra("name", name) ;

                        startActivity(intent) ;
                    }
                });
                findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainChatbot.this, DetailActivicy.class);
                        Button button4 = (Button) findViewById(R.id.button4);
                        String name = button4.getText().toString();
                        intent.putExtra("name", name) ;

                        startActivity(intent) ;
                    }
                });
                findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainChatbot.this, DetailActivicy.class);
                        Button button5 = (Button) findViewById(R.id.button5);
                        String name = button5.getText().toString();
                        intent.putExtra("name", name) ;

                        startActivity(intent) ;
                    }
                });
            }
        }
        layout.requestFocus();
        queryEditText.requestFocus(); // change focus back to edit text to continue typing
    }

    FrameLayout getUserLayout() {
        LayoutInflater inflater = LayoutInflater.from(MainChatbot.this);
        return (FrameLayout) inflater.inflate(R.layout.user_msg_layout, null);
    }

    FrameLayout getBotLayout() {
        LayoutInflater inflater = LayoutInflater.from(MainChatbot.this);
        return (FrameLayout) inflater.inflate(R.layout.bot_msg_layout, null);
    }
    FrameLayout getButtonLayout() {
        LayoutInflater inflater = LayoutInflater.from(MainChatbot.this);
        return (FrameLayout) inflater.inflate(R.layout.button, null);
    }
    public void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
