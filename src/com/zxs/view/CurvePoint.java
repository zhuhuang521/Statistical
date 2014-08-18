package com.zxs.view;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;

public class CurvePoint {

	private float x=0,y=0;
	private float radius=0;
	private ShapeDrawable shape;
	private Paint paint;
	private RectF rectf;
	public CurvePoint()
	{
		
	}
	public CurvePoint(float arg0,float arg1,float arg2)
	{
		paint =new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.GREEN);
		paint.setAlpha(0);
		x=arg0;
		y=arg1;
		radius=arg2;
		rectf=new RectF(arg0-arg2, arg1-arg2, arg0+arg2, arg1+arg2);
	}
	public RectF getrectf()
	{
		return rectf;
	}
	public Paint getPaint()
	{
		return paint;
	}
	public void setPaintAlpha(float arg0)
	{
		paint.setAlpha(255);
	}
	public void SetX(float arg0)
	{
		this.x=arg0;
	}
	public void SetY(float arg0)
	{
		this.y=arg0;
	}
	public void setRadius(float arg0)
	{
		rectf.set(x-arg0, y-arg0, x+arg0, y+arg0);
		this.radius=arg0;
	}
	public float getX()
	{
		return x;
	}
	public float getY()
	{
		return y;
	}
	public float getRadius()
	{
		Shape s=shape.getShape();
		s.resize(radius, radius);
		return radius;
	}
	public void setShape(ShapeDrawable values)
	{
		shape=values;
	}
	public  ShapeDrawable getShape()
	{
		return shape;
	}
}
