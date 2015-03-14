package query.parser.vo;

import java.util.ArrayList;
import java.util.List;

import query.parser.QueryCommVar;
import query.parser.QueryCommVar.CMPR_OP;

public class ConditionInfo implements WhereType {
	
	// �񱳿����� ��
	private String comparisionOp = "";

	private QueryComponentType[] valueList = new QueryComponentType[2];

	public String getComparisionOp() {
		return comparisionOp;
	}

	public void setComparisionOp(String comparisionOp) {
		this.comparisionOp = comparisionOp;
	}

	public void setSourceValue(QueryComponentType sourceValue){
		valueList[0] = sourceValue;
	}
	
	public QueryComponentType getSourceValue(){
		return valueList[0];
	}
	
	public void setTargetValue(QueryComponentType targetValue){
		valueList[1] = targetValue;
	}
	
	public QueryComponentType getTargetValue(){
		return valueList[1];
	}
	
	public String toString(){
		String result = "<�񱳿����� : [" + this.comparisionOp + "] >\n";
		
		for(int i = 0; i < valueList.length; i++){
			result = result + valueList[i] + "\n";
		}
		
		return result;
	}
	
	public static ConditionInfo convertStringToInfo(String value) throws Exception{
		ConditionInfo conditionInfo = new ConditionInfo();
		
		// TODO Ư���ϰ�  <=�� ���  <    = �̷��� ���ᵵ ����ȴ�.
		for(CMPR_OP cmprOp : CMPR_OP.values()){
			final String cmprOpString = cmprOp.getValue();
			
			if(value.indexOf(cmprOpString) > 0){
				conditionInfo.setComparisionOp(cmprOpString);
				
				String[] splitCondition = value.split(cmprOpString);
				for(int k = 0; k < splitCondition.length; k++){
					QueryComponentType queryComponentType = null;
					
					String selectStmt = splitCondition[k].trim();
					if(ColumnInfo.isColumnType(selectStmt)){
						queryComponentType = ColumnInfo.convertStringToInfo(selectStmt);
						
					}else if(ConstInfo.isConstType(selectStmt)){
						queryComponentType = ConstInfo.convertStringToInfo(selectStmt);
						
					}else{
						throw new Exception("�߸��� WHERE�� CONDITION ����");
					}
					
					// TODO �ӽ÷� ù��°�� Source �ι�°�� Target���� ����
					// ���߿� column�� ù��°, �Ѵ� column�̸� ������� ������ ��
					if(k == 0){
						conditionInfo.setSourceValue(queryComponentType);
					}else {
						conditionInfo.setTargetValue(queryComponentType);
					}
				}
				
				break;
			}
		}
		
		return conditionInfo;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = super.equals(obj);
		
		if(super.equals(obj) == false){
			if(obj instanceof ConditionInfo){
				ConditionInfo targetInfo = (ConditionInfo)obj;
				
				if(targetInfo.getComparisionOp().equals(this.comparisionOp)
						&& targetInfo.getSourceValue().equals(this.getSourceValue())
						&& targetInfo.getTargetValue().equals(this.getTargetValue())){
					result = true;
				}
				
			} else {
				result = false;
			}
		}
		
		return result;
	}
}
