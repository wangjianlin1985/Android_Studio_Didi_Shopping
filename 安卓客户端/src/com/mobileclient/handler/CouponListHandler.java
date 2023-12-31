package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Coupon;
public class CouponListHandler extends DefaultHandler {
	private List<Coupon> couponList = null;
	private Coupon coupon;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (coupon != null) { 
            String valueString = new String(ch, start, length); 
            if ("couponId".equals(tempString)) 
            	coupon.setCouponId(new Integer(valueString).intValue());
            else if ("couponName".equals(tempString)) 
            	coupon.setCouponName(valueString); 
            else if ("couponMoney".equals(tempString)) 
            	coupon.setCouponMoney(new Float(valueString).floatValue());
            else if ("sellerObj".equals(tempString)) 
            	coupon.setSellerObj(valueString); 
            else if ("userObj".equals(tempString)) 
            	coupon.setUserObj(valueString); 
            else if ("couponTime".equals(tempString)) 
            	coupon.setCouponTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Coupon".equals(localName)&&coupon!=null){
			couponList.add(coupon);
			coupon = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		couponList = new ArrayList<Coupon>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Coupon".equals(localName)) {
            coupon = new Coupon(); 
        }
        tempString = localName; 
	}

	public List<Coupon> getCouponList() {
		return this.couponList;
	}
}
