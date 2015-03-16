package query.parser.vo;

import java.util.ArrayList;
import java.util.List;

import query.parser.QueryParserCommFunc;

public class TableInfo extends TableViewType {
	
	public String getTableName() {
		return tableViewId;
	}

	public void setTableName(String tableName) {
		this.tableViewId = tableName;
	}

	public String getAlias() {
		return tableViewAlias;
	}

	public void setAlias(String alias) {
		this.tableViewAlias = alias;
	}
	
	public String toString(){
		return "TABLE명 : [" + this.tableViewId + "] // Alias : ["+ this.tableViewAlias + "]";
	}
	
	public static TableInfo convertStringToInfo(String value) throws Exception{
		TableInfo tableInfo = new TableInfo();
		
		value = value.trim();
		
		String[] splitedValue = value.split(" ");
		
		if(splitedValue.length == 2){
			tableInfo.setTableName(splitedValue[0].trim()); // 테이블 명
			tableInfo.setAlias(splitedValue[1].trim()); // alias
			
		}else if(splitedValue.length == 1){
			tableInfo.setTableName(splitedValue[0].trim()); // 테이블 명
			
		}else{
			throw new Exception("올바른 Table Type String이 아닙니다.");
		}
		
		return tableInfo;
	}
	
	public static boolean isTableType(String value) throws Exception{
		List<String> regexList = new ArrayList<String>();
		regexList.add("^[a-zA-Z][a-zA-Z0-9]* \".+\""); // ex) TABLE "테이블  table"
		regexList.add("^[a-zA-Z][a-zA-Z0-9]* [a-zA-Z][a-zA-Z0-9]*$"); // TABLE TAB
		regexList.add("^[a-zA-Z][a-zA-Z0-9]*$"); // TABLE
		
		return QueryParserCommFunc.isMatched(value, regexList);
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = super.equals(obj);
		
		if(super.equals(obj) == false){
			if(obj instanceof TableInfo){
				TableInfo targetInfo = (TableInfo)obj;
				
				if(targetInfo.getTableName().equals(this.tableViewId)
						&& targetInfo.getAlias().equals(this.tableViewAlias)){
					result = true;
				}
				
			} else {
				result = false;
			}
		}
		
		return result;
	}
}
