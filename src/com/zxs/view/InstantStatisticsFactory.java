/**
 * @author zxs
 * @since 2014-2-23
 * @use 对活动内容进行分析创建列表图新****/
package com.zxs.view;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;

public class InstantStatisticsFactory {

	private Context mContext;
	public InstantStatisticsFactory(Context context)
	{
		this.mContext=context;
	}
	public InstantCurveView getnoticecurveview()
	{
		InstantCurveView instantcurveview=new InstantCurveView(mContext,getinstantdata());
		return instantcurveview;
	}
	private ArrayList<HashMap<String,Integer>> getinstantdata()
	{
		ArrayList<HashMap<String, Integer>> datalist=new ArrayList<HashMap<String,Integer>>();
		HashMap<String, Integer> map1=new HashMap<String, Integer>();
		map1.put("type", 0);
		map1.put("pointx", 50);
		map1.put("pointy", 450);
		map1.put("radius", 20);
		datalist.add(map1);
		
		HashMap<String, Integer> map2=new HashMap<String, Integer>();
		map2.put("type", 1);
		map2.put("beginx", 50);
		map2.put("beginy", 450);
		map2.put("endx", 350);
		map2.put("endy", 50);
		datalist.add(map2);
		
		HashMap<String, Integer> map3=new HashMap<String, Integer>();
		map3.put("type", 0);
		map3.put("pointx", 350);
		map3.put("pointy", 50);
		map3.put("radius", 20);
		datalist.add(map3);
		
		HashMap<String, Integer> map4=new HashMap<String, Integer>();
		map4.put("type", 1);
		map4.put("beginx", 350);
		map4.put("beginy", 50);
		map4.put("endx", 750);
		map4.put("endy", 450);
		datalist.add(map4);
		
		HashMap<String, Integer> map5=new HashMap<String, Integer>();
		map5.put("type", 0);
		map5.put("pointx", 750);
		map5.put("pointy", 450);
		map5.put("radius", 20);
		datalist.add(map5);
		return datalist;
	}
}
