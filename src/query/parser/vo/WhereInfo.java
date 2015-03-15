package query.parser.vo;

import java.util.ArrayList;
import java.util.List;

import query.parser.QueryCommVar.LGCL_OP;
import query.parser.QueryParserCommFunc;

public class WhereInfo implements WhereType{
	// �񱳿�����
	private LGCL_OP relationOp;
	
	// Condition ���
	private List<WhereType> conditionList = new ArrayList<WhereType>();

	public String getRelationOp() {
		return this.relationOp.getValue();
	}

	public void setRelationOp(String relationOp) throws Exception {
		this.relationOp = LGCL_OP.getEnum(relationOp);
	}
	
	public void setRelationOp(LGCL_OP relationOp) {
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
	
	// Condition (AND/OR) Condition ������ ����
	// 2���� �̻��� Condition���� ������ WhereInfo
	public static boolean isWhereInfo(String value) throws Exception{
		String regex = ".+[^a-zA-Z0-9_$#](AND|OR)[^a-zA-Z0-9_$#].+";
		
		return QueryParserCommFunc.isMatched(value, regex);
	}
	
	public static boolean hasAndOperator(String value) throws Exception{
		String regex = ".+[^a-zA-Z0-9_$#]AND[^a-zA-Z0-9_$#].+";
		
		return QueryParserCommFunc.isMatched(value, regex);
	}
	
	public static boolean hasOrOperator(String value) throws Exception{
		String regex = ".+[^a-zA-Z0-9_$#]OR[^a-zA-Z0-9_$#].+";
		
		return QueryParserCommFunc.isMatched(value, regex);
	}
	
	public static boolean isSubConditionType(String value) throws Exception{
		String regex = "^[0-9]+_OTHER_BRACKET$";

		return QueryParserCommFunc.isMatched(value, regex);
	}
	
	public String toString(){
		String result = "<�� ������ : \"" + this.relationOp + "\" >\n";
		
		for(int i = 0; i < conditionList.size(); i++){
			result = result + conditionList.get(i) + "\n";
		}
		
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = super.equals(obj);
		
		if(super.equals(obj) == false){
			if(obj instanceof WhereInfo){ // �ش� Object�� WhereInfo Ÿ���̰�
				WhereInfo targetInfo = (WhereInfo)obj;
				
				if(targetInfo.getRelationOp().equals(this.relationOp.getValue())){ // �񱳿����ڰ� �����Ҷ�
					// ������� ����(relationOp)�� ��� ����������
					result = true;
					
					boolean isAllConditionEqual = false;
					
					for(int i = 0; i < this.conditionList.size(); i++){
						isAllConditionEqual = false;
						
						for( int j = 0; j < targetInfo.getValueList().size(); j++){
							if(this.conditionList.get(i).equals(targetInfo.getValueList().get(j))){
								isAllConditionEqual = true;
								break;
							}
						}
						
						result = result && isAllConditionEqual; // ���ݱ����� ��� ������ true�̸�
						
						if(result == false){
							break;
						}
					}
				}
				
			} else {
				result = false;
			}
		}
		
		return result;
	}
}
