package com.jikexueyuan.drawerlayoutusing;

import java.util.ArrayList;
import java.util.HashMap;

import com.jikexueyuan.drawerlayoutusing.R.drawable;

import android.R.integer;
import android.animation.Animator;
import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ContentFragment extends Fragment {
	
	
	private SQLiteDatabase database;
	private Cursor cursor;
	
	//private LayoutInflater inflater1;
	//private View view1=inflater1.inflate(R.layout.activity_main, null);
	private String image;
	private String[] del;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater,  ViewGroup container,
			Bundle savedInstanceState) {
		final LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_content, container, false);
		
		
		String text = getArguments().getString("text");
		String username = getArguments().getString("username");
		
	
		database = view.getContext().openOrCreateDatabase("jerry.db3", Context.MODE_PRIVATE, null);
		
		if(text=="全部"){ 
			cursor=database.query("note", null, "name='"+username+"'", null, null, null, null);
			
			}
		else{
		 cursor=database.query("note", null, "type='"+text+"'and name='"+username+"'", null, null, null, null);}
		
			
		
		cursor.moveToFirst();
		for (int i = 0; i < cursor.getCount(); i++) {
			String value = cursor.getString(cursor.getColumnIndex("content"));
			final int id=cursor.getInt(cursor.getColumnIndex("_id"));
			 image =cursor.getString(cursor.getColumnIndex("path"));
			final String state=cursor.getString(cursor.getColumnIndex("state"));
			 final TextView a=new TextView(view.getContext());
			 a.setText(value);
			 if(image!=null){
				 Bitmap bitmap = BitmapFactory.decodeFile(image); 
				 Drawable drawable =new BitmapDrawable(bitmap);
				 drawable.setBounds(0, 0, 50, 
						50);
				 a.setCompoundDrawables(null, null, drawable, null);
				 }
			 a.setTextSize(17);
			 a.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
			 LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.FILL_PARENT,  ViewGroup.LayoutParams.WRAP_CONTENT);
			 
			layoutParams.setMargins(7,3,7,3);//4个参数按顺序分别是左上右下mView.setLayoutParams(layoutParams);
			
			a.setLayoutParams(layoutParams);
			a.setPadding(5, 5, 5, 5);
			a.setHeight(70);
			 a.setBackgroundColor(android.graphics.Color.WHITE);
			 if(cursor.getString(cursor.getColumnIndex("state")).equals("已完成")){
				 int color = Color.rgb(105, 105, 105);
			 a.setTextColor(color);
			 }
			 else a.setTextColor(Color.BLACK);
			view.addView(a);
			//设置点击进入编辑界面
			a.setOnClickListener(new OnClickListener() {
				
			
				
				public void onClick(View arg0) {
					//Fragment content = new Content();
					Bundle args = new Bundle();
					
					
					args.putInt("id", id);
					MainActivity activity=(MainActivity)getActivity();
				
					
					   Fragment content= new Content();
					    android.app.FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
					    transaction.replace(R.id.content_frame, content);
					    transaction.addToBackStack(null);
					    content.setArguments(args);
					   
					    transaction.commit();
					    
					
				}
			});
			
			
			a.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View arg0) {
					// TODO Auto-generated method stub
					if(state.equals("已完成")){ del = new String[]{"删除"};}
					else{del = new String[]{"删除","已完成"};}
					
					AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
					builder.setItems(del, new DialogInterface.OnClickListener(){

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							if(arg1==0){
								database.delete("note", "_id="+id, null);
								
							MainActivity activity=(MainActivity)getActivity();
								
							activity.refresh();
								  /* Fragment content= new ContentFragment();
								    android.app.FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
								    transaction.replace(R.id.content_frame, ContentFragment.this);
								   transaction.addToBackStack(null);
								    transaction.commit();*/
								
							}
							if(arg1==1){
								ContentValues contentValues=new ContentValues();
								contentValues.put("state", "已完成");
								database.update("note", contentValues, "_id="+id, null);
								MainActivity activity=(MainActivity)getActivity();
								
								activity.refresh();
							}
						}
						
					});
					builder.create().show();
					
					
					//设置为真，不扩散，不会同时触发点击事件
					return true;
				}
			});
			

			cursor.moveToNext();
		
		
	}
		
		// android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
		   // transaction.replace(R.id.content_frame, ContentFragment.this);
		   // transaction.addToBackStack(null);
		    
		   
		   // transaction.commit();
		//删除后不能及时刷新
		return view;
	}


	
	
}
