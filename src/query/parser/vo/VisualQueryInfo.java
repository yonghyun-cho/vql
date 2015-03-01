package query.parser.vo;

import java.util.HashMap;
import java.util.Map;

public class VisualQueryInfo {
	
	// 메인 쿼리 ID
	private QueryInfo mainQueryInfo;
	
	// SubQuery HashMap (SuqQueryId, QueryInfo)
	private Map<String, QueryInfo> subQueryMap = new HashMap<String, QueryInfo>();

	// SubQuery Tree 구조?
	
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
}
