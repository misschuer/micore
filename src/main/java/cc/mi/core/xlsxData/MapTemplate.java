package cc.mi.core.xlsxData;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cc.mi.core.constance.MapTypeConst;

public final class MapTemplate {
	// 基础信息
	protected MapBaseinfo baseInfo;
	// 障碍物信息
	protected int[] obstacleMask;
	// 复活点
	protected List<MapRaise> raises;
	// 传送点
	protected List<MapTeleport> teleports;
	//刷怪点
	protected Map<Integer, MapMonster> monsters;
	//刷游戏对象点
	protected List<MapGameobject> gameobjects;
	
	public MapTemplate() {
		this.raises = new LinkedList<>();
		this.teleports = new LinkedList<>();
		this.gameobjects = new LinkedList<>();
		this.monsters = new HashMap<>();
	}
	
	public boolean isInstance() {
		int type = this.getBaseInfo().getType();
		return type == MapTypeConst.MAP_TYPE_INSTANCE;
	}
	
	public boolean isValidPosition(int x, int y) {
		if (x < 0 || y < 0 || x >= this.baseInfo.getWidth() || y >= this.baseInfo.getHeight()) {
			return false;
		}
		return !this.isObstacle(x, y);
	}
	
	public boolean isValidPosition(int index) {
		return !this.isObstacle(index);
	}
	
	// 传入给定路径，判断是否可以通过
	public boolean isCanRun(float fromX, float fromY, List<Float> path) {
		if (path == null || path.isEmpty()) {
			return this.isValidPosition((int)fromX, (int)fromY);
		}
		
//		//第一点,无视障碍 
//		if (!this.isValidPosition((int)fromX, (int)fromY)) {
//			return true;
//		}
		
	    if(!this.isCanRun(fromX, fromY, path.get(0), path.get(1), true)) {
	        return false;
	    }
		
		//逐点判断，不能通过立刻跳出
		for (int i = 0; i < path.size(); i += 2) {
			
			if (!this.isCanRun(fromX, fromY, path.get(i), path.get(i+1), false)) {
				return false;
			}
			fromX = path.get( i );
			fromY = path.get(i+1);
		}  
	    return true;
	}

	//传入起点和终点，判断是可以通过
	public boolean isCanRun(float fromx, float fromy, float tox, float toy, 
			boolean isStart) {
		
		int x0 = (int)fromx, y0 = (int)fromy, x1 = (int)tox, y1 = (int)toy;
		int dx =  Math.abs(x1 - x0), sx = x0 < x1 ? 1 : -1;
		int dy = -Math.abs(y1 - y0), sy = y0 < y1 ? 1 : -1;
		int err = dx + dy, e2; /* error value e_xy */	

		for(;;) {
			/* loop */
			if(!this.isValidPosition(x0, y0) && !isStart) {
				return false;
			}
			isStart = false;
			if(x0 == x1 && y0 == y1) break;

			e2 = 2 * err;
			if(e2 >= dy) {
				err += dy;    /* e_xy+e_x > 0 */
				x0 += sx;
			}

			if(e2 <= dx) {
				err += dx;    /* e_xy+e_y < 0 */
				y0 += sy;
			}
		}

		return true;
	}

	public boolean isCanRun(float fromX, float fromY, float toX, float toY, 
			int lastX, int lastY, boolean isStart) {
		
		int nx = 0, ny = 0;
		return this.isCanRun(fromX, fromY, toX, toY, lastX, lastY, nx, ny, isStart);
	}

	public boolean isCanRun(float fromx, float fromy, float tox, float toy, 
			int lastX, int lastY, int cannotx, int cannoty, boolean isStart) {
		
//		int x0 = (int)fromx, y0 = (int)fromy, x1 = (int)tox, y1 = (int)toy;
//		int dx =  Math.abs(x1 - x0), sx = x0 < x1 ? 1 : -1;
//		int dy = -Math.abs(y1 - y0), sy = y0 < y1 ? 1 : -1;
//		int err = dx + dy, e2; /* error value e_xy */
//		lastX = 0;
//		lastY = 0;
//
//		for(;;) {
//			/* loop */
//			if(!this.isValidPosition(x0, y0) && !isStart) {
//				
//				cannotx = x0;
//				cannoty = y0;
//				return false;
//			}
//
//			lastX = x0;
//			lastY = y0;
//			isStart = false;
//
//			if(x0 == x1 && y0 == y1) break;
//
//			e2 = 2 * err;
//
//			if(e2 >= dy) {
//				err += dy;    /* e_xy+e_x > 0 */
//				x0 += sx;
//			}
//
//			if(e2 <= dx) {
//				err += dx;    /* e_xy+e_y < 0 */
//				y0 += sy;
//			}
//		}
//
//		return true;
		return false;
	}
	
	public boolean isInMap(int x, int y) {
		if (x < 0 || y < 0 || x >= this.baseInfo.getWidth() || y >= this.baseInfo.getHeight()) {
			return false;
		}
		return true;
	}
	
	public boolean isObstacle(int x, int y) {
		return this.isObstacle(x + y * this.baseInfo.getWidth());
	}

	private boolean isObstacle(int index) {
		return (this.obstacleMask[index >> 5] & (1 << (index & 31))) > 0;
	}

	public MapBaseinfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(MapBaseinfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public void setObstacleMask(int[] obstacleMask) {
		this.obstacleMask = obstacleMask;
	}

	public void addRaise(MapRaise raise) {
		this.raises.add(raise);
	}
	
	public void addTeleport(MapTeleport teleport) {
		this.teleports.add(teleport);
	}
	
	public void addGameObject(MapGameobject obj) {
		this.gameobjects.add(obj);
	}
	
	public void addMonster(int pos, MapMonster monster) {
		this.monsters.put(pos, monster);
	}
}
