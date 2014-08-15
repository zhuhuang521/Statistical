package com.zxs.view;

import java.util.ArrayList;
import java.util.HashMap;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
/**
 * @author xuesong.zhu
 * @since 2014 8 11*****/
public class ChartView extends View {
	private ArrayList<HashMap<String, Object>> datalist;
	private ArrayList<HashMap<String, Float>> anglelist;
	private ArrayList<ChartLine> chartlinelist;
	private int chartnum;
	private int viewheight, viewwidth;
	private int left, top, right, bottom;
	private RectF rectf;
	private Paint paint;
	private int colors[];

	private float leftx, rightx;
	private float radius;
	
	private ArrayList<ChartItem> chartitemlist;
	public ChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initdata(context);
	}

	public ChartView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initdata(context);
	}
	
	private void initdata(Context context) {
		colors = new int[] { 0xff673ab7, 0xff03a9f4, 0xffcddc39, 0xff14e715,
				0xffffc107, 0xffff5722, 0xff33b5e5, 0xffe51c23, 0xff9c27b0,
				0xff5677fc };
		datalist = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map6 = new HashMap<String, Object>();
		map6.put("chartname", "name6");
		map6.put("chartvalues", 20f);

		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("chartname", "name1");
		map1.put("chartvalues", 10f);
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("chartname", "name2");
		map2.put("chartvalues", 30f);
		HashMap<String, Object> map3 = new HashMap<String, Object>();
		map3.put("chartname", "name3");
		map3.put("chartvalues", 5f);
		HashMap<String, Object> map4 = new HashMap<String, Object>();
		map4.put("chartname", "name4");
		map4.put("chartvalues", 45f);
		HashMap<String, Object> map5 = new HashMap<String, Object>();
		map5.put("chartname", "name5");
		map5.put("chartvalues", 22f);
		datalist.add(map1);
		datalist.add(map2);
		datalist.add(map3);
		datalist.add(map4);
		datalist.add(map5);
		datalist.add(map6);

		anglelist = new ArrayList<HashMap<String, Float>>();
		chartnum = datalist.size();
		float anglenum = 0;
		for (int i = 0; i < chartnum; i++) {
			anglenum = anglenum + (Float) datalist.get(i).get("chartvalues");
		}
		float beginangle = -90;
		for (int i = 0; i < chartnum; i++) {
			HashMap<String, Float> map = new HashMap<String, Float>();
			map.put("beginangle", beginangle);
			map.put("endangle",
					(float) (((Float) datalist.get(i).get("chartvalues")) * 360 / anglenum));
			anglelist.add(map);
			beginangle = beginangle + map.get("endangle");
		}
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		WindowManager windowmanager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		windowmanager.getDefaultDisplay().getMetrics(mDisplayMetrics);
		int screenwidth = mDisplayMetrics.widthPixels;
		int screenheight = mDisplayMetrics.heightPixels;
		viewheight = screenwidth * 6 / 10;
		viewwidth = viewheight;
		leftx = screenwidth * 0.15f;
		rightx = screenwidth * 0.85f;
		radius = screenwidth * 0.325f;
		left = (screenwidth - viewwidth) / 2;
		top = (screenheight - viewheight) / 2;
		right = screenwidth - left;
		bottom = screenheight - top;
		rectf = new RectF(left, top, right, bottom);
		paint = new Paint();
		paint.setTextSize(25);
		paint.setAntiAlias(true);
		initline();
		initanim();
	}
	@SuppressLint("NewApi")
	private void initanim()
	{
		chartitemlist=new ArrayList<ChartView.ChartItem>();
		AnimatorSet animset=new AnimatorSet();
		AnimListener listener=new AnimListener();
		ArrayList<Animator> animlist=new ArrayList<Animator>();
		for(int i=0;i<chartnum;i++)
		{
			ChartItem item=new ChartItem( viewheight/2, colors[i], 0,(right+left)/2, (top+bottom)/2,anglelist.get(i).get("beginangle"),
					anglelist.get(i).get("endangle"));
			chartitemlist.add(item);
			ObjectAnimator anim=ObjectAnimator.ofInt(item, "alpha", 0,255).setDuration(1200);
			ObjectAnimator anim1=ObjectAnimator.ofFloat(item, "pointx", chartlinelist.get(i).points[2],(float)(right+left)/2).setDuration(1200);
			ObjectAnimator anim3=ObjectAnimator.ofFloat(item, "pointy", chartlinelist.get(i).points[3],(float)(top+bottom)/2).setDuration(1200);
			AnimatorSet anim4=new AnimatorSet();
			anim3.addUpdateListener(listener);
			anim4.playTogether(anim,anim1,anim3);
			anim1.addUpdateListener(listener);
			animlist.add(anim4);
		}
		animset.playTogether(animlist);
		animset.start();
		
		//line anim
		ArrayList<Animator> lineanimlist=new ArrayList<Animator>();
		for(int i=0;i<chartnum;i++)
		{
			ObjectAnimator anim2=ObjectAnimator.ofInt(chartlinelist.get(i), "alpha", 0,255).setDuration(150);
			anim2.setStartDelay(i*120);
			anim2.addUpdateListener(listener);
			lineanimlist.add(anim2);
		}
		AnimatorSet lineset=new AnimatorSet();
		lineset.setStartDelay(1200);
		lineset.playTogether(lineanimlist);
		lineset.start();
		
	}
	private class AnimListener implements AnimatorUpdateListener
	{

		@Override
		public void onAnimationUpdate(ValueAnimator animation) {
			// TODO Auto-generated method stub
			ChartView.this.invalidate();
		}
		
	}
	private void initline() {
		chartlinelist = new ArrayList<ChartView.ChartLine>();
		for (int i = 0; i < chartnum; i++) {
			ChartLine chartline = new ChartLine(colors[i],0);
			chartline.points = getpoints(anglelist.get(i).get("beginangle"),
					anglelist.get(i).get("endangle"));
			chartline.textstartx = chartline.points[4];
			chartline.textstarty = chartline.points[5];
			chartline.flagtext = datalist.get(i).get("chartname").toString();
			chartline.isright=getrightflag(anglelist.get(i).get("beginangle"),
					anglelist.get(i).get("endangle"));
			chartlinelist.add(chartline);
		}
	}

	private boolean getrightflag(float startangle, float swipeangle) {
		boolean rightflag;
		float midangle = startangle + swipeangle / 2;
		if (midangle < 0)
			rightflag = true;
		else if (midangle >= 0 && midangle < 90)
			rightflag = true;
		else if (midangle >= 90 && midangle < 180)
			rightflag = false;
		else
			rightflag = false;
		return rightflag;
	}

	private float[] getpoints(float startangle, float swipeangle) {
		float points[] = new float[6];
		float midangle = startangle + swipeangle / 2;
		if (midangle < 0) {
			// 
			points[0] = (right + left) / 2;
			points[1] = (top + bottom) / 2;
			points[2] = points[0]
					+ (float) (radius * Math.cos(Math.toRadians(-midangle)));
			points[3] = points[1]
					- (float) (radius * Math.sin(Math.toRadians(-midangle)));
			points[4] = rightx;
			points[5] = points[3];
		} else if (midangle >= 0 && midangle < 90) {
			// 
			points[0] = (right + left) / 2;
			points[1] = (top + bottom) / 2;
			points[2] = points[0]
					+ (float) (radius * Math.cos(Math.toRadians(midangle)));
			points[3] = points[1]
					+ (float) (radius * Math.sin(Math.toRadians(midangle)));
			points[4] = rightx;
			points[5] = points[3];
		} else if (midangle >= 90 && midangle < 180) {
			//
			points[0] = (right + left) / 2;
			points[1] = (top + bottom) / 2;
			points[2] = points[0]
					- (float) (radius * Math.sin(Math.toRadians(midangle - 90)));
			points[3] = points[1]
					+ (float) (radius * Math.cos(Math.toRadians(midangle - 90)));
			points[4] = leftx;
			points[5] = points[3];
		} else {
			//
			points[0] = (right + left) / 2;
			points[1] = (top + bottom) / 2;
			points[2] = points[0]
					- (float) (radius * Math
							.cos(Math.toRadians(midangle - 180)));
			points[3] = points[1]
					- (float) (radius * Math
							.sin(Math.toRadians(midangle - 180)));
			points[4] = leftx;
			points[5] = points[3];
		}
		return points;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		drawchart(canvas);
		drawline(canvas);
	}

	private void drawchart(Canvas canvas) {
//		for (int i = 0; i < chartnum; i++) {
//			paint.setColor(colors[i]);
//			canvas.drawArc(rectf, anglelist.get(i).get("beginangle"), anglelist
//					.get(i).get("endangle"), true, paint);
//		}
		for(int i=0;i<chartnum;i++)
		{
			canvas.drawArc(chartitemlist.get(i).getRectf(), chartitemlist.get(i).getStartangle(),
					chartitemlist.get(i).getAddangle(), true, chartitemlist.get(i).getpaint());
		}
	}

	private void drawline(Canvas canvas) {
		for (int i = 0; i < chartnum; i++) {
			// int i=0;
			if(chartlinelist.get(i).isright)
				chartlinelist.get(i).getPaint().setTextAlign(Paint.Align.LEFT);
			else
				chartlinelist.get(i).getPaint().setTextAlign(Paint.Align.RIGHT);
			
			paint.setColor(colors[i]);
			canvas.drawLine(chartlinelist.get(i).points[0],
					chartlinelist.get(i).points[1],
					chartlinelist.get(i).points[2],
					chartlinelist.get(i).points[3], chartlinelist.get(i).getPaint());
			canvas.drawLine(chartlinelist.get(i).points[2],
					chartlinelist.get(i).points[3],
					chartlinelist.get(i).points[4],
					chartlinelist.get(i).points[5],  chartlinelist.get(i).getPaint());
			canvas.drawText(chartlinelist.get(i).flagtext,
					chartlinelist.get(i).textstartx,
					chartlinelist.get(i).textstarty, chartlinelist.get(i).getPaint());
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		Log.v("zxs", "touch event");
		return false;
		//return super.onTouchEvent(event);
	}

	private class ChartLine {
		float points[];
		String flagtext;
		float textstartx;
		float textstarty;
		boolean isright;
		int color;
		int alpha;
		Paint paint;
		public ChartLine(int color,int alpha)
		{
			this.color=color;
			this.alpha=alpha;
			paint=new Paint();
			paint.setColor(color);
			paint.setAntiAlias(true);
			paint.setAlpha(alpha);
			paint.setTextSize(25);
		}
		public int getColor() {
			return color;
		}
		public void setColor(int color) {
			this.color = color;
		}
		public int getAlpha() {
			return alpha;
		}
		public void setAlpha(int alpha) {
			this.alpha = alpha;
			paint.setAlpha(alpha);
		}
		public Paint getPaint() {
			return paint;
		}
		public void setPaint(Paint paint) {
			this.paint = paint;
		}
		
	}
	//chart class
	private class ChartItem
	{
		int color;
		float pointx;
		float pointy;
		RectF rectf;
		int alpha;
		Paint paint;
		float radius;
		float startangle,addangle;
		public ChartItem(float radius,int color,int alpha,float x,float y,float start,float add)
		{
			this.startangle=start;
			this.addangle=add;
			this.radius=radius;
			rectf=new RectF(x-radius, y-radius, x+radius, y+radius);
			pointx=x;
			pointy=y;
			this.color=color;
			this.alpha=alpha;
			paint=new Paint();
			paint.setAntiAlias(true);
			paint.setColor(color);
			paint.setAlpha(alpha);
		}
		public float getStartangle()
		{
			return startangle;
		}
		public float getAddangle()
		{
			return addangle;
		}
		public Paint getpaint()
		{
			return paint;
		}
		public void setAlpha(int a)
		{
			this.alpha=a;
			paint.setAlpha(alpha);
		}
		public int getAlpha()
		{
			return alpha;
		}
		public void setColor(int arg0){
			this.color=arg0;
			paint.setColor(color);
		}
		public int getColor()
		{
			return color;
		}
		public void setPointx(float x)
		{
			//Log.v("zxs", "point x"+x);
			this.pointx=x;
			rectf.set(pointx-radius, pointy-radius, pointx+radius, pointy+radius);
		}
		public float getPointx()
		{
			return pointx;
		}
		public void setPointy(float y)
		{
			//Log.v("zxs", "point x"+x);
			this.pointy=y;
			rectf.set(pointx-radius, pointy-radius, pointx+radius, pointy+radius);
		}
		public float getPointy()
		{
			return pointy;
		}
		public RectF getRectf()
		{
			return rectf;
		}
	}
}
