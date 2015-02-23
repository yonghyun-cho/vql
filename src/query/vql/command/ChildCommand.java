package query.vql.command;

import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.examples.shapes.model.Shape;
import org.eclipse.gef.examples.shapes.model.ShapesDiagram;

import query.vql.view.model.BlockShape;

public class ChildCommand extends Command {
	private Point oldLocation;
	private BlockShape diagram;
	private Shape child;

	public ChildCommand() {
		super("CHILD_COMMAND_LABEL");
	}

	public void execute() {
		List children = diagram.getChildren();
		oldLocation = child.getLocation();
		diagram.removeChild(child);
	}

	public void redo() {
		diagram.removeChild(child);
	}

	public void setChild(Shape child) {
		this.child = child;
	}

	public void setParent(BlockShape parent) {
		diagram = parent;
	}

	public void undo() {
		child.setLocation(oldLocation);
		diagram.addChild(child);
	}
	
}
