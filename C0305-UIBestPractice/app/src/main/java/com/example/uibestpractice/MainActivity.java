package com.example.uibestpractice;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Message> msgList = new ArrayList<>();
    private EditText msgText;
    private Button sendButton;
    private RecyclerView msgRecyclerView;
    private MessageAdapter msgAdapter;

    private void initMsg() {
        Message msg1 = new Message("Hello World!", Message.TYPE_RECEIVED);
        msgList.add(msg1);
        Message msg2 = new Message("Hi...?", Message.TYPE_SENT);
        msgList.add(msg2);
        Message msg3 = new Message("Nice to meet you, I'm an Android App Instance.", Message.TYPE_RECEIVED);
        msgList.add(msg3);
    }

    private void ClearChatList() {
        msgList.clear();
        msgAdapter.notifyDataSetChanged();
        Toast.makeText(MainActivity.this, "Chat list Cleared", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_clear:
                // 新建弹出对话框
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle(android.R.string.dialog_alert_title);
                dialog.setMessage("Are you sure clear chat list?");
                dialog.setPositiveButton(android.R.string.ok , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ClearChatList();
                    }
                });
                dialog.setNegativeButton(android.R.string.cancel , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialog.show();

                break;
            case R.id.item_new:
                finish();
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initMsg();

        msgText = (EditText) findViewById(R.id.text_message);
        sendButton = (Button) findViewById(R.id.button_sent);
        msgRecyclerView = (RecyclerView) findViewById(R.id.chat_area);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        msgAdapter = new MessageAdapter(msgList);
        msgRecyclerView.setAdapter(msgAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = msgText.getText().toString();
                if (!"".equals(content)) {
                    if ("//clear".equals(content)) {
                        ClearChatList();
                    } else {
                        Message msg = new Message(content, Message.TYPE_SENT);
                        msgList.add(msg);
                        msgAdapter.notifyItemInserted(msgList.size() - 1);
                    }
                    msgText.setText(null);
                }
                msgRecyclerView.scrollToPosition(msgList.size() - 1);
            }
        });

        sendButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                msgList.add(new Message(msgText.getText().toString(), Message.TYPE_RECEIVED));
                msgAdapter.notifyItemInserted(msgList.size() - 1);
                msgRecyclerView.scrollToPosition(msgList.size() - 1);
                msgText.setText(null);
                return true;    // 只响应长按事件
            }
        });

        toolbar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                msgRecyclerView.scrollToPosition(0);
                return true;
            }
        });

        // TODO:添加菜单
    }
}
