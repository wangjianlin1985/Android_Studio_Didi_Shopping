package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class CouponEditActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// �����Ż�ȯidTextView
	private TextView TV_couponId;
	// �����Ż�ȯ���������
	private EditText ET_couponName;
	// ������������
	private EditText ET_couponMoney;
	// ���������̼�������
	private Spinner spinner_sellerObj;
	private ArrayAdapter<String> sellerObj_adapter;
	private static  String[] sellerObj_ShowText  = null;
	private List<Seller> sellerList = null;
	/*�����̼ҹ���ҵ���߼���*/
	private SellerService sellerService = new SellerService();
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

	private int couponId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.coupon_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�༭�Ż�ȯ��Ϣ");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_couponId = (TextView) findViewById(R.id.TV_couponId);
		ET_couponName = (EditText) findViewById(R.id.ET_couponName);
		ET_couponMoney = (EditText) findViewById(R.id.ET_couponMoney);
		spinner_sellerObj = (Spinner) findViewById(R.id.Spinner_sellerObj);
		// ��ȡ���еķ����̼�
		try {
			sellerList = sellerService.QuerySeller(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int sellerCount = sellerList.size();
		sellerObj_ShowText = new String[sellerCount];
		for(int i=0;i<sellerCount;i++) { 
			sellerObj_ShowText[i] = sellerList.get(i).getSellerName();
		}
		// ����ѡ������ArrayAdapter��������
		sellerObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, sellerObj_ShowText);
		// ����ͼ����������б�ķ��
		sellerObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_sellerObj.setAdapter(sellerObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_sellerObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				coupon.setSellerObj(sellerList.get(arg2).getSellUserName()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_sellerObj.setVisibility(View.VISIBLE);
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
		// ����ͼ����������б�ķ��
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
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		couponId = extras.getInt("couponId");
		/*�����޸��Ż�ȯ��ť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ�Ż�ȯ����*/ 
					if(ET_couponName.getText().toString().equals("")) {
						Toast.makeText(CouponEditActivity.this, "�Ż�ȯ�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_couponName.setFocusable(true);
						ET_couponName.requestFocus();
						return;	
					}
					coupon.setCouponName(ET_couponName.getText().toString());
					/*��֤��ȡ���*/ 
					if(ET_couponMoney.getText().toString().equals("")) {
						Toast.makeText(CouponEditActivity.this, "������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_couponMoney.setFocusable(true);
						ET_couponMoney.requestFocus();
						return;	
					}
					coupon.setCouponMoney(Float.parseFloat(ET_couponMoney.getText().toString()));
					/*��֤��ȡ����ʱ��*/ 
					if(ET_couponTime.getText().toString().equals("")) {
						Toast.makeText(CouponEditActivity.this, "����ʱ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_couponTime.setFocusable(true);
						ET_couponTime.requestFocus();
						return;	
					}
					coupon.setCouponTime(ET_couponTime.getText().toString());
					/*����ҵ���߼����ϴ��Ż�ȯ��Ϣ*/
					CouponEditActivity.this.setTitle("���ڸ����Ż�ȯ��Ϣ���Ե�...");
					String result = couponService.UpdateCoupon(coupon);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* ��ʼ����ʾ�༭��������� */
	private void initViewData() {
	    coupon = couponService.GetCoupon(couponId);
		this.TV_couponId.setText(couponId+"");
		this.ET_couponName.setText(coupon.getCouponName());
		this.ET_couponMoney.setText(coupon.getCouponMoney() + "");
		for (int i = 0; i < sellerList.size(); i++) {
			if (coupon.getSellerObj().equals(sellerList.get(i).getSellUserName())) {
				this.spinner_sellerObj.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < userInfoList.size(); i++) {
			if (coupon.getUserObj().equals(userInfoList.get(i).getUser_name())) {
				this.spinner_userObj.setSelection(i);
				break;
			}
		}
		this.ET_couponTime.setText(coupon.getCouponTime());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
