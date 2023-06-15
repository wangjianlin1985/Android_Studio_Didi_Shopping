<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Product" %>
<%@ page import="com.chengxusheji.domain.ProductClass" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的ProductClass信息
    List<ProductClass> productClassList = (List<ProductClass>)request.getAttribute("productClassList");
    Product product = (Product)request.getAttribute("product");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改商品</TITLE>
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
    var productNo = document.getElementById("product.productNo").value;
    if(productNo=="") {
        alert('请输入商品编号!');
        return false;
    }
    var productName = document.getElementById("product.productName").value;
    if(productName=="") {
        alert('请输入商品名称!');
        return false;
    }
    var productDesc = document.getElementById("product.productDesc").value;
    if(productDesc=="") {
        alert('请输入物品描述!');
        return false;
    }
    var stockDesc = document.getElementById("product.stockDesc").value;
    if(stockDesc=="") {
        alert('请输入物品存货!');
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
    <TD align="left" vAlign=top ><s:form action="Product/Product_ModifyProduct.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>商品编号:</td>
    <td width=70%><input id="product.productNo" name="product.productNo" type="text" value="<%=product.getProductNo() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>商品类目:</td>
    <td width=70%>
      <select name="product.productClassObj.classId">
      <%
        for(ProductClass productClass:productClassList) {
          String selected = "";
          if(productClass.getClassId() == product.getProductClassObj().getClassId())
            selected = "selected";
      %>
          <option value='<%=productClass.getClassId() %>' <%=selected %>><%=productClass.getClassName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>商品名称:</td>
    <td width=70%><input id="product.productName" name="product.productName" type="text" size="20" value='<%=product.getProductName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>物品描述:</td>
    <td width=70%><textarea id="product.productDesc" name="product.productDesc" rows=5 cols=50><%=product.getProductDesc() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>物品价格:</td>
    <td width=70%><input id="product.price" name="product.price" type="text" size="8" value='<%=product.getPrice() %>'/></td>
  </tr>

  <tr>
    <td width=30%>物品图片:</td>
    <td width=70%><img src="<%=basePath %><%=product.getProductPhoto() %>" width="200px" border="0px"/><br/>
    <input type=hidden name="product.productPhoto" value="<%=product.getProductPhoto() %>" />
    <input id="productPhotoFile" name="productPhotoFile" type="file" size="50" /></td>
  </tr>
  <tr>
    <td width=30%>物品存货:</td>
    <td width=70%><input id="product.stockDesc" name="product.stockDesc" type="text" size="50" value='<%=product.getStockDesc() %>'/></td>
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
