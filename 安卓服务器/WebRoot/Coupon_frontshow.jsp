<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Coupon" %>
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
    Coupon coupon = (Coupon)request.getAttribute("coupon");

%>
<HTML><HEAD><TITLE>查看优惠券</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>优惠券id:</td>
    <td width=70%><%=coupon.getCouponId() %></td>
  </tr>

  <tr>
    <td width=30%>优惠券名称:</td>
    <td width=70%><%=coupon.getCouponName() %></td>
  </tr>

  <tr>
    <td width=30%>金额:</td>
    <td width=70%><%=coupon.getCouponMoney() %></td>
  </tr>

  <tr>
    <td width=30%>发放商家:</td>
    <td width=70%>
      <%=coupon.getSellerObj().getSellerName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>发放用户:</td>
    <td width=70%>
      <%=coupon.getUserObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>过期时间:</td>
    <td width=70%><%=coupon.getCouponTime() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
