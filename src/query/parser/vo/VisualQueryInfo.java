package query.parser.vo;

import java.util.HashMap;
import java.util.Map;

public class VisualQueryInfo {
	
	/** SubQuery HashMap (Query ID, QueryInfo) */
	private Map<String, QueryInfo> queryMap = new HashMap<String, QueryInfo>();
	
	/** Function HashMap (Function ID, Function String) */
	private Map<String, String> functionMap = new HashMap<String, String>();
	
	/** Function HashMap (Other Bracket ID, Other Bracket String) */
	private Map<String, String> otherBracketMap = new HashMap<String, String>();
	
	public VisualQueryInfo() { }
	
	public VisualQueryInfo(Map<String, QueryInfo> queryMap, Map<String, String> functionMap, Map<String, String> otherBracketMap) {
		this.queryMap = queryMap;
		this.functionMap = functionMap;
		this.otherBracketMap = otherBracketMap;
	}

	public void setQueryMap(Map<String, QueryInfo> subQueryMap) {
		this.queryMap = subQueryMap;
	}
	
	public void setFunctionMap(Map<String, String> functionMap) {
		this.functionMap = functionMap;
	}

	public void setOtherBracketMap(Map<String, String> otherBracketMap) {
		this.otherBracketMap = otherBracketMap;
	}

	public QueryInfo getQueryInfo(String queryId){
		return queryMap.get(queryId);
	}
	
	public QueryInfo getMainQueryInfo(){
		// TODO 나중에 _SUBQUERY_TEMP를 공통 변수로 만들 것.
		return this.getQueryInfo("0_SUBQUERY_TEMP");
	}
	
	public void printVisualQueryInfo(){
		// Sub Query 츨력
		for(String subQueryId: queryMap.keySet()){
			System.out.println("<< Map key: " + subQueryId + ">>");
			queryMap.get(subQueryId).printQueryStructure();
		}
	}
}
