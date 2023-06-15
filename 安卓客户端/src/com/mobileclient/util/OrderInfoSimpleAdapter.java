package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.ProductService;
import com.mobileclient.service.OrderStateService;
import com.mobileclient.service.UserInfoService;
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

public class OrderInfoSimpleAdapter extends SimpleAdapter { 
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

    public OrderInfoSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.orderinfo_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*�󶨸�view�����ؼ�*/
	  holder.tv_productObj = (TextView)convertView.findViewById(R.id.tv_productObj);
	  holder.tv_arrivePlace = (TextView)convertView.findViewById(R.id.tv_arrivePlace);
	  holder.tv_stateObj = (TextView)convertView.findViewById(R.id.tv_stateObj);
	  holder.tv_telephone = (TextView)convertView.findViewById(R.id.tv_telephone);
	  holder.tv_orderUser = (TextView)convertView.findViewById(R.id.tv_orderUser);
	  holder.tv_addTime = (TextView)convertView.findViewById(R.id.tv_addTime);
	  holder.tv_receiveSeller = (TextView)convertView.findViewById(R.id.tv_receiveSeller);
	  /*���ø����ؼ���չʾ����*/
	  holder.tv_productObj.setText("��Ʒ��Ϣ��" + (new ProductService()).GetProduct(mData.get(position).get("productObj").toString()).getProductName());
	  holder.tv_arrivePlace.setText("���͵ص㣺" + mData.get(position).get("arrivePlace").toString());
	  holder.tv_stateObj.setText("����״̬��" + (new OrderStateService()).GetOrderState(Integer.parseInt(mData.get(position).get("stateObj").toString())).getStateName());
	  holder.tv_telephone.setText("��ϵ�绰��" + mData.get(position).get("telephone").toString());
	  holder.tv_orderUser.setText("�µ��û���" + (new UserInfoService()).GetUserInfo(mData.get(position).get("orderUser").toString()).getName());
	  holder.tv_addTime.setText("�µ�ʱ�䣺" + mData.get(position).get("addTime").toString());
	  holder.tv_receiveSeller.setText("�ӵ��̼ң�" + mData.get(position).get("receiveSeller").toString());
	  /*�����޸ĺõ�view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_productObj;
    	TextView tv_arrivePlace;
    	TextView tv_stateObj;
    	TextView tv_telephone;
    	TextView tv_orderUser;
    	TextView tv_addTime;
    	TextView tv_receiveSeller;
    }
} 
