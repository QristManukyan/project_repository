package com.project.devicemanager;

import java.util.List;


import android.R.string;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class FileArrayAdapter extends ArrayAdapter<Item>{
	
	private Context con;
	private int id;
	private List<Item> items;
	
	public FileArrayAdapter (Context context, int textViewResourceId,List<Item> objects){
		super(context, textViewResourceId, objects);
		
		this.con = context;
		this.id = textViewResourceId;
		this.items = objects;
	}
	
	public Item getItem(int possition) {
		return items.get(possition);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null){
			LayoutInflater inflater = (LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(id, null);
		}
		
		
	
		
		final Item item = items.get(position);
		
		if (item != null) {
	//		CheckBox chekBox = (CheckBox) view.findViewById(R.id.file_item_check);
			TextView nameText = (TextView) view.findViewById(R.id.file_item_name_text);
			TextView dateText = (TextView) view.findViewById(R.id.file_item_date_text);
			//TextView dataText = (TextView) view.findViewById(R.id.file_item_data_text);
			ImageView imageIcon = (ImageView) view.findViewById(R.id.file_item_fd_icon);
			String uri = "drawable/"+ item.getImage();
			int imageResource = con.getResources().getIdentifier(uri, null, con.getPackageName());
			Drawable image = view.getResources().getDrawable(imageResource);
			
			imageIcon.setImageDrawable(image);
			if (nameText != null) {
				nameText.setText(item.getName());
			}
			if (dateText != null) {
				dateText.setText(item.getDate());
				System.out.println("date is =  "+item.getDate());
			}
			
//			if(dataText != null) {
//				dataText.setText(item.getData());
//			}
//			if(chekBox != null) {
//				chekBox.setClickable(true);
//				chekBox.setOnClickListener(new View.OnClickListener() {
//					
//					@Override
//					public void onClick(View view) {
//						CheckBox check = (CheckBox) view;
//						
//						System.out.println("checkBox is checked");
//						
//						System.out.println("item  "+ item.getName() +"path  "+item.getPath());
//					}
//				});
				
			}
//		}
		
		return view;
	}

}
