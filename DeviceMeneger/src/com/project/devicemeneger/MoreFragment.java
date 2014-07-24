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

public class MoreFragment extends Fragment {
	static DevicesDataSource datasource;

	public static DeviceAdapter deviceAdapter;

	public static ListView listView;
	public static List<Device> deviceList = new ArrayList<Device>();
	private static final String ARG_SECTION_NUMBER = "section_number";

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	};

	public static MoreFragment newInstance(int sectionNumber) {
		MoreFragment fragment = new MoreFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public MoreFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
//		String pageTitle = getActivity().getActionBar().getSelectedTab().getText()
//		.toString();
		datasource = new DevicesDataSource(getActivity());
		datasource.open();
		List<Device> values = datasource.getMoreInfos();
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		listView = (ListView) rootView.findViewById(R.id.device_list_view);
		deviceAdapter = new DeviceAdapter(values, getActivity());
		listView.setAdapter(deviceAdapter);
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
