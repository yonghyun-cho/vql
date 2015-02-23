package query.vql.view.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.swt.graphics.Color;

public class WhereFigure extends Figure {
	public WhereFigure(Label sourceColumn1, Label sourceColumn2, Label RelationOp, Label targetColumn1, Label targetColumn2) {
		ToolbarLayout layout = new ToolbarLayout(true);
		layout.setSpacing(5);
		
		setLayoutManager(layout);
		setBorder(new LineBorder(ColorConstants.black, 1));
		setBackgroundColor(new Color(null, 255, 255, 206));
		setOpaque(true);
		
		add(sourceColumn1);
		add(sourceColumn2);
		add(RelationOp);
		add(targetColumn1);
		add(targetColumn2);
	}
	
	public WhereFigure(String sourceColumn1, String sourceColumn2, String comparisionOp, String targetColumn1, String targetColumn2) {
		ToolbarLayout layout = new ToolbarLayout(true);
		layout.setSpacing(5);
		
		setLayoutManager(layout);
		setBorder(new LineBorder(ColorConstants.black, 1));
		setBackgroundColor(new Color(null, 255, 255, 206));
		setOpaque(true);
		
		add(new Label(sourceColumn1));
		add(new Label(sourceColumn2));
		add(new Label(comparisionOp));
		add(new Label(targetColumn1));
		add(new Label(targetColumn2));
	}
}
