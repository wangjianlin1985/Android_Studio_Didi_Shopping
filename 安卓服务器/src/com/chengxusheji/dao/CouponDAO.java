package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.Seller;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.domain.Coupon;

@Service @Transactional
public class CouponDAO {

	@Resource SessionFactory factory;
    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
    public void AddCoupon(Coupon coupon) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(coupon);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Coupon> QueryCouponInfo(String couponName,Seller sellerObj,UserInfo userObj,String couponTime,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Coupon coupon where 1=1";
    	if(!couponName.equals("")) hql = hql + " and coupon.couponName like '%" + couponName + "%'";
    	if(null != sellerObj && !sellerObj.getSellUserName().equals("")) hql += " and coupon.sellerObj.sellUserName='" + sellerObj.getSellUserName() + "'";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and coupon.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!couponTime.equals("")) hql = hql + " and coupon.couponTime like '%" + couponTime + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List couponList = q.list();
    	return (ArrayList<Coupon>) couponList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Coupon> QueryCouponInfo(String couponName,Seller sellerObj,UserInfo userObj,String couponTime) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Coupon coupon where 1=1";
    	if(!couponName.equals("")) hql = hql + " and coupon.couponName like '%" + couponName + "%'";
    	if(null != sellerObj && !sellerObj.getSellUserName().equals("")) hql += " and coupon.sellerObj.sellUserName='" + sellerObj.getSellUserName() + "'";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and coupon.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!couponTime.equals("")) hql = hql + " and coupon.couponTime like '%" + couponTime + "%'";
    	Query q = s.createQuery(hql);
    	List couponList = q.list();
    	return (ArrayList<Coupon>) couponList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Coupon> QueryAllCouponInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Coupon";
        Query q = s.createQuery(hql);
        List couponList = q.list();
        return (ArrayList<Coupon>) couponList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String couponName,Seller sellerObj,UserInfo userObj,String couponTime) {
        Session s = factory.getCurrentSession();
        String hql = "From Coupon coupon where 1=1";
        if(!couponName.equals("")) hql = hql + " and coupon.couponName like '%" + couponName + "%'";
        if(null != sellerObj && !sellerObj.getSellUserName().equals("")) hql += " and coupon.sellerObj.sellUserName='" + sellerObj.getSellUserName() + "'";
        if(null != userObj && !userObj.getUser_name().equals("")) hql += " and coupon.userObj.user_name='" + userObj.getUser_name() + "'";
        if(!couponTime.equals("")) hql = hql + " and coupon.couponTime like '%" + couponTime + "%'";
        Query q = s.createQuery(hql);
        List couponList = q.list();
        recordNumber = couponList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Coupon GetCouponByCouponId(int couponId) {
        Session s = factory.getCurrentSession();
        Coupon coupon = (Coupon)s.get(Coupon.class, couponId);
        return coupon;
    }

    /*����Coupon��Ϣ*/
    public void UpdateCoupon(Coupon coupon) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(coupon);
    }

    /*ɾ��Coupon��Ϣ*/
    public void DeleteCoupon (int couponId) throws Exception {
        Session s = factory.getCurrentSession();
        Object coupon = s.load(Coupon.class, couponId);
        s.delete(coupon);
    }

}
