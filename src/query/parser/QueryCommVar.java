package query.parser;


public class QueryCommVar {
	// QUERY STATEMENT
	public final static String SELECT = "SELECT";
	public final static String FROM = "FROM";
	public final static String WHERE = "WHERE";
	
	public final static String[] STATEMENT_LIST = new String[]{SELECT, FROM, WHERE};
	
	// COMPARISION OPERATION
	// TODO ���� �����ڴ�..?
	public final static String EQUAL = "=";
	public final static String LESS_THAN = "<";
	public final static String LESS_THAN_EQUAL = "<=";
	public final static String GREATER_THEN = ">";
	public final static String GREATER_THEN_EQUAL = ">=";
	
	public final static String[] COMPARISION_OP_LIST = new String[]{EQUAL, LESS_THAN, LESS_THAN_EQUAL, GREATER_THEN, GREATER_THEN_EQUAL};

	public final static String AND = "AND";
	public final static String OR = "OR";
	
	public final static String SUM = "SUM";
	public final static String COUNT = "COUNT";
	public final static String AVG = "AVG";
	public final static String MAX = "MAX";
	public final static String MIN = "MIN";
	
	public final static String[] AGG_FUNCTION_LIST = new String[]{SUM, COUNT, AVG, MAX, MIN};
}