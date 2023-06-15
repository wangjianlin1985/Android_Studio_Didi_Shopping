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
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明优惠券idTextView
	private TextView TV_couponId;
	// 声明优惠券名称输入框
	private EditText ET_couponName;
	// 声明金额输入框
	private EditText ET_couponMoney;
	// 声明发放商家下拉框
	private Spinner spinner_sellerObj;
	private ArrayAdapter<String> sellerObj_adapter;
	private static  String[] sellerObj_ShowText  = null;
	private List<Seller> sellerList = null;
	/*发放商家管理业务逻辑层*/
	private SellerService sellerService = new SellerService();
	// 声明发放用户下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*发放用户管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明过期时间输入框
	private EditText ET_couponTime;
	protected String carmera_path;
	/*要保存的优惠券信息*/
	Coupon coupon = new Coupon();
	/*优惠券管理业务逻辑层*/
	private CouponService couponService = new CouponService();

	private int couponId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.coupon_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑优惠券信息");
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
		// 获取所有的发放商家
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
		// 将可选内容与ArrayAdapter连接起来
		sellerObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, sellerObj_ShowText);
		// 设置图书类别下拉列表的风格
		sellerObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_sellerObj.setAdapter(sellerObj_adapter);
		// 添加事件Spinner事件监听
		spinner_sellerObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				coupon.setSellerObj(sellerList.get(arg2).getSellUserName()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_sellerObj.setVisibility(View.VISIBLE);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的发放用户
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
		// 将可选内容与ArrayAdapter连接起来
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// 设置图书类别下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				coupon.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_couponTime = (EditText) findViewById(R.id.ET_couponTime);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		couponId = extras.getInt("couponId");
		/*单击修改优惠券按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取优惠券名称*/ 
					if(ET_couponName.getText().toString().equals("")) {
						Toast.makeText(CouponEditActivity.this, "优惠券名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_couponName.setFocusable(true);
						ET_couponName.requestFocus();
						return;	
					}
					coupon.setCouponName(ET_couponName.getText().toString());
					/*验证获取金额*/ 
					if(ET_couponMoney.getText().toString().equals("")) {
						Toast.makeText(CouponEditActivity.this, "金额输入不能为空!", Toast.LENGTH_LONG).show();
						ET_couponMoney.setFocusable(true);
						ET_couponMoney.requestFocus();
						return;	
					}
					coupon.setCouponMoney(Float.parseFloat(ET_couponMoney.getText().toString()));
					/*验证获取过期时间*/ 
					if(ET_couponTime.getText().toString().equals("")) {
						Toast.makeText(CouponEditActivity.this, "过期时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_couponTime.setFocusable(true);
						ET_couponTime.requestFocus();
						return;	
					}
					coupon.setCouponTime(ET_couponTime.getText().toString());
					/*调用业务逻辑层上传优惠券信息*/
					CouponEditActivity.this.setTitle("正在更新优惠券信息，稍等...");
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

	/* 初始化显示编辑界面的数据 */
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
