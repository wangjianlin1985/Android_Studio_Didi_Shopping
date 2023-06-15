package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Product;
import com.mobileclient.util.HttpUtil;

/*��Ʒ����ҵ���߼���*/
public class ProductService {
	/* �����Ʒ */
	public String AddProduct(Product product) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("productNo", product.getProductNo());
		params.put("productClassObj", product.getProductClassObj() + "");
		params.put("productName", product.getProductName());
		params.put("productDesc", product.getProductDesc());
		params.put("price", product.getPrice() + "");
		params.put("productPhoto", product.getProductPhoto());
		params.put("stockDesc", product.getStockDesc());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ProductServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯ��Ʒ */
	public List<Product> QueryProduct(Product queryConditionProduct) throws Exception {
		String urlString = HttpUtil.BASE_URL + "ProductServlet?action=query";
		if(queryConditionProduct != null) {
			urlString += "&productNo=" + URLEncoder.encode(queryConditionProduct.getProductNo(), "UTF-8") + "";
			urlString += "&productClassObj=" + queryConditionProduct.getProductClassObj();
			urlString += "&productName=" + URLEncoder.encode(queryConditionProduct.getProductName(), "UTF-8") + "";
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		ProductListHandler productListHander = new ProductListHandler();
		xr.setContentHandler(productListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Product> productList = productListHander.getProductList();
		return productList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<Product> productList = new ArrayList<Product>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Product product = new Product();
				product.setProductNo(object.getString("productNo"));
				product.setProductClassObj(object.getInt("productClassObj"));
				product.setProductName(object.getString("productName"));
				product.setProductDesc(object.getString("productDesc"));
				product.setPrice((float) object.getDouble("price"));
				product.setProductPhoto(object.getString("productPhoto"));
				product.setStockDesc(object.getString("stockDesc"));
				productList.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productList;
	}

	/* ������Ʒ */
	public String UpdateProduct(Product product) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("productNo", product.getProductNo());
		params.put("productClassObj", product.getProductClassObj() + "");
		params.put("productName", product.getProductName());
		params.put("productDesc", product.getProductDesc());
		params.put("price", product.getPrice() + "");
		params.put("productPhoto", product.getProductPhoto());
		params.put("stockDesc", product.getStockDesc());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ProductServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ����Ʒ */
	public String DeleteProduct(String productNo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("productNo", productNo);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ProductServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "��Ʒ��Ϣɾ��ʧ��!";
		}
	}

	/* ������Ʒ��Ż�ȡ��Ʒ���� */
	public Product GetProduct(String productNo)  {
		List<Product> productList = new ArrayList<Product>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("productNo", productNo);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ProductServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Product product = new Product();
				product.setProductNo(object.getString("productNo"));
				product.setProductClassObj(object.getInt("productClassObj"));
				product.setProductName(object.getString("productName"));
				product.setProductDesc(object.getString("productDesc"));
				product.setPrice((float) object.getDouble("price"));
				product.setProductPhoto(object.getString("productPhoto"));
				product.setStockDesc(object.getString("stockDesc"));
				productList.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = productList.size();
		if(size>0) return productList.get(0); 
		else return null; 
	}
}
