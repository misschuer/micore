package cc.mi.core.xlsxData;

public class MapBaseinfo {
	private int		mapId;				/*地图ID*/
	private String	name;				/*地图名称*/
	private int		gridWidth;			/*格子宽*/
	private int		gridHeight;			/*格子高*/
	private int		width;				/*地图宽方向的格子数*/
	private int		height;				/*地图高方向的格子数*/
	private int		parentId;           /*父级地图ID*/
//	private int     type;				/*地图类型 from enum MapInstanceType*/ 配表
	
	public MapBaseinfo() {}

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGridWidth() {
		return gridWidth;
	}

	public void setGridWidth(int gridWidth) {
		this.gridWidth = gridWidth;
	}

	public int getGridHeight() {
		return gridHeight;
	}

	public void setGridHeight(int gridHeight) {
		this.gridHeight = gridHeight;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
}
