package com.qrqc.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qrqc.activity.R;
import com.qrqc.domain.mComment;

public class AtyMessageCommentListAdapter extends BaseAdapter {

	public AtyMessageCommentListAdapter(Context context) {
		this.context = context;

	}

	@Override
	public int getCount() {

		return data.size();
	}

	@Override
	public mComment getItem(int position) {

		return data.get(position);
	}

	@Override
	public long getItemId(int arg0) {

		return arg0;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup parent) {

		if (convertview == null) {
			convertview = LayoutInflater.from(getContext()).inflate(R.layout.aty_comment_list_cell, null);
			convertview.setTag(new listCellHolder((TextView) convertview.findViewById(R.id.comment_tv_username),
					(TextView) convertview.findViewById(R.id.comment_tv_text), (TextView) convertview
							.findViewById(R.id.comment_tv_time)));
		}
		// 我们都知道在getView方法中的操作是这样的：先从xml中创建view对象（inflate操作，我们采用了重用convertView方法优化），然后在这个view去findViewById，
		// 找到每一个子View，如：一个TextView等。这里的findViewById操作是一个树查找过程，也是一个耗时的操作，所以这里也需要优化，就是使用viewHolder，
		// 把每一个子View都放在Holder中，当第一次创建convertView对象时，把这些子view找出来。然后用convertView的setTag将viewHolder设置到Tag中，
		// 以便系统第二次绘制ListView时从Tag中取出。当第二次重用convertView时，只需从convertView中getTag取出来就可以。
		listCellHolder holder = (listCellHolder) convertview.getTag();
		mComment c = getItem(position);
		holder.getComment_tv_username().setText(c.getUsername());
		holder.getComment_tv_text().setText(c.getContent());
		holder.getComment_tv_time().setText(c.getDate());
		return convertview;
	}

	public Context getContext() {
		return context;
	}

	public void addAll(List<mComment> mComments) {
		this.data.addAll(mComments);
		notifyDataSetChanged();// 数据有改变
		System.out.println("already add");

	}

	public void clear() {
		data.clear();
		notifyDataSetChanged();
	}

	private static class listCellHolder {

		public listCellHolder(TextView comment_tv_username, TextView comment_tv_text, TextView comment_tv_time) {
			super();
			this.comment_tv_username = comment_tv_username;
			this.comment_tv_text = comment_tv_text;
			this.comment_tv_time = comment_tv_time;
		}

		private final TextView comment_tv_username;
		private final TextView comment_tv_text;
		private final TextView comment_tv_time;

		public TextView getComment_tv_username() {
			return comment_tv_username;
		}

		public TextView getComment_tv_text() {
			return comment_tv_text;
		}

		public TextView getComment_tv_time() {
			return comment_tv_time;
		}

	}

	private final List<mComment> data = new ArrayList<mComment>();
	private Context context = null;
}
