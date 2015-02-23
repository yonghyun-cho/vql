package query.parser.vo;

import java.util.ArrayList;
import java.util.List;

public class ConstInfo extends PrimitiveType{
	
	// enum 형식으로 변환할 것.
	private final static String regexString = "^'[^']*'$";
	private final static String regexNumber = "^[0-9]+$";
	
	private String constValue = "";
	private String typeName = "";
	
	public String getConstValue() {
		return constValue;
	}
	public void setConstValue(String constValue) {
		this.constValue = constValue;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public String toString(){
		return "상수값 : [" + this.constValue + "] // 상수타입 : ["+ this.typeName + "]";
	}
	
	public static ConstInfo convertStringToInfo(String value) throws Exception{
		String trimmedValue = value.trim();
		ConstInfo constInfo = new ConstInfo();
		
		constInfo.setConstValue(trimmedValue);
		
		if(trimmedValue.matches(regexString)){
			constInfo.setTypeName("STRING");
			
		}else if(trimmedValue.matches(regexNumber)){
			constInfo.setTypeName("NUMBER");
			
		}else{
			throw new Exception("SELECT STATEMENT ERROR");
		}
		
		return constInfo;
	}
	
	public static boolean isConstType(String value){
		List<String> regexList = new ArrayList<String>();
		regexList.add(regexString);
		regexList.add(regexNumber);
		
		boolean result = false;
		
		if(value != null){
			for(int i = 0; i < regexList.size(); i++){
				result = value.matches(regexList.get(i));
				
				if(result == true){
					break;
				}
			}
		}
		
		return result;
	}
	
}
