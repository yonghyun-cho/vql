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
		
		/* TODO 일단 이부분은 구현이 안된 부분이라 view에 보여주지 않기 위해 주석 처리.
		 * shape위치들만 잘 정리하면 괜찮을 듯.
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
		
		// SELECT Shape 변경
		List<Shape> selectShapeList = getSelectModel(queryInfo);
		queryShapeList.addAll(selectShapeList);
		
		// TODO WHERE Shape 변경
		
		// WHERE Shape 변경
		xLoc = 20;
		yLoc = 100;
		
		List<Shape> WhereShapeList = getWhereModel(queryInfo.getWhereStmtInfo(), 0);
		queryShapeList.addAll(WhereShapeList);

		// TODO 다른 Statement
		
		return queryShapeList;
	}

	// SELECT Info를 Shape로 변경
	private List<Shape> getSelectModel(QueryInfo queryInfo){
		List<Shape> selectShapeList = new ArrayList<Shape>();
		
		List<QueryComponentType> selectInfoList = queryInfo.getSelectStmtInfo();
		
		for(int i = 0; i < selectInfoList.size(); i++){
			QueryComponentType selectInfo = selectInfoList.get(i);
			SelectShape selectShape = new SelectShape();
			
			if(selectInfo instanceof ColumnInfo){
				ColumnInfo columnInfo = (ColumnInfo)selectInfo;
				
				selectShape.setTableName(columnInfo.getTableName()); // 테이블 명
				selectShape.addColumnToList(columnInfo.getColumnName()); // 컬럼 명
				
			}else if(selectInfo instanceof ConstInfo){
				ConstInfo constInfo = (ConstInfo)selectInfo;
				
				selectShape.setTableName("상수(Const)"); // 테이블 명
				selectShape.addColumnToList(constInfo.getConstValue() + "<" + constInfo.getTypeName() + ">"); // 컬럼 명
				
			}else if(selectInfo instanceof FunctionInfo){
				// TODO
				
			}else if(selectInfo instanceof SubQueryInfo){
				SubQueryInfo subQueryInfo = new SubQueryInfo();
				
				// TODO selectShape가 아니라 그냥 Shape로 쓰도록 변경 할 것.
			}
			
			selectShape.setSize(new Dimension(80, 20));
			selectShape.setLocation(new Point(xLoc, 20));
			
			xLoc = xLoc + 100;
			
			selectShapeList.add(selectShape);
		}
		
		if(selectShapeList.size() > 0){
			BlockShape blockShape = new BlockShape("SELECT 절");
			blockShape.setSize(new Dimension(500, 60));
			blockShape.setLocation(new Point(10, 10));
			
			selectShapeList.add(0, blockShape);
		}
		
		return selectShapeList;
	}
	
	// WHERE Info를 Shape로 변경
	private List<Shape> getWhereModel(WhereInfo whereInfo, int depth){
		List<Shape> whereShapeList = new ArrayList<Shape>();
		
		List<WhereType> whereInfoList = whereInfo.getValueList();
		
		// 각 Condition 또는 child
		for(int i = 0; i < whereInfoList.size(); i++){
			WhereType value = whereInfoList.get(i);
			
			if(value instanceof ConditionInfo){ // A = B 형식의 ConditionType
				WhereShape whereShape = new WhereShape();
				
				ConditionInfo conditionInfo = (ConditionInfo)value;

				// 비교 연산자 설정
				whereShape.setComparisionOp(conditionInfo.getComparisionOp());
				
				// 소스값 설정
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
					// Function인 경우에나 Subquery인 경우 처리로직 추가할 것.
				}
				
				// 타겟값 설정
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
					// Function인 경우에나 Subquery인 경우 처리로직 추가할 것.
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
			BlockShape blockShape = new BlockShape("WHERE 절");
			blockShape.setSize(new Dimension(500, yLoc));
			blockShape.setLocation(new Point(10, 90));
			
			whereShapeList.add(0, blockShape);
		}

		return whereShapeList;
	}
	
}
