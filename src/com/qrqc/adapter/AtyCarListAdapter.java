package com.qrqc.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qrqc.activity.R;
import com.qrqc.domain.Car;
import com.squareup.picasso.Picasso;

public class AtyCarListAdapter extends BaseAdapter {

	public AtyCarListAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {

		return data.size();
	}

	@Override
	public Car getItem(int position) {

		return data.get(position);
	}

	@Override
	public long getItemId(int arg0) {

		return arg0;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup parent) {

		if (convertview == null) {
			convertview = LayoutInflater.from(getContext()).inflate(R.layout.aty_car_list_cell, null);
			convertview.setTag(new listCellHolder((TextView) convertview.findViewById(R.id.Carlist_tv_title__cell),
					(ImageView) convertview.findViewById(R.id.Carlist_ImageView_cell), (TextView) convertview
							.findViewById(R.id.Carlist_tv_price__cell)));
		}
		// 我们都知道在getView方法中的操作是这样的：先从xml中创建view对象（inflate操作，我们采用了重用convertView方法优化），然后在这个view去findViewById，
		// 找到每一个子View，如：一个TextView等。这里的findViewById操作是一个树查找过程，也是一个耗时的操作，所以这里也需要优化，就是使用viewHolder，
		// 把每一个子View都放在Holder中，当第一次创建convertView对象时，把这些子view找出来。然后用convertView的setTag将viewHolder设置到Tag中，
		// 以便系统第二次绘制ListView时从Tag中取出。当第二次重用convertView时，只需从convertView中getTag取出来就可以。
		listCellHolder holder = (listCellHolder) convertview.getTag();
		Car car = getItem(position);

		holder.getTv_title__cell().setText(car.getMaintype() + "-" + car.getSecondtype());
		Picasso.with(context).load(car.getDraw()).into(holder.getImageView_cell());
		holder.getTv_price__cell().setText("");

		return convertview;
	}

	public Context getContext() {
		return context;
	}

	public void addAll(List<Car> cars) {
		this.data.addAll(cars);
		notifyDataSetChanged();// 数据有改变

	}

	public void clear() {
		data.clear();
		notifyDataSetChanged();
	}

	private static class listCellHolder {

		@SuppressWarnings("unused")
		private final TextView tv_title__cell;
		private final ImageView imageView_cell;

		private final TextView tv_price__cell;

		public TextView getTv_title__cell() {
			return tv_title__cell;
		}

		public ImageView getImageView_cell() {
			return imageView_cell;
		}

		public TextView getTv_price__cell() {
			return tv_price__cell;
		}

		@SuppressWarnings("unused")
		public listCellHolder(TextView tv_title__cell, ImageView imageView_cell, TextView tv_price__cell) {
			super();
			this.tv_title__cell = tv_title__cell;
			this.imageView_cell = imageView_cell;
			this.tv_price__cell = tv_price__cell;
		}

	}

	private final List<Car> data = new ArrayList<Car>();
	private Context context = null;

}
