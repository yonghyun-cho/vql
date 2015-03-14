package query.parser;


public class QueryCommVar {
	// QUERY STATEMENT
	public enum STATEMENT{
		SELECT ("SELECT"), FROM ("FROM"), WHERE("WHERE")
		, GROUP_BY("GROUP BY"), HAVING("HAVING");
		
		private String value;
		
		private STATEMENT(String value){
			this.value = value; 
		}
		
		public String getValue(){
			return value;
		}
	}
	
	// COMPARISION OPERATION
	// TODO 부정 연산자는..?
	public enum CMPR_OP{
		EQUAL ("=")
		, LESS_THAN ("<"), LESS_THAN_EQUAL("<=")
		, GREATER_THEN(">"), GREATER_THEN_EQUAL(">=");
		
		private String value;
		
		private CMPR_OP(String value){
			this.value = value; 
		}
		
		public String getValue(){
			return value;
		}
	}
	
	// LOGICAL OPERATION
	public enum LGCL_OP{
		AND ("AND"), OR ("OR");
		
		private String value;
		
		private LGCL_OP(String value){
			this.value = value; 
		}
		
		public String getValue(){
			return value;
		}
	}
	
	public final static String SUM = "SUM";
	public final static String COUNT = "COUNT";
	public final static String AVG = "AVG";
	public final static String MAX = "MAX";
	public final static String MIN = "MIN";
	
	public final static String[] AGG_FUNCTION_LIST = new String[]{SUM, COUNT, AVG, MAX, MIN};
}

