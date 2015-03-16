package query.vql.view.model;

import org.eclipse.gef.examples.shapes.model.Shape;
import org.eclipse.swt.graphics.Image;

public class BlockShape extends Shape{
	private String blockName;
	
	/** A 16x16 pictogram of a rectangular shape. */
	private static final Image RECTANGLE_ICON = createImage("icons/rectangle16.gif");
	
	private static final long serialVersionUID = 1;
	
	public BlockShape(String blockName) {
		this.blockName = blockName;
	}
	
	public String toString() {
		return "Block " + hashCode();
	}

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public Image getIcon() {
		return RECTANGLE_ICON;
	}
}
