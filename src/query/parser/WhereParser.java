package query.parser;

import java.util.ArrayList;
import java.util.List;

import query.parser.vo.ConditionInfo;
import query.parser.vo.WhereInfo;
import query.parser.vo.WhereType;
import query.parser.vo.WhereInfo;

public class WhereParser {
	// TODO ��ø�� WHERE ���� ó���� ��!!
	// 			SubQueryParser�� otherBracketMap�� �̿��ؼ� ��ȣ�ȿ� condition�鵵 ��� ó���� �� �ֵ���!!
	// TODO parsingWhereStatement �Լ� �����丵 �� ��.
	
	// WHERE Statement�� parsing
	public WhereInfo parsingWhereStatement(String contents) throws Exception{
		contents = contents.trim();
		
		WhereInfo whereInfo = new WhereInfo();
		List<WhereType> conditionList = new ArrayList<WhereType>();
		
		String[] splitContentArray = null;
		
		// AND �����ڰ� OR �����ں��� �켱�� �ǹǷ� OR�� ���� ���´�. 
		if(WhereInfo.hasOrOperator(contents)){ // ���� LEVEL�� OR �����ڰ� �ִ� ���
			whereInfo.setRelationOp(QueryCommVar.OR);
			
			splitContentArray = contents.split(QueryCommVar.OR);
			
			for(int i = 0; i < splitContentArray.length; i++){
				WhereType subWhereType = null;
				
				String splitContent = splitContentArray[i].trim();
				if(WhereInfo.isWhereInfo(splitContent)){ // SUB CONDITION
					subWhereType = this.parsingWhereStatement(splitContentArray[i].trim());
					
				}else{ // ���� CONDITION
					subWhereType = ConditionInfo.convertStringToInfo(splitContentArray[i].trim());
				}
				conditionList.add(subWhereType);
			}
			
		} else if((WhereInfo.hasAndOperator(contents))) { // OR �����ڰ� ���� ��� AND �����ڰ� �ִ��� Ȯ��
			whereInfo.setRelationOp(QueryCommVar.AND);
			
			splitContentArray = contents.split(QueryCommVar.AND);
			
			for(int i = 0; i < splitContentArray.length; i++){
				WhereType subWhereType = null;
				
				String splitContent = splitContentArray[i].trim();
				if(WhereInfo.isWhereInfo(splitContent)){ // SUB CONDITION
					subWhereType = this.parsingWhereStatement(splitContentArray[i].trim());
					
				}else{ // ���� CONDITION
					subWhereType = ConditionInfo.convertStringToInfo(splitContentArray[i].trim());
				}
				conditionList.add(subWhereType);
			}
			
		} else if(contents.length() > 0){ // ������ �Ѱ� �ִ� ���
			ConditionInfo conditionInfo = ConditionInfo.convertStringToInfo(contents);
			conditionList.add(conditionInfo);
			
		}else{ // ����ִ� ����
			throw new Exception("WHERE Condition�� ��� ����");
		}

		whereInfo.setValueList(conditionList);
		
		return whereInfo;
	}
}
