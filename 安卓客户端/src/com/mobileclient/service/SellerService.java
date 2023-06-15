package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Seller;
import com.mobileclient.util.HttpUtil;

/*�̼ҹ���ҵ���߼���*/
public class SellerService {
	/* ����̼� */
	public String AddSeller(Seller seller) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("sellUserName", seller.getSellUserName());
		params.put("password", seller.getPassword());
		params.put("sellerName", seller.getSellerName());
		params.put("telephone", seller.getTelephone());
		params.put("addDate", seller.getAddDate().toString());
		params.put("address", seller.getAddress());
		params.put("latitude", seller.getLatitude() + "");
		params.put("longitude", seller.getLongitude() + "");
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SellerServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯ�̼� */
	public List<Seller> QuerySeller(Seller queryConditionSeller) throws Exception {
		String urlString = HttpUtil.BASE_URL + "SellerServlet?action=query";
		if(queryConditionSeller != null) {
			urlString += "&sellUserName=" + URLEncoder.encode(queryConditionSeller.getSellUserName(), "UTF-8") + "";
			urlString += "&sellerName=" + URLEncoder.encode(queryConditionSeller.getSellerName(), "UTF-8") + "";
			urlString += "&telephone=" + URLEncoder.encode(queryConditionSeller.getTelephone(), "UTF-8") + "";
			if(queryConditionSeller.getAddDate() != null) {
				urlString += "&addDate=" + URLEncoder.encode(queryConditionSeller.getAddDate().toString(), "UTF-8");
			}
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		SellerListHandler sellerListHander = new SellerListHandler();
		xr.setContentHandler(sellerListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Seller> sellerList = sellerListHander.getSellerList();
		return sellerList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<Seller> sellerList = new ArrayList<Seller>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Seller seller = new Seller();
				seller.setSellUserName(object.getString("sellUserName"));
				seller.setPassword(object.getString("password"));
				seller.setSellerName(object.getString("sellerName"));
				seller.setTelephone(object.getString("telephone"));
				seller.setAddDate(Timestamp.valueOf(object.getString("addDate")));
				seller.setAddress(object.getString("address"));
				seller.setLatitude((float) object.getDouble("latitude"));
				seller.setLongitude((float) object.getDouble("longitude"));
				sellerList.add(seller);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sellerList;
	}

	/* �����̼� */
	public String UpdateSeller(Seller seller) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("sellUserName", seller.getSellUserName());
		params.put("password", seller.getPassword());
		params.put("sellerName", seller.getSellerName());
		params.put("telephone", seller.getTelephone());
		params.put("addDate", seller.getAddDate().toString());
		params.put("address", seller.getAddress());
		params.put("latitude", seller.getLatitude() + "");
		params.put("longitude", seller.getLongitude() + "");
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SellerServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ���̼� */
	public String DeleteSeller(String sellUserName) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("sellUserName", sellUserName);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SellerServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "�̼���Ϣɾ��ʧ��!";
		}
	}

	/* �����̼��˺Ż�ȡ�̼Ҷ��� */
	public Seller GetSeller(String sellUserName)  {
		List<Seller> sellerList = new ArrayList<Seller>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("sellUserName", sellUserName);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SellerServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Seller seller = new Seller();
				seller.setSellUserName(object.getString("sellUserName"));
				seller.setPassword(object.getString("password"));
				seller.setSellerName(object.getString("sellerName"));
				seller.setTelephone(object.getString("telephone"));
				seller.setAddDate(Timestamp.valueOf(object.getString("addDate")));
				seller.setAddress(object.getString("address"));
				seller.setLatitude((float) object.getDouble("latitude"));
				seller.setLongitude((float) object.getDouble("longitude"));
				sellerList.add(seller);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = sellerList.size();
		if(size>0) return sellerList.get(0); 
		else return null; 
	}
}
