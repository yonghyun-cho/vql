package query.parser.vo;

import java.util.ArrayList;
import java.util.List;

import query.parser.QueryCommVar.TYPE_NAME;
import query.parser.QueryParserCommFunc;

public class ConstInfo extends PrimitiveType{
	
	// enum 형식으로 변환할 것.
	private final static String regexString = "^'[^']*'$";
	private final static String regexNumber = "^[0-9]+$";
	
	private String constValue = "";
	private TYPE_NAME typeName;
	
	public String getConstValue() {
		return constValue;
	}
	public void setConstValue(String constValue) {
		this.constValue = constValue;
	}
	public TYPE_NAME getTypeName() {
		return typeName;
	}
	public void setTypeName(TYPE_NAME typeName) {
		this.typeName = typeName;
	}
	
	public String toString(){
		return "상수값 : [" + this.constValue + "] // 상수타입 : ["+ this.typeName + "]";
	}
	
	public static ConstInfo convertStringToInfo(String value) throws Exception{
		String trimmedValue = value.trim();
		ConstInfo constInfo = new ConstInfo();
		
		constInfo.setConstValue(trimmedValue);
		
		if(QueryParserCommFunc.isMatched(trimmedValue, regexString)){
			constInfo.setTypeName(TYPE_NAME.STRING);
			
		}else if(QueryParserCommFunc.isMatched(trimmedValue, regexNumber)){
			constInfo.setTypeName(TYPE_NAME.INTEGER);
			
		}else{
			throw new Exception("SELECT STATEMENT ERROR");
		}
		
		return constInfo;
	}
	
	public static boolean isConstType(String value) throws Exception{
		List<String> regexList = new ArrayList<String>();
		regexList.add(regexString);
		regexList.add(regexNumber);
		
		return QueryParserCommFunc.isMatched(value, regexList);
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = super.equals(obj);
		
		if(super.equals(obj) == false){
			if(obj instanceof ConstInfo){
				ConstInfo targetInfo = (ConstInfo)obj;
				
				result = targetInfo.getConstValue().equals(this.constValue);
				result = targetInfo.getTypeName().equals(this.typeName) && result; 
			}
		}
		
		return result;
	}
}
