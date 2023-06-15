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
	// �������ذ�ť
	private Button btnReturn;
	// �����û����ؼ�
	private TextView TV_user_name;
	// ������¼����ؼ�
	private TextView TV_password;
	// ���������ؼ�
	private TextView TV_name;
	// �����Ա�ؼ�
	private TextView TV_sex;
	// �����������ڿؼ�
	private TextView TV_birthDate;
	// �����û���ƬͼƬ��
	private ImageView iv_userImage;
	// �����û�qq�ؼ�
	private TextView TV_qq;
	// ��������ؼ�
	private TextView TV_email;
	// ����ע��ʱ��ؼ�
	private TextView TV_regTime;
	// �����û���ַ�ؼ�
	private TextView TV_address;
	// ����γ�ȿؼ�
	private TextView TV_latitude;
	// �������ȿؼ�
	private TextView TV_longitude;
	/* Ҫ������û���Ϣ */
	UserInfo userInfo = new UserInfo(); 
	/* �û�����ҵ���߼��� */
	private UserInfoService userInfoService = new UserInfoService();
	private String user_name;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.userinfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴�û�����");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
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
	/* ��ʼ����ʾ������������ */
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
			// ��ȡͼƬ����
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
