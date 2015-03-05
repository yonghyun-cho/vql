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
import query.parser.vo.VisualQueryInfo;
import query.parser.vo.WhereInfo;
import query.parser.vo.WhereType;
import query.vql.view.model.BlockShape;
import query.vql.view.model.SelectShape;
import query.vql.view.model.WhereShape;

public class InfoModelMapper {
	private VisualQueryInfo visualQueryInfo = new VisualQueryInfo();
	
	int yLoc = 0;
	int xLoc = 0;
	
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
		
		// SELECT Shape ����
		List<Shape> selectShapeList = getSelectModel(queryInfo);
		queryShapeList.addAll(selectShapeList);
		
		// TODO WHERE Shape ����
		
		// WHERE Shape ����
		xLoc = 20;
		yLoc = 100;
		
		List<Shape> WhereShapeList = getWhereModel(queryInfo.getWhereStmtInfo(), 0);
		queryShapeList.addAll(WhereShapeList);

		// TODO �ٸ� Statement
		
		return queryShapeList;
	}

	// SELECT Info�� Shape�� ����
	private List<Shape> getSelectModel(QueryInfo queryInfo){
		List<Shape> selectShapeList = new ArrayList<Shape>();
		
		List<QueryComponentType> selectInfoList = queryInfo.getSelectStmtInfo();
		
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
			
			selectShape.setSize(new Dimension(80, 20));
			selectShape.setLocation(new Point(xLoc, 20));
			
			xLoc = xLoc + 100;
			
			selectShapeList.add(selectShape);
		}
		
		if(selectShapeList.size() > 0){
			BlockShape blockShape = new BlockShape("SELECT ��");
			blockShape.setSize(new Dimension(500, 60));
			blockShape.setLocation(new Point(10, 10));
			
			selectShapeList.add(0, blockShape);
		}
		
		return selectShapeList;
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
			
				} else {
					// TODO
					// Function�� ��쿡�� Subquery�� ��� ó������ �߰��� ��.
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
			
				} else {
					// TODO
					// Function�� ��쿡�� Subquery�� ��� ó������ �߰��� ��.
				}
				
				whereShape.setSize(new Dimension(200, 20));
				whereShape.setLocation(new Point(xLoc, yLoc));
				
				yLoc = yLoc + 40;
				
				whereShapeList.add(whereShape);
				
			} else if(value instanceof WhereInfo){
				
				xLoc = xLoc + 40 * depth;
				List<Shape> subWhereShapeList = this.getWhereModel((WhereInfo)value, depth + 1);
				xLoc = xLoc - 40 * depth;
				
				whereShapeList.addAll(subWhereShapeList);
			}
		}
		
		if(whereShapeList.size() > 0){
			BlockShape blockShape = new BlockShape("WHERE ��");
			blockShape.setSize(new Dimension(500, yLoc));
			blockShape.setLocation(new Point(10, 90));
			
			whereShapeList.add(0, blockShape);
		}

		return whereShapeList;
	}
	
}
