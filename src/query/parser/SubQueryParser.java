package query.parser;

import java.util.HashMap;
import java.util.Map;

import query.parser.vo.SubQueryInfo;


public class SubQueryParser {
	// 분리된 SubQuery 목록 
	Map<String, String> subQueryStringMap = new HashMap<String, String>();
	
	// 현재(2015.03.01)에는 사용하지 않고 단지 subquery 분리할때 괄호를 혼동되지 않게 하는 역할.
	Map<String, String> otherBracketMap = new HashMap<String, String>(); 
	
	// subQueryCnt 0은 메인 쿼리임.
	int subQueryCnt = 0;
	final String SUBQUERY_ID_MID = "_SUBQUERY_";
	
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
		int bracketStartIndex = this.lastIndexOf(originalQuery, "(", originalQuery.indexOf("("), bracketEndIndex);
		
		System.out.println("<< SUBQUERY >> " + originalQuery.substring(bracketStartIndex, bracketEndIndex + 1));
		System.out.println("----------------------------");
		
		// 소괄호 안에 있는 string을 추출.
		String bracketString = originalQuery.substring(bracketStartIndex, bracketEndIndex + 1).trim();
		
		// 해당 string이 subqueryText인지 -> () 제거하여 저장
		if(SubQueryInfo.isSubQueryText(bracketString)){ 
			subQueryCnt++;
			
			// TODO "TEMP"에는 추후에 SELECT, FROM 같은게 들어갈 예정
			String subQueryId = subQueryCnt + SUBQUERY_ID_MID + "TEMP";
			subQueryStringMap.put(subQueryId, this.replaceBracket(bracketString));
			
			originalQuery = this.replaceString(originalQuery, subQueryId, bracketStartIndex, bracketEndIndex);
			
		// function이나 단순한 연산자의 소괄호인지 구분. -> 나중에 원복해서 돌려놔야 할 듯.
		} else {
			otherBracketCnt++;
			
			String otherBracketId = otherBracketCnt + OTHER_BRACKET_ID;
			subQueryStringMap.put(otherBracketId, bracketString);
			
			originalQuery = this.replaceString(originalQuery, otherBracketId, bracketStartIndex, bracketEndIndex);
		}
		
		System.out.println(originalQuery);
		System.out.println("");
		
		return originalQuery;
	}
	
	// string에서 fromIndex부터 endIndex까지 중 ch의 (중복뒤는 경우 마지막)위치.
	private int lastIndexOf(String string, String ch, int fromIndex, int endIndex){
		String subString = string.substring(fromIndex, endIndex);
		
		int lastIndex = subString.lastIndexOf(ch);
		
		if(lastIndex >= 0){ // 해당 ch의 index가 존재하는 경우에만.
			lastIndex = fromIndex + lastIndex;
		}
		
		return lastIndex;
	}
	
	// originalString에서 fromIndex부터 endIndex까지 제거하고 해당 부분을 replaceString으로 대체한다.
	private String replaceString(String originalString, String replaceString, int fromIndex, int endIndex){
		String newString = originalString.substring(0, fromIndex) + replaceString + originalString.substring(endIndex + 1);
		
		return newString;
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
