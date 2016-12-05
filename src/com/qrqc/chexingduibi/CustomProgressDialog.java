package com.qrqc.chexingduibi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.qrqc.activity.R;

/**
 * @Description:自定义对话框
 * @author http://blog.csdn.net/finddreams
 */
public class CustomProgressDialog extends ProgressDialog {

	private AnimationDrawable mAnimation;
	private final Context mContext;
	private ImageView mImageView;
	private final String mLoadingTip;
	private TextView mLoadingTv;
	private final int count = 0;
	private String oldLoadingTip;
	private final int mResid;

	public CustomProgressDialog(Context context, String content, int id) {
		super(context, android.R.style.Theme);
		setOwnerActivity((Activity) context);
		// super(context);
		this.mContext = context;
		this.mLoadingTip = content;
		this.mResid = id;
		setCanceledOnTouchOutside(true);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initView();
		initData();
	}

	private void initData() {

		mImageView.setBackgroundResource(mResid);
		// 通过ImageView对象拿到背景显示的AnimationDrawable
		mAnimation = (AnimationDrawable) mImageView.getBackground();
		// 为了防止在onCreate方法中只显示第一帧的解决方案之一
		mImageView.post(new Runnable() {
			@Override
			public void run() {
				mAnimation.start();

			}
		});
		mLoadingTv.setText(mLoadingTip);

	}

	public void setContent(String str) {
		mLoadingTv.setText(str);
	}

	private void initView() {
		setContentView(R.layout.progress_dialog);
		mLoadingTv = (TextView) findViewById(R.id.loadingTv);
		mImageView = (ImageView) findViewById(R.id.loadingIv);
	}

	/*
	 * @Override public void onWindowFocusChanged(boolean hasFocus) { // TODO
	 * Auto-generated method stub mAnimation.start();
	 * super.onWindowFocusChanged(hasFocus); }
	 */
}
