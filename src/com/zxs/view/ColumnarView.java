package com.zxs.view;

import java.util.ArrayList;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
/****
 * @author xuesong.zhu
 * @since 2014 7 15****/
public class ColumnarView extends View{

	private int viewheight,viewwidth;
	private ArrayList<Integer> datalist;
	private int mMaxData;
	private float itemwidth,itemheight;
	private float maxitemwidth;
	private Paint paint;
	
	
	private ArrayList<ColumnarItem> rectlist;
	//color 
	private int colors[] = new int[] { 0xff673ab7, 0xff03a9f4, 0xffcddc39, 0xff14e715,
			0xffffc107, 0xffff5722, 0xff33b5e5, 0xffe51c23, 0xff9c27b0,
			0xff5677fc };
	public ColumnarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public ColumnarView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		paint=new Paint();
		paint.setColor(0xff33b5e5);
		rectlist=new ArrayList<ColumnarView.ColumnarItem>();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		drawcoordinate(canvas);
		drawHistogram(canvas);
	}
	/**
	 * draw coordinate
	 * with x y line and triangle**/
	private void drawcoordinate(Canvas canvas)
	{
		//x
		canvas.drawLine(0.05f*viewwidth, 0.9f*viewheight, 0.95f*viewwidth, 0.9f*viewheight, paint);
		//y
		canvas.drawLine(0.05f*viewwidth, 0.1f*viewheight, 0.05f*viewwidth, 0.9f*viewheight, paint);
		
	}
	/**
	 * draw histogram**/
	private void drawHistogram(Canvas canvas)
	{
		for(int i=0;i<datalist.size();i++)
		{
			canvas.drawRect(rectlist.get(i).rect, rectlist.get(i).paint);
		}
	}
	/**
	 * set columnar list****/
	public void setListData(ArrayList<Integer> data)
	{
		this.datalist=data;
		mMaxData=(int)(getMaxData()*1.2);
		this.invalidate();
	}
	private int getMaxData()
	{
		int max=0;
		int num=datalist.size();
		for(int i=0;i<num;i++)
		{
			if(datalist.get(i)>max)
				max=datalist.get(i);
		}
		return max;
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		//super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		viewheight=measureHeight(heightMeasureSpec);
		viewwidth=measureWidth(widthMeasureSpec);
		setMeasuredDimension(viewwidth, viewheight);
		inithistogramdata();
	}
	private void inithistogramdata()
	{
		itemheight=(0.8f*viewheight)/mMaxData;
		itemwidth=(0.9f*viewwidth)/(datalist.size()+(datalist.size()+1)*0.25f);
		maxitemwidth=(0.9f*viewwidth)/5;
		if(itemwidth>maxitemwidth)
			itemwidth=maxitemwidth;
		int num=datalist.size();
		for(int i=0;i<num;i++)
		{
			ColumnarItem item=new ColumnarItem(i);
			item.setColor(colors[i%colors.length]);
			rectlist.add(item);
		}
		initanimation();
	}
	
	private void initanimation()
	{
		ArrayList<Animator> animlist=new ArrayList<Animator>();
		AnimListener animlistener=new AnimListener();
		AnimatorSet animset=new AnimatorSet();
		for(int i=0;i<rectlist.size();i++)
		{
			ObjectAnimator anim=ObjectAnimator.ofFloat(rectlist.get(i), "top", rectlist.get(i).bottom,rectlist.get(i).maxtop).setDuration(1200);
			animlist.add(anim);
			if(i==0)
				anim.addUpdateListener(animlistener);
		}
		animset.playTogether(animlist);
		animset.start();
	}
	
	private class AnimListener implements AnimatorUpdateListener
	{

		@Override
		public void onAnimationUpdate(ValueAnimator animation) {
			// TODO Auto-generated method stub
			ColumnarView.this.invalidate();
		}
		
	}
	
	private int measureWidth(int measureSpec)
	{
		int result=0;
		int specMode=MeasureSpec.getMode(measureSpec);
		int specSize=MeasureSpec.getSize(measureSpec);
		if(specMode==MeasureSpec.EXACTLY)
			result=specSize;
		else
		{
			result=diptopx(getviewminwidth())+getPaddingLeft()+getPaddingRight();
			if(specMode==MeasureSpec.AT_MOST)
				result=Math.min(result, specSize);
		}
		return result;
	}
	
	private int measureHeight(int measureSpec)
	{
		int result=0;
		int specMode=MeasureSpec.getMode(measureSpec);
		int specSize=MeasureSpec.getSize(measureSpec);
		if(specMode==MeasureSpec.EXACTLY)
			result=specSize;
		else
		{
			result=diptopx(getviewminheight())+getPaddingTop()+getPaddingBottom();
			if(specMode==MeasureSpec.AT_MOST)
				result=Math.min(result, specSize);
		}
		return result;
	}
	private int diptopx(int dip)
	{
		int px=0;
		return px;
	}
	private int getviewminwidth()
	{
		return 0;
	}
	private int getviewminheight()
	{
		return 0;
	}
	
	private class ColumnarItem
	{
		public int maxdata;
		public int data;
		int position;
		float recttop;
		RectF rect;
		Paint paint;
		private int color;
		private float left,top,right,bottom;
		private float maxtop;
		public ColumnarItem(int position)
		{
			maxdata=datalist.get(position);
			this.position=position;
			left=0.05f*viewwidth+(position+1)*itemwidth/4f+position*itemwidth;
			right=0.05f*viewwidth+(position+1)*itemwidth/4f+(position+1)*itemwidth;
			bottom=viewheight*0.9f;
			top=0.9f*viewheight-data*itemheight;
			maxtop=0.9f*viewheight-maxdata*itemheight;
			rect=new RectF(left, top, right, bottom);
			paint=new Paint();
			paint.setColor(colors[position%colors.length]);
			paint.setAntiAlias(true);
		}
		
		public float getTop() {
			return top;
		}

		public void setTop(float top) {
			this.top = top;
			rect=new RectF(left, top, right, bottom);
		}

		public int getData() {
			return data;
		}
		public void setData(int data) {
			this.data = data;
			top=0.9f*viewheight-data*itemheight;
			rect=new RectF(left, top, right, bottom);
		}
		public int getColor() {
			return color;
		}
		public void setColor(int color) {
			this.color = color;
		}
		public int getPosition() {
			return position;
		}
		public void setPosition(int position) {
			this.position = position;
		}
		public float getRecttop() {
			return recttop;
		}
		public void setRecttop(float recttop) {
			this.recttop = recttop;
		}
	}
	
	//坐标系
	private class CoorDinate
	{
		float beginx,beginy,endx,endy;
		boolean hasarrows;
	}
	private class CoorDinateLine
	{
		
	}
}
