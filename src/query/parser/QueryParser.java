package query.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import query.parser.vo.ColumnInfo;
import query.parser.vo.ConditionInfo;
import query.parser.vo.ConstInfo;
import query.parser.vo.PrimitiveType;
import query.parser.vo.QueryComponentType;
import query.parser.vo.QueryInfo;
import query.parser.vo.SubQueryInfo;
import query.parser.vo.TableViewType;
import query.parser.vo.WhereInfo;
import query.parser.vo.WhereType;

// TODO SELECT, FROM, WHERE Parser class 분리할 것.
public class QueryParser {
	// 처음 입력된 Query
	private String originalQuery = "";
	
	// Parsing된 QueryInfo
	private QueryInfo mainQueryInfo = new QueryInfo();
	
	// subQuery의 QueryInfo
	private Map<String, QueryInfo> subQueryInfoList = new HashMap<String, QueryInfo>();
	
	// subQuery의 String. 이 Map을 기반으로 subQueryInfoList를 생성.
	private Map<String, String> subQueryStringList = new HashMap<String, String>();

	public String getOriginalQuery() {
		return originalQuery;
	}

	public void setOriginalQuery(String inputQuery) {
		this.originalQuery = inputQuery;
	}
	
	public QueryInfo getMainQueryInfo() {
		return mainQueryInfo;
	}
	
	public Map<String, QueryInfo> getSubQueryInfoList(){
		return subQueryInfoList;
	}
	
	// filePath로 읽기
	public void readQueryTextFile(String filePath) throws Exception{
		BufferedReader br = null;
		
	    try {
	    	br = new BufferedReader(new FileReader(filePath));
	    	
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            line = br.readLine();
	        }
	        
	        originalQuery = trimAllWhiteSpace(sb.toString());
	        
	    } finally {
			br.close();
			
			if(originalQuery == null){
				throw new Exception("Empty Query Exception");
			}
	    }
	}
	
	private String trimAllWhiteSpace(String originalString){
		String convertString = originalString.replaceAll("\\r|\\n", " ");
		convertString = convertString.replaceAll("\\s+", " ");
		convertString = convertString.trim();
		
		return convertString;
	}
	
	public void parsingQueryToVisualQueryInfo() throws Exception{
		// newLine 제거 및 모두 대문자화
		String simpleQuery = trimAllWhiteSpace(originalQuery);
		simpleQuery = simpleQuery.toUpperCase();
		
		SubQueryParser subQueryParser = new SubQueryParser();
		subQueryParser.splitSubQuery(simpleQuery);
		
		// SubQuery 파싱.
		this.subQueryStringList = subQueryParser.getSubQueryStringMap();
		
		simpleQuery = subQueryParser.getMainQuery();
		
		// MainQuery 파싱. MainQuery의 QueryId는 0_SUBQUERY_TEMP으로 고정.
		mainQueryInfo = this.parsingSubQuery(simpleQuery);
		mainQueryInfo.setQueryId("0_SUBQUERY_MAIN");
		
		for(String subQueryId : subQueryStringList.keySet()){
			String subQueryText = subQueryStringList.get(subQueryId);
			
			QueryInfo subQueryInfo = this.parsingSubQuery(subQueryText);
			subQueryInfo.setQueryId(subQueryId);
			
			this.subQueryInfoList.put(subQueryId, subQueryInfo);
		}
	}
	
	private QueryInfo parsingSubQuery(String queryText) throws Exception{
		// newLine 제거 및 모두 대문자화
		String simpleQuery = trimAllWhiteSpace(queryText);
		simpleQuery = simpleQuery.toUpperCase();
		
		QueryInfo subQueryInfo = new QueryInfo();
		
		ArrayList<Integer> statementLocation = getStatementLocation(simpleQuery);
		
		Map<String, String> splitStatement = splitStatement(simpleQuery, statementLocation);
		
		for ( String stmtNameKey  : splitStatement.keySet()) {
			String stmtString = splitStatement.get(stmtNameKey);

		    switch(stmtNameKey){
		    case QueryCommVar.SELECT:
		    	List<QueryComponentType> selectInfo = parsingSelectStatement(stmtString);
		    	subQueryInfo.setSelectStmtInfo(selectInfo);
		    	break;
		    	
		    case QueryCommVar.FROM:
		    	List<TableViewType> fromInfo = parsingFromStatement(stmtString);
		    	subQueryInfo.setFromStmtInfo(fromInfo);
		    	break;
		    	
		    case QueryCommVar.WHERE:
		    	WhereInfo whereInfo = parsingWhereStatement(stmtString);
		    	subQueryInfo.setWhereStmtInfo(whereInfo);
		    	break;
		    }
		}
		
		return subQueryInfo;
	}
	
	// SELECT, FROM, WHERE등의 Statement의 각 위치를 파악한다.
	private ArrayList<Integer> getStatementLocation(String simpleQuery){
		ArrayList<Integer> statementLocation = new ArrayList<Integer>();
		int index = -1; // 해당 Statement의 index
		
		// TODO "SELECT" 같이 텍스트로 있는 경우는 어떻게 해야 할까..

		for(int i = 0; i < QueryCommVar.STATEMENT_LIST.length; i++){
			index = simpleQuery.indexOf(QueryCommVar.STATEMENT_LIST[i]);
			if(index >= 0){
				statementLocation.add(index);
			}
		}
		
		return statementLocation;
	}
	
	// 각 Statement별로 String을 나눔.
	private Map<String, String> splitStatement(String simpleQuery, ArrayList<Integer> statementLocation){
		Map<String, String> statementInfo = new HashMap<String, String>();
		
		// statementLocation을 오름차순으로 정렬
		Collections.sort(statementLocation);
		
		for(int i = 0; i < statementLocation.size(); i++){
			String subString = "";
			
			if(i < statementLocation.size() - 1){
				subString= simpleQuery.substring(statementLocation.get(i), statementLocation.get(i + 1));
				
			}else{ // 마지막 statement인 경우에는 끝까지 가져오기
				subString= simpleQuery.substring(statementLocation.get(i));
			}
			
			subString = subString.trim();
			
			for(int j = 0; j < QueryCommVar.STATEMENT_LIST.length; j++){
				String statement = QueryCommVar.STATEMENT_LIST[j];
				
				if(subString.startsWith(statement)){
					String statementContents = subString.replaceFirst(statement, "");
					statementInfo.put(statement, statementContents.trim());
					break;
				}
			}
		}
		
		return statementInfo;
	}
	
	// SELECT Statement를 parsing
	private List<QueryComponentType> parsingSelectStatement(String contents) throws Exception{
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
	
	// FROM Statement를 parsing
	private List<TableViewType> parsingFromStatement(String contents) throws Exception{
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
	
	// TODO 중첩된 WHERE 조건 처리할 것!!
	// WHERE Statement를 parsing
	private WhereInfo parsingWhereStatement(String contents) throws Exception{
		WhereInfo whereInfo = new WhereInfo();
		whereInfo.setRelationOp(QueryCommVar.AND);
		
		String[] splitContentArray = contents.split(QueryCommVar.AND);
		
		for(int i = 0; i < splitContentArray.length; i++){
			ConditionInfo conditionInfo = ConditionInfo.convertStringToInfo(splitContentArray[i].trim());
			whereInfo.addValueToList(conditionInfo);
		}
		
		return whereInfo;
	}
}
