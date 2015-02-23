package query.parser.vo;

import java.util.ArrayList;
import java.util.List;

public class WhereInfo implements WhereType{
	// 비교연산자
	private String relationOp;
	
	// Condition 목록
	private List<WhereType> conditionList = new ArrayList<WhereType>();

	public String getRelationOp() {
		return relationOp;
	}

	public void setRelationOp(String relationOp) {
		this.relationOp = relationOp;
	}

	public List<WhereType> getValueList() {
		return conditionList;
	}

	public void setValueList(List<WhereType> valueList) {
		this.conditionList = valueList;
	}
	
	public void addValueToList(WhereType value){
		this.conditionList.add(value);
	}
}
