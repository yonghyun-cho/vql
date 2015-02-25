package query.parser.vo;

import java.util.ArrayList;
import java.util.List;

public class TableInfo extends QueryComponentType {
	
	private String tableName;
	
	private String alias;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public String toString(){
		return "TABLE명 : [" + this.tableName + "] // Alias : ["+ this.alias + "]";
	}
	
	public static ColumnInfo convertStringToInfo(String value) throws Exception{
		// TODO
//		String trimmedValue = value.trim();
//		String [] splitSelectStmt = trimmedValue.split("\\.");
//		ColumnInfo columnInfo = new ColumnInfo();
//		
//		if(splitSelectStmt.length == 1){
//			columnInfo.setColumnName(trimmedValue);
//			
//		}else if(splitSelectStmt.length == 2){ // TABLE명.COLUMN명 형식
//			columnInfo.setTableName(splitSelectStmt[0].trim());
//			columnInfo.setColumnName(splitSelectStmt[1].trim());
//			
//		}else{
//			throw new Exception("SELECT STATEMENT ERROR");
//		}
//		
//		return columnInfo;
		return null;
	}
	
	public static boolean isTableType(String value){
		// TODO
		
		List<String> regexList = new ArrayList<String>();
		// TODO table명 "alias" 인 경우 처리
		regexList.add("^[a-zA-Z][a-zA-Z0-9]* [a-zA-Z][a-zA-Z0-9]*$");
		regexList.add("^[a-zA-Z][a-zA-Z0-9]*$");
		
		boolean result = false;
		
		if(value != null){
			for(int i = 0; i < regexList.size(); i++){
				result = value.matches(regexList.get(i));
				
				if(result == true){
					break;
				}
			}
		}
		
		return result;
	}
}
