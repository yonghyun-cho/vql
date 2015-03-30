package vql.test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import query.parser.BracketDistributor;
import query.parser.SubQuerySplitter;

public class SubQuerySplitterTest {

	SubQuerySplitter subQuerySplitter = new SubQuerySplitter();
	
	@Test
	public void test1() {
		String testString = "SELECT EMP.NAME FROM(SELECT DEPT.DNAME FROM DEPT)WHERE (EMP.NAME = 'YH')";
		
		subQuerySplitter.splitSubQuery(testString);
		
		String mainQuery = subQuerySplitter.getMainQuery();
		Map<String, String> subQueryStringMap = subQuerySplitter.getSubQueryStringMap();
		
		String targetMainQuery = "SELECT EMP.NAME FROM 1_SUBQUERY_TEMP WHERE (EMP.NAME = 'YH')";
		assertThat(mainQuery, is(targetMainQuery));
		
		List<String> subQueryStringArr = new ArrayList<String>(subQueryStringMap.values());
		List<String> targetSubQueryStringArr = new ArrayList<String>();
		targetSubQueryStringArr.add("SELECT DEPT.DNAME FROM DEPT");
		
		assertThat(subQueryStringArr, is(targetSubQueryStringArr));
	}

	@Test
	public void test2() {
		String testString = "SELECT SUM(EMP.SAL)FROM EMP,DEPT WHERE EMP.DEPTNO=DEPT.DEPTNO AND(EMP.COMM>50 OR EMP.SAL>1000)AND(EMP.SAL>800)";
		
		subQuerySplitter.splitSubQuery(testString);
		
		String mainQuery = subQuerySplitter.getMainQuery();
		
		String targetMainQuery = "SELECT SUM(EMP.SAL)FROM EMP,DEPT WHERE EMP.DEPTNO=DEPT.DEPTNO AND(EMP.COMM>50 OR EMP.SAL>1000)AND(EMP.SAL>800)";
		assertThat(mainQuery, is(targetMainQuery));
	}
	
	@Test
	public void test3() {
		String testString = "SELECT EMP.ENAME, EMP.SAL, DEPT.DNAME FROM EMP, (SELECT DEPT.DNAME, DEPT.DEPTNO, DEPT.LOC FROM DEPT WHERE DEPTNO.SIZE > 100) DEPT WHERE EMP.DEPTNO = DEPT.DEPTNO OR (EMP.LOC = DEPT.LOC AND EMP.COLA = DEPT.COLA) OR (EMP.COLB = DEPT.COLB AND (EMP.COLC = 'TEST' OR EMP.COLC = 'DOING'))";
		
		subQuerySplitter.splitSubQuery(testString);
		
		String mainQuery = subQuerySplitter.getMainQuery();
		Map<String, String> subQueryStringMap = subQuerySplitter.getSubQueryStringMap();
		
		String targetMainQuery = "SELECT EMP.ENAME, EMP.SAL, DEPT.DNAME FROM EMP,  1_SUBQUERY_TEMP  DEPT WHERE EMP.DEPTNO = DEPT.DEPTNO OR (EMP.LOC = DEPT.LOC AND EMP.COLA = DEPT.COLA) OR (EMP.COLB = DEPT.COLB AND (EMP.COLC = 'TEST' OR EMP.COLC = 'DOING'))";
		assertThat(mainQuery, is(targetMainQuery));
		
		List<String> subQueryStringArr = new ArrayList<String>(subQueryStringMap.values());
		List<String> targetSubQueryStringArr = new ArrayList<String>();
		targetSubQueryStringArr.add("SELECT DEPT.DNAME, DEPT.DEPTNO, DEPT.LOC FROM DEPT WHERE DEPTNO.SIZE > 100");
		
		assertThat(subQueryStringArr, is(targetSubQueryStringArr));
	}
	
	@Test
	public void test4() {
		String testString = "SELECT EMP.ENAME, SUM(EMP.SAL) FROM (SELECT DEPT.DNAME FROM DEPT WHERE DEPT.DEPTNO = (SELECT DEPT.DEPTNO FROM DEPT WHERE DEPTNO = 101)) WHERE (EMP.ENAME = DEPT.DNAME) AND (EMP.SAL > BOSS.SAL OR EMP.AGE <= BOSS.AGE)";
		
		subQuerySplitter.splitSubQuery(testString);
		
		String mainQuery = subQuerySplitter.getMainQuery();
		Map<String, String> subQueryStringMap = subQuerySplitter.getSubQueryStringMap();
		
		String targetMainQuery = "SELECT EMP.ENAME, SUM(EMP.SAL) FROM  2_SUBQUERY_TEMP  WHERE (EMP.ENAME = DEPT.DNAME) AND (EMP.SAL > BOSS.SAL OR EMP.AGE <= BOSS.AGE)";
		assertThat(mainQuery, is(targetMainQuery));
		
		List<String> subQueryStringArr = new ArrayList<String>(subQueryStringMap.values());
		List<String> targetSubQueryStringArr = new ArrayList<String>();
		targetSubQueryStringArr.add("SELECT DEPT.DNAME FROM DEPT WHERE DEPT.DEPTNO =  1_SUBQUERY_TEMP");
		targetSubQueryStringArr.add("SELECT DEPT.DEPTNO FROM DEPT WHERE DEPTNO = 101");
		
		assertThat(subQueryStringArr, is(targetSubQueryStringArr));
	}
	
	@Test
	public void test5() {
		String testString = "SELECT EMP.ENAME, SUM(EMP.SAL) FROM (SELECT DEPT.DNAME FROM DEPT WHERE DEPT.DEPTNO = (SELECT DEPT.DEPTNO FROM DEPT WHERE DEPTNO = MOD(4,1))) WHERE (EMP.ENAME = DEPT.DNAME) AND (EMP.SAL > BOSS.SAL OR EMP.AGE <= BOSS.AGE)";
		
		subQuerySplitter.splitSubQuery(testString);
		
		String mainQuery = subQuerySplitter.getMainQuery();
		Map<String, String> subQueryStringMap = subQuerySplitter.getSubQueryStringMap();
		
		String targetMainQuery = "SELECT EMP.ENAME, SUM(EMP.SAL) FROM  2_SUBQUERY_TEMP  WHERE (EMP.ENAME = DEPT.DNAME) AND (EMP.SAL > BOSS.SAL OR EMP.AGE <= BOSS.AGE)";
		assertThat(mainQuery, is(targetMainQuery));
		
		List<String> subQueryStringArr = new ArrayList<String>(subQueryStringMap.values());
		List<String> targetSubQueryStringArr = new ArrayList<String>();
		targetSubQueryStringArr.add("SELECT DEPT.DNAME FROM DEPT WHERE DEPT.DEPTNO =  1_SUBQUERY_TEMP");
		targetSubQueryStringArr.add("SELECT DEPT.DEPTNO FROM DEPT WHERE DEPTNO = MOD(4,1)");
		
		assertThat(subQueryStringArr, is(targetSubQueryStringArr));
	}
}
