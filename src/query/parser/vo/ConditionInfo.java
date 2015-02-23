package query.parser.vo;

import java.util.ArrayList;
import java.util.List;

import query.parser.QueryCommVar;

public class ConditionInfo implements WhereType {
	
	// 비교연산자 명
	private String comparisionOp = "";

	private QueryComponentType[] valueList = new QueryComponentType[2];

	public String getComparisionOp() {
		return comparisionOp;
	}

	public void setComparisionOp(String comparisionOp) {
		this.comparisionOp = comparisionOp;
	}

	public void setSourceValue(QueryComponentType sourceValue){
		valueList[0] = sourceValue;
	}
	
	public QueryComponentType getSourceValue(){
		return valueList[0];
	}
	
	public void setTargetValue(QueryComponentType targetValue){
		valueList[1] = targetValue;
	}
	
	public QueryComponentType getTargetValue(){
		return valueList[1];
	}
	
	public String toString(){
		String result = "<비교연산자 : [" + this.comparisionOp + "] >\n";
		
		for(int i = 0; i < valueList.length; i++){
			result = result + valueList[i] + "\n";
		}
		
		return result;
	}
	
	public static ConditionInfo convertStringToInfo(String value) throws Exception{
		ConditionInfo conditionInfo = new ConditionInfo();
		
		for(int j = 0; j < QueryCommVar.COMPARISION_OP_LIST.length; j++){
			if(value.indexOf(QueryCommVar.COMPARISION_OP_LIST[j]) > 0){
				String[] splitCondition = value.split(QueryCommVar.COMPARISION_OP_LIST[j]);
				
				conditionInfo.setComparisionOp(QueryCommVar.COMPARISION_OP_LIST[j]);
				
				for(int k = 0; k < splitCondition.length; k++){
					QueryComponentType queryComponentType = null;
					
					String selectStmt = splitCondition[k].trim();
					
					if(ColumnInfo.isColumnType(selectStmt)){
						queryComponentType = ColumnInfo.convertStringToInfo(selectStmt);
						
					}else if(ConstInfo.isConstType(selectStmt)){
						queryComponentType = ConstInfo.convertStringToInfo(selectStmt);
						
					}else{
						throw new Exception("잘못된 SELECT절 형식");
					}
					
					
					// TODO 임시로 첫번째가 Source 두번째가 Target으로 설정
					if(k == 0){
						conditionInfo.setSourceValue(queryComponentType);
					}else {
						conditionInfo.setTargetValue(queryComponentType);
					}
				}
				
				break;
			}
		}
		
		return conditionInfo;
	}
}
