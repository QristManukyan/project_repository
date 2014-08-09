package com.project.devicemanager;

import java.util.ArrayList;
import java.util.List;
import com.project.devicemanager.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class RecentDevicesFragment extends Fragment {

	static MySQLiteHelper datasource;
	public static DeviceAdapter recentDeviceAdapter;

	public static ListView listView;
	public static List<Device> recentDeviceList = new ArrayList<Device>();
	private static final String ARG_SECTION_NUMBER = "section_number";
	static final String SAVE_PAGE_NUMBER = "save_page_number";
	int pageNumber;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		
		datasource = new MySQLiteHelper(getActivity(),"RECENTDEVICESBASE.db");
		datasource.getWritableDatabase();
		List<Device> values = datasource.getAllDevices();
		View rootView = inflater.inflate(R.layout.fragment_main, container,	false);
		listView = (ListView) rootView.findViewById(R.id.device_list_view);
		recentDeviceAdapter = new DeviceAdapter(values, getActivity());
		recentDeviceAdapter.notifyDataSetChanged();
		listView.setAdapter(recentDeviceAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
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
	 @Override
	  public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    outState.putInt(SAVE_PAGE_NUMBER, pageNumber);
	  }
	  
	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
