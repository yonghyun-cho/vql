package query.parser.vo;

public class QueryComponentType {
	public static boolean isQueryComponenetType(String value) throws Exception{
		return PrimitiveType.isPrimitiveType(value) || SubQueryInfo.isSubQueryType(value);
	}
	
	// String���� PrimitiveType�� �ϳ��� ��ȯ
	public static QueryComponentType convertStringToType(String value) throws Exception{
		QueryComponentType queryComponentType = null;
		
		if(PrimitiveType.isPrimitiveType(value)){
			queryComponentType = PrimitiveType.convertStringToType(value);
			
		} else if(TableViewType.isTableViewType(value)) { 
			queryComponentType = TableViewType.convertStringToType(value);
			
		} else {
			throw new Exception("�߸��� QueryComponentType ����");
		}
		
		return queryComponentType;
	}
}
