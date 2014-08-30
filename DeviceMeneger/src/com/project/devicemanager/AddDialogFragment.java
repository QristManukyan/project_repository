package com.project.devicemanager;
import java.util.Random;

import com.project.devicemanager.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class AddDialogFragment extends DialogFragment{
	
	private MySQLiteHelper myDatasourse = MyDevicesFragment.datasource;
	private MySQLiteHelper recentDatasourse = RecentDevicesFragment.datasource;
	private MySQLiteHelper moreDatasourse = MoreDevicesFragment.datasource;
	private int deviceRandomOwner = new Random().nextInt(100);
	private int deviceRandomIp = new Random().nextInt(200);
	private int pageIndex;
	
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View  dialogView = inflater.inflate(R.layout.add_dialog, null);
		builder.setView(dialogView);
		builder.setTitle("Add Device");
		pageIndex = getActivity().getActionBar().getSelectedTab().getPosition();
		int myDId = 1, moreDId = 1, recentDId = 1;
		
		final EditText editName = (EditText) dialogView.findViewById(R.id.dialog_edit_name);
		EditText editId = (EditText) dialogView.findViewById(R.id.dialog_edit_id);
		
		EditText editOwner = (EditText) dialogView.findViewById(R.id.dialog_edit_owner);
		editOwner.setText(Integer.toString(deviceRandomOwner));
	
		EditText editIp = (EditText) dialogView.findViewById(R.id.dialog_edit_ip);
		editIp.setText(Integer.toString(deviceRandomIp));

		if(myDatasourse.getDevicesCount() != 0 )
			myDId = myDatasourse.getNextId();

		if (recentDatasourse.getDevicesCount() != 0)
			recentDId = recentDatasourse.getNextId();
		
		if (moreDatasourse.getDevicesCount() !=0 )
			moreDId = moreDatasourse.getNextId();
		
		switch (pageIndex){
			case 0:
				editId.setText(Integer.toString(myDId));
				break;
			case 1:			
				editId.setText(Integer.toString(recentDId));
				break;
			case 2:			
				editId.setText(Integer.toString(moreDId));
				break;
		}
		
		builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				if(which == DialogInterface.BUTTON_NEGATIVE){
					dialog.dismiss();
				}
			}
		});
		
		builder.setPositiveButton(R.string.dialog_add_new, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
			
				String deviceName = editName.getText().toString();
				Device crateDevice = new Device(deviceName, deviceRandomOwner, deviceRandomIp);
				
				switch(pageIndex) {
				case 0:
					MyDevicesFragment.deviceList.add(crateDevice);
					MyDevicesFragment.deviceAdapter.add(crateDevice);
					MyDevicesFragment.deviceAdapter.notifyDataSetChanged();
					myDatasourse.addDevice(crateDevice);
					break;
				case 1:
					RecentDevicesFragment.recentDeviceList.add(crateDevice);
					RecentDevicesFragment.recentDeviceAdapter.add(crateDevice);
					RecentDevicesFragment.recentDeviceAdapter.notifyDataSetChanged();
					recentDatasourse.addDevice(crateDevice);
					break;
				case 2:
					MoreDevicesFragment.moreDeviceList.add(crateDevice);
					MoreDevicesFragment.moredeviceAdapter.add(crateDevice);
					MoreDevicesFragment.moredeviceAdapter.notifyDataSetChanged();
					moreDatasourse.addDevice(crateDevice);
					break;
				}
			}
		});
		return builder.create();
	}
		
}
