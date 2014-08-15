package com.zxs.chart;

import android.app.Activity;
import android.os.Bundle;

import com.zxs.view.ChartView;

public class MainActivity extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ChartView chartview=new ChartView(this);
		setContentView(chartview);
		//setContentView(R.layout.activity_main);
	}

	
}
