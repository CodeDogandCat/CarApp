package com.qrqc.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.qrqc.adapter.AtyCarListAdapter;
import com.qrqc.domain.Car;

public class CarlistActivity extends ListActivity {

	private AtyCarListAdapter adapter = null;
	private final List<Car> carlist = new ArrayList<Car>();

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_carlist);
		System.out.println("TimelineActivity....oncreate");

		adapter = new AtyCarListAdapter(this);
		// 设置分割线的颜色
		getListView().setDivider(new ColorDrawable(Color.LTGRAY));
		getListView().setDividerHeight(1);
		setListAdapter(adapter);
		loadAllMessage();

	}

	// public Handler loadAllMessageHandler = new Handler() {
	//
	// @Override
	// public void handleMessage(android.os.Message msg) {
	// System.out.println("TimelineActivity....getTimelineHandler");
	//
	// try {
	// JSONArray msgJsonArray = new JSONArray(msg.getData().getString("msgs"));
	// JSONObject object;
	//
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// System.out.println("upanddown....downloadTimeline...getjson...size" +
	// carlist.size());
	//
	// adapter.clear();
	// adapter.addAll(carlist);
	// }
	//
	// };

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Car car = adapter.getItem(position);
		Intent i = new Intent(CarlistActivity.this, CarActivity.class);
		System.out.println("点击了" + car.getMaintype() + "-" + car.getSecondtype());
		i.putExtra("comparecars", car.getCompareCars());
		i.putExtra("maintype", car.getMaintype());
		i.putExtra("secondtype", car.getSecondtype());
		i.putExtra("id", car.getId());
		startActivityForResult(i, Config.ACTIVITY_STATUS_SHOW_CAR);

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

				Toast.makeText(CarlistActivity.this, "发帖成功", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(CarlistActivity.this, "发帖失败", Toast.LENGTH_LONG).show();
			}

			break;

		default:
			break;
		}

	}

	private void loadAllMessage() {// 初始化列表内容
		System.out.println("carlistActivity....loadMessage");
		// 总共十九个
		// 前七个是奇瑞的车
		carlist.add(new Car(25227, "艾瑞泽5", "2016款1.5L手动领尚版", new int[] { 24228, 24230, 23236, 24258, 24277, 24045 },
				R.drawable.c25227));
		carlist.add(new Car(25229, "艾瑞泽5", "2016款1.5L手动领锐版", new int[] { 24228, 24230, 23236, 24258, 24277, 24045 },
				R.drawable.c25229));
		carlist.add(new Car(24552, "艾瑞泽5", "2016款1.5L自动领臻版", new int[] { 24228, 24230, 23236, 24258, 24277, 24045 },
				R.drawable.c24552));

		carlist.add(new Car(23654, "风云2 ", "2015款1.5L手动新意版", new int[] { 20857, 20133, 23629, 19430, 19431, 18966 },
				R.drawable.c23654));
		carlist.add(new Car(23653, "风云2 ", "2015款1.5L手动新锐版", new int[] { 20857, 20133, 23629, 19430, 19431, 18966 },
				R.drawable.c23653));
		carlist.add(new Car(24481, "风云2 ", "2016款1.5L手动超值版", new int[] { 20857, 20133, 23629, 19430, 19431, 18966 },
				R.drawable.c24481));

		carlist.add(new Car(22672, "新瑞虎3", "2015款1.6L手动风尚运动版", new int[] { 25363, 25358, 25367, 24717, 24703, 24707 },
				R.drawable.c22672));
		carlist.add(new Car(22674, "新瑞虎3", "2015款1.6L手动尊尚运动版", new int[] { 25363, 25358, 25367, 24717, 24703, 24707 },
				R.drawable.c22674));
		carlist.add(new Car(24361, "新瑞虎3", "2015款1.6L CVT尊尚纪念版",
				new int[] { 25363, 25358, 25367, 24717, 24703, 24707 }, R.drawable.c24361));

		carlist.add(new Car(24274, "新瑞虎5 ", "2016款2.0L手动家享版", new int[] { 24106, 24102, 23934, 25481, 25088, 20868 },
				R.drawable.c24274));
		carlist.add(new Car(25444, "新瑞虎5 ", "2016款2.0L CVT家尊信赖版",
				new int[] { 24106, 24102, 23934, 25481, 25088, 20868 }, R.drawable.c25444));
		carlist.add(new Car(25446, "新瑞虎5 ", "2016款2.0L CVT荣耀信赖版",
				new int[] { 24106, 24102, 23934, 25481, 25088, 20868 }, R.drawable.c25446));

		carlist.add(new Car(22657, "瑞虎5", "2015款2.0L手动家享版", new int[] { 24106, 24102, 23934, 25481, 25088, 20868 },
				R.drawable.c22657));
		carlist.add(new Car(22661, "瑞虎5", "2015款2.0L CVT家悦版", new int[] { 24106, 24102, 23934, 25481, 25088, 20868 },
				R.drawable.c22661));
		carlist.add(new Car(22664, "瑞虎5", "2015款2.0L CVT家臻版", new int[] { 24106, 24102, 23934, 25481, 25088, 20868 },
				R.drawable.c22664));

		carlist.add(new Car(21805, "E3", "2015款1.5L手动趣尚版", new int[] { 22309, 22310, 22311, 21211, 21212, 21996 },
				R.drawable.c21805));
		carlist.add(new Car(21806, "E3", "2015款1.5L手动风尚版", new int[] { 22309, 22310, 22311, 21211, 21212, 21996 },
				R.drawable.c21806));
		carlist.add(new Car(21803, "E3", "2015款1.5L手动实尚版", new int[] { 22309, 22310, 22311, 21211, 21212, 21996 },
				R.drawable.c21803));

		carlist.add(new Car(22056, "艾瑞泽7", "2015款1.6L手动致领版", new int[] { 24782, 24785, 24788, 19899, 19902, 19906 },
				R.drawable.c22056));
		carlist.add(new Car(22060, "艾瑞泽7", "2015款1.6L CVT致尚版", new int[] { 24782, 24785, 24788, 19899, 19902, 19906 },
				R.drawable.c22060));
		carlist.add(new Car(21992, "艾瑞泽7", "2015款1.5T手动致尊版", new int[] { 24782, 24785, 24788, 19899, 19902, 19906 },
				R.drawable.c21992));

		adapter.addAll(carlist);

	}
}
