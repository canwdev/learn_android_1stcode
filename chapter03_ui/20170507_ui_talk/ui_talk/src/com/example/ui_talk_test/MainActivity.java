package com.example.ui_talk_test;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ListView msgListView;
	private EditText inputText;
	private ImageButton btn_send;
	private MsgAdapter adapter;
	private List<Msg> msgList = new ArrayList<Msg>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		inputText = (EditText) findViewById(R.id.inputText);
		btn_send = (ImageButton) findViewById(R.id.btn_send);
		msgListView = (ListView) findViewById(R.id.msgListView);
		
		initMsgs();
		adapter = new MsgAdapter(MainActivity.this, R.layout.msg_item, msgList);
		msgListView.setAdapter(adapter);
		
		btn_send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String content = inputText.getText().toString();
				if (!"".equals(content)) {
					Msg msg = new Msg(content, Msg.TYPE_SENT);
					msgList.add(msg);
					adapter.notifyDataSetChanged(); //当有新消息时，刷新ListView中的显示
					msgListView.setSelection(msgList.size()); // 将ListView定位到最后一行
					inputText.setText("");
				} else {
					Toast.makeText(MainActivity.this, "Please Input...", Toast.LENGTH_SHORT).show();
					msgListView.setSelection(msgList.size());
				}
			}
		});
		
		inputText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				msgListView.setSelection(msgList.size());
			}
		});
	}

	private void initMsgs() {
		Msg msg1 = new Msg("Hello!", Msg.TYPE_RECEIVED);
		msgList.add(msg1);
		Msg msg2 = new Msg("Hi! How are you?" , Msg.TYPE_SENT);
		msgList.add(msg2);
		Msg msg3 = new Msg("I,m "+getResources().getString(R.string.hello_world)+" thankyou, and you?", Msg.TYPE_RECEIVED);
		msgList.add(msg3);
	}
}
