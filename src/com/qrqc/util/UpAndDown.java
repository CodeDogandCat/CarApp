package com.qrqc.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.Header;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qrqc.activity.Config;
import com.qrqc.message.util.FileUtils;
import com.squareup.picasso.Picasso;

public class UpAndDown {
	/**
	 * 获取车型参数
	 * 
	 * @param url
	 * @param handler
	 * @param duibicarids
	 */
	public static void getCanshuJSON(final String url, final Handler handler, final ArrayList<String> duibicarids) {
		System.out.println("HttpUtils....start");

		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getId() + "..........");
				HttpURLConnection conn;
				InputStream is;
				ArrayList<String> returnJSONList = new ArrayList<String>();
				for (int i = 0; i < duibicarids.size(); i++) {
					try {
						String tmpUrl = url + duibicarids.get(i);
						System.out.println(tmpUrl);
						conn = (HttpURLConnection) new URL(tmpUrl).openConnection();
						conn.setRequestMethod("GET");
						is = conn.getInputStream();
						BufferedReader reader = new BufferedReader(new InputStreamReader(is));
						String line = "";
						StringBuilder result = new StringBuilder();
						while ((line = reader.readLine()) != null) {
							// System.out.println(line + "..............");
							result.append(line);
						}
						// System.out.println(result.toString());
						returnJSONList.add(result.toString());
					} catch (Exception e) {
						e.printStackTrace();
						Log.i("网络连接失败", "网络连接失败");
						Message msg = new Message();
						Bundle bundle = new Bundle();
						bundle.putString("state", "网络连接失败"); // 往Bundle中存放数据
						msg.setData(bundle);// mes利用Bundle传递数据
						handler.sendMessage(msg);
					} finally {
						Log.i("httputils", "finally");
					}
				}
				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putStringArrayList("content", returnJSONList);
				bundle.putString("state", "网络连接成功");
				msg.setData(bundle);// mes利用Bundle传递数据
				System.out.println("HttpUtils....end");
				handler.sendMessage(msg);

			}
		}).start();
	}

	/**
	 * 基本的网络操作，get类型
	 * 
	 * @param url
	 * @param handler
	 */
	public static void netUtils(final String url, final Handler handler) {
		// System.out.println("HttpUtils....start");

		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpURLConnection conn;
				InputStream is;
				StringBuilder result = null;
				try {
					conn = (HttpURLConnection) new URL(url).openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(20000);
					is = conn.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
					String line = "";
					result = new StringBuilder();
					while ((line = reader.readLine()) != null) {
						// System.out.println(line + "..............");
						result.append(line);
					}
					// System.out.println("///////////////////////////////" +
					// result.toString());
				} catch (Exception e) {
					e.printStackTrace();
					Message msg = new Message();
					Bundle bundle = new Bundle();
					bundle.putString(Config.KEY_NET_STATUS, Config.STATUS_NET_FAIL); // 往Bundle中存放数据
					msg.setData(bundle);// mes利用Bundle传递数据
					handler.sendMessage(msg);
				} finally {
					Log.i("httputils", "finally");
				}
				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putString(Config.KEY_RESULT_STATUS, result.toString());
				bundle.putString(Config.KEY_NET_STATUS, Config.STATUS_NET_OK);
				msg.setData(bundle);// mes利用Bundle传递数据
				// System.out.println("HttpUtils....end");
				handler.sendMessage(msg);

			}
		}).start();
	}

	public static void uploadMessageImages(final Context mContext, final Handler sendMessageImagesHandler,
			final String path, String url, String username, String token, String send_message_time) throws Exception {
		File file = new File(path);
		if (file.exists() && file.length() > 0) {
			// final ProgressDialog pd = ProgressDialog.show(mContext, "请等待",
			// "玩命上传中...");
			AsyncHttpClient client = new AsyncHttpClient();
			RequestParams params = new RequestParams();
			params.put("uploadfile", file);
			params.put("username", username);
			params.put("token", token);
			params.put("send_message_time", send_message_time);
			System.out.println("username...UPANDDOWN" + username);
			System.out.println("send_message_time...UPANDDOWN" + send_message_time);
			// 上传文件
			client.post(url, params, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, responseBody);

					Message msg = new Message();
					Bundle bundle = new Bundle();
					bundle.putString("status", Config.STATUS_PUBLISH_OK);
					msg.setData(bundle);// mes利用Bundle传递数据
					// System.out.println("HttpUtils....end");
					sendMessageImagesHandler.sendMessage(msg);

				}

				@Override
				public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
					// TODO Auto-generated method stub
					super.onFailure(statusCode, headers, responseBody, error);
					// pd.dismiss();
					error.printStackTrace();
					// 上传失败后要做到工作
					// Toast.makeText(mContext, "图片上传失败",
					// Toast.LENGTH_LONG).show();
					Message msg = new Message();
					Bundle bundle = new Bundle();
					bundle.putString("status", Config.STATUS_PUBLISH_FAIL);
					msg.setData(bundle);// mes利用Bundle传递数据
					// System.out.println("HttpUtils....end");
					sendMessageImagesHandler.sendMessage(msg);
				}

				@Override
				public void onRetry() {
					// TODO Auto-generated method stub
					super.onRetry();
				}

				@Override
				public void onProgress(int bytesWritten, int totalSize) {
					// TODO Auto-generated method stub
					super.onProgress(bytesWritten, totalSize);
					// System.out.println("上传图片大小" + totalSize);
					int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
					// 上传进度显示
					// Log.e("上传 Progress>>>>>", bytesWritten + " / " +
					// totalSize);
					// 删除临时图片
					if (bytesWritten >= totalSize) {
						FileUtils.delFile(path);
					}

				}
			});
		} else {
			Toast.makeText(mContext, "图片不存在", Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 上传发帖的文字部分
	 * 
	 * @param mContext
	 * @param sendMessageTextHandler
	 * @param title
	 * @param content
	 * @param url
	 * @param username
	 * @param token
	 * @param send_message_time
	 * @param picnum
	 * @throws Exception
	 */
	public static void uploadMessageText(final Context mContext, final Handler sendMessageTextHandler, String title,
			String content, String url, String username, String token, String send_message_time, int picnum)
			throws Exception {
		if (!(title.equals("")) && !(content.equals(""))) {
			// final ProgressDialog pd = ProgressDialog.show(mContext, "请等待",
			// "玩命上传中...");
			AsyncHttpClient client = new AsyncHttpClient();
			RequestParams params = new RequestParams();
			params.put("title", title);
			params.put("content", content);
			params.put("username", username);
			params.put("token", token);
			params.put("send_message_time", send_message_time);
			params.put("picnum", String.valueOf(picnum));
			System.out.println("username...uploadText" + username);
			System.out.println("send_message_time...uploadText" + send_message_time);
			System.out.println("title...uploadText" + title);
			System.out.println("content...uploadText" + content);
			System.out.println("picnum...uploadText" + picnum);
			// 上传文件
			client.post(url, params, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, responseBody);

					Message msg = new Message();
					Bundle bundle = new Bundle();
					bundle.putString("status", Config.STATUS_PUBLISH_OK);
					msg.setData(bundle);// mes利用Bundle传递数据
					sendMessageTextHandler.sendMessage(msg);
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
					// TODO Auto-generated method stub
					super.onFailure(statusCode, headers, responseBody, error);
					// pd.dismiss();
					error.printStackTrace();

					Message msg = new Message();
					Bundle bundle = new Bundle();
					bundle.putString("status", Config.STATUS_PUBLISH_FAIL);
					msg.setData(bundle);// mes利用Bundle传递数据
					// System.out.println("HttpUtils....end");
					sendMessageTextHandler.sendMessage(msg);
				}

				@Override
				public void onRetry() {
					// TODO Auto-generated method stub
					super.onRetry();
				}

				@Override
				public void onProgress(int bytesWritten, int totalSize) {
					// TODO Auto-generated method stub
					super.onProgress(bytesWritten, totalSize);
					// System.out.println("上传文字大小" + totalSize);
					// int count = (int) ((bytesWritten * 1.0 / totalSize) *
					// 100);
					// 上传进度显示
					// Log.e("上传 Progress>>>>>", bytesWritten + " / " +
					// totalSize);
				}
			});
		} else {
			Toast.makeText(mContext, "请填写文本内容", Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 上传回帖部分
	 * 
	 * @param mContext
	 * @param replyHandler
	 * @param mid
	 * @param content
	 * @param url
	 * @param username
	 * @param token
	 * @throws Exception
	 */
	public static void uploadReply(final Context mContext, final Handler replyHandler, String mid, String content,
			String url, String username, String token) throws Exception {

		if (!(content.equals(""))) {
			// final ProgressDialog pd = ProgressDialog.show(mContext, "请等待",
			// "玩命上传中...");
			AsyncHttpClient client = new AsyncHttpClient();
			RequestParams params = new RequestParams();
			params.put("content", content);
			params.put("username", username);
			params.put("token", token);
			params.put("Mid", mid);
			System.out.println("username...uploadText" + username);
			System.out.println("content...uploadText" + content);
			// 上传文件
			client.post(url, params, new AsyncHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, responseBody);
					Message msg = new Message();
					Bundle bundle = new Bundle();
					bundle.putString("status", Config.STATUS_REPLY_OK);
					msg.setData(bundle);// mes利用Bundle传递数据
					replyHandler.sendMessage(msg);
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

					// TODO Auto-generated method stub
					super.onFailure(statusCode, headers, responseBody, error);
					// pd.dismiss();
					error.printStackTrace();
					Message msg = new Message();
					Bundle bundle = new Bundle();
					bundle.putString("status", Config.STATUS_REPLY_FAIL);
					msg.setData(bundle);// mes利用Bundle传递数据
					replyHandler.sendMessage(msg);
				}

				@Override
				public void onRetry() {

					// TODO Auto-generated method stub
					super.onRetry();
				}

				@Override
				public void onProgress(int bytesWritten, int totalSize) {

					// TODO Auto-generated method stub
					super.onProgress(bytesWritten, totalSize);
					// System.out.println("上传文字大小" + totalSize);
					// int count = (int) ((bytesWritten * 1.0 / totalSize) *
					// 100);
					// 上传进度显示
					// Log.e("上传 Progress>>>>>", bytesWritten + " / " +
					// totalSize);
				}
			});
		} else {
			Toast.makeText(mContext, "请填写文本内容", Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 下载发帖列表
	 * 
	 * @param mContext
	 * @param getTimelineHandler
	 * @param page
	 * @param url
	 * @param username
	 * @param token
	 * @throws Exception
	 */
	public static void downloadTimeline(final Context mContext, final Handler getTimelineHandler, int page, String url,
			String username, String token) throws Exception {
		System.out.println("upanddown....downloadTimeline" + page);
		// final ProgressDialog pd = ProgressDialog.show(mContext, "请等待",
		// "玩命上传中...");
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();

		params.put("page", String.valueOf(page));
		params.put("username", username);
		params.put("token", token);
		System.out.println("username...downloadTimeline" + username);
		System.out.println("page...downloadTimeline" + page);

		// 上传文件
		client.post(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, responseBody);
				// System.out.println(new String(responseBody));
				// Looper.prepare();
				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putString("msgs", new String(responseBody));
				msg.setData(bundle);// mes利用Bundle传递数据
				// System.out.println("HttpUtils....end");
				getTimelineHandler.sendMessage(msg);
				System.out.println("获取到的JSON" + new String(responseBody));
				// pd.dismiss();
				// 上传成功后要做的工作
				// Toast.makeText(mContext, "获取列表成功", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, responseBody, error);
				// pd.dismiss();
				error.printStackTrace();
				// 上传失败后要做到工作
				// Toast.makeText(mContext, "获取列表失败", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onRetry() {
				// TODO Auto-generated method stub
				super.onRetry();
			}

			@Override
			public void onProgress(int bytesWritten, int totalSize) {
				// TODO Auto-generated method stub
				super.onProgress(bytesWritten, totalSize);
				// System.out.println("上传文字大小" + totalSize);
				// int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
				// // 上传进度显示
				// Log.e("获取 Progress>>>>>", bytesWritten + " / " + totalSize);
			}
		});

	}

	/**
	 * 显示网络图片
	 * 
	 * @param mContext
	 * @param iv_img
	 * @param img_url
	 * @param x
	 * @param y
	 * @param errorDrawable
	 */
	public static void ImageCache(final Context mContext, ImageView iv_img, String img_url, int x, int y,
			int errorDrawable) {
		// img_url =
		// "http://192.168.43.107/QRQC/luntanimages/qwertyuio/1457680494014/1457680494014.JPEG";
		Picasso.with(mContext).load(img_url).into(iv_img);
		Picasso.with(mContext).load(img_url).resize(x, y).into(iv_img);
		Picasso.with(mContext).load(img_url).error(errorDrawable).into(iv_img);
	}

	/**
	 * 下载回帖
	 * 
	 * @param mContext
	 * @param getCommentHandler
	 * @param mid
	 * @param url
	 * @param username
	 * @param token
	 * @throws Exception
	 */
	public static void downloadComment(final Context mContext, final Handler getCommentHandler, String mid, String url,
			String username, String token) throws Exception {

		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("Mid", mid);
		params.put("username", username);
		params.put("token", token);
		System.out.println("username...downloadTimeline" + username);
		System.out.println("mid...downloadTimeline" + mid);

		// 上传文件
		client.post(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, responseBody);

				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putString("msgs", new String(responseBody));
				msg.setData(bundle);// mes利用Bundle传递数据
				// System.out.println("HttpUtils....end");
				getCommentHandler.sendMessage(msg);
				System.out.println("获取到的comment_JSON" + new String(responseBody));

			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, responseBody, error);
				error.printStackTrace();
			}

			@Override
			public void onRetry() {
				// TODO Auto-generated method stub
				super.onRetry();
			}

			@Override
			public void onProgress(int bytesWritten, int totalSize) {
				// TODO Auto-generated method stub
				super.onProgress(bytesWritten, totalSize);
				// System.out.println("上传文字大小" + totalSize);
				// int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
				// // 上传进度显示
				// Log.e("获取 Progress>>>>>", bytesWritten + " / " + totalSize);
			}
		});

	}

	/**
	 * 获取总的发帖页数
	 * 
	 * @param mContext
	 * @param getTotalPageHandler
	 * @param url
	 * @param username
	 * @param token
	 * @throws Exception
	 */
	public static void getTotalPage(final Context mContext, final Handler getTotalPageHandler, String url,
			String username, String token) throws Exception {

		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("username", username);
		params.put("token", token);
		System.out.println("username...getTotalPage" + username);

		// 上传文件
		client.post(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, responseBody);

				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putString("msgs", new String(responseBody));
				msg.setData(bundle);// mes利用Bundle传递数据
				// System.out.println("HttpUtils....end");
				getTotalPageHandler.sendMessage(msg);
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@获取到的totalpage" + new String(responseBody));

			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, responseBody, error);
				error.printStackTrace();
			}

			@Override
			public void onRetry() {
				// TODO Auto-generated method stub
				super.onRetry();
			}

			@Override
			public void onProgress(int bytesWritten, int totalSize) {
				// TODO Auto-generated method stub
				super.onProgress(bytesWritten, totalSize);

			}
		});

	}

	/**
	 * 根据获取发帖的图片的服务器地址
	 * 
	 * @param mContext
	 * @param getPriceHandler
	 * @param url
	 * @param username
	 * @param token
	 * @throws Exception
	 */
	public static void getImageUrls(final Context mContext, final Handler getPriceHandler, String url, String username,
			String token) throws Exception {

		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("username", username);
		params.put("token", token);
		System.out.println("username...getTotalPage" + username);

		// 上传文件
		client.post(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, responseBody);

				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putString("msgs", new String(responseBody));
				msg.setData(bundle);// mes利用Bundle传递数据
				// System.out.println("HttpUtils....end");
				getPriceHandler.sendMessage(msg);
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@获取到的totalpage" + new String(responseBody));

			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, responseBody, error);
				error.printStackTrace();
			}

			@Override
			public void onRetry() {
				// TODO Auto-generated method stub
				super.onRetry();
			}

			@Override
			public void onProgress(int bytesWritten, int totalSize) {
				// TODO Auto-generated method stub
				super.onProgress(bytesWritten, totalSize);

			}
		});

	}

	/**
	 * 下载广告
	 * 
	 * @param mContext
	 * @param getAdHandler
	 * @param url
	 * @param username
	 * @param token
	 * @throws Exception
	 */
	public static void downloadAd(final Context mContext, final Handler getAdHandler, String url, String username,
			String token) throws Exception {

		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();

		params.put("username", username);
		params.put("token", token);
		System.out.println("username...downloadAd" + username);

		// 上传文件
		client.post(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, responseBody);

				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putString("msgs", new String(responseBody));
				msg.setData(bundle);// mes利用Bundle传递数据
				// System.out.println("HttpUtils....end");
				getAdHandler.sendMessage(msg);
				System.out.println("获取到的ad_JSON" + new String(responseBody));

			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, responseBody, error);
				error.printStackTrace();
			}

			@Override
			public void onRetry() {
				// TODO Auto-generated method stub
				super.onRetry();
			}

			@Override
			public void onProgress(int bytesWritten, int totalSize) {
				// TODO Auto-generated method stub
				super.onProgress(bytesWritten, totalSize);
				// System.out.println("上传文字大小" + totalSize);
				// int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
				// // 上传进度显示
				// Log.e("获取 Progress>>>>>", bytesWritten + " / " + totalSize);
			}
		});

	}

}
