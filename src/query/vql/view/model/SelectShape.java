/*******************************************************************************
 * Copyright (c) 2004, 2005 Elias Volanakis and others.
?* All rights reserved. This program and the accompanying materials
?* are made available under the terms of the Eclipse Public License v1.0
?* which accompanies this distribution, and is available at
?* http://www.eclipse.org/legal/epl-v10.html
?*
?* Contributors:
?*????Elias Volanakis - initial API and implementation
?*******************************************************************************/
package query.vql.view.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.examples.shapes.model.Shape;
import org.eclipse.swt.graphics.Image;

/**
 * An elliptical shape.
 * 
 * @author Elias Volanakis
 */
public class SelectShape extends Shape {
	
	private String TableName;
	private List<String> ColumnList = new ArrayList<String>();

	/** A 16x16 pictogram of a rectangular shape. */
	private static final Image RECTANGLE_ICON = createImage("icons/rectangle16.gif");

	private static final long serialVersionUID = 1;

	public Image getIcon() {
		return RECTANGLE_ICON;
	}

	public String toString() {
		return "테이블 " + hashCode();
	}

	public String getTableName() {
		return TableName;
	}

	public void setTableName(String tableName) {
		TableName = tableName;
	}

	public List<String> getColumnList() {
		return ColumnList;
	}

	public void setColumnList(List<String> columnList) {
		ColumnList = columnList;
	}
	
	public void addColumnToList(String columnName){
		this.ColumnList.add(columnName);
	}
}
