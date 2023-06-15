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
    //��ȡ���е�Product��Ϣ
    List<Product> productList = (List<Product>)request.getAttribute("productList");
    //��ȡ���е�OrderState��Ϣ
    List<OrderState> orderStateList = (List<OrderState>)request.getAttribute("orderStateList");
    //��ȡ���е�UserInfo��Ϣ
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    OrderInfo orderInfo = (OrderInfo)request.getAttribute("orderInfo");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸Ķ���</TITLE>
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
/*��֤��*/
function checkForm() {
    var arrivePlace = document.getElementById("orderInfo.arrivePlace").value;
    if(arrivePlace=="") {
        alert('���������͵ص�!');
        return false;
    }
    var telephone = document.getElementById("orderInfo.telephone").value;
    if(telephone=="") {
        alert('��������ϵ�绰!');
        return false;
    }
    var receiveSeller = document.getElementById("orderInfo.receiveSeller").value;
    if(receiveSeller=="") {
        alert('������ӵ��̼�!');
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
    <td width=30%>�������:</td>
    <td width=70%><input id="orderInfo.orderId" name="orderInfo.orderId" type="text" value="<%=orderInfo.getOrderId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>��Ʒ��Ϣ:</td>
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
    <td width=30%>���͵ص�:</td>
    <td width=70%><input id="orderInfo.arrivePlace" name="orderInfo.arrivePlace" type="text" size="60" value='<%=orderInfo.getArrivePlace() %>'/></td>
  </tr>

  <tr>
    <td width=30%>γ��:</td>
    <td width=70%><input id="orderInfo.latitude" name="orderInfo.latitude" type="text" size="8" value='<%=orderInfo.getLatitude() %>'/></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="orderInfo.longitude" name="orderInfo.longitude" type="text" size="8" value='<%=orderInfo.getLongitude() %>'/></td>
  </tr>

  <tr>
    <td width=30%>����״̬:</td>
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
    <td width=30%>��ϵ�绰:</td>
    <td width=70%><input id="orderInfo.telephone" name="orderInfo.telephone" type="text" size="20" value='<%=orderInfo.getTelephone() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�µ��û�:</td>
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
    <td width=30%>�µ�ʱ��:</td>
    <td width=70%><input id="orderInfo.addTime" name="orderInfo.addTime" type="text" size="20" value='<%=orderInfo.getAddTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�ӵ��̼�:</td>
    <td width=70%><input id="orderInfo.receiveSeller" name="orderInfo.receiveSeller" type="text" size="60" value='<%=orderInfo.getReceiveSeller() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�ӵ�ʱ��:</td>
    <td width=70%><input id="orderInfo.receiveTime" name="orderInfo.receiveTime" type="text" size="20" value='<%=orderInfo.getReceiveTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>������Ϣ:</td>
    <td width=70%><textarea id="orderInfo.memo" name="orderInfo.memo" rows=5 cols=50><%=orderInfo.getMemo() %></textarea></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
