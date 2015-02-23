package query.infoModelMapper;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

import query.parser.vo.ColumnInfo;
import query.parser.vo.ConditionInfo;
import query.parser.vo.ConstInfo;
import query.parser.vo.FunctionInfo;
import query.parser.vo.QueryComponentType;
import query.parser.vo.QueryInfo;
import query.parser.vo.WhereInfo;
import query.parser.vo.WhereType;
import query.vql.view.model.BlockShape;
import query.vql.view.model.SelectShape;
import query.vql.view.model.WhereShape;

public class InfoModelMapper {
	private QueryInfo queryInfo = new QueryInfo();

	public QueryInfo getQueryInfo() {
		return queryInfo;
	}

	public void setQueryInfo(QueryInfo queryInfo) {
		this.queryInfo = queryInfo;
	}
	
	public BlockShape getSelectBlock(){
		BlockShape blockShape = new BlockShape("SELECT ��");
		
		blockShape.addChild(this.getSelectModel());
		
		return blockShape;
	}
	
	public List<SelectShape> getSelectModel(){
		List<SelectShape> selectShapeList = new ArrayList<SelectShape>();
		
		List<QueryComponentType> selectInfoList = queryInfo.getSelectInfo();
		
		int xLoc = 10;
		
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
			}
			
			selectShape.setSize(new Dimension(80, 20));
			selectShape.setLocation(new Point(xLoc, 10));
			
			xLoc = xLoc + 100;
			
			selectShapeList.add(selectShape);
		}
		
		return selectShapeList;
	}
	
	public List<WhereShape> getWhereModel(){
		List<WhereShape> whereShapeList = new ArrayList<WhereShape>();
		
		WhereInfo whereInfo = queryInfo.getWhereInfo();
		
		List<WhereType> valueList = whereInfo.getValueList();
		
		int yLoc = 100;
		
		// �� Condition �Ǵ� child
		for(int i = 0; i < valueList.size(); i++){
			WhereType value = valueList.get(i);
			
			WhereShape whereShape = new WhereShape();
			
			if(value instanceof ConditionInfo){ // A = B ������ ConditionType
				ConditionInfo conditionInfo = (ConditionInfo)value;

				// �� ������ ����
				whereShape.setComparisionOp(conditionInfo.getComparisionOp());
				
				// �ҽ��� ����
				QueryComponentType sourceValue = conditionInfo.getSourceValue();
				if(sourceValue instanceof ColumnInfo){
					ColumnInfo columnInfo = (ColumnInfo)sourceValue;
					
					whereShape.setSourceColumn1(columnInfo.getTableName());
					whereShape.setSourceColumn2(columnInfo.getColumnName());
					
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
					whereShape.setTargetColumn2(columnInfo.getColumnName());
					
				} else if(targetValue instanceof ConstInfo){
					ConstInfo constInfo = (ConstInfo)targetValue;
					
					whereShape.setTargetColumn1(constInfo.getConstValue());
					whereShape.setTargetColumn2("<" + constInfo.getTypeName() + ">");
			
				} else {
					// TODO
					// Function�� ��쿡�� Subquery�� ��� ó������ �߰��� ��.
				}
				
				
			}else if(value instanceof WhereInfo){
				// TODO
			}
			
			whereShape.setSize(new Dimension(200, 20));
			whereShape.setLocation(new Point(10, yLoc));
			
			yLoc = yLoc + 40;
			
			whereShapeList.add(whereShape);
		}

		return whereShapeList;
	}
	
}
