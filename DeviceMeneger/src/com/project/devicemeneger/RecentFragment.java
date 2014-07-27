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

public class RecentFragment extends Fragment {
	

	static MySQLiteHelper datasource;
	public static DeviceAdapter recentDeviceAdapter;

	public static ListView listView;
	public static List<Device> recentDeviceList = new ArrayList<Device>();
	private static final String ARG_SECTION_NUMBER = "section_number2";

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	};

	public static RecentFragment newInstance(int sectionNumber) {
		RecentFragment fragment = new RecentFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public RecentFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
//		String pageTitle = getActivity().getActionBar().getSelectedTab().getText()
//		.toString();
		datasource = new MySQLiteHelper(getActivity());
		datasource.getWritableDatabase();
		List<Device> values = datasource.getAllDevices();
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		listView = (ListView) rootView.findViewById(R.id.device_list_view);
		recentDeviceAdapter = new DeviceAdapter(values, getActivity());
		listView.setAdapter(recentDeviceAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				((InfoListener) getActivity()).onChangeInfo(recentDeviceList.get(
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
