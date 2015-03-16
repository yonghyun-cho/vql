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
		
		public static STATEMENT getEnum(String value) throws Exception {
			STATEMENT statement = null;
			
			for(STATEMENT v : values()) {
	            if(v.getValue().equalsIgnoreCase(value)){ 
	            	statement = v;
	            	break;
	            }
	        }
			
			if(statement == null){
				throw new Exception("올바르지 않은 Statement입니다.");
				
			}else{
				return statement;
			}
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
		
		public static CMPR_OP getEnum(String value) throws Exception {
			CMPR_OP cmprOp = null;
			
			for(CMPR_OP v : values()) {
	            if(v.getValue().equalsIgnoreCase(value)){ 
	            	cmprOp = v;
	            	break;
	            }
	        }
			
			if(cmprOp == null){
				throw new Exception("올바르지 않은 비교 연산자입니다.");
				
			}else{
				return cmprOp;
			}
	    }
	}
	
	// LOGICAL OPERATION
	public enum LGCL_OP {
		AND ("AND"), OR ("OR");
		
		private String value;
		
		private LGCL_OP(String value){
			this.value = value; 
		}
		
		public String getValue(){
			return value;
		}
		
		public static LGCL_OP getEnum(String value) throws Exception {
			LGCL_OP lgclOp = null;
			
			for(LGCL_OP v : values()) {
	            if(v.getValue().equalsIgnoreCase(value)){ 
	            	lgclOp = v;
	            	break;
	            }
	        }
			
			if(lgclOp == null){
				throw new Exception("올바르지 않은 비교 연산자입니다.");
				
			}else{
				return lgclOp;
			}
	    }
	}
	
	// FUNCTION
	public enum FUNCTION {
		SUM("SUM"), COUNT("COUNT"), AVG("AVG"), MAX("MAX"), MIN("MIN");
		
		private String value;
		
		private FUNCTION(String value){
			this.value = value; 
		}
		
		public String getValue(){
			return value;
		}
		
		public static FUNCTION getEnum(String value) throws Exception {
			FUNCTION function = null;
			
			for(FUNCTION v : values()) {
	            if(v.getValue().equalsIgnoreCase(value)){ 
	            	function = v;
	            	break;
	            }
	        }
			
			if(function == null){
				throw new Exception("올바르지 않은 합수명 입니다.");
				
			}else{
				return function;
			}
	    }
	}
}

