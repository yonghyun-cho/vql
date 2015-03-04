package query.parser.vo;

public class QueryComponentType {
	public static boolean isQueryComponenetType(String value){
		return PrimitiveType.isPrimitiveType(value) || true; // TODO SubQueryInfo 의 is... 함수 생성하여 추가할 것.
	}
	
	// String값을 PrimitiveType중 하나로 변환
	public static QueryComponentType convertStringToType(String value) throws Exception{
		QueryComponentType queryComponentType = null;
		
		if(PrimitiveType.isPrimitiveType(value)){
			queryComponentType = PrimitiveType.convertStringToType(value);
			
		} else if(TableViewType.isTableViewType(value)) { 
			queryComponentType = TableViewType.convertStringToType(value);
			
		} else {
			throw new Exception("잘못된 QueryComponentType 형식");
		}
		
		return queryComponentType;
	}
}
