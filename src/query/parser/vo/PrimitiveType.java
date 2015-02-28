package query.parser.vo;


public class PrimitiveType extends QueryComponentType{
	
	public static boolean isPrimitiveType(String value){
		return ColumnInfo.isColumnType(value) || ConstInfo.isConstType(value) || FunctionInfo.isFunctionType(value);
	}
	
	// String���� PrimitiveType�� �ϳ��� ��ȯ
	public static PrimitiveType convertStringToType(String value) throws Exception{
		PrimitiveType primitiveType = null;
		
		if(ColumnInfo.isColumnType(value)){
			primitiveType = ColumnInfo.convertStringToInfo(value);
			
		}else if(ConstInfo.isConstType(value)){
			primitiveType = ConstInfo.convertStringToInfo(value);
			
		}else if(FunctionInfo.isFunctionType(value)){
			primitiveType = FunctionInfo.convertStringToInfo(value);
			
		}else{
			throw new Exception("�߸��� PrimitiveType ����");
		}
		
		return primitiveType;
	}
}
