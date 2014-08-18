package com.zxs.view;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;

public class CurveLine {

	private float pointbeginx;
	private float pointbeginy;
	private float pointendx;
	private float pointendy;
	private Paint paint;
	private ShapeDrawable shape;
	public CurveLine()
	{
		paint=new Paint();
		paint.setColor(Color.BLUE);
	}
	public CurveLine(float arg0,float arg1,float arg2,float arg3)
	{
		paint=new Paint();
		paint.setAntiAlias(true);
		paint.setStrokeWidth(15);
		paint.setColor(Color.argb(255, 51, 181, 229));
		paint.setAlpha(0);
		pointbeginx=arg0;
		pointbeginy=arg1;
		pointendx=arg2;
		pointendy=arg3;
	}
	public Paint getPaint()
	{
		return paint;
	}
	public void setPaintAlpha(float alpha)
	{
		paint.setAlpha(255);
	}
	public void setpaint(Paint arg0)
	{
		this.paint=arg0;
	}
	public void setPointbeginX(float x)
	{
		this.pointbeginx=x;
	}
	public void setPointbeginY(float y)
	{
		//Shape s=shape.getShape();
		//s.resize(getPointendX()-getPointbeginX(), Math.abs(y-getPointbeginY()));
		this.pointbeginy=y;
	}
	public void setPointendX(float x)
	{
		//Shape s=shape.getShape();
		//s.resize(x-getPointbeginX(), Math.abs(getPointendY()-getPointbeginY()));
		this.pointendx=x;
	}
	public void setPointendY(float y)
	{
		this.pointendy=y;
	}
	public float getPointbeginX()
	{
		return pointbeginx;
	}
	public float getPointbeginY()
	{
		return pointbeginy;
	}
	public float getPointendX()
	{
		return pointendx;
	}
	public float getPointendY()
	{
		return pointendy;
	}
	public void setShape(ShapeDrawable values)
	{
		shape=values;
	}
	public ShapeDrawable getShape()
	{
		return shape;
	}
}
