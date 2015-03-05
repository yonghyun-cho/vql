package query.parser.vo;

import java.util.ArrayList;
import java.util.List;

public class ColumnInfo extends PrimitiveType{
	private String columnName = "";
	private String tableName = "";
	
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String toString(){
		return "TABLE명 : [" + this.tableName + "] // COLUMN명 : ["+ this.columnName + "]";
	}
	
	public static ColumnInfo convertStringToInfo(String value) throws Exception{
		String trimmedValue = value.trim();
		String [] splitSelectStmt = trimmedValue.split("\\.");
		ColumnInfo columnInfo = new ColumnInfo();
		
		if(splitSelectStmt.length == 1){
			columnInfo.setColumnName(trimmedValue);
			
		}else if(splitSelectStmt.length == 2){ // TABLE명.COLUMN명 형식
			columnInfo.setTableName(splitSelectStmt[0].trim());
			columnInfo.setColumnName(splitSelectStmt[1].trim());
			
		}else{
			throw new Exception("SELECT STATEMENT ERROR");
		}
		
		return columnInfo;
	}
	
	public static boolean isColumnType(String value){
		List<String> regexList = new ArrayList<String>();
		regexList.add("^[a-zA-Z][a-zA-Z0-9]*\\.[a-zA-Z][a-zA-Z0-9]*$");
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
	
	@Override
	public boolean equals(Object obj) {
		boolean result = super.equals(obj);
		
		if(super.equals(obj) == false){
			if(obj instanceof ColumnInfo){
				ColumnInfo targetInfo = (ColumnInfo)obj;
				
				if(targetInfo.getColumnName().equals(this.columnName)
						&& targetInfo.getTableName().equals(this.tableName)){
					result = true;
				}
				
			} else {
				result = false;
			}
		}
		
		return result;
	}
}
