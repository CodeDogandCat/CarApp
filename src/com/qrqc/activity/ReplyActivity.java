package com.qrqc.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.qrqc.message.util.Res;
import com.qrqc.util.UpAndDown;

/**
 * 首页面activity
 * 
 * @author king
 * @QQ:595163260
 * @version 2014年10月18日 下午11:48:34
 */
public class ReplyActivity extends Activity {
	// private int cancelClickTimes;
	private int backClickTimes;
	private String Mid;
	private View parentView;
	private TextView tv_text;

	// private String username;
	// private String token;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Res.init(this);
		parentView = getLayoutInflater().inflate(R.layout.activity_reply, null);
		setContentView(parentView);
		Log.i("avtivity", "MainActivity is on creating");
		Init();

	}

	public void Init() {

		Mid = getIntent().getExtras().getString("Mid");
		// cancelClickTimes = 0;
		backClickTimes = 0;
		tv_text = (TextView) findViewById(R.id.replyacticity_tv_text);
		findViewById(R.id.replyacticity_cancel).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// if (cancelClickTimes == 1) {
				Intent intent = new Intent();
				intent.putExtra("status", "fail");
				setResult(Config.ACTIVITY_STATUS_REPLY, intent);
				finish();
				// } else {
				// Toast.makeText(ReplyActivity.this, "再按一次取消回帖",
				// Toast.LENGTH_SHORT).show();
				// }
				// cancelClickTimes++;

			}
		});
		findViewById(R.id.replyacticity_send).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					SharedPreferences sharedPreferences = ReplyActivity.this.getSharedPreferences(Config.APP_ID,
							Context.MODE_PRIVATE);

					UpAndDown.uploadReply(ReplyActivity.this, ReplyHandler, Mid, tv_text.getText().toString(),
							Config.URL_UPLOAD_REPLY, sharedPreferences.getString(Config.KEY_USERNAME, ""),
							sharedPreferences.getString(Config.KEY_TOKEN, ""));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

	@SuppressLint("HandlerLeak")
	public Handler ReplyHandler = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {

			if (msg.getData().getString("status").equals(Config.STATUS_REPLY_FAIL)) {
				Toast.makeText(ReplyActivity.this, Config.STATUS_PUBLISH_FAIL, Toast.LENGTH_LONG).show();
			} else {

				Intent intent = new Intent();
				intent.putExtra("status", "ok");
				setResult(Config.ACTIVITY_STATUS_REPLY, intent);
				finish();

			}

		}

	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (backClickTimes == 1) {
				Intent intent = new Intent();
				intent.putExtra("status", "fail");
				setResult(Config.ACTIVITY_STATUS_REPLY, intent);
				finish();
			} else {
				Toast.makeText(ReplyActivity.this, "再按一次取消回帖", Toast.LENGTH_SHORT).show();
			}
			backClickTimes++;
		}
		return true;
	}

}
