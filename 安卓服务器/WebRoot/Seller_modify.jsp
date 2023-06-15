<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Seller" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    Seller seller = (Seller)request.getAttribute("seller");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改商家</TITLE>
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
    var sellUserName = document.getElementById("seller.sellUserName").value;
    if(sellUserName=="") {
        alert('请输入商家账号!');
        return false;
    }
    var password = document.getElementById("seller.password").value;
    if(password=="") {
        alert('请输入登录密码!');
        return false;
    }
    var sellerName = document.getElementById("seller.sellerName").value;
    if(sellerName=="") {
        alert('请输入商家名称!');
        return false;
    }
    var telephone = document.getElementById("seller.telephone").value;
    if(telephone=="") {
        alert('请输入联系电话!');
        return false;
    }
    var address = document.getElementById("seller.address").value;
    if(address=="") {
        alert('请输入商家地址!');
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
    <TD align="left" vAlign=top ><s:form action="Seller/Seller_ModifySeller.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>商家账号:</td>
    <td width=70%><input id="seller.sellUserName" name="seller.sellUserName" type="text" value="<%=seller.getSellUserName() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>登录密码:</td>
    <td width=70%><input id="seller.password" name="seller.password" type="text" size="20" value='<%=seller.getPassword() %>'/></td>
  </tr>

  <tr>
    <td width=30%>商家名称:</td>
    <td width=70%><input id="seller.sellerName" name="seller.sellerName" type="text" size="20" value='<%=seller.getSellerName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>联系电话:</td>
    <td width=70%><input id="seller.telephone" name="seller.telephone" type="text" size="20" value='<%=seller.getTelephone() %>'/></td>
  </tr>

  <tr>
    <td width=30%>入驻日期:</td>
    <% DateFormat addDateSDF = new SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><input type="text" readonly  id="seller.addDate"  name="seller.addDate" onclick="setDay(this);" value='<%=addDateSDF.format(seller.getAddDate()) %>'/></td>
  </tr>

  <tr>
    <td width=30%>商家地址:</td>
    <td width=70%><input id="seller.address" name="seller.address" type="text" size="80" value='<%=seller.getAddress() %>'/></td>
  </tr>

  <tr>
    <td width=30%>纬度:</td>
    <td width=70%><input id="seller.latitude" name="seller.latitude" type="text" size="8" value='<%=seller.getLatitude() %>'/></td>
  </tr>

  <tr>
    <td width=30%>经度:</td>
    <td width=70%><input id="seller.longitude" name="seller.longitude" type="text" size="8" value='<%=seller.getLongitude() %>'/></td>
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
