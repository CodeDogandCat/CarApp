package com.juhe.petrolstation.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.juhe.petrolstation.bean.Petrol;
import com.qrqc.activity.R;

public class PriceListAdapter extends BaseAdapter {

	private final List<Petrol> list;
	private final LayoutInflater mInflater;

	public PriceListAdapter(Context context, List<Petrol> list) {
		this.list = list;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Petrol getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View rowView = null;
		if (convertView == null) {
			rowView = mInflater.inflate(R.layout.item_info_list, null);
		} else {
			rowView = convertView;
		}
		TextView tv_name = (TextView) rowView.findViewById(R.id.tv_name);
		TextView tv_price = (TextView) rowView.findViewById(R.id.tv_price);
		Petrol p = getItem(position);
		tv_name.setText(p.getType());
		tv_price.setText(p.getPrice());
		return rowView;
	}

}
