package query.parser.vo;

import java.util.ArrayList;
import java.util.List;

public class SubQueryInfo extends TableViewType {
	
	public String getCurrentQueryId() {
		return tableViewId;
	}

	public void setCurrentQueryId(String currentQueryId) {
		this.tableViewId = currentQueryId;
	}
	
	public String getAlias() {
		return tableViewAlias;
	}

	public void setAlias(String alias) {
		this.tableViewAlias = alias;
	}

	public static boolean isSubQueryType(String value){
		List<String> regexList = new ArrayList<String>();
		// TODO table명 "alias" 인 경우 처리
		regexList.add("^[0-9]+_SUBQUERY_[a-zA-Z]+ [a-zA-Z][a-zA-Z0-9]*$");
		regexList.add("^[0-9]+_SUBQUERY_[a-zA-Z]+$");
		
		boolean result = false;
		
		if(value != null){
			for(int i = 0; i < regexList.size(); i++){
				result = value.matches(regexList.get(i));
				
				if(result == true){
					break;
				}
			}
		}
		
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
	
	public static SubQueryInfo convertStringToInfo(String value) throws Exception{
		SubQueryInfo subQueryInfo = new SubQueryInfo();
		
		value = value.trim();
		
		String[] splitedValue = value.split(" ");
		
		if(splitedValue.length == 2){
			subQueryInfo.setCurrentQueryId(splitedValue[0].trim()); // 테이블 명
			subQueryInfo.setAlias(splitedValue[1].trim()); // alias
			
		}else if(splitedValue.length == 1){
			subQueryInfo.setCurrentQueryId(splitedValue[0].trim()); // 테이블 명
			
		}else{
			throw new Exception("올바른 SubQuery Type String이 아닙니다.");
		}
		
		return subQueryInfo;
	}
	
	public String toString(){
		return "Subquery ID : [" + this.tableViewId + "] // Alias : ["+ this.tableViewAlias + "]";
	}
}
