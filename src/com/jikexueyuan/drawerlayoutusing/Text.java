package com.jikexueyuan.drawerlayoutusing;


import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;


public class Text extends Fragment {
	private String username;
private EditText et;
private SQLiteDatabase database;
private Cursor cursor;
private Spinner sp;
private String type1="全部";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final LinearLayout view = (LinearLayout) inflater.inflate(R.layout.text, container, false);
		et=(EditText)view.findViewById(R.id.et);
		sp=(Spinner)view.findViewById(R.id.sp);
		et.setOnKeyListener(backListener);
		 username = getArguments().getString("username");
		 database = view.getContext().openOrCreateDatabase("jerry.db3", Context.MODE_PRIVATE, null);
		 
		 
		 
		 sp.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					String[] type = getResources().getStringArray(R.array.type);
					
				type1=type[arg2];
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		 
		 
		return view;
	}
	
	
	
	
	private View.OnKeyListener backListener = new View.OnKeyListener() {  
		public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
			// edittext要获取焦点才会输出
			 if (arg1 == KeyEvent.KEYCODE_BACK && arg2.getAction() == KeyEvent.ACTION_DOWN) {  
	         //执行修改
				
				 if(!"".equals(et.getText().toString())){
					  ContentValues contentValues=new ContentValues();
					 contentValues.put("name", username);
					 contentValues.put("content", et.getText().toString());
					 contentValues.put("state", "未完成");
					 contentValues.put("type", type1);
					 contentValues.putNull("path");
					 database.insert("note", null, contentValues);
				 }
	            }    
			return false;
		}  
    };  



}
