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
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// �����̼��˺�TextView
	private TextView TV_sellUserName;
	// ������¼���������
	private EditText ET_password;
	// �����̼����������
	private EditText ET_sellerName;
	// ������ϵ�绰�����
	private EditText ET_telephone;
	// ������פ���ڿؼ�
	private DatePicker dp_addDate;
	// �����̼ҵ�ַ�����
	private EditText ET_address;
	// ����γ�������
	private EditText ET_latitude;
	// �������������
	private EditText ET_longitude;
	protected String carmera_path;
	/*Ҫ������̼���Ϣ*/
	Seller seller = new Seller();
	/*�̼ҹ���ҵ���߼���*/
	private SellerService sellerService = new SellerService();
	
	MapView mMapView = null;
	BaiduMap mBaiduMap = null;

	private String sellUserName;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.seller_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�༭�̼���Ϣ");
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
		/*�����޸��̼Ұ�ť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ��¼����*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(SellerEditActivity.this, "��¼�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					seller.setPassword(ET_password.getText().toString());
					/*��֤��ȡ�̼�����*/ 
					if(ET_sellerName.getText().toString().equals("")) {
						Toast.makeText(SellerEditActivity.this, "�̼��������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_sellerName.setFocusable(true);
						ET_sellerName.requestFocus();
						return;	
					}
					seller.setSellerName(ET_sellerName.getText().toString());
					/*��֤��ȡ��ϵ�绰*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(SellerEditActivity.this, "��ϵ�绰���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					seller.setTelephone(ET_telephone.getText().toString());
					/*��ȡ��������*/
					Date addDate = new Date(dp_addDate.getYear()-1900,dp_addDate.getMonth(),dp_addDate.getDayOfMonth());
					seller.setAddDate(new Timestamp(addDate.getTime()));
					/*��֤��ȡ�̼ҵ�ַ*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(SellerEditActivity.this, "�̼ҵ�ַ���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					seller.setAddress(ET_address.getText().toString());
					/*��֤��ȡγ��*/ 
					if(ET_latitude.getText().toString().equals("")) {
						Toast.makeText(SellerEditActivity.this, "γ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_latitude.setFocusable(true);
						ET_latitude.requestFocus();
						return;	
					}
					seller.setLatitude(Float.parseFloat(ET_latitude.getText().toString()));
					/*��֤��ȡ����*/ 
					if(ET_longitude.getText().toString().equals("")) {
						Toast.makeText(SellerEditActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_longitude.setFocusable(true);
						ET_longitude.requestFocus();
						return;	
					}
					seller.setLongitude(Float.parseFloat(ET_longitude.getText().toString()));
					/*����ҵ���߼����ϴ��̼���Ϣ*/
					SellerEditActivity.this.setTitle("���ڸ����̼���Ϣ���Ե�...");
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
		//����Markerͼ��
		BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);   
		//�ڵ�ͼ�����Marker������ʾ     
		//����MarkerOption�������ڵ�ͼ�����Marker   
		MarkerOptions options = new MarkerOptions()  
		    .position(point) 
		    .icon(bitmap) 
		    .zIndex(9)  //����Marker���ڲ㼶
			.draggable(true);  //����������ק
	  
		Marker marker = (Marker) (mBaiduMap.addOverlay(options));  
		 
		//����BaiduMap�����setOnMarkerDragListener��������Marker��ק�ļ���
		mBaiduMap.setOnMarkerDragListener(new OnMarkerDragListener() {
		    public void onMarkerDrag(Marker marker) {
		        //��ק��
		    }
		    public void onMarkerDragEnd(Marker marker) {
		        //��ק����
		    	LatLng latLng = marker.getPosition();
		    	//Toast.makeText(OrderInfoUserAddActivity.this, latLng.latitude + "," + latLng.longitude, Toast.LENGTH_LONG).show();
		    	ET_latitude.setText((float)latLng.latitude + "");
		    	ET_longitude.setText((float)latLng.longitude + "");
		    	
		    }
		    public void onMarkerDragStart(Marker marker) {
		        //��ʼ��ק
		    }
		});
		
	}
	

private void InitCenter() {
	//�趨���ĵ����� 
    LatLng cenpt = new LatLng(seller.getLatitude(), seller.getLongitude()); 
   
    //�����ͼ״̬
    MapStatus mMapStatus = new MapStatus.Builder()
    .target(cenpt)
    .zoom(18)
    .build();
    //����MapStatusUpdate�����Ա�������ͼ״̬��Ҫ�����ı仯


    MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
    //�ı��ͼ״̬
    mBaiduMap = mMapView.getMap(); 
    mBaiduMap.setMapStatus(mMapStatusUpdate);  

    
    UiSettings mUiSettings = mBaiduMap.getUiSettings();
	//ʵ����UiSettings����� 
	//mUiSettings.setCompassEnabled(true);  
	mUiSettings.setScrollGesturesEnabled(true);
	mUiSettings.setZoomGesturesEnabled(true);
	mUiSettings.setOverlookingGesturesEnabled(true);
	mMapView.showScaleControl(false);
	 
	
	/* �������û��������ͼ���߻�����ͼʱ����ScrollView���ضϵ���¼��������ݸ���View����Ҳ���ǵ�ͼȥ�������¼���
	 * ���û���ָ̧���ʱ�򣬽�ScrollView��״̬�ָ���֮ǰ��״̬��Ҳ����ScrollView���Խضϵ���¼� */
	final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
	mMapView.getChildAt(0).setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_UP){
                //����ScrollView�ضϵ���¼���ScrollView�ɻ���
            	scrollView.requestDisallowInterceptTouchEvent(false);
            }else{
                //������ScrollView�ضϵ���¼�������¼�����View����
            	scrollView.requestDisallowInterceptTouchEvent(true);
            }
            return false;
        }
    });
}


	/* ��ʼ����ʾ�༭��������� */
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
