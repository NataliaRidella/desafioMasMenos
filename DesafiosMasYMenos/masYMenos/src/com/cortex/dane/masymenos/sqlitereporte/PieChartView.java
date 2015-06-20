package com.cortex.dane.masymenos.sqlitereporte;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.AbstractChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.R;
import android.content.Context;
import android.graphics.Color;

public class PieChartView extends GraphicalView
{
 
	public static final int COLOR_GREEN = Color.parseColor("#62c51a");
	public static final int COLOR_ORANGE = Color.parseColor("#ff6c0a");
	public static final int COLOR_BLUE = Color.parseColor("#000080");
 
	
	private PieChartView(Context context, AbstractChart arg1)
	{
		super(context, arg1);
	}
 

	public static GraphicalView getNewInstance(Context context, int correctos, int incorrectos)
	{
		return ChartFactory.getPieChartView(context, getDataSet(context, correctos, incorrectos), getRenderer());
	}
 
	/**
	 * Creates the renderer for the pie chart and sets the basic color scheme and hides labels and legend.
	 * 
	 * @return a renderer for the pie chart
	 */
	private static DefaultRenderer getRenderer()
	{
		int[] colors = new int[] { COLOR_BLUE, COLOR_ORANGE };
 
		DefaultRenderer defaultRenderer = new DefaultRenderer();
		for (int color : colors)
		{
			SimpleSeriesRenderer simpleRenderer = new SimpleSeriesRenderer();
			simpleRenderer.setColor(color);
			defaultRenderer.addSeriesRenderer(simpleRenderer);
		}
		defaultRenderer.setLabelsTextSize(30);
		defaultRenderer.setLegendTextSize(30);
		defaultRenderer.setDisplayValues(true);
		return defaultRenderer;
	}
 
	/**
	 * Creates the data set used by the piechart by adding the string represantation and it's integer value to a
	 * CategorySeries object. Note that the string representations are hard coded.
	 * 
	 * @param context
	 *            the context
	 * @param income
	 *            the total income
	 * @param costs
	 *            the total costs
	 * @return a CategorySeries instance with the data supplied
	 */
	private static CategorySeries getDataSet(Context context, int correctos, int incorrectos)
	{
		CategorySeries series = new CategorySeries("Chart");
		series.add("Correctos", correctos);
		series.add("Incorrectos", incorrectos);
		return series;
	}
}
