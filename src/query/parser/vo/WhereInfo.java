package query.parser.vo;

import java.util.ArrayList;
import java.util.List;

import query.parser.QueryParserCommFunc;

public class WhereInfo implements WhereType{
	// 비교연산자
	private String relationOp = "";
	
	// Condition 목록
	private List<WhereType> conditionList = new ArrayList<WhereType>();

	public String getRelationOp() {
		return relationOp;
	}

	public void setRelationOp(String relationOp) {
		this.relationOp = relationOp;
	}

	public List<WhereType> getValueList() {
		return conditionList;
	}

	public void setValueList(List<WhereType> valueList) {
		this.conditionList = valueList;
	}
	
	public void addValueToList(WhereType value){
		this.conditionList.add(value);
	}
	
	// Condition (AND/OR) Condition 구조와 같은
	// 2개이 이상의 Condition으로 구성된 WhereInfo
	public static boolean isWhereInfo(String value){
		String regex = ".+[^a-zA-Z0-9_$#](AND|OR)[^a-zA-Z0-9_$#].+";
		
		return value.matches(regex);
	}
	
	public static boolean hasAndOperator(String value){
		String regex = ".+[^a-zA-Z0-9_$#]AND[^a-zA-Z0-9_$#].+";
		
		return value.matches(regex);
	}
	
	public static boolean hasOrOperator(String value){
		String regex = ".+[^a-zA-Z0-9_$#]OR[^a-zA-Z0-9_$#].+";
		
		return value.matches(regex);
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = super.equals(obj);
		
		if(super.equals(obj) == false){
			if(obj instanceof WhereInfo){ // 해당 Object가 WhereInfo 타입이고
				WhereInfo targetInfo = (WhereInfo)obj;
				
				if(targetInfo.getRelationOp().equals(this.relationOp)){ // 비교연산자가 동일할때
					// 현재까지 조건(relationOp)은 모두 만족했으니
					result = true;
					
					boolean isAllConditionEqual = false;
					
					for(int i = 0; i < this.conditionList.size(); i++){
						isAllConditionEqual = false;
						
						for( int j = 0; j < targetInfo.getValueList().size(); j++){
							if(this.conditionList.get(i).equals(targetInfo.getValueList().get(j))){
								isAllConditionEqual = true;
								break;
							}
						}
						
						result = result && isAllConditionEqual; // 지금까지의 모든 조건이 true이면
						
						if(result == false){
							break;
						}
					}
				}
				
			} else {
				result = false;
			}
		}
		
		return result;
	}
}
