package com.qrqc.activity;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.qrqc.util.UpAndDown;

public class MainActivity extends Activity {
	private String username;
	private String tokenString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		username = Config.getCachedUsername(this);
		tokenString = Config.getCachedToken(this);
		String tmpUrl = Config.URL_CHECK_TOKEN + "username=" + username + "&token=" + tokenString;
		System.out.println(tmpUrl + "----------------------------------");
		if (tokenString != null && username != null) {
			UpAndDown.netUtils(tmpUrl, checkTokenHandler);
		} else {
			startActivity(new Intent(this, DengluActivity.class));
			finish();
		}

	}

	public Handler checkTokenHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			// System.out.println("getZhuceHandler..........................");
			// System.out.println(jsonData);
			if (msg.getData().getString("state") == Config.STATUS_NET_FAIL) {
				Toast.makeText(MainActivity.this, Config.STATUS_NET_FAIL, Toast.LENGTH_LONG).show();
				System.exit(0);
			} else {
				try {
					System.out.println(msg.getData().getString(Config.KEY_RESULT_STATUS) + "//////////");
					JSONObject object = new JSONObject(msg.getData().getString(Config.KEY_RESULT_STATUS));
					if (object.getString(Config.KEY_RESULT_CODE).equals(Config.STATUS_TOKEN_OK)) {

						Intent intent = new Intent(MainActivity.this, First.class);
						// intent.putExtra(Config.KEY_TOKEN, tokenString);
						// intent.putExtra(Config.KEY_USERNAME, username);

						startActivity(intent);
						finish();
					} else {
						startActivity(new Intent(MainActivity.this, DengluActivity.class));
						finish();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	};
}
