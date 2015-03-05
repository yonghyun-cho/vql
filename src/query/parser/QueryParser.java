package query.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import query.parser.vo.ConditionInfo;
import query.parser.vo.QueryComponentType;
import query.parser.vo.QueryInfo;
import query.parser.vo.TableViewType;
import query.parser.vo.WhereInfo;

// TODO WHERE �� ������ condition ó���� ��.

// TODO FunctionInfo�� SubQueryInfo�� �޸�
// TODO   Info ��ü������ �м��� �� �ؼ� ������ �־�� �� ��.

public class QueryParser {
	// ó�� �Էµ� Query
	private String originalQuery = "";
	
	// Parsing�� QueryInfo
	private QueryInfo mainQueryInfo = new QueryInfo();
	
	// subQuery�� QueryInfo
	private Map<String, QueryInfo> subQueryInfoList = new HashMap<String, QueryInfo>();
	
	// subQuery�� String. �� Map�� ������� subQueryInfoList�� ����.
	private Map<String, String> subQueryStringList = new HashMap<String, String>();
	
	// SELECT Statement Parser
	SelectParser selectParser = new SelectParser();
	
	// FROM Statement Parser
	FromParser fromParser = new FromParser();

	// WHERE Statement Parser
	WhereParser whereParser = new WhereParser();
	
	public String getOriginalQuery() {
		return originalQuery;
	}

	public void setOriginalQuery(String inputQuery) {
		this.originalQuery = QueryParserCommFunc.trimWhiteSpace(inputQuery);
	}
	
	public QueryInfo getMainQueryInfo() {
		return mainQueryInfo;
	}
	
	public Map<String, QueryInfo> getSubQueryInfoList(){
		return subQueryInfoList;
	}
	
	// filePath�� �б�
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
	        
	        originalQuery = QueryParserCommFunc.trimWhiteSpace(sb.toString());
	        
	    } finally {
			br.close();
			
			if(originalQuery == null){
				throw new Exception("Empty Query Exception");
			}
	    }
	}
	
	public void parsingQueryToVisualQueryInfo() throws Exception{
		// newLine ���� �� ��� �빮��ȭ
		String simpleQuery = QueryParserCommFunc.trimWhiteSpace(originalQuery);
		simpleQuery = simpleQuery.toUpperCase();
		
		SubQueryParser subQueryParser = new SubQueryParser();
		subQueryParser.splitSubQuery(simpleQuery);
		
		// SubQuery �Ľ�.
		this.subQueryStringList = subQueryParser.getSubQueryStringMap();
		
		simpleQuery = subQueryParser.getMainQuery();
		
		// MainQuery �Ľ�. MainQuery�� QueryId�� 0_SUBQUERY_TEMP���� ����.
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
		// newLine ���� �� ��� �빮��ȭ
		String simpleQuery = QueryParserCommFunc.trimWhiteSpace(queryText);
		simpleQuery = simpleQuery.toUpperCase();
		
		QueryInfo subQueryInfo = new QueryInfo();
		
		ArrayList<Integer> statementLocation = getStatementLocation(simpleQuery);
		
		Map<String, String> splitStatement = splitStatement(simpleQuery, statementLocation);
		
		for ( String stmtNameKey  : splitStatement.keySet()) {
			String stmtString = splitStatement.get(stmtNameKey);

		    switch(stmtNameKey){
		    case QueryCommVar.SELECT:
		    	List<QueryComponentType> selectInfo = this.selectParser.parsingSelectStatement(stmtString);
		    	subQueryInfo.setSelectStmtInfo(selectInfo);
		    	break;
		    	
		    case QueryCommVar.FROM:
		    	List<TableViewType> fromInfo = this.fromParser.parsingFromStatement(stmtString);
		    	subQueryInfo.setFromStmtInfo(fromInfo);
		    	break;
		    	
		    case QueryCommVar.WHERE:
		    	WhereInfo whereInfo = this.whereParser.parsingWhereStatement(stmtString);
		    	subQueryInfo.setWhereStmtInfo(whereInfo);
		    	break;
		    }
		}
		
		return subQueryInfo;
	}
	
	// SELECT, FROM, WHERE���� Statement�� �� ��ġ�� �ľ��Ѵ�.
	private ArrayList<Integer> getStatementLocation(String simpleQuery){
		ArrayList<Integer> statementLocation = new ArrayList<Integer>();
		int index = -1; // �ش� Statement�� index
		
		// TODO "SELECT" ���� �ؽ�Ʈ�� �ִ� ���� ��� �ؾ� �ұ�..

		for(int i = 0; i < QueryCommVar.STATEMENT_LIST.length; i++){
			index = simpleQuery.indexOf(QueryCommVar.STATEMENT_LIST[i]);
			if(index >= 0){
				statementLocation.add(index);
			}
		}
		
		return statementLocation;
	}
	
	// �� Statement���� String�� ����.
	private Map<String, String> splitStatement(String simpleQuery, ArrayList<Integer> statementLocation){
		Map<String, String> statementInfo = new HashMap<String, String>();
		
		// statementLocation�� ������������ ����
		Collections.sort(statementLocation);
		
		for(int i = 0; i < statementLocation.size(); i++){
			String subString = "";
			
			if(i < statementLocation.size() - 1){
				subString= simpleQuery.substring(statementLocation.get(i), statementLocation.get(i + 1));
				
			}else{ // ������ statement�� ��쿡�� ������ ��������
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
}
