<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Seller" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    Seller seller = (Seller)request.getAttribute("seller");

%>
<HTML><HEAD><TITLE>查看商家</TITLE>
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
    <td width=30%>商家账号:</td>
    <td width=70%><%=seller.getSellUserName() %></td>
  </tr>

  <tr>
    <td width=30%>登录密码:</td>
    <td width=70%><%=seller.getPassword() %></td>
  </tr>

  <tr>
    <td width=30%>商家名称:</td>
    <td width=70%><%=seller.getSellerName() %></td>
  </tr>

  <tr>
    <td width=30%>联系电话:</td>
    <td width=70%><%=seller.getTelephone() %></td>
  </tr>

  <tr>
    <td width=30%>入驻日期:</td>
        <% java.text.DateFormat addDateSDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=addDateSDF.format(seller.getAddDate()) %></td>
  </tr>

  <tr>
    <td width=30%>商家地址:</td>
    <td width=70%><%=seller.getAddress() %></td>
  </tr>

  <tr>
    <td width=30%>纬度:</td>
    <td width=70%><%=seller.getLatitude() %></td>
  </tr>

  <tr>
    <td width=30%>经度:</td>
    <td width=70%><%=seller.getLongitude() %></td>
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
