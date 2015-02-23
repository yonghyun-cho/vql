package query.parser.vo;

import java.util.ArrayList;
import java.util.List;

public class FunctionInfo extends PrimitiveType {
	private String functionName = "";
	private List<PrimitiveType> valueList = new ArrayList<PrimitiveType>();
	
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public List<PrimitiveType> getValueList() {
		return valueList;
	}
	public void setValueList(List<PrimitiveType> valueList) {
		this.valueList = valueList;
	}
	
	public static boolean isFunctionType(String value){
		List<String> regexList = new ArrayList<String>();
		regexList.add(""); // TODO
		
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
