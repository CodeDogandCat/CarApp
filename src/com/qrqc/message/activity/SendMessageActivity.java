package com.qrqc.message.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qrqc.activity.Config;
import com.qrqc.activity.R;
import com.qrqc.activity.TimelineActivity;
import com.qrqc.message.util.Bimp;
import com.qrqc.message.util.FileUtils;
import com.qrqc.message.util.ImageItem;
import com.qrqc.message.util.PublicWay;
import com.qrqc.message.util.Res;
import com.qrqc.util.UpAndDown;

/**
 * 首页面activity
 * 
 * @author king
 * @QQ:595163260
 * @version 2014年10月18日 下午11:48:34
 */
public class SendMessageActivity extends Activity {

	private GridView noScrollgridview;
	private GridAdapter adapter;
	private View parentView;
	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	public static Bitmap bimap;
	private EditText ed_title;
	private EditText ed_content;
	private TextView tv_send;
	private TextView tv_cancel;
	// private int cancelClickTimes;
	private int backClickTimes;
	private int handlerTimes;

	// private String username;
	// private String token;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Res.init(this);
		bimap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_unfocused);
		PublicWay.activityList.add(this);
		parentView = getLayoutInflater().inflate(R.layout.activity_selectimg, null);
		setContentView(parentView);
		Log.i("avtivity", "MainActivity is on creating");
		Init();
		if (Bimp.tempSelectBitmap == null) {
			System.out.println("tempSelectBitmap is null onCreate");
		}

	}

	public void saveText() {

		Bimp.titleString = ed_title.getText().toString();
		Bimp.textString = ed_content.getText().toString();
	}

	public void Init() {
		// System.out.println("///////////////////////////////" +
		// getIntent().getExtras().getString("hello"));

		handlerTimes = 0;
		// cancelClickTimes = 0;
		backClickTimes = 0;
		// username = getIntent().getStringExtra(Config.KEY_USERNAME);
		// token = getIntent().getStringExtra(Config.KEY_TOKEN);

		pop = new PopupWindow(SendMessageActivity.this);

		View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);

		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);

		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
		Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
		Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
		parent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				photo();
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(SendMessageActivity.this, AlbumActivity.class);
				startActivity(intent);
				saveText();
				finish();
				overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		ed_title = (EditText) findViewById(R.id.mainacticity_tv_title);
		ed_title.setText(Bimp.titleString);
		ed_content = (EditText) findViewById(R.id.mainacticity_tv_text);
		ed_content.setText(Bimp.textString);
		tv_cancel = (TextView) findViewById(R.id.activity_selectimg_cancel);
		tv_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// if (cancelClickTimes == 1) {
				Intent intent = new Intent();
				intent.putExtra("status", "fail");
				setResult(Config.ACTIVITY_STATUS_SEND_MESSAGE, intent);
				finish();
				// } else {
				// Toast.makeText(SendMessageActivity.this, "再按一次取消发帖",
				// Toast.LENGTH_SHORT).show();
				// }
				// cancelClickTimes++;
			}
		});
		tv_send = (TextView) findViewById(R.id.activity_selectimg_send);
		tv_send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (Bimp.tempSelectBitmap == null) {
					System.out.println("tempSelectBitmap is null ");
				} else {
					System.out.println("tempSelectBitmap size is   " + Bimp.tempSelectBitmap.size());
					String send_message_time = String.valueOf(System.currentTimeMillis());
					// 发布文本
					try {
						System.out.println("picnum...SendMessage" + Bimp.tempSelectBitmap.size());
						UpAndDown.uploadMessageText(SendMessageActivity.this, sendMessageHandler, ed_title.getText()
								.toString(), ed_content.getText().toString(), Config.URL_UPLOAD_TEXT, Config
								.getCachedUsername(SendMessageActivity.this), Config
								.getCachedToken(SendMessageActivity.this), send_message_time, Bimp.tempSelectBitmap
								.size());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					// 发布图片
					for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
						String fileName = String.valueOf(Long.parseLong(send_message_time) + i);
						Bitmap bm = Bimp.tempSelectBitmap.get(i).getBitmap();
						String path = FileUtils.saveBitmap(bm, fileName);

						Bimp.tempSelectBitmap.get(i).setImagePath(path);
						try {
							UpAndDown.uploadMessageImages(SendMessageActivity.this, sendMessageHandler, path,
									Config.URL_UPLOAD_IMG, Config.getCachedUsername(SendMessageActivity.this),
									Config.getCachedToken(SendMessageActivity.this), send_message_time);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("照片路径" + i + " " + path);

					}

				}
			}
		});
		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (arg2 == Bimp.tempSelectBitmap.size()) {
					Log.i("ddddddd", "----------");
					ll_popup.startAnimation(AnimationUtils.loadAnimation(SendMessageActivity.this,
							R.anim.activity_translate_in));
					pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
				} else {
					Intent intent = new Intent(SendMessageActivity.this, GalleryActivity.class);
					intent.putExtra("position", "1");
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});

	}

	public Handler sendMessageHandler = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {
			++handlerTimes;
			if (msg.getData().getString("status").equals(Config.STATUS_PUBLISH_FAIL)) {
				Toast.makeText(SendMessageActivity.this, Config.STATUS_PUBLISH_FAIL, Toast.LENGTH_LONG).show();
			} else {
				if (handlerTimes >= Bimp.tempSelectBitmap.size() + 1) {
					System.out.println("SendMessageActivity发帖成功,准备返回到timeline");
					Intent intent = new Intent(SendMessageActivity.this, TimelineActivity.class);
					intent.putExtra("status", "ok");
					setResult(Config.ACTIVITY_STATUS_SEND_MESSAGE, intent);

					finish();
					System.err.println("/////////////////////////////////////////finish()");
				}

			}

		}

	};

	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private final LayoutInflater inflater;
		private int selectedPosition = -1;
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		@Override
		public int getCount() {
			if (Bimp.tempSelectBitmap.size() == 9) {
				return 9;
			}
			return (Bimp.tempSelectBitmap.size() + 1);
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_published_grida, parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == Bimp.tempSelectBitmap.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(getResources(),
						R.drawable.icon_addpic_unfocused));
				if (position == 9) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.tempSelectBitmap.size()) {
							// Bimp.max -= 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							Bimp.max += 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;// 在这添加

						}
					}
				}
			}).start();
		}
	}

	@Override
	protected void onRestart() {
		adapter.update();
		super.onRestart();
	}

	private static final int TAKE_PICTURE = 10086;

	public void photo() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PICTURE:
			if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {

				// String fileName = String.valueOf(System.currentTimeMillis());
				Bitmap bm = (Bitmap) data.getExtras().get("data");
				ImageItem takePhoto = new ImageItem();
				takePhoto.setBitmap(bm);
				Bimp.tempSelectBitmap.add(takePhoto);

			}
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// for (int i = 0; i < PublicWay.activityList.size(); i++) {
			// if (null != PublicWay.activityList.get(i)) {
			// PublicWay.activityList.get(i).finish();
			// }
			// }
			// System.exit(0);
			if (backClickTimes == 1) {
				Intent intent = new Intent();
				intent.putExtra("status", "fail");
				setResult(Config.ACTIVITY_STATUS_SEND_MESSAGE, intent);
				finish();
			} else {
				Toast.makeText(SendMessageActivity.this, "再按一次取消发帖", Toast.LENGTH_SHORT).show();
			}
			backClickTimes++;
		}
		return true;
	}

}
