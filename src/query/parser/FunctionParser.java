package query.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import query.parser.FunctionNameEnum.FUNCTION;
import query.parser.vo.FunctionInfo;
import query.parser.vo.PrimitiveType;
import query.parser.vo.QueryComponentType;
import query.parser.vo.TableViewType;


// 1. 함수명 ( 파라미터 )
// 2. 변수 + 변수 - 변수
// 3. FUNCTION명 (...) OVER (...)

public class FunctionParser {
//	private String function 
	/** 분리된 함수 목록 */
	private Map<String, String> functionMap = new HashMap<String, String>();
	
	/** 분리된 기타 (연산자 관련 소괄호) */
	private Map<String, String> otherBracketMap = new HashMap<String, String>();
	
	/** 서브쿼리 목록 */
	
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
		argumentsString = BracketDistributor.replaceBracket(argumentsString);
		
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
			
			if(PrimitiveType.isPrimitiveType(selectStmt)){
				queryComponentType = PrimitiveType.convertStringToType(selectStmt);
				
			} else if(TableViewType.isTableViewType(selectStmt)){
				queryComponentType = TableViewType.convertStringToType(selectStmt);
				
			} else if(FunctionInfo.isFunctionId(selectStmt)){
				selectStmt = functionMap.get(selectStmt);
				// TODO FunctionParser
				
			} else{
				throw new Exception("잘못된 Function의 Argument형식");
			}
			
			argumentsList.add(queryComponentType);
		}
		
		return argumentsList;
	}
}
