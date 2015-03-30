package query.parser.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import query.parser.QueryCommVar.STATEMENT;
import query.parser.QueryParser;
import query.parser.QueryParserCommFunc;


public class QueryInfo {
	// 자신의 상위 쿼리 ID
	private String superQueryId = "";
	
	private String queryString = "";
	
	private boolean isParsed = false;
	
	// SELECT절 정보
	private List<QueryComponentType> selectStmtInfo = new ArrayList<QueryComponentType>();
	
	// FROM절 정보
	private List<TableViewType> fromStmtInfo = new ArrayList<TableViewType>();
	
	// WHERE 와 JOIN 정보 통합
	private WhereInfo whereStmtInfo = new WhereInfo();
	
	public QueryInfo(String queryString) {
		this.queryString = queryString;
	}

	public String getSuperQueryId() {
		return superQueryId;
	}

	public void setSuperQueryId(String superQueryId) {
		this.superQueryId = superQueryId;
	}
	
	public String getQueryString() {
		return queryString;
	}
	
	public boolean isParsed() {
		return isParsed;
	}

	public List<QueryComponentType> getSelectStmtInfo() {
		return selectStmtInfo;
	}

	public void setSelectStmtInfo(List<QueryComponentType> selectStmtInfo) {
		this.selectStmtInfo = selectStmtInfo;
	}

	
	public List<TableViewType> getFromStmtInfo() {
		return fromStmtInfo;
	}
	
	public void setFromStmtInfo(List<TableViewType> fromStmtInfo) {
		this.fromStmtInfo = fromStmtInfo;
	}

	public WhereInfo getWhereStmtInfo() {
		return whereStmtInfo;
	}

	public void setWhereStmtInfo(WhereInfo whereStmtInfo) {
		this.whereStmtInfo = whereStmtInfo;
	}

	public static boolean isQueryType(String value){
		String trimmedValue = value.trim();

		return trimmedValue.startsWith(STATEMENT.SELECT.getValue() + " ");
	}
	
	public void printQueryStructure(){
		System.out.println("SELECT절");
		for(int i = 0; i < selectStmtInfo.size(); i++){
			System.out.println(selectStmtInfo.get(i).toString());
		}
		System.out.println("===========================");
		
		System.out.println("FROM절");
		for(int i = 0; i < fromStmtInfo.size(); i++){
			System.out.println(fromStmtInfo.get(i).toString());
		}
		System.out.println("===========================");
		
		System.out.println("WHERE절(현재 JOIN부분도 표시)");
		System.out.println(whereStmtInfo);
		
		System.out.println("===========================");
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = super.equals(obj);
		
		if(super.equals(obj) == false){
			if(obj instanceof QueryInfo){
				QueryInfo targetInfo = (QueryInfo)obj;
				
				// SELECT 절 비교
				result = QueryParserCommFunc.isEqualWithoutOrder(targetInfo.getSelectStmtInfo(), this.selectStmtInfo);
				
				// FROM절 비교
				if(result == true){
					result = QueryParserCommFunc.isEqualWithoutOrder(targetInfo.getFromStmtInfo(), this.fromStmtInfo);
				}

				// WHERE절 비교
				if(result == true){
					WhereInfo targetWhereInfo = targetInfo.getWhereStmtInfo();
					
					result = targetWhereInfo.equals(targetInfo.getWhereStmtInfo());
				}
			}
		}
		
		return result;
	}
}
