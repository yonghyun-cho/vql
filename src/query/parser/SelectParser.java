package query.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.TableView;

import query.parser.vo.FunctionInfo;
import query.parser.vo.PrimitiveType;
import query.parser.vo.QueryComponentType;
import query.parser.vo.SubQueryInfo;
import query.parser.vo.TableViewType;

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
			if(PrimitiveType.isPrimitiveType(selectStmt)){
				queryComponentType = PrimitiveType.convertStringToType(selectStmt);
				
			} else if(TableViewType.isTableViewType(selectStmt)){
				queryComponentType = TableViewType.convertStringToType(selectStmt);
				
			} else if(FunctionInfo.isFunctionId(selectStmt)){
				selectStmt = functionMap.get(selectStmt);
				// TODO FunctionParser
				
			} else{
				throw new Exception("잘못된 SELECT절 형식");
			}
			selectStmList.add(queryComponentType);
		}
		
		return selectStmList;
	}
}
