package com.project.devicemanager;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FileArrayAdapter extends ArrayAdapter<Item> {

	private Context con;
	private int id;
	private List<Item> items;
	ActionMode actionMode;

	public FileArrayAdapter(Context context, int textViewResourceId,
			List<Item> objects) {
		super(context, textViewResourceId, objects);

		this.con = context;
		this.id = textViewResourceId;
		this.items = objects;
	}

	public Item getItem(int possition) {
		return items.get(possition);
	}

	ArrayList<Item> getCheckedItemsList() {
		ArrayList<Item> checkedList = new ArrayList<Item>();
		for (Item p : items) {
			if (p.check)
				checkedList.add(p);
		}
		return checkedList;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) con
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(id, null);
		}
		final Item item = getItem(position);

		if (item != null) {
			CheckBox checkBox = (CheckBox) view
					.findViewById(R.id.file_item_check);
			TextView nameText = (TextView) view
					.findViewById(R.id.file_item_name_text);
			TextView dateText = (TextView) view
					.findViewById(R.id.file_item_date_text);
			ImageView imageIcon = (ImageView) view
					.findViewById(R.id.file_item_fd_icon);

			if (dateText != null) {
				dateText.setText(item.getDate());
			}

			String uri = "drawable/" + item.getImage();
			int imageResource = con.getResources().getIdentifier(uri, null,
					con.getPackageName());

			Drawable image = view.getResources().getDrawable(imageResource);
			imageIcon.setImageDrawable(image);
			if (item.getImage().equals("file_icon")) {
				if (item.getName().contains(".jpg")
						|| item.getName().contains(".png")) {
					Bitmap bmp = BitmapFactory.decodeFile(getItem(position)
							.getPath());
					imageIcon.setImageBitmap(bmp);
					imageIcon.setLayoutParams(new RelativeLayout.LayoutParams(
							60, 60));
				}
			}

			GridView gridView = FileChooserActivity.gridView;
			int colWidth = gridView.getColumnWidth();
			String uName = item.getName();
			int uNameLength = uName.length();
			int nLength = 0;

			if (nameText != null) {
				nameText.setText(item.getName());
				if (uNameLength > 10) {
					if (gridView.getNumColumns() > 1) {
						if (colWidth > 400) {
							nLength = uNameLength - uNameLength / 3;
							uName = uName.substring(0, nLength) + "..";
						} else {
							nLength = uNameLength / 2;
							uName = uName.substring(0, nLength) + "..";
						}
					} else {
						if (uNameLength > 20) {
							nLength = uNameLength - uNameLength / 3;
							uName = uName.substring(0, nLength) + "..";
						}
					}

				}
				if (colWidth < 400 && uNameLength >= 8) {
					uName = uName.substring(0, 6) + "..";
				}
				nameText.setText(uName);
				nameText.invalidate();
			}

			if (checkBox != null) {
				if (!item.getCheckVisible()) {
					checkBox.setVisibility(View.GONE);
				}
				checkBox.setClickable(true);
				checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						getItem((Integer) buttonView.getTag()).check = isChecked;
					}
				});

				checkBox.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
					}
				});
				checkBox.setTag(position);
				checkBox.setChecked(item.check);
			}
		}
		return view;
	}

}
