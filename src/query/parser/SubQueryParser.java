package query.parser;

import java.util.HashMap;
import java.util.Map;

import query.parser.vo.SubQueryInfo;


public class SubQueryParser {
	// �и��� SubQuery ��� 
	Map<String, String> subQueryStringMap = new HashMap<String, String>();
	
	// ����(2015.03.01)���� ������� �ʰ� ���� subquery �и��Ҷ� ��ȣ�� ȥ������ �ʰ� �ϴ� ����.
	Map<String, String> otherBracketMap = new HashMap<String, String>(); 
	
	// subQueryCnt 0�� ���� ������.
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
		
		// �Ұ�ȣ �ȿ� �ִ� string�� ����.
		String bracketString = originalQuery.substring(bracketStartIndex, bracketEndIndex + 1).trim();
		
		// �ش� string�� subqueryText���� -> () �����Ͽ� ����
		if(SubQueryInfo.isSubQueryText(bracketString)){ 
			subQueryCnt++;
			
			// TODO "TEMP"���� ���Ŀ� SELECT, FROM ������ �� ����
			String subQueryId = subQueryCnt + SUBQUERY_ID_MID + "TEMP";
			subQueryStringMap.put(subQueryId, this.replaceBracket(bracketString));
			
			originalQuery = this.replaceString(originalQuery, subQueryId, bracketStartIndex, bracketEndIndex);
			
		// function�̳� �ܼ��� �������� �Ұ�ȣ���� ����. -> ���߿� �����ؼ� �������� �� ��.
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
	
	// string���� fromIndex���� endIndex���� �� ch�� (�ߺ��ڴ� ��� ������)��ġ.
	private int lastIndexOf(String string, String ch, int fromIndex, int endIndex){
		String subString = string.substring(fromIndex, endIndex);
		
		int lastIndex = subString.lastIndexOf(ch);
		
		if(lastIndex >= 0){ // �ش� ch�� index�� �����ϴ� ��쿡��.
			lastIndex = fromIndex + lastIndex;
		}
		
		return lastIndex;
	}
	
	// originalString���� fromIndex���� endIndex���� �����ϰ� �ش� �κ��� replaceString���� ��ü�Ѵ�.
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
