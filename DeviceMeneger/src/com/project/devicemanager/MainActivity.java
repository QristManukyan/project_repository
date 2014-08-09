package com.project.devicemanager;

import com.project.devicemanager.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	
	final int REQUSET_CODE_DEVICE = 1;
	final int REQUSET_CODE_FILES = 2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_page_layout);

		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setTitle("Devices");

		Button deviceBtn = (Button) findViewById(R.id.home_page_devices_btn);
		deviceBtn.setOnClickListener(this);
		Button fileBtn = (Button) findViewById(R.id.home_page_files_btn);
		fileBtn.setOnClickListener(this);
		Button button3Btn = (Button) findViewById(R.id.home_page_btn3);
		button3Btn.setOnClickListener(this);
		Button button4Btn = (Button) findViewById(R.id.home_page_btn4);
		button4Btn.setOnClickListener(this);
		
	}


	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
	}

	@Override
	public void onClick(View view) {
		Intent intent;
		switch (view.getId()){
		case R.id.home_page_devices_btn :
			 intent = new Intent(this, DeviceManageActivity.class);
			 startActivityForResult(intent, REQUSET_CODE_DEVICE);
			 break;
//TODO
		case R.id.home_page_files_btn:
			System.out.println("file on click");
			intent = new Intent(this, FileChooserActivity.class);
			startActivityForResult(intent, REQUSET_CODE_FILES);
			
			break;
		case R.id.home_page_btn3:
			System.out.println("btn3");
			//todo
//			startService(new Intent(this, MyService.class).putExtra("time", 5));
//			startService(new Intent(this, MyService.class).putExtra("time", 7));
//			startService(new Intent(this, MyService.class).putExtra("time", 4));
			break;
		case R.id.home_page_btn4:
			System.out.println("btn4");
			//todo
			break;
		
		}
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (requestCode == REQUSET_CODE_FILES){
			if (resultCode == RESULT_OK){
				String curFileName = data.getStringExtra("GetFileName"); 
				System.out.println("curFileName is = "+curFileName);
			}
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	@Override
	protected void onStop() {
		super.onStop();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}