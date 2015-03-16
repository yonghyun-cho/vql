package vql.test;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Ignore;
import org.junit.Test;

import query.parser.SelectParser;
import query.parser.vo.ColumnInfo;
import query.parser.vo.ConstInfo;
import query.parser.vo.QueryComponentType;

public class SelectParserTest {
	
	SelectParser selectParser = new SelectParser();

	@Test
	public void simple_SelectStmt_Test() throws Exception {
		String selectStmt = "EMP.ENAME, EMP.SAL, DEPT.DNAME";
		
		List<QueryComponentType> selectStmtInfo = selectParser.parsingSelectStatement(selectStmt);
		
		ColumnInfo columnInfo1 = new ColumnInfo();
		columnInfo1.setTableName("EMP");
		columnInfo1.setColumnName("ENAME");
		
		ColumnInfo columnInfo2 = new ColumnInfo();
		columnInfo2.setTableName("EMP");
		columnInfo2.setColumnName("SAL");

		ColumnInfo columnInfo3 = new ColumnInfo();
		columnInfo3.setTableName("DEPT");
		columnInfo3.setColumnName("DNAME");
		
		assertThat(selectStmtInfo, 
				IsIterableContainingInAnyOrder.<QueryComponentType>containsInAnyOrder(columnInfo1, columnInfo2, columnInfo3));
	}
	
	@Test
	public void const_SelectStmt_Test() throws Exception {
		String selectStmt = "EMP.ENAME, EMP.SAL, 1002";
		
		List<QueryComponentType> selectStmtInfo = selectParser.parsingSelectStatement(selectStmt);
		
		ColumnInfo columnInfo1 = new ColumnInfo();
		columnInfo1.setTableName("EMP");
		columnInfo1.setColumnName("ENAME");
		
		ColumnInfo columnInfo2 = new ColumnInfo();
		columnInfo2.setTableName("EMP");
		columnInfo2.setColumnName("SAL");

		ConstInfo constInfo = new ConstInfo();
		constInfo.setConstValue("1002");
		constInfo.setTypeName("NUMBER");
		
		assertThat(selectStmtInfo, 
				IsIterableContainingInAnyOrder.<QueryComponentType>containsInAnyOrder(columnInfo1, columnInfo2, constInfo));
	}

	// TODO scalar subquery 관련 테스트 추가할 것.
	@Test @Ignore
	public void subquery_SelectStmt_Test() throws Exception {
		String selectStmt = "EMP.ENAME, EMP.SAL";
		
		List<QueryComponentType> selectStmtInfo = selectParser.parsingSelectStatement(selectStmt);
		
		ColumnInfo columnInfo1 = new ColumnInfo();
		columnInfo1.setTableName("EMP");
		columnInfo1.setColumnName("ENAME");
		
		ColumnInfo columnInfo2 = new ColumnInfo();
		columnInfo2.setTableName("EMP");
		columnInfo2.setColumnName("SAL");

		ConstInfo constInfo = new ConstInfo();
		constInfo.setConstValue("1002");
		constInfo.setTypeName("NUMBER");
		
		assertThat(selectStmtInfo, 
				IsIterableContainingInAnyOrder.<QueryComponentType>containsInAnyOrder(columnInfo1, columnInfo2, constInfo));
	}
}
