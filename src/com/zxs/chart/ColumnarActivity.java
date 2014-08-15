package com.zxs.chart;

import java.util.ArrayList;

import com.zxs.view.ColumnarView;

import android.app.Activity;
import android.os.Bundle;

public class ColumnarActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ColumnarView view=new ColumnarView(this);
		ArrayList<Integer> list=new ArrayList<Integer>();
		list.add(6000);
		list.add(4500);
		list.add(5700);
		list.add(6800);
		list.add(2160);
		list.add(1400);
		list.add(1925);
		view.setListData(list);
		setContentView(view);
	}

	
}
