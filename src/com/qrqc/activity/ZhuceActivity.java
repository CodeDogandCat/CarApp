package com.qrqc.activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class ZhuceActivity extends Activity {
	private EditText et_yonghuming = null;
	private EditText et_youxiang = null;
	private EditText et_mima = null;
	private EditText et_mima_2 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_zhuce);

		et_yonghuming = (EditText) findViewById(R.id.et_yonghuming_zhuce);
		et_youxiang = (EditText) findViewById(R.id.et_youxiang_zhuce);
		et_mima = (EditText) findViewById(R.id.et_mima_zhuce);
		et_mima_2 = (EditText) findViewById(R.id.et_mima_zhuce_queren);
		findViewById(R.id.btn_zhuce).setOnClickListener(clickListener);
	}

	public Handler getZhuceHandler = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {

			// System.out.println("getZhuceHandler..........................");
			// System.out.println(jsonData);
			if (msg.getData().getString(Config.KEY_NET_STATUS).equals(Config.STATUS_NET_FAIL)) {
				Toast.makeText(ZhuceActivity.this, Config.STATUS_NET_FAIL, Toast.LENGTH_LONG).show();
				return;
			} else {
				try {
					// System.out.println(msg.getData().getString(Config.KEY_RESULT_STATUS)
					// + "//////////");
					JSONObject object = new JSONObject(msg.getData().getString(Config.KEY_RESULT_STATUS));
					// System.out.println(object.getString(Config.KEY_RESULT_CODE)
					// + "..........");
					if (object.getString(Config.KEY_RESULT_CODE).equals(Config.STATUS_USERNAME_ALREADY_EXISTS)) {
						Toast.makeText(ZhuceActivity.this, Config.STATUS_USERNAME_ALREADY_EXISTS, Toast.LENGTH_LONG)
								.show();
						et_yonghuming.setText("");
						return;
					} else if (object.getString(Config.KEY_RESULT_CODE).equals(Config.STATUS_REG_OK)) {
						// System.out.println("yes...........");
						Toast.makeText(ZhuceActivity.this, Config.STATUS_REG_OK, Toast.LENGTH_LONG).show();
						Thread.sleep(2);
						startActivity(new Intent().setClass(ZhuceActivity.this, DengluActivity.class));
						finish();
					} else {
						Toast.makeText(ZhuceActivity.this, Config.STATUS_REG_FAIL, Toast.LENGTH_LONG).show();
						return;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	};

	//
	protected View.OnClickListener clickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {

			int resultcode = validate();
			System.out.println(resultcode);
			switch (resultcode) {

			case 0:
				Toast.makeText(ZhuceActivity.this, "两次密码输入不一致", Toast.LENGTH_LONG).show();
				break;
			case 1:
				Toast.makeText(ZhuceActivity.this, "密码要求为8位", Toast.LENGTH_LONG).show();
				break;
			case 2:
				Toast.makeText(ZhuceActivity.this, "密码不能包括数字和字母之外的符号", Toast.LENGTH_LONG).show();
				break;
			case 3:
				Toast.makeText(ZhuceActivity.this, "邮箱格式不正确", Toast.LENGTH_LONG).show();
				break;
			case 4:
				Toast.makeText(ZhuceActivity.this, "用户名长度要求在6位到20位之间", Toast.LENGTH_LONG).show();
				break;
			case 200:
				// 进行数据库相应操作,判断用户名是否唯一,唯一则插入信息
				String yonghuming = et_yonghuming.getText().toString().trim();
				String youxiang = et_youxiang.getText().toString().trim();
				String mima = et_mima.getText().toString().trim();
				String tmpUrl = Config.URL_REG + "username=" + yonghuming + "&email=" + youxiang + "&password=" + mima;
				UpAndDown.netUtils(tmpUrl, getZhuceHandler);
				break;
			default:
				break;
			}

		}

	};

	public int validate() {
		// 判断两次的密码是否相同，密码只能包括数字或字母
		if (et_mima.getText().toString().trim().equals(et_mima_2.getText().toString().trim())) {
			String password = et_mima.getText().toString().trim();
			boolean hasOther = !password.matches("^[\\da-zA-Z]*$");
			if (password.length() != 8) {
				return 1;
			}
			if (hasOther) {
				return 2;
			}
		} else {
			return 0;

		}
		// 判断邮箱格式
		String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(et_youxiang.getText().toString().trim());
		boolean isMatched = matcher.matches();
		if (!isMatched) {
			return 3;
		}
		// 判断用户名的长度大于8小于25，并且唯一
		String yonghuming = et_yonghuming.getText().toString().trim();
		System.out.println(yonghuming.length());
		if (yonghuming.length() < 6 || yonghuming.length() > 20) {
			return 4;
		}

		return 200;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.putExtra("content", "请登录");
			setResult(0, intent);
			finish();

		}
		return true;
	}
}
