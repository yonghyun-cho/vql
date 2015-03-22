package query.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import query.parser.FunctionNameEnum.FUNCTION;
import query.parser.vo.FunctionInfo;
import query.parser.vo.QueryComponentType;


// 1. 함수명 ( 파라미터 )
// 2. 변수 + 변수 - 변수
// 3. FUNCTION명 (...) OVER (...)

public class FunctionParser {
//	private String function 
	
	public FunctionInfo parsingFunction(String functionId, Map<String, String> functionMap) throws Exception{
		FunctionInfo functionInfo = new FunctionInfo();
		functionInfo.setFunctionId(functionId);
		
		functionInfo = this.parsingFunction(functionInfo, functionMap);
		
		return functionInfo;
	}
	
	public FunctionInfo parsingFunction(FunctionInfo functionInfo, Map<String, String> functionMap) throws Exception{
		String functionId = functionInfo.getFunctionId();
		
		String functionString = functionMap.get(functionId);
		functionInfo = this.parsingFunction(functionString);
		
		functionInfo.setFunctionId(functionId);
		
		return functionInfo;
	}
	
	public FunctionInfo parsingFunction(String functionString) throws Exception{
		FunctionInfo functionInfo = new FunctionInfo();
		
		functionString = functionString.trim();
		
		int bracketBeginIndex = functionString.indexOf("(");
		
		String functionName = functionString.substring(0, bracketBeginIndex).trim();
		for(FUNCTION function: FUNCTION.values()){
			final String functionNameString = function.getValue();
			
			if(functionNameString.equals(functionName)){
				functionInfo.setFunctionName(function);
				break;
			}
		}
		
		if(functionInfo.getFunctionName() == null){
			throw new Exception("올바른 Function명이 아닙니다. // " + functionName);
		}
		
		String argumentsString = functionString.substring(bracketBeginIndex).trim();
		argumentsString = BracketReplacer.replaceBracket(argumentsString);
		
		List<QueryComponentType> argumentList = this.parsingArguments(argumentsString);
		functionInfo.setArguments(argumentList);
		
		return functionInfo;
	}
	
	// SELECT Statement를 parsing
	public List<QueryComponentType> parsingArguments(String argumentsString) throws Exception{
		List<QueryComponentType> argumentsList = new ArrayList<QueryComponentType>();
		
		String[] splitContents = argumentsString.split(",");
		
		for(int i = 0; i < splitContents.length; i++){
			String selectStmt = splitContents[i].trim();
			
			QueryComponentType queryComponentType = null;
			
			if(QueryComponentType.isQueryComponenetType(selectStmt)){
				queryComponentType = QueryComponentType.convertStringToType(selectStmt);
			
			} else if(false){
				// TODO ?? 뭘 하려는 부분인지?
				
			} else{
				throw new Exception("잘못된 Function의 argument형식입니다.");
			}
			
			argumentsList.add(queryComponentType);
		}
		
		return argumentsList;
	}
}
