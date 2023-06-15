package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Coupon;
import com.mobileclient.util.HttpUtil;

/*�Ż�ȯ����ҵ���߼���*/
public class CouponService {
	/* ����Ż�ȯ */
	public String AddCoupon(Coupon coupon) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("couponId", coupon.getCouponId() + "");
		params.put("couponName", coupon.getCouponName());
		params.put("couponMoney", coupon.getCouponMoney() + "");
		params.put("sellerObj", coupon.getSellerObj());
		params.put("userObj", coupon.getUserObj());
		params.put("couponTime", coupon.getCouponTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CouponServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯ�Ż�ȯ */
	public List<Coupon> QueryCoupon(Coupon queryConditionCoupon) throws Exception {
		String urlString = HttpUtil.BASE_URL + "CouponServlet?action=query";
		if(queryConditionCoupon != null) {
			urlString += "&couponName=" + URLEncoder.encode(queryConditionCoupon.getCouponName(), "UTF-8") + "";
			urlString += "&sellerObj=" + URLEncoder.encode(queryConditionCoupon.getSellerObj(), "UTF-8") + "";
			urlString += "&userObj=" + URLEncoder.encode(queryConditionCoupon.getUserObj(), "UTF-8") + "";
			urlString += "&couponTime=" + URLEncoder.encode(queryConditionCoupon.getCouponTime(), "UTF-8") + "";
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		CouponListHandler couponListHander = new CouponListHandler();
		xr.setContentHandler(couponListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Coupon> couponList = couponListHander.getCouponList();
		return couponList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<Coupon> couponList = new ArrayList<Coupon>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Coupon coupon = new Coupon();
				coupon.setCouponId(object.getInt("couponId"));
				coupon.setCouponName(object.getString("couponName"));
				coupon.setCouponMoney((float) object.getDouble("couponMoney"));
				coupon.setSellerObj(object.getString("sellerObj"));
				coupon.setUserObj(object.getString("userObj"));
				coupon.setCouponTime(object.getString("couponTime"));
				couponList.add(coupon);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return couponList;
	}

	/* �����Ż�ȯ */
	public String UpdateCoupon(Coupon coupon) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("couponId", coupon.getCouponId() + "");
		params.put("couponName", coupon.getCouponName());
		params.put("couponMoney", coupon.getCouponMoney() + "");
		params.put("sellerObj", coupon.getSellerObj());
		params.put("userObj", coupon.getUserObj());
		params.put("couponTime", coupon.getCouponTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CouponServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ���Ż�ȯ */
	public String DeleteCoupon(int couponId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("couponId", couponId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CouponServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "�Ż�ȯ��Ϣɾ��ʧ��!";
		}
	}

	/* �����Ż�ȯid��ȡ�Ż�ȯ���� */
	public Coupon GetCoupon(int couponId)  {
		List<Coupon> couponList = new ArrayList<Coupon>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("couponId", couponId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CouponServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Coupon coupon = new Coupon();
				coupon.setCouponId(object.getInt("couponId"));
				coupon.setCouponName(object.getString("couponName"));
				coupon.setCouponMoney((float) object.getDouble("couponMoney"));
				coupon.setSellerObj(object.getString("sellerObj"));
				coupon.setUserObj(object.getString("userObj"));
				coupon.setCouponTime(object.getString("couponTime"));
				couponList.add(coupon);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = couponList.size();
		if(size>0) return couponList.get(0); 
		else return null; 
	}
}
