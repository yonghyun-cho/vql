package query.vql.policy;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.AbstractEditPolicy;
import org.eclipse.gef.examples.shapes.model.Shape;
import org.eclipse.gef.examples.shapes.model.ShapesDiagram;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.GroupRequest;

import query.vql.command.ChildCommand;
import query.vql.part.BlockEditpart;
import query.vql.view.model.BlockShape;

public class BlockContainerEditPolicy extends AbstractEditPolicy {

	/**
	 * Override to contribute to add requests.
	 * 
	 * @param request
	 *            the add request
	 * @return the command contribution to the add
	 */
	protected Command getAddCommand(GroupRequest request) {
		return null;
	}

	/**
	 * Override to contribute to clone requests.
	 * 
	 * @param request
	 *            the clone request
	 * @return the command contribution to the clone
	 */
	protected Command getCloneCommand(ChangeBoundsRequest request) {
		return null;
	}

	/**
	 * Overridden to check for add, create, and orphan.
	 * 
	 * @see org.eclipse.gef.EditPolicy#getCommand(org.eclipse.gef.Request)
	 */
	public Command getCommand(Request request) {
		if (REQ_CREATE.equals(request.getType()))
			return getCreateCommand((CreateRequest) request);
		if (REQ_ADD.equals(request.getType()))
			return getAddCommand((GroupRequest) request);
		if (REQ_CLONE.equals(request.getType()))
			return getCloneCommand((ChangeBoundsRequest) request);
		if (REQ_ORPHAN_CHILDREN.equals(request.getType()))
			return getChildCommand((GroupRequest) request);
		return null;
	}

	protected Command getCreateCommand(CreateRequest request) {
		return null;
	}

	public Command getChildCommand(GroupRequest request) {
		List parts = request.getEditParts();
		CompoundCommand result = new CompoundCommand("Orphan children");
		for (int i = 0; i < parts.size(); i++) {
			ChildCommand childCommand = new ChildCommand();
			childCommand.setChild((Shape) ((EditPart) parts.get(i)).getModel());
			childCommand.setParent((BlockShape) getHost().getModel());
			childCommand.setLabel("CHILD_COMMAND_DIAGRAM");
			result.add(childCommand);
		}
		return result.unwrap();
	}
}
