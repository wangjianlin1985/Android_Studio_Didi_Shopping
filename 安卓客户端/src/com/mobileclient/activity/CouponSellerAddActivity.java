package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Coupon;
import com.mobileclient.service.CouponService;
import com.mobileclient.domain.Seller;
import com.mobileclient.service.SellerService;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class CouponSellerAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// �����Ż�ȯ���������
	private EditText ET_couponName;
	// ������������
	private EditText ET_couponMoney;
	 
	// ���������û�������
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*�����û�����ҵ���߼���*/
	private UserInfoService userInfoService = new UserInfoService();
	// ��������ʱ�������
	private EditText ET_couponTime;
	protected String carmera_path;
	/*Ҫ������Ż�ȯ��Ϣ*/
	Coupon coupon = new Coupon();
	/*�Ż�ȯ����ҵ���߼���*/
	private CouponService couponService = new CouponService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.coupon_seller_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("����Ż�ȯ");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_couponName = (EditText) findViewById(R.id.ET_couponName);
		ET_couponMoney = (EditText) findViewById(R.id.ET_couponMoney);
		  
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// ��ȡ���еķ����û�
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount];
		for(int i=0;i<userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i).getName();
		}
		// ����ѡ������ArrayAdapter��������
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// ���������б�ķ��
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_userObj.setAdapter(userObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				coupon.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_couponTime = (EditText) findViewById(R.id.ET_couponTime);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*��������Ż�ȯ��ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					Declare declare = (Declare) CouponSellerAddActivity.this.getApplication();
					coupon.setSellerObj(declare.getUserName());
					
					/*��֤��ȡ�Ż�ȯ����*/ 
					if(ET_couponName.getText().toString().equals("")) {
						Toast.makeText(CouponSellerAddActivity.this, "�Ż�ȯ�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_couponName.setFocusable(true);
						ET_couponName.requestFocus();
						return;	
					}
					coupon.setCouponName(ET_couponName.getText().toString());
					/*��֤��ȡ���*/ 
					if(ET_couponMoney.getText().toString().equals("")) {
						Toast.makeText(CouponSellerAddActivity.this, "������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_couponMoney.setFocusable(true);
						ET_couponMoney.requestFocus();
						return;	
					}
					coupon.setCouponMoney(Float.parseFloat(ET_couponMoney.getText().toString()));
					/*��֤��ȡ����ʱ��*/ 
					if(ET_couponTime.getText().toString().equals("")) {
						Toast.makeText(CouponSellerAddActivity.this, "����ʱ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_couponTime.setFocusable(true);
						ET_couponTime.requestFocus();
						return;	
					}
					coupon.setCouponTime(ET_couponTime.getText().toString());
					/*����ҵ���߼����ϴ��Ż�ȯ��Ϣ*/
					CouponSellerAddActivity.this.setTitle("�����ϴ��Ż�ȯ��Ϣ���Ե�...");
					String result = couponService.AddCoupon(coupon);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
