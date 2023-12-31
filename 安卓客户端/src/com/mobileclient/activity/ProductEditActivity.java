package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Product;
import com.mobileclient.service.ProductService;
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

public class ProductEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明商品编号TextView
	private TextView TV_productNo;
	// 声明商品类目下拉框
	private Spinner spinner_productClassObj;
	private ArrayAdapter<String> productClassObj_adapter;
	private static  String[] productClassObj_ShowText  = null;
	private List<ProductClass> productClassList = null;
	/*商品类目管理业务逻辑层*/
	private ProductClassService productClassService = new ProductClassService();
	// 声明商品名称输入框
	private EditText ET_productName;
	// 声明物品描述输入框
	private EditText ET_productDesc;
	// 声明物品价格输入框
	private EditText ET_price;
	// 声明物品图片图片框控件
	private ImageView iv_productPhoto;
	private Button btn_productPhoto;
	protected int REQ_CODE_SELECT_IMAGE_productPhoto = 1;
	private int REQ_CODE_CAMERA_productPhoto = 2;
	// 声明物品存货输入框
	private EditText ET_stockDesc;
	protected String carmera_path;
	/*要保存的商品信息*/
	Product product = new Product();
	/*商品管理业务逻辑层*/
	private ProductService productService = new ProductService();

	private String productNo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.product_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑商品信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_productNo = (TextView) findViewById(R.id.TV_productNo);
		spinner_productClassObj = (Spinner) findViewById(R.id.Spinner_productClassObj);
		// 获取所有的商品类目
		try {
			productClassList = productClassService.QueryProductClass(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int productClassCount = productClassList.size();
		productClassObj_ShowText = new String[productClassCount];
		for(int i=0;i<productClassCount;i++) { 
			productClassObj_ShowText[i] = productClassList.get(i).getClassName();
		}
		// 将可选内容与ArrayAdapter连接起来
		productClassObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, productClassObj_ShowText);
		// 设置图书类别下拉列表的风格
		productClassObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_productClassObj.setAdapter(productClassObj_adapter);
		// 添加事件Spinner事件监听
		spinner_productClassObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				product.setProductClassObj(productClassList.get(arg2).getClassId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_productClassObj.setVisibility(View.VISIBLE);
		ET_productName = (EditText) findViewById(R.id.ET_productName);
		ET_productDesc = (EditText) findViewById(R.id.ET_productDesc);
		ET_price = (EditText) findViewById(R.id.ET_price);
		iv_productPhoto = (ImageView) findViewById(R.id.iv_productPhoto);
		/*单击图片显示控件时进行图片的选择*/
		iv_productPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(ProductEditActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_productPhoto);
			}
		});
		btn_productPhoto = (Button) findViewById(R.id.btn_productPhoto);
		btn_productPhoto.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_productPhoto.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_productPhoto);  
			}
		});
		ET_stockDesc = (EditText) findViewById(R.id.ET_stockDesc);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		productNo = extras.getString("productNo");
		/*单击修改商品按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取商品名称*/ 
					if(ET_productName.getText().toString().equals("")) {
						Toast.makeText(ProductEditActivity.this, "商品名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_productName.setFocusable(true);
						ET_productName.requestFocus();
						return;	
					}
					product.setProductName(ET_productName.getText().toString());
					/*验证获取物品描述*/ 
					if(ET_productDesc.getText().toString().equals("")) {
						Toast.makeText(ProductEditActivity.this, "物品描述输入不能为空!", Toast.LENGTH_LONG).show();
						ET_productDesc.setFocusable(true);
						ET_productDesc.requestFocus();
						return;	
					}
					product.setProductDesc(ET_productDesc.getText().toString());
					/*验证获取物品价格*/ 
					if(ET_price.getText().toString().equals("")) {
						Toast.makeText(ProductEditActivity.this, "物品价格输入不能为空!", Toast.LENGTH_LONG).show();
						ET_price.setFocusable(true);
						ET_price.requestFocus();
						return;	
					}
					product.setPrice(Float.parseFloat(ET_price.getText().toString()));
					if (!product.getProductPhoto().startsWith("upload/")) {
						//如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
						ProductEditActivity.this.setTitle("正在上传图片，稍等...");
						String productPhoto = HttpUtil.uploadFile(product.getProductPhoto());
						ProductEditActivity.this.setTitle("图片上传完毕！");
						product.setProductPhoto(productPhoto);
					} 
					/*验证获取物品存货*/ 
					if(ET_stockDesc.getText().toString().equals("")) {
						Toast.makeText(ProductEditActivity.this, "物品存货输入不能为空!", Toast.LENGTH_LONG).show();
						ET_stockDesc.setFocusable(true);
						ET_stockDesc.requestFocus();
						return;	
					}
					product.setStockDesc(ET_stockDesc.getText().toString());
					/*调用业务逻辑层上传商品信息*/
					ProductEditActivity.this.setTitle("正在更新商品信息，稍等...");
					String result = productService.UpdateProduct(product);
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
	    product = productService.GetProduct(productNo);
		this.TV_productNo.setText(productNo);
		for (int i = 0; i < productClassList.size(); i++) {
			if (product.getProductClassObj() == productClassList.get(i).getClassId()) {
				this.spinner_productClassObj.setSelection(i);
				break;
			}
		}
		this.ET_productName.setText(product.getProductName());
		this.ET_productDesc.setText(product.getProductDesc());
		this.ET_price.setText(product.getPrice() + "");
		byte[] productPhoto_data = null;
		try {
			// 获取图片数据
			productPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + product.getProductPhoto());
			Bitmap productPhoto = BitmapFactory.decodeByteArray(productPhoto_data, 0, productPhoto_data.length);
			this.iv_productPhoto.setImageBitmap(productPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.ET_stockDesc.setText(product.getStockDesc());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE_CAMERA_productPhoto  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_productPhoto.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_productPhoto.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// 把数据写入文件 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_productPhoto.setImageBitmap(booImageBm);
				this.iv_productPhoto.setScaleType(ScaleType.FIT_CENTER);
				this.product.setProductPhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_productPhoto && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/" + filename;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(filepath, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 128*128);
			opts.inJustDecodeBounds = false; 
			try { 
				Bitmap bm = BitmapFactory.decodeFile(filepath, opts);
				this.iv_productPhoto.setImageBitmap(bm); 
				this.iv_productPhoto.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			product.setProductPhoto(filename); 
		}
	}
}
