package query.vql.view.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.ToolbarLayout;

public class ColumnFigure extends Figure{
	
	public ColumnFigure() {
		ToolbarLayout layout = new ToolbarLayout();
		
		// 자식 항목의 정렬을 설정
		layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
		
		// 자식 항목의 확장 여부
		layout.setStretchMinorAxis(false);
		
		// 자식간 간격 설정
		layout.setSpacing(2);
		
		setLayoutManager(layout);
		setBorder(new LineBorder(ColorConstants.black,1));
	}
}
