package query.infoModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.examples.shapes.model.Shape;

import query.parser.vo.ColumnInfo;
import query.parser.vo.ConditionInfo;
import query.parser.vo.ConstInfo;
import query.parser.vo.FunctionInfo;
import query.parser.vo.QueryComponentType;
import query.parser.vo.QueryInfo;
import query.parser.vo.SubQueryInfo;
import query.parser.vo.TableInfo;
import query.parser.vo.TableViewType;
import query.parser.vo.VisualQueryInfo;
import query.parser.vo.WhereInfo;
import query.parser.vo.WhereType;
import query.vql.view.figure.SubQueryFigure;
import query.vql.view.model.BlockShape;
import query.vql.view.model.FromShape;
import query.vql.view.model.SelectShape;
import query.vql.view.model.WhereShape;

public class InfoModelMapper {
	private VisualQueryInfo visualQueryInfo = new VisualQueryInfo();
	
	private int yLoc = 0;
	private int xLoc = 0;
	
	private final int BLOCK_HORIZONTAL_SIZE = 500;
	private final int BLOCK_HORIZONTAL_LOCATION = 10;
	
	public VisualQueryInfo getVisualQueryInfo() {
		return visualQueryInfo;
	}

	public void setVisualQueryInfo(VisualQueryInfo visualQueryInfo) {
		this.visualQueryInfo = visualQueryInfo;
	}
	
	public List<Shape> convertInfoToShape(){
		List<Shape> queryShapeList = new ArrayList<Shape>();
		
		// MainQuery
		QueryInfo mainQueryInfo = visualQueryInfo.getMainQueryInfo();
		List<Shape> mainQueryShapeList = this.convertQueryInfoToShape(mainQueryInfo);
		queryShapeList.addAll(mainQueryShapeList);
		
		/* TODO �ϴ� �̺κ��� ������ �ȵ� �κ��̶� view�� �������� �ʱ� ���� �ּ� ó��.
		 * shape��ġ�鸸 �� �����ϸ� ������ ��.
		 * 
		// SubQuery
		Map<String, QueryInfo> subQueryMap = visualQueryInfo.getSubQueryMap();
		for(String subQueryKey: subQueryMap.keySet()){
			QueryInfo subQueryInfo = subQueryMap.get(subQueryKey);
			List<Shape> subQueryShapeList = this.convertQueryInfoToShape(subQueryInfo);
			queryShapeList.addAll(subQueryShapeList);
		}
		*/
		
		return queryShapeList;
	}
	
	private List<Shape> convertQueryInfoToShape(QueryInfo queryInfo){
		List<Shape> queryShapeList = new ArrayList<Shape>();
		
		// TODO  Shape ����
		xLoc = 20; yLoc = 20;
		int selectBlockBeginYLoc = yLoc - 10;
		
		List<Shape> selectShapeList = getSelectModel(queryInfo.getSelectStmtInfo());
		if(selectShapeList.size() > 0){
			yLoc = yLoc + 20;
			BlockShape selectBlockShape = this.getBlockShape("SELECT ��", yLoc - selectBlockBeginYLoc, selectBlockBeginYLoc);
			selectShapeList.add(0, selectBlockShape);
		}
		queryShapeList.addAll(selectShapeList);
		
		// TODO FROM Shape ����
		yLoc = yLoc + 20;
		int fromBlockBeginYLoc = yLoc;
		
		xLoc = 20;
		yLoc = yLoc + 10;
		
		List<Shape> fromShapeList = getFromModel(queryInfo.getFromStmtInfo());
		if(fromShapeList.size() > 0){
			yLoc = yLoc + 20;
			BlockShape fromBlockShape = this.getBlockShape("FROM ��", yLoc - fromBlockBeginYLoc, fromBlockBeginYLoc);
			fromShapeList.add(0, fromBlockShape);
		}
		queryShapeList.addAll(fromShapeList);
		
		// TODO WHERE Shape ����
		yLoc = yLoc + 20;
		int whereBlockBeginYLoc = yLoc;
		
		xLoc = 20; yLoc = yLoc + 20;
		
		List<Shape> WhereShapeList = getWhereModel(queryInfo.getWhereStmtInfo(), 0);
		if(WhereShapeList.size() > 0){
			// WHERE ���� �������� 40�� �� ���ϹǷ� ���� size�� �÷����� ����.
			BlockShape whereBlockShape = this.getBlockShape("WHERE ��", yLoc - whereBlockBeginYLoc, whereBlockBeginYLoc);
			WhereShapeList.add(0, whereBlockShape);
		}
		queryShapeList.addAll(WhereShapeList);

		// TODO �ٸ� Statement
		
		return queryShapeList;
	}

	// SELECT Info�� Shape�� ����
	private List<Shape> getSelectModel(List<QueryComponentType> selectInfoList){
		final int figureHeight = 20;
		
		List<Shape> selectShapeList = new ArrayList<Shape>();
		
		for(int i = 0; i < selectInfoList.size(); i++){
			QueryComponentType selectInfo = selectInfoList.get(i);
			SelectShape selectShape = new SelectShape();
			
			if(selectInfo instanceof ColumnInfo){
				ColumnInfo columnInfo = (ColumnInfo)selectInfo;
				
				selectShape.setTableName(columnInfo.getTableName()); // ���̺� ��
				selectShape.addColumnToList(columnInfo.getColumnName()); // �÷� ��
				
			}else if(selectInfo instanceof ConstInfo){
				ConstInfo constInfo = (ConstInfo)selectInfo;
				
				selectShape.setTableName("���(Const)"); // ���̺� ��
				selectShape.addColumnToList(constInfo.getConstValue() + "<" + constInfo.getTypeName() + ">"); // �÷� ��
				
			}else if(selectInfo instanceof FunctionInfo){
				// TODO
				
			}else if(selectInfo instanceof SubQueryInfo){
				SubQueryInfo subQueryInfo = new SubQueryInfo();
				
				// TODO selectShape�� �ƴ϶� �׳� Shape�� ������ ���� �� ��.
			}
			
			selectShape.setSize(new Dimension(80, figureHeight));
			selectShape.setLocation(new Point(xLoc, yLoc));
			
			xLoc = xLoc + 80 + 20;
			if(BLOCK_HORIZONTAL_SIZE < xLoc){
				xLoc = 20;
				yLoc = yLoc + figureHeight;
			}
			
			selectShapeList.add(selectShape);
		}
		
		yLoc = yLoc + figureHeight;
		
		return selectShapeList;
	}
	
	private List<Shape> getFromModel(List<TableViewType> fromInfoList){ 
		int fromHeight = 40;
		
		List<Shape> fromShapeList = new ArrayList<Shape>();
		
		for(int i = 0; i < fromInfoList.size(); i++){
			TableViewType fromInfo = fromInfoList.get(i);
			FromShape fromShape = new FromShape();
			
			if(fromInfo instanceof TableInfo){
				TableInfo tableInfo = (TableInfo)fromInfo;
				fromShape.setTableName(tableInfo.getTableName());
				fromShape.setAlias(tableInfo.getAlias());
				
				
			}else if(fromInfo instanceof SubQueryInfo){
				SubQueryInfo subQueryInfo = (SubQueryInfo)fromInfo;
				fromShape.setTableName(subQueryInfo.getCurrentQueryId());
				fromShape.setAlias(subQueryInfo.getAlias());
			}
			
			fromShape.setSize(new Dimension(80, fromHeight));
			fromShape.setLocation(new Point(xLoc, yLoc));
			
			xLoc = xLoc + 80 + 40;
			if(BLOCK_HORIZONTAL_SIZE < xLoc){
				xLoc = 20;
				yLoc = yLoc + fromHeight + 20;
			}
			
			fromShapeList.add(fromShape);
		}
		
		yLoc = yLoc + fromHeight;
		
		return fromShapeList;
	}
	
	// WHERE Info�� Shape�� ����
	private List<Shape> getWhereModel(WhereInfo whereInfo, int depth){
		List<Shape> whereShapeList = new ArrayList<Shape>();
		
		List<WhereType> whereInfoList = whereInfo.getValueList();
		
		// �� Condition �Ǵ� child
		for(int i = 0; i < whereInfoList.size(); i++){
			WhereType value = whereInfoList.get(i);
			
			if(value instanceof ConditionInfo){ // A = B ������ ConditionType
				WhereShape whereShape = new WhereShape();
				
				ConditionInfo conditionInfo = (ConditionInfo)value;

				// �� ������ ����
				whereShape.setComparisionOp(conditionInfo.getComparisionOp());
				
				// �ҽ��� ����
				QueryComponentType sourceValue = conditionInfo.getSourceValue();
				if(sourceValue instanceof ColumnInfo){
					ColumnInfo columnInfo = (ColumnInfo)sourceValue;
					
					whereShape.setSourceColumn1(columnInfo.getTableName());
					whereShape.setSourceColumn2("[" + columnInfo.getColumnName() + "]");
					
				} else if(sourceValue instanceof ConstInfo){
					ConstInfo constInfo = (ConstInfo)sourceValue;
					
					whereShape.setSourceColumn1(constInfo.getConstValue());
					whereShape.setSourceColumn2("<" + constInfo.getTypeName() + ">");
			
				} else if(sourceValue instanceof SubQueryInfo){
					SubQueryInfo subQueryInfo = (SubQueryInfo)sourceValue;
					
					whereShape.setSourceColumn1(subQueryInfo.getCurrentQueryId());
					whereShape.setSourceColumn2("< : " + subQueryInfo.getAlias() + ">");
					
				} else {
					// TODO Function�� ��� ó������ �߰��� ��.
					
				}
				
				// Ÿ�ٰ� ����
				QueryComponentType targetValue = conditionInfo.getTargetValue();
				if(targetValue instanceof ColumnInfo){
					ColumnInfo columnInfo = (ColumnInfo)targetValue;
					
					whereShape.setTargetColumn1(columnInfo.getTableName());
					whereShape.setTargetColumn2("[" + columnInfo.getColumnName() + "]");
					
				} else if(targetValue instanceof ConstInfo){
					ConstInfo constInfo = (ConstInfo)targetValue;
					
					whereShape.setTargetColumn1(constInfo.getConstValue());
					whereShape.setTargetColumn2("<" + constInfo.getTypeName() + ">");
			
				} else if(sourceValue instanceof SubQueryInfo){
					SubQueryInfo subQueryInfo = (SubQueryInfo)sourceValue;
					
					whereShape.setTargetColumn1(subQueryInfo.getCurrentQueryId());
					whereShape.setTargetColumn2("< : " + subQueryInfo.getAlias() + ">");
					
				} else {
					// TODO Function�� ��� ó������ �߰��� ��.
					
				}
				
				whereShape.setSize(new Dimension(200, 20));
				whereShape.setLocation(new Point(xLoc, yLoc));
				
				yLoc = yLoc + 40;
				
				whereShapeList.add(whereShape);
				
			} else if(value instanceof WhereInfo){
				yLoc = yLoc + 20;
				xLoc = xLoc + 40 * (depth + 1);
				
				List<Shape> subWhereShapeList = this.getWhereModel((WhereInfo)value, depth + 1);
				
				yLoc = yLoc + 20;
				xLoc = xLoc - 40 * (depth + 1);
				
				whereShapeList.addAll(subWhereShapeList);
			}
		}

		return whereShapeList;
	}
	
	private BlockShape getBlockShape(String blockName, int verticalLength, int verticalLocation){
		BlockShape blockShape = new BlockShape(blockName);
		blockShape.setSize(new Dimension(BLOCK_HORIZONTAL_SIZE, verticalLength));
		blockShape.setLocation(new Point(BLOCK_HORIZONTAL_LOCATION, verticalLocation));
		
		return blockShape;
	}
	
}
