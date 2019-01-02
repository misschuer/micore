package cc.mi.core.constance;

/**
 * 定义p对象的下标
 * @author DZ-A-009
 *
 */
public class SceneElementEnumFields {

	public static final int ELEMENT_INT_FIELD_ENTRY			= 0;									//模板
	
	public static final int ELEMENT_INT_FIELD_MAP_ID		= ELEMENT_INT_FIELD_ENTRY + 1;			//地图id
	public static final int ELEMENT_INT_FIELD_INSTANCE_ID	= ELEMENT_INT_FIELD_MAP_ID + 1;			//地图实例id
	public static final int ELEMENT_INT_FIELD_LINE_NO		= ELEMENT_INT_FIELD_INSTANCE_ID + 1;	//地图分线号
	public static final int ELEMENT_INT_FIELD_POS_X			= ELEMENT_INT_FIELD_LINE_NO + 1;		//X坐标
	public static final int ELEMENT_INT_FIELD_POS_Y			= ELEMENT_INT_FIELD_POS_X + 1;			//Y坐标
	
	public static final int ELEMENT_INT_FIELD_ELEMENT_INFO	= ELEMENT_INT_FIELD_POS_Y + 1;			//元素基础属性 (0:元素类型,1:移动类型,2:反应类型)
	public static final int ELEMENT_INT_FIELD_ELEMENT_INFO2	= ELEMENT_INT_FIELD_ELEMENT_INFO + 1;	//元素基础属性2
	//长度
	public static final int ELEMENT_INT_FIELDS_SIZE			= ELEMENT_INT_FIELD_ELEMENT_INFO2 + 1;	//int类型数据的大小
	
	/*****************************************************************************************************************/
	//长度
	public static final int ELEMENT_STR_FIELDS_SIZE			= BinlogStrFieldIndice.BINLOG_STRING_FIELD_OWNER + 1;	//str类型数据的大小
}
