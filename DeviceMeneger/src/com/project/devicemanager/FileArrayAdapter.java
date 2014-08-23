package com.project.devicemanager;


import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
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
	
	 
	 ArrayList<Item> getCheckedItemsList() {
		    ArrayList<Item> checkedList = new ArrayList<Item>();
		    for (Item p : items) {
		      // если в корзине
		      if (p.check)
		    	  checkedList.add(p);
		    }
		    return checkedList;
		  }
	 
	 
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null){
			LayoutInflater inflater = (LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(id, null);
		}
		final Item item = getItem(position);
		
		
		if (item != null) {
			//TextView dataText = (TextView) view.findViewById(R.id.file_item_data_text);
			CheckBox checkBox = (CheckBox) view.findViewById(R.id.file_item_check);
			TextView nameText = (TextView) view.findViewById(R.id.file_item_name_text);
			TextView dateText = (TextView) view.findViewById(R.id.file_item_date_text);
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
			}
			if(checkBox != null) {
				if (!item.getCheckVisible()) 
					checkBox.setVisibility(View.GONE);
				checkBox.setClickable(true);
				checkBox.setOnCheckedChangeListener(myCheckChangList);
//				checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//					
//					@Override
//					public void onCheckedChanged(CompoundButton buttonView, boolean arg1) {
//						CheckBox checkBox = (CheckBox) buttonView;
//						boolean isChecked = checkBox.isChecked();
//						if  (isChecked){
//							
//							getItem((Integer) buttonView.getTag()).check = isChecked;
//							System.out.println("check box is checked");
//							
//						}else {
//							System.out.println("check box is not  checked");
//						}
//					}
//				});
				checkBox.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						//
					}
				});
				checkBox.setTag(position);
				checkBox.setChecked(item.check);
			}
		}
		
		return view;
	}
	
	OnCheckedChangeListener  myCheckChangList = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

			getItem((Integer) buttonView.getTag()).check = isChecked;
			
		}
	};

}
