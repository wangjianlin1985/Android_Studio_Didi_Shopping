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
import com.chengxusheji.dao.ProductDAO;
import com.chengxusheji.domain.Product;
import com.chengxusheji.dao.ProductClassDAO;
import com.chengxusheji.domain.ProductClass;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class ProductAction extends BaseAction {

	/*ͼƬ���ļ��ֶ�productPhoto��������*/
	private File productPhotoFile;
	private String productPhotoFileFileName;
	private String productPhotoFileContentType;
	public File getProductPhotoFile() {
		return productPhotoFile;
	}
	public void setProductPhotoFile(File productPhotoFile) {
		this.productPhotoFile = productPhotoFile;
	}
	public String getProductPhotoFileFileName() {
		return productPhotoFileFileName;
	}
	public void setProductPhotoFileFileName(String productPhotoFileFileName) {
		this.productPhotoFileFileName = productPhotoFileFileName;
	}
	public String getProductPhotoFileContentType() {
		return productPhotoFileContentType;
	}
	public void setProductPhotoFileContentType(String productPhotoFileContentType) {
		this.productPhotoFileContentType = productPhotoFileContentType;
	}
    /*�������Ҫ��ѯ������: ��Ʒ���*/
    private String productNo;
    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }
    public String getProductNo() {
        return this.productNo;
    }

    /*�������Ҫ��ѯ������: ��Ʒ��Ŀ*/
    private ProductClass productClassObj;
    public void setProductClassObj(ProductClass productClassObj) {
        this.productClassObj = productClassObj;
    }
    public ProductClass getProductClassObj() {
        return this.productClassObj;
    }

    /*�������Ҫ��ѯ������: ��Ʒ����*/
    private String productName;
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductName() {
        return this.productName;
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

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource ProductClassDAO productClassDAO;
    @Resource ProductDAO productDAO;

    /*��������Product����*/
    private Product product;
    public void setProduct(Product product) {
        this.product = product;
    }
    public Product getProduct() {
        return this.product;
    }

    /*��ת�����Product��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�ProductClass��Ϣ*/
        List<ProductClass> productClassList = productClassDAO.QueryAllProductClassInfo();
        ctx.put("productClassList", productClassList);
        return "add_view";
    }

    /*���Product��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddProduct() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤��Ʒ����Ƿ��Ѿ�����*/
        String productNo = product.getProductNo();
        Product db_product = productDAO.GetProductByProductNo(productNo);
        if(null != db_product) {
            ctx.put("error",  java.net.URLEncoder.encode("����Ʒ����Ѿ�����!"));
            return "error";
        }
        try {
            ProductClass productClassObj = productClassDAO.GetProductClassByClassId(product.getProductClassObj().getClassId());
            product.setProductClassObj(productClassObj);
            /*������ƷͼƬ�ϴ�*/
            String productPhotoPath = "upload/noimage.jpg"; 
       	 	if(productPhotoFile != null)
       	 		productPhotoPath = photoUpload(productPhotoFile,productPhotoFileContentType);
       	 	product.setProductPhoto(productPhotoPath);
            productDAO.AddProduct(product);
            ctx.put("message",  java.net.URLEncoder.encode("Product��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Product���ʧ��!"));
            return "error";
        }
    }

    /*��ѯProduct��Ϣ*/
    public String QueryProduct() {
        if(currentPage == 0) currentPage = 1;
        if(productNo == null) productNo = "";
        if(productName == null) productName = "";
        List<Product> productList = productDAO.QueryProductInfo(productNo, productClassObj, productName, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        productDAO.CalculateTotalPageAndRecordNumber(productNo, productClassObj, productName);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = productDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = productDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("productList",  productList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("productNo", productNo);
        ctx.put("productClassObj", productClassObj);
        List<ProductClass> productClassList = productClassDAO.QueryAllProductClassInfo();
        ctx.put("productClassList", productClassList);
        ctx.put("productName", productName);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryProductOutputToExcel() { 
        if(productNo == null) productNo = "";
        if(productName == null) productName = "";
        List<Product> productList = productDAO.QueryProductInfo(productNo,productClassObj,productName);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Product��Ϣ��¼"; 
        String[] headers = { "��Ʒ���","��Ʒ��Ŀ","��Ʒ����","��Ʒ�۸�","��ƷͼƬ","��Ʒ���"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<productList.size();i++) {
        	Product product = productList.get(i); 
        	dataset.add(new String[]{product.getProductNo(),product.getProductClassObj().getClassName(),
product.getProductName(),product.getPrice() + "",product.getProductPhoto(),product.getStockDesc()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Product.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯProduct��Ϣ*/
    public String FrontQueryProduct() {
        if(currentPage == 0) currentPage = 1;
        if(productNo == null) productNo = "";
        if(productName == null) productName = "";
        List<Product> productList = productDAO.QueryProductInfo(productNo, productClassObj, productName, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        productDAO.CalculateTotalPageAndRecordNumber(productNo, productClassObj, productName);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = productDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = productDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("productList",  productList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("productNo", productNo);
        ctx.put("productClassObj", productClassObj);
        List<ProductClass> productClassList = productClassDAO.QueryAllProductClassInfo();
        ctx.put("productClassList", productClassList);
        ctx.put("productName", productName);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Product��Ϣ*/
    public String ModifyProductQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������productNo��ȡProduct����*/
        Product product = productDAO.GetProductByProductNo(productNo);

        List<ProductClass> productClassList = productClassDAO.QueryAllProductClassInfo();
        ctx.put("productClassList", productClassList);
        ctx.put("product",  product);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Product��Ϣ*/
    public String FrontShowProductQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������productNo��ȡProduct����*/
        Product product = productDAO.GetProductByProductNo(productNo);

        List<ProductClass> productClassList = productClassDAO.QueryAllProductClassInfo();
        ctx.put("productClassList", productClassList);
        ctx.put("product",  product);
        return "front_show_view";
    }

    /*�����޸�Product��Ϣ*/
    public String ModifyProduct() {
        ActionContext ctx = ActionContext.getContext();
        try {
            ProductClass productClassObj = productClassDAO.GetProductClassByClassId(product.getProductClassObj().getClassId());
            product.setProductClassObj(productClassObj);
            /*������ƷͼƬ�ϴ�*/
            if(productPhotoFile != null) {
            	String productPhotoPath = photoUpload(productPhotoFile,productPhotoFileContentType);
            	product.setProductPhoto(productPhotoPath);
            }
            productDAO.UpdateProduct(product);
            ctx.put("message",  java.net.URLEncoder.encode("Product��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Product��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Product��Ϣ*/
    public String DeleteProduct() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            productDAO.DeleteProduct(productNo);
            ctx.put("message",  java.net.URLEncoder.encode("Productɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Productɾ��ʧ��!"));
            return "error";
        }
    }

}
