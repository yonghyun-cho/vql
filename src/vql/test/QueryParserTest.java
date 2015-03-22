package vql.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import query.parser.QueryParser;
import query.parser.QueryCommVar.LGCL_OP;
import query.parser.QueryCommVar.TYPE_NAME;
import query.parser.vo.ColumnInfo;
import query.parser.vo.ConditionInfo;
import query.parser.vo.ConstInfo;
import query.parser.vo.QueryComponentType;
import query.parser.vo.QueryInfo;
import query.parser.vo.TableInfo;
import query.parser.vo.TableViewType;
import query.parser.vo.WhereInfo;
import query.parser.vo.WhereType;

public class QueryParserTest {

	QueryParser queryParser = new QueryParser();
	
	@Test
	public void simple_QueryStmt_Test() throws Exception {
		String queryStmt = "SELECT EMP.ENAME, EMP.SAL, DEPT.DNAME FROM EMP, DEPT WHERE EMP.DEPTNO = DEPT.DEPTNO";
		
		queryParser.setOriginalQuery(queryStmt);
		queryParser.parsingQueryToVisualQueryInfo();
		
		QueryInfo mainQueryInfo = queryParser.getMainQueryInfo();
		
		////
		QueryInfo targetQueryInfo = new QueryInfo();
		targetQueryInfo.setQueryId("0_SUBQUERY_MAIN");
		
		//
		List<QueryComponentType> selectStmtInfo = new ArrayList<QueryComponentType>();
		
		ColumnInfo columnInfo1 = new ColumnInfo();
		columnInfo1.setTableName("EMP");
		columnInfo1.setColumnName("ENAME");
		selectStmtInfo.add(columnInfo1);
		
		ColumnInfo columnInfo2 = new ColumnInfo();
		columnInfo2.setTableName("EMP");
		columnInfo2.setColumnName("SAL");
		selectStmtInfo.add(columnInfo2);
		
		ColumnInfo columnInfo3 = new ColumnInfo();
		columnInfo3.setTableName("DEPT");
		columnInfo3.setColumnName("DNAME");
		selectStmtInfo.add(columnInfo3);
		
		targetQueryInfo.setSelectStmtInfo(selectStmtInfo);
		
		//
		List<TableViewType> fromStmtInfo = new ArrayList<TableViewType>();
		
		TableInfo tableInfo1 = new TableInfo();
		tableInfo1.setTableName("EMP");
		fromStmtInfo.add(tableInfo1);
		
		TableInfo tableInfo2 = new TableInfo();
		tableInfo2.setTableName("DEPT");
		fromStmtInfo.add(tableInfo2);
		
		targetQueryInfo.setFromStmtInfo(fromStmtInfo);
		
		//
		WhereInfo whereStmtInfo = new WhereInfo();
		whereStmtInfo.setRelationOp(LGCL_OP.AND);
		
		ConditionInfo conditionInfo1 = new ConditionInfo();
		conditionInfo1.setComparisionOp("=");
		
		ColumnInfo columnInfo_c1 = new ColumnInfo();
		columnInfo_c1.setTableName("EMP");
		columnInfo_c1.setColumnName("DEPTNO");
		conditionInfo1.setSourceValue(columnInfo_c1);
		
		ColumnInfo columnInfo_c2 = new ColumnInfo();
		columnInfo_c2.setTableName("DEPT");
		columnInfo_c2.setColumnName("DEPTNO");
		conditionInfo1.setTargetValue(columnInfo_c2);
		
		whereStmtInfo.addValueToList(conditionInfo1);
		
		targetQueryInfo.setWhereStmtInfo(whereStmtInfo);
		
		////
		
		assertThat(mainQueryInfo, is(targetQueryInfo));
	}

	@Test
	public void simple2_QueryStmt_Test() throws Exception {
		String queryStmt = "SELECT EMP.ENAME, EMP.SAL, DEPT.DNAME "
				+ "FROM EMP, DEPT"
				+ "WHERE EMP.DEPTNO = DEPT.DEPTNO AND EMP.LOC = DEPT.LOC AND EMP.COLA = DEPT.COLA";
		
		queryParser.setOriginalQuery(queryStmt);
		queryParser.parsingQueryToVisualQueryInfo();
		
		QueryInfo mainQueryInfo = queryParser.getMainQueryInfo();
		
		////
		QueryInfo targetQueryInfo = new QueryInfo();
		targetQueryInfo.setQueryId("0_SUBQUERY_MAIN");
		
		//
		List<QueryComponentType> selectStmtInfo = new ArrayList<QueryComponentType>();
		
		ColumnInfo columnInfo1 = new ColumnInfo();
		columnInfo1.setTableName("EMP");
		columnInfo1.setColumnName("ENAME");
		selectStmtInfo.add(columnInfo1);
		
		ColumnInfo columnInfo2 = new ColumnInfo();
		columnInfo2.setTableName("EMP");
		columnInfo2.setColumnName("SAL");
		selectStmtInfo.add(columnInfo2);
		
		ColumnInfo columnInfo3 = new ColumnInfo();
		columnInfo3.setTableName("DEPT");
		columnInfo3.setColumnName("DNAME");
		selectStmtInfo.add(columnInfo3);
		
		targetQueryInfo.setSelectStmtInfo(selectStmtInfo);
		
		//
		List<TableViewType> fromStmtInfo = new ArrayList<TableViewType>();
		
		TableInfo tableInfo1 = new TableInfo();
		tableInfo1.setTableName("EMP");
		fromStmtInfo.add(tableInfo1);
		
		TableInfo tableInfo2 = new TableInfo();
		tableInfo2.setTableName("DEPT");
		fromStmtInfo.add(tableInfo2);
		
		targetQueryInfo.setFromStmtInfo(fromStmtInfo);
		
		//
		WhereInfo whereStmtInfo = new WhereInfo();
		whereStmtInfo.setRelationOp("AND");
		List<WhereType> conditionList = new ArrayList<WhereType>();

		ConditionInfo conditionInfo = new ConditionInfo();
		conditionInfo.setComparisionOp("=");
		
		ColumnInfo columnInfo4 = new ColumnInfo();
		columnInfo4.setTableName("EMP");
		columnInfo4.setColumnName("DEPTNO");
		conditionInfo.setSourceValue(columnInfo4);
		
		ColumnInfo columnInfo5 = new ColumnInfo();
		columnInfo5.setTableName("DEPT");
		columnInfo5.setColumnName("DEPTNO");
		conditionInfo.setTargetValue(columnInfo5);
		
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
		whereStmtInfo.setValueList(conditionList);
		
		targetQueryInfo.setWhereStmtInfo(whereStmtInfo);
		
		////
		
		assertThat(mainQueryInfo, is(targetQueryInfo));
	}
	
	@Test
	public void simple3_QueryStmt_Test() throws Exception {
		String queryStmt = 	  "SELECT EMP.ENAME, EMP.SAL, DEPT.DNAME "
							+ "FROM EMP, DEPT"
							+ "WHERE EMP.DEPTNO = DEPT.DEPTNO "
									+ "OR EMP.LOC = DEPT.LOC "
									+ "AND EMP.COLA = DEPT.COLA "
									+ "OR EMP.COLB = DEPT.COLB";
		
		queryParser.setOriginalQuery(queryStmt);
		queryParser.parsingQueryToVisualQueryInfo();
		
		QueryInfo mainQueryInfo = queryParser.getMainQueryInfo();
		
		////
		QueryInfo targetQueryInfo = new QueryInfo();
		targetQueryInfo.setQueryId("0_SUBQUERY_MAIN");
		
		//
		List<QueryComponentType> selectStmtInfo = new ArrayList<QueryComponentType>();
		
		ColumnInfo columnInfo1 = new ColumnInfo();
		columnInfo1.setTableName("EMP");
		columnInfo1.setColumnName("ENAME");
		selectStmtInfo.add(columnInfo1);
		
		ColumnInfo columnInfo2 = new ColumnInfo();
		columnInfo2.setTableName("EMP");
		columnInfo2.setColumnName("SAL");
		selectStmtInfo.add(columnInfo2);
		
		ColumnInfo columnInfo3 = new ColumnInfo();
		columnInfo3.setTableName("DEPT");
		columnInfo3.setColumnName("DNAME");
		selectStmtInfo.add(columnInfo3);
		
		targetQueryInfo.setSelectStmtInfo(selectStmtInfo);
		
		//
		List<TableViewType> fromStmtInfo = new ArrayList<TableViewType>();
		
		TableInfo tableInfo1 = new TableInfo();
		tableInfo1.setTableName("EMP");
		fromStmtInfo.add(tableInfo1);
		
		TableInfo tableInfo2 = new TableInfo();
		tableInfo2.setTableName("DEPT");
		fromStmtInfo.add(tableInfo2);
		
		targetQueryInfo.setFromStmtInfo(fromStmtInfo);
		
		WhereInfo whereStmtInfo = new WhereInfo();
		whereStmtInfo.setRelationOp("OR");
		List<WhereType> conditionList = new ArrayList<WhereType>();

		//
		ConditionInfo conditionInfo = new ConditionInfo();
		conditionInfo.setComparisionOp("=");
		
		ColumnInfo columnInfo4 = new ColumnInfo();
		columnInfo4.setTableName("EMP");
		columnInfo4.setColumnName("DEPTNO");
		conditionInfo.setSourceValue(columnInfo4);
		
		ColumnInfo columnInfo5 = new ColumnInfo();
		columnInfo5.setTableName("DEPT");
		columnInfo5.setColumnName("DEPTNO");
		conditionInfo.setTargetValue(columnInfo5);
		
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
		whereStmtInfo.setValueList(conditionList);
		
		targetQueryInfo.setWhereStmtInfo(whereStmtInfo);
		
		////
		
		assertThat(mainQueryInfo, is(targetQueryInfo));
	}
	
	@Test
	public void simple4_QueryStmt_Test() throws Exception {
		String queryStmt = 	  "SELECT EMP.ENAME, EMP.SAL, DEPT.DNAME "
							+ "FROM EMP, DEPT"
							+ "WHERE EMP.DEPTNO = DEPT.DEPTNO "
									+ "OR EMP.LOC = DEPT.LOC "
									+ "AND EMP.COLA = DEPT.COLA "
									+ "OR EMP.COLB = DEPT.COLB "
									+ "AND EMP.COLC = 2014 "
									+ "OR DEPT.COLD = 'TEST'";
		
		queryParser.setOriginalQuery(queryStmt);
		queryParser.parsingQueryToVisualQueryInfo();
		
		QueryInfo mainQueryInfo = queryParser.getMainQueryInfo();
		
		////
		QueryInfo targetQueryInfo = new QueryInfo();
		targetQueryInfo.setQueryId("0_SUBQUERY_MAIN");
		
		//
		List<QueryComponentType> selectStmtInfo = new ArrayList<QueryComponentType>();
		
		ColumnInfo columnInfo1 = new ColumnInfo();
		columnInfo1.setTableName("EMP");
		columnInfo1.setColumnName("ENAME");
		selectStmtInfo.add(columnInfo1);
		
		ColumnInfo columnInfo2 = new ColumnInfo();
		columnInfo2.setTableName("EMP");
		columnInfo2.setColumnName("SAL");
		selectStmtInfo.add(columnInfo2);
		
		ColumnInfo columnInfo3 = new ColumnInfo();
		columnInfo3.setTableName("DEPT");
		columnInfo3.setColumnName("DNAME");
		selectStmtInfo.add(columnInfo3);
		
		targetQueryInfo.setSelectStmtInfo(selectStmtInfo);
		
		//
		List<TableViewType> fromStmtInfo = new ArrayList<TableViewType>();
		
		TableInfo tableInfo1 = new TableInfo();
		tableInfo1.setTableName("EMP");
		fromStmtInfo.add(tableInfo1);
		
		TableInfo tableInfo2 = new TableInfo();
		tableInfo2.setTableName("DEPT");
		fromStmtInfo.add(tableInfo2);
		
		targetQueryInfo.setFromStmtInfo(fromStmtInfo);
		
		//
		WhereInfo whereStmtInfo = new WhereInfo();
		whereStmtInfo.setRelationOp("OR");
		List<WhereType> conditionList = new ArrayList<WhereType>();

		//
		ConditionInfo conditionInfo = new ConditionInfo();
		conditionInfo.setComparisionOp("=");
		
		ColumnInfo columnInfo4 = new ColumnInfo();
		columnInfo4.setTableName("EMP");
		columnInfo4.setColumnName("DEPTNO");
		conditionInfo.setSourceValue(columnInfo4);
		
		ColumnInfo columnInfo5 = new ColumnInfo();
		columnInfo5.setTableName("DEPT");
		columnInfo5.setColumnName("DEPTNO");
		conditionInfo.setTargetValue(columnInfo5);
		
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
		WhereInfo subWhereInfo2 = new WhereInfo();
		subWhereInfo2.setRelationOp("AND");
		List<WhereType> subConditionList2 = new ArrayList<WhereType>();
		
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
		
		subConditionList2.add(conditionInfo4);
		
		//
		ConditionInfo conditionInfo5 = new ConditionInfo();
		conditionInfo5.setComparisionOp("=");
		
		ColumnInfo columnInfo5_1 = new ColumnInfo();
		columnInfo5_1.setTableName("EMP");
		columnInfo5_1.setColumnName("COLC");
		conditionInfo5.setSourceValue(columnInfo5_1);
		
		ConstInfo constInfo1 = new ConstInfo();
		constInfo1.setConstValue("2014");
		constInfo1.setTypeName(TYPE_NAME.INTEGER);
		conditionInfo5.setTargetValue(constInfo1);
		
		subConditionList2.add(conditionInfo5);
		
		subWhereInfo2.setValueList(subConditionList2);
		
		conditionList.add(subWhereInfo2);
		
		//
		ConditionInfo conditionInfo6 = new ConditionInfo();
		conditionInfo6.setComparisionOp("=");
		
		ColumnInfo columnInfo6_1 = new ColumnInfo();
		columnInfo6_1.setTableName("DEPT");
		columnInfo6_1.setColumnName("COLD");
		conditionInfo6.setSourceValue(columnInfo6_1);
		
		ConstInfo constInfo2 = new ConstInfo();
		constInfo2.setConstValue("'TEST'");
		constInfo2.setTypeName(TYPE_NAME.STRING);
		conditionInfo6.setTargetValue(constInfo2);
		
		conditionList.add(conditionInfo6);
		
		////
		whereStmtInfo.setValueList(conditionList);
		
		targetQueryInfo.setWhereStmtInfo(whereStmtInfo);
		
		////
		
		assertThat(mainQueryInfo, is(targetQueryInfo));
	}
}
