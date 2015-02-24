package query.parser.vo;

public class QueryComponentType {
	public static boolean isQueryComponenetType(String value){
		return PrimitiveType.isPrimitiveType(value) || true; // TODO SubQueryInfo �� is... �Լ� �����Ͽ� �߰��� ��.
	}
	
	// String���� PrimitiveType�� �ϳ��� ��ȯ
	public static QueryComponentType convertStringToInfo(String value) throws Exception{
		QueryComponentType queryComponentType = null;
		
		if(PrimitiveType.isPrimitiveType(value)){
			queryComponentType = PrimitiveType.convertStringToInfo(value);
			
		} else if(SubQueryInfo.isSubQueryType(value)) { 
			queryComponentType = SubQueryInfo.convertStringToInfo(value);
			
		} else {
			throw new Exception("�߸��� QueryComponentType ����");
		}
		
		return queryComponentType;
	}
}
