package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.ProductClassService;
import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class ProductSimpleAdapter extends SimpleAdapter { 
	/*��Ҫ�󶨵Ŀؼ���Դid*/
    private int[] mTo;
    /*map���Ϲؼ�������*/
    private String[] mFrom;
/*��Ҫ�󶨵�����*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //ͼƬ�첽���������,���ڴ滺����ļ�����
    private SyncImageLoader syncImageLoader;

    public ProductSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*��һ��װ�����viewʱ=null,���½�һ������inflate��Ⱦһ��view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.product_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*�󶨸�view�����ؼ�*/
	  holder.tv_productNo = (TextView)convertView.findViewById(R.id.tv_productNo);
	  holder.tv_productClassObj = (TextView)convertView.findViewById(R.id.tv_productClassObj);
	  holder.tv_productName = (TextView)convertView.findViewById(R.id.tv_productName);
	  holder.tv_price = (TextView)convertView.findViewById(R.id.tv_price);
	  holder.iv_productPhoto = (ImageView)convertView.findViewById(R.id.iv_productPhoto);
	  /*���ø����ؼ���չʾ����*/
	  holder.tv_productNo.setText("��Ʒ��ţ�" + mData.get(position).get("productNo").toString());
	  holder.tv_productClassObj.setText("��Ʒ��Ŀ��" + (new ProductClassService()).GetProductClass(Integer.parseInt(mData.get(position).get("productClassObj").toString())).getClassName());
	  holder.tv_productName.setText("��Ʒ���ƣ�" + mData.get(position).get("productName").toString());
	  holder.tv_price.setText("��Ʒ�۸�" + mData.get(position).get("price").toString());
	  holder.iv_productPhoto.setImageResource(R.drawable.default_photo);
	  ImageLoadListener productPhotoLoadListener = new ImageLoadListener(mListView,R.id.iv_productPhoto);
	  syncImageLoader.loadImage(position,(String)mData.get(position).get("productPhoto"),productPhotoLoadListener);  
	  /*�����޸ĺõ�view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_productNo;
    	TextView tv_productClassObj;
    	TextView tv_productName;
    	TextView tv_price;
    	ImageView iv_productPhoto;
    }
} 
