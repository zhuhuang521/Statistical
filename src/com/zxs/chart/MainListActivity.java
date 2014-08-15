package com.zxs.chart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainListActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainlistlayout);
		ListView listview=(ListView)findViewById(R.id.listview);
		listview.setOnItemClickListener(new ClickListener());
		listview.setAdapter(new ListAdapter());
	}
	private class ClickListener implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			switch(position)
			{
			case 0:
				intent.setClass(MainListActivity.this, MainActivity.class);
				break;
			case 1:
				intent.setClass(MainListActivity.this, ColumnarActivity.class);
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			}
			startActivity(intent);
		}
		
	}
	private class ListAdapter extends BaseAdapter
	{
		String listnames[]=new String[]{"饼图","柱状图","折线图","","",""};
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 5;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView textview=new TextView(MainListActivity.this);
			textview.setTextSize(25);
			textview.setText(listnames[position]);
			return textview;
		}
		
	}
	
}
