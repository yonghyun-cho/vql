package query.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import query.parser.QueryCommVar.STATEMENT;
import query.parser.vo.QueryComponentType;
import query.parser.vo.QueryInfo;
import query.parser.vo.TableViewType;
import query.parser.vo.WhereInfo;

// eclipse plugin -> web쪽으로 바꿀 것. (Gradle을 사용해 보자)

// TODO FunctionInfo는 SubQueryInfo와 달리
// TODO   Info 자체적으로 분석을 다 해서 가지고 있어야 할 듯.
public class QueryParser {
	/** SELECT Statement Parser */
	private SelectParser selectParser = null;
	
	/** FROM Statement Parser */
	private FromParser fromParser = null;

	/** WHERE Statement Parser */
	private WhereParser whereParser = null;
	
	public QueryParser(Map<String, String> functionMap, Map<String, String> otherBracketMap) {
		// 각 parser 초기화
		this.initiateParser(functionMap, otherBracketMap);
	}
	
	/**
	 * 각 Parser를 초기화한다.
	 * <br/>
	 * - functionMap, otherBracketMap 설정
	 * 
	 */
	private void initiateParser(Map<String, String> functionMap, Map<String, String> otherBracketMap){
		this.selectParser = new SelectParser(functionMap, otherBracketMap);
		
		this.fromParser = new FromParser(functionMap, otherBracketMap);

		this.whereParser = new WhereParser(functionMap, otherBracketMap);
	}
	
	public QueryInfo setQueryStmtInfo(QueryInfo queryInfo) throws Exception{
		String queryString = queryInfo.getQueryString();
		
		ArrayList<Integer> statementLocation = getStatementLocation(queryString);
		Map<STATEMENT, String> splitStatement = splitStatement(queryString, statementLocation);
		
		for (STATEMENT stmtNameKey  : splitStatement.keySet()) {
			String stmtString = splitStatement.get(stmtNameKey);

		    switch(stmtNameKey){
		    case SELECT:
		    	List<QueryComponentType> selectInfo = this.selectParser.parsingSelectStatement(stmtString);
		    	queryInfo.setSelectStmtInfo(selectInfo);
		    	break;
		    	
		    case FROM:
		    	List<TableViewType> fromInfo = this.fromParser.parsingFromStatement(stmtString);
		    	queryInfo.setFromStmtInfo(fromInfo);
		    	break;
		    	
		    case WHERE:
		    	WhereInfo whereInfo = this.whereParser.parsingWhereStatement(stmtString);
		    	queryInfo.setWhereStmtInfo(whereInfo);
		    	break;
		    }
		}
		
		return queryInfo;
	}
	
	// SELECT, FROM, WHERE등의 Statement의 각 위치를 파악한다.
	private ArrayList<Integer> getStatementLocation(String simpleQuery){
		ArrayList<Integer> statementLocation = new ArrayList<Integer>();
		
		// TODO "SELECT" 같이 텍스트로 있는 경우는 어떻게 해야 할까..
		// 		-> " " + SELECT + " " 이렇게 앞 뒤로 space나, (쿼리 시작) + SELECT + " " 이렇게..
		
		for(STATEMENT statement: STATEMENT.values()){
			final String statementString = statement.getValue();
			
			final int index = simpleQuery.indexOf(statementString);
			if(index >= 0){
				statementLocation.add(index);
			}
		}
		
		return statementLocation;
	}
	
	// 각 Statement별로 String을 나눔.
	private Map<STATEMENT, String> splitStatement(String simpleQuery, ArrayList<Integer> statementLocation){
		Map<STATEMENT, String> statementInfo = new HashMap<STATEMENT, String>();
		
		// statementLocation을 오름차순으로 정렬
		Collections.sort(statementLocation);
		
		for(int i = 0; i < statementLocation.size(); i++){
			String subString = "";
			if(i < statementLocation.size() - 1){
				subString= simpleQuery.substring(statementLocation.get(i), statementLocation.get(i + 1));
				
			}else{ // 마지막 statement인 경우에는 끝까지 가져오기
				subString= simpleQuery.substring(statementLocation.get(i));
			}
			subString = subString.trim();
			
			for(STATEMENT statement: STATEMENT.values()){
				final String statementString = statement.getValue();
				
				if(subString.startsWith(statementString)){
					String statementContents = subString.replaceFirst(statementString, "");
					statementInfo.put(statement, statementContents.trim());
					break;
				}
			}
		}
		
		return statementInfo;
	}
}
