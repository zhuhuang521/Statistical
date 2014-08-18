package com.zxs.chart;

import com.zxs.view.InstantCurveView;
import com.zxs.view.InstantStatisticsFactory;

import android.app.Activity;
import android.os.Bundle;

public class CurveActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		InstantStatisticsFactory data=new InstantStatisticsFactory(this);
		InstantCurveView view=data.getnoticecurveview();
		setContentView(view);
	}

	
}
