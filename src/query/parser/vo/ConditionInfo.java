package query.parser.vo;

import query.parser.QueryCommVar.CMPR_OP;

public class ConditionInfo implements WhereType {
	
	// 비교연산자 명
	private CMPR_OP comparisionOp;

	private QueryComponentType[] valueList = new QueryComponentType[2];

	public CMPR_OP getComparisionOp() {
		return comparisionOp;
	}

	public void setComparisionOp(String comparisionOp) throws Exception {
		this.comparisionOp = CMPR_OP.getEnum(comparisionOp);
	}
	
	public void setComparisionOp(CMPR_OP comparisionOp) {
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
		String result = "<비교연산자 : [" + this.comparisionOp + "] >\n";
		
		for(int i = 0; i < valueList.length; i++){
			result = result + valueList[i] + "\n";
		}
		
		return result;
	}
	
	public static ConditionInfo convertStringToInfo(String value) throws Exception{
		ConditionInfo conditionInfo = new ConditionInfo();
		
		// TODO 특이하게  <=의 경우  <    = 이렇게 띄어써도 적용된다.
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
						throw new Exception("잘못된 WHERE절 CONDITION 형식");
					}
					
					// TODO 임시로 첫번째가 Source 두번째가 Target으로 설정
					// 나중에 column을 첫번째, 둘다 column이면 순서대로 설정할 것
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
			
				result = targetInfo.getComparisionOp().equals(this.comparisionOp);
				result = targetInfo.getSourceValue().equals(this.getSourceValue()) && result;
				result = targetInfo.getTargetValue().equals(this.getTargetValue()) && result;
			}
		}
		
		return result;
	}
}
