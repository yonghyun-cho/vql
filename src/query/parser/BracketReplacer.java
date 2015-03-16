package query.parser;

import java.util.HashMap;
import java.util.Map;

import query.parser.vo.FunctionInfo;
import query.parser.vo.SubQueryInfo;

public class BracketReplacer {
	// 遺由щ SubQuery 紐⑸? 
	Map<String, String> subQueryStringMap = new HashMap<String, String>();
	
	// 遺由щ ?⑥ 紐⑸?
	Map<String, String> functionMap = new HashMap<String, String>();
	
	// 遺由щ 湲고 (?곗곗 愿???愿??
	Map<String, String> otherBracketMap = new HashMap<String, String>();
	
	// subQueryCnt 0? 硫??荑쇰━?.
	int subQueryTotalCnt = 0;
	private final String SUBQUERY_ID_TEMP = "_SUBQUERY_TEMP";
	
	int functionCnt = 0;
	private final String FUNCTION_BRACKET_ID = "_FUNCTION";
	
	int otherBracketCnt = 0;
	private final String OTHER_BRACKET_ID = "_OTHER_BRACKET";
	
	String mainQuery;
	
	public String getMainQuery(){
		return mainQuery;
	}
	
	public Map<String, String> getSubQueryStringMap(){
		return subQueryStringMap;
	}
	
	public Map<String, String> getFunctionMap(){
		return functionMap;
	}
	
	public Map<String, String> getOtherBracketMap(){
		return otherBracketMap;
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
		
		// ?愿???? ?? string? 異異.
		final String bracketString = originalQuery.substring(bracketStartIndex, bracketEndIndex + 1).trim();
		
		// ?대?string??subqueryText?몄? -> () ?嫄고?????
		if(SubQueryInfo.isSubQueryText(bracketString)){ 
			subQueryTotalCnt++;
			
			// TODO "TEMP"?? 異?? SELECT, FROM 媛?寃 ?ㅼ닿? ??
			String subQueryId = subQueryTotalCnt + SUBQUERY_ID_TEMP;
			subQueryStringMap.put(subQueryId, this.replaceBracket(bracketString));
			
			// ?뱀 愿?멸? ?臾몄? 遺?댁? ? ??댁 ? ?ㅻ? " " 異媛??
			originalQuery = QueryParserCommFunc.replaceString(originalQuery, " " + subQueryId + " ", bracketStartIndex, bracketEndIndex);
			
			System.out.println("<< SUBQUERY >> " + bracketString);
			
		// function?대 ?⑥? ?곗곗? ?愿?몄몄? 援щ?.
		} else if(FunctionInfo.isFunctionText(originalQuery, bracketStartIndex, bracketString)){
			functionCnt++;
			
			int functionStartIndex = FunctionInfo.getFunctionStartIndex(originalQuery, bracketStartIndex, bracketString);
			String functionString = originalQuery.substring(functionStartIndex, bracketEndIndex + 1);
			
			String functionBracketId = functionCnt + FUNCTION_BRACKET_ID;
			functionMap.put(functionBracketId, functionString);
			
			// ?뱀 愿?멸? ?臾몄? 遺?댁? ? ??댁 ? ?ㅻ? " " 異媛??
			originalQuery = QueryParserCommFunc.replaceString(originalQuery, " " + functionBracketId + " ", functionStartIndex, bracketEndIndex);
			
			// TODO FUNCTION紐(#_OTHER_BRACKET) ?대곗?쇰? ?源..
			
			System.out.println("<< FUNCTION >> " + functionString);
			
		}else{
			otherBracketCnt++;
			
			String otherBracketId = otherBracketCnt + OTHER_BRACKET_ID;
			otherBracketMap.put(otherBracketId, this.replaceBracket(bracketString));
			
			// ?뱀 愿?멸? ?臾몄? 遺?댁? ? ??댁 ? ?ㅻ? " " 異媛??
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
