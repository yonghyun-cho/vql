package query.parser.vo;

import java.util.ArrayList;
import java.util.List;

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
		return "TABLE�� : [" + this.tableViewId + "] // Alias : ["+ this.tableViewAlias + "]";
	}
	
	public static TableInfo convertStringToInfo(String value) throws Exception{
		TableInfo tableInfo = new TableInfo();
		
		value = value.trim();
		
		String[] splitedValue = value.split(" ");
		
		if(splitedValue.length == 2){
			tableInfo.setTableName(splitedValue[0].trim()); // ���̺� ��
			tableInfo.setAlias(splitedValue[1].trim()); // alias
			
		}else if(splitedValue.length == 1){
			tableInfo.setTableName(splitedValue[0].trim()); // ���̺� ��
			
		}else{
			throw new Exception("�ùٸ� Table Type String�� �ƴմϴ�.");
		}
		
		return tableInfo;
	}
	
	public static boolean isTableType(String value){
		List<String> regexList = new ArrayList<String>();
		// TODO table�� "alias" �� ��� ó��
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
