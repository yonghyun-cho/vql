package query.vql.view.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.swt.graphics.Color;

public class SubQueryFigure extends Figure {
	public SubQueryFigure(String suqQueryId, String alias) {
		ToolbarLayout layout = new ToolbarLayout(true);
		layout.setSpacing(5);
		
		setLayoutManager(layout);
		setBorder(new LineBorder(ColorConstants.black, 1));
		setBackgroundColor(new Color(null, 255, 255, 206));
		setOpaque(true);
		
		add(new Label("SubQuery"));
		add(new Label(suqQueryId));
		
		if(alias != null || alias.trim() != ""){
			add(new Label(alias));
		}
	}
}
