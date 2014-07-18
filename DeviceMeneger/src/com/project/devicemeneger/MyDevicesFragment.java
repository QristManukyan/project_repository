package com.project.devicemeneger;

import java.util.ArrayList;
import java.util.List;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MyDevicesFragment extends Fragment  {
	static DevicesDataSource datasource;
	

static DeviceAdapter deviceAdapter;
	ListView listView;
	 public static List<Device> deviceList = new ArrayList<Device>();
	private static final String ARG_SECTION_NUMBER = "section_number";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	//	addDevice();
		
	
	};

	public static MyDevicesFragment newInstance(int sectionNumber) {
		MyDevicesFragment fragment = new MyDevicesFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public MyDevicesFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		datasource = new DevicesDataSource(getActivity());
		datasource.open();
		List<Device> values = datasource.getAllInfos();
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);

		ListView listView = (ListView) rootView
				.findViewById(R.id.device_list_view);
		System.out.println("listView is created" + listView);
		deviceAdapter = new DeviceAdapter(values, getActivity());
		listView.setAdapter(deviceAdapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				((InfoListener) getActivity()).onChangeInfo(deviceList
						.get(position).name);
				System.out.println("Device is selected");
			}
		});
		setHasOptionsMenu(true);

		return rootView;
	}
	
	 

	public interface InfoListener {

		public void onChangeInfo(String info);
	}

	private void init() {
		deviceList.add(new Device(1, "Device1", "10", "100"));
		deviceList.add(new Device(2, "Device2", "20", "200"));
		deviceList.add(new Device(3, "Device3", "30", "300"));
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
	}
	
//	void addDevice() {
//    	final Dialog dialog = new Dialog(getActivity());
//    	dialog.setContentView(R.layout.dialog);
//    	dialog.setTitle("Add planet");
//    	dialog.setCancelable(true);
//    	
//    	final EditText editId = (EditText) dialog.findViewById(R.id.dialog_edit_id);
//    	final EditText editName = (EditText) dialog.findViewById(R.id.dialog_edit_name);
//    	final EditText editOwner = (EditText) dialog.findViewById(R.id.dialog_edit_owner);
//    	final EditText editIp = (EditText) dialog.findViewById(R.id.dialog_edit_ip);
//    	
//    	Button addBtn = (Button) dialog.findViewById(R.id.dialog_add_btn);
//    	addBtn.setOnClickListener(new View.OnClickListener() {
//			
//			public void onClick(View v) {
//				Device deviceIndfo = null;
//				long deviceId = editId.getText().hashCode();
//				String deviceName = editName.getText().toString();
//				String deviceOwner = editOwner.getText().toString();
//				String deviceIp = editIp.getText().toString();
//				
//				deviceList.add(new Device(deviceId, deviceName, deviceOwner, deviceIp));
//				deviceIndfo = datasource.createInfo(deviceName);
//				deviceAdapter.add(deviceIndfo);
//				System.out.println("Device list = " +deviceList );
//				deviceAdapter.notifyDataSetChanged();
//				dialog.dismiss();
//			}
//		});
//    	
//    	dialog.show();
//    }
}
