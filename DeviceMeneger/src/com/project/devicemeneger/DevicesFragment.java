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

public class DevicesFragment extends Fragment {
	static DevicesDataSource datasource;

	public static DeviceAdapter deviceAdapter;
	public static DeviceAdapter deviceRecentAdapter;
	public static DeviceAdapter deviceMoreAdapter;

	public static ListView listView;
	public static List<Device> deviceList = new ArrayList<Device>();
	private static final String ARG_SECTION_NUMBER = "section_number";

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	};

	public static DevicesFragment newInstance(int sectionNumber) {
		DevicesFragment fragment = new DevicesFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public DevicesFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		datasource = new DevicesDataSource(getActivity());
		datasource.open();
		List<Device> values = datasource.getAllInfos();
		List<Device> valuesRecent = datasource.getResentInfos();
		List<Device> valuesMore = datasource.getAllInfos();
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		listView = (ListView) rootView.findViewById(R.id.device_list_view);
		deviceAdapter = new DeviceAdapter(values, getActivity());
		listView.setAdapter(deviceAdapter);
//TODO
/*		String pageTitle = getActivity().getActionBar().getSelectedTab().getText()
				.toString();

		switch (pageTitle){
		case "My Devices":
			System.out.println("pageTitle"+pageTitle);
			deviceAdapter = new DeviceAdapter(values, getActivity());
			listView.setAdapter(deviceAdapter);
			break;
		case "Recent":
			System.out.println("pageTitle"+pageTitle);
			deviceRecentAdapter = new DeviceAdapter(valuesRecent, getActivity());
			listView.setAdapter(deviceRecentAdapter);
			break;
		case "More":
			System.out.println("pageTitle"+pageTitle);
			deviceMoreAdapter = new DeviceAdapter(valuesMore, getActivity());
			listView.setAdapter(deviceMoreAdapter);
			break;
		}
	*/	
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				((InfoListener) getActivity()).onChangeInfo(deviceList.get(
						position).getName());
			}
		});
		setHasOptionsMenu(true);
		return rootView;
	}

	public interface InfoListener {
		public void onChangeInfo(String info);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}

	// public static void deleteListItem (){
	// int position = listView.getSelectedItemPosition();
	// listView.removeViews(listView.getSelectedItemPosition(), 1);
	// }
	//

}
