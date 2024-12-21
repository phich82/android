package com.java.sample;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.java.sample.dto.Student;
import com.java.sample.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class SocketIOActivity extends AppCompatActivity {

    private EditText txtMessageChat;
    private ImageButton btnAddUserChat, btnSendMessageChat;
    private ListView usersListView, chatHistoryListView;


    private ArrayList<String> usersChat;
    private ArrayAdapter usersChatAdapter;
    private ArrayList<String> messagesChat;
    private ArrayAdapter messagesChatAdapter;


    private Socket clientSocket;

    private static final String USER_LIST_SERVER_EVENT = "USER_LIST_SERVER_EVENT";
    private static final String REGISTER_USER_CLIENT_EVENT = "REGISTER_USER_CLIENT_EVENT";
    private static final String REGISTER_USER_SERVER_EVENT = "REGISTER_USER_SERVER_EVENT";
    private static final String SEND_CHAT_CLIENT_EVENT = "SEND_CHAT_CLIENT_EVENT";
    private static final String SEND_CHAT_SERVER_EVENT = "SEND_CHAT_SERVER_EVENT";
    private static final String SEND_CHAT_REPLY_SERVER_EVENT = "SEND_CHAT_REPLY_SERVER_EVENT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_socket_ioactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initUIElements();

        try {
            // Connect to socket server
            clientSocket = IO.socket("http://192.168.1.111:3000/");
            clientSocket.connect();

            // Show Chat User List
            usersChat = new ArrayList<>();
            //usersChatAdapter = new ArrayAdapter<>(this, R.layout.user_item_chat, usersChat);
            usersChatAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usersChat);
            usersListView.setAdapter(usersChatAdapter);

            // Show Chat Message List
            messagesChat = new ArrayList<>();
            messagesChatAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messagesChat);
            chatHistoryListView.setAdapter(messagesChatAdapter);

            // Add user
            btnAddUserChat.setOnClickListener(view -> {
                String user = txtMessageChat.getText().toString().trim();
                if (user.isEmpty()) {
                    Toast.makeText(this, "Please enter user!", Toast.LENGTH_SHORT).show();
                } else {
                    clientSocket.emit(REGISTER_USER_CLIENT_EVENT, user);
                }
            });

            // Send message
            btnSendMessageChat.setOnClickListener(view -> {
                String message = txtMessageChat.getText().toString().trim();
                if (message.isEmpty()) {
                    Toast.makeText(this, "Please enter message!", Toast.LENGTH_SHORT).show();
                } else {
                    clientSocket.emit(SEND_CHAT_CLIENT_EVENT, message);
                }
            });

            // Get user list
            clientSocket.on(USER_LIST_SERVER_EVENT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject data = (JSONObject) args[0];

                                System.out.println("Received (users) from socket server ===> " + data);

                                boolean success = data.getBoolean("success");
                                String message = data.getString("message");

                                if (success) {
                                    JSONArray users = data.getJSONArray("data");
                                    usersChat.clear();
                                    for (int i=0; i < users.length(); i++) {
                                        usersChat.add(users.getString(i));
                                    }
                                    usersChatAdapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(SocketIOActivity.this, message, Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });

            // Listening register user
            clientSocket.on(REGISTER_USER_SERVER_EVENT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject data = (JSONObject) args[0];
                                System.out.println("Received (new user) from socket server ===> " + data);
                                boolean success = data.getBoolean("success");
                                String message = success ? "Register successfully!" : data.getString("message");
                                Toast.makeText(SocketIOActivity.this, message, Toast.LENGTH_SHORT).show();
                                if (success) {
                                    txtMessageChat.setText("");
                                    txtMessageChat.setFocusable(true);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });

            // Listening send message result
            clientSocket.on(SEND_CHAT_REPLY_SERVER_EVENT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject data = (JSONObject) args[0];
                                System.out.println("Received (message) from socket server ===> " + data);
                                boolean success = data.getBoolean("success");
                                if (!success) {
                                    String message = data.getString("message");
                                    Toast.makeText(SocketIOActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });

            // Listening send message from registered clients
            clientSocket.on(SEND_CHAT_SERVER_EVENT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject data = (JSONObject) args[0];
                                System.out.println("Received (message) from socket server ===> " + data);
                                boolean success = data.getBoolean("success");
                                String message = success ? "Register successfully!" : data.getString("message");
                                String content = data.getString("data");
                                if (success) {
                                    messagesChat.add(content);
                                    messagesChatAdapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(SocketIOActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });

        } catch (URISyntaxException e) {
            System.out.println("socket (error) ===> " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void initUIElements() {
        txtMessageChat = findViewById(R.id.txtMessageChat);
        btnAddUserChat = findViewById(R.id.btnAddUserChat);
        btnSendMessageChat = findViewById(R.id.btnSendMessageChat);
        usersListView = findViewById(R.id.listViewUser);
        chatHistoryListView = findViewById(R.id.listViewChat);
    }
}