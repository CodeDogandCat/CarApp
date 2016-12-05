package com.qrqc.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.qrqc.adapter.AtyTimelineMessageListAdapter;
import com.qrqc.domain.mMessage;
import com.qrqc.message.activity.SendMessageActivity;
import com.qrqc.util.UpAndDown;
import com.whos.swiperefreshandload.view.SwipeRefreshLayout;
import com.whos.swiperefreshandload.view.SwipeRefreshLayout.OnLoadListener;
import com.whos.swiperefreshandload.view.SwipeRefreshLayout.OnRefreshListener;

public class TimelineActivity extends ListActivity implements OnRefreshListener, OnLoadListener {

	private AtyTimelineMessageListAdapter adapter = null;
	private final List<mMessage> timeline = new ArrayList<mMessage>();
	SwipeRefreshLayout mSwipeLayout;
	private int page;
	private int totalPage;
	private String username;
	private String token;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_timeline);
		SharedPreferences sharedPreferences = TimelineActivity.this.getSharedPreferences(Config.APP_ID,
				Context.MODE_PRIVATE);
		username = sharedPreferences.getString(Config.KEY_USERNAME, "");
		token = sharedPreferences.getString(Config.KEY_TOKEN, "");
		System.out.println("TimelineActivity....oncreate");
		page = 0;
		totalPage = 0;
		adapter = new AtyTimelineMessageListAdapter(this);
		// 设置分割线的颜色
		getListView().setDivider(new ColorDrawable(Color.LTGRAY));
		getListView().setDividerHeight(1);
		setListAdapter(adapter);
		loadAllMessage();
		findViewById(R.id.tv_send_message).setOnClickListener(clickListener);

		mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setOnLoadListener(this);
		mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_red_light);
		mSwipeLayout.setMode(SwipeRefreshLayout.Mode.BOTH);
		mSwipeLayout.setLoadNoFull(false);
	}

	protected View.OnClickListener clickListener = new View.OnClickListener() {

		@SuppressLint("CommitPrefEdits")
		@Override
		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.tv_send_message:
				Intent intent = new Intent(TimelineActivity.this, SendMessageActivity.class);
				// intent.putExtra("hello", "你好");
				startActivityForResult(intent, Config.ACTIVITY_STATUS_SEND_MESSAGE);
				break;

			default:
				break;
			}
		}

	};

	public Handler loadAllMessageHandler = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {
			System.out.println("TimelineActivity....getTimelineHandler");

			try {
				JSONArray msgJsonArray = new JSONArray(msg.getData().getString("msgs"));
				JSONObject object;
				for (int i = 0; i < msgJsonArray.length(); i++) {
					object = msgJsonArray.getJSONObject(i);
					mMessage m = new mMessage(object.getString(Config.KEY_MSG_ID),
							object.getString(Config.KEY_USERNAME), object.getString(Config.KEY_TITLE),
							object.getString(Config.KEY_CONTENT), object.getString(Config.KEY_PICTUREURL),
							object.getString(Config.KEY_MSG_DATE), object.getString(Config.KEY_REPLY_NUM),
							object.getString(Config.KEY_PICNUM));

					// 加入list
					timeline.add(m);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("upanddown....downloadTimeline...getjson...size" + timeline.size());
			// for (int i = 0; i < timeline.size(); i++) {
			// System.out.println(timeline.get(i).toString());
			// }
			adapter.clear();
			adapter.addAll(timeline);
		}

	};

	public Handler loadAddtionalMessageHandler = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {
			System.out.println("TimelineActivity....getTimelineHandler");

			try {
				JSONArray msgJsonArray = new JSONArray(msg.getData().getString("msgs"));
				JSONObject object;
				for (int i = 0; i < msgJsonArray.length(); i++) {
					object = msgJsonArray.getJSONObject(i);
					mMessage m = new mMessage(object.getString(Config.KEY_MSG_ID),
							object.getString(Config.KEY_USERNAME), object.getString(Config.KEY_TITLE),
							object.getString(Config.KEY_CONTENT), object.getString(Config.KEY_PICTUREURL),
							object.getString(Config.KEY_MSG_DATE), object.getString(Config.KEY_REPLY_NUM),
							object.getString(Config.KEY_PICNUM));

					// 加入list
					timeline.add(m);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("upanddown....loadAddtionalMessage...getjson...size" + timeline.size());
			// for (int i = 0; i < timeline.size(); i++) {
			// System.out.println(timeline.get(i).toString());
			// }

			adapter.addAll(timeline);
		}

	};

	public Handler getTotalPageHandler = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {
			System.out.println("TimelineActivity....getTotalPageHandler");

			String totalPageString = msg.getData().getString("msgs");
			if (totalPageString != null) {
				totalPage = Integer.parseInt(totalPageString);
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + page + "/" + totalPage);
				// 和page比较，如果page+2<=totalpage,就加载下一页，这个时候不要清空timeline
				if (page + 1 <= totalPage) {

					// timeline清空，adapter不要清空
					timeline.clear();
					loadAddtionalMessage();

				} else {
					// 否则提示没有了
					Toast.makeText(TimelineActivity.this, "没有了", Toast.LENGTH_LONG).show();

				}
			}

		}

	};

	private void loadAllMessage() {// 下载消息列表内容
		System.out.println("TimelineActivity....loadMessage");
		try {
			UpAndDown.downloadTimeline(TimelineActivity.this, loadAllMessageHandler, page, Config.URL_DOWNLOAD_TEXT,
					username, token);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void loadAddtionalMessage() {
		System.out.println("TimelineActivity....loadAddtionalMessage");
		try {
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@loadAddtionalMessage下一个要加载的page" + page);
			UpAndDown.downloadTimeline(TimelineActivity.this, loadAddtionalMessageHandler, page,
					Config.URL_DOWNLOAD_TEXT, username, token);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getTotalPage() {
		System.out.println("TimelineActivity....getTotalPage");
		try {
			UpAndDown.getTotalPage(TimelineActivity.this, getTotalPageHandler, Config.URL_GET_MESSAGE_PAGES, username,
					token);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		mMessage msg = adapter.getItem(position);
		Intent i = new Intent(TimelineActivity.this, MessageActivity.class);
		System.out.println("点击了messa" + msg.getTitle());
		i.putExtra("Mid", msg.getId());
		i.putExtra("title", msg.getTitle());
		i.putExtra("username", msg.getUsername());
		i.putExtra("time", msg.getDate());
		i.putExtra("content", msg.getContent());
		i.putExtra("pictureurl", msg.getPictureurl());
		i.putExtra("sign", msg.getSign());
		i.putExtra("picnum", msg.getPicnum());
		startActivityForResult(i, Config.ACTIVITY_STATUS_SHOW_MESSAGE);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("/////////////////////////////////////resultCode" + requestCode);
		switch (resultCode) {

		case Config.ACTIVITY_STATUS_SEND_MESSAGE:
			System.out.println("/////////////////////////////////////TimelineActivity发帖");
			if (data.getExtras().getString("status").equals("ok")) {
				System.out.println("/////////////////////////////////////TimelineActivity发帖成功");

				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						timeline.clear();
						adapter.clear();
						loadAllMessage();
						// System.out.println("TimelineActivity---run");
					}
				}, 500);
				Toast.makeText(TimelineActivity.this, "发帖成功", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(TimelineActivity.this, "发帖失败", Toast.LENGTH_LONG).show();
			}

			break;
		case Config.ACTIVITY_STATUS_SHOW_MESSAGE:
			System.out.println("/////////////////////////////////////TimelineActivity查看成功");
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					timeline.clear();
					adapter.clear();
					loadAllMessage();
					// System.out.println("TimelineActivity---run");
				}
			}, 100);
			break;

		default:
			break;
		}

	}

	@Override
	public void onLoad() {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				mSwipeLayout.setLoading(false);
				++page;
				getTotalPage();

			}
		}, 1000);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				mSwipeLayout.setRefreshing(false);
				page = 0;
				timeline.clear();
				adapter.clear();
				loadAllMessage();

			}
		}, 500);
	}
}
