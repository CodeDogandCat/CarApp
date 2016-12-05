package com.qrqc.activity;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

public class Config {

	public static final String CHARSET = "utf-8";

	public static final int RESULT_STATUS_SUCCESS = 1;
	public static final int ACTIVITY_RESULT_NEED_REFRESH = 7;
	public static final String URL_BASIC = "http://192.168.191.1";
	public static final String URL_REG = URL_BASIC + "/QRQC/reg.php?";
	public static final String URL_LOG = URL_BASIC + "/QRQC/login.php?";
	public static final String URL_CHECK_TOKEN = URL_BASIC + "/QRQC/checkToken.php?";
	public static final String URL_UPLOAD_IMG = URL_BASIC + "/QRQC/uploadImage.php";
	public static final String URL_UPLOAD_TEXT = URL_BASIC + "/QRQC/uploadText.php";
	public static final String URL_UPLOAD_REPLY = URL_BASIC + "/QRQC/uploadReply.php";
	public static final String URL_DOWNLOAD_TEXT = URL_BASIC + "/QRQC/downloadMessage.php";
	public static final String URL_DOWNLOAD_COMMENT = URL_BASIC + "/QRQC/downloadComment.php";
	public static final String URL_GET_MESSAGE_PAGES = URL_BASIC + "/QRQC/getMessagePages.php";
	public static final String URL_IMAGE_CACHE = URL_BASIC + "/QRQC/";
	public static final String URL_AD = URL_BASIC + "/QRQC/downloadAd.php";
	public static final String URL_NEWS = URL_BASIC + "/QRQC/downloadNews.php";
	public static final String BASIC_CANSHU_URL = URL_BASIC + "/QRQC/test.php?k=";
	public static final String BASIC_IMG_URL = URL_BASIC + "/QRQC/test2.php?k=";
	public static final String BASIC_PRICE_URL = URL_BASIC + "/QRQC/test3.php?k=";
	public static final String KEY_NET_STATUS = "net_status";
	public static final String KEY_RESULT_STATUS = "result_status";
	public static final String KEY_RESULT_CODE = "resultcode";
	public static final String KEY_TOKEN = "token";
	public static final String KEY_USERNAME = "username";
	public static final String KEY_MSG_ID = "Mid";
	public static final String KEY_TITLE = "title";
	public static final String KEY_CONTENT = "content";
	public static final String KEY_MSG_DATE = "Mdate";
	public static final String KEY_REPLY_NUM = "replynum";
	public static final String KEY_PICTUREURL = "pictureURL";
	public static final String KEY_PICNUM = "picnum";
	public static final String KEY_REPLY_ID = "Cid";
	public static final String KEY_REPLY_DATE = "Cdate";

	public static final String STATUS_USERNAME_ALREADY_EXISTS = "already exists";
	public static final String STATUS_REG_OK = "register succeed";
	public static final String STATUS_REG_FAIL = "register failed";
	public static final String STATUS_LOG_OK = "login succeed";
	public static final String STATUS_LOG_FAIL = "login failed";
	public static final String STATUS_NET_OK = "net connect succeed";
	public static final String STATUS_NET_FAIL = "net connect failed";
	public static final String STATUS_PUBLISH_OK = "publish succeed";
	public static final String STATUS_PUBLISH_FAIL = "publish failed";
	public static final String STATUS_REPLY_OK = "reply succeed";
	public static final String STATUS_REPLY_FAIL = "reply failed";
	public static final String STATUS_TOKEN_OK = "有效token";
	public static final String STATUS_TOKEN_INVALID = "无效token";

	public static final int ACTIVITY_STATUS_SHOW_MESSAGE = 0;
	public static final int ACTIVITY_STATUS_SEND_MESSAGE = 1;
	public static final int ACTIVITY_STATUS_REPLY = 2;
	public static final int ACTIVITY_STATUS_SHOW_CAR = 3;
	public static final int ACTIVITY_STATUS_SHOW_CAR_PEIZHI = 4;
	public static final int ACTIVITY_STATUS_SHOW_CAR_ZIXUN = 5;

	public static final String ACTION_SEND_PASS = "send_pass";
	public static final String APP_ID = "com.example.log_reg";

	public static final String removeBOM(String data) {

		if (TextUtils.isEmpty(data)) {

			return data;

		}

		if (data.startsWith("\ufeff")) {

			return data.substring(1);

		} else {

			return data;

		}

	}

	public static String getCachedToken(Context context) {
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_TOKEN, null);

	}

	public static void cacheToken(Context context, String token) {
		Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
		e.putString(KEY_TOKEN, token);
		e.commit();
	}

	//
	public static String getCachedUsername(Context context) {
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_USERNAME, null);

	}

	//
	public static void cacheUsername(Context context, String username) {
		Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
		e.putString(KEY_USERNAME, username);
		e.commit();
	}
}
