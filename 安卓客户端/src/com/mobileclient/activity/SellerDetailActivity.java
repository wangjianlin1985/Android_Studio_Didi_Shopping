package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Seller;
import com.mobileclient.service.SellerService;
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
public class SellerDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// �����̼��˺ſؼ�
	private TextView TV_sellUserName;
	// ������¼����ؼ�
	private TextView TV_password;
	// �����̼����ƿؼ�
	private TextView TV_sellerName;
	// ������ϵ�绰�ؼ�
	private TextView TV_telephone;
	// ������פ���ڿؼ�
	private TextView TV_addDate;
	// �����̼ҵ�ַ�ؼ�
	private TextView TV_address;
	// ����γ�ȿؼ�
	private TextView TV_latitude;
	// �������ȿؼ�
	private TextView TV_longitude;
	/* Ҫ������̼���Ϣ */
	Seller seller = new Seller(); 
	/* �̼ҹ���ҵ���߼��� */
	private SellerService sellerService = new SellerService();
	private String sellUserName;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.seller_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴�̼�����");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_sellUserName = (TextView) findViewById(R.id.TV_sellUserName);
		TV_password = (TextView) findViewById(R.id.TV_password);
		TV_sellerName = (TextView) findViewById(R.id.TV_sellerName);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		TV_addDate = (TextView) findViewById(R.id.TV_addDate);
		TV_address = (TextView) findViewById(R.id.TV_address);
		TV_latitude = (TextView) findViewById(R.id.TV_latitude);
		TV_longitude = (TextView) findViewById(R.id.TV_longitude);
		Bundle extras = this.getIntent().getExtras();
		sellUserName = extras.getString("sellUserName");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SellerDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    seller = sellerService.GetSeller(sellUserName); 
		this.TV_sellUserName.setText(seller.getSellUserName());
		this.TV_password.setText(seller.getPassword());
		this.TV_sellerName.setText(seller.getSellerName());
		this.TV_telephone.setText(seller.getTelephone());
		Date addDate = new Date(seller.getAddDate().getTime());
		String addDateStr = (addDate.getYear() + 1900) + "-" + (addDate.getMonth()+1) + "-" + addDate.getDate();
		this.TV_addDate.setText(addDateStr);
		this.TV_address.setText(seller.getAddress());
		this.TV_latitude.setText(seller.getLatitude() + "");
		this.TV_longitude.setText(seller.getLongitude() + "");
	} 
}
