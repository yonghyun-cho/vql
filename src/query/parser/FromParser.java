package query.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import query.parser.vo.FunctionInfo;
import query.parser.vo.TableViewType;

//TODO functionMap, otherBracketMap를 이용한 파싱 로직 추가.

public class FromParser {
	/** 분리된 함수 목록 */
	private Map<String, String> functionMap = new HashMap<String, String>();
	
	/** 분리된 기타 (연산자 관련 소괄호) */
	private Map<String, String> otherBracketMap = new HashMap<String, String>();
	
	public FromParser(Map<String, String> functionMap, Map<String, String> otherBracketMap) {
		this.functionMap = functionMap;
		this.otherBracketMap = otherBracketMap;
	}
	
	// FROM Statement를 parsing
	public List<TableViewType> parsingFromStatement(String contents) throws Exception{
		List<TableViewType> fromStmList = new ArrayList<TableViewType>();
		
		String[] splitContents = contents.split(",");
		
		for(int i = 0; i < splitContents.length; i++){
			String fromStmt = splitContents[i].trim();
			
			TableViewType tableViewType = null;
			
			if(TableViewType.isTableViewType(fromStmt)){
				tableViewType = TableViewType.convertStringToType(fromStmt);
			
			}else{
				throw new Exception("잘못된 From절 형식");
			}
			
			fromStmList.add(tableViewType);
		}
		
		return fromStmList;
	}
}
