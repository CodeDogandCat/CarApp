package com.qrqc.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qrqc.adapter.AtyMessageCommentListAdapter;
import com.qrqc.domain.mComment;
import com.qrqc.message.util.FileUtils;
import com.qrqc.util.UpAndDown;

public class MessageActivity extends ListActivity {
	private int longclicktimes;
	private String Mid;
	private String title;
	private String time;
	private String content;
	private String name;
	private long sign;
	private String pictureurl;
	private String picnum;
	private TextView showMessage_tv_title;
	private TextView showMessage_tv_time;
	private TextView showMessage_tv_content;
	private TextView showMessage_tv_username;
	private AtyMessageCommentListAdapter adapter = null;
	private final List<mComment> Comment = new ArrayList<mComment>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_message);
		// Toast.makeText(MessageActivity.this, Config.USERNAME + Config.TOKEN,
		// Toast.LENGTH_LONG).show();
		init();

	}

	public void init() {
		adapter = new AtyMessageCommentListAdapter(this);
		setListAdapter(adapter);

		longclicktimes = 0;
		showMessage_tv_title = (TextView) findViewById(R.id.showMessage_tv_title);
		showMessage_tv_time = (TextView) findViewById(R.id.showMessage_tv_time);
		showMessage_tv_content = (TextView) findViewById(R.id.showMessage_tv_text);
		showMessage_tv_username = (TextView) findViewById(R.id.showMessage_tv_username);
		Mid = getIntent().getExtras().getString("Mid");
		title = getIntent().getExtras().getString("title");
		time = getIntent().getExtras().getString("time");
		content = getIntent().getExtras().getString("content");
		name = getIntent().getExtras().getString("username");
		sign = getIntent().getExtras().getLong("sign");
		pictureurl = getIntent().getExtras().getString("pictureurl");
		picnum = getIntent().getExtras().getString("picnum");

		System.out.println("MessageActivity" + title + name + time + content + "sign:" + sign);

		showMessage_tv_title.setText(title);
		showMessage_tv_time.setText(time);
		showMessage_tv_content.setText(content);
		showMessage_tv_username.setText(name);
		findViewById(R.id.showMessage_btn_reply).setOnClickListener(clickListener);
		drawImage();
		loadComment();

	}

	public void loadComment() {

		System.out.println("MessageActivity....loadComment");
		try {
			SharedPreferences sharedPreferences = MessageActivity.this.getSharedPreferences(Config.APP_ID,
					Context.MODE_PRIVATE);

			UpAndDown.downloadComment(MessageActivity.this, getCommentHandler, Mid, Config.URL_DOWNLOAD_COMMENT,
					sharedPreferences.getString(Config.KEY_USERNAME, ""),
					sharedPreferences.getString(Config.KEY_TOKEN, ""));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setListViewHeightBasedOnChildren() {
		// 获取ListView对应的Adapter
		ListView listView = getListView();
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		System.out.println("list 的count" + listAdapter.getCount());
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			// listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			// 计算子项View 的宽高
			listItem.measure(0, 0);
			// 统计所有子项的总高度
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

		listView.setLayoutParams(params);
	}

	public Handler getCommentHandler = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {
			System.out.println("TimelineActivity....getTimelineHandler");

			try {
				JSONArray msgJsonArray = new JSONArray(msg.getData().getString("msgs"));
				JSONObject object;
				for (int i = 0; i < msgJsonArray.length(); i++) {
					object = msgJsonArray.getJSONObject(i);
					mComment c = new mComment(object.getString(Config.KEY_REPLY_ID),
							object.getString(Config.KEY_USERNAME), object.getString(Config.KEY_CONTENT),
							object.getString(Config.KEY_REPLY_DATE));

					// 加入list
					Comment.add(c);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("upanddown....downloadcomment...getjson...size" + Comment.size());
			for (int i = 0; i < Comment.size(); i++) {
				System.out.println(Comment.get(i).toString());
			}
			adapter.clear();
			adapter.addAll(Comment);
			setListViewHeightBasedOnChildren();
		}

	};

	public void drawImage() {
		ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup);
		int sum = Integer.parseInt(picnum);
		// ImageView[] imageViews = new ImageView[sum];
		for (int i = 0; i < sum; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setLayoutParams(new LayoutParams(1000, 800));
			imageView.setOnLongClickListener(new PicOnLongClick());
			String img_url;
			img_url = getImageUrl(pictureurl);
			UpAndDown.ImageCache(MessageActivity.this, imageView, img_url + (sign + i) + ".JPEG", 80, 80,
					R.drawable.ic_launcher);
			// imageViews[i] = imageView;
			group.addView(imageView, i);
		}
	}

	public String getImageUrl(String str) {
		Pattern pattern = Pattern.compile("/");
		String[] strs = pattern.split(str);
		return Config.URL_IMAGE_CACHE + "/" + strs[1] + "/" + strs[2] + "/" + strs[3] + "/";

	}

	protected View.OnClickListener clickListener = new View.OnClickListener() {
		@SuppressLint("CommitPrefEdits")
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.showMessage_btn_reply:
				Intent intent = new Intent();
				intent.putExtra("Mid", Mid);
				intent.setClass(MessageActivity.this, ReplyActivity.class);
				startActivityForResult(intent, Config.ACTIVITY_STATUS_REPLY);

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
		case Config.ACTIVITY_STATUS_REPLY:
			if (data.getExtras().getString("status").equals("ok")) {
				System.out.println("TimelineActivity回帖成功");
				Toast.makeText(MessageActivity.this, "回帖成功", Toast.LENGTH_LONG).show();
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						Comment.clear();
						adapter.clear();
						loadComment();

					}
				}, 100);
			} else {
				Toast.makeText(MessageActivity.this, "回帖失败", Toast.LENGTH_LONG).show();
			}

			break;

		default:
			break;
		}
	}

	// 长按图片保存图片
	private class PicOnLongClick implements OnLongClickListener {
		@Override
		public boolean onLongClick(View view) {
			if (longclicktimes > 0) {
				try {
					ImageView iv = (ImageView) view;
					// 启用图形缓冲
					iv.setDrawingCacheEnabled(true);
					// 使用当前缓冲图形创建Bitmap
					Bitmap bmp = Bitmap.createBitmap(iv.getDrawingCache());
					// 保存图片
					String savePath = FileUtils.saveBitmap(bmp, String.valueOf(System.currentTimeMillis()));
					Toast.makeText(getApplicationContext(), "保存路径" + savePath, Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "下载图片失败", Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(getApplicationContext(), "长按下载图片", Toast.LENGTH_LONG).show();
			}
			longclicktimes++;
			return true;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.putExtra("status", "ok");
			setResult(Config.ACTIVITY_STATUS_SHOW_MESSAGE, intent);
			finish();

		}
		return true;
	}

}
