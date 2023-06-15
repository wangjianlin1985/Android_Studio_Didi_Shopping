package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.OrderInfo;
public class OrderInfoListHandler extends DefaultHandler {
	private List<OrderInfo> orderInfoList = null;
	private OrderInfo orderInfo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (orderInfo != null) { 
            String valueString = new String(ch, start, length); 
            if ("orderId".equals(tempString)) 
            	orderInfo.setOrderId(new Integer(valueString).intValue());
            else if ("productObj".equals(tempString)) 
            	orderInfo.setProductObj(valueString); 
            else if ("arrivePlace".equals(tempString)) 
            	orderInfo.setArrivePlace(valueString); 
            else if ("latitude".equals(tempString)) 
            	orderInfo.setLatitude(new Float(valueString).floatValue());
            else if ("longitude".equals(tempString)) 
            	orderInfo.setLongitude(new Float(valueString).floatValue());
            else if ("stateObj".equals(tempString)) 
            	orderInfo.setStateObj(new Integer(valueString).intValue());
            else if ("telephone".equals(tempString)) 
            	orderInfo.setTelephone(valueString); 
            else if ("orderUser".equals(tempString)) 
            	orderInfo.setOrderUser(valueString); 
            else if ("addTime".equals(tempString)) 
            	orderInfo.setAddTime(valueString); 
            else if ("receiveSeller".equals(tempString)) 
            	orderInfo.setReceiveSeller(valueString); 
            else if ("receiveTime".equals(tempString)) 
            	orderInfo.setReceiveTime(valueString); 
            else if ("memo".equals(tempString)) 
            	orderInfo.setMemo(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("OrderInfo".equals(localName)&&orderInfo!=null){
			orderInfoList.add(orderInfo);
			orderInfo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		orderInfoList = new ArrayList<OrderInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("OrderInfo".equals(localName)) {
            orderInfo = new OrderInfo(); 
        }
        tempString = localName; 
	}

	public List<OrderInfo> getOrderInfoList() {
		return this.orderInfoList;
	}
}
