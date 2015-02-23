package query.parser.vo;

import java.util.ArrayList;
import java.util.List;

public class SubQueryInfo extends QueryComponentType {
	
	// ºÎ¸ð Äõ¸® ID
	String superQueryId;
	
	// ÇöÀç Äõ¸® ID
	String currentQueryId;
	
	// ÇöÀç Äõ¸® Info
	QueryInfo queryInfo = new QueryInfo();

	// ÀÚ½Ä Äõ¸® ID ¸®½ºÆ®
	List<String> subQueryIdList = new ArrayList<String>();
	
	public String getSuperQueryId() {
		return superQueryId;
	}

	public void setSuperQueryId(String superQueryId) {
		this.superQueryId = superQueryId;
	}

	public String getCurrentQueryId() {
		return currentQueryId;
	}

	public void setCurrentQueryId(String currentQueryId) {
		this.currentQueryId = currentQueryId;
	}

	public QueryInfo getQueryInfo() {
		return queryInfo;
	}

	public void setQueryInfo(QueryInfo queryInfo) {
		this.queryInfo = queryInfo;
	}

	public List<String> getSubQueryIdList() {
		return subQueryIdList;
	}
	
	public void addSubQueryIdToList(String subQueryId){
		this.subQueryIdList.add(subQueryId);
	}

	public void setSubQueryIdList(List<String> subQueryIdList) {
		this.subQueryIdList = subQueryIdList;
	}

	public static boolean isSubQueryType(String value){
		String trimmedValue = value.trim();
		
		// "(", ")" °ýÈ£ ¾ø¾Ö±â
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

		return QueryInfo.isQueryType(trimmedValue);
	}
}
