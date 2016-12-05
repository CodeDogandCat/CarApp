package com.qrqc.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.juhe.petrolstation.activity.PertrolStationActivity;
import com.weizhang.activity.WeizhangActivity;

public class ShangjiafuwuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shangjiafuwu);
		findViewById(R.id.btn_jiayouzhan).setOnClickListener(clickListener);
		findViewById(R.id.btn_weizhangchaxun).setOnClickListener(clickListener);

	}

	protected View.OnClickListener clickListener = new View.OnClickListener() {

		@SuppressLint("CommitPrefEdits")
		@Override
		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.btn_jiayouzhan:
				Intent intent3 = new Intent(ShangjiafuwuActivity.this, PertrolStationActivity.class);
				startActivity(intent3);
				break;
			case R.id.btn_weizhangchaxun:
				Intent intent4 = new Intent(ShangjiafuwuActivity.this, WeizhangActivity.class);
				startActivity(intent4);
				break;

			default:
				break;
			}
		}

	};

}
