package cc.mi.core.xlsxData;

public class MapMonster {
	private int templateid;					//ID
	private int x;						//X轴坐标
	private int y;						//Y轴坐标
	private int count;						//数量
	private int respawnTime;				//重生时间
	private int spawnType;					//0死亡后刷新，1定时刷新
	private int moveType;					//移动类别
	private int flag;                       //npc标识
	private float toward;					//npc朝向
	
	public MapMonster() {}

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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getRespawnTime() {
		return respawnTime;
	}

	public void setRespawnTime(int respawnTime) {
		this.respawnTime = respawnTime;
	}

	public int getSpawnType() {
		return spawnType;
	}

	public void setSpawnType(int spawnType) {
		this.spawnType = spawnType;
	}

	public int getMoveType() {
		return moveType;
	}

	public void setMoveType(int moveType) {
		this.moveType = moveType;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public float getToward() {
		return toward;
	}

	public void setToward(float toward) {
		this.toward = toward;
	}
}
