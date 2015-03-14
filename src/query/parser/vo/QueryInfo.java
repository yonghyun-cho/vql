package query.parser.vo;

import java.util.ArrayList;
import java.util.List;

import query.parser.QueryCommVar.STATEMENT;


public class QueryInfo {
	// ���� ���� ID
	String queryId = "";
	
	// �ڽ��� ���� ���� ID
	String superQueryId = "";
	
	// SELECT�� ����
	private List<QueryComponentType> selectStmtInfo = new ArrayList<QueryComponentType>();
	
	// FROM�� ����
	private List<TableViewType> fromStmtInfo = new ArrayList<TableViewType>();
	
	// WHERE �� JOIN ���� ����
	private WhereInfo whereStmtInfo = new WhereInfo();
	
	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public String getSuperQueryId() {
		return superQueryId;
	}

	public void setSuperQueryId(String superQueryId) {
		this.superQueryId = superQueryId;
	}

	public List<QueryComponentType> getSelectStmtInfo() {
		return selectStmtInfo;
	}

	public void setSelectStmtInfo(List<QueryComponentType> selectInfo) {
		this.selectStmtInfo = selectInfo;
	}

	public List<TableViewType> getFromStmtInfo() {
		return fromStmtInfo;
	}

	public void setFromStmtInfo(List<TableViewType> fromInfo) {
		this.fromStmtInfo = fromInfo;
	}

	public WhereInfo getWhereStmtInfo() {
		return whereStmtInfo;
	}

	public void setWhereStmtInfo(WhereInfo whereInfo) {
		this.whereStmtInfo = whereInfo;
	}
	
	public static boolean isQueryType(String value){
		String trimmedValue = value.trim();

		return trimmedValue.startsWith(STATEMENT.SELECT.getValue() + " ");
	}
	
	public void printQueryStructure(){
		System.out.println(queryId);
		System.out.println("===========================");
		
		System.out.println("SELECT��");
		for(int i = 0; i < selectStmtInfo.size(); i++){
			System.out.println(selectStmtInfo.get(i).toString());
		}
		System.out.println("===========================");
		
		System.out.println("FROM��");
		for(int i = 0; i < fromStmtInfo.size(); i++){
			System.out.println(fromStmtInfo.get(i).toString());
		}
		System.out.println("===========================");
		
		System.out.println("WHERE��(���� JOIN�κе� ǥ��)");
		System.out.println(whereStmtInfo);
		
		System.out.println("===========================");
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = super.equals(obj);
		
		if(super.equals(obj) == false){
			if(obj instanceof QueryInfo){
				QueryInfo targetInfo = (QueryInfo)obj;
				
				boolean isPropertyEqual = false;

				if(targetInfo.getQueryId().equals(this.queryId)
						&& targetInfo.getSuperQueryId().equals(this.superQueryId)){
					
					// ��������� true�̹Ƿ�
					result = true;
					
					// SELECT�� ��
					List<QueryComponentType> targetSelectInfo = targetInfo.getSelectStmtInfo();
					for(int i = 0 ; i < targetSelectInfo.size(); i++){
						isPropertyEqual = false;
						
						for(int j = 0; j < this.selectStmtInfo.size(); j++){
							if(targetSelectInfo.get(i).equals(selectStmtInfo.get(j))){
								isPropertyEqual = true;
								break;
							}
						}
						
						result = result && isPropertyEqual;
						
						if(result == false){
							break;
						}
					}
				}	
				
				if(result == true){
					// FROM�� ��
					List<TableViewType> targetFromInfo = targetInfo.getFromStmtInfo();
					for(int i = 0 ; i < targetFromInfo.size(); i++){
						isPropertyEqual = false;
						
						for(int j = 0; j < this.fromStmtInfo.size(); j++){
							if(targetFromInfo.get(i).equals(fromStmtInfo.get(j))){
								isPropertyEqual = true;
								break;
							}
						}
						
						result = result && isPropertyEqual;
						
						if(result == false){
							break;
						}
					}
				}
				
				if(result == true){
					// WHERE�� ��
					WhereInfo targetWhereInfo = targetInfo.getWhereStmtInfo();
					
					result = targetWhereInfo.equals(this.whereStmtInfo);
				}
				
			} else {
				result = false;
			}
		}
		
		return result;
	}
}
