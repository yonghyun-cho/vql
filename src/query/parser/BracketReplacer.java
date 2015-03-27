package query.parser;

import java.util.HashMap;
import java.util.Map;

import query.parser.vo.FunctionInfo;
import query.parser.vo.QueryInfo;
import query.parser.vo.SubQueryInfo;

public class BracketReplacer {
	/** 분리된 SubQuery 목록  */
	Map<String, QueryInfo> queryStringMap = new HashMap<String, QueryInfo>();
	
	/** 분리된 함수 목록  */
	Map<String, FunctionInfo> functionMap = new HashMap<String, FunctionInfo>();
	
	/** 분리된 기타 (연산자 관련 소괄호)  */
	Map<String, String> otherBracketMap = new HashMap<String, String>();
	
	// subQueryCnt 0은 메인 쿼리임.
	int subQueryTotalCnt = 0;
	private final String SUBQUERY_ID_TEMP = "_SUBQUERY_TEMP";
	
	int functionCnt = 0;
	private final String FUNCTION_BRACKET_ID = "_FUNCTION";
	
	int otherBracketCnt = 0;
	private final String OTHER_BRACKET_ID = "_OTHER_BRACKET";
	
	public Map<String, QueryInfo> getQueryStringMap(){
		return queryStringMap;
	}
	
	public Map<String, FunctionInfo> getFunctionMap(){
		return functionMap;
	}
	
	public Map<String, String> getOtherBracketMap(){
		return otherBracketMap;
	}
	
	/**
	 * 모든 () 구문을 분리해서 종류에 맞게 각 map에 id를 설정하여 추가한다.
	 * 그리고 모든 () 구문이 변환된 메인 쿼리를 전역번수인 mainQuery에 설정하고 종료한다.
	 * 
	 * @param originalQuery () 구문을 제거할 쿼리 String
	 */
	public void splitSubQuery(String originalQuery){
		int bracketEndIndex = originalQuery.indexOf(")");
		
		// ")"가 존재하지 않을 떄 까지 반복하여 대체한다.
		while(bracketEndIndex > 0){
			originalQuery = splitBracket(originalQuery, bracketEndIndex);
			
			bracketEndIndex = originalQuery.indexOf(")");
		}
		
		// MainQuery의 QueryId는 0_SUBQUERY_TEMP으로 고정.
		queryStringMap.put("0"+SUBQUERY_ID_TEMP, new QueryInfo(this.replaceBracket(originalQuery)));
	}
	
	/**
	 * 전체 쿼리에서 가장 먼저 있는 ")" 에 대응하는 구문을 찾아서 map에 추가하고
	 * 해당 구문을 ID로 대체한 메인 쿼리를 반환한다.
	 * 
	 * @param originalQuery () 구문을 대체할 전체 쿼리
	 * @param bracketEndIndex 가장 앞에 존재하는 "("의 index
	 * @return 최초 ")"에 대응하는 괄호 구문을 제거한 메인 쿼리
	 */
	private String splitBracket(String originalQuery, int bracketEndIndex){
		int bracketStartIndex = QueryParserCommFunc.lastIndexOf(originalQuery, "(", originalQuery.indexOf("("), bracketEndIndex);
		
		// 소괄호 안에 있는 string을 추출.
		String bracketString = originalQuery.substring(bracketStartIndex, bracketEndIndex + 1).trim();
		
		// 해당 string이 subqueryText인지 -> () 제거하여 저장
		if(SubQueryInfo.isSubQueryText(bracketString)){ 
			subQueryTotalCnt++;
			
			// TODO "TEMP"에는 추후에 SELECT, FROM 같은게 들어갈 예정
			String subQueryId = subQueryTotalCnt + SUBQUERY_ID_TEMP;
			queryStringMap.put(subQueryId, new QueryInfo(this.replaceBracket(bracketString)));
			
			// 혹시 괄호가 앞문자와 붙어있을 수 있어서 앞 뒤로 " " 추가함
			originalQuery = QueryParserCommFunc.replaceString(originalQuery, " " + subQueryId + " ", bracketStartIndex, bracketEndIndex);
			
			System.out.println("<< SUBQUERY >> " + bracketString);
			
		// function이나 단순한 연산자의 소괄호인지 구분.
		} else if(FunctionInfo.isFunctionText(originalQuery, bracketStartIndex, bracketString)){
			functionCnt++;
			
			int functionStartIndex = FunctionInfo.getFunctionStartIndex(originalQuery, bracketStartIndex, bracketString);
			String functionString = originalQuery.substring(functionStartIndex, bracketEndIndex + 1);
			
			String functionBracketId = functionCnt + FUNCTION_BRACKET_ID;
			functionMap.put(functionBracketId, new FunctionInfo(functionString));
			
			// 혹시 괄호가 앞문자와 붙어있을 수 있어서 앞 뒤로 " " 추가함
			originalQuery = QueryParserCommFunc.replaceString(originalQuery, " " + functionBracketId + " ", functionStartIndex, bracketEndIndex);
			
			// TODO FUNCTION명(#_OTHER_BRACKET) 이런식으로 할까..
			
			System.out.println("<< FUNCTION >> " + functionString);
			
		}else{
			otherBracketCnt++;
			
			String otherBracketId = otherBracketCnt + OTHER_BRACKET_ID;
			otherBracketMap.put(otherBracketId, this.replaceBracket(bracketString));
			
			// 혹시 괄호가 앞문자와 붙어있을 수 있어서 앞 뒤로 " " 추가함
			originalQuery = QueryParserCommFunc.replaceString(originalQuery, " " + otherBracketId + " ", bracketStartIndex, bracketEndIndex);
			
			System.out.println("<< OTHER BRACKET >> " + bracketString);
		}
		
		System.out.println("----------------------------");
		
		System.out.println(originalQuery);
		System.out.println("");
		
		return originalQuery;
	}
	
	
	public static String replaceBracket(String originalString){
		String newString = originalString.trim();
		
		if(newString.startsWith("(")){
			newString = newString.substring(1);
			newString = newString.trim();
		}
		
		if(newString.endsWith(")")){
			newString = newString.substring(0, newString.length() - 1);
		}
		
		return newString.trim();
	}
}
