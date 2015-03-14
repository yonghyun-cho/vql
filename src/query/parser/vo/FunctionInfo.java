package query.parser.vo;

import static query.parser.QueryCommVar.AGG_FUNCTION_LIST;

import java.util.ArrayList;
import java.util.List;

import query.parser.QueryCommVar.STATEMENT;
import query.parser.QueryParserCommFunc;

// TODO FunctionInfo는 SubQueryInfo와 달리
// TODO   Info 자체적으로 분석을 다 해서 가지고 있어야 할 듯.

public class FunctionInfo extends PrimitiveType {
	private String functionId = "";
	
	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
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
			for(int i = 0; i < AGG_FUNCTION_LIST.length; i++){
				if(AGG_FUNCTION_LIST[i].equals(functionName)){
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
			for(int i = 0; i < AGG_FUNCTION_LIST.length; i++){
				if(AGG_FUNCTION_LIST[i].equals(functionName)){
					break;
				}
			}
		}
		
		// lastSpecialIndex는 함수의 처음이 아니라 [a-zA-Z0-9] 이외 문자의 처음이므로.
		return lastSpecialIndex + 1;
	}
	
	public static FunctionInfo convertStringToInfo(String value){
		FunctionInfo functionInfo = new FunctionInfo();
		functionInfo.setFunctionId(value);
		
		return functionInfo;
	}
}
