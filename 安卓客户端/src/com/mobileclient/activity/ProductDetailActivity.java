package com.mobileclient.activity;

import java.util.Date;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Product;
import com.mobileclient.service.ProductService;
import com.mobileclient.domain.ProductClass;
import com.mobileclient.service.ProductClassService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.content.Intent;
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
public class ProductDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// ��Ҫ�µ���ť
	private Button btnOrder;
	// ������Ʒ��ſؼ�
	private TextView TV_productNo;
	// ������Ʒ��Ŀ�ؼ�
	private TextView TV_productClassObj;
	// ������Ʒ���ƿؼ�
	private TextView TV_productName;
	// ������Ʒ�����ؼ�
	private TextView TV_productDesc;
	// ������Ʒ�۸�ؼ�
	private TextView TV_price;
	// ������ƷͼƬͼƬ��
	private ImageView iv_productPhoto;
	// ������Ʒ����ؼ�
	private TextView TV_stockDesc;
	/* Ҫ�������Ʒ��Ϣ */
	Product product = new Product(); 
	/* ��Ʒ����ҵ���߼��� */
	private ProductService productService = new ProductService();
	private ProductClassService productClassService = new ProductClassService();
	private String productNo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.product_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴��Ʒ����");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		btnOrder = (Button) findViewById(R.id.btnOrder);
		TV_productNo = (TextView) findViewById(R.id.TV_productNo);
		TV_productClassObj = (TextView) findViewById(R.id.TV_productClassObj);
		TV_productName = (TextView) findViewById(R.id.TV_productName);
		TV_productDesc = (TextView) findViewById(R.id.TV_productDesc);
		TV_price = (TextView) findViewById(R.id.TV_price);
		iv_productPhoto = (ImageView) findViewById(R.id.iv_productPhoto); 
		TV_stockDesc = (TextView) findViewById(R.id.TV_stockDesc);
		Bundle extras = this.getIntent().getExtras();
		productNo = extras.getString("productNo");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ProductDetailActivity.this.finish();
			}
		}); 
		
		btnOrder.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("productNo", productNo);
				Intent intent = new Intent(ProductDetailActivity.this,OrderInfoUserAddActivity.class);
				intent.putExtras(bundle);
				startActivity(intent); 
			}
		}); 
		Declare declare = (Declare)ProductDetailActivity.this.getApplication();
		if(!declare.getIdentify().equals("user"))
			btnOrder.setVisibility(View.GONE);
		
		initViewData();
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    product = productService.GetProduct(productNo); 
		this.TV_productNo.setText(product.getProductNo());
		ProductClass productClassObj = productClassService.GetProductClass(product.getProductClassObj());
		this.TV_productClassObj.setText(productClassObj.getClassName());
		this.TV_productName.setText(product.getProductName());
		this.TV_productDesc.setText(product.getProductDesc());
		this.TV_price.setText(product.getPrice() + "");
		byte[] productPhoto_data = null;
		try {
			// ��ȡͼƬ����
			productPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + product.getProductPhoto());
			Bitmap productPhoto = BitmapFactory.decodeByteArray(productPhoto_data, 0,productPhoto_data.length);
			this.iv_productPhoto.setImageBitmap(productPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TV_stockDesc.setText(product.getStockDesc());
	} 
}
