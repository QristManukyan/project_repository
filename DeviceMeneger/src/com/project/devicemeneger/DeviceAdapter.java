package com.project.devicemeneger;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
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
			 holder.deviceOptionsMore = (ViewGroup) convertView.findViewById(R.id.my_devices_item_options_more);
			 holder.deviceOptionsMore.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {

					PopupMenu popupMenu = new PopupMenu(context, holder.deviceOptionsMore);
					popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
					
					popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
						
						@Override
						public boolean onMenuItemClick(MenuItem item) {
							String itemTitle = item.getTitle().toString();
							switch (itemTitle){
							case "Send":
								Toast.makeText(context, "Send is clicked", Toast.LENGTH_LONG).show();
								break;
							case "Browse":
								Toast.makeText(context, "Browse is clicked", Toast.LENGTH_LONG).show();
								break;
							case "Info":
								Toast.makeText(context, "Info is clicked", Toast.LENGTH_LONG).show();
								break;
							case "Disconect":
								Toast.makeText(context, "Disconect is clicked", Toast.LENGTH_LONG).show();
								break;
							case "Remove":
								Toast.makeText(context, "Remove is clicked", Toast.LENGTH_LONG).show();
								break;
							}
							return true;
						}
					});
					popupMenu.show();
				}
			});
		return convertView;

	}

	private class ViewHolder {
		public TextView deviceNameTextView = null;
		public ViewGroup deviceOptionsMore = null;
	}

}


