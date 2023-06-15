package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.ProductClass;
import com.mobileclient.service.ProductClassService;
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

public class ProductClassEditActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// ���������TextView
	private TextView TV_classId;
	// ����������������
	private EditText ET_className;
	// ����������ڿؼ�
	private DatePicker dp_addTime;
	// ����������������
	private EditText ET_classDesc;
	protected String carmera_path;
	/*Ҫ�������Ʒ�����Ϣ*/
	ProductClass productClass = new ProductClass();
	/*��Ʒ������ҵ���߼���*/
	private ProductClassService productClassService = new ProductClassService();

	private int classId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.productclass_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�༭��Ʒ�����Ϣ");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_classId = (TextView) findViewById(R.id.TV_classId);
		ET_className = (EditText) findViewById(R.id.ET_className);
		dp_addTime = (DatePicker)this.findViewById(R.id.dp_addTime);
		ET_classDesc = (EditText) findViewById(R.id.ET_classDesc);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		classId = extras.getInt("classId");
		/*�����޸���Ʒ���ť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ�������*/ 
					if(ET_className.getText().toString().equals("")) {
						Toast.makeText(ProductClassEditActivity.this, "����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_className.setFocusable(true);
						ET_className.requestFocus();
						return;	
					}
					productClass.setClassName(ET_className.getText().toString());
					/*��ȡ��������*/
					Date addTime = new Date(dp_addTime.getYear()-1900,dp_addTime.getMonth(),dp_addTime.getDayOfMonth());
					productClass.setAddTime(new Timestamp(addTime.getTime()));
					/*��֤��ȡ�������*/ 
					if(ET_classDesc.getText().toString().equals("")) {
						Toast.makeText(ProductClassEditActivity.this, "����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_classDesc.setFocusable(true);
						ET_classDesc.requestFocus();
						return;	
					}
					productClass.setClassDesc(ET_classDesc.getText().toString());
					/*����ҵ���߼����ϴ���Ʒ�����Ϣ*/
					ProductClassEditActivity.this.setTitle("���ڸ�����Ʒ�����Ϣ���Ե�...");
					String result = productClassService.UpdateProductClass(productClass);
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
	    productClass = productClassService.GetProductClass(classId);
		this.TV_classId.setText(classId+"");
		this.ET_className.setText(productClass.getClassName());
		Date addTime = new Date(productClass.getAddTime().getTime());
		this.dp_addTime.init(addTime.getYear() + 1900,addTime.getMonth(), addTime.getDate(), null);
		this.ET_classDesc.setText(productClass.getClassDesc());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
