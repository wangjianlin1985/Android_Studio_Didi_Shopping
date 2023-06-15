<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Coupon" %>
<%@ page import="com.chengxusheji.domain.Seller" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Seller信息
    List<Seller> sellerList = (List<Seller>)request.getAttribute("sellerList");
    //获取所有的UserInfo信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    Coupon coupon = (Coupon)request.getAttribute("coupon");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改优惠券</TITLE>
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
    var couponName = document.getElementById("coupon.couponName").value;
    if(couponName=="") {
        alert('请输入优惠券名称!');
        return false;
    }
    var couponTime = document.getElementById("coupon.couponTime").value;
    if(couponTime=="") {
        alert('请输入过期时间!');
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
    <TD align="left" vAlign=top ><s:form action="Coupon/Coupon_ModifyCoupon.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>优惠券id:</td>
    <td width=70%><input id="coupon.couponId" name="coupon.couponId" type="text" value="<%=coupon.getCouponId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>优惠券名称:</td>
    <td width=70%><input id="coupon.couponName" name="coupon.couponName" type="text" size="60" value='<%=coupon.getCouponName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>金额:</td>
    <td width=70%><input id="coupon.couponMoney" name="coupon.couponMoney" type="text" size="8" value='<%=coupon.getCouponMoney() %>'/></td>
  </tr>

  <tr>
    <td width=30%>发放商家:</td>
    <td width=70%>
      <select name="coupon.sellerObj.sellUserName">
      <%
        for(Seller seller:sellerList) {
          String selected = "";
          if(seller.getSellUserName().equals(coupon.getSellerObj().getSellUserName()))
            selected = "selected";
      %>
          <option value='<%=seller.getSellUserName() %>' <%=selected %>><%=seller.getSellerName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>发放用户:</td>
    <td width=70%>
      <select name="coupon.userObj.user_name">
      <%
        for(UserInfo userInfo:userInfoList) {
          String selected = "";
          if(userInfo.getUser_name().equals(coupon.getUserObj().getUser_name()))
            selected = "selected";
      %>
          <option value='<%=userInfo.getUser_name() %>' <%=selected %>><%=userInfo.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>过期时间:</td>
    <td width=70%><input id="coupon.couponTime" name="coupon.couponTime" type="text" size="20" value='<%=coupon.getCouponTime() %>'/></td>
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
