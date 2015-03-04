package query.parser;

import java.util.ArrayList;
import java.util.List;

import query.parser.vo.TableViewType;

public class FromParser {
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
