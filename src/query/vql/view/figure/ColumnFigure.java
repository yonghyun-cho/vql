package query.vql.view.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.ToolbarLayout;

public class ColumnFigure extends Figure{
	
	public ColumnFigure() {
		ToolbarLayout layout = new ToolbarLayout();
		
		// �ڽ� �׸��� ������ ����
		layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
		
		// �ڽ� �׸��� Ȯ�� ����
		layout.setStretchMinorAxis(false);
		
		// �ڽİ� ���� ����
		layout.setSpacing(2);
		
		setLayoutManager(layout);
		setBorder(new LineBorder(ColorConstants.black,1));
	}
}
