package query.parser;

import java.util.ArrayList;
import java.util.List;

import query.parser.vo.QueryComponentType;

public class SelectParser {
	// SELECT Statement를 parsing
	public List<QueryComponentType> parsingSelectStatement(String contents) throws Exception{
		List<QueryComponentType> selectStmList = new ArrayList<QueryComponentType>();
		
		String[] splitContents = contents.split(",");
		
		for(int i = 0; i < splitContents.length; i++){
			String selectStmt = splitContents[i].trim();
			
			QueryComponentType queryComponentType = null;
			
			if(QueryComponentType.isQueryComponenetType(selectStmt)){
				queryComponentType = QueryComponentType.convertStringToType(selectStmt);
			
			}else{
				throw new Exception("잘못된 SELECT절 형식");
			}
			
			selectStmList.add(queryComponentType);
		}
		
		return selectStmList;
	}
}
