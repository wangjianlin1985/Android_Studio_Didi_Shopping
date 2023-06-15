package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Seller;

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
public class SellerQueryActivity extends Activity {
	// ������ѯ��ť
	private Button btnQuery;
	// �����̼��˺������
	private EditText ET_sellUserName;
	// �����̼����������
	private EditText ET_sellerName;
	// ������ϵ�绰�����
	private EditText ET_telephone;
	// ��פ���ڿؼ�
	private DatePicker dp_addDate;
	private CheckBox cb_addDate;
	/*��ѯ�����������浽���������*/
	private Seller queryConditionSeller = new Seller();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.seller_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�����̼Ҳ�ѯ����");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_sellUserName = (EditText) findViewById(R.id.ET_sellUserName);
		ET_sellerName = (EditText) findViewById(R.id.ET_sellerName);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		dp_addDate = (DatePicker) findViewById(R.id.dp_addDate);
		cb_addDate = (CheckBox) findViewById(R.id.cb_addDate);
		/*������ѯ��ť*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��ȡ��ѯ����*/
					queryConditionSeller.setSellUserName(ET_sellUserName.getText().toString());
					queryConditionSeller.setSellerName(ET_sellerName.getText().toString());
					queryConditionSeller.setTelephone(ET_telephone.getText().toString());
					if(cb_addDate.isChecked()) {
						/*��ȡ��פ����*/
						Date addDate = new Date(dp_addDate.getYear()-1900,dp_addDate.getMonth(),dp_addDate.getDayOfMonth());
						queryConditionSeller.setAddDate(new Timestamp(addDate.getTime()));
					} else {
						queryConditionSeller.setAddDate(null);
					} 
					Intent intent = getIntent();
					//����ʹ��bundle��������������
					Bundle bundle =new Bundle();
					//�����������Ȼ�Ǽ�ֵ�Ե���ʽ
					bundle.putSerializable("queryConditionSeller", queryConditionSeller);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
