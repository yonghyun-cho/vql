package query.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import query.parser.vo.FunctionInfo;
import query.parser.vo.QueryComponentType;
import query.parser.vo.SubQueryInfo;

//TODO functionMap, otherBracketMap를 이용한 파싱 로직 추가.

public class SelectParser {
	/** 분리된 함수 목록 */
	private Map<String, String> functionMap = new HashMap<String, String>();
	
	/** 분리된 기타 (연산자 관련 소괄호) */
	private Map<String, String> otherBracketMap = new HashMap<String, String>();
	
	public SelectParser(Map<String, String> functionMap, Map<String, String> otherBracketMap) {
		this.functionMap = functionMap;
		this.otherBracketMap = otherBracketMap;
	}
	
	// SELECT Statement를 parsing
	public List<QueryComponentType> parsingSelectStatement(String contents) throws Exception{
		List<QueryComponentType> selectStmList = new ArrayList<QueryComponentType>();
		
		String[] splitContents = contents.split(",");
		
		for(int i = 0; i < splitContents.length; i++){
			String selectStmt = splitContents[i].trim();
			
			QueryComponentType queryComponentType = null;
			if(FunctionInfo.isFunctionType(selectStmt)){
//				queryComponentType = function
				
			} else if(QueryComponentType.isQueryComponenetType(selectStmt)){
				queryComponentType = QueryComponentType.convertStringToType(selectStmt);
			
			} else{
				throw new Exception("잘못된 SELECT절 형식");
			}
			selectStmList.add(queryComponentType);
		}
		
		return selectStmList;
	}
}
