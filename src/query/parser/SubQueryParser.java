package query.parser;

import java.util.HashMap;
import java.util.Map;

import query.parser.vo.FunctionInfo;
import query.parser.vo.SubQueryInfo;

// TODO 이름을.. SubQueryParser가 아니라
// TODO 	BracketReplacer로 할까...

public class SubQueryParser {
	// 분리된 SubQuery 목록 
	Map<String, String> subQueryStringMap = new HashMap<String, String>();
	
	// 분리된 함수 목록
	Map<String, String> functionMap = new HashMap<String, String>();
	
	// 분리된 기타 (연산자 관련 소괄호)
	Map<String, String> otherBracketMap = new HashMap<String, String>();
	
	// subQueryCnt 0은 메인 쿼리임.
	int subQueryTotalCnt = 0;
	final String SUBQUERY_ID_TEMP = "_SUBQUERY_TEMP";
	
	int functionCnt = 0;
	final String FUNCTION_BRACKET_ID = "_FUNCTION";
	
	int otherBracketCnt = 0;
	final String OTHER_BRACKET_ID = "_OTHER_BRACKET";
	
	String mainQuery;
	
	public String getMainQuery(){
		return mainQuery;
	}
	
	public Map<String, String> getSubQueryStringMap(){
		return subQueryStringMap;
	}
	
	public void splitSubQuery(String originalQuery){
		int bracketEndIndex = originalQuery.indexOf(")");
		
		mainQuery = originalQuery;
		
		while(bracketEndIndex > 0){
			mainQuery = splitBracket(mainQuery, bracketEndIndex);
			
			bracketEndIndex = mainQuery.indexOf(")");
		}
	}
	
	private String splitBracket(String originalQuery, int bracketEndIndex){
		int bracketStartIndex = QueryParserCommFunc.lastIndexOf(originalQuery, "(", originalQuery.indexOf("("), bracketEndIndex);
		
		
		
		// 소괄호 안에 있는 string을 추출.
		String bracketString = originalQuery.substring(bracketStartIndex, bracketEndIndex + 1).trim();
		
		// 해당 string이 subqueryText인지 -> () 제거하여 저장
		if(SubQueryInfo.isSubQueryText(bracketString)){ 
			subQueryTotalCnt++;
			
			// TODO "TEMP"에는 추후에 SELECT, FROM 같은게 들어갈 예정
			String subQueryId = subQueryTotalCnt + SUBQUERY_ID_TEMP;
			subQueryStringMap.put(subQueryId, this.replaceBracket(bracketString));
			
			// 혹시 괄호가 앞문자와 붙어있을 수 있어서 앞 뒤로 " " 추가함
			originalQuery = QueryParserCommFunc.replaceString(originalQuery, " " + subQueryId + " ", bracketStartIndex, bracketEndIndex);
			
			System.out.println("<< SUBQUERY >> " + bracketString);
			
		// function이나 단순한 연산자의 소괄호인지 구분.
		} else if(FunctionInfo.isFunctionText(originalQuery, bracketStartIndex, bracketString)){
			functionCnt++;
			
			int functionStartIndex = FunctionInfo.getFunctionStartIndex(originalQuery, bracketStartIndex, bracketString);
			String functionString = originalQuery.substring(functionStartIndex, bracketEndIndex + 1);
			
			String functionBracketId = functionCnt + FUNCTION_BRACKET_ID;
			functionMap.put(functionBracketId, functionString);
			
			// 혹시 괄호가 앞문자와 붙어있을 수 있어서 앞 뒤로 " " 추가함
			originalQuery = QueryParserCommFunc.replaceString(originalQuery, " " + functionBracketId + " ", functionStartIndex, bracketEndIndex);
			
			// TODO FUNCTION명(#_OTHER_BRACKET) 이런식으로 할까..
			
			System.out.println("<< FUNCTION >> " + functionString);
			
		}else{
			otherBracketCnt++;
			
			String otherBracketId = otherBracketCnt + OTHER_BRACKET_ID;
			otherBracketMap.put(otherBracketId, bracketString);
			
			// 혹시 괄호가 앞문자와 붙어있을 수 있어서 앞 뒤로 " " 추가함
			originalQuery = QueryParserCommFunc.replaceString(originalQuery, " " + otherBracketId + " ", bracketStartIndex, bracketEndIndex);
			
			System.out.println("<< OTHER BRACKET >> " + bracketString);
		}
		
		System.out.println("----------------------------");
		
		System.out.println(originalQuery);
		System.out.println("");
		
		return originalQuery;
	}
	
	
	
	private String replaceBracket(String originalString){
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
