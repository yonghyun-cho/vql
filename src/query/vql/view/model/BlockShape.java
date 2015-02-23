package query.vql.view.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.examples.shapes.model.Shape;
import org.eclipse.gef.examples.shapes.model.ShapesDiagram;
import org.eclipse.swt.graphics.Image;

public class BlockShape extends Shape{
	private String blockName;
	
	/** A 16x16 pictogram of a rectangular shape. */
	private static final Image RECTANGLE_ICON = createImage("icons/rectangle16.gif");
	
	private static final long serialVersionUID = 1;
	
	/** Property ID to use when a child is added to this diagram. */
	public static final String CHILD_ADDED_PROP = "ShapesDiagram.ChildAdded";
	/** Property ID to use when a child is removed from this diagram. */
	public static final String CHILD_REMOVED_PROP = "ShapesDiagram.ChildRemoved";
	
	/** Size of this shape. */
	private Dimension size = new Dimension(50, 50);
	
	private List<Shape> shapes = new ArrayList<Shape>();
	
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

	/**
	 * Add a shape to this diagram.
	 * 
	 * @param s
	 *            a non-null shape instance
	 * @return true, if the shape was added, false otherwise
	 */
	public boolean addChild(Shape s) {
		if (s != null && shapes.add(s)) {
			firePropertyChange(CHILD_ADDED_PROP, null, s);
			return true;
		}
		return false;
	}
	
	public boolean addChild(List shapeList){
		boolean result = true;
		
		for(int i = 0; i < shapeList.size(); i++){
			if(shapeList.get(i) instanceof Shape){
				result = result && this.addChild((Shape)shapeList.get(i));
				
			}else{
				result = false;
			}
			
			if(result == false){
				break;
			}
		}
		
		return result;
	}

	/**
	 * Return a List of Shapes in this diagram. The returned List should not be
	 * modified.
	 */
	public List<Shape> getChildren() {
		return shapes;
	}

	/**
	 * Remove a shape from this diagram.
	 * 
	 * @param s
	 *            a non-null shape instance;
	 * @return true, if the shape was removed, false otherwise
	 */
	public boolean removeChild(Shape s) {
		if (s != null && shapes.remove(s)) {
			firePropertyChange(CHILD_REMOVED_PROP, null, s);
			return true;
		}
		return false;
	}
	
	public void setSize(Dimension newSize) {
		if (newSize != null) {
			size.setSize(newSize);
			firePropertyChange("Shape.Size", null, size);
		}
	}
	
	public Image getIcon() {
		return RECTANGLE_ICON;
	}
}
