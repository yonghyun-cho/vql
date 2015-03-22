package vql.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import query.parser.FunctionNameEnum.FUNCTION;
import query.parser.FunctionParser;
import query.parser.QueryCommVar.TYPE_NAME;
import query.parser.vo.ColumnInfo;
import query.parser.vo.ConstInfo;
import query.parser.vo.FunctionInfo;
import query.parser.vo.QueryComponentType;

public class FunctionParserTest {
	
	FunctionParser functionParser = new FunctionParser();

	@Test
	public void simple_FunctionText_Test() throws Exception {
		String functionText = "SUM(EMP.SAL)";
		
		FunctionInfo functionInfo = functionParser.parsingFunction(functionText);
		
		FunctionInfo targetFunctionInfo = new FunctionInfo();
		targetFunctionInfo.setFunctionName(FUNCTION.SUM);
		
		List<QueryComponentType> arguments = new ArrayList<QueryComponentType>();
		ColumnInfo columnInfo1 = new ColumnInfo();
		columnInfo1.setColumnName("SAL");
		columnInfo1.setTableName("EMP");
		arguments.add(columnInfo1);
		
		
		targetFunctionInfo.setArguments(arguments);
		
		assertThat(functionInfo, is(targetFunctionInfo));
	}

	@Test
	public void simple2_FunctionText_Test() throws Exception {
		String functionText = "MOD(11,4)";
		
		FunctionInfo functionInfo = functionParser.parsingFunction(functionText);
		
		FunctionInfo targetFunctionInfo = new FunctionInfo();
		targetFunctionInfo.setFunctionName(FUNCTION.MOD);
		
		List<QueryComponentType> arguments = new ArrayList<QueryComponentType>();
		
		ConstInfo constInfo1 = new ConstInfo();
		constInfo1.setConstValue("11");
		constInfo1.setTypeName(TYPE_NAME.INTEGER);
		arguments.add(constInfo1);
		
		ConstInfo constInfo2 = new ConstInfo();
		constInfo2.setConstValue("4");
		constInfo2.setTypeName(TYPE_NAME.INTEGER);
		arguments.add(constInfo2);
		
		targetFunctionInfo.setArguments(arguments);
		
		assertThat(functionInfo, is(targetFunctionInfo));
	}
	
	@Test
	public void simple3_FunctionText_Test() throws Exception {
		// RTrim(LTrim(@sData))
		String functionText = "RTRIM(0_FUNCTION)";
		
		FunctionInfo functionInfo = functionParser.parsingFunction(functionText);
		
		FunctionInfo targetFunctionInfo = new FunctionInfo();
		targetFunctionInfo.setFunctionName(FUNCTION.RTRIM);
		
		List<QueryComponentType> arguments = new ArrayList<QueryComponentType>();
		
		FunctionInfo functionInfo1 = new FunctionInfo();
		functionInfo1.setFunctionId("0_FUNCTION");
		arguments.add(functionInfo1);
		
		targetFunctionInfo.setArguments(arguments);
		
		assertThat(functionInfo, is(targetFunctionInfo));
	}
}
