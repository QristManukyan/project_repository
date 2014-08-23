package com.project.devicemanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FileChooserActivity extends ListActivity  {
	private File currentDir;
	private FileArrayAdapter fileAdapter;
	private boolean enabled;
	
	int menuInfoPosition;
	TextView titleTextView;
	ListView listView;
	View wantedView;
	CheckBox checkBox;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_view_layout);
		titleTextView = (TextView) findViewById(R.id.file_view_path_text);
		
		listView = (ListView) findViewById(android.R.id.list);
		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	
		// Set up the action bar.
				final ActionBar actionBar = getActionBar();
				actionBar.setHomeButtonEnabled(true);
				actionBar.setDisplayHomeAsUpEnabled(true);
				actionBar.setDisplayUseLogoEnabled(false);
				actionBar.setDisplayShowHomeEnabled(false);
				actionBar.setTitle("Files");
		
		registerForContextMenu(this.getListView());
		currentDir = new File("/sdcard/");
		createFile(currentDir);
		
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo);
	    if (view.getId() == this.getListView().getId()) {
	    	MenuInflater inflater = getMenuInflater();
	    	inflater.inflate(R.menu.context_menu, menu);
	    	if (!enabled){
	    		menu.getItem(3).setEnabled(false);
	    	}
	    }
	}
	
	//TODO	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.copy :
			enabled = true;
//			File source = new File("/sdcard/Alarms");
//		    File dest = new File("/sdcard/DCIM");
//			try {
//				copyFileUsingFileChannels (source, dest);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			break;
		case R.id.move :
			enabled = true;
			// 
			break;
		case R.id.delete :
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
			menuInfoPosition = info.position;
			fileAdapter.remove(fileAdapter.getItem(menuInfoPosition));
			fileAdapter.notifyDataSetChanged();
			break;
		case R.id.paste :
			enabled = false;
			// 
			break;
		}
		return true;
	}

	public static void copyFileUsingFileChannels(File source, File dest)
	
            throws IOException {

        FileChannel inputChannel = null;

        FileChannel outputChannel = null;

        try {

            inputChannel = new FileInputStream(source).getChannel();

            outputChannel = new FileOutputStream(dest).getChannel();

            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());

        } finally {

            inputChannel.close();

            outputChannel.close();

        }
        System.out.println("inputChannel  "+inputChannel);
        System.out.println("outputChannel  " +outputChannel);
    }
	
	private void createFile(File file) {
		File[] fileDirectores = file.listFiles();
		titleTextView.setText(file.getPath());
		List<Item> directory = new ArrayList<Item>();
		List<Item> files = new ArrayList<Item>();
		try {
			for (File fileIndex : fileDirectores) {

				Date lastModDate = new Date(fileIndex.lastModified());
				DateFormat formater = DateFormat.getDateTimeInstance();

				String date_modify = formater.format(lastModDate);
				if (fileIndex.isDirectory()) {

					File[] fileBuf = fileIndex.listFiles();
					int buf = 0;
					if (fileBuf != null) {
						buf = fileBuf.length;
					} else
						buf = 0;

					String itemNumbers = String.valueOf(buf);
					
					if (buf == 0) {
						itemNumbers = itemNumbers + "item";
					} else
						itemNumbers = itemNumbers + "items";

					directory.add(new Item(fileIndex.getName(), itemNumbers,
							date_modify, fileIndex.getAbsolutePath(),
							"directory_icon", true, false));
				} else {
					
					files.add(new Item(fileIndex.getName(), fileIndex.length()
							+ " Byte", date_modify,
							fileIndex.getAbsolutePath(), "file_icon", true, false));
				}
			}
		} catch (Exception e) {

		}
		Collections.sort(directory);
		Collections.sort(files);
		directory.addAll(files);
		 if(!file.getName().equalsIgnoreCase("sdcard"))
			 directory.add(0,new Item("...","","",file.getParent(),"directory_up", false, false));
		 
		 fileAdapter = new FileArrayAdapter(FileChooserActivity.this,R.layout.file_item_view,directory);
		 this.setListAdapter(fileAdapter); 
		 
		 
		 
	}
	
	
	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id) {
		super.onListItemClick(listView, view, position, id);
		Item item = fileAdapter.getItem(position);
		if (item.getImage().equalsIgnoreCase("directory_icon")||item.getImage().equalsIgnoreCase("directory_up")) {
			currentDir = new File (item.getPath());
			createFile(currentDir);
		}
		else {
			onFileClick (item);
		}
	}
	

	private void onFileClick(Item item) {
		
		Toast.makeText(this, "Folder Clicked: "+ currentDir, Toast.LENGTH_SHORT).show();
		Intent intent = new Intent();
        intent.putExtra("GetPath",currentDir.toString());
        intent.putExtra("GetFileName",item.getName());
        setResult(RESULT_OK, intent);
        finish();
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.context_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
			Intent intent = new Intent(this, MainActivity.class);
			switch (item.getItemId()) {
			case android.R.id.home:
				startActivity(intent);
				break;
			case R.id.copy:
				copyFile();
				break;
			case R.id.move:
				moveFile();
				break;
			case R.id.delete:
				deleteFile ();
			break;
			case R.id.paste:
				pasteFile();
				break;
			}
			setResult(RESULT_OK, intent);
			return super.onOptionsItemSelected(item);
	}

		private void pasteFile() {
			// TODO Auto-generated method stub
			
		}

		private void copyFile() {
			// TODO Auto-generated method stub
			
		}

		private void moveFile() {
			// TODO Auto-generated method stub
			
		}

		private void deleteFile() {
			ArrayList<Item>  list = fileAdapter.getCheckedItemsList();
			int position = 0;
		    for (Item p : list) {
		      position = fileAdapter.getPosition(p);
		      getListView().setItemChecked(position, true);
		      SparseBooleanArray checkedItemPositions = getListView().getCheckedItemPositions();
		      if (checkedItemPositions.get(position)){
		    	  fileAdapter.remove(fileAdapter.getItem(position));
		      }
		    }
    	    fileAdapter.notifyDataSetChanged();
			
		}
	}




