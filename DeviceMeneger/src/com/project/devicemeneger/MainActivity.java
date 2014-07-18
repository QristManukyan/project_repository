package com.project.devicemeneger;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_page_layout);

		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		Button deviceBtn = (Button) findViewById(R.id.home_page_devices_btn);
		deviceBtn.setOnClickListener(this);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
	}

	@Override
	public void onClick(View view) {
		System.out.println("onClick");
		if (view.getId() == R.id.home_page_devices_btn) {
			System.out.println("new ACtivity");

			Intent intent = new Intent(this, DeviceManageActivity.class);
			startActivity(intent);

		}
	}
}