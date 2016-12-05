package com.qrqc.message.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qrqc.message.util.Bimp;
import com.qrqc.message.util.PublicWay;
import com.qrqc.message.util.Res;
import com.qrqc.message.zoom.PhotoView;
import com.qrqc.message.zoom.ViewPagerFixed;

/**
 * 这个是用于进行图片浏览时的界面
 * 
 * @author king
 * @QQ:595163260
 * @version 2014年10月18日 下午11:47:53
 */
public class GalleryActivity extends Activity {
	private Intent intent;
	// 返回按钮
	private Button back_bt;
	// 发送按钮
	private Button send_bt;
	// 删除按钮
	private Button del_bt;
	// 顶部显示预览图片位置的textview
	private TextView positionTextView;
	// 获取前一个activity传过来的position
	private int position;
	// 当前的位置
	private int location = 0;

	private ArrayList<View> listViews = null;
	private ViewPagerFixed pager;
	private MyPageAdapter adapter;

	public List<Bitmap> bmp = new ArrayList<Bitmap>();
	public List<String> drr = new ArrayList<String>();
	public List<String> del = new ArrayList<String>();

	private Context mContext;

	RelativeLayout photo_relativeLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(Res.getLayoutID("plugin_camera_gallery"));// 切屏到主界面
		Log.i("avtivity", "GalleryActivity is on creating");
		PublicWay.activityList.add(this);
		mContext = this;
		back_bt = (Button) findViewById(Res.getWidgetID("gallery_back"));
		send_bt = (Button) findViewById(Res.getWidgetID("send_button"));// 完成按钮
		del_bt = (Button) findViewById(Res.getWidgetID("gallery_del"));
		back_bt.setOnClickListener(new BackListener());
		send_bt.setOnClickListener(new GallerySendListener());
		del_bt.setOnClickListener(new DelListener());
		intent = getIntent();
		Bundle bundle = intent.getExtras();
		position = Integer.parseInt(intent.getStringExtra("position"));
		isShowOkBt();
		// 为发送按钮设置文字
		pager = (ViewPagerFixed) findViewById(Res.getWidgetID("gallery01"));
		pager.setOnPageChangeListener(pageChangeListener);
		for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
			initListViews(Bimp.tempSelectBitmap.get(i).getBitmap());
		}

		adapter = new MyPageAdapter(listViews);
		pager.setAdapter(adapter);
		pager.setPageMargin(getResources().getDimensionPixelOffset(Res.getDimenID("ui_10_dip")));
		int id = intent.getIntExtra("ID", 0);
		pager.setCurrentItem(id);
	}

	private final OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			location = arg0;
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}
	};

	private void initListViews(Bitmap bm) {
		if (listViews == null)
			listViews = new ArrayList<View>();
		PhotoView img = new PhotoView(this);
		img.setBackgroundColor(0xff000000);
		img.setImageBitmap(bm);
		img.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		listViews.add(img);
	}

	// 返回按钮添加的监听器
	private class BackListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			intent.setClass(GalleryActivity.this, ImageFile.class);
			startActivity(intent);
		}
	}

	// 删除按钮添加的监听器
	private class DelListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (listViews.size() == 1) {
				Bimp.tempSelectBitmap.clear();
				Bimp.max = 0;
				send_bt.setText(Res.getString("finish") + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num
						+ ")");
				Intent intent = new Intent("data.broadcast.action");
				sendBroadcast(intent);
				finish();
			} else {
				Bimp.tempSelectBitmap.remove(location);
				Bimp.max--;
				pager.removeAllViews();
				listViews.remove(location);
				adapter.setListViews(listViews);
				send_bt.setText(Res.getString("finish") + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num
						+ ")");
				adapter.notifyDataSetChanged();
			}
		}
	}

	// 完成按钮的监听
	private class GallerySendListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			finish();
			intent.setClass(mContext, SendMessageActivity.class);
			startActivity(intent);
		}

	}

	public void isShowOkBt() {
		if (Bimp.tempSelectBitmap.size() > 0) {
			send_bt.setText(Res.getString("finish") + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
			send_bt.setPressed(true);
			send_bt.setClickable(true);
			send_bt.setTextColor(Color.WHITE);
		} else {
			send_bt.setPressed(false);
			send_bt.setClickable(false);
			send_bt.setTextColor(Color.parseColor("#E1E0DE"));
		}
	}

	/**
	 * 监听返回按钮
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (position == 1) {
				this.finish();
				intent.setClass(GalleryActivity.this, AlbumActivity.class);
				startActivity(intent);
			} else if (position == 2) {
				this.finish();
				intent.setClass(GalleryActivity.this, ShowAllPhoto.class);
				startActivity(intent);
			}
		}
		return true;
	}

	class MyPageAdapter extends PagerAdapter {

		private ArrayList<View> listViews;

		private int size;

		public MyPageAdapter(ArrayList<View> listViews) {
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public void setListViews(ArrayList<View> listViews) {
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		@Override
		public int getCount() {
			return size;
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPagerFixed) arg0).removeView(listViews.get(arg1 % size));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			try {
				((ViewPagerFixed) arg0).addView(listViews.get(arg1 % size), 0);

			} catch (Exception e) {
			}
			return listViews.get(arg1 % size);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}
}
