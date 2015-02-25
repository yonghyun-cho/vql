package query.parser.vo;

public class SubQueryInfo extends QueryComponentType {
	
	// 현재 쿼리 ID
	String currentQueryId;
	
	// (From 절에 있는 subquery인 경우) 별칭
	String alias;
	
	public String getCurrentQueryId() {
		return currentQueryId;
	}

	public void setCurrentQueryId(String currentQueryId) {
		this.currentQueryId = currentQueryId;
	}

	public static boolean isSubQueryType(String value){
		String regex = "^[0-9]+_SUBQUERY_[a-zA-Z]+$";
		
		boolean result = value.matches(regex);
		
		return result;
	}
	
	public static boolean isSubQueryText(String value){
		String trimmedValue = value.trim();
		
		// "(", ")" 괄호 없애기
		if(trimmedValue.startsWith("(")){
			trimmedValue = trimmedValue.substring(1);
		} else{
			return false;
		}
		
		if(trimmedValue.endsWith(")")){
			trimmedValue = trimmedValue.substring(0, trimmedValue.length() - 2);
		} else {
			return false;
		}

		return QueryInfo.isQueryType(trimmedValue.trim());
	}
	
	public static SubQueryInfo convertStringToInfo(String value){
		SubQueryInfo subQueryInfo = new SubQueryInfo();
		subQueryInfo.setCurrentQueryId(value);
		
		return subQueryInfo;
	}
	
	public String toString(){
		return "Subquery ID : [" + this.currentQueryId + "] // Alias : ["+ this.alias + "]";
	}
}
