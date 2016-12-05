package com.qrqc.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class LianxiwomenActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_lianxiwomen);

	}

}
