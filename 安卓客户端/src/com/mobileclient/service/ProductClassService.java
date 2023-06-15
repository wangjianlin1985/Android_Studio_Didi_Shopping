package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.ProductClass;
import com.mobileclient.util.HttpUtil;

/*��Ʒ������ҵ���߼���*/
public class ProductClassService {
	/* �����Ʒ��� */
	public String AddProductClass(ProductClass productClass) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("classId", productClass.getClassId() + "");
		params.put("className", productClass.getClassName());
		params.put("addTime", productClass.getAddTime().toString());
		params.put("classDesc", productClass.getClassDesc());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ProductClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯ��Ʒ��� */
	public List<ProductClass> QueryProductClass(ProductClass queryConditionProductClass) throws Exception {
		String urlString = HttpUtil.BASE_URL + "ProductClassServlet?action=query";
		if(queryConditionProductClass != null) {
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		ProductClassListHandler productClassListHander = new ProductClassListHandler();
		xr.setContentHandler(productClassListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<ProductClass> productClassList = productClassListHander.getProductClassList();
		return productClassList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<ProductClass> productClassList = new ArrayList<ProductClass>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				ProductClass productClass = new ProductClass();
				productClass.setClassId(object.getInt("classId"));
				productClass.setClassName(object.getString("className"));
				productClass.setAddTime(Timestamp.valueOf(object.getString("addTime")));
				productClass.setClassDesc(object.getString("classDesc"));
				productClassList.add(productClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productClassList;
	}

	/* ������Ʒ��� */
	public String UpdateProductClass(ProductClass productClass) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("classId", productClass.getClassId() + "");
		params.put("className", productClass.getClassName());
		params.put("addTime", productClass.getAddTime().toString());
		params.put("classDesc", productClass.getClassDesc());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ProductClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ����Ʒ��� */
	public String DeleteProductClass(int classId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("classId", classId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ProductClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "��Ʒ�����Ϣɾ��ʧ��!";
		}
	}

	/* ��������Ż�ȡ��Ʒ������ */
	public ProductClass GetProductClass(int classId)  {
		List<ProductClass> productClassList = new ArrayList<ProductClass>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("classId", classId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ProductClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				ProductClass productClass = new ProductClass();
				productClass.setClassId(object.getInt("classId"));
				productClass.setClassName(object.getString("className"));
				productClass.setAddTime(Timestamp.valueOf(object.getString("addTime")));
				productClass.setClassDesc(object.getString("classDesc"));
				productClassList.add(productClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = productClassList.size();
		if(size>0) return productClassList.get(0); 
		else return null; 
	}
}
