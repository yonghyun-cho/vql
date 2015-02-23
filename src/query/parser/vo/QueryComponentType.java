package query.parser.vo;

public class QueryComponentType {
	public static boolean isQueryComponenetType(String value){
		return PrimitiveType.isPrimitiveType(value) || true; // TODO SubQueryInfo 의 is... 함수 생성하여 추가할 것.
	}
}
