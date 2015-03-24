package query.parser.vo;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.requests.TargetRequest;

import query.parser.FunctionNameEnum.FUNCTION;
import query.parser.QueryParserCommFunc;

// TODO FunctionInfo는 SubQueryInfo와 달리
// TODO   Info 자체적으로 분석을 다 해서 가지고 있어야 할 듯.

public class FunctionInfo extends QueryComponentType {
	// TODO Function Bracket 정리되면 같이 정리할 것.
	private String functionId = "";
	// TODO Function Bracket 정리되면 같이 정리할 것.	
	private String functionText = "";
	
	private FUNCTION functionName;
	
	private List<QueryComponentType> arguments = new ArrayList<QueryComponentType>();
	
	public FunctionInfo() {
		// Junit 테스트용.. 생성자에서 functionText를 입력하지 않으면 항상 빈값임.
	}
	// TODO Function Bracket 정리되면 같이 정리할 것.
	public FunctionInfo(String functionText) {
		this.functionText = functionText;
	}
	// TODO Function Bracket 정리되면 같이 정리할 것.
	public String getFunctionId() {
		return functionId;
	}
	// TODO Function Bracket 정리되면 같이 정리할 것.
	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}
	// TODO Function Bracket 정리되면 같이 정리할 것.
	public void setFunctionText(String functionText) {
		this.functionText = functionText;
	}
	// TODO Function Bracket 정리되면 같이 정리할 것.
	public String getFunctionText() {
		return functionText;
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

	public static boolean isFunctionId(String value) throws Exception{
		List<String> regexList = new ArrayList<String>();
		regexList.add("^[0-9]+_FUNCTION [a-zA-Z][a-zA-Z0-9]*$");
		regexList.add("^[0-9]+_FUNCTION$");

		return QueryParserCommFunc.isMatched(value, regexList);
	}
	
//	TODO FunctionInfo의 toString
//	public String toString(){
//		return "Function ID : [" + this.functionId + "]";
//	}
	
	public static boolean isFunctionText(String entireQuery, int bracketStartIndex, String bracketString){
		// "(" 이전에 Object명이 아닌 이외의 문자 [white space, ")", 등.. ]이 처음으로 오는 위치 파악.
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
		
		// lastSpecialIndex는 함수의 처음이 아니라 [a-zA-Z0-9] 이외 문자의 처음이므로.
		return lastSpecialIndex + 1;
	}

	// TODO 외부에서 값을 가져오는게 아니라 내부의 FunctionText를 parsing하도록 될 듯.
	public static FunctionInfo convertIdToInfo(String value){
		FunctionInfo functionInfo = new FunctionInfo();
//		functionInfo.setFunctionId(value);
		
		return functionInfo;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = super.equals(obj);
		
		if(super.equals(obj) == false){
			if(obj instanceof FunctionInfo){
				FunctionInfo targetInfo = (FunctionInfo)obj;
				
				result = targetInfo.getFunctionName().equals(this.functionName);
				result = QueryParserCommFunc.isEqualWithoutOrder(targetInfo.getArguments(), this.arguments) && result;
			}
		}
		
		return result;
	}
}
