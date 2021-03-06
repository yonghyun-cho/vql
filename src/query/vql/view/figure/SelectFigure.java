﻿package query.vql.view.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.swt.graphics.Color;

public class SelectFigure extends Figure{

	public SelectFigure(Label tableName) {
		ToolbarLayout layout = new ToolbarLayout(true);
		layout.setSpacing(5);
		
		setLayoutManager(layout);
		setBorder(new LineBorder(ColorConstants.black, 1));
		setBackgroundColor(new Color(null, 255, 255, 206));
		setOpaque(true);
		
		add(tableName);
	}
	
	public void addColumn(String columnName){
		ColumnFigure column= new ColumnFigure();
		column.add(new Label(columnName));
		add(column);
	}
}
