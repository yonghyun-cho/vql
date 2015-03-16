package query.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import query.parser.QueryCommVar.CMPR_OP;
import query.parser.vo.QueryInfo;
import query.parser.vo.VisualQueryInfo;

/*
 * 
SELECT EMP.ENAME, EMP.SAL, DEPT.DNAME, (SELECT EMP.SAL FROM EMP WHERE EMPNO = 7839) FROM EMP, (SELECT DEPT.DNAME, DEPT.DEPTNO, DEPT.LOC FROM DEPT WHERE DEPTNO.SIZE > 100) DEPT WHERE EMP.DEPTNO = DEPT.DEPTNO AND DEPT.LOC = 'CHICAGO'  
===========================
SELECT SUM(EMP.SAL)FROM EMP,DEPT WHERE EMP.DEPTNO=DEPT.DEPTNO AND(EMP.COMM>50 OR EMP.SAL>1000)AND(EMP.SAL>800)
===========================
SELECT EMP.ENAME, EMP.SAL, DEPT.DNAME FROM EMP, (SELECT DEPT.DNAME, DEPT.DEPTNO, DEPT.LOC FROM DEPT WHERE DEPTNO.SIZE > 100) DEPT WHERE EMP.DEPTNO = DEPT.DEPTNO OR (EMP.LOC = DEPT.LOC AND EMP.COLA = DEPT.COLA) OR (EMP.COLB = DEPT.COLB AND (EMP.COLC = 'TEST' OR EMP.COLC = 'DOING'))
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
		
		visualQueryInfo.printVisualQueryInfo();
		
		///////////////////////////////////////////////////
		
//		statementTest();
		
		///////////////////////////////////////////////////
//		
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
	
	// SELECT, FROM, WHERE 구분하는 내용
	// TODO 아무래도.. "" 도 () 처럼 대체했다가 다시 넣어야 할 듯 싶음....
	// "" 이건 잘 안쓰이는 부분.. 일단 ""나 --같은 주석은 나중에 처리하도록.
	public static void statementTest(){
		
		//////// TOBE [[[ FALSE ]]] !!!!
		List<String> testSelectList_FALSE = new ArrayList<String>();
		testSelectList_FALSE.add("\"SELECT TAB1");
		testSelectList_FALSE.add("TOSELECT TAB1");
		testSelectList_FALSE.add("TOSELECT SELECTNOT");
		testSelectList_FALSE.add("\"THIS IS SELECT TAB1");
		testSelectList_FALSE.add("\"테스트임, SELECT TAB2,");
		testSelectList_FALSE.add("\"SELECT TAB1");
		
		//////// TOBE [[[ TRUE ]]] !!!!		
		List<String> testSelectList_TRUE = new ArrayList<String>();
		testSelectList_TRUE.add("SELECT TAB1");
		testSelectList_TRUE.add(" SELECT TAB2,");
		testSelectList_TRUE.add("(SELECT TAB1,");
		testSelectList_TRUE.add("(  SELECT TAB2,");
		testSelectList_TRUE.add("\"테스트임\", SELECT TAB2,");
		testSelectList_TRUE.add("\"테스트임\",(SELECT TAB2,");
		
		// 문자가 있고. Object이름 가능 문자, "가 오지 않거나 또는 아무 문자가 없고 "SELECT "인 경우
//		String regex = "(.*[^a-zA-Z0-9_$#\"]|)SELECT .*";

		// 문자가 있고. (, 공백 이거나 또는 아무 문자가 없고 "SELECT "인 경우 
		String regex = "(.*(\\(|[\\s]+)|)SELECT .*";
		
		for(String string: testSelectList_FALSE){
			if(string.matches(regex)){
				System.out.println("[[FALSE]]여야하는데 TRUE임!!");
				System.out.println(string);
				System.out.println("---------------");
			}
		}
		
		for(String string: testSelectList_TRUE){
			if(string.matches(regex) == false){
				System.out.println("[[TRUE]]여야 하는데 FALSE임!!");
				System.out.println(string);
				System.out.println("---------------");
			}
		}
	}
	
}
