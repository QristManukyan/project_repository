package com.project.devicemeneger;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


	
	public class RecentDevicesFragment extends Fragment {

		static DeviceAdapter deviceAdapter;
		ListView listView;
		static List<Device> deviceList = new ArrayList<Device>();
		private static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			init();
		};

		public static RecentDevicesFragment newInstance(int sectionNumber) {
			RecentDevicesFragment fragment = new RecentDevicesFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public RecentDevicesFragment() {
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
		
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
		}
	}
	
	

