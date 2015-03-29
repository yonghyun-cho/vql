package query.parser;

import java.io.BufferedReader;
import java.io.FileReader;

public class TempQueryInput {
	// filePath로 읽기
	public static String readQueryTextFile(String filePath) throws Exception{
		String originalQuery = "";
		
		BufferedReader br = null;
		
	    try {
	    	br = new BufferedReader(new FileReader(filePath));
	    	
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            line = br.readLine();
	        }
	        
	        originalQuery = QueryParserCommFunc.trimWhiteSpace(sb.toString());
	        
	    } finally {
			br.close();
			
			if(originalQuery == null){
				throw new Exception("Empty Query Exception");
			}
	    }
	    
	    return originalQuery;
	}
}
