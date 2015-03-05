package query.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryParserCommFunc {
	public static String trimAllWhiteSpace(String string){
		String convertString = string.replaceAll("\\r|\\n", " ");
		convertString = convertString.replaceAll("\\s+", " ");
		convertString = convertString.trim();
		
		return convertString;
	}
	
	public static String replaceString(String originalString, String replaceString, int fromIndex, int endIndex){
		String newString = originalString.substring(0, fromIndex) + replaceString + originalString.substring(endIndex + 1);
		
		return newString;
	}
	
	public static int lastIndexOf(String string, String ch, int fromIndex, int endIndex){
		if(fromIndex < 0){
			fromIndex = 0;
		}
		if(endIndex > string.length()){
			endIndex = string.length();
		}
		
		String subString = string.substring(fromIndex, endIndex);
		
		int lastIndex = subString.lastIndexOf(ch);
		
		if(lastIndex >= 0){ // 해당 ch의 index가 존재하는 경우에만.
			lastIndex = fromIndex + lastIndex;
		}
		
		return lastIndex;
	}
	
	public static int lastIndexOfRegex(String string, String regex, int fromIndex, int endIndex){
		if(fromIndex < 0){
			fromIndex = 0;
		}
		if(endIndex > string.length()){
			endIndex = string.length();
		}
		
		int lastIndex = -1;
		
	    Pattern pattern = Pattern.compile(regex);
	    
	    String subString = string.substring(fromIndex, endIndex);
	    Matcher matcher = pattern.matcher(subString);
	    if(matcher.find()){

	    	lastIndex = matcher.start();
	    }
	    
	    if(lastIndex >= 0){ // 해당 ch의 index가 존재하는 경우에만.
			lastIndex = fromIndex + lastIndex;
		}
	    
	    return lastIndex;
	}
}
