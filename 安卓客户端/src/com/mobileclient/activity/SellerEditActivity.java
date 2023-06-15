package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.map.BaiduMap.OnMarkerDragListener;
import com.baidu.mapapi.model.LatLng;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Seller;
import com.mobileclient.service.SellerService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class SellerEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明商家账号TextView
	private TextView TV_sellUserName;
	// 声明登录密码输入框
	private EditText ET_password;
	// 声明商家名称输入框
	private EditText ET_sellerName;
	// 声明联系电话输入框
	private EditText ET_telephone;
	// 出版入驻日期控件
	private DatePicker dp_addDate;
	// 声明商家地址输入框
	private EditText ET_address;
	// 声明纬度输入框
	private EditText ET_latitude;
	// 声明经度输入框
	private EditText ET_longitude;
	protected String carmera_path;
	/*要保存的商家信息*/
	Seller seller = new Seller();
	/*商家管理业务逻辑层*/
	private SellerService sellerService = new SellerService();
	
	MapView mMapView = null;
	BaiduMap mBaiduMap = null;

	private String sellUserName;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.seller_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑商家信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_sellUserName = (TextView) findViewById(R.id.TV_sellUserName);
		ET_password = (EditText) findViewById(R.id.ET_password);
		ET_sellerName = (EditText) findViewById(R.id.ET_sellerName);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		dp_addDate = (DatePicker)this.findViewById(R.id.dp_addDate);
		ET_address = (EditText) findViewById(R.id.ET_address);
		ET_latitude = (EditText) findViewById(R.id.ET_latitude);
		ET_longitude = (EditText) findViewById(R.id.ET_longitude);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		sellUserName = extras.getString("sellUserName");
		/*单击修改商家按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取登录密码*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(SellerEditActivity.this, "登录密码输入不能为空!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					seller.setPassword(ET_password.getText().toString());
					/*验证获取商家名称*/ 
					if(ET_sellerName.getText().toString().equals("")) {
						Toast.makeText(SellerEditActivity.this, "商家名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_sellerName.setFocusable(true);
						ET_sellerName.requestFocus();
						return;	
					}
					seller.setSellerName(ET_sellerName.getText().toString());
					/*验证获取联系电话*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(SellerEditActivity.this, "联系电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					seller.setTelephone(ET_telephone.getText().toString());
					/*获取出版日期*/
					Date addDate = new Date(dp_addDate.getYear()-1900,dp_addDate.getMonth(),dp_addDate.getDayOfMonth());
					seller.setAddDate(new Timestamp(addDate.getTime()));
					/*验证获取商家地址*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(SellerEditActivity.this, "商家地址输入不能为空!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					seller.setAddress(ET_address.getText().toString());
					/*验证获取纬度*/ 
					if(ET_latitude.getText().toString().equals("")) {
						Toast.makeText(SellerEditActivity.this, "纬度输入不能为空!", Toast.LENGTH_LONG).show();
						ET_latitude.setFocusable(true);
						ET_latitude.requestFocus();
						return;	
					}
					seller.setLatitude(Float.parseFloat(ET_latitude.getText().toString()));
					/*验证获取经度*/ 
					if(ET_longitude.getText().toString().equals("")) {
						Toast.makeText(SellerEditActivity.this, "经度输入不能为空!", Toast.LENGTH_LONG).show();
						ET_longitude.setFocusable(true);
						ET_longitude.requestFocus();
						return;	
					}
					seller.setLongitude(Float.parseFloat(ET_longitude.getText().toString()));
					/*调用业务逻辑层上传商家信息*/
					SellerEditActivity.this.setTitle("正在更新商家信息，稍等...");
					String result = sellerService.UpdateSeller(seller);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
		
		mMapView = (MapView) findViewById(R.id.bmapView);
		InitCenter();
		AddMarker();
	}

	

	private void AddMarker() {
		mBaiduMap.clear();
		LatLng point = new LatLng(seller.getLatitude(), seller.getLongitude()); 
		//构建Marker图标
		BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);   
		//在地图上添加Marker，并显示     
		//构建MarkerOption，用于在地图上添加Marker   
		MarkerOptions options = new MarkerOptions()  
		    .position(point) 
		    .icon(bitmap) 
		    .zIndex(9)  //设置Marker所在层级
			.draggable(true);  //设置手势拖拽
	  
		Marker marker = (Marker) (mBaiduMap.addOverlay(options));  
		 
		//调用BaiduMap对象的setOnMarkerDragListener方法设置Marker拖拽的监听
		mBaiduMap.setOnMarkerDragListener(new OnMarkerDragListener() {
		    public void onMarkerDrag(Marker marker) {
		        //拖拽中
		    }
		    public void onMarkerDragEnd(Marker marker) {
		        //拖拽结束
		    	LatLng latLng = marker.getPosition();
		    	//Toast.makeText(OrderInfoUserAddActivity.this, latLng.latitude + "," + latLng.longitude, Toast.LENGTH_LONG).show();
		    	ET_latitude.setText((float)latLng.latitude + "");
		    	ET_longitude.setText((float)latLng.longitude + "");
		    	
		    }
		    public void onMarkerDragStart(Marker marker) {
		        //开始拖拽
		    }
		});
		
	}
	

private void InitCenter() {
	//设定中心点坐标 
    LatLng cenpt = new LatLng(seller.getLatitude(), seller.getLongitude()); 
   
    //定义地图状态
    MapStatus mMapStatus = new MapStatus.Builder()
    .target(cenpt)
    .zoom(18)
    .build();
    //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化


    MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
    //改变地图状态
    mBaiduMap = mMapView.getMap(); 
    mBaiduMap.setMapStatus(mMapStatusUpdate);  

    
    UiSettings mUiSettings = mBaiduMap.getUiSettings();
	//实例化UiSettings类对象 
	//mUiSettings.setCompassEnabled(true);  
	mUiSettings.setScrollGesturesEnabled(true);
	mUiSettings.setZoomGesturesEnabled(true);
	mUiSettings.setOverlookingGesturesEnabled(true);
	mMapView.showScaleControl(false);
	 
	
	/* 就是在用户点击到地图或者滑动地图时候，让ScrollView不截断点击事件，并传递给子View处理，也就是地图去处理点击事件；
	 * 当用户手指抬起的时候，将ScrollView的状态恢复至之前的状态，也就是ScrollView可以截断点击事件 */
	final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
	mMapView.getChildAt(0).setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_UP){
                //允许ScrollView截断点击事件，ScrollView可滑动
            	scrollView.requestDisallowInterceptTouchEvent(false);
            }else{
                //不允许ScrollView截断点击事件，点击事件由子View处理
            	scrollView.requestDisallowInterceptTouchEvent(true);
            }
            return false;
        }
    });
}


	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    seller = sellerService.GetSeller(sellUserName);
		this.TV_sellUserName.setText(sellUserName);
		this.ET_password.setText(seller.getPassword());
		this.ET_sellerName.setText(seller.getSellerName());
		this.ET_telephone.setText(seller.getTelephone());
		Date addDate = new Date(seller.getAddDate().getTime());
		this.dp_addDate.init(addDate.getYear() + 1900,addDate.getMonth(), addDate.getDate(), null);
		this.ET_address.setText(seller.getAddress());
		this.ET_latitude.setText(seller.getLatitude() + "");
		this.ET_longitude.setText(seller.getLongitude() + "");
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
