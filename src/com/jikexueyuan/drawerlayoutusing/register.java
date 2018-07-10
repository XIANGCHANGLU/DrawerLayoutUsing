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
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
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
public class register extends Activity implements OnClickListener {

	private TextView mBtnLogin;
	
	private View progress;
	
	private View mInputLayout;

	private float mWidth, mHeight;

	private LinearLayout mName, mPsw;
	private EditText username,password;
	 private String username1=null;
	 private String password1=null;
	 private SQLiteDatabase database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.userregister);

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
		username=(EditText)findViewById(R.id.username);
		password=(EditText)findViewById(R.id.password);
		username1=username.getText().toString();
		password1=password.getText().toString();
		
		database=this.openOrCreateDatabase("jerry.db3", Context.MODE_PRIVATE, null);
		
		Cursor cursor = database.query("user", null, "name='"+username1+"'", null, null, null, null);
		if("".equals(username1)||"".equals(password1)){
			Toast.makeText(this, "用户名或密码为空", 1).show();
			finish();
			startActivity(new Intent(this,User.class));
	
		}
		
		
		else{
			if(cursor.getCount()!=0){
				Toast.makeText(this, "用户已存在", 1).show();
				finish();
				startActivity(new Intent(this,User.class));
			}
		
			else{
				ContentValues contentValues=new ContentValues();
				contentValues.put("name", username1);
				contentValues.put("password", password1);
				
				database.insert("user", null, contentValues);
		
		Toast.makeText(this, "注册成功", 1).show();
		setResult(RESULT_OK, intent);
		
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				finish();
			}
		}, 2000);
		
		}
		}

	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	private void inputAnimator(final View view, float w, float h) {

		AnimatorSet set = new AnimatorSet();

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
