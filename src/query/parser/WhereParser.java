package query.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import query.parser.vo.ConditionInfo;
import query.parser.vo.WhereInfo;
import query.parser.vo.WhereType;
import query.parser.vo.WhereInfo;

// TODO Functionó��

public class WhereParser {

	// �и��� �Լ� ���
	private Map<String, String> functionMap = new HashMap<String, String>();
	
	// �и��� ��ȣ ���
	private Map<String, String> otherBracketMap = new HashMap<String, String>();
	
	public WhereInfo parsingWhereStatement(String contents, Map<String, String> functionMap, Map<String, String> otherBracketMap) throws Exception{
		WhereInfo whereInfo = null;
		
		this.functionMap = functionMap;
		this.otherBracketMap = otherBracketMap;
		
		WhereType wherType = this.parsingSubCondtion(contents);
		
		// WHERE���� ������ 1���� ���.
		if(wherType instanceof ConditionInfo){
			whereInfo = new WhereInfo();
			whereInfo.addValueToList(wherType);
		
		// WHERE���� ������ 2�� �̻��� ���.
		} else {
			whereInfo = (WhereInfo)wherType;
		}
		
		return whereInfo;
	}
	
	// WHERE Statement�� parsing
	private WhereType parsingSubCondtion(String contents) throws Exception{
		WhereType whereType = null;
		
		contents = contents.trim();
		if(WhereInfo.isWhereInfo(contents)){
			String operator = "";
			if(WhereInfo.hasOrOperator(contents)){ // ���� LEVEL�� OR �����ڰ� �ִ� ���
				operator = QueryCommVar.OR;
				
			} else if((WhereInfo.hasAndOperator(contents))) { // OR �����ڰ� ���� ��� AND �����ڰ� �ִ��� Ȯ��
				operator = QueryCommVar.AND;
			}
			
			WhereInfo whereInfo = new WhereInfo();
			whereInfo.setRelationOp(operator);
			
			String[] splitContentArray = contents.split(operator);
			List<WhereType> conditionList = this.parsingSubCondition(splitContentArray);
			whereInfo.setValueList(conditionList);
			
			whereType = whereInfo;
			
		// ������ �Ѱ� �ִ� ���
		} else if(contents.length() > 0){ 
			if(WhereInfo.isSubConditionType(contents)){ // SubConditionId�� ���
				String subConditionString = this.otherBracketMap.get(contents);
				whereType = this.parsingSubCondtion(subConditionString);
				
			}else{ // �Ѱ��� Condition�� ���. (recursive�� ������)
				whereType = ConditionInfo.convertStringToInfo(contents);
			}
			
		}else{ // ����ִ� ����
			throw new Exception("WHERE Condition�� ��� ����");
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
				
			}else if(WhereInfo.isSubConditionType(splitContent)){ // SubConditionId�� ���
				String subConditionString = this.otherBracketMap.get(splitContent);
				subCondition = this.parsingSubCondtion(subConditionString);
				
			}else{ // �Ѱ��� Condition�� ���. (recursive�� ������)
				subCondition = ConditionInfo.convertStringToInfo(splitContent);
			}
				
			conditionList.add(subCondition);
		}
		
		return conditionList;
	}
}
