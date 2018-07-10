package com.jikexueyuan.drawerlayoutusing;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.style.UpdateAppearance;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Content extends Fragment {
	private int id;
	private SQLiteDatabase database;
	private Cursor cursor;
	private EditText et;
	private ImageView iv;
	private ImageView tv;
	private Spinner sp;
	private int pos;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final LinearLayout view = (LinearLayout) inflater.inflate(R.layout.content, container, false);
		et=(EditText)view.findViewById(R.id.et);
		iv=(ImageView)view.findViewById(R.id.iv);
		tv=(ImageView)view.findViewById(R.id.tv);
		sp=(Spinner)view.findViewById(R.id.sp);
		
		 id = getArguments().getInt("id");
		view.findViewById(R.id.et).setOnKeyListener(backListener);
		
		database = view.getContext().openOrCreateDatabase("jerry.db3", Context.MODE_PRIVATE, null);
		cursor=database.query("note", null, "_id="+id, null, null, null, null);
		cursor.moveToFirst();
		for (int i = 0; i < cursor.getCount(); i++) {
			String value = cursor.getString(cursor.getColumnIndex("content"));
			String path = cursor.getString(cursor.getColumnIndex("path"));
			String type= cursor.getString(cursor.getColumnIndex("type"));
			Bitmap bitmap = BitmapFactory.decodeFile(path);
			Drawable drawable =new BitmapDrawable(bitmap);
			et.setText(value);
			iv.setBackgroundDrawable(drawable);
			
			if(path==null){tv.setVisibility(View.GONE);}
			tv.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					iv.setBackgroundDrawable(null);
					tv.setVisibility(View.GONE);
					//执行更新path为空操作
					ContentValues contentValues=new ContentValues();
					contentValues.putNull("path");
					database.update("note", contentValues, "_id="+id, null);
					
				}
			});
			if(type.equals("全部"))pos=0;if(type.equals("生活"))pos=1;if(type.equals("工作"))pos=2;
			sp.setSelection(pos, true);
			sp.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					String[] type = getResources().getStringArray(R.array.type);
					ContentValues contentValues=new ContentValues();
					contentValues.put("type", type[arg2]);
					database.update("note", contentValues, "_id="+id, null);
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			
			
		}
		
		
		
		return view;
	}
	
	
	private View.OnKeyListener backListener = new View.OnKeyListener() {  
		public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
			// edittext要获取焦点才会输出
			 if (arg1 == KeyEvent.KEYCODE_BACK && arg2.getAction() == KeyEvent.ACTION_DOWN) {  
	         //执行修改
				 //System.out.println(id);
				if(!"".equals(et.getText().toString())){
				 ContentValues contentValues=new ContentValues();
				 contentValues.put("content", et.getText().toString());
				 database.update("note", contentValues, "_id="+id, null);}
				
				 else{
					 database.delete("note", "_id="+id, null);
				 }
	            }    
			
			return false;
		}  
    };  



}
