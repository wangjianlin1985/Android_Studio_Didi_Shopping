package com.mobileclient.activity;

import java.util.Date;
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
public class UserInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明用户名控件
	private TextView TV_user_name;
	// 声明登录密码控件
	private TextView TV_password;
	// 声明姓名控件
	private TextView TV_name;
	// 声明性别控件
	private TextView TV_sex;
	// 声明出生日期控件
	private TextView TV_birthDate;
	// 声明用户照片图片框
	private ImageView iv_userImage;
	// 声明用户qq控件
	private TextView TV_qq;
	// 声明邮箱控件
	private TextView TV_email;
	// 声明注册时间控件
	private TextView TV_regTime;
	// 声明用户地址控件
	private TextView TV_address;
	// 声明纬度控件
	private TextView TV_latitude;
	// 声明经度控件
	private TextView TV_longitude;
	/* 要保存的用户信息 */
	UserInfo userInfo = new UserInfo(); 
	/* 用户管理业务逻辑层 */
	private UserInfoService userInfoService = new UserInfoService();
	private String user_name;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.userinfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看用户详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_user_name = (TextView) findViewById(R.id.TV_user_name);
		TV_password = (TextView) findViewById(R.id.TV_password);
		TV_name = (TextView) findViewById(R.id.TV_name);
		TV_sex = (TextView) findViewById(R.id.TV_sex);
		TV_birthDate = (TextView) findViewById(R.id.TV_birthDate);
		iv_userImage = (ImageView) findViewById(R.id.iv_userImage); 
		TV_qq = (TextView) findViewById(R.id.TV_qq);
		TV_email = (TextView) findViewById(R.id.TV_email);
		TV_regTime = (TextView) findViewById(R.id.TV_regTime);
		TV_address = (TextView) findViewById(R.id.TV_address);
		TV_latitude = (TextView) findViewById(R.id.TV_latitude);
		TV_longitude = (TextView) findViewById(R.id.TV_longitude);
		Bundle extras = this.getIntent().getExtras();
		user_name = extras.getString("user_name");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				UserInfoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    userInfo = userInfoService.GetUserInfo(user_name); 
		this.TV_user_name.setText(userInfo.getUser_name());
		this.TV_password.setText(userInfo.getPassword());
		this.TV_name.setText(userInfo.getName());
		this.TV_sex.setText(userInfo.getSex());
		Date birthDate = new Date(userInfo.getBirthDate().getTime());
		String birthDateStr = (birthDate.getYear() + 1900) + "-" + (birthDate.getMonth()+1) + "-" + birthDate.getDate();
		this.TV_birthDate.setText(birthDateStr);
		byte[] userImage_data = null;
		try {
			// 获取图片数据
			userImage_data = ImageService.getImage(HttpUtil.BASE_URL + userInfo.getUserImage());
			Bitmap userImage = BitmapFactory.decodeByteArray(userImage_data, 0,userImage_data.length);
			this.iv_userImage.setImageBitmap(userImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TV_qq.setText(userInfo.getQq());
		this.TV_email.setText(userInfo.getEmail());
		this.TV_regTime.setText(userInfo.getRegTime());
		this.TV_address.setText(userInfo.getAddress());
		this.TV_latitude.setText(userInfo.getLatitude() + "");
		this.TV_longitude.setText(userInfo.getLongitude() + "");
	} 
}
