package query.parser.vo;

abstract public class QueryComponentType {

	/**
	 * 해당 value가 QueryComponentType으로 변환될 수 있는지 확인
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static boolean isQueryComponentType(String value) throws Exception{
		return PrimitiveType.isPrimitiveType(value) || TableViewType.isTableViewType(value) || FunctionInfo.isFunctionId(value);
	}
	
	/**
	 * StringInfo, ConstInfo, TableInfo : String값을 PrimitiveType중 하나로 변환<br/>
	 * SubQueryInfo, FunctionInfo : 변환된 id값을 입력. (실제 Info 구조는 List에 String으로 있고 나중에 parsing)
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static QueryComponentType convertStringToType(String value) throws Exception{
		QueryComponentType queryComponentType = null;
		
		if(PrimitiveType.isPrimitiveType(value)){
			queryComponentType = PrimitiveType.convertStringToType(value);
			
		} else if(TableViewType.isTableViewType(value)) { 
			queryComponentType = TableViewType.convertStringToType(value);
			
		} else if(FunctionInfo.isFunctionId(value)){
			queryComponentType = FunctionInfo.convertIdToInfo(value);
				
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
