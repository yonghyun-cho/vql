package query.parser;

import java.util.HashMap;
import java.util.Map;

import query.parser.vo.SubQueryInfo;


public class SubQueryParser {

	Map<String, String> suqQueryStringMap = new HashMap<String, String>();
	Map<String, String> otherBracketMap = new HashMap<String, String>(); 
	
	int subQueryCnt = 0;
	final String SUBQUERY_ID_MID = "_SUBQUERY_";
	
	int otherBracketCnt = 0;
	final String OTHER_BRACKET_ID = "_OTHER_BRACKET";
	
	String superQuery;
	
	public String getSuperQuery(){
		return superQuery;
	}
	
	public void replaceAllBracket(String originalQuery){
		
		int bracketEndIndex = originalQuery.indexOf(")");
		
		superQuery = originalQuery;
		
		while(bracketEndIndex > 0){
			superQuery = replaceBracket(superQuery, bracketEndIndex);
			
			bracketEndIndex = superQuery.indexOf(")");
		}
	}
	
	public String replaceBracket(String originalQuery, int bracketEndIndex){
		int bracketStartIndex = originalQuery.indexOf("(");
		
		bracketStartIndex = this.lastIndexOf(originalQuery, "(", bracketStartIndex, bracketEndIndex);
		
		System.out.println("<< SUBQUERY >> " + originalQuery.substring(bracketStartIndex, bracketEndIndex + 1));
		System.out.println("----------------------------");
		
		String bracketString = originalQuery.substring(bracketStartIndex, bracketEndIndex + 1).trim();
		
		if(SubQueryInfo.isSubQueryText(bracketString)){
			subQueryCnt++;
			// "TEMP"에는 추후에 SELECT, FROM 같은게 들어갈 예정
			
			String subQueryId = subQueryCnt + SUBQUERY_ID_MID + "TEMP";
			suqQueryStringMap.put(subQueryId, bracketString);
			
			originalQuery = this.replaceString(originalQuery, subQueryId, bracketStartIndex, bracketEndIndex);
			
		} else {
			otherBracketCnt++;
			
			String otherBracketId = otherBracketCnt + OTHER_BRACKET_ID;
			suqQueryStringMap.put(otherBracketId, bracketString);
			
			originalQuery = this.replaceString(originalQuery, otherBracketId, bracketStartIndex, bracketEndIndex);
		}
		
		System.out.println(originalQuery);
		System.out.println("");
		
		return originalQuery;
	}
	
	private int lastIndexOf(String string, String ch, int fromIndex, int endIndex){
		String subString = string.substring(fromIndex, endIndex);
		
		int lastIndex = subString.lastIndexOf(ch);
		
		if(lastIndex >= 0){ // 해당 ch의 index가 존재하는 경우에만.
			lastIndex = fromIndex + lastIndex;
		}
		
		return lastIndex;
	}
	
	private String replaceString(String originalString, String replaceString, int fromIndex, int endIndex){
		String newString = originalString.substring(0, fromIndex) + replaceString + originalString.substring(endIndex + 1);
		
		return newString;
	}
}
