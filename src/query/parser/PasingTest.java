package query.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
		
		// ���� �Է� ���� ���� // 2015.02.12. ������
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
	
	// TODO SELECT, FROM, WHERE �����ϴ� ����
	// TODO �ƹ�����.. "" �� () ó�� ��ü�ߴٰ� �ٽ� �־�� �� �� ����....
	public static void statementTest(){
		
		//////// TOBE [[[ FALSE ]]] !!!!
		List<String> testSelectList_FALSE = new ArrayList<String>();
		testSelectList_FALSE.add("\"SELECT TAB1");
		testSelectList_FALSE.add("TOSELECT TAB1");
		testSelectList_FALSE.add("TOSELECT SELECTNOT");
		testSelectList_FALSE.add("\"THIS IS SELECT TAB1");
		testSelectList_FALSE.add("\"�׽�Ʈ��, SELECT TAB2,");
		testSelectList_FALSE.add("\"SELECT TAB1");
		
		//////// TOBE [[[ TRUE ]]] !!!!		
		List<String> testSelectList_TRUE = new ArrayList<String>();
		testSelectList_TRUE.add("SELECT TAB1");
		testSelectList_TRUE.add(" SELECT TAB2,");
		testSelectList_TRUE.add("(SELECT TAB1,");
		testSelectList_TRUE.add("(  SELECT TAB2,");
		testSelectList_TRUE.add("\"�׽�Ʈ��\", SELECT TAB2,");
		testSelectList_TRUE.add("\"�׽�Ʈ��\",(SELECT TAB2,");
		
		// ���ڰ� �ְ�. Object�̸� ���� ����, "�� ���� �ʰų� �Ǵ� �ƹ� ���ڰ� ���� "SELECT "�� ���
//		String regex = "(.*[^a-zA-Z0-9_$#\"]|)SELECT .*";

		// ���ڰ� �ְ�. (, ���� �̰ų� �Ǵ� �ƹ� ���ڰ� ���� "SELECT "�� ��� 
		String regex = "(.*(\\(|[\\s]+)|)SELECT .*";
		
		for(String string: testSelectList_FALSE){
			if(string.matches(regex)){
				System.out.println("[[FALSE]]�����ϴµ� TRUE��!!");
				System.out.println(string);
				System.out.println("---------------");
			}
		}
		
		for(String string: testSelectList_TRUE){
			if(string.matches(regex) == false){
				System.out.println("[[TRUE]]���� �ϴµ� FALSE��!!");
				System.out.println(string);
				System.out.println("---------------");
			}
		}
	}
	
}
