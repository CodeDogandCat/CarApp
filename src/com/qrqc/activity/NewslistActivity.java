package com.qrqc.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;

import com.qrqc.adapter.AtyNewsListAdapter;
import com.qrqc.domain.News;
import com.qrqc.util.UpAndDown;

public class NewslistActivity extends ListActivity {
	private String username;
	private String token;
	private AtyNewsListAdapter adapter = null;
	private final List<News> newslist = new ArrayList<News>();

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_newslist);
		System.out.println("TimelineActivity....oncreate");
		SharedPreferences sharedPreferences = NewslistActivity.this.getSharedPreferences(Config.APP_ID,
				Context.MODE_PRIVATE);
		username = sharedPreferences.getString(Config.KEY_USERNAME, "");
		token = sharedPreferences.getString(Config.KEY_TOKEN, "");
		adapter = new AtyNewsListAdapter(this);
		// 设置分割线的颜色
		getListView().setDivider(new ColorDrawable(Color.LTGRAY));
		getListView().setDividerHeight(1);
		setListAdapter(adapter);
		downLoadNews();
	}

	private void downLoadNews() {
		// TODO Auto-generated method stub
		System.out.println("NewsListActivity....downLoadNews");
		try {
			UpAndDown.downloadAd(NewslistActivity.this, loadNewsHandler, Config.URL_NEWS, username, token);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Handler loadNewsHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			System.out.println("loadNewsHandler....loadNewsHandler");

			try {
				JSONArray msgJsonArray = new JSONArray(msg.getData().getString("msgs"));
				JSONObject object;
				for (int i = 0; i < msgJsonArray.length(); i++) {
					object = msgJsonArray.getJSONObject(i);

					News news = new News();
					news.setId(object.getString("id"));
					news.setDate(object.getString("date"));
					news.setTitle(object.getString("title"));
					news.setImgUrl(object.getString("imgUrl"));
					news.setTargetUrl(object.getString("targetUrl"));
					news.setAd(true);
					System.out.println(news.toString());
					// 加入list
					newslist.add(news);

				}
				System.out.println("///////" + newslist.size());
				adapter.clear();
				adapter.addAll(newslist);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	};

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		News news = adapter.getItem(position);
		Uri uri = Uri.parse(news.getTargetUrl());
		Intent it = new Intent(Intent.ACTION_VIEW, uri);
		startActivityForResult(it, 0);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("/////////////////////////////////////resultCode" + requestCode);
		switch (resultCode) {
		default:
			break;
		}

	}
}
