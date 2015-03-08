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
		
		yLoc = yLoc + 50;
		
		// SubQuery
		Map<String, QueryInfo> subQueryMap = visualQueryInfo.getSubQueryMap();
		for(String subQueryKey: subQueryMap.keySet()){
			QueryInfo subQueryInfo = subQueryMap.get(subQueryKey);
			List<Shape> subQueryShapeList = this.convertQueryInfoToShape(subQueryInfo);
			queryShapeList.addAll(subQueryShapeList);
			
			yLoc = yLoc + 50;
		}
		
		return queryShapeList;
	}
	
	private List<Shape> convertQueryInfoToShape(QueryInfo queryInfo){
		List<Shape> queryShapeList = new ArrayList<Shape>();
		
		int queryBlockBegin_yLoc = yLoc + 10;

		yLoc = yLoc + 20;
		xLoc = 20;
		
		BlockShape subQueryTestBlockShape = this.getBlockShape(queryInfo.getQueryId(), 40, yLoc);
		queryShapeList.add(0, subQueryTestBlockShape);
		yLoc = yLoc + 60;
		
		int selectBlockBeginYLoc = yLoc - 10;
		
		List<Shape> selectShapeList = getSelectModel(queryInfo.getSelectStmtInfo());
		queryShapeList.addAll(selectShapeList);
		
		if(selectShapeList.size() > 0){
			yLoc = yLoc + 20;
			BlockShape selectBlockShape = this.getBlockShape("SELECT ��", yLoc - selectBlockBeginYLoc, selectBlockBeginYLoc);
			queryShapeList.add(0, selectBlockShape);
		}
		
		// TODO FROM Shape ����
		yLoc = yLoc + 10;
		int fromBlockBeginYLoc = yLoc;
		
		xLoc = 20;
		yLoc = yLoc + 10;
		
		List<Shape> fromShapeList = getFromModel(queryInfo.getFromStmtInfo());
		queryShapeList.addAll(fromShapeList);
		
		if(fromShapeList.size() > 0){
			yLoc = yLoc + 20;
			BlockShape fromBlockShape = this.getBlockShape("FROM ��", yLoc - fromBlockBeginYLoc, fromBlockBeginYLoc);
			queryShapeList.add(0, fromBlockShape);
		}
		
		// TODO WHERE Shape ����
		yLoc = yLoc + 10;
		int whereBlockBeginYLoc = yLoc;
		
		xLoc = 40; yLoc = yLoc + 20;
		
		List<Shape> WhereShapeList = getWhereModel(queryInfo.getWhereStmtInfo(), 0);
		queryShapeList.addAll(WhereShapeList);
		
		if(WhereShapeList.size() > 0){
			// WHERE ���� �������� 40�� �� ���ϹǷ� ���� size�� �÷����� ����.
			BlockShape whereBlockShape = this.getBlockShape("WHERE ��", yLoc - whereBlockBeginYLoc, whereBlockBeginYLoc);
			queryShapeList.add(0, whereBlockShape);
		}

		// TODO �ٸ� Statement
		
		
		if(WhereShapeList.size() > 0){
			BlockShape whereBlockShape = this.getQueryBlockShape(yLoc - queryBlockBegin_yLoc + 10, queryBlockBegin_yLoc);
			queryShapeList.add(0, whereBlockShape);
		}
		
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
		
		int opBegin_yLoc = yLoc - 10;
		int opBegin_xLoc = xLoc - 20;
		
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
				WhereInfo subWhereInfo = (WhereInfo)value;
				yLoc = yLoc + 20;
				xLoc = xLoc + 20 * (depth + 1);
				
				List<Shape> subWhereShapeList = this.getWhereModel(subWhereInfo, depth + 1);
				
				yLoc = yLoc + 20;
				xLoc = xLoc - 10 * (depth + 1);
				
				whereShapeList.addAll(subWhereShapeList);
			}
		}
		
		int opEnd_yLoc = yLoc - 10;
		
		// TODO AND, OR ����� �ϴ� �ӽ÷� statementBlock�� ������ BlockShape�� ���
		if(whereShapeList.size() > 0){
			BlockShape blockShape = this.getWhereOperatorBlockShape(whereInfo.getRelationOp(), opBegin_xLoc, opBegin_yLoc, opEnd_yLoc);
			whereShapeList.add(0, blockShape);
		}

		return whereShapeList;
	}
	
	private BlockShape getWhereOperatorBlockShape(String opName, int begin_xLoc, int begin_yLoc, int end_yLoc){
		BlockShape operatorBlock = new BlockShape(opName);
		operatorBlock.setLocation(new Point(begin_xLoc, begin_yLoc));
		operatorBlock.setSize(new Dimension(50, end_yLoc - begin_yLoc));
		
		return operatorBlock;
	}
	
	private BlockShape getBlockShape(String blockName, int verticalLength, int verticalLocation){
		BlockShape blockShape = new BlockShape(blockName);
		blockShape.setSize(new Dimension(BLOCK_HORIZONTAL_SIZE, verticalLength));
		blockShape.setLocation(new Point(BLOCK_HORIZONTAL_LOCATION, verticalLocation));
		
		return blockShape;
	}
	
	private BlockShape getQueryBlockShape(int verticalLength, int verticalLocation){
		BlockShape blockShape = new BlockShape("");
		blockShape.setSize(new Dimension(BLOCK_HORIZONTAL_SIZE + 20, verticalLength));
		blockShape.setLocation(new Point(BLOCK_HORIZONTAL_LOCATION - 10, verticalLocation));
		
		return blockShape;
	}
	
}
