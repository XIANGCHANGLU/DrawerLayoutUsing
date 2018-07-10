package com.jikexueyuan.drawerlayoutusing;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class Image extends Fragment {

	private EditText et;
	private ImageView iv;
	private ImageView tv;
	private String username,path;
	private SQLiteDatabase database;
	private Cursor cursor;
	private Spinner sp;
	private String type1="全部";
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final LinearLayout view = (LinearLayout) inflater.inflate(R.layout.image, container, false);
		et=(EditText) view.findViewById(R.id.et);
		iv=(ImageView) view.findViewById(R.id.iv);
		tv=(ImageView) view.findViewById(R.id.tv);
		sp=(Spinner)view.findViewById(R.id.sp);
		username=getArguments().getString("username");
		path=getArguments().getString("path");
		//String type= cursor.getString(cursor.getColumnIndex("type"));
		 database = view.getContext().openOrCreateDatabase("jerry.db3", Context.MODE_PRIVATE, null);
		 et.setOnKeyListener(backListener);
		 
			Bitmap bitmap = BitmapFactory.decodeFile(path);
			Drawable drawable =new BitmapDrawable(bitmap);
			//System.out.println(path);
			iv.setBackgroundDrawable(drawable);
			tv.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					iv.setBackgroundDrawable(null);
					path=null;
					tv.setVisibility(View.GONE);
				}
			});
		 
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
				
				 if(!"".equals(et.getText().toString())||path!=null){
					  /*ContentValues contentValues=new ContentValues();
					 contentValues.put("name", username);
					 contentValues.put("content", et.getText().toString());
					 contentValues.put("state", "未完成");
					 contentValues.put("type", "全部");
					 contentValues.putNull("path");
					 database.insert("note", null, contentValues);*/
					 if(!"".equals(et.getText().toString())&&path!=null){
						 ContentValues contentValues=new ContentValues();
						 contentValues.put("name", username);
						 contentValues.put("content", et.getText().toString());
						 contentValues.put("state", "未完成");
						 contentValues.put("type", type1);
						 contentValues.put("path",path);
						 database.insert("note", null, contentValues);
					 }
					
					 if("".equals(et.getText().toString())&&path!=null){
						 ContentValues contentValues=new ContentValues();
						 contentValues.put("name", username);
						 contentValues.put("content", "图片便签");
						 contentValues.put("state", "未完成");
						 contentValues.put("type", type1);
						 contentValues.put("path",path);
						 database.insert("note", null, contentValues);
					 }
					 
					 
					 
				 }
	            }    
			return false;
		}  
    };  


}
