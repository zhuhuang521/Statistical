package com.zxs.view;

import java.util.ArrayList;
import java.util.HashMap;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;

public class InstantCurveView extends View implements ValueAnimator.AnimatorUpdateListener{

	//data
	private ArrayList<HashMap<String, Integer>> curvedata;
	private ArrayList<CurveLine> linelist;
	private ArrayList<CurvePoint> pointlist;
	private ArrayList<Animator> animlist;
	private int linenum;
	private int pointnum;
	private int datanum;
	private AnimatorSet curveanimator;
	public InstantCurveView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public InstantCurveView(Context context,ArrayList<HashMap<String, Integer>> data)
	{
		super(context);
		this.curvedata=data;
		initdata();
	}
	public InstantCurveView(Context context)
	{
		super(context);
	}
	public void setdata(ArrayList<HashMap<String, Integer>> data)
	{
		this.curvedata=data;
		initdata();
	}
	private void initdata()
	{
		linelist=new ArrayList<CurveLine>();
		pointlist=new ArrayList<CurvePoint>();
		datanum=curvedata.size();
		linenum=0;
		pointnum=0;
		for(int i=0;i<datanum;i++)
		{
			HashMap<String, Integer> hashmap=curvedata.get(i);
			if(hashmap.get("type")==0)
			{
				//type=0,point =1,line
				pointnum++;
				pointlist.add(initcurvePoint((float)hashmap.get("pointx"),(float)hashmap.get("pointy"),(float)hashmap.get("radius")));
			}
			else
			{
				linenum++;
				linelist.add(initCurveLine((float)hashmap.get("beginx"),(float)hashmap.get("beginy"),(float)hashmap.get("endx"),(float)hashmap.get("endy")));
			}
		}
		initCurveAnimation();
	}
	//init anim
	private void initCurveAnimation()
	{
		curveanimator=new AnimatorSet();
		animlist=new ArrayList<Animator>();
		for(int i=0;i<datanum;i++)
		{
			if(i%2==0)
			{
				//point
				CurvePoint curvepoint=pointlist.get(i/2);
				PropertyValuesHolder pvhalpha = PropertyValuesHolder.ofFloat("paintAlpha", 0,255);
				PropertyValuesHolder pvhradius = PropertyValuesHolder.ofFloat("radius", 0,curvepoint.getRadius()*3/2,curvepoint.getRadius());
				ObjectAnimator animpoint = ObjectAnimator.ofPropertyValuesHolder(curvepoint,pvhalpha, pvhradius).setDuration(130);
				animpoint.addUpdateListener(this);
				animlist.add(animpoint);
			}
			else
			{
				//line
				CurveLine curveline=linelist.get(i/2);
				PropertyValuesHolder pvhlineendx = PropertyValuesHolder.ofFloat("pointendX", curveline.getPointbeginX(),
	                        curveline.getPointendX());
				PropertyValuesHolder pvhalpha = PropertyValuesHolder.ofFloat("paintAlpha", 0,
                        255);
	            PropertyValuesHolder pvhlineendy = PropertyValuesHolder.ofFloat("pointendY", curveline.getPointbeginY(),
	                        curveline.getPointendY());
	            ObjectAnimator animline = ObjectAnimator.ofPropertyValuesHolder(curveline,pvhalpha, pvhlineendx, pvhlineendy).setDuration(350);
	            animline.addUpdateListener(this);
	            animlist.add(animline);
			}
		}
		curveanimator.playSequentially(animlist);
		curveanimator.start();
	}
	
	//init curveline
	private CurveLine initCurveLine(float beginx,float beginy,float endx,float endy)
	{
		CurveLine curveline=new CurveLine(beginx,beginy,endx,endy);
		return curveline;
	}
	//init curvepoint
	private CurvePoint initcurvePoint(float pointx,float pointy,float radius)
	{
		CurvePoint curvepoint=new CurvePoint(pointx,pointy,radius);
		OvalShape ovalshape=new OvalShape();
		ovalshape.resize(radius, radius);
		ShapeDrawable shapedrawable=new ShapeDrawable(ovalshape);
		curvepoint.setShape(shapedrawable);
		return curvepoint;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		//super.onDraw(canvas);
		for(int i=0;i<linenum;i++)
		{
			canvas.drawLine(linelist.get(i).getPointbeginX(), linelist.get(i).getPointbeginY(), 
					linelist.get(i).getPointendX(), linelist.get(i).getPointendY(), linelist.get(i).getPaint());
			
		}
		for(int i=0;i<pointnum;i++)
		{
			//pointlist.get(i).getShape().draw(canvas);
			canvas.drawOval(pointlist.get(i).getrectf(), pointlist.get(i).getPaint());
		}
	}
	@Override
	public void onAnimationUpdate(ValueAnimator animation) {
		// TODO Auto-generated method stub
		invalidate();
	}
	
	
}
