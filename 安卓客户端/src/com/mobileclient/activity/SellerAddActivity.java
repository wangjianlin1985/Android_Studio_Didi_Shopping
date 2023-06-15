package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
public class SellerAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// �����̼��˺������
	private EditText ET_sellUserName;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.seller_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("����̼�");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_sellUserName = (EditText) findViewById(R.id.ET_sellUserName);
		ET_password = (EditText) findViewById(R.id.ET_password);
		ET_sellerName = (EditText) findViewById(R.id.ET_sellerName);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		dp_addDate = (DatePicker)this.findViewById(R.id.dp_addDate);
		ET_address = (EditText) findViewById(R.id.ET_address);
		ET_latitude = (EditText) findViewById(R.id.ET_latitude);
		ET_longitude = (EditText) findViewById(R.id.ET_longitude);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*��������̼Ұ�ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ�̼��˺�*/
					if(ET_sellUserName.getText().toString().equals("")) {
						Toast.makeText(SellerAddActivity.this, "�̼��˺����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_sellUserName.setFocusable(true);
						ET_sellUserName.requestFocus();
						return;
					}
					seller.setSellUserName(ET_sellUserName.getText().toString());
					/*��֤��ȡ��¼����*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(SellerAddActivity.this, "��¼�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					seller.setPassword(ET_password.getText().toString());
					/*��֤��ȡ�̼�����*/ 
					if(ET_sellerName.getText().toString().equals("")) {
						Toast.makeText(SellerAddActivity.this, "�̼��������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_sellerName.setFocusable(true);
						ET_sellerName.requestFocus();
						return;	
					}
					seller.setSellerName(ET_sellerName.getText().toString());
					/*��֤��ȡ��ϵ�绰*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(SellerAddActivity.this, "��ϵ�绰���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					seller.setTelephone(ET_telephone.getText().toString());
					/*��ȡ��פ����*/
					Date addDate = new Date(dp_addDate.getYear()-1900,dp_addDate.getMonth(),dp_addDate.getDayOfMonth());
					seller.setAddDate(new Timestamp(addDate.getTime()));
					/*��֤��ȡ�̼ҵ�ַ*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(SellerAddActivity.this, "�̼ҵ�ַ���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					seller.setAddress(ET_address.getText().toString());
					/*��֤��ȡγ��*/ 
					if(ET_latitude.getText().toString().equals("")) {
						Toast.makeText(SellerAddActivity.this, "γ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_latitude.setFocusable(true);
						ET_latitude.requestFocus();
						return;	
					}
					seller.setLatitude(Float.parseFloat(ET_latitude.getText().toString()));
					/*��֤��ȡ����*/ 
					if(ET_longitude.getText().toString().equals("")) {
						Toast.makeText(SellerAddActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_longitude.setFocusable(true);
						ET_longitude.requestFocus();
						return;	
					}
					seller.setLongitude(Float.parseFloat(ET_longitude.getText().toString()));
					/*����ҵ���߼����ϴ��̼���Ϣ*/
					SellerAddActivity.this.setTitle("�����ϴ��̼���Ϣ���Ե�...");
					String result = sellerService.AddSeller(seller);
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
