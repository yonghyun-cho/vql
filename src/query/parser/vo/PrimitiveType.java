package query.parser.vo;


public class PrimitiveType extends QueryComponentType{
	
	public static boolean isPrimitiveType(String value){
		return ColumnInfo.isColumnType(value) || ConstInfo.isConstType(value) || FunctionInfo.isFunctionType(value);
	}
	
	// String���� PrimitiveType�� �ϳ��� ��ȯ
	public static PrimitiveType convertStringToInfo(String value) throws Exception{
		PrimitiveType primitiveType = null;
		
		return primitiveType;
	}
}
