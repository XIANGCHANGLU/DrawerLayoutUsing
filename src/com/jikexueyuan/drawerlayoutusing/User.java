package com.jikexueyuan.drawerlayoutusing;



import java.util.Timer;
import java.util.TimerTask;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class User extends Activity implements OnClickListener {

	private TextView mBtnLogin;
	
	private View progress;
	
	private AnimatorSet set;
	/*private ObjectAnimator animator3;
	private PropertyValuesHolder animator2;
	private PropertyValuesHolder animator;*/
	
	private View mInputLayout;

	private float mWidth, mHeight;
	
	private LinearLayout mName, mPsw;
	 //private LayoutInflater mInflater; 
	 private EditText username,password;
	 private String username1=null;
	 private String password1=null;
	 private SQLiteDatabase database;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.userlogin);
		initView();
		
		
	}

	private void initView() {
		mBtnLogin = (TextView) findViewById(R.id.main_btn_login);
		progress = findViewById(R.id.layout_progress);
		mInputLayout = findViewById(R.id.input_layout);
		mName = (LinearLayout) findViewById(R.id.input_layout_name);
		mPsw = (LinearLayout) findViewById(R.id.input_layout_psw);

		mBtnLogin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		mWidth = mBtnLogin.getMeasuredWidth();
		mHeight = mBtnLogin.getMeasuredHeight();

		mName.setVisibility(View.INVISIBLE);
		mPsw.setVisibility(View.INVISIBLE);

		inputAnimator(mInputLayout, mWidth, mHeight);
		
		Intent intent=new Intent();
		//mInflater=getLayoutInflater();
		//注意,包含进来的布局不用inflate
		//View view=mInflater.inflate(R.layout.input_layout, null);
		username=(EditText)findViewById(R.id.username);
		password=(EditText)findViewById(R.id.password);
		username1=username.getText().toString();
		password1=password.getText().toString();
		
		//查询账号密码
		database=this.openOrCreateDatabase("jerry.db3", Context.MODE_PRIVATE, null);
		Cursor cursor = database.query("user", null, "name='"+username1+"'and password='"+password1+"'", null, null, null, null);
		
		
		
		
		
		 //此处判断输入以及是否存在用户
		//这个if不会被执行?
		if("".equals(username1)||"".equals(password1)){
			Toast.makeText(this, "用户名或密码为空", 1).show();
			//很笨的办法
	finish();
			startActivity(new Intent(this,User.class));
		}
		
		
		else{
			
			//账户密码错误
			if(cursor.getCount()<1){
				Toast.makeText(this, "用户名或密码错误", 1).show();
				finish();
				startActivity(new Intent(this,User.class));
				
			}
			//账号密码正确
			else{
		//Bundle bundle = new Bundle();
		intent.putExtra("username", username1);
		intent.putExtra("password", password1);
		
		//bundle.putString("username",username.getText().toString() );
		//bundle.putString("password",password.getText().toString());
		//System.out.println(username1+password1);
		
		//intent.putExtras(bundle);
		Toast.makeText(this, "欢迎回来", 1).show();
		setResult(RESULT_OK, intent);
		
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				finish();
			}
		}, 2000);
		
		}
	

	}}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	private void inputAnimator(final View view, float w, float h) {

		/*AnimatorSet*/ set = new AnimatorSet();

		ValueAnimator animator = ValueAnimator.ofFloat(0, w);
		animator.addUpdateListener(new AnimatorUpdateListener() {

			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@SuppressLint("NewApi")
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float value = (Float) animation.getAnimatedValue();
				ViewGroup.MarginLayoutParams params = (MarginLayoutParams) view
						.getLayoutParams();
				params.leftMargin = (int) value;
				params.rightMargin = (int) value;
				view.setLayoutParams(params);
			}
		});

		ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout,
				"scaleX", 1f, 0.5f);
		set.setDuration(1000);
		set.setInterpolator(new AccelerateDecelerateInterpolator());
		set.playTogether(animator, animator2);
		set.start();
		set.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animator animation) {

				progress.setVisibility(View.VISIBLE);
				progressAnimator(progress);
				mInputLayout.setVisibility(View.INVISIBLE);

			}

			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub

			}
		});

	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void progressAnimator(final View view) {
		PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
				0.3f, 0.6f);
		PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
				0.3f, 0.6f);
		ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
				animator, animator2);
		animator3.setDuration(1000);
		animator3.setInterpolator(new JellyInterpolator());
		animator3.start();

	}
	
	
	
	
}
