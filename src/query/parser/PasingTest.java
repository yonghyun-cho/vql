package query.parser;

import query.parser.vo.ColumnInfo;
import query.parser.vo.ConstInfo;
import query.parser.vo.QueryInfo;
import query.parser.vo.SubQueryInfo;

public class PasingTest {

	public static void main(String[] args) throws Exception {
		// 파일 입력 로직 변경 // 2015.02.12. 조용현
		QueryParser qp = new QueryParser();
		qp.readQueryTextFile("C:\\Users\\RHYH\\Documents\\testQuery.txt");
		
//		qp.parsingQuery();
//		
//		qp.getQueryInfo();
//		
//		System.out.println(qp.toString());
		//////////////////////////////////////////
		
		String simpleQuery = qp.getInputQuery().replace("\r\n", " ").replace("\n", " "); 
		simpleQuery = simpleQuery.toUpperCase();
		
		System.out.println(simpleQuery);
		
		System.out.println("===========================");
		SubQueryParser subQueryParser = new SubQueryParser();
		subQueryParser.replaceAllBracket(simpleQuery);
		System.out.println("===========================");
	}
	
}
