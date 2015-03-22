package query.parser.vo;

abstract public class QueryComponentType {
	public static boolean isQueryComponenetType(String value) throws Exception{
		return PrimitiveType.isPrimitiveType(value) || SubQueryInfo.isSubQueryType(value);
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
	
	@Override
	// 임시로 각 Info에 hashCode를 설정하기엔 아직 변경사항이 많아서 무조건 0 을 반환하도록 수정함.
	// TODO 추후 성능 문제가 존재하면 제대로 구현할 것. (근데 어차피 equals 관련한건 Junit test에서만 쓰일거라..)
	public int hashCode() {
		return 0;
	}
}
