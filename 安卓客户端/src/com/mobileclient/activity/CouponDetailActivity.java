package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Coupon;
import com.mobileclient.service.CouponService;
import com.mobileclient.domain.Seller;
import com.mobileclient.service.SellerService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class CouponDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明优惠券id控件
	private TextView TV_couponId;
	// 声明优惠券名称控件
	private TextView TV_couponName;
	// 声明金额控件
	private TextView TV_couponMoney;
	// 声明发放商家控件
	private TextView TV_sellerObj;
	// 声明发放用户控件
	private TextView TV_userObj;
	// 声明过期时间控件
	private TextView TV_couponTime;
	/* 要保存的优惠券信息 */
	Coupon coupon = new Coupon(); 
	/* 优惠券管理业务逻辑层 */
	private CouponService couponService = new CouponService();
	private SellerService sellerService = new SellerService();
	private UserInfoService userInfoService = new UserInfoService();
	private int couponId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.coupon_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看优惠券详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_couponId = (TextView) findViewById(R.id.TV_couponId);
		TV_couponName = (TextView) findViewById(R.id.TV_couponName);
		TV_couponMoney = (TextView) findViewById(R.id.TV_couponMoney);
		TV_sellerObj = (TextView) findViewById(R.id.TV_sellerObj);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_couponTime = (TextView) findViewById(R.id.TV_couponTime);
		Bundle extras = this.getIntent().getExtras();
		couponId = extras.getInt("couponId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CouponDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    coupon = couponService.GetCoupon(couponId); 
		this.TV_couponId.setText(coupon.getCouponId() + "");
		this.TV_couponName.setText(coupon.getCouponName());
		this.TV_couponMoney.setText(coupon.getCouponMoney() + "");
		Seller sellerObj = sellerService.GetSeller(coupon.getSellerObj());
		this.TV_sellerObj.setText(sellerObj.getSellerName());
		UserInfo userObj = userInfoService.GetUserInfo(coupon.getUserObj());
		this.TV_userObj.setText(userObj.getName());
		this.TV_couponTime.setText(coupon.getCouponTime());
	} 
}
