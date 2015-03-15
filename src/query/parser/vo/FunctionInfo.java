package query.parser.vo;

import java.util.ArrayList;
import java.util.List;

import query.parser.QueryCommVar.FUNCTION;
import query.parser.QueryParserCommFunc;

// TODO FunctionInfo�� SubQueryInfo�� �޸�
// TODO   Info ��ü������ �м��� �� �ؼ� ������ �־�� �� ��.

public class FunctionInfo extends PrimitiveType {
	private String functionId = "";
	
	private FUNCTION functionName;
	
	private List<QueryComponentType> arguments = new ArrayList<QueryComponentType>();
	
	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}
	
	public FUNCTION getFunctionName() {
		return functionName;
	}

	public void setFunctionName(FUNCTION functionName) {
		this.functionName = functionName;
	}

	public List<QueryComponentType> getArguments() {
		return arguments;
	}

	public void setArguments(List<QueryComponentType> arguments) {
		this.arguments = arguments;
	}
	
	public boolean addArguments(QueryComponentType argument){
		return this.arguments.add(argument);
	}

	public static boolean isFunctionType(String value) throws Exception{
		List<String> regexList = new ArrayList<String>();
		regexList.add("^[0-9]+_FUNCTION [a-zA-Z][a-zA-Z0-9]*$");
		regexList.add("^[0-9]+_FUNCTION$");

		return QueryParserCommFunc.isMatched(value, regexList);
	}
	
	public static boolean isFunctionText(String value){
		
		return false;
	}
	
	public String toString(){
		return "Function ID : [" + this.functionId + "]";
	}
	
	public static boolean isFunctionText(String entireQuery, int bracketStartIndex, String bracketString){
		int lastSpecialIndex = QueryParserCommFunc.lastIndexOfRegex(entireQuery, "[^a-zA-Z0-9_$#]", bracketStartIndex - 50, bracketStartIndex);
		
		boolean isFunctionName = false;
		String functionName = "";

		if(lastSpecialIndex > 0){
			functionName = entireQuery.substring(lastSpecialIndex + 1, bracketStartIndex).trim();
			
			for(FUNCTION function: FUNCTION.values()){
				final String functionString = function.getValue();
				
				if(functionString.equals(functionName)){
					isFunctionName = true;
					break;
				}
			}
		}
		
		return isFunctionName;
	}
	
	public static int getFunctionStartIndex(String entireQuery, int bracketStartIndex, String bracketString){
		int lastSpecialIndex = QueryParserCommFunc.lastIndexOfRegex(entireQuery, "[^a-zA-Z0-9_$#]", bracketStartIndex - 50, bracketStartIndex);
		String functionName = "";

		if(lastSpecialIndex > 0){
			functionName = entireQuery.substring(lastSpecialIndex + 1, bracketStartIndex).trim();
			
			for(FUNCTION function: FUNCTION.values()){
				final String functionString = function.getValue();
				
				if(functionString.equals(functionName)){
					break;
				}
			}
		}
		
		// lastSpecialIndex�� �Լ��� ó���� �ƴ϶� [a-zA-Z0-9] �̿� ������ ó���̹Ƿ�.
		return lastSpecialIndex + 1;
	}
	
	public static FunctionInfo convertStringToInfo(String value){
		FunctionInfo functionInfo = new FunctionInfo();
		
		
		
		functionInfo.setFunctionId(value);
		
		return functionInfo;
	}
}
