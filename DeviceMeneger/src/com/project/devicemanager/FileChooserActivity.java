package com.project.devicemanager;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FileChooserActivity extends Activity {
	private File currentDir;
	private FileArrayAdapter fileAdapter;
	private boolean enabled;

	int menuInfoPosition;
	TextView titleTextView;
	static GridView gridView;
	View wantedView;
	CheckBox checkBox;
	File globalFileDirectory;
	Stack<File> pathStack = new Stack<File>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_view_layout);

		gridView = (GridView) findViewById(R.id.file_view_grid);
		gridView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		gridView.setOnItemClickListener(listener);

		titleTextView = (TextView) findViewById(R.id.file_view_path_text);

		ImageButton listButton = (ImageButton) findViewById(R.id.file_list_view_btn);
		listButton.setOnClickListener(listenerbtn);
		ImageButton gridButton = (ImageButton) findViewById(R.id.file_grid_view_btn);
		gridButton.setOnClickListener(listenerbtn);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setTitle("Files");

		registerForContextMenu(gridView);
		currentDir = new File("/sdcard/");
		createFile(currentDir);
	}

	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long id) {
			Item item = fileAdapter.getItem(position);
			if (item.getImage().equalsIgnoreCase("directory_icon")
					|| item.getImage().equalsIgnoreCase("directory_up")) {
				currentDir = new File(item.getPath());
				createFile(currentDir);
			} else {
				onFileClick(item);
			}

		}
	};

	OnClickListener listenerbtn = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.file_list_view_btn:
				gridView.setNumColumns(1);
				currentDir = new File("/sdcard/");
				createFile(currentDir);
				break;
			case R.id.file_grid_view_btn:
				gridView.setNumColumns(GridView.AUTO_FIT);
				break;
			}
			;
		}
	};

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo);
		if (view.getId() == gridView.getId()) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.context_menu, menu);
			if (!enabled) {
				menu.getItem(3).setEnabled(false);
			}
		}
	}

	// TODO
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		menuInfoPosition = info.position;
		switch (item.getItemId()) {

		case R.id.copy:
			enabled = true;
			pathStack.push(new File(getPath(menuInfoPosition)));
			break;
		case R.id.move:
			enabled = true;
			//
			break;
		case R.id.delete:

			fileAdapter.remove(fileAdapter.getItem(menuInfoPosition));
			File file = new File(fileAdapter.getItem(menuInfoPosition)
					.getPath());
			file.delete();
			fileAdapter.notifyDataSetChanged();
			break;
		case R.id.paste:
			enabled = false;
			File copyPath = pathStack.pop();
			File pastePath = new File(getPath(menuInfoPosition));

			try {
				copyDirectory(copyPath, pastePath);

			} catch (IOException e) {
				e.printStackTrace();
			}
			// TODO
			createFile(pastePath);
			fileAdapter.notifyDataSetChanged();
			break;
		}
		return true;
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
			// copyFile();
			break;
		case R.id.move:
			moveFile();
			break;
		case R.id.delete:
			deleteFile();
			break;
		case R.id.paste:
			pasteFile();
			break;
		}
		setResult(RESULT_OK, intent);
		return super.onOptionsItemSelected(item);
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
					//globalFileDirectory = fileIndex.getAbsoluteFile();

				} else {

					files.add(new Item(fileIndex.getName(), fileIndex.length()
							+ " Byte", date_modify,
							fileIndex.getAbsolutePath(), "file_icon", true,
							false));
				}
			}
		} catch (Exception e) {

		}
		Collections.sort(directory);
		Collections.sort(files);
		directory.addAll(files);
		if (!file.getName().equalsIgnoreCase("sdcard"))
			directory.add(0, new Item("...", "", "", file.getParent(),
					"directory_up", false, false));
		fileAdapter = new FileArrayAdapter(FileChooserActivity.this,
				R.layout.file_item_view, directory);
		gridView.setAdapter(fileAdapter);

	}

	private void onFileClick(Item item) {

		Intent intent = new Intent();
		intent.putExtra("GetPath", currentDir.toString());
		intent.putExtra("GetFileName", item.getName());
		setResult(RESULT_OK, intent);
		item.check = true;
		fileAdapter.notifyDataSetChanged();
	}

	private void pasteFile() {
		// TODO Auto-generated method stub

	}

	private void moveFile() {
		// TODO Auto-generated method stub

	}

	private void deleteFile() {
		ArrayList<Item> list = fileAdapter.getCheckedItemsList();
		int position = 0;
		for (Item p : list) {
			position = fileAdapter.getPosition(p);
			gridView.setItemChecked(position, true);
			SparseBooleanArray checkedItemPositions = gridView
					.getCheckedItemPositions();
			if (checkedItemPositions.get(position)) {
				fileAdapter.remove(fileAdapter.getItem(position));
				File file = new File(p.getPath());
				file.delete();
			}
		}
		fileAdapter.notifyDataSetChanged();
	}

	private String getPath(int index) {
		return fileAdapter.getItem(index).getPath();

	}

	public void copyDirectory(File sourceLocation, File targetLocation)
			throws IOException {

		if (sourceLocation.isDirectory()) {
			if (!targetLocation.exists()) {
				targetLocation.mkdir();
			}
			String[] children = sourceLocation.list();
			for (int i = 0; i < children.length; i++) {
				copyDirectory(new File(sourceLocation, children[i]), new File(
						targetLocation, children[i]));
			}
		} else {

			InputStream in = new FileInputStream(sourceLocation);
			OutputStream out = new FileOutputStream(targetLocation);

			// Copy the bits from instream to outstream
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}
	}
}
