package query.parser;

import java.util.HashMap;
import java.util.Map;

import query.parser.vo.FunctionInfo;
import query.parser.vo.SubQueryInfo;

// TODO �̸���.. SubQueryParser�� �ƴ϶�
// TODO 	BracketReplacer�� �ұ�...

public class SubQueryParser {
	// �и��� SubQuery ��� 
	Map<String, String> subQueryStringMap = new HashMap<String, String>();
	
	// �и��� �Լ� ���
	Map<String, String> functionMap = new HashMap<String, String>();
	
	// �и��� ��Ÿ (������ ���� �Ұ�ȣ)
	Map<String, String> otherBracketMap = new HashMap<String, String>();
	
	// subQueryCnt 0�� ���� ������.
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
		
		
		
		// �Ұ�ȣ �ȿ� �ִ� string�� ����.
		String bracketString = originalQuery.substring(bracketStartIndex, bracketEndIndex + 1).trim();
		
		// �ش� string�� subqueryText���� -> () �����Ͽ� ����
		if(SubQueryInfo.isSubQueryText(bracketString)){ 
			subQueryTotalCnt++;
			
			// TODO "TEMP"���� ���Ŀ� SELECT, FROM ������ �� ����
			String subQueryId = subQueryTotalCnt + SUBQUERY_ID_TEMP;
			subQueryStringMap.put(subQueryId, this.replaceBracket(bracketString));
			
			// Ȥ�� ��ȣ�� �չ��ڿ� �پ����� �� �־ �� �ڷ� " " �߰���
			originalQuery = QueryParserCommFunc.replaceString(originalQuery, " " + subQueryId + " ", bracketStartIndex, bracketEndIndex);
			
			System.out.println("<< SUBQUERY >> " + bracketString);
			
		// function�̳� �ܼ��� �������� �Ұ�ȣ���� ����.
		} else if(FunctionInfo.isFunctionText(originalQuery, bracketStartIndex, bracketString)){
			functionCnt++;
			
			int functionStartIndex = FunctionInfo.getFunctionStartIndex(originalQuery, bracketStartIndex, bracketString);
			String functionString = originalQuery.substring(functionStartIndex, bracketEndIndex + 1);
			
			String functionBracketId = functionCnt + FUNCTION_BRACKET_ID;
			functionMap.put(functionBracketId, functionString);
			
			// Ȥ�� ��ȣ�� �չ��ڿ� �پ����� �� �־ �� �ڷ� " " �߰���
			originalQuery = QueryParserCommFunc.replaceString(originalQuery, " " + functionBracketId + " ", functionStartIndex, bracketEndIndex);
			
			// TODO FUNCTION��(#_OTHER_BRACKET) �̷������� �ұ�..
			
			System.out.println("<< FUNCTION >> " + functionString);
			
		}else{
			otherBracketCnt++;
			
			String otherBracketId = otherBracketCnt + OTHER_BRACKET_ID;
			otherBracketMap.put(otherBracketId, bracketString);
			
			// Ȥ�� ��ȣ�� �չ��ڿ� �پ����� �� �־ �� �ڷ� " " �߰���
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
