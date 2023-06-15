<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.OrderInfo" %>
<%@ page import="com.chengxusheji.domain.Product" %>
<%@ page import="com.chengxusheji.domain.OrderState" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Product信息
    List<Product> productList = (List<Product>)request.getAttribute("productList");
    //获取所有的OrderState信息
    List<OrderState> orderStateList = (List<OrderState>)request.getAttribute("orderStateList");
    //获取所有的UserInfo信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    OrderInfo orderInfo = (OrderInfo)request.getAttribute("orderInfo");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改订单</TITLE>
<STYLE type=text/css>
BODY {
	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*验证表单*/
function checkForm() {
    var arrivePlace = document.getElementById("orderInfo.arrivePlace").value;
    if(arrivePlace=="") {
        alert('请输入配送地点!');
        return false;
    }
    var telephone = document.getElementById("orderInfo.telephone").value;
    if(telephone=="") {
        alert('请输入联系电话!');
        return false;
    }
    var receiveSeller = document.getElementById("orderInfo.receiveSeller").value;
    if(receiveSeller=="") {
        alert('请输入接单商家!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>
<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="OrderInfo/OrderInfo_ModifyOrderInfo.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>订单编号:</td>
    <td width=70%><input id="orderInfo.orderId" name="orderInfo.orderId" type="text" value="<%=orderInfo.getOrderId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>商品信息:</td>
    <td width=70%>
      <select name="orderInfo.productObj.productNo">
      <%
        for(Product product:productList) {
          String selected = "";
          if(product.getProductNo().equals(orderInfo.getProductObj().getProductNo()))
            selected = "selected";
      %>
          <option value='<%=product.getProductNo() %>' <%=selected %>><%=product.getProductName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>配送地点:</td>
    <td width=70%><input id="orderInfo.arrivePlace" name="orderInfo.arrivePlace" type="text" size="60" value='<%=orderInfo.getArrivePlace() %>'/></td>
  </tr>

  <tr>
    <td width=30%>纬度:</td>
    <td width=70%><input id="orderInfo.latitude" name="orderInfo.latitude" type="text" size="8" value='<%=orderInfo.getLatitude() %>'/></td>
  </tr>

  <tr>
    <td width=30%>经度:</td>
    <td width=70%><input id="orderInfo.longitude" name="orderInfo.longitude" type="text" size="8" value='<%=orderInfo.getLongitude() %>'/></td>
  </tr>

  <tr>
    <td width=30%>订单状态:</td>
    <td width=70%>
      <select name="orderInfo.stateObj.stateId">
      <%
        for(OrderState orderState:orderStateList) {
          String selected = "";
          if(orderState.getStateId() == orderInfo.getStateObj().getStateId())
            selected = "selected";
      %>
          <option value='<%=orderState.getStateId() %>' <%=selected %>><%=orderState.getStateName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>联系电话:</td>
    <td width=70%><input id="orderInfo.telephone" name="orderInfo.telephone" type="text" size="20" value='<%=orderInfo.getTelephone() %>'/></td>
  </tr>

  <tr>
    <td width=30%>下单用户:</td>
    <td width=70%>
      <select name="orderInfo.orderUser.user_name">
      <%
        for(UserInfo userInfo:userInfoList) {
          String selected = "";
          if(userInfo.getUser_name().equals(orderInfo.getOrderUser().getUser_name()))
            selected = "selected";
      %>
          <option value='<%=userInfo.getUser_name() %>' <%=selected %>><%=userInfo.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>下单时间:</td>
    <td width=70%><input id="orderInfo.addTime" name="orderInfo.addTime" type="text" size="20" value='<%=orderInfo.getAddTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>接单商家:</td>
    <td width=70%><input id="orderInfo.receiveSeller" name="orderInfo.receiveSeller" type="text" size="60" value='<%=orderInfo.getReceiveSeller() %>'/></td>
  </tr>

  <tr>
    <td width=30%>接单时间:</td>
    <td width=70%><input id="orderInfo.receiveTime" name="orderInfo.receiveTime" type="text" size="20" value='<%=orderInfo.getReceiveTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>附加信息:</td>
    <td width=70%><textarea id="orderInfo.memo" name="orderInfo.memo" rows=5 cols=50><%=orderInfo.getMemo() %></textarea></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
