package query.vql.view.model;

import org.eclipse.gef.examples.shapes.model.Shape;
import org.eclipse.swt.graphics.Image;

public class WhereShape extends Shape{
	private String sourceColumn1;
	private String sourceColumn2;
	private String comparisionOp;
	private String targetColumn1;
	private String targetColumn2;
	
	/** A 16x16 pictogram of a rectangular shape. */
	private static final Image RECTANGLE_ICON = createImage("icons/rectangle16.gif");

	private static final long serialVersionUID = 1;

	public Image getIcon() {
		return RECTANGLE_ICON;
	}

	public String toString() {
		return "Å×ÀÌºí " + hashCode();
	}

	public String getSourceColumn1() {
		return sourceColumn1;
	}

	public void setSourceColumn1(String sourceColumn1) {
		this.sourceColumn1 = sourceColumn1;
	}

	public String getSourceColumn2() {
		return sourceColumn2;
	}

	public void setSourceColumn2(String sourceColumn2) {
		this.sourceColumn2 = sourceColumn2;
	}

	public String getComparisionOp() {
		return comparisionOp;
	}

	public void setComparisionOp(String comparisionOp) {
		this.comparisionOp = comparisionOp;
	}

	public String getTargetColumn1() {
		return targetColumn1;
	}

	public void setTargetColumn1(String targetColumn1) {
		this.targetColumn1 = targetColumn1;
	}

	public String getTargetColumn2() {
		return targetColumn2;
	}

	public void setTargetColumn2(String targetColumn2) {
		this.targetColumn2 = targetColumn2;
	}

}
