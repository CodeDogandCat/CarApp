package com.juhe.petrolstation.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.juhe.petrolstation.bean.Station;
import com.qrqc.activity.R;

public class StationListAdapter extends BaseAdapter {

	private final Context mContext;
	private final List<Station> list;
	private final LayoutInflater mInflater;

	public StationListAdapter(Context context, List<Station> list) {
		this.list = list;
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Station getItem(int position) {
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
			rowView = mInflater.inflate(R.layout.item_station_list, null);
		} else {
			rowView = convertView;
		}
		TextView tv_id = (TextView) rowView.findViewById(R.id.tv_id);
		TextView tv_name = (TextView) rowView.findViewById(R.id.tv_name);
		TextView tv_distance = (TextView) rowView.findViewById(R.id.tv_distance);
		TextView tv_addr = (TextView) rowView.findViewById(R.id.tv_addr);
		Station s = getItem(position);
		tv_id.setText((position + 1) + ".");
		tv_name.setText(s.getName());
		tv_distance.setText(s.getDistance() + "m");
		tv_addr.setText(s.getAddr());
		GridView gv = (GridView) rowView.findViewById(R.id.gv_price);
		ListGridViewAdapter adapter = new ListGridViewAdapter(mContext, s.getGastPriceList());
		gv.setAdapter(adapter);
		return rowView;
	}

}
