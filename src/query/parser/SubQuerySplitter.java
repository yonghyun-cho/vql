package query.parser;

import java.util.HashMap;
import java.util.Map;

import query.parser.vo.SubQueryInfo;

public class SubQuerySplitter {
	/** 분리된 SubQuery 목록  */
	Map<String, String> subQueryStringMap = new HashMap<String, String>();
	
	// subQueryCnt 0은 메인 쿼리임.
	int subQueryTotalCnt = 0;
	private final String SUBQUERY_ID_TEMP = "_SUBQUERY_TEMP";
	
	String mainQuery;
	
	public String getMainQuery(){
		return mainQuery;
	}
	
	public Map<String, String> getSubQueryStringMap(){
		return subQueryStringMap;
	}
	
	/**
	 * 모든 () 구문을 분리해서 종류에 맞게 각 map에 id를 설정하여 추가한다.
	 * 그리고 모든 () 구문이 변환된 메인 쿼리를 전역번수인 mainQuery에 설정하고 종료한다.
	 * 
	 * @param originalQuery () 구문을 제거할 쿼리 String
	 */
	public void splitSubQuery(String originalQuery){
		int skipBracketNumber = 0;
		
		mainQuery = originalQuery;
		
		int bracketEndIndex = originalQuery.indexOf(")");
		// ")"가 존재하지 않을 떄 까지 반복하여 대체한다.
		while(bracketEndIndex > 0){
			int bracketStartIndex = this.indexOfLastBeginBracket(mainQuery, bracketEndIndex, skipBracketNumber);
			String bracketString = mainQuery.substring(bracketStartIndex, bracketEndIndex + 1).trim();
			
			if(SubQueryInfo.isSubQueryText(bracketString)){ 
				this.subQueryTotalCnt++;
				
				// TODO "TEMP"에는 추후에 SELECT, FROM 같은게 들어갈 예정
				String subQueryId = subQueryTotalCnt + SUBQUERY_ID_TEMP;
				subQueryStringMap.put(subQueryId, this.replaceBracket(bracketString));
				
				// 혹시 괄호가 앞문자와 붙어있을 수 있어서 앞 뒤로 " " 추가함
				mainQuery = QueryParserCommFunc.replaceString(mainQuery, " " + subQueryId + " ", bracketStartIndex, bracketEndIndex);
				
			} else {
				// n번 이후 ")"를 선택하도록 설정
				skipBracketNumber++; 
			}
			
			bracketEndIndex = this.indexOfFirstEndBracket(mainQuery, skipBracketNumber);
		}
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
	
	private int indexOfLastBeginBracket(String string, int endIndex, int skipBracketNumber){
		int index = endIndex;
		
		for(int i = 0; i < skipBracketNumber+1; i++){
			index = string.lastIndexOf("(", index-1);
		}
		
		return index;
	}
	 
	private int indexOfFirstEndBracket(String string, int skipBracketNumber){
		// indexOf에서 index+1을 하므로.
		int index = -2;
		
		for(int i = 0; i < skipBracketNumber+1; i++){
			index = string.indexOf(")", index+1);
		}
		
		return index;
	}
}
