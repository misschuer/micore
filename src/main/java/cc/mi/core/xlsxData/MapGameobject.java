package cc.mi.core.xlsxData;

public class MapGameobject {
	private int templateid;					//ID
	private int x;							//X轴坐标
	private int y;							//Y轴坐标
	private int respawnTime;				//重生时间
	private float  toward;					//朝向
	
	public MapGameobject() {}

	public int getTemplateid() {
		return templateid;
	}

	public void setTemplateid(int templateid) {
		this.templateid = templateid;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getRespawnTime() {
		return respawnTime;
	}

	public void setRespawnTime(int respawnTime) {
		this.respawnTime = respawnTime;
	}

	public float getToward() {
		return toward;
	}

	public void setToward(float toward) {
		this.toward = toward;
	}
}
