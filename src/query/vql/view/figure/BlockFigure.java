﻿package query.vql.view.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.PuristicScrollPane;
import org.eclipse.draw2d.ScrollPane;
import org.eclipse.draw2d.StackLayout;

public class BlockFigure extends Figure{
	public BlockFigure(String blockName) {
		setBorder(new LineBorder(ColorConstants.black, 1));
		setOpaque(false);
		
		ScrollPane scrollpane = new PuristicScrollPane();
		IFigure pane = new FreeformLayer();
		pane.setLayoutManager(new FreeformLayout());
		setLayoutManager(new StackLayout());
		add(scrollpane);
		scrollpane.setViewport(new FreeformViewport());
		scrollpane.setContents(pane);

		add(new Label(blockName));
	}
}
