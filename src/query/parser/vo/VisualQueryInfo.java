package query.parser.vo;

import java.util.HashMap;
import java.util.Map;

public class VisualQueryInfo {
	
	// ���� ���� ID
	private QueryInfo mainQueryInfo;
	
	// SubQuery HashMap (SuqQueryId, QueryInfo)
	private Map<String, QueryInfo> subQueryMap = new HashMap<String, QueryInfo>();

	// SubQuery Tree ����?
	
	public QueryInfo getMainQueryInfo() {
		return mainQueryInfo;
	}

	public void setMainQueryInfo(QueryInfo queryInfo) {
		this.mainQueryInfo = queryInfo;
	}

	public Map<String, QueryInfo> getSubQueryMap() {
		return subQueryMap;
	}

	public void setSubQueryMap(Map<String, QueryInfo> subQueryMap) {
		this.subQueryMap = subQueryMap;
	}
	
	public void printVisualQueryInfo(){
		// Main Query ���
		mainQueryInfo.printQueryStructure();
		
		// Sub Query ����
		for(String subQueryId: subQueryMap.keySet()){
			System.out.println("<< Map key: " + subQueryId + ">>");
			subQueryMap.get(subQueryId).printQueryStructure();
		}
	}
}
