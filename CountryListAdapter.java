package com.example.umesh.countrylistview;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CountryListAdapter extends BaseAdapter {

	Context context;
	ArrayList<CountryListItems> countryLists;
	ArrayList<StateListItems> stateLists;
	String cnt;
	MyUtility myUtility;
	public CountryListAdapter(Context context, ArrayList<CountryListItems> list) {

		this.context = context;
		countryLists = list;
		myUtility=new MyUtility(context);
	}
	@Override
	public int getCount() {

		return countryLists.size();
	}

	@Override
	public Object getItem(int position) {

		return countryLists.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {

		final CountryListItems countryListItems = countryLists.get(position);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.activity_list_display, null);

		}
		TextView txtCountry = (TextView) convertView.findViewById(R.id.txtCountry);
		txtCountry.setText(countryListItems.getName());
		final int id=countryListItems.getId();
		final String name=countryListItems.getName();
		Button btnDel=(Button)convertView.findViewById(R.id.btnDelete);
		btnDel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				deleteCountry(id);
				myUtility.selectCountry(countryLists);
				notifyDataSetChanged();
				DemoClass d=new DemoClass((IDemoInterface) context);
				d.onDelete();
			}
		});
		Button btnEdit=(Button)convertView.findViewById(R.id.btnEdit);
		btnEdit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				View parentRow = (View) v.getParent();
				ListView listView = (ListView) parentRow.getParent();
				final int position = listView.getPositionForView(parentRow);
				inputD(id, name);
			}
		});
		return convertView;
	}
	private void deleteCountry(int position)
	{

		String query = "DELETE FROM COUNTRY WHERE id="+position;
		SqlHandler sqlHandler = new SqlHandler(context);
		sqlHandler.executeQuery(query);
		String queryState = "DELETE FROM STATE WHERE country_id="+position;
		sqlHandler.executeQuery(queryState);
	}
	public void inputD(final int id,String name) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("COUNTRY");
		builder.setMessage("Enter Country Name:");
		final EditText input = new EditText(context);
		input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
		input.setText(name.toCharArray(), 0, name.length());
		builder.setView(input);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String val = input.getText().toString();
				String query = "UPDATE COUNTRY SET country_name='" + val + "' WHERE ID=" + id;
				SqlHandler sqlHandler = new SqlHandler(context);
				sqlHandler.executeQuery(query);
				myUtility.selectCountry(countryLists);
				notifyDataSetChanged();
			}
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		builder.show();
	}

}
