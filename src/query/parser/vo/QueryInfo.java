package query.parser.vo;

import java.util.ArrayList;
import java.util.List;

import query.parser.QueryCommVar;


public class QueryInfo {
	// ���� ���� ID
	String queryId;
	
	// �ڽ��� ���� ���� ID
	String superQueryId;
	
	// SELECT �� ����
	private List<QueryComponentType> selectInfo = new ArrayList<QueryComponentType>();
	
	// WHERE �� JOIN ���� ����
	private WhereInfo whereInfo = new WhereInfo();
	
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

	public List<QueryComponentType> getSelectInfo() {
		return selectInfo;
	}

	public void setSelectInfo(List<QueryComponentType> selectInfo) {
		this.selectInfo = selectInfo;
	}

	public WhereInfo getWhereInfo() {
		return whereInfo;
	}

	public void setWhereInfo(WhereInfo whereInfo) {
		this.whereInfo = whereInfo;
	}
	
	public static boolean isQueryType(String value){
		String trimmedValue = value.trim();

		return trimmedValue.startsWith(QueryCommVar.SELECT + " ");
	}
}
