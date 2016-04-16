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

public class StateListAdapter extends BaseAdapter {

	Context context;
	ArrayList<StateListItems> stateLists;
	String cnt;
	MyUtility myUtility;
	public StateListAdapter(Context context, ArrayList<StateListItems> list) {

		this.context = context;
		stateLists = list;
		myUtility=new MyUtility(context);
	}

	@Override
	public int getCount() {

		return stateLists.size();
	}

	@Override
	public Object getItem(int position) {

		return stateLists.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {

		final StateListItems StateListItems = stateLists.get(position);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.activity_list_state, null);

		}
		TextView txtState = (TextView) convertView.findViewById(R.id.txtState);
		final int id=StateListItems.getId();
		final int cid=StateListItems.getCid();
		txtState.setText(StateListItems.getName());
		final String name=StateListItems.getName();
		Button btnDel=(Button)convertView.findViewById(R.id.btnStateDelete);
		btnDel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				deleteState(id,cid);
				myUtility.selectState(stateLists,cid);
				notifyDataSetChanged();
			}
		});
		Button btnStateEdit=(Button)convertView.findViewById(R.id.btnStateEdit);
		btnStateEdit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				View parentRow = (View) v.getParent();
				ListView listView = (ListView) parentRow.getParent();
				final int position = listView.getPositionForView(parentRow);
				inputD(id, name,cid);
			}
		});
		return convertView;
	}
	private void deleteState(int position,int cid)
	{

		String query = "DELETE FROM STATE WHERE id="+position;
		SqlHandler sqlHandler = new SqlHandler(context);
		sqlHandler.executeQuery(query);
	}
	public void inputD(final int id,String name, final int cid) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("STATE");
		builder.setMessage("Enter State Name:");
		final EditText input = new EditText(context);
		input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
		input.setText(name.toCharArray(), 0, name.length());
		builder.setView(input);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String val = input.getText().toString();
				String query = "UPDATE STATE SET state_name='" + val + "' WHERE ID=" + id;
				SqlHandler sqlHandler = new SqlHandler(context);
				sqlHandler.executeQuery(query);
				myUtility.selectState(stateLists,cid);
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
