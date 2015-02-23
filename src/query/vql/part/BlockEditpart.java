package query.vql.part;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.examples.shapes.model.ModelElement;
import org.eclipse.gef.examples.shapes.model.Shape;
import org.eclipse.gef.examples.shapes.model.ShapesDiagram;
import org.eclipse.gef.examples.shapes.model.commands.ShapeCreateCommand;
import org.eclipse.gef.examples.shapes.model.commands.ShapeSetConstraintCommand;
import org.eclipse.gef.examples.shapes.parts.ShapeEditPart;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import query.vql.policy.BlockContainerEditPolicy;
import query.vql.view.figure.BlockFigure;
import query.vql.view.model.BlockShape;

public class BlockEditpart extends AbstractGraphicalEditPart implements
PropertyChangeListener, NodeEditPart {
	
	/**
	 * Upon activation, attach to the model element as a property change
	 * listener.
	 */
	public void activate() {
		if (!isActive()) {
			super.activate();
			((ModelElement) getModel()).addPropertyChangeListener(this);
		}
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(
			ConnectionEditPart connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(
			ConnectionEditPart connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
		if (Shape.SIZE_PROP.equals(prop) || Shape.LOCATION_PROP.equals(prop)) {
			refreshVisuals();
		} else if (Shape.SOURCE_CONNECTIONS_PROP.equals(prop)) {
			refreshSourceConnections();
		} else if (Shape.TARGET_CONNECTIONS_PROP.equals(prop)) {
			refreshTargetConnections();
			
		// TODO 이것도 child에 역할하는 듯
		} else if (ShapesDiagram.CHILD_ADDED_PROP.equals(prop)
				|| ShapesDiagram.CHILD_REMOVED_PROP.equals(prop)) {
			refreshChildren();
		}
	}
	
	protected void refreshVisuals() {
		// notify parent container of changed position & location
		// if this line is removed, the XYLayoutManager used by the parent
		// container
		// (the Figure of the ShapesDiagramEditPart), will not know the bounds
		// of this figure
		// and will not draw it correctly.
		Rectangle bounds = new Rectangle(getCastedModel().getLocation(),
				getCastedModel().getSize());
		((GraphicalEditPart) getParent()).setLayoutConstraint(this,
				getFigure(), bounds);
	}
	
	private BlockShape getCastedModel() {
		return (BlockShape) getModel();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	protected List getModelChildren() {
		return getCastedModel().getChildren(); // return a list of shapes
	}

	// TODO 여기서 뭔가 Diagram과 차이가 생기는듯.
	// DiagramEditPart의 createFigure의 FreeformLayer Figure가 역할을 하는 듯.
	@Override
	protected IFigure createFigure() {
		IFigure f = null;
		Object shape = getModel();
		
		if(shape instanceof BlockShape){
			BlockShape blockShape = (BlockShape)shape;
			
			BlockFigure blockFigure = new BlockFigure(blockShape.getBlockName());
			
			f = blockFigure;
		}
		
		f = new FreeformLayer();
		f.setBorder(new MarginBorder(3));
		f.setLayoutManager(new FreeformLayout());
		f.setSize(100, 100);
		
		return f;
	}

	@Override
	protected void createEditPolicies() {
		// 이건 삭제 기능인 것 같은데..
//		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ShapeComponentEditPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		
		// 이게 자식을 담기 위한 것?
		installEditPolicy(EditPolicy.CONTAINER_ROLE, new BlockContainerEditPolicy());
//		installEditPolicy(EditPolicy.LAYOUT_ROLE, editPolicy);
		
		// handles constraint changes (e.g. moving and/or resizing) of model elements
		// and creation of new model elements
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ShapesXYLayoutEditPolicy());
	}

	/**
	 * EditPolicy for the Figure used by this edit part. Children of
	 * XYLayoutEditPolicy can be used in Figures with XYLayout.
	 * 
	 * @author Elias Volanakis
	 */
	private static class ShapesXYLayoutEditPolicy extends XYLayoutEditPolicy {

		/*
		 * (non-Javadoc)
		 * 
		 * @see ConstrainedLayoutEditPolicy#createChangeConstraintCommand(
		 * ChangeBoundsRequest, EditPart, Object)
		 */
		protected Command createChangeConstraintCommand(
				ChangeBoundsRequest request, EditPart child, Object constraint) {
			if (child instanceof ShapeEditPart
					&& constraint instanceof Rectangle) {
				// return a command that can move and/or resize a Shape
				return new ShapeSetConstraintCommand((Shape) child.getModel(),
						request, (Rectangle) constraint);
			}
			return super.createChangeConstraintCommand(request, child,
					constraint);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * ConstrainedLayoutEditPolicy#createChangeConstraintCommand(EditPart,
		 * Object)
		 */
		protected Command createChangeConstraintCommand(EditPart child,
				Object constraint) {
			// not used in this example
			return null;
		}

		// 여기서 ShapeCreateCommand를 호출함!!
		// LayoutEditPolicy의 상속 함수.
		protected Command getCreateCommand(CreateRequest request) {
			Object childClass = request.getNewObjectType();
			if (childClass instanceof Shape) {
				// return a command that can add a Shape to a ShapesDiagram
				return new ShapeCreateCommand((Shape) request.getNewObject(),
						(ShapesDiagram) getHost().getModel(),
						(Rectangle) getConstraintFor(request));
				
			}
			return null;
		}

	}
	
}
