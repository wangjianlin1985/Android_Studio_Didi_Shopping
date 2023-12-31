package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Product;
import com.mobileclient.service.ProductService;
import com.mobileclient.util.ActivityUtils;import com.mobileclient.util.ProductSimpleAdapter;
import com.mobileclient.util.HttpUtil;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class ProductListActivity extends Activity {
	ProductSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	String productNo;
	/* 商品操作业务逻辑层对象 */
	ProductService productService = new ProductService();
	/*保存查询参数条件的商品对象*/
	private Product queryConditionProduct;

	private MyProgressDialog dialog; //进度条	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.product_list);
		dialog = MyProgressDialog.getInstance(this);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		//标题栏控件
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(ProductListActivity.this, ProductQueryActivity.class);
				startActivityForResult(intent,ActivityUtils.QUERY_CODE);//此处的requestCode应与下面结果处理函中调用的requestCode一致
			}
		});
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("商品查询列表");
		ImageView add_btn = (ImageView) this.findViewById(R.id.add_btn);
		add_btn.setOnClickListener(new android.view.View.OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(ProductListActivity.this, ProductAddActivity.class);
				startActivityForResult(intent,ActivityUtils.ADD_CODE);
			}
		});
		
		if(!declare.getIdentify().equals("admin")) {
			add_btn.setVisibility(View.GONE);
		}
			
		
		setViews();
	}

	//结果处理函数，当从secondActivity中返回时调用此函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ActivityUtils.QUERY_CODE && resultCode==RESULT_OK){
        	Bundle extras = data.getExtras();
        	if(extras != null)
        		queryConditionProduct = (Product)extras.getSerializable("queryConditionProduct");
        	setViews();
        }
        if(requestCode==ActivityUtils.EDIT_CODE && resultCode==RESULT_OK){
        	setViews();
        }
        if(requestCode == ActivityUtils.ADD_CODE && resultCode == RESULT_OK) {
        	queryConditionProduct = null;
        	setViews();
        }
    }

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		dialog.show();
		final Handler handler = new Handler();
		new Thread(){
			@Override
			public void run() {
				//在子线程中进行下载数据操作
				list = getDatas();
				//发送消失到handler，通知主线程下载完成
				handler.post(new Runnable() {
					@Override
					public void run() {
						dialog.cancel();
						adapter = new ProductSimpleAdapter(ProductListActivity.this, list,
	        					R.layout.product_list_item,
	        					new String[] { "productNo","productClassObj","productName","price","productPhoto" },
	        					new int[] { R.id.tv_productNo,R.id.tv_productClassObj,R.id.tv_productName,R.id.tv_price,R.id.iv_productPhoto,},lv);
	        			lv.setAdapter(adapter);
					}
				});
			}
		}.start(); 

		// 添加长按点击
		lv.setOnCreateContextMenuListener(productListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	String productNo = list.get(arg2).get("productNo").toString();
            	Intent intent = new Intent();
            	intent.setClass(ProductListActivity.this, ProductDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putString("productNo", productNo);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener productListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			menu.add(0, 0, 0, "编辑商品信息"); 
			menu.add(0, 1, 0, "删除商品信息");
		}
	};

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //编辑商品信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取商品编号
			productNo = list.get(position).get("productNo").toString();
			Intent intent = new Intent();
			intent.setClass(ProductListActivity.this, ProductEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("productNo", productNo);
			intent.putExtras(bundle);
			startActivityForResult(intent,ActivityUtils.EDIT_CODE);
		} else if (item.getItemId() == 1) {// 删除商品信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取商品编号
			productNo = list.get(position).get("productNo").toString();
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// 删除
	protected void dialog() {
		Builder builder = new Builder(ProductListActivity.this);
		builder.setMessage("确认删除吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = productService.DeleteProduct(productNo);
				Toast.makeText(getApplicationContext(), result, 1).show();
				setViews();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private List<Map<String, Object>> getDatas() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			/* 查询商品信息 */
			List<Product> productList = productService.QueryProduct(queryConditionProduct);
			for (int i = 0; i < productList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("productNo", productList.get(i).getProductNo());
				map.put("productClassObj", productList.get(i).getProductClassObj());
				map.put("productName", productList.get(i).getProductName());
				map.put("price", productList.get(i).getPrice());
				/*byte[] productPhoto_data = ImageService.getImage(HttpUtil.BASE_URL+ productList.get(i).getProductPhoto());// 获取图片数据
				BitmapFactory.Options productPhoto_opts = new BitmapFactory.Options();  
				productPhoto_opts.inJustDecodeBounds = true;  
				BitmapFactory.decodeByteArray(productPhoto_data, 0, productPhoto_data.length, productPhoto_opts); 
				productPhoto_opts.inSampleSize = photoListActivity.computeSampleSize(productPhoto_opts, -1, 100*100); 
				productPhoto_opts.inJustDecodeBounds = false; 
				try {
					Bitmap productPhoto = BitmapFactory.decodeByteArray(productPhoto_data, 0, productPhoto_data.length, productPhoto_opts);
					map.put("productPhoto", productPhoto);
				} catch (OutOfMemoryError err) { }*/
				map.put("productPhoto", HttpUtil.BASE_URL+ productList.get(i).getProductPhoto());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

}
