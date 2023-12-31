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
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
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
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.orderinfo_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_productObj = (TextView)convertView.findViewById(R.id.tv_productObj);
	  holder.tv_arrivePlace = (TextView)convertView.findViewById(R.id.tv_arrivePlace);
	  holder.tv_stateObj = (TextView)convertView.findViewById(R.id.tv_stateObj);
	  holder.tv_telephone = (TextView)convertView.findViewById(R.id.tv_telephone);
	  holder.tv_orderUser = (TextView)convertView.findViewById(R.id.tv_orderUser);
	  holder.tv_addTime = (TextView)convertView.findViewById(R.id.tv_addTime);
	  holder.tv_receiveSeller = (TextView)convertView.findViewById(R.id.tv_receiveSeller);
	  /*设置各个控件的展示内容*/
	  holder.tv_productObj.setText("商品信息：" + (new ProductService()).GetProduct(mData.get(position).get("productObj").toString()).getProductName());
	  holder.tv_arrivePlace.setText("配送地点：" + mData.get(position).get("arrivePlace").toString());
	  holder.tv_stateObj.setText("订单状态：" + (new OrderStateService()).GetOrderState(Integer.parseInt(mData.get(position).get("stateObj").toString())).getStateName());
	  holder.tv_telephone.setText("联系电话：" + mData.get(position).get("telephone").toString());
	  holder.tv_orderUser.setText("下单用户：" + (new UserInfoService()).GetUserInfo(mData.get(position).get("orderUser").toString()).getName());
	  holder.tv_addTime.setText("下单时间：" + mData.get(position).get("addTime").toString());
	  holder.tv_receiveSeller.setText("接单商家：" + mData.get(position).get("receiveSeller").toString());
	  /*返回修改好的view*/
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
