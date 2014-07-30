package com.project.devicemeneger;

import java.util.Random;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class AddDialogFragment extends DialogFragment{
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View  dialogView = inflater.inflate(R.layout.dialog, null);
		builder.setView(dialogView);
		builder.setTitle("Add Device");

		final int deviceRandomOwner = new Random().nextInt(100);
		final int deviceRandomIp = new Random().nextInt(200);
		
		final EditText editName = (EditText) dialogView.findViewById(R.id.dialog_edit_name);
		
		EditText editOwner = (EditText) dialogView.findViewById(R.id.dialog_edit_owner);
		editOwner.setText(Integer.toString(deviceRandomOwner));
		
		EditText editIp = (EditText) dialogView.findViewById(R.id.dialog_edit_ip);
		editIp.setText(Integer.toString(deviceRandomIp));
		
		try
		{
			EditText editId = (EditText) dialogView.findViewById(R.id.dialog_edit_id);
			int deviceId = Integer.parseInt(editId.getText().toString());	
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		 
		
		
		builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				if(which == dialog.BUTTON_NEGATIVE){
					dialog.dismiss();
				}
								
			}
		});
		builder.setPositiveButton(R.string.dialog_add_new, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				String pageTitle = getActivity().getActionBar().getSelectedTab().getText().toString();
				String deviceName = editName.getText().toString();
				 Device crateDevice = new Device(deviceName, deviceRandomOwner, deviceRandomIp);
				
				//int deviceId = Integer.parseInt(editId.getText().toString());
				 
				if (pageTitle.equals("My Devices")) {
					DevicesFragment.deviceList.add(crateDevice);
					DevicesFragment.deviceAdapter.add(crateDevice);
					DevicesFragment.deviceAdapter.notifyDataSetChanged();
					DevicesFragment.datasource.addDevice(crateDevice);

				}
				if (pageTitle.equals("Recent")) {
					RecentFragment.recentDeviceList.add(crateDevice);
					RecentFragment.recentDeviceAdapter.add(crateDevice);
					RecentFragment.recentDeviceAdapter.notifyDataSetChanged();
					RecentFragment.datasource.addDevice(crateDevice);
				}
				if (pageTitle.equals("More")) {
					MoreFragment.moreDeviceList.add(crateDevice);
					MoreFragment.moredeviceAdapter.add(crateDevice);
					MoreFragment.moredeviceAdapter.notifyDataSetChanged();
					MoreFragment.datasource.addDevice(crateDevice);
				}
				
				
			}
		});
		return builder.create();
	}
		
}
