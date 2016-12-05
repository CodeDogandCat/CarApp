package com.qrqc.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.qrqc.chexingduibi.CustomProgressDialog;
import com.qrqc.chexingduibi.HListViewActivity;
import com.qrqc.domain.Car;
import com.qrqc.message.util.FileUtils;
import com.qrqc.util.UpAndDown;

public class CarActivity extends Activity {
	private final List<Car> carlist = new ArrayList<Car>();// 保存可能需要对比的所有车的基本信息
	private final List<Car> carlistSelected = new ArrayList<Car>();// 保存所选对比的所有车的基本信息
	ArrayList<String> duibicarids = new ArrayList<String>();// 保存所有需要查看参数的车ID
	private final ArrayList<String> picurls = new ArrayList<String>();// 保存当前车的所有图片地址
	private boolean[] arrayCarSelected;// 保存保存当前车的所有可能对比车的被选择与否
	private String[] arrayCar;// 保存当前车的所有可能对比车的名称

	private int longclicktimes;
	private int id;
	private String maintype;
	private String secondtype;
	private String priceString;
	private int[] comparecars;
	private TextView name;
	private TextView price;
	private TextView peizhi;
	private TextView zixun;
	private TextView duibi;
	private int sum;
	private CustomProgressDialog dialog = null;
	private Dialog alertDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.aty_car);
		// Toast.makeText(CarActivity.this, Config.USERNAME + Config.TOKEN,
		// Toast.LENGTH_LONG).show();
		dialog = new CustomProgressDialog(this, "正在加载中", R.anim.frame);
		dialog.show();
		init();

	}

	public void init() {

		longclicktimes = 0;
		name = (TextView) findViewById(R.id.car_tv_title);
		price = (TextView) findViewById(R.id.car_tv_price);
		peizhi = (TextView) findViewById(R.id.car_tv_peizhi);
		zixun = (TextView) findViewById(R.id.car_tv_zixun);
		duibi = (TextView) findViewById(R.id.car_tv_duibi);
		maintype = getIntent().getExtras().getString("maintype");
		secondtype = getIntent().getExtras().getString("secondtype");
		comparecars = getIntent().getExtras().getIntArray("comparecars");
		id = getIntent().getExtras().getInt("id");
		System.out.println("///////////////////////////////id是" + id);
		name.setText(maintype + "-" + secondtype);
		peizhi.setOnClickListener(clickListener);
		zixun.setOnClickListener(clickListener);
		duibi.setOnClickListener(clickListener);
		loadCarList();
		getImageUrl();
		arrayCar = new String[] { "1", "1", "3", "4", "5", "6" };
		arrayCarSelected = new boolean[] { true, false, false, false, false, false };
		System.out.println("////////////////////////长度" + comparecars.length);
		// // 转换成车型名称数组
		for (int j = 0; j < comparecars.length; j++) {
			for (int i = 0; i < carlist.size(); i++) {
				if (carlist.get(i).getId() == comparecars[j]) {
					carlistSelected.add(carlist.get(i));
					arrayCar[j] = carlist.get(i).getMaintype() + "-" + carlist.get(i).getSecondtype();
					break;
				}
			}
		}

	}

	public void getprice() {
		UpAndDown.netUtils(Config.BASIC_PRICE_URL + id, getpriceHandler);

	}

	public void getImageUrl() {
		UpAndDown.netUtils(Config.BASIC_IMG_URL + id, getImageUrlHandler);
		System.out.println("/////////////////////////目的地址" + Config.BASIC_IMG_URL + id);
	}

	public Handler getpriceHandler = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {

			if (msg.getData().getString(Config.KEY_NET_STATUS).equals(Config.STATUS_NET_OK)) {
				priceString = msg.getData().getString(Config.KEY_RESULT_STATUS);
				price.setText("￥：" + priceString);

			} else {
				Toast.makeText(getApplicationContext(), Config.STATUS_NET_FAIL, Toast.LENGTH_LONG).show();
				price.setText("￥：" + "暂未获取");
			}
			dialog.dismiss();
		}

	};
	public Handler getImageUrlHandler = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {
			if (msg.getData().getString(Config.KEY_NET_STATUS).equals(Config.STATUS_NET_OK)) {
				String urlsString = msg.getData().getString(Config.KEY_RESULT_STATUS);
				// 去除中括号
				urlsString = urlsString.replace("[", "");
				urlsString = urlsString.replace("]", "");
				System.out.println(urlsString);
				String[] strarray = urlsString.split(",");// 分割
				for (int j = 0; j < 25; j++) {
					String tmpString = strarray[j];
					// 去除双引号
					if (j == 0) {
						tmpString = tmpString.trim().substring(1, strarray[j].length() - 1);
					} else {
						tmpString = tmpString.trim().substring(1, strarray[j].length() - 2);
					}

					System.out.println(tmpString);
					picurls.add(tmpString);
				}

				drawImage();
			} else {
				Toast.makeText(getApplicationContext(), Config.STATUS_NET_FAIL, Toast.LENGTH_LONG).show();
			}
			getprice();

		}

	};

	public void drawImage() {
		ViewGroup group = (ViewGroup) findViewById(R.id.car_viewGroup);

		// ImageView[] imageViews = new ImageView[sum];
		for (int i = 0; i < 25; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setLayoutParams(new LayoutParams(1000, 700));
			imageView.setOnLongClickListener(new PicOnLongClick());
			String img_url;
			img_url = picurls.get(i);
			UpAndDown.ImageCache(CarActivity.this, imageView, img_url, 220, 165, R.drawable.ic_launcher);
			// imageViews[i] = imageView;
			group.addView(imageView, i);
		}
	}

	protected View.OnClickListener clickListener = new View.OnClickListener() {
		@SuppressLint("CommitPrefEdits")
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.car_tv_peizhi:

				Intent i = new Intent();
				String caridString = String.valueOf(id);
				duibicarids.add(caridString);
				i.putStringArrayListExtra("carids", duibicarids);
				startActivityForResult(i.setClass(CarActivity.this, HListViewActivity.class),
						Config.ACTIVITY_STATUS_SHOW_CAR_PEIZHI);

				break;
			case R.id.car_tv_zixun:
				Intent intent7 = new Intent(CarActivity.this, NewslistActivity.class);
				startActivity(intent7);
				break;
			case R.id.car_tv_duibi:
				duibicarids.clear();
				duibicarids.add(String.valueOf(id));
				alertDialog = new AlertDialog.Builder(CarActivity.this)
						.setTitle("选择对比车型(至多5个)")
						.setMultiChoiceItems(arrayCar, arrayCarSelected,
								new DialogInterface.OnMultiChoiceClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which, boolean isChecked) {
										arrayCarSelected[which] = isChecked;
									}
								}).setPositiveButton("确认", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// StringBuilder stringBuilder = new
								// StringBuilder();
								for (int i = 0; i < arrayCarSelected.length; i++) {
									if (arrayCarSelected[i] == true) {
										// stringBuilder.append(arrayCar[i] +
										// "、");
										if (duibicarids.size() < 6) {
											duibicarids.add(String.valueOf(carlistSelected.get(i).getId()));
										}

									}
								}
								// Toast.makeText(CarActivity.this,
								// stringBuilder.toString(),
								// Toast.LENGTH_SHORT).show();
								Intent i = new Intent();
								i.putStringArrayListExtra("carids", duibicarids);
								startActivityForResult(i.setClass(CarActivity.this, HListViewActivity.class),
										Config.ACTIVITY_STATUS_SHOW_CAR_PEIZHI);
							}
						}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
							}
						}).create();
				alertDialog.show();
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
		case Config.ACTIVITY_STATUS_SHOW_CAR_PEIZHI:
			duibicarids.clear();
			// if (data.getExtras().getString("status").equals("ok")) {
			//
			// } else {
			// // Toast.makeText(CarActivity.this, "回帖失败",
			// // Toast.LENGTH_LONG).show();
			// }

			break;
		case Config.ACTIVITY_STATUS_SHOW_CAR_ZIXUN:

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
			picurls.clear();
			finish();

		}
		return true;
	}

	public void loadCarList() {
		// 后面是对比的车
		carlist.add(new Car(24228, "一汽奔腾B30", "2016款1.6L手动舒适型", new int[] { 0 }, 0));
		carlist.add(new Car(24230, "一汽奔腾B30", "2016款1.6L手动尊享型", new int[] { 0 }, 0));
		carlist.add(new Car(23236, "一汽奔腾B30", "2016款1.6L自动尊贵型", new int[] { 0 }, 0));

		carlist.add(new Car(24258, "东风风神A60", "2016款1.5L手动豪华型", new int[] { 0 }, 0));
		carlist.add(new Car(24277, "东风风神A60", "2016款1.5L手动尊贵型", new int[] { 0 }, 0));
		carlist.add(new Car(24045, "东风风神A60", "2016款1.6L自动豪华型", new int[] { 0 }, 0));

		carlist.add(new Car(20857, "凯翼C3", "2015款1.5L手动蓝钻型", new int[] { 0 }, 0));
		carlist.add(new Car(20133, "凯翼C3", "2015款1.5L手动黄钻型", new int[] { 0 }, 0));
		carlist.add(new Car(23629, "凯翼C3", "2015款1.5L ATM金钻型", new int[] { 0 }, 0));

		carlist.add(new Car(19430, "启辰R30", "2014款1.2L手动易享版", new int[] { 0 }, 0));
		carlist.add(new Car(19431, "启辰R30", "2014款1.2L手动舒享版", new int[] { 0 }, 0));
		carlist.add(new Car(18966, "启辰R30", "2014款1.2L手动尊享版", new int[] { 0 }, 0));

		carlist.add(new Car(25363, "长安CS35", "2016款1.6L手动舒适型", new int[] { 0 }, 0));
		carlist.add(new Car(25358, "长安CS35", "2016款1.6L手动尊贵型", new int[] { 0 }, 0));
		carlist.add(new Car(25367, "长安CS35", "2016款1.6L自动尊贵型", new int[] { 0 }, 0));

		carlist.add(new Car(24717, "哈佛H1", "2016款红标1.5L ATM豪华型", new int[] { 0 }, 0));
		carlist.add(new Car(24703, "哈佛H1", "2016款蓝标1.5L 手动豪华型", new int[] { 0 }, 0));
		carlist.add(new Car(24707, "哈佛H1", "2016款蓝标1.5L ATM尊贵型", new int[] { 0 }, 0));

		carlist.add(new Car(24106, "比亚迪宋", "2016款1.5TI手动舒适型", new int[] { 0 }, 0));
		carlist.add(new Car(24102, "比亚迪宋", "2016款2.0TID自动豪华型", new int[] { 0 }, 0));
		carlist.add(new Car(23934, "比亚迪宋", "2016款2.0TID自动旗舰型", new int[] { 0 }, 0));

		carlist.add(new Car(25481, "观致5", "2016款1.6T手动时尚型", new int[] { 0 }, 0));
		carlist.add(new Car(25088, "观致5", "2016款1.6T自动舒适型", new int[] { 0 }, 0));
		carlist.add(new Car(20868, "观致5", "2016款1.6T自动豪华型", new int[] { 0 }, 0));

		carlist.add(new Car(22309, "长安悦翔V3", "2015款1.4L手动美满型", new int[] { 0 }, 0));
		carlist.add(new Car(22310, "长安悦翔V3", "2015款1.4L手动温馨型", new int[] { 0 }, 0));
		carlist.add(new Car(22311, "长安悦翔V3", "2015款1.4L手动幸福型", new int[] { 0 }, 0));

		carlist.add(new Car(21211, "雪佛兰赛欧", "2015款1.3L手动温馨版", new int[] { 0 }, 0));
		carlist.add(new Car(21212, "雪佛兰赛欧", "2015款1.5L手动理想版", new int[] { 0 }, 0));
		carlist.add(new Car(21996, "雪佛兰赛欧", "2015款1.5L ATM幸福天窗版", new int[] { 0 }, 0));

		carlist.add(new Car(24782, "吉利帝豪", "2016款三厢1.5L手动时尚版", new int[] { 0 }, 0));
		carlist.add(new Car(24785, "吉利帝豪", "2016款三厢1.5L CVT豪华版", new int[] { 0 }, 0));
		carlist.add(new Car(24788, "吉利帝豪", "2016款三厢1.3T CVT向上版", new int[] { 0 }, 0));

		carlist.add(new Car(19899, "大众桑塔纳尚纳", "2015款1.4L手动风尚版", new int[] { 0 }, 0));
		carlist.add(new Car(19902, "大众桑塔纳尚纳", "2015款1.6L自动风尚版", new int[] { 0 }, 0));
		carlist.add(new Car(19906, "大众桑塔纳尚纳", "2015款1.6L自动豪华版", new int[] { 0 }, 0));
	}

}
