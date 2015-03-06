package query.parser;

import java.util.ArrayList;
import java.util.List;

import query.parser.vo.ConditionInfo;
import query.parser.vo.WhereInfo;
import query.parser.vo.WhereType;
import query.parser.vo.WhereInfo;

public class WhereParser {
	// TODO 중첩된 WHERE 조건 처리할 것!!
	// 			SubQueryParser의 otherBracketMap를 이용해서 괄호안에 condition들도 모두 처리할 수 있도록!!
	// TODO parsingWhereStatement 함수 리펙토링 할 것.
	
	// WHERE Statement를 parsing
	public WhereInfo parsingWhereStatement(String contents) throws Exception{
		contents = contents.trim();
		
		WhereInfo whereInfo = new WhereInfo();
		List<WhereType> conditionList = new ArrayList<WhereType>();
		
		String[] splitContentArray = null;
		
		// AND 연산자가 OR 연산자보다 우선시 되므로 OR를 먼저 끊는다. 
		if(WhereInfo.hasOrOperator(contents)){ // 같은 LEVEL에 OR 연산자가 있는 경우
			whereInfo.setRelationOp(QueryCommVar.OR);
			
			splitContentArray = contents.split(QueryCommVar.OR);
			
			for(int i = 0; i < splitContentArray.length; i++){
				WhereType subWhereType = null;
				
				String splitContent = splitContentArray[i].trim();
				if(WhereInfo.isWhereInfo(splitContent)){ // SUB CONDITION
					subWhereType = this.parsingWhereStatement(splitContentArray[i].trim());
					
				}else{ // 단일 CONDITION
					subWhereType = ConditionInfo.convertStringToInfo(splitContentArray[i].trim());
				}
				conditionList.add(subWhereType);
			}
			
		} else if((WhereInfo.hasAndOperator(contents))) { // OR 연산자가 없는 경우 AND 연산자가 있는지 확인
			whereInfo.setRelationOp(QueryCommVar.AND);
			
			splitContentArray = contents.split(QueryCommVar.AND);
			
			for(int i = 0; i < splitContentArray.length; i++){
				WhereType subWhereType = null;
				
				String splitContent = splitContentArray[i].trim();
				if(WhereInfo.isWhereInfo(splitContent)){ // SUB CONDITION
					subWhereType = this.parsingWhereStatement(splitContentArray[i].trim());
					
				}else{ // 단일 CONDITION
					subWhereType = ConditionInfo.convertStringToInfo(splitContentArray[i].trim());
				}
				conditionList.add(subWhereType);
			}
			
		} else if(contents.length() > 0){ // 조건이 한개 있는 경우
			ConditionInfo conditionInfo = ConditionInfo.convertStringToInfo(contents);
			conditionList.add(conditionInfo);
			
		}else{ // 비어있는 상태
			throw new Exception("WHERE Condition이 비어 있음");
		}

		whereInfo.setValueList(conditionList);
		
		return whereInfo;
	}
}
