package query.parser.vo;

import java.util.ArrayList;
import java.util.List;

import query.parser.QueryCommVar;


public class QueryInfo {
	// SELECT 절 정보
	private List<QueryComponentType> selectInfo = new ArrayList<QueryComponentType>();
	
	// WHERE 와 JOIN 정보 통합
	private WhereInfo whereInfo = new WhereInfo();

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
