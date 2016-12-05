package com.qrqc.chexingduibi;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.qrqc.activity.Config;
import com.qrqc.activity.R;
import com.qrqc.util.UpAndDown;

/**
 * 
 * 带滑动表头与固定列的ListView
 */
public class HListViewActivity extends Activity {
	private ArrayList<String> duibicarids = new ArrayList<String>();
	private final ArrayList<String> canshutypeList = new ArrayList<String>();
	private final String[][] canshuStrings = new String[194][6];
	private ListView mListView;
	// 方便测试，直接写的public
	public HorizontalScrollView mTouchView;
	// 装入所有的HScrollView
	protected List<CHScrollView2> mHScrollViews = new ArrayList<CHScrollView2>();
	private final String[] cols = new String[] { "title", "data_1", "data_2", "data_3", "data_4", "data_5", "data_6" };
	private ScrollAdapter mAdapter;
	private CustomProgressDialog dialog = null;
	public Handler getCanshuHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			System.out.println("handling..........................");
			// System.out.println(jsonData);
			if (msg.getData().getString("state") == "网络连接失败") {
				Toast.makeText(HListViewActivity.this, "网络异常，请重试", Toast.LENGTH_LONG).show();
				return;
			} else {
				try {
					System.out.println(msg.getData().getString("state"));
					ArrayList<String> jsonData = msg.getData().getStringArrayList("content");
					System.out.println(jsonData.toString());
					int[] array_hengTitle = { R.id.btn_car0, R.id.btn_car1, R.id.btn_car2, R.id.btn_car3,
							R.id.btn_car4, R.id.btn_car5 };
					// 添加数据的列初始化
					for (int index = 0; index < jsonData.size(); index++) {
						JSONArray jsonArray = new JSONArray(jsonData.get(index));
						ArrayList<String> temp = new ArrayList<String>();
						// 小标题行，没有参数
						int[] indexOfTitle = { 0, 3, 19, 33, 56, 60, 66, 80, 95, 105, 119, 137, 150, 162, 176, 183, 194 };
						// 添加横向标题
						TextView hengTitle = (TextView) findViewById(array_hengTitle[index]);
						// hengTitle.setOnClickListener(clickListener);
						hengTitle.setText(jsonArray.getJSONObject(0).getString(canshutypeList.get(1)));
						// 把参数存放在 temp
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject object = jsonArray.getJSONObject(i);
							// jump的长度比jsonArray 大 1
							for (int j = indexOfTitle[i] + 1; j < indexOfTitle[i + 1]; j++) {
								System.out.println(canshutypeList.get(j) + ".............");// 输出“车型信息”
								System.out.println(object.toString() + "到底有没有");
								System.out.println(object.getString(canshutypeList.get(j)).replaceAll("&nbsp;/&nbsp;",
										" "));
								temp.add(object.getString(canshutypeList.get(j)).replaceAll("&nbsp;/&nbsp;", " "));// 注意从文件读取到的键值的多余空格
							}

						}
						// for (int j = 0; j < temp.size(); j++) {
						// System.out.println("temp" + j + temp.get(j));
						// }
						// 构造对标表格参数部分的 二维数组，跳过标题行
						int canshuzunum = 0;
						for (int i = 0; i < 194; ++i) {
							// 如果当前为小标题行
							if (Arrays.binarySearch(indexOfTitle, i) >= 0) {
								System.out.println("小标题" + i);
								canshuStrings[i][index] = "*";// 表格为*
							} else {

								canshuStrings[i][index] = temp.get(canshuzunum);
								++canshuzunum;
							}
						}
					}

					// 未添加数据的列初始化
					for (int k = 0; k < 194; k++) {
						for (int k2 = jsonData.size(); k2 < 6; k2++) {
							canshuStrings[k][k2] = "*";
						}
					}
					// 输出数据
					for (int k = 0; k < 194; k++) {
						for (int k2 = 0; k2 < 6; k2++) {
							System.out.println("第" + (k + 1) + "行," + "第" + (k2 + 1) + "列：" + canshuStrings[k][k2]);
						}
					}
					System.out.println("initViews........");
					List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
					Map<String, String> data = null;
					CHScrollView2 headerScroll = (CHScrollView2) findViewById(R.id.item_scroll_title);
					// 添加头滑动事件
					mHScrollViews.add(headerScroll);
					mListView = (ListView) findViewById(R.id.hlistview_scroll_list);
					// 填充表格
					for (int i = 0; i < 194; i++) {
						data = new HashMap<String, String>();
						data.put("title", canshutypeList.get(i));
						for (int j = 0; j < 6; j++) {
							data.put("data_" + (j + 1), canshuStrings[i][j]);
						}

						datas.add(data);
					}

					mAdapter = new ScrollAdapter(HListViewActivity.this, datas, R.layout.common_item_hlistview// R.layout.item
							, cols, new int[] { R.id.item_titlev, R.id.item_datav1, R.id.item_datav2, R.id.item_datav3,
									R.id.item_datav4, R.id.item_datav5, R.id.item_datav6 });
					mListView.setAdapter(mAdapter);
					dialog.dismiss();
					System.out.println("setAdapter........");

				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(HListViewActivity.this, "数据异常，请等待后重试", Toast.LENGTH_LONG).show();
				}
			}

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		dialog = new CustomProgressDialog(this, "正在加载中", R.anim.frame);
		dialog.show();

		setContentView(R.layout.activity_hlistview);
		// 接受传递过来的intent 参数 caridsList
		duibicarids = this.getIntent().getStringArrayListExtra("carids");
		System.out.println(duibicarids.get(0));
		// 加载canshutype到数组
		initCanshutype();
		System.out.println(canshutypeList.get(0));
		// 获取json参数,并有handler 初始化参数对比界面
		getCanshuJSON();
		TextView btn_back = (TextView) findViewById(R.id.btn_gototianjia);
		btn_back.setOnClickListener(clickListener);

	}

	public void initCanshutype() {
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null; // 用于包装InputStreamReader,提高处理性能。因为BufferedReader有缓冲的，而InputStreamReader没有。
		try {
			String str = "";
			is = getResources().openRawResource(R.raw.canshutype);
			// 从文件系统中的某个文件中获取字节
			isr = new InputStreamReader(is);// InputStreamReader 是字节流通向字符流的桥梁,
			br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new
											// InputStreamReader的对象
			while ((str = br.readLine()) != null) {
				canshutypeList.add(str);
			}
			// 当读取的一行不为空时,把读到的str的值赋给str1
		} catch (FileNotFoundException e) {
			System.out.println("找不到指定文件");
		} catch (IOException e) {
			System.out.println("读取文件失败");
		} finally {
			try {
				br.close();
				isr.close();
				is.close();
				// 关闭的时候最好按照先后顺序关闭最后开的先关闭所以先关s,再关n,最后关m
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void getCanshuJSON() {

		UpAndDown.getCanshuJSON(Config.BASIC_CANSHU_URL, getCanshuHandler, duibicarids);
	}

	public void addHViews(final CHScrollView2 hScrollView) {
		if (!mHScrollViews.isEmpty()) {
			int size = mHScrollViews.size();
			CHScrollView2 scrollView = mHScrollViews.get(size - 1);
			final int scrollX = scrollView.getScrollX();
			// 第一次满屏后，向下滑动，有一条数据在开始时未加入
			if (scrollX != 0) {
				mListView.post(new Runnable() {
					@Override
					public void run() {
						// 当listView刷新完成之后，把该条移动到最终位置
						hScrollView.scrollTo(scrollX, 0);
					}
				});
			}
		}
		mHScrollViews.add(hScrollView);
	}

	public void onScrollChanged(int l, int t, int oldl, int oldt) {
		for (CHScrollView2 scrollView : mHScrollViews) {
			// 防止重复滑动
			if (mTouchView != scrollView)
				scrollView.smoothScrollTo(l, t);
		}
	}

	class ScrollAdapter extends SimpleAdapter {

		private final List<? extends Map<String, ?>> datas;
		private final int res;
		private final String[] from;
		private final int[] to;
		private final Context context;

		public ScrollAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
			super(context, data, resource, from, to);
			this.context = context;
			this.datas = data;
			this.res = resource;
			this.from = from;
			this.to = to;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				v = LayoutInflater.from(context).inflate(res, null);
				// 第一次初始化的时候装进来
				addHViews((CHScrollView2) v.findViewById(R.id.item_chscroll_scroll));
				View[] views = new View[to.length];
				// 单元格点击事件
				for (int i = 0; i < to.length; i++) {
					View tv = v.findViewById(to[i]);
					// tv.setOnClickListener(clickListener);
					views[i] = tv;
				}
				// 每行点击事件
				/*
				 * for(int i = 0 ; i < from.length; i++) { View tv =
				 * v.findViewById(row_hlistview[i]); }
				 */
				//
				v.setTag(views);
			}
			View[] holders = (View[]) v.getTag();
			int len = holders.length;
			for (int i = 0; i < len; i++) {
				((TextView) holders[i]).setText(this.datas.get(position).get(from[i]).toString());
			}
			return v;
		}
	}

	// 测试点击的事件
	protected View.OnClickListener clickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_gototianjia:

				Intent intent = new Intent();
				// intent.putStringArrayListExtra("carids", duibicarids);
				setResult(Config.ACTIVITY_STATUS_SHOW_CAR_PEIZHI, intent);
				finish();
				break;

			default:
				break;
			}
		}
	};
}
