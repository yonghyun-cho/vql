package query.parser.vo;

abstract public class QueryComponentType {

	/**
	 * 해당 value가 QueryComponentType으로 변환될 수 있는지 확인
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static boolean isQueryComponentType(String value) throws Exception{
		return PrimitiveType.isPrimitiveType(value) || TableViewType.isTableViewType(value) || FunctionInfo.isFunctionId(value);
	}
	
	@Override
	// 임시로 각 Info에 hashCode를 설정하기엔 아직 변경사항이 많아서 무조건 0 을 반환하도록 수정함.
	// TODO 추후 성능 문제가 존재하면 제대로 구현할 것. (근데 어차피 equals 관련한건 Junit test에서만 쓰일거라..)
	public int hashCode() {
		return 0;
	}
}
