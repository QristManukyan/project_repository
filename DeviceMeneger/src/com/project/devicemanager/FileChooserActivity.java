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
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

public class FileChooserActivity extends ListActivity  {
	private static final int MENU_QUIT_ID = 1;
	private File currentDir;
	private FileArrayAdapter fileAdapter;
	private boolean enabled;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_view_layout);
		registerForContextMenu(this.getListView());
		currentDir = new File("/sdcard/");
		getFile(currentDir);
		enabled = false;
		
	}
	
	public void onClick (View view) {
		
		boolean checked = ((CheckBox) view).isChecked();
		switch (view.getId()) {
		case R.id.file_item_check:
			if(checked) {
				System.out.println("chekbox  is checked");
			}
			else {
				System.out.println("chekbox  is not checked");
			}
			break;
		default:
			break;
		}
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
		System.out.println("enabled== "+enabled);
		switch (item.getItemId()) {
		case R.id.copy :
		//	AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
			enabled = true;
			
			File source = new File("/sdcard/Alarms");
		    File dest = new File("/sdcard/DCIM");
			try {
				copyFileUsingFileChannels (source, dest);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case R.id.move :
			enabled = true;
			// 
			break;
		case R.id.delete :
			// 
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

	
	private void getFile(File file) {
		File[] fileDirectores = file.listFiles();
		//this.setTitle(file.getPath());
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
							"directory_icon"));
				} else {
					
					files.add(new Item(fileIndex.getName(), fileIndex.length()
							+ " Byte", date_modify,
							fileIndex.getAbsolutePath(), "file_icon"));
				}
			}
		} catch (Exception e) {

		}
		Collections.sort(directory);
		Collections.sort(files);
		directory.addAll(files);
		 if(!file.getName().equalsIgnoreCase("sdcard"))
			 directory.add(0,new Item("..","Parent Directory","",file.getParent(),"directory_up"));
		 fileAdapter = new FileArrayAdapter(FileChooserActivity.this,R.layout.file_item_view,directory);
		 this.setListAdapter(fileAdapter); 
	}
	
	
	
	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id) {
		super.onListItemClick(listView, view, position, id);
		Item item = fileAdapter.getItem(position);
		if (item.getImage().equalsIgnoreCase("directory_icon")||item.getImage().equalsIgnoreCase("directory_up")) {
			currentDir = new File (item.getPath());
			getFile(currentDir);
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
		//getMenuInflater().inflate(R.menu.main, menu)
		menu.addSubMenu(0, MENU_QUIT_ID, 0, getString(R.string.action_quit));
		return super.onCreateOptionsMenu(menu);
	}
	
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
	
			Intent intent = new Intent(this, MainActivity.class);
			switch (item.getItemId()) {
			
			case android.R.id.home:
				startActivity(intent);
				break;
			case MENU_QUIT_ID:
				finish();
			}
			setResult(RESULT_OK, intent);
			return super.onOptionsItemSelected(item);
		}
	}
