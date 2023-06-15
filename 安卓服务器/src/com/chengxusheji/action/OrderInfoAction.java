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
import com.chengxusheji.dao.OrderInfoDAO;
import com.chengxusheji.domain.OrderInfo;
import com.chengxusheji.dao.ProductDAO;
import com.chengxusheji.domain.Product;
import com.chengxusheji.dao.OrderStateDAO;
import com.chengxusheji.domain.OrderState;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class OrderInfoAction extends BaseAction {

    /*�������Ҫ��ѯ������: ��Ʒ��Ϣ*/
    private Product productObj;
    public void setProductObj(Product productObj) {
        this.productObj = productObj;
    }
    public Product getProductObj() {
        return this.productObj;
    }

    /*�������Ҫ��ѯ������: ����״̬*/
    private OrderState stateObj;
    public void setStateObj(OrderState stateObj) {
        this.stateObj = stateObj;
    }
    public OrderState getStateObj() {
        return this.stateObj;
    }

    /*�������Ҫ��ѯ������: ��ϵ�绰*/
    private String telephone;
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return this.telephone;
    }

    /*�������Ҫ��ѯ������: �µ��û�*/
    private UserInfo orderUser;
    public void setOrderUser(UserInfo orderUser) {
        this.orderUser = orderUser;
    }
    public UserInfo getOrderUser() {
        return this.orderUser;
    }

    /*�������Ҫ��ѯ������: �µ�ʱ��*/
    private String addTime;
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
    public String getAddTime() {
        return this.addTime;
    }

    /*�������Ҫ��ѯ������: �ӵ��̼�*/
    private String receiveSeller;
    public void setReceiveSeller(String receiveSeller) {
        this.receiveSeller = receiveSeller;
    }
    public String getReceiveSeller() {
        return this.receiveSeller;
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

    private int orderId;
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getOrderId() {
        return orderId;
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
    @Resource ProductDAO productDAO;
    @Resource OrderStateDAO orderStateDAO;
    @Resource UserInfoDAO userInfoDAO;
    @Resource OrderInfoDAO orderInfoDAO;

    /*��������OrderInfo����*/
    private OrderInfo orderInfo;
    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }
    public OrderInfo getOrderInfo() {
        return this.orderInfo;
    }

    /*��ת�����OrderInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Product��Ϣ*/
        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        /*��ѯ���е�OrderState��Ϣ*/
        List<OrderState> orderStateList = orderStateDAO.QueryAllOrderStateInfo();
        ctx.put("orderStateList", orderStateList);
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*���OrderInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddOrderInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Product productObj = productDAO.GetProductByProductNo(orderInfo.getProductObj().getProductNo());
            orderInfo.setProductObj(productObj);
            OrderState stateObj = orderStateDAO.GetOrderStateByStateId(orderInfo.getStateObj().getStateId());
            orderInfo.setStateObj(stateObj);
            UserInfo orderUser = userInfoDAO.GetUserInfoByUser_name(orderInfo.getOrderUser().getUser_name());
            orderInfo.setOrderUser(orderUser);
            orderInfoDAO.AddOrderInfo(orderInfo);
            ctx.put("message",  java.net.URLEncoder.encode("OrderInfo��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯOrderInfo��Ϣ*/
    public String QueryOrderInfo() {
        if(currentPage == 0) currentPage = 1;
        if(telephone == null) telephone = "";
        if(addTime == null) addTime = "";
        if(receiveSeller == null) receiveSeller = "";
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfoInfo(productObj, stateObj, telephone, orderUser, addTime, receiveSeller, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        orderInfoDAO.CalculateTotalPageAndRecordNumber(productObj, stateObj, telephone, orderUser, addTime, receiveSeller);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = orderInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = orderInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderInfoList",  orderInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("productObj", productObj);
        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        ctx.put("stateObj", stateObj);
        List<OrderState> orderStateList = orderStateDAO.QueryAllOrderStateInfo();
        ctx.put("orderStateList", orderStateList);
        ctx.put("telephone", telephone);
        ctx.put("orderUser", orderUser);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("addTime", addTime);
        ctx.put("receiveSeller", receiveSeller);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryOrderInfoOutputToExcel() { 
        if(telephone == null) telephone = "";
        if(addTime == null) addTime = "";
        if(receiveSeller == null) receiveSeller = "";
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfoInfo(productObj,stateObj,telephone,orderUser,addTime,receiveSeller);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "OrderInfo��Ϣ��¼"; 
        String[] headers = { "�������","��Ʒ��Ϣ","���͵ص�","����״̬","��ϵ�绰","�µ��û�","�µ�ʱ��","�ӵ��̼�"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<orderInfoList.size();i++) {
        	OrderInfo orderInfo = orderInfoList.get(i); 
        	dataset.add(new String[]{orderInfo.getOrderId() + "",orderInfo.getProductObj().getProductName(),
orderInfo.getArrivePlace(),orderInfo.getStateObj().getStateName(),
orderInfo.getTelephone(),orderInfo.getOrderUser().getName(),
orderInfo.getAddTime(),orderInfo.getReceiveSeller()});
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
			response.setHeader("Content-disposition","attachment; filename="+"OrderInfo.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯOrderInfo��Ϣ*/
    public String FrontQueryOrderInfo() {
        if(currentPage == 0) currentPage = 1;
        if(telephone == null) telephone = "";
        if(addTime == null) addTime = "";
        if(receiveSeller == null) receiveSeller = "";
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfoInfo(productObj, stateObj, telephone, orderUser, addTime, receiveSeller, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        orderInfoDAO.CalculateTotalPageAndRecordNumber(productObj, stateObj, telephone, orderUser, addTime, receiveSeller);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = orderInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = orderInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderInfoList",  orderInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("productObj", productObj);
        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        ctx.put("stateObj", stateObj);
        List<OrderState> orderStateList = orderStateDAO.QueryAllOrderStateInfo();
        ctx.put("orderStateList", orderStateList);
        ctx.put("telephone", telephone);
        ctx.put("orderUser", orderUser);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("addTime", addTime);
        ctx.put("receiveSeller", receiveSeller);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�OrderInfo��Ϣ*/
    public String ModifyOrderInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������orderId��ȡOrderInfo����*/
        OrderInfo orderInfo = orderInfoDAO.GetOrderInfoByOrderId(orderId);

        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        List<OrderState> orderStateList = orderStateDAO.QueryAllOrderStateInfo();
        ctx.put("orderStateList", orderStateList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("orderInfo",  orderInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�OrderInfo��Ϣ*/
    public String FrontShowOrderInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������orderId��ȡOrderInfo����*/
        OrderInfo orderInfo = orderInfoDAO.GetOrderInfoByOrderId(orderId);

        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        List<OrderState> orderStateList = orderStateDAO.QueryAllOrderStateInfo();
        ctx.put("orderStateList", orderStateList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("orderInfo",  orderInfo);
        return "front_show_view";
    }

    /*�����޸�OrderInfo��Ϣ*/
    public String ModifyOrderInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Product productObj = productDAO.GetProductByProductNo(orderInfo.getProductObj().getProductNo());
            orderInfo.setProductObj(productObj);
            OrderState stateObj = orderStateDAO.GetOrderStateByStateId(orderInfo.getStateObj().getStateId());
            orderInfo.setStateObj(stateObj);
            UserInfo orderUser = userInfoDAO.GetUserInfoByUser_name(orderInfo.getOrderUser().getUser_name());
            orderInfo.setOrderUser(orderUser);
            orderInfoDAO.UpdateOrderInfo(orderInfo);
            ctx.put("message",  java.net.URLEncoder.encode("OrderInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��OrderInfo��Ϣ*/
    public String DeleteOrderInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            orderInfoDAO.DeleteOrderInfo(orderId);
            ctx.put("message",  java.net.URLEncoder.encode("OrderInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderInfoɾ��ʧ��!"));
            return "error";
        }
    }

}
