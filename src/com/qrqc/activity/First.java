package com.qrqc.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.qrqc.domain.News;
import com.qrqc.util.UpAndDown;

public class First extends Activity {

	private int count;

	public static String IMAGE_CACHE_PATH = "imageloader/Cache"; // 图片缓存路径

	private ViewPager adViewPager;
	private List<ImageView> imageViews;// 滑动的图片集合

	private List<View> dots; // 图片标题正文的那些点
	private List<View> dotList;

	private TextView tv_date;
	private TextView tv_title;
	private TextView tv_topic_from;
	private TextView tv_topic;
	private int currentItem = 0; // 当前图片的索引号
	// 定义的五个指示点
	private View dot0;
	private View dot1;
	private View dot2;
	private View dot3;
	private View dot4;

	private ScheduledExecutorService scheduledExecutorService;

	// 异步加载图片
	private ImageLoader mImageLoader;
	private DisplayImageOptions options;

	// 轮播banner的数据
	private List<News> adList;

	private final Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			adViewPager.setCurrentItem(currentItem);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_first);
		count = 0;
		// 使用ImageLoader之前初始化
		initImageLoader();

		// 获取图片加载实例
		mImageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder().showStubImage(R.drawable.top_banner_android)
				.showImageForEmptyUri(R.drawable.top_banner_android).showImageOnFail(R.drawable.top_banner_android)
				.cacheInMemory(true).cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.EXACTLY).build();
		getBannerAd();
		// setContentView(R.layout.activity_hello);
		// Toast.makeText(Hello.this, Config.USERNAME + Config.TOKEN,
		// Toast.LENGTH_LONG).show();
		findViewById(R.id.btn_tuichu).setOnClickListener(clickListener);

		findViewById(R.id.btn_tuichu).setOnClickListener(clickListener);
		findViewById(R.id.btn_chexingbaojia).setOnClickListener(clickListener);
		findViewById(R.id.btn_cheyouluntan).setOnClickListener(clickListener);
		findViewById(R.id.btn_shangjiafuwu).setOnClickListener(clickListener);
		findViewById(R.id.btn_xinwenzixun).setOnClickListener(clickListener);
		findViewById(R.id.btn_gongsijianjie).setOnClickListener(clickListener);
		findViewById(R.id.btn_lianxiwomen).setOnClickListener(clickListener);
	}

	protected View.OnClickListener clickListener = new View.OnClickListener() {

		@SuppressLint("CommitPrefEdits")
		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			// 0.退出登录
			case R.id.btn_tuichu:
				// TODO Auto-generated method stub

				new AlertDialog.Builder(First.this).setTitle("提示")// 设置对话框标题

						.setMessage("确认要退出账号吗？").setCancelable(false)// 设置显示的内容

						.setPositiveButton("确定", new DialogInterface.OnClickListener() {// 添加确定按钮

									@Override
									public void onClick(DialogInterface dialog, int which) {// 确定按钮的响应事件

										Editor e = First.this.getSharedPreferences(Config.APP_ID, Context.MODE_PRIVATE)
												.edit();
										e.remove(Config.KEY_TOKEN);
										e.remove(Config.KEY_USERNAME);
										e.commit();
										startActivity(new Intent(First.this, MainActivity.class));
										finish();
									}

								}).setNegativeButton("取消", new DialogInterface.OnClickListener() {// 添加返回按钮

									@Override
									public void onClick(DialogInterface dialog, int which) {// 响应事件

										// TODO Auto-generated method stub

									}

								}).show();// 在按键响应事件中显示此对话框

				break;

			// 1.车型报价
			case R.id.btn_chexingbaojia:
				Intent intent1 = new Intent(First.this, CarlistActivity.class);
				startActivity(intent1);
				break;
			// 2.车友论坛
			case R.id.btn_cheyouluntan:
				Intent intent2 = new Intent(First.this, TimelineActivity.class);
				startActivity(intent2);
				break;

			// 3.商家服务
			case R.id.btn_shangjiafuwu:
				Intent intent3 = new Intent(First.this, ShangjiafuwuActivity.class);
				startActivity(intent3);
				break;

			// 4.新闻资讯
			case R.id.btn_xinwenzixun:
				// Toast.makeText(getApplicationContext(), "不要着急哦，还没实现︿(￣︶￣)︿",
				// Toast.LENGTH_SHORT).show();
				Intent intent7 = new Intent(First.this, NewslistActivity.class);
				startActivity(intent7);
				break;
			// 5.企业介绍
			case R.id.btn_gongsijianjie:
				Intent intent5 = new Intent(First.this, GongsijieshaoActivity.class);
				startActivity(intent5);
				break;
			// 6 .联系我们
			case R.id.btn_lianxiwomen:
				Intent intent6 = new Intent(First.this, LianxiwomenActivity.class);
				startActivity(intent6);
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

		default:
			break;
		}
	}

	private final Handler getAdHandler = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {
			System.out.println("getAdHandler....getAdHandler");

			try {
				JSONArray msgJsonArray = new JSONArray(msg.getData().getString("msgs"));
				JSONObject object;
				for (int i = 0; i < msgJsonArray.length(); i++) {
					object = msgJsonArray.getJSONObject(i);

					News news = new News();
					news.setId(object.getString("id"));
					news.setDate(object.getString("date"));
					news.setTitle(object.getString("title"));
					news.setTopicFrom(object.getString("topicFrom"));
					news.setTopic(object.getString("topic"));
					news.setImgUrl(object.getString("imgUrl"));
					news.setTargetUrl(object.getString("targetUrl"));
					news.setAd(true);
					System.out.println(news.toString());
					// 加入list
					adList.add(news);

				}
				System.out.println("///////" + adList.size());
				// 广告数据
				initAdData();

				startAd();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	};

	private void initImageLoader() {
		File cacheDir = com.nostra13.universalimageloader.utils.StorageUtils.getOwnCacheDirectory(
				getApplicationContext(), IMAGE_CACHE_PATH);

		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true)
				.build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
				.defaultDisplayImageOptions(defaultOptions).memoryCache(new LruMemoryCache(12 * 1024 * 1024))
				.memoryCacheSize(12 * 1024 * 1024).discCacheSize(32 * 1024 * 1024).discCacheFileCount(100)
				.discCache(new UnlimitedDiscCache(cacheDir)).threadPriority(Thread.NORM_PRIORITY - 2)
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();

		ImageLoader.getInstance().init(config);
	}

	private void initAdData() {

		imageViews = new ArrayList<ImageView>();

		// 点
		dots = new ArrayList<View>();
		dotList = new ArrayList<View>();
		dot0 = findViewById(R.id.v_dot0);
		dot1 = findViewById(R.id.v_dot1);
		dot2 = findViewById(R.id.v_dot2);
		dot3 = findViewById(R.id.v_dot3);
		dot4 = findViewById(R.id.v_dot4);
		dots.add(dot0);
		dots.add(dot1);
		dots.add(dot2);
		dots.add(dot3);
		dots.add(dot4);

		addDynamicView();

		tv_date = (TextView) findViewById(R.id.tv_date);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_topic_from = (TextView) findViewById(R.id.tv_topic_from);
		tv_topic = (TextView) findViewById(R.id.tv_topic);

		adViewPager = (ViewPager) findViewById(R.id.vp);
		adViewPager.setAdapter(new MyAdapter());// 设置填充ViewPager页面的适配器
		// 设置一个监听器，当ViewPager中的页面改变时调用
		adViewPager.setOnPageChangeListener(new MyPageChangeListener());

	}

	private void addDynamicView() {
		System.out.println("///////addDynamicView" + adList.size());
		System.out.println("@@@@@@@addDynamicView" + imageViews.size());
		// 动态添加图片和下面指示的圆点
		// 初始化图片资源
		for (int i = 0; i < adList.size(); i++) {
			ImageView imageView = new ImageView(this);
			// 异步加载图片
			mImageLoader.displayImage(adList.get(i).getImgUrl(), imageView, options);
			imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);// 把图片按比例扩大/缩小到View的宽度，居中显示
			imageViews.add(imageView);
			dots.get(i).setVisibility(View.VISIBLE);
			dotList.add(dots.get(i));
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void startAd() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		// 当Activity显示出来后，每两秒切换一次图片显示
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2, TimeUnit.SECONDS);
	}

	private class ScrollTask implements Runnable {

		@Override
		public void run() {
			synchronized (adViewPager) {
				currentItem = (currentItem + 1) % imageViews.size();
				handler.obtainMessage().sendToTarget();
			}
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		// 当Activity不可见的时候停止切换
		// scheduledExecutorService.shutdown();
	}

	private class MyPageChangeListener implements OnPageChangeListener {

		private int oldPosition = 0;

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int position) {
			currentItem = position;
			News news = adList.get(position);
			tv_title.setText(news.getTitle()); // 设置标题
			tv_date.setText(news.getDate());
			tv_topic_from.setText(news.getTopicFrom());
			tv_topic.setText(news.getTopic());
			dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			oldPosition = position;
		}
	}

	private class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return adList.size();
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView iv = imageViews.get(position);
			((ViewPager) container).addView(iv);
			final News news = adList.get(position);
			// 在这个方法里面设置图片的点击事件
			iv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 处理跳转逻辑
					Uri uri = Uri.parse(news.getTargetUrl());
					Intent it = new Intent(Intent.ACTION_VIEW, uri);
					startActivityForResult(it, 0);
				}
			});
			return iv;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}

	}

	/**
	 * 轮播广播模拟数据
	 * 
	 * @return
	 */
	public void getBannerAd() {
		adList = new ArrayList<News>();
		// adList.clear();
		try {
			UpAndDown.downloadAd(getApplicationContext(), getAdHandler, Config.URL_AD, " ", " ");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
