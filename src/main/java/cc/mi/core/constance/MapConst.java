package cc.mi.core.constance;

public final class MapConst {
	/**
	 * 障碍物
	 */
	public static final int BLOCK = 1;
	
	/**
	 * 空地
	 */
	public static final int EMPTY = 0;
	
	/**
	 * 分线最大数量
	 */
	public static final int LINE_NO_MAX = 32;
	
	/**
	 * 人物站立的小格子宽度
	 */
	public static final int SMALL_GRID_WIDTH = 20;
	
	/**
	 * 人物站立的小格子高度
	 */
	public static final int SMALL_GRID_HEIGHT = 20;
	
	/**
	 * AOI的大格子宽度
	 */
	public static final int SCREEN_WIDTH = 800;
	
	/**
	 * AOI的大格子高度
	 */
	public static final int SCREEN_HEIGHT = 600;
	
	/**
	 * 宽度方向上的感兴趣格子距离
	 */
	public static final int INTEREST_AREA_GRID_LENGTH_FOR_WIDTH = SCREEN_WIDTH / SMALL_GRID_WIDTH;
	
	/**
	 * 高度方向上的感兴趣格子距离
	 */
	public static final int INTEREST_AREA_GRID_LENGTH_FOR_HEIGHT = SCREEN_HEIGHT / SMALL_GRID_HEIGHT;
}
