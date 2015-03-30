package query.vql.view.model;

import org.eclipse.gef.examples.shapes.model.Shape;
import org.eclipse.swt.graphics.Image;

public class FromShape extends Shape {
	private String TableName;
	private String alias;
	
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

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}
