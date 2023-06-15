package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Coupon;
import com.mobileclient.domain.Seller;
import com.mobileclient.service.SellerService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class CouponQueryActivity extends Activity {
	// ������ѯ��ť
	private Button btnQuery;
	// �����Ż�ȯ���������
	private EditText ET_couponName;
	// ���������̼�������
	private Spinner spinner_sellerObj;
	private ArrayAdapter<String> sellerObj_adapter;
	private static  String[] sellerObj_ShowText  = null;
	private List<Seller> sellerList = null; 
	/*�̼ҹ���ҵ���߼���*/
	private SellerService sellerService = new SellerService();
	// ���������û�������
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null; 
	/*�û�����ҵ���߼���*/
	private UserInfoService userInfoService = new UserInfoService();
	// ��������ʱ�������
	private EditText ET_couponTime;
	/*��ѯ�����������浽���������*/
	private Coupon queryConditionCoupon = new Coupon();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.coupon_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�����Ż�ȯ��ѯ����");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_couponName = (EditText) findViewById(R.id.ET_couponName);
		spinner_sellerObj = (Spinner) findViewById(R.id.Spinner_sellerObj);
		// ��ȡ���е��̼�
		try {
			sellerList = sellerService.QuerySeller(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int sellerCount = sellerList.size();
		sellerObj_ShowText = new String[sellerCount+1];
		sellerObj_ShowText[0] = "������";
		for(int i=1;i<=sellerCount;i++) { 
			sellerObj_ShowText[i] = sellerList.get(i-1).getSellerName();
		} 
		// ����ѡ������ArrayAdapter��������
		sellerObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, sellerObj_ShowText);
		// ���÷����̼������б�ķ��
		sellerObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_sellerObj.setAdapter(sellerObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_sellerObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionCoupon.setSellerObj(sellerList.get(arg2-1).getSellUserName()); 
				else
					queryConditionCoupon.setSellerObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_sellerObj.setVisibility(View.VISIBLE);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// ��ȡ���е��û�
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount+1];
		userObj_ShowText[0] = "������";
		for(int i=1;i<=userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i-1).getName();
		} 
		// ����ѡ������ArrayAdapter��������
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// ���÷����û������б�ķ��
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_userObj.setAdapter(userObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionCoupon.setUserObj(userInfoList.get(arg2-1).getUser_name()); 
				else
					queryConditionCoupon.setUserObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_couponTime = (EditText) findViewById(R.id.ET_couponTime);
		/*������ѯ��ť*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��ȡ��ѯ����*/
					queryConditionCoupon.setCouponName(ET_couponName.getText().toString());
					queryConditionCoupon.setCouponTime(ET_couponTime.getText().toString());
					Intent intent = getIntent();
					//����ʹ��bundle��������������
					Bundle bundle =new Bundle();
					//�����������Ȼ�Ǽ�ֵ�Ե���ʽ
					bundle.putSerializable("queryConditionCoupon", queryConditionCoupon);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
