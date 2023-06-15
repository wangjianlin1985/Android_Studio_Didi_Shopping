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

/*��������ҵ���߼���*/
public class OrderInfoService {
	/* ��Ӷ��� */
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

	/* ��ѯ���� */
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

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
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
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
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
	
	
	
	
	/* �̼Ҳ�ѯ�����������Ķ��� */
	public List<OrderInfo> QueryWaitOrderInfo(String sellerUserName) throws Exception {
		String urlString = HttpUtil.BASE_URL + "OrderInfoServlet?action=queryWaitOrder";
		urlString += "&sellUserName=" + URLEncoder.encode(sellerUserName, "UTF-8") + ""; 
		  
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
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
	
	
	
	
	

	/* ���¶��� */
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
	
	
	/* �̼ҽӵ����� */
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
	

	/* ɾ������ */
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
			return "������Ϣɾ��ʧ��!";
		}
	}

	/* ���ݶ�����Ż�ȡ�������� */
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
