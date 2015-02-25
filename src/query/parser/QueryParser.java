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
import query.parser.vo.WhereInfo;
import query.parser.vo.WhereType;

public class QueryParser {
	// 처음 입력된 Query
	private String originalQuery = "";
	
	// Parsing된 QueryInfo
	private QueryInfo queryInfo = new QueryInfo();

	public String getInputQuery() {
		return originalQuery;
	}

	public void setInputQuery(String inputQuery) {
		this.originalQuery = inputQuery;
	}
	
	public QueryInfo getQueryInfo() {
		return queryInfo;
	}
	
	public String toString(){
		String result = "";

		System.out.println(originalQuery);
		System.out.println("===========================");
		
		System.out.println("SELECT절");
		List<QueryComponentType> selectList = queryInfo.getSelectInfo();
		for(int i = 0; i < selectList.size(); i++){
			System.out.println(selectList.get(i).toString());
		}
		System.out.println("===========================");
		
		System.out.println("FROM(JOIN)절");
		WhereInfo whereInfo = queryInfo.getWhereInfo();
		System.out.println("<<" + whereInfo.getRelationOp() + ">>");
		
		List<WhereType> valueList = whereInfo.getValueList();
		for(int i = 0; i < valueList.size(); i++){
			System.out.println(valueList.get(i).toString());
		}
		System.out.println("===========================");
		
		return result;
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
	
	// TODO subquery 분석을 위해 전체 query를 나누고 각 query를 statement별로 다시 분석
	public void parsingQueryToVisualQueryInfo() throws Exception{
		// newLine 제거 및 모두 대문자화
		String simpleQuery = trimAllWhiteSpace(originalQuery);
		simpleQuery = simpleQuery.toUpperCase();
		
		SubQueryParser subQueryParser = new SubQueryParser();
		subQueryParser.replaceAllBracket(simpleQuery);
		
		simpleQuery = subQueryParser.getSuperQuery();
		
		// TODO 여기서 Subquery Shape 만드는 부분 추가할 것.
		// MainQuery 파싱. MainQuery의 QueryId는 0_SUBQUERY_TEMP으로 고정.
		queryInfo = this.parsingSubQuery(simpleQuery);
		queryInfo.setQueryId("0_SUBQUERY_TEMP");
		
		// TODO Subquery 부분 파싱하는 로직도 추가하고!
		// subquery를 리스트로 넣을 것인지.. 아니면  child로 넣을 것인지는.. 고민해보기
	}
	
	private QueryInfo parsingSubQuery(String queryText) throws Exception{
		// newLine 제거 및 모두 대문자화
		String simpleQuery = trimAllWhiteSpace(queryText);
		simpleQuery = simpleQuery.toUpperCase();
		
		QueryInfo subQueryInfo = new QueryInfo();
		
		ArrayList<Integer> statementLocation = getStatementLocation(simpleQuery);
		
		Map<String, String> splitStatement = splitStatement(simpleQuery, statementLocation);
		
		for ( Map.Entry<String, String> entry : splitStatement.entrySet()) {
		    String statement = entry.getKey();
		    String contents = entry.getValue();

		    switch(statement){
		    case QueryCommVar.SELECT:
		    	List<QueryComponentType> selectInfo = parsingSelectStatement(contents);
		    	subQueryInfo.setSelectInfo(selectInfo);
		    	break;
		    	
		    case QueryCommVar.FROM:
		    	
		    	break;
		    	
		    case QueryCommVar.WHERE:
		    	WhereInfo whereInfo = parsingWhereStatement(contents);
		    	subQueryInfo.setWhereInfo(whereInfo);
		    	break;
		    }
		}
		
		return subQueryInfo;
	}
	
	// SELECT, FROM, WHERE등의 Statement의 각 위치를 파악한다.
	private ArrayList<Integer> getStatementLocation(String simpleQuery){
		ArrayList<Integer> statementLocation = new ArrayList<Integer>();
		int index = -1; // 해당 Statement의 index
		
		// TODO 
		// "SELECT" 같이 텍스트로 있는 경우는 어떻게 해야 할까..

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
				queryComponentType = QueryComponentType.convertStringToInfo(selectStmt);
			
			}else{
				throw new Exception("잘못된 SELECT절 형식");
			}
			
			selectStmList.add(queryComponentType);
		}
		
		return selectStmList;
	}
	
	// From Statement를 parsing
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
