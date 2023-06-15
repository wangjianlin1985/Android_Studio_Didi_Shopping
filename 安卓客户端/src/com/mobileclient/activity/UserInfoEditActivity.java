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
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
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

public class UserInfoEditActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// �����û���TextView
	private TextView TV_user_name;
	// ������¼���������
	private EditText ET_password;
	// �������������
	private EditText ET_name;
	// �����Ա������
	private EditText ET_sex;
	// ����������ڿؼ�
	private DatePicker dp_birthDate;
	// �����û���ƬͼƬ��ؼ�
	private ImageView iv_userImage;
	private Button btn_userImage;
	protected int REQ_CODE_SELECT_IMAGE_userImage = 1;
	private int REQ_CODE_CAMERA_userImage = 2;
	// �����û�qq�����
	private EditText ET_qq;
	// �������������
	private EditText ET_email;
	// ����ע��ʱ�������
	private EditText ET_regTime;
	// �����û���ַ�����
	private EditText ET_address;
	// ����γ�������
	private EditText ET_latitude;
	// �������������
	private EditText ET_longitude;
	protected String carmera_path;
	/*Ҫ������û���Ϣ*/
	UserInfo userInfo = new UserInfo();
	/*�û�����ҵ���߼���*/
	private UserInfoService userInfoService = new UserInfoService();
	
	MapView mMapView = null;
	BaiduMap mBaiduMap = null;

	private String user_name;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.userinfo_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�༭�û���Ϣ");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_user_name = (TextView) findViewById(R.id.TV_user_name);
		ET_password = (EditText) findViewById(R.id.ET_password);
		ET_name = (EditText) findViewById(R.id.ET_name);
		ET_sex = (EditText) findViewById(R.id.ET_sex);
		dp_birthDate = (DatePicker)this.findViewById(R.id.dp_birthDate);
		iv_userImage = (ImageView) findViewById(R.id.iv_userImage);
		/*����ͼƬ��ʾ�ؼ�ʱ����ͼƬ��ѡ��*/
		iv_userImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(UserInfoEditActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_userImage);
			}
		});
		btn_userImage = (Button) findViewById(R.id.btn_userImage);
		btn_userImage.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_userImage.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_userImage);  
			}
		});
		ET_qq = (EditText) findViewById(R.id.ET_qq);
		ET_email = (EditText) findViewById(R.id.ET_email);
		ET_regTime = (EditText) findViewById(R.id.ET_regTime);
		ET_address = (EditText) findViewById(R.id.ET_address);
		ET_latitude = (EditText) findViewById(R.id.ET_latitude);
		ET_longitude = (EditText) findViewById(R.id.ET_longitude);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		user_name = extras.getString("user_name");
		/*�����޸��û���ť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ��¼����*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "��¼�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					userInfo.setPassword(ET_password.getText().toString());
					/*��֤��ȡ����*/ 
					if(ET_name.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_name.setFocusable(true);
						ET_name.requestFocus();
						return;	
					}
					userInfo.setName(ET_name.getText().toString());
					/*��֤��ȡ�Ա�*/ 
					if(ET_sex.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "�Ա����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_sex.setFocusable(true);
						ET_sex.requestFocus();
						return;	
					}
					userInfo.setSex(ET_sex.getText().toString());
					/*��ȡ��������*/
					Date birthDate = new Date(dp_birthDate.getYear()-1900,dp_birthDate.getMonth(),dp_birthDate.getDayOfMonth());
					userInfo.setBirthDate(new Timestamp(birthDate.getTime()));
					if (!userInfo.getUserImage().startsWith("upload/")) {
						//���ͼƬ��ַ��Ϊ�գ�˵���û�ѡ����ͼƬ����ʱ��Ҫ���ӷ������ϴ�ͼƬ
						UserInfoEditActivity.this.setTitle("�����ϴ�ͼƬ���Ե�...");
						String userImage = HttpUtil.uploadFile(userInfo.getUserImage());
						UserInfoEditActivity.this.setTitle("ͼƬ�ϴ���ϣ�");
						userInfo.setUserImage(userImage);
					} 
					/*��֤��ȡ�û�qq*/ 
					if(ET_qq.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "�û�qq���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_qq.setFocusable(true);
						ET_qq.requestFocus();
						return;	
					}
					userInfo.setQq(ET_qq.getText().toString());
					/*��֤��ȡ����*/ 
					if(ET_email.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_email.setFocusable(true);
						ET_email.requestFocus();
						return;	
					}
					userInfo.setEmail(ET_email.getText().toString());
					/*��֤��ȡע��ʱ��*/ 
					if(ET_regTime.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "ע��ʱ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_regTime.setFocusable(true);
						ET_regTime.requestFocus();
						return;	
					}
					userInfo.setRegTime(ET_regTime.getText().toString());
					/*��֤��ȡ�û���ַ*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "�û���ַ���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					userInfo.setAddress(ET_address.getText().toString());
					/*��֤��ȡγ��*/ 
					if(ET_latitude.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "γ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_latitude.setFocusable(true);
						ET_latitude.requestFocus();
						return;	
					}
					userInfo.setLatitude(Float.parseFloat(ET_latitude.getText().toString()));
					/*��֤��ȡ����*/ 
					if(ET_longitude.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_longitude.setFocusable(true);
						ET_longitude.requestFocus();
						return;	
					}
					userInfo.setLongitude(Float.parseFloat(ET_longitude.getText().toString()));
					/*����ҵ���߼����ϴ��û���Ϣ*/
					UserInfoEditActivity.this.setTitle("���ڸ����û���Ϣ���Ե�...");
					String result = userInfoService.UpdateUserInfo(userInfo);
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
			LatLng point = new LatLng(userInfo.getLatitude(), userInfo.getLongitude()); 
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
        LatLng cenpt = new LatLng(userInfo.getLatitude(), userInfo.getLongitude()); 
       
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
	    userInfo = userInfoService.GetUserInfo(user_name);
		this.TV_user_name.setText(user_name);
		this.ET_password.setText(userInfo.getPassword());
		this.ET_name.setText(userInfo.getName());
		this.ET_sex.setText(userInfo.getSex());
		Date birthDate = new Date(userInfo.getBirthDate().getTime());
		this.dp_birthDate.init(birthDate.getYear() + 1900,birthDate.getMonth(), birthDate.getDate(), null);
		byte[] userImage_data = null;
		try {
			// ��ȡͼƬ����
			userImage_data = ImageService.getImage(HttpUtil.BASE_URL + userInfo.getUserImage());
			Bitmap userImage = BitmapFactory.decodeByteArray(userImage_data, 0, userImage_data.length);
			this.iv_userImage.setImageBitmap(userImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.ET_qq.setText(userInfo.getQq());
		this.ET_email.setText(userInfo.getEmail());
		this.ET_regTime.setText(userInfo.getRegTime());
		this.ET_address.setText(userInfo.getAddress());
		this.ET_latitude.setText(userInfo.getLatitude() + "");
		this.ET_longitude.setText(userInfo.getLongitude() + "");
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE_CAMERA_userImage  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_userImage.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_userImage.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// ������д���ļ� 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_userImage.setImageBitmap(booImageBm);
				this.iv_userImage.setScaleType(ScaleType.FIT_CENTER);
				this.userInfo.setUserImage(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_userImage && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/" + filename;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(filepath, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 128*128);
			opts.inJustDecodeBounds = false; 
			try { 
				Bitmap bm = BitmapFactory.decodeFile(filepath, opts);
				this.iv_userImage.setImageBitmap(bm); 
				this.iv_userImage.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			userInfo.setUserImage(filename); 
		}
	}
}
