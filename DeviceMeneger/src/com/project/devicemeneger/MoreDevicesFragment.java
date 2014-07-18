package com.project.devicemeneger;

import java.util.ArrayList;
import java.util.List;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;


	
	public class MoreDevicesFragment extends Fragment {
		private DevicesDataSource datasource;


		static DeviceAdapter deviceAdapter;
		ListView listView;
		static List<Device> deviceList = new ArrayList<Device>();
		private static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			init();
		};

		public static MoreDevicesFragment newInstance(int sectionNumber) {
			MoreDevicesFragment fragment = new MoreDevicesFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public MoreDevicesFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			ListView listView = (ListView) rootView
					.findViewById(R.id.device_list_view);
			System.out.println("listView is created" + listView);
			DeviceAdapter deviceAdapter = new DeviceAdapter(deviceList,
					getActivity());
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
			deviceList.add(new Device(4, "Device4", "40", "400"));
			deviceList.add(new Device(5, "Device5", "50", "500"));
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
		}
	}
	
	

