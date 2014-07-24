package com.project.devicemeneger;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DeviceAdapter extends ArrayAdapter<Device> {

	private List<Device> deviceList;
	private Context context;

	public DeviceAdapter(List<Device> deviceList, Context context) {
		super(context, R.layout.my_devices_item, deviceList);
		this.context = context;
		this.deviceList = deviceList;
	}

	@Override
	public int getCount() {
		return deviceList.size();
	}

	@Override
	public Device getItem(int position) {
		return deviceList.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return deviceList.get(position).hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null){
			
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.my_devices_item, null);
			holder = new ViewHolder();
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder)convertView.getTag();
		}
		
		if (holder.deviceNameTextView == null){
			
			holder.deviceNameTextView = (TextView) convertView.findViewById(R.id.my_devices_item_name);
			holder.deviceNameTextView.setText(deviceList.get(position).getName());
		}
		if (holder.deviceOptionsSpinnerView == null){
			 holder.deviceOptionsSpinnerView = (Spinner) convertView.findViewById(R.id.my_devices_item_options_spinner);
			
			final ArrayAdapter<CharSequence> adapter = ArrayAdapter
					.createFromResource(getContext(),
							R.array.item_options_array,
							android.R.layout.simple_spinner_item);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			holder.deviceOptionsSpinnerView.setAdapter(adapter);
			holder.deviceOptionsSpinnerView.setPrompt("title");
			holder.deviceOptionsSpinnerView.setOnItemSelectedListener(
		                new AdapterView.OnItemSelectedListener() {
		                    public void onItemSelected(
		                            AdapterView<?> parent, View view, int position, long id) {
		                    
		                    	
		                        String optionName = adapter.getItem(position).toString();
		                        switch (optionName){
		                        case "Send":
		                        //	Toast.makeText(context, optionName +" is Clicked",Toast.LENGTH_LONG ).show();
		                        	break;
		                        case "Browse":
		                        	Toast.makeText(context, optionName +" is Clicked",Toast.LENGTH_LONG ).show();
		                        	break;
		                        case "Info":
		                        	Toast.makeText(context, optionName +" is Clicked",Toast.LENGTH_LONG ).show();
		                        	break;
		                        case "Disconect":
		                        	Toast.makeText(context, optionName +" is Clicked",Toast.LENGTH_LONG ).show();
		                        	break;
		                        case "Remove":
		                        	Toast.makeText(context, optionName +" is Clicked",Toast.LENGTH_LONG ).show();
		                        	int viewPosition = DevicesFragment.listView.getSelectedItemPosition();
		                        	//deleteItem(viewPosition);
		                        	DevicesFragment.deviceAdapter.clear();
		                        	DevicesFragment.deviceAdapter.notifyDataSetChanged();
		                        	break;
		                        }
		                    }

		                    public void onNothingSelected(AdapterView<?> parent) {
		                    }
		                });			
		}
		return convertView;

	}
	public void deleteItem(int position){
		deviceList.remove(position);
	}

	private class ViewHolder {
		public TextView deviceNameTextView = null;
		public Spinner deviceOptionsSpinnerView = null;
	}


}
