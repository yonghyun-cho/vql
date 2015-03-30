package query.parser;

import java.util.Map;

import query.parser.vo.QueryInfo;
import query.parser.vo.VisualQueryInfo;

public class Executor {
	public VisualQueryInfo execute() throws Exception{
		// 1. read QueryString
		String queryString = TempQueryInput.readQueryTextFile("C:\\testQuery.txt");
		
		// 2. seperate bracket(parenthesis) "(, )"
		BracketDistributor bracketDistributor = new BracketDistributor();
		bracketDistributor.splitSubQuery(queryString);
		
		// 분리된 SubQuery 목록 
		Map<String, QueryInfo> queryMap = bracketDistributor.getQueryMap();
		// 분리된 함수 목록 
		Map<String, String> functionMap = bracketDistributor.getFunctionMap();
		// 분리된 기타 (연산자 관련 소괄호)
		Map<String, String> otherBracketMap = bracketDistributor.getOtherBracketMap();
		
		// 3. set QueryFactory
		VisualQueryInfo visualQueryInfo = new VisualQueryInfo(queryMap, functionMap, otherBracketMap);
		
		return visualQueryInfo;
	}
}
