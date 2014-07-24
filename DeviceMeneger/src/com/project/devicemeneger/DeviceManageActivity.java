package com.project.devicemeneger;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost.OnTabChangeListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DeviceManageActivity extends Activity implements
		ActionBar.TabListener {

	SectionsPagerAdapter mSectionsPagerAdapter;
	CharSequence pageTitle;

	ViewPager mViewPager;
	static MySQLiteHelper dbHelper;

	private static final int MENU_QUIT_ID = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setTitle("Devices");
		

		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			pageTitle = mSectionsPagerAdapter.getPageTitle(i);
		}

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));

		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		menu.addSubMenu(0, MENU_QUIT_ID, 0, getString(R.string.action_quit));
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.new_device_item:
			addDevice(mViewPager);
			break;
		case android.R.id.home:
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			break;
		case MENU_QUIT_ID:
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	void addDevice(View view) {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialog);
		dialog.setTitle("Add Device");
		dialog.setCancelable(true);

		final EditText editId = (EditText) dialog.findViewById(R.id.dialog_edit_id);
		final EditText editName = (EditText) dialog.findViewById(R.id.dialog_edit_name);
		final EditText editOwner = (EditText) dialog.findViewById(R.id.dialog_edit_owner);
		final EditText editIp = (EditText) dialog.findViewById(R.id.dialog_edit_ip);

		Button addBtn = (Button) dialog.findViewById(R.id.dialog_add_btn);
		addBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				

				Device deviceInfo = null;
				int deviceId = editId.getText().hashCode();
				String deviceName = editName.getText().toString();
				int deviceOwner = editOwner.getText().hashCode();
				int deviceIp =  editIp.getText().hashCode();
				String pageTitle = getActionBar().getSelectedTab().getText()
						.toString();
				
//				DevicesFragment.deviceList.add(new Device(deviceId,
//						deviceName, deviceOwner, deviceIp));
//				//deviceInfo = createInfo(deviceName);
//				DevicesFragment.deviceAdapter.add(deviceInfo);
//				System.out.println("adapter= "+DevicesFragment.deviceAdapter.getContext());
//				DevicesFragment.deviceAdapter.notifyDataSetChanged();
				//createInfo(deviceName);
				
				switch (pageTitle) {
				case "My Devices":
					System.out.println("page title "+ pageTitle);
					DevicesFragment.deviceList.add(new Device(deviceId,
							deviceName, deviceOwner, deviceIp));
					deviceInfo = DevicesFragment.datasource.createInfo(deviceName);
					DevicesFragment.deviceAdapter.add(deviceInfo);
					DevicesFragment.deviceAdapter.notifyDataSetChanged();
					break;

				case "Recent":
					System.out.println("page title "+ pageTitle);
					RecentFragment.deviceList.add(new Device(deviceId,
							deviceName, deviceOwner, deviceIp));
					deviceInfo = RecentFragment.datasource.createInfo(deviceName);
					System.out.println("deviceInfo"+deviceInfo);
					RecentFragment.deviceAdapter.add(deviceInfo);
					RecentFragment.deviceAdapter.notifyDataSetChanged();
					break;
				case "More":
					System.out.println("page title "+ pageTitle);
					MoreFragment.deviceList.add(new Device(deviceId,
							deviceName, deviceOwner, deviceIp));
					deviceInfo = MoreFragment.datasource.createInfo(deviceName);
					MoreFragment.deviceAdapter.add(deviceInfo);
					MoreFragment.deviceAdapter.notifyDataSetChanged();
					break;

				}

				dialog.dismiss();
			}

//			private void createInfo(String deviceName) {
//				
//				 ContentValues cv = new ContentValues();
//				    
//				    // �������� ������ �� ����� �����
//
//				    // ������������ � ��
//				    SQLiteDatabase db = dbHelper.getWritableDatabase();
//				    
//				    cv.put("name", deviceName);
//				      cv.put("email", deviceName);
//				      // ��������� ������ � �������� �� ID
//				      long rowID = db.insert("mytable", null, cv);
//				      System.out.println( "row inserted, ID = " + rowID);
//				}

		
		});

		dialog.show();
	}
	

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		mViewPager.setCurrentItem(tab.getPosition());
		
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch(position){
			case 0:
				return DevicesFragment.newInstance(position + 1);
			
			case 1:
				return RecentFragment.newInstance(position + 1);
			
			case 2:
				return MoreFragment.newInstance(position + 1);
			}
			System.out.println("no fragment");
			return null;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.title_section1);
			case 1:
				return getString(R.string.title_section2);
			case 2:
				return getString(R.string.title_section3);
			}
			return null;
		}
	}
	
	
}
