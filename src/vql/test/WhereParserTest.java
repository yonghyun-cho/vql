package vql.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import query.parser.WhereParser;
import query.parser.vo.ColumnInfo;
import query.parser.vo.ConditionInfo;
import query.parser.vo.WhereInfo;
import query.parser.vo.WhereType;

public class WhereParserTest {
	
	WhereParser whereParser = new WhereParser();
	
	@Test
	public void simple_WhereStmt_Test() throws Exception {
		String whereStmt = "EMP.DEPTNO = DEPT.DEPTNO";
		
		WhereInfo whereInfo = whereParser.parsingWhereStatement(whereStmt);
		
		WhereInfo targetWhereInfo = new WhereInfo();
		List<WhereType> conditionList = new ArrayList<WhereType>();

		ConditionInfo conditionInfo = new ConditionInfo();
		conditionInfo.setComparisionOp("=");
		
		ColumnInfo columnInfo1 = new ColumnInfo();
		columnInfo1.setTableName("EMP");
		columnInfo1.setColumnName("DEPTNO");
		conditionInfo.setSourceValue(columnInfo1);
		
		ColumnInfo columnInfo2 = new ColumnInfo();
		columnInfo2.setTableName("DEPT");
		columnInfo2.setColumnName("DEPTNO");
		conditionInfo.setTargetValue(columnInfo2);
		
		conditionList.add(conditionInfo);
		targetWhereInfo.setValueList(conditionList);
		
		assertThat(whereInfo, is(targetWhereInfo));
	}
	
	@Test
	public void twoCondition_WhereStmt_Test() throws Exception {
		String whereStmt = "EMP.DEPTNO = DEPT.DEPTNO AND EMP.LOC = DEPT.LOC";
		
		WhereInfo whereInfo = whereParser.parsingWhereStatement(whereStmt);
		
		WhereInfo targetWhereInfo = new WhereInfo();
		targetWhereInfo.setRelationOp("AND");
		List<WhereType> conditionList = new ArrayList<WhereType>();

		ConditionInfo conditionInfo = new ConditionInfo();
		conditionInfo.setComparisionOp("=");
		
		ColumnInfo columnInfo1 = new ColumnInfo();
		columnInfo1.setTableName("EMP");
		columnInfo1.setColumnName("DEPTNO");
		conditionInfo.setSourceValue(columnInfo1);
		
		ColumnInfo columnInfo2 = new ColumnInfo();
		columnInfo2.setTableName("DEPT");
		columnInfo2.setColumnName("DEPTNO");
		conditionInfo.setTargetValue(columnInfo2);
		
		conditionList.add(conditionInfo);
		
		//
		ConditionInfo conditionInfo2 = new ConditionInfo();
		conditionInfo2.setComparisionOp("=");
		
		ColumnInfo columnInfo2_1 = new ColumnInfo();
		columnInfo2_1.setTableName("EMP");
		columnInfo2_1.setColumnName("LOC");
		conditionInfo2.setSourceValue(columnInfo2_1);
		
		ColumnInfo columnInfo2_2 = new ColumnInfo();
		columnInfo2_2.setTableName("DEPT");
		columnInfo2_2.setColumnName("LOC");
		conditionInfo2.setTargetValue(columnInfo2_2);
		
		conditionList.add(conditionInfo2);
		
		//
		targetWhereInfo.setValueList(conditionList);
		
		assertThat(whereInfo, is(targetWhereInfo));
	}
	
	@Test
	public void threeCondition_WhereStmt_Test() throws Exception {
		String whereStmt = "EMP.DEPTNO = DEPT.DEPTNO AND EMP.LOC = DEPT.LOC "
				+ "AND EMP.COLA = DEPT.COLA";
		
		WhereInfo whereInfo = whereParser.parsingWhereStatement(whereStmt);
		
		WhereInfo targetWhereInfo = new WhereInfo();
		targetWhereInfo.setRelationOp("AND");
		List<WhereType> conditionList = new ArrayList<WhereType>();

		ConditionInfo conditionInfo = new ConditionInfo();
		conditionInfo.setComparisionOp("=");
		
		ColumnInfo columnInfo1 = new ColumnInfo();
		columnInfo1.setTableName("EMP");
		columnInfo1.setColumnName("DEPTNO");
		conditionInfo.setSourceValue(columnInfo1);
		
		ColumnInfo columnInfo2 = new ColumnInfo();
		columnInfo2.setTableName("DEPT");
		columnInfo2.setColumnName("DEPTNO");
		conditionInfo.setTargetValue(columnInfo2);
		
		conditionList.add(conditionInfo);
		
		//
		ConditionInfo conditionInfo2 = new ConditionInfo();
		conditionInfo2.setComparisionOp("=");
		
		ColumnInfo columnInfo2_1 = new ColumnInfo();
		columnInfo2_1.setTableName("EMP");
		columnInfo2_1.setColumnName("LOC");
		conditionInfo2.setSourceValue(columnInfo2_1);
		
		ColumnInfo columnInfo2_2 = new ColumnInfo();
		columnInfo2_2.setTableName("DEPT");
		columnInfo2_2.setColumnName("LOC");
		conditionInfo2.setTargetValue(columnInfo2_2);
		
		conditionList.add(conditionInfo2);
		
		//
		ConditionInfo conditionInfo3 = new ConditionInfo();
		conditionInfo3.setComparisionOp("=");
		
		ColumnInfo columnInfo3_1 = new ColumnInfo();
		columnInfo3_1.setTableName("EMP");
		columnInfo3_1.setColumnName("COLA");
		conditionInfo3.setSourceValue(columnInfo3_1);
		
		ColumnInfo columnInfo3_2 = new ColumnInfo();
		columnInfo3_2.setTableName("DEPT");
		columnInfo3_2.setColumnName("COLA");
		conditionInfo3.setTargetValue(columnInfo3_2);
		
		conditionList.add(conditionInfo3);
		
		//
		targetWhereInfo.setValueList(conditionList);
		
		assertThat(whereInfo, is(targetWhereInfo));
	}
	
	// EMP.DEPTNO = DEPT.DEPTNO 
	// OR (EMP.LOC = DEPT.LOC AND EMP.COLA = DEPT.COLA)
	@Test
	public void ComplexOp_WhereStmt_Test() throws Exception {
		String whereStmt = "EMP.DEPTNO = DEPT.DEPTNO OR EMP.LOC = DEPT.LOC "
				+ "AND EMP.COLA = DEPT.COLA";
		
		WhereInfo whereInfo = whereParser.parsingWhereStatement(whereStmt);
		
		////
		WhereInfo targetWhereInfo = new WhereInfo();
		targetWhereInfo.setRelationOp("OR");
		List<WhereType> conditionList = new ArrayList<WhereType>();

		//
		ConditionInfo conditionInfo = new ConditionInfo();
		conditionInfo.setComparisionOp("=");
		
		ColumnInfo columnInfo1 = new ColumnInfo();
		columnInfo1.setTableName("EMP");
		columnInfo1.setColumnName("DEPTNO");
		conditionInfo.setSourceValue(columnInfo1);
		
		ColumnInfo columnInfo2 = new ColumnInfo();
		columnInfo2.setTableName("DEPT");
		columnInfo2.setColumnName("DEPTNO");
		conditionInfo.setTargetValue(columnInfo2);
		
		conditionList.add(conditionInfo);
		
		////
		WhereInfo subWhereInfo = new WhereInfo();
		subWhereInfo.setRelationOp("AND");
		List<WhereType> subConditionList = new ArrayList<WhereType>();
		
		//
		ConditionInfo conditionInfo2 = new ConditionInfo();
		conditionInfo2.setComparisionOp("=");
		
		ColumnInfo columnInfo2_1 = new ColumnInfo();
		columnInfo2_1.setTableName("EMP");
		columnInfo2_1.setColumnName("LOC");
		conditionInfo2.setSourceValue(columnInfo2_1);
		
		ColumnInfo columnInfo2_2 = new ColumnInfo();
		columnInfo2_2.setTableName("DEPT");
		columnInfo2_2.setColumnName("LOC");
		conditionInfo2.setTargetValue(columnInfo2_2);
		
		subConditionList.add(conditionInfo2);
		
		//
		ConditionInfo conditionInfo3 = new ConditionInfo();
		conditionInfo3.setComparisionOp("=");
		
		ColumnInfo columnInfo3_1 = new ColumnInfo();
		columnInfo3_1.setTableName("EMP");
		columnInfo3_1.setColumnName("COLA");
		conditionInfo3.setSourceValue(columnInfo3_1);
		
		ColumnInfo columnInfo3_2 = new ColumnInfo();
		columnInfo3_2.setTableName("DEPT");
		columnInfo3_2.setColumnName("COLA");
		conditionInfo3.setTargetValue(columnInfo3_2);
		
		subConditionList.add(conditionInfo3);
		
		subWhereInfo.setValueList(subConditionList);
		
		conditionList.add(subWhereInfo);
		
		////
		targetWhereInfo.setValueList(conditionList);
		
		assertThat(whereInfo, is(targetWhereInfo));
	}
	
	// EMP.DEPTNO = DEPT.DEPTNO 
	// OR (EMP.LOC = DEPT.LOC AND EMP.COLA = DEPT.COLA)
	@Test
	public void ComplexOp2_WhereStmt_Test() throws Exception {
		String whereStmt = "EMP.DEPTNO = DEPT.DEPTNO OR EMP.LOC = DEPT.LOC "
				+ "AND EMP.COLA = DEPT.COLA OR EMP.COLB = DEPT.COLB";
		
		WhereInfo whereInfo = whereParser.parsingWhereStatement(whereStmt);
		
		////
		WhereInfo targetWhereInfo = new WhereInfo();
		targetWhereInfo.setRelationOp("OR");
		List<WhereType> conditionList = new ArrayList<WhereType>();

		//
		ConditionInfo conditionInfo = new ConditionInfo();
		conditionInfo.setComparisionOp("=");
		
		ColumnInfo columnInfo1 = new ColumnInfo();
		columnInfo1.setTableName("EMP");
		columnInfo1.setColumnName("DEPTNO");
		conditionInfo.setSourceValue(columnInfo1);
		
		ColumnInfo columnInfo2 = new ColumnInfo();
		columnInfo2.setTableName("DEPT");
		columnInfo2.setColumnName("DEPTNO");
		conditionInfo.setTargetValue(columnInfo2);
		
		conditionList.add(conditionInfo);
		
		////
		WhereInfo subWhereInfo = new WhereInfo();
		subWhereInfo.setRelationOp("AND");
		List<WhereType> subConditionList = new ArrayList<WhereType>();
		
		//
		ConditionInfo conditionInfo2 = new ConditionInfo();
		conditionInfo2.setComparisionOp("=");
		
		ColumnInfo columnInfo2_1 = new ColumnInfo();
		columnInfo2_1.setTableName("EMP");
		columnInfo2_1.setColumnName("LOC");
		conditionInfo2.setSourceValue(columnInfo2_1);
		
		ColumnInfo columnInfo2_2 = new ColumnInfo();
		columnInfo2_2.setTableName("DEPT");
		columnInfo2_2.setColumnName("LOC");
		conditionInfo2.setTargetValue(columnInfo2_2);
		
		subConditionList.add(conditionInfo2);
		
		//
		ConditionInfo conditionInfo3 = new ConditionInfo();
		conditionInfo3.setComparisionOp("=");
		
		ColumnInfo columnInfo3_1 = new ColumnInfo();
		columnInfo3_1.setTableName("EMP");
		columnInfo3_1.setColumnName("COLA");
		conditionInfo3.setSourceValue(columnInfo3_1);
		
		ColumnInfo columnInfo3_2 = new ColumnInfo();
		columnInfo3_2.setTableName("DEPT");
		columnInfo3_2.setColumnName("COLA");
		conditionInfo3.setTargetValue(columnInfo3_2);
		
		subConditionList.add(conditionInfo3);
		
		subWhereInfo.setValueList(subConditionList);
		
		conditionList.add(subWhereInfo);
		
		//
		ConditionInfo conditionInfo4 = new ConditionInfo();
		conditionInfo4.setComparisionOp("=");
		
		ColumnInfo columnInfo4_1 = new ColumnInfo();
		columnInfo4_1.setTableName("EMP");
		columnInfo4_1.setColumnName("COLB");
		conditionInfo4.setSourceValue(columnInfo4_1);
		
		ColumnInfo columnInfo4_2 = new ColumnInfo();
		columnInfo4_2.setTableName("DEPT");
		columnInfo4_2.setColumnName("COLB");
		conditionInfo4.setTargetValue(columnInfo4_2);
		
		conditionList.add(conditionInfo4);
		
		////
		targetWhereInfo.setValueList(conditionList);
		
		assertThat(whereInfo, is(targetWhereInfo));
	}

	@Test
	public void complexCondition1_WhereStmt_Test() throws Exception {
		// 0_OTHER_BRACKET : EMP.DEPTNO = DEPT.DEPTNO
		// 1_OTHER_BRACKET : EMP.LOC = DEPT.LOC
		
		String whereStmt = "0_OTHER_BRACKET AND 1_OTHER_BRACKET";
		
		WhereInfo whereInfo = whereParser.parsingWhereStatement(whereStmt);
		
		WhereInfo targetWhereInfo = new WhereInfo();
		targetWhereInfo.setRelationOp("AND");
		List<WhereType> conditionList = new ArrayList<WhereType>();

		ConditionInfo conditionInfo = new ConditionInfo();
		conditionInfo.setComparisionOp("=");
		
		ColumnInfo columnInfo1 = new ColumnInfo();
		columnInfo1.setTableName("EMP");
		columnInfo1.setColumnName("DEPTNO");
		conditionInfo.setSourceValue(columnInfo1);
		
		ColumnInfo columnInfo2 = new ColumnInfo();
		columnInfo2.setTableName("DEPT");
		columnInfo2.setColumnName("DEPTNO");
		conditionInfo.setTargetValue(columnInfo2);
		
		conditionList.add(conditionInfo);
		
		//
		ConditionInfo conditionInfo2 = new ConditionInfo();
		conditionInfo2.setComparisionOp("=");
		
		ColumnInfo columnInfo2_1 = new ColumnInfo();
		columnInfo2_1.setTableName("EMP");
		columnInfo2_1.setColumnName("LOC");
		conditionInfo2.setSourceValue(columnInfo2_1);
		
		ColumnInfo columnInfo2_2 = new ColumnInfo();
		columnInfo2_2.setTableName("DEPT");
		columnInfo2_2.setColumnName("LOC");
		conditionInfo2.setTargetValue(columnInfo2_2);
		
		conditionList.add(conditionInfo2);
		
		//
		targetWhereInfo.setValueList(conditionList);
		
		assertThat(whereInfo, is(targetWhereInfo));
	}
}
