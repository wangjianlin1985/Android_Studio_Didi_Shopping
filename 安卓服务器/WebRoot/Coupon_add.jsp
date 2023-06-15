<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.Seller" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Seller信息
    List<Seller> sellerList = (List<Seller>)request.getAttribute("sellerList");
    //获取所有的UserInfo信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加优惠券</TITLE> 
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
    <TD align="left" vAlign=top >
    <s:form action="Coupon/Coupon_AddCoupon.action" method="post" id="couponAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>优惠券名称:</td>
    <td width=70%><input id="coupon.couponName" name="coupon.couponName" type="text" size="60" /></td>
  </tr>

  <tr>
    <td width=30%>金额:</td>
    <td width=70%><input id="coupon.couponMoney" name="coupon.couponMoney" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>发放商家:</td>
    <td width=70%>
      <select name="coupon.sellerObj.sellUserName">
      <%
        for(Seller seller:sellerList) {
      %>
          <option value='<%=seller.getSellUserName() %>'><%=seller.getSellerName() %></option>
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
      %>
          <option value='<%=userInfo.getUser_name() %>'><%=userInfo.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>过期时间:</td>
    <td width=70%><input id="coupon.couponTime" name="coupon.couponTime" type="text" size="20" /></td>
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
