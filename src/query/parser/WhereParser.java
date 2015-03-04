package query.parser;

import query.parser.vo.ConditionInfo;
import query.parser.vo.WhereInfo;

public class WhereParser {
	// TODO ��ø�� WHERE ���� ó���� ��!!
	// WHERE Statement�� parsing
	public WhereInfo parsingWhereStatement(String contents) throws Exception{
		WhereInfo whereInfo = new WhereInfo();
		whereInfo.setRelationOp(QueryCommVar.AND);
		
		String[] splitContentArray = contents.split(QueryCommVar.AND);
		
		for(int i = 0; i < splitContentArray.length; i++){
			ConditionInfo conditionInfo = ConditionInfo.convertStringToInfo(splitContentArray[i].trim());
			whereInfo.addValueToList(conditionInfo);
		}
		
		return whereInfo;
	}
	
//	public void 
}
