package com.qrqc.activity;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.qrqc.util.UpAndDown;

public class DengluActivity extends Activity {
	private int count;
	private int countofdenglu;

	private EditText et_youhuming = null;
	private EditText et_mima = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_denglu);
		findViewById(R.id.btn_tozhuce).setOnClickListener(clickListener);
		findViewById(R.id.btn_denglu).setOnClickListener(clickListener);
		et_youhuming = (EditText) findViewById(R.id.et_yonghuming);
		et_mima = (EditText) findViewById(R.id.et_mima);
		count = 0;
		countofdenglu = 0;
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		countofdenglu = 0;
	}

	public Handler getDengluHandler = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {

			// System.out.println("getZhuceHandler..........................");
			// System.out.println(jsonData);
			if (msg.getData().getString(Config.KEY_NET_STATUS).equals(Config.STATUS_NET_FAIL)) {
				Toast.makeText(DengluActivity.this, Config.STATUS_NET_FAIL, Toast.LENGTH_LONG).show();
				return;
			} else {
				try {
					System.out.println(msg.getData().getString(Config.KEY_RESULT_STATUS) + "//////////");
					JSONObject object = new JSONObject(msg.getData().getString(Config.KEY_RESULT_STATUS));
					if (object.getString(Config.KEY_RESULT_CODE).equals(Config.STATUS_LOG_OK)) {
						// System.out.println("yes...........");
						Toast.makeText(DengluActivity.this, Config.STATUS_LOG_OK, Toast.LENGTH_LONG).show();
						Config.cacheToken(DengluActivity.this, android.os.Build.SERIAL);
						Config.cacheUsername(DengluActivity.this, et_youhuming.getText().toString());
						Intent i = new Intent(DengluActivity.this, First.class);
						i.putExtra(Config.KEY_TOKEN, android.os.Build.SERIAL);
						i.putExtra(Config.KEY_USERNAME, et_youhuming.getText().toString());
						startActivity(i);
						finish();
					} else {
						Toast.makeText(DengluActivity.this, Config.STATUS_LOG_FAIL, Toast.LENGTH_LONG).show();
						countofdenglu = 0;
						return;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	};
	protected View.OnClickListener clickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_denglu:
				if (countofdenglu == 0) {

					String yonghuming = et_youhuming.getText().toString();
					String mima = et_mima.getText().toString();
					if (yonghuming.equals("") || mima.equals("")) {
						Toast.makeText(getApplicationContext(), "用户名或密码为空", Toast.LENGTH_SHORT).show();
						countofdenglu = 0;
					} else {
						String tmpUrl = Config.URL_LOG + "username=" + yonghuming + "&password=" + mima + "&token="
								+ android.os.Build.SERIAL;
						System.out.println(tmpUrl);
						UpAndDown.netUtils(tmpUrl, getDengluHandler);
						countofdenglu++;
					}
				}

				break;
			case R.id.btn_tozhuce:
				startActivity(new Intent().setClass(DengluActivity.this, ZhuceActivity.class));
				finish();
				break;

			default:
				break;
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		// case 0:
		// if (data.getExtras().getString("content").equals("请登录")) {
		// Toast.makeText(DengluActivity.this, "请登录", Toast.LENGTH_LONG).show();
		// }
		// break;

		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (count >= 1) {
				System.exit(0);
			} else {
				Toast.makeText(getApplicationContext(), "再按一次退出应用", Toast.LENGTH_SHORT).show();
			}
			count++;
		}
		return true;
	}

}
