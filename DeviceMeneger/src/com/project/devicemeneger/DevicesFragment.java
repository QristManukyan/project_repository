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
	static MySQLiteHelper datasource;
	public static DeviceAdapter deviceAdapter;

	public static ListView listView;
	public static List<Device> deviceList = new ArrayList<Device>();
	public static final String ARG_SECTION_NUMBER = "section_number1";
	static final String SAVE_PAGE_NUMBER = "save_page_number";
	int pageNumber;

	
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
		datasource = new MySQLiteHelper(getActivity(),"DEVICESBASE.db");
		datasource.getWritableDatabase();
		List<Device> values = datasource.getAllDevices();
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		
		listView = (ListView) rootView.findViewById(R.id.device_list_view);
		deviceAdapter = new DeviceAdapter(values, getActivity());
		listView.setAdapter(deviceAdapter);

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
	  public void onDestroy() {
	    super.onDestroy();
	  }

}
