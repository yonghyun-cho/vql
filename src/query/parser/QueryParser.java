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
	private String inputQuery = "";
	private QueryInfo queryInfo = new QueryInfo();

	public String getInputQuery() {
		return inputQuery;
	}

	public void setInputQuery(String inputQuery) {
		this.inputQuery = inputQuery;
	}
	
	public QueryInfo getQueryInfo() {
		return queryInfo;
	}
	
	public String toString(){
		String result = "";

		System.out.println(inputQuery);
		System.out.println("===========================");
		
		System.out.println("SELECT��");
		List<QueryComponentType> selectList = queryInfo.getSelectInfo();
		for(int i = 0; i < selectList.size(); i++){
			System.out.println(selectList.get(i).toString());
		}
		System.out.println("===========================");
		
		System.out.println("FROM(JOIN)��");
		WhereInfo whereInfo = queryInfo.getWhereInfo();
		System.out.println("<<" + whereInfo.getRelationOp() + ">>");
		
		List<WhereType> valueList = whereInfo.getValueList();
		for(int i = 0; i < valueList.size(); i++){
			System.out.println(valueList.get(i).toString());
		}
		System.out.println("===========================");
		
		return result;
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
	        
	        inputQuery = sb.toString();
	        
	    } finally {
			br.close();
			
			if(inputQuery == null){
				throw new Exception("Empty Query Exception");
			}
	    }
	}
	
	// TODO subquery �м��� ���� ��ü query�� ������ �� query�� statement���� �ٽ� �м�
	public void parsingQuery() throws Exception{
		// newLine ���� �� ��� �빮��ȭ
		String simpleQuery = inputQuery.replaceAll("\n", " ");
		simpleQuery = simpleQuery.toUpperCase();
		
		SubQueryParser subQueryParser = new SubQueryParser();
		subQueryParser.replaceAllBracket(simpleQuery);
		
		simpleQuery = subQueryParser.getSuperQuery();
		
		// TODO ���⼭ Subquery Shape ����� �κ� �߰��� ��.
		this.parsingSubQuery(simpleQuery);
		
		// TODO Subquery �κ� �Ľ��ϴ� ������ �߰��ϰ�!
		// subquery�� ����Ʈ�� ���� ������.. �ƴϸ�  child�� ���� ��������.. ����غ���
	}
	
	private QueryInfo parsingSubQuery(String queryText) throws Exception{
		// newLine ���� �� ��� �빮��ȭ
		String simpleQuery = queryText.replaceAll("\n", " ");
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
		    	
		    case QueryCommVar.WHERE:
		    	WhereInfo whereInfo = parsingWhereStatement(contents);
		    	subQueryInfo.setWhereInfo(whereInfo);
		    	break;
		    }
		}
		
		return subQueryInfo;
	}
	
	// SELECT, FROM, WHERE���� Statement�� �� ��ġ�� �ľ��Ѵ�.
	private ArrayList<Integer> getStatementLocation(String simpleQuery){
		ArrayList<Integer> statementLocation = new ArrayList<Integer>();
		int index = -1; // �ش� Statement�� index
		
		// TODO 
		// "SELECT" ���� �ؽ�Ʈ�� �ִ� ���� ��� �ؾ� �ұ�..

		for(int i = 0; i < QueryCommVar.STATEMENT_LIST.length; i++){
			index = inputQuery.indexOf(QueryCommVar.STATEMENT_LIST[i]);
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
	
	// SELECT Statement�� parsing
	private List<QueryComponentType> parsingSelectStatement(String contents) throws Exception{
		List<QueryComponentType> selectStmList = new ArrayList<QueryComponentType>();
		
		String[] splitContents = contents.split(",");
		
		for(int i = 0; i < splitContents.length; i++){
			String selectStmt = splitContents[i].trim();
			
			QueryComponentType queryComponentType = null;
			
			if(PrimitiveType.isPrimitiveType(selectStmt)){
				queryComponentType = PrimitiveType.convertStringToInfo(selectStmt);
			
			}else{
				throw new Exception("�߸��� SELECT�� ����");
			}
			
			selectStmList.add(queryComponentType);
		}
		
		return selectStmList;
	}
	
	// From Statement�� parsing
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
