package query.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import query.parser.vo.ConditionInfo;
import query.parser.vo.WhereInfo;
import query.parser.vo.WhereType;
import query.parser.vo.WhereInfo;

// TODO Function처리

public class WhereParser {

	// 분리된 함수 목록
	private Map<String, String> functionMap = new HashMap<String, String>();
	
	// 분리된 괄호 목록
	private Map<String, String> otherBracketMap = new HashMap<String, String>();
	
	public WhereInfo parsingWhereStatement(String contents, Map<String, String> functionMap, Map<String, String> otherBracketMap) throws Exception{
		WhereInfo whereInfo = null;
		
		this.functionMap = functionMap;
		this.otherBracketMap = otherBracketMap;
		
		WhereType wherType = this.parsingSubCondtion(contents);
		
		// WHERE절에 조건이 1개인 경우.
		if(wherType instanceof ConditionInfo){
			whereInfo = new WhereInfo();
			whereInfo.addValueToList(wherType);
		
		// WHERE절에 조건이 2개 이상인 경우.
		} else {
			whereInfo = (WhereInfo)wherType;
		}
		
		return whereInfo;
	}
	
	// WHERE Statement를 parsing
	private WhereType parsingSubCondtion(String contents) throws Exception{
		WhereType whereType = null;
		
		contents = contents.trim();
		if(WhereInfo.isWhereInfo(contents)){
			String operator = "";
			if(WhereInfo.hasOrOperator(contents)){ // 같은 LEVEL에 OR 연산자가 있는 경우
				operator = QueryCommVar.OR;
				
			} else if((WhereInfo.hasAndOperator(contents))) { // OR 연산자가 없는 경우 AND 연산자가 있는지 확인
				operator = QueryCommVar.AND;
			}
			
			WhereInfo whereInfo = new WhereInfo();
			whereInfo.setRelationOp(operator);
			
			String[] splitContentArray = contents.split(operator);
			List<WhereType> conditionList = this.parsingSubCondition(splitContentArray);
			whereInfo.setValueList(conditionList);
			
			whereType = whereInfo;
			
		// 조건이 한개 있는 경우
		} else if(contents.length() > 0){ 
			if(WhereInfo.isSubConditionType(contents)){ // SubConditionId인 경우
				String subConditionString = this.otherBracketMap.get(contents);
				whereType = this.parsingSubCondtion(subConditionString);
				
			}else{ // 한개의 Condition인 경우. (recursive의 마지막)
				whereType = ConditionInfo.convertStringToInfo(contents);
			}
			
		}else{ // 비어있는 상태
			throw new Exception("WHERE Condition이 비어 있음");
		}
		
		return whereType;
	}
	
	private List<WhereType> parsingSubCondition(String[] splitContentArray) throws Exception{
		List<WhereType> conditionList = new ArrayList<WhereType>();
		
		for(int i = 0; i < splitContentArray.length; i++){
			WhereType subCondition = null;
			
			String splitContent = splitContentArray[i].trim();
			
			if(WhereInfo.isWhereInfo(splitContent)){ // SUB CONDITION
				subCondition = this.parsingSubCondtion(splitContentArray[i].trim());
				
			}else if(WhereInfo.isSubConditionType(splitContent)){ // SubConditionId인 경우
				String subConditionString = this.otherBracketMap.get(splitContent);
				subCondition = this.parsingSubCondtion(subConditionString);
				
			}else{ // 한개의 Condition인 경우. (recursive의 마지막)
				subCondition = ConditionInfo.convertStringToInfo(splitContent);
			}
				
			conditionList.add(subCondition);
		}
		
		return conditionList;
	}
}
