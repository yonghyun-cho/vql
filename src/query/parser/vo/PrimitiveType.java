package query.parser.vo;


public class PrimitiveType extends QueryComponentType{
	
	public static boolean isPrimitiveType(String value){
		return ColumnInfo.isColumnType(value) || ConstInfo.isConstType(value) || FunctionInfo.isFunctionType(value);
	}
	
	// String값을 PrimitiveType중 하나로 변환
	public static PrimitiveType convertStringToInfo(String value) throws Exception{
		PrimitiveType primitiveType = null;
		
		return primitiveType;
	}
}
