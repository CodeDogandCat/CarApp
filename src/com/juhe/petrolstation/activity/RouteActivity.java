package com.juhe.petrolstation.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.qrqc.activity.R;

public class RouteActivity extends Activity implements OnGetRoutePlanResultListener {

	private Context mContext;
	private MapView mMapView = null;
	private BaiduMap mBaiduMap = null;
	private ImageView iv_back = null;
	private RoutePlanSearch mSearch = null;

	// private RouteLine<DrivingRouteLine.DrivingStep> route = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route);
		mContext = this;
		initView();
	}

	private void initView() {
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setVisibility(View.VISIBLE);
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		mMapView = (MapView) findViewById(R.id.bmapView);
		mMapView.showScaleControl(false);
		mMapView.showZoomControls(false);
		mBaiduMap = mMapView.getMap();

		mSearch = RoutePlanSearch.newInstance();
		mSearch.setOnGetRoutePlanResultListener(this);
		Intent intent = getIntent();
		LatLng locLatLng = new LatLng(intent.getDoubleExtra("locLat", 0), intent.getDoubleExtra("locLon", 0));
		LatLng desLatLng = new LatLng(intent.getDoubleExtra("lat", 0), intent.getDoubleExtra("lon", 0));
		PlanNode st = PlanNode.withLocation(locLatLng);
		PlanNode en = PlanNode.withLocation(desLatLng);
		mSearch.drivingSearch(new DrivingRoutePlanOption().from(st).to(en));
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mMapView.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mMapView.onDestroy();
	}

	@Override
	public void onGetDrivingRouteResult(DrivingRouteResult result) {
		// TODO Auto-generated method stub
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(mContext, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
		}

		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {

			return;
		}

		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			// route = result.getRouteLines().get(0);
			DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap);
			overlay.setData(result.getRouteLines().get(0));
			overlay.addToMap();
			overlay.zoomToSpan();
		}

	}

	@Override
	public void onGetTransitRouteResult(TransitRouteResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult arg0) {
		// TODO Auto-generated method stub

	}

}
