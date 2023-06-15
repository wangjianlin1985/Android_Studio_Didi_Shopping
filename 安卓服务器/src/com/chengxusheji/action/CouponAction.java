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

    /*界面层需要查询的属性: 优惠券名称*/
    private String couponName;
    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }
    public String getCouponName() {
        return this.couponName;
    }

    /*界面层需要查询的属性: 发放商家*/
    private Seller sellerObj;
    public void setSellerObj(Seller sellerObj) {
        this.sellerObj = sellerObj;
    }
    public Seller getSellerObj() {
        return this.sellerObj;
    }

    /*界面层需要查询的属性: 发放用户*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*界面层需要查询的属性: 过期时间*/
    private String couponTime;
    public void setCouponTime(String couponTime) {
        this.couponTime = couponTime;
    }
    public String getCouponTime() {
        return this.couponTime;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
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

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource SellerDAO sellerDAO;
    @Resource UserInfoDAO userInfoDAO;
    @Resource CouponDAO couponDAO;

    /*待操作的Coupon对象*/
    private Coupon coupon;
    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }
    public Coupon getCoupon() {
        return this.coupon;
    }

    /*跳转到添加Coupon视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Seller信息*/
        List<Seller> sellerList = sellerDAO.QueryAllSellerInfo();
        ctx.put("sellerList", sellerList);
        /*查询所有的UserInfo信息*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*添加Coupon信息*/
    @SuppressWarnings("deprecation")
    public String AddCoupon() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Seller sellerObj = sellerDAO.GetSellerBySellUserName(coupon.getSellerObj().getSellUserName());
            coupon.setSellerObj(sellerObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(coupon.getUserObj().getUser_name());
            coupon.setUserObj(userObj);
            couponDAO.AddCoupon(coupon);
            ctx.put("message",  java.net.URLEncoder.encode("Coupon添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Coupon添加失败!"));
            return "error";
        }
    }

    /*查询Coupon信息*/
    public String QueryCoupon() {
        if(currentPage == 0) currentPage = 1;
        if(couponName == null) couponName = "";
        if(couponTime == null) couponTime = "";
        List<Coupon> couponList = couponDAO.QueryCouponInfo(couponName, sellerObj, userObj, couponTime, currentPage);
        /*计算总的页数和总的记录数*/
        couponDAO.CalculateTotalPageAndRecordNumber(couponName, sellerObj, userObj, couponTime);
        /*获取到总的页码数目*/
        totalPage = couponDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryCouponOutputToExcel() { 
        if(couponName == null) couponName = "";
        if(couponTime == null) couponTime = "";
        List<Coupon> couponList = couponDAO.QueryCouponInfo(couponName,sellerObj,userObj,couponTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Coupon信息记录"; 
        String[] headers = { "优惠券id","优惠券名称","金额","发放商家","发放用户","过期时间"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Coupon.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
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
    /*前台查询Coupon信息*/
    public String FrontQueryCoupon() {
        if(currentPage == 0) currentPage = 1;
        if(couponName == null) couponName = "";
        if(couponTime == null) couponTime = "";
        List<Coupon> couponList = couponDAO.QueryCouponInfo(couponName, sellerObj, userObj, couponTime, currentPage);
        /*计算总的页数和总的记录数*/
        couponDAO.CalculateTotalPageAndRecordNumber(couponName, sellerObj, userObj, couponTime);
        /*获取到总的页码数目*/
        totalPage = couponDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的Coupon信息*/
    public String ModifyCouponQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键couponId获取Coupon对象*/
        Coupon coupon = couponDAO.GetCouponByCouponId(couponId);

        List<Seller> sellerList = sellerDAO.QueryAllSellerInfo();
        ctx.put("sellerList", sellerList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("coupon",  coupon);
        return "modify_view";
    }

    /*查询要修改的Coupon信息*/
    public String FrontShowCouponQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键couponId获取Coupon对象*/
        Coupon coupon = couponDAO.GetCouponByCouponId(couponId);

        List<Seller> sellerList = sellerDAO.QueryAllSellerInfo();
        ctx.put("sellerList", sellerList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("coupon",  coupon);
        return "front_show_view";
    }

    /*更新修改Coupon信息*/
    public String ModifyCoupon() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Seller sellerObj = sellerDAO.GetSellerBySellUserName(coupon.getSellerObj().getSellUserName());
            coupon.setSellerObj(sellerObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(coupon.getUserObj().getUser_name());
            coupon.setUserObj(userObj);
            couponDAO.UpdateCoupon(coupon);
            ctx.put("message",  java.net.URLEncoder.encode("Coupon信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Coupon信息更新失败!"));
            return "error";
       }
   }

    /*删除Coupon信息*/
    public String DeleteCoupon() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            couponDAO.DeleteCoupon(couponId);
            ctx.put("message",  java.net.URLEncoder.encode("Coupon删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Coupon删除失败!"));
            return "error";
        }
    }

}
