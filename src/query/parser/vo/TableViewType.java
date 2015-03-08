package query.parser.vo;

public class TableViewType extends QueryComponentType {
	// ���̺�(��) ID(�̸�)
	protected String tableViewId = "";
	
	// ���̺�(��) alias
	protected String tableViewAlias = "";
	
	public static boolean isTableViewType(String value) throws Exception{
		return TableInfo.isTableType(value) || SubQueryInfo.isSubQueryType(value);
	}
	
	public static TableViewType convertStringToType(String value) throws Exception{
		TableViewType tableViewType = null;
		
		if(SubQueryInfo.isSubQueryType(value)){
			tableViewType = SubQueryInfo.convertStringToInfo(value);
			
		}else if(TableInfo.isTableType(value)){
			tableViewType = TableInfo.convertStringToInfo(value);
			
		}else{
			throw new Exception("�߸��� TableViewType �Դϴ�.");
		}
		
		return tableViewType;
	}
}
