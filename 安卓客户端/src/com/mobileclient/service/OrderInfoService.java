package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.OrderInfo;
import com.mobileclient.util.HttpUtil;

/*订单管理业务逻辑层*/
public class OrderInfoService {
	/* 添加订单 */
	public String AddOrderInfo(OrderInfo orderInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderInfo.getOrderId() + "");
		params.put("productObj", orderInfo.getProductObj());
		params.put("arrivePlace", orderInfo.getArrivePlace());
		params.put("latitude", orderInfo.getLatitude() + "");
		params.put("longitude", orderInfo.getLongitude() + "");
		params.put("stateObj", orderInfo.getStateObj() + "");
		params.put("telephone", orderInfo.getTelephone());
		params.put("orderUser", orderInfo.getOrderUser());
		params.put("addTime", orderInfo.getAddTime());
		params.put("receiveSeller", orderInfo.getReceiveSeller());
		params.put("receiveTime", orderInfo.getReceiveTime());
		params.put("memo", orderInfo.getMemo());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询订单 */
	public List<OrderInfo> QueryOrderInfo(OrderInfo queryConditionOrderInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "OrderInfoServlet?action=query";
		if(queryConditionOrderInfo != null) {
			urlString += "&productObj=" + URLEncoder.encode(queryConditionOrderInfo.getProductObj(), "UTF-8") + "";
			urlString += "&stateObj=" + queryConditionOrderInfo.getStateObj();
			urlString += "&telephone=" + URLEncoder.encode(queryConditionOrderInfo.getTelephone(), "UTF-8") + "";
			urlString += "&orderUser=" + URLEncoder.encode(queryConditionOrderInfo.getOrderUser(), "UTF-8") + "";
			urlString += "&addTime=" + URLEncoder.encode(queryConditionOrderInfo.getAddTime(), "UTF-8") + "";
			urlString += "&receiveSeller=" + URLEncoder.encode(queryConditionOrderInfo.getReceiveSeller(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		OrderInfoListHandler orderInfoListHander = new OrderInfoListHandler();
		xr.setContentHandler(orderInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<OrderInfo> orderInfoList = orderInfoListHander.getOrderInfoList();
		return orderInfoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setOrderId(object.getInt("orderId"));
				orderInfo.setProductObj(object.getString("productObj"));
				orderInfo.setArrivePlace(object.getString("arrivePlace"));
				orderInfo.setLatitude((float) object.getDouble("latitude"));
				orderInfo.setLongitude((float) object.getDouble("longitude"));
				orderInfo.setStateObj(object.getInt("stateObj"));
				orderInfo.setTelephone(object.getString("telephone"));
				orderInfo.setOrderUser(object.getString("orderUser"));
				orderInfo.setAddTime(object.getString("addTime"));
				orderInfo.setReceiveSeller(object.getString("receiveSeller"));
				orderInfo.setReceiveTime(object.getString("receiveTime"));
				orderInfo.setMemo(object.getString("memo"));
				orderInfoList.add(orderInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderInfoList;
	}
	
	
	
	
	/* 商家查询附近待抢购的订单 */
	public List<OrderInfo> QueryWaitOrderInfo(String sellerUserName) throws Exception {
		String urlString = HttpUtil.BASE_URL + "OrderInfoServlet?action=queryWaitOrder";
		urlString += "&sellUserName=" + URLEncoder.encode(sellerUserName, "UTF-8") + ""; 
		  
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setOrderId(object.getInt("orderId"));
				orderInfo.setProductObj(object.getString("productObj"));
				orderInfo.setArrivePlace(object.getString("arrivePlace"));
				orderInfo.setLatitude((float) object.getDouble("latitude"));
				orderInfo.setLongitude((float) object.getDouble("longitude"));
				orderInfo.setStateObj(object.getInt("stateObj"));
				orderInfo.setTelephone(object.getString("telephone"));
				orderInfo.setOrderUser(object.getString("orderUser"));
				orderInfo.setAddTime(object.getString("addTime"));
				orderInfo.setReceiveSeller(object.getString("receiveSeller"));
				orderInfo.setReceiveTime(object.getString("receiveTime"));
				orderInfo.setMemo(object.getString("memo"));
				orderInfoList.add(orderInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderInfoList;
	}
	
	
	
	
	

	/* 更新订单 */
	public String UpdateOrderInfo(OrderInfo orderInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderInfo.getOrderId() + "");
		params.put("productObj", orderInfo.getProductObj());
		params.put("arrivePlace", orderInfo.getArrivePlace());
		params.put("latitude", orderInfo.getLatitude() + "");
		params.put("longitude", orderInfo.getLongitude() + "");
		params.put("stateObj", orderInfo.getStateObj() + "");
		params.put("telephone", orderInfo.getTelephone());
		params.put("orderUser", orderInfo.getOrderUser());
		params.put("addTime", orderInfo.getAddTime());
		params.put("receiveSeller", orderInfo.getReceiveSeller());
		params.put("receiveTime", orderInfo.getReceiveTime());
		params.put("memo", orderInfo.getMemo());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	/* 商家接单订单 */
	public String sellerReceiveOrder(OrderInfo orderInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderInfo.getOrderId() + ""); 
		params.put("receiveSeller", orderInfo.getReceiveSeller()); 
		params.put("action", "sellerReceiveOrder");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	

	/* 删除订单 */
	public String DeleteOrderInfo(int orderId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "订单信息删除失败!";
		}
	}

	/* 根据订单编号获取订单对象 */
	public OrderInfo GetOrderInfo(int orderId)  {
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setOrderId(object.getInt("orderId"));
				orderInfo.setProductObj(object.getString("productObj"));
				orderInfo.setArrivePlace(object.getString("arrivePlace"));
				orderInfo.setLatitude((float) object.getDouble("latitude"));
				orderInfo.setLongitude((float) object.getDouble("longitude"));
				orderInfo.setStateObj(object.getInt("stateObj"));
				orderInfo.setTelephone(object.getString("telephone"));
				orderInfo.setOrderUser(object.getString("orderUser"));
				orderInfo.setAddTime(object.getString("addTime"));
				orderInfo.setReceiveSeller(object.getString("receiveSeller"));
				orderInfo.setReceiveTime(object.getString("receiveTime"));
				orderInfo.setMemo(object.getString("memo"));
				orderInfoList.add(orderInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = orderInfoList.size();
		if(size>0) return orderInfoList.get(0); 
		else return null; 
	}
}
