package query.parser;

import query.parser.vo.ColumnInfo;
import query.parser.vo.ConstInfo;
import query.parser.vo.QueryInfo;
import query.parser.vo.SubQueryInfo;

/*
 * 
SELECT EMP.ENAME, EMP.SAL, DEPT.DNAME, (SELECT EMP.SAL FROM EMP WHERE EMPNO = 7839) FROM EMP, (SELECT DEPT.DNAME, DEPT.DEPTNO, DEPT.LOC FROM DEPT WHERE DEPTNO.SIZE > 100) DEPT WHERE EMP.DEPTNO = DEPT.DEPTNO AND DEPT.LOC = 'CHICAGO'  
===========================
<< SUBQUERY >> (SELECT EMP.SAL FROM EMP WHERE EMPNO = 7839)
----------------------------
SELECT EMP.ENAME, EMP.SAL, DEPT.DNAME, 1_SubQuery_TEMP FROM EMP, (SELECT DEPT.DNAME, DEPT.DEPTNO, DEPT.LOC FROM DEPT WHERE DEPTNO.SIZE > 100) DEPT WHERE EMP.DEPTNO = DEPT.DEPTNO AND DEPT.LOC = 'CHICAGO'  

<< SUBQUERY >> (SELECT DEPT.DNAME, DEPT.DEPTNO, DEPT.LOC FROM DEPT WHERE DEPTNO.SIZE > 100)
----------------------------
SELECT EMP.ENAME, EMP.SAL, DEPT.DNAME, 1_SubQuery_TEMP FROM EMP, 2_SubQuery_TEMP DEPT WHERE EMP.DEPTNO = DEPT.DEPTNO AND DEPT.LOC = 'CHICAGO'  
===========================

 */

public class PasingTest {

	public static void main(String[] args) throws Exception {
		// 파일 입력 로직 변경 // 2015.02.12. 조용현
		QueryParser qp = new QueryParser();
		qp.readQueryTextFile("C:\\testQuery.txt");
		
		qp.parsingQueryToVisualQueryInfo();
		
		qp.getQueryInfo();
		
		System.out.println(qp.toString());
		//////////////////////////////////////////
		
//		String simpleQuery = qp.getInputQuery().replace("\r\n", " ").replace("\n", " "); 
//		simpleQuery = simpleQuery.toUpperCase();
//		
//		System.out.println(simpleQuery);
//		
//		System.out.println("===========================");
//		SubQueryParser subQueryParser = new SubQueryParser();
//		subQueryParser.replaceAllBracket(simpleQuery);
//		System.out.println("===========================");
	}
	
}
