package com.jikexueyuan.drawerlayoutusing;

import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.R.anim;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener {
    private String username="游客";
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList,mainListView;
	private ArrayList<HashMap<String, Object>> menuLists;
	private SimpleAdapter adapter;
	private ActionBarDrawerToggle mDrawerToggle;
	private String mTitle;
	private SQLiteDatabase database;
	private ArrayList<String> arrayList;
	private ArrayAdapter<String> arrayAdapter;
	private final String IMAGE_TYPE = "image/*";
	private final int IMAGE_CODE = 0;
	private TextView a,b,c;
	private String path;

	
	
	
	
	


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		/*LayoutInflater inflater = getLayoutInflater();
		View view=inflater.inflate(R.layout.user, null);
		view.findViewById(R.id.login).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,User.class);
				startActivity(intent);
			}
		});*/
		
		
		/*Fragment contentFragment = new ContentFragment();
		
		Bundle args = new Bundle();
		args.putString("text", "全部");
	
		args.putString("username", username);
		
		contentFragment.setArguments(args);

		FragmentManager fm = getFragmentManager();
		fm.beginTransaction().replace(R.id.content_frame, contentFragment)
				.commit();*/
		
		refresh();
		
		

		mTitle = (String) getTitle();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		menuLists = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("word", "全部");
		map1.put("image", R.drawable.all);
		menuLists.add(map1);
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("word", "生活");
		map2.put("image", R.drawable.life);
		menuLists.add(map2);
		HashMap<String, Object> map3 = new HashMap<String, Object>();
		map3.put("word", "工作");
		map3.put("image", R.drawable.work);
		menuLists.add(map3);
			
			
		adapter = new SimpleAdapter(this, menuLists, R.layout.listview, new String[]{"word","image"}, new int[]{R.id.text,R.id.image});
		//LayoutInflater inflater1=getLayoutInflater();
		//mDrawerList.addFooterView(inflater1.inflate(R.layout.user, null));
		/*LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
			    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 100, 0, 0);*/
		 a=new TextView(this);
	 b=new TextView(this);
		 c=new TextView(this);
		
		
		if(username=="游客"){
			a.setText("登录");
			b.setText("注册");
			a.setX(10);
		a.setY(200);
		b.setX(10);
		b.setY(215);
		}
		else{
			a.setText(username);
			a.setClickable(false);
			//b.setClickable(false);
			//b.setVisibility(View.GONE);
			c.setText("注销");
			a.setX(10);
			a.setY(200);
			c.setX(10);
			c.setY(200);
		}
		
		a.setTextColor(Color.BLUE);
		b.setTextColor(Color.BLUE);
		c.setTextColor(Color.BLUE);
		
		
		mDrawerList.addFooterView(a);
		mDrawerList.addFooterView(b);
		mDrawerList.addFooterView(c);
		
		
		
		a.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,User.class);
				
				startActivityForResult(intent, 1);
				
			}
		});
		
b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,register.class);
				startActivityForResult(intent, 2);
			}
		});


c.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		username="游客";
		refresh();
		visitor();
		
	//invalidate(); 
		//refresh();
		
	}
});




		
		
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(this);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle("请选择");
				invalidateOptionsMenu(); // Call onPrepareOptionsMenu()
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		//开启ActionBar上APP ICON的功能
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		
		
		
		/*arrayList=new ArrayList<String>();
		database = this.openOrCreateDatabase("jerry.db3", Context.MODE_PRIVATE, null);
		//database.execSQL("create table note(_id INTEGER PRIMARY KEY AUTOINCREMENT, name varchar NOT NULL,password varchar,content varchar,state varchar,type varchar,path varchar)");
		Cursor cursor = database.query("note", null, null, null, null, null, null);
		cursor.moveToFirst();
		for (int i = 0; i < cursor.getCount(); i++) {
			String value = cursor.getString(cursor.getColumnIndex("content"));
			arrayList.add(value);
			System.out.println(value);
			cursor.moveToNext();
		}
		arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
		mainListView=(ListView)findViewById(R.id.mainListView);
		mainListView.setAdapter(arrayAdapter);*/
		
		
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_websearch).setVisible(!isDrawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		menu.add(1, 1, 1, "新建文本便签");
		menu.add(1, 2, 1, "新建图片便签");
		
		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		
		//如果单击了选项菜单按钮，则新开一个fragment用于新建便签
		if(item.getItemId()==1){
			//Toast.makeText(MainActivity.this, "新建文字便签", Toast.LENGTH_SHORT).show();  
		
			
			Bundle args = new Bundle();
			args.putString("username", username);
		
			
			
			

			   Fragment text= new Text();
			    android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
			    transaction.replace(R.id.content_frame, text);
			    transaction.addToBackStack(null);
			    text.setArguments(args);
			   
			    transaction.commit();
		}
			
		if(item.getItemId()==2){
			//Toast.makeText(MainActivity.this, "新建图片便签", Toast.LENGTH_SHORT).show();  
			Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
			getAlbum.setType(IMAGE_TYPE);
			startActivityForResult(getAlbum, IMAGE_CODE);
			
		/*	Bundle args = new Bundle();
			args.putString("username", username);
			args.putString("path", path);
			
		
			    Fragment image= new Image();
			    image.setArguments(args);
			    android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
			    transaction.replace(R.id.content_frame, image);
			    transaction.addToBackStack(null);
			    
			   
			    transaction.commit();*/
			
		}
		
		//将ActionBar上的图标与Drawer结合起来
		if (mDrawerToggle.onOptionsItemSelected(item)){
			return true;
		}
		switch (item.getItemId()) {
		case R.id.action_websearch:
			Intent intent = new Intent();
			intent.setAction("android.intent.action.VIEW");
			Uri uri = Uri.parse("http://home.sise.cn/");
			intent.setData(uri);
			startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		//需要将ActionDrawerToggle与DrawerLayout的状态同步
		//将ActionBarDrawerToggle中的drawer图标，设置为ActionBar中的Home-Button的Icon
		mDrawerToggle.syncState();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// 动态插入一个Fragment到FrameLayout当中
		Fragment contentFragment = new ContentFragment();
		Bundle args = new Bundle();
		args.putString("text",(String) menuLists.get(position).get("word"));
		args.putString("username",username);
		contentFragment.setArguments(args);

		FragmentManager fm = getFragmentManager();
		fm.beginTransaction().replace(R.id.content_frame, contentFragment)
				.commit();

		

		mDrawerLayout.closeDrawer(mDrawerList);
	}
	
	
	
	
	public void refresh() {  
		Fragment contentFragment = new ContentFragment();
		Bundle args = new Bundle();
		args.putString("text", "全部");
	
		args.putString("username", username);
		
		contentFragment.setArguments(args);

		FragmentManager fm = getFragmentManager();
		fm.beginTransaction().replace(R.id.content_frame, contentFragment)
				.commit();
	} 
	
	public void user(){
		a.setText(username);
		a.setClickable(false);
		b.setVisibility(View.GONE);
		c.setVisibility(View.VISIBLE);
		//b.setClickable(false);
		//b.setVisibility(View.GONE);
		c.setText("注销");
		a.setX(10);
		a.setY(350);
		c.setX(10);
		c.setY(380);
		
		a.setTextColor(Color.BLUE);
		
		c.setTextColor(Color.BLUE);
	}
	public void visitor(){
		b.setVisibility(View.VISIBLE);
		a.setClickable(true);
		a.setText("登录");
		b.setText("注册");
		a.setX(10);
	a.setY(355);
	b.setX(10);
	b.setY(388);
	c.setVisibility(View.GONE);
	a.setTextColor(Color.BLUE);
	b.setTextColor(Color.BLUE);
	
	}
	
//注意添加sd卡权限
public void onActivityResult(int requestCode, int resultCode, Intent data){
		    if (resultCode != RESULT_OK) {        //此处的 RESULT_OK 是系统自定义得一个常量
		        Log.e("TAG","ActivityResult resultCode error");
		        return;
		    }
		    Bitmap bm = null;
		    //外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
		    ContentResolver resolver = getContentResolver();
		    //此处的用于判断接收的Activity是不是你想要的那个
		    if (requestCode == IMAGE_CODE) {
		        try { 
		            Uri originalUri = data.getData();        //获得图片的uri 
		            bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);        //显得到bitmap图片
		            String[] proj = {MediaStore.Images.Media.DATA};
		            //好像是android多媒体数据库的封装接口，具体的看Android文档
		            Cursor cursor = managedQuery(originalUri, proj, null, null, null); 
		            //按我个人理解 这个是获得用户选择的图片的索引值
		            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		            //将光标移至开头 ，这个很重要，不小心很容易引起越界
		            cursor.moveToFirst();
		            //最后根据索引值获取图片路径
		            path = cursor.getString(column_index);
		            //Toast.makeText(MainActivity.this, path, 1).show();
		            
		            Bundle args = new Bundle();
					args.putString("username", username);
					args.putString("path", path);
					
				
					    Fragment image= new Image();
					    image.setArguments(args);
					    android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
					    transaction.replace(R.id.content_frame, image);
					    transaction.addToBackStack(null);
					    
					   
					    transaction.commit();
		            
		        }catch (IOException e) {
		            Log.e("TAG",e.toString()); 
		        }
		    }
       if(requestCode==1){
    	   //Bundle bundle = data.getExtras();
    	   //String username1 = bundle.getString("username");
    	  // String password1 = bundle.getString("password");
    	  // username=username1;
    	   //Toast.makeText(MainActivity.this, username1+password1, 1);
    	   String username1=data.getStringExtra("username");
    	   String password1=data.getStringExtra("password");
    	   
    	  
    	   
    	   
    	   username=username1;
    	   refresh();
    	   user();
       }
       if(requestCode==2){
    	 //Bundle bundle = data.getExtras();
    	   //String username1 = bundle.getString("username");
    	  // String password1 = bundle.getString("password");
    	  // username=username1;
    	   //Toast.makeText(MainActivity.this, username1+password1, 1);
    	   String username1=data.getStringExtra("username");
    	   String password1=data.getStringExtra("password");
    	 //System.out.println(username1+password1);
       }
		

		}

}
