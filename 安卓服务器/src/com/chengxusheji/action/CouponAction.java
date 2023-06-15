package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.CouponDAO;
import com.chengxusheji.domain.Coupon;
import com.chengxusheji.dao.SellerDAO;
import com.chengxusheji.domain.Seller;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class CouponAction extends BaseAction {

    /*�������Ҫ��ѯ������: �Ż�ȯ����*/
    private String couponName;
    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }
    public String getCouponName() {
        return this.couponName;
    }

    /*�������Ҫ��ѯ������: �����̼�*/
    private Seller sellerObj;
    public void setSellerObj(Seller sellerObj) {
        this.sellerObj = sellerObj;
    }
    public Seller getSellerObj() {
        return this.sellerObj;
    }

    /*�������Ҫ��ѯ������: �����û�*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*�������Ҫ��ѯ������: ����ʱ��*/
    private String couponTime;
    public void setCouponTime(String couponTime) {
        this.couponTime = couponTime;
    }
    public String getCouponTime() {
        return this.couponTime;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int couponId;
    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }
    public int getCouponId() {
        return couponId;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource SellerDAO sellerDAO;
    @Resource UserInfoDAO userInfoDAO;
    @Resource CouponDAO couponDAO;

    /*��������Coupon����*/
    private Coupon coupon;
    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }
    public Coupon getCoupon() {
        return this.coupon;
    }

    /*��ת�����Coupon��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Seller��Ϣ*/
        List<Seller> sellerList = sellerDAO.QueryAllSellerInfo();
        ctx.put("sellerList", sellerList);
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*���Coupon��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddCoupon() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Seller sellerObj = sellerDAO.GetSellerBySellUserName(coupon.getSellerObj().getSellUserName());
            coupon.setSellerObj(sellerObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(coupon.getUserObj().getUser_name());
            coupon.setUserObj(userObj);
            couponDAO.AddCoupon(coupon);
            ctx.put("message",  java.net.URLEncoder.encode("Coupon��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Coupon���ʧ��!"));
            return "error";
        }
    }

    /*��ѯCoupon��Ϣ*/
    public String QueryCoupon() {
        if(currentPage == 0) currentPage = 1;
        if(couponName == null) couponName = "";
        if(couponTime == null) couponTime = "";
        List<Coupon> couponList = couponDAO.QueryCouponInfo(couponName, sellerObj, userObj, couponTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        couponDAO.CalculateTotalPageAndRecordNumber(couponName, sellerObj, userObj, couponTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = couponDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = couponDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("couponList",  couponList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("couponName", couponName);
        ctx.put("sellerObj", sellerObj);
        List<Seller> sellerList = sellerDAO.QueryAllSellerInfo();
        ctx.put("sellerList", sellerList);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("couponTime", couponTime);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryCouponOutputToExcel() { 
        if(couponName == null) couponName = "";
        if(couponTime == null) couponTime = "";
        List<Coupon> couponList = couponDAO.QueryCouponInfo(couponName,sellerObj,userObj,couponTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Coupon��Ϣ��¼"; 
        String[] headers = { "�Ż�ȯid","�Ż�ȯ����","���","�����̼�","�����û�","����ʱ��"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<couponList.size();i++) {
        	Coupon coupon = couponList.get(i); 
        	dataset.add(new String[]{coupon.getCouponId() + "",coupon.getCouponName(),coupon.getCouponMoney() + "",coupon.getSellerObj().getSellerName(),
coupon.getUserObj().getName(),
coupon.getCouponTime()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Coupon.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*ǰ̨��ѯCoupon��Ϣ*/
    public String FrontQueryCoupon() {
        if(currentPage == 0) currentPage = 1;
        if(couponName == null) couponName = "";
        if(couponTime == null) couponTime = "";
        List<Coupon> couponList = couponDAO.QueryCouponInfo(couponName, sellerObj, userObj, couponTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        couponDAO.CalculateTotalPageAndRecordNumber(couponName, sellerObj, userObj, couponTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = couponDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = couponDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("couponList",  couponList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("couponName", couponName);
        ctx.put("sellerObj", sellerObj);
        List<Seller> sellerList = sellerDAO.QueryAllSellerInfo();
        ctx.put("sellerList", sellerList);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("couponTime", couponTime);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Coupon��Ϣ*/
    public String ModifyCouponQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������couponId��ȡCoupon����*/
        Coupon coupon = couponDAO.GetCouponByCouponId(couponId);

        List<Seller> sellerList = sellerDAO.QueryAllSellerInfo();
        ctx.put("sellerList", sellerList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("coupon",  coupon);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Coupon��Ϣ*/
    public String FrontShowCouponQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������couponId��ȡCoupon����*/
        Coupon coupon = couponDAO.GetCouponByCouponId(couponId);

        List<Seller> sellerList = sellerDAO.QueryAllSellerInfo();
        ctx.put("sellerList", sellerList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("coupon",  coupon);
        return "front_show_view";
    }

    /*�����޸�Coupon��Ϣ*/
    public String ModifyCoupon() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Seller sellerObj = sellerDAO.GetSellerBySellUserName(coupon.getSellerObj().getSellUserName());
            coupon.setSellerObj(sellerObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(coupon.getUserObj().getUser_name());
            coupon.setUserObj(userObj);
            couponDAO.UpdateCoupon(coupon);
            ctx.put("message",  java.net.URLEncoder.encode("Coupon��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Coupon��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Coupon��Ϣ*/
    public String DeleteCoupon() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            couponDAO.DeleteCoupon(couponId);
            ctx.put("message",  java.net.URLEncoder.encode("Couponɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Couponɾ��ʧ��!"));
            return "error";
        }
    }

}
