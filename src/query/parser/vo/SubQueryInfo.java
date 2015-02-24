package query.parser.vo;

import java.util.ArrayList;
import java.util.List;

public class SubQueryInfo extends QueryComponentType {
	
	// �θ� ���� ID
	String superQueryId;
	
	// ���� ���� ID
	String currentQueryId;
	
	// ���� ���� Info
	QueryInfo queryInfo = new QueryInfo();

	// �ڽ� ���� ID ����Ʈ
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
		String regex = "^[0-9]+_SUBQUERY_[a-zA-Z]+$";
		
		boolean result = value.matches(regex);
		
		return result;
	}
	
	public static boolean isSubQueryText(String value){
		String trimmedValue = value.trim();
		
		// "(", ")" ��ȣ ���ֱ�
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
}
