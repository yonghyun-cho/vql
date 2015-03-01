package query.parser;

import java.util.Map;

import query.parser.vo.QueryInfo;
import query.parser.vo.VisualQueryInfo;

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
		VisualQueryInfo visualQueryInfo = new VisualQueryInfo();
		
		// 파일 입력 로직 변경 // 2015.02.12. 조용현
		QueryParser qp = new QueryParser();
		qp.readQueryTextFile("C:\\testQuery.txt");
		// C:\\Users\\RHYH\\Documents\\testQuery.txt
		
		qp.parsingQueryToVisualQueryInfo();
		
		// Main Query
		QueryInfo mainQueryInfo = qp.getMainQueryInfo();
		visualQueryInfo.setMainQueryInfo(mainQueryInfo);
		
		// Sub Query
		Map<String, QueryInfo> subQueryInfoList = qp.getSubQueryInfoList();
		visualQueryInfo.setSubQueryMap(subQueryInfoList);
		
		// TODO
		// toString 정비 및 is...Info(Type) 함수에서 regex의 List 검증하는것
		// 공통 상위 클래스 함수로 뺄 것.
		mainQueryInfo.printQueryStructure();
		//////////////////////////////////////////
		
		for(String subQueryId: subQueryInfoList.keySet()){
			System.out.println("<<" + subQueryId + ">>");
			subQueryInfoList.get(subQueryId).printQueryStructure();
		}
		
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
