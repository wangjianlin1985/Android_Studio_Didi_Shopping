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

/*商品管理业务逻辑层*/
public class ProductService {
	/* 添加商品 */
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

	/* 查询商品 */
	public List<Product> QueryProduct(Product queryConditionProduct) throws Exception {
		String urlString = HttpUtil.BASE_URL + "ProductServlet?action=query";
		if(queryConditionProduct != null) {
			urlString += "&productNo=" + URLEncoder.encode(queryConditionProduct.getProductNo(), "UTF-8") + "";
			urlString += "&productClassObj=" + queryConditionProduct.getProductClassObj();
			urlString += "&productName=" + URLEncoder.encode(queryConditionProduct.getProductName(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
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
		//第2种是基于json数据格式解析，我们采用的是第2种
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

	/* 更新商品 */
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

	/* 删除商品 */
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
			return "商品信息删除失败!";
		}
	}

	/* 根据商品编号获取商品对象 */
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
