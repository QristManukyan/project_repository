//package com.project.devicemanager;
//import com.project.devicemanager.R;
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.app.DialogFragment;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.EditText;
//
//public class RenamDialogFragment extends DialogFragment{
//	
//	@Override
//	public Dialog onCreateDialog(Bundle savedInstanceState) {
//
//		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//		LayoutInflater inflater = getActivity().getLayoutInflater();
//		View  dialogView = inflater.inflate(R.layout.rename_dialog, null);
//		builder.setView(dialogView);
//		builder.setTitle("Rename File");
//		
//		final EditText editName = (EditText) dialogView.findViewById(R.id.dialog_edit_name);
//		editName.setText(getArguments().getString("name"));
//		builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				
//				if(which == DialogInterface.BUTTON_NEGATIVE){
//					dialog.dismiss();
//				}
//			}
//		});
//		
//		builder.setPositiveButton(R.string.dialog_rename_file, new DialogInterface.OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				System.out.println("this is onclick");
//				String fileName = editName.getText().toString();
//				FileChooserActivity activity = (FileChooserActivity) getActivity();
//				//activity.setNewName(fileName);
//			}
//		});
//		return builder.create();
//	}
//		
//}
//to activity
//	public void renameFile(int menuInfoPosition) {
//		final RenamDialogFragment dialog = new RenamDialogFragment();
//		dialog.show(getFragmentManager(), "rename");
//		Bundle args = new Bundle();
//		String name = fileAdapter.getItem(menuInfoPosition).getName();
//		args.putString("name", name);
//		dialog.setArguments(args);
//		
//		
//	}
