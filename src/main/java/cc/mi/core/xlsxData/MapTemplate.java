package cc.mi.core.xlsxData;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cc.mi.core.callback.InvokeCallback;
import cc.mi.core.utils.Floyd;
import cc.mi.core.utils.MIMath;
import cc.mi.core.utils.Path;
import cc.mi.core.utils.Point2D;

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
	
	protected MapMainLoad mainLoad;
	
	public MapTemplate() {
		Arrays.asList();
		this.raises = new LinkedList<>();
		this.teleports = new LinkedList<>();
		this.gameobjects = new LinkedList<>();
		this.monsters = new HashMap<>();
		this.mainLoad = new MapMainLoad();
	}
	
	public boolean isInstance() {
		// type 通过配表获得
//		return type == MapTypeConst.MAP_TYPE_INSTANCE;
		return false;
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
	
	public void addMainNode(int id, int x, int y) {
		this.mainLoad.addMainNode(id, x, y);
	}
	
	public void linkMainNode(int id1, int id2) {
		this.mainLoad.linkMainNode(id1, id2);
	}
	
	public void initMainNodeDist(int n) {
		this.mainLoad.initDist(n);
	}
	
	private void makeLink(JointNode<Float, Integer> node, float[][] dist, List<JointNode<Float, Integer>> infos) {
		Collections.sort(infos, new Comparator<JointNode<Float, Integer>>() {
			@Override
			public int compare(JointNode<Float, Integer> o1, JointNode<Float, Integer> o2) {
				double a = MIMath.INSTANCE.getDistance(node.getX(), node.getY(), o1.getX(), o1.getY());
				double b = MIMath.INSTANCE.getDistance(node.getX(), node.getY(), o2.getX(), o2.getY());
				if (a < b)
					return -1;
				if (a > b)
					return 1;
				return 0;
			}
		});
		
		// 最多取10个点连接 看看能不能直接走过去, 最好地图生成的时候尽量最优的
		// 如果地图太大的话需要修改
		for (int i = 0; i < 10 && i < infos.size(); ++ i) {
			JointNode<Float, Integer> info = infos.get(i);
			if (this.isCanRun(node.getX(), node.getY(), info.getX(), info.getY(), true)) {
				dist[node.getZ()][info.getZ()] = (float) MIMath.INSTANCE.getDistance(node.getX(), node.getY(), info.getX(), info.getY());
			}
		}
	}
	
	public Path getPath(float x1, float y1, float x2, float y2) {
		int n = this.mainLoad.getN();
		List<JointNode<Float, Integer>> infos = this.mainLoad.getNodeInfoList();
		float[][] dist = this.mainLoad.cloneDist();
		
		final JointNode<Float, Integer> a = new JointNode<Float, Integer>(x1, y1, n-2);
		final JointNode<Float, Integer> b = new JointNode<Float, Integer>(x2, y2, n-1);
		
		// 放到hash里面 这2个会被后面的覆盖掉, 有引用也不怕内存泄漏
		this.mainLoad.putNode(a, b);
		// 这里是核心代码 构造可走路径
		this.makeLink(a, dist, infos);
		this.makeLink(b, dist, infos);
		
		Floyd floyd = new Floyd(n);
		List<Integer> pathIndx = floyd.getPath(n-2, n-1, dist);
		Path path = new Path();
		for (int indx : pathIndx) {
			JointNode<Float, Integer> info = this.mainLoad.getNode(indx);
			path.addPath(new Point2D<Float>(info.getX(), info.getY()));
		}
		
		return path;
	}
	
	public void foreachTeleport(InvokeCallback<MapTeleport> callback) {
		for (MapTeleport value : this.teleports) {
			callback.invoke(value);
		}
	}
	
	public void foreachRaise(InvokeCallback<MapRaise> callback) {
		for (MapRaise value : this.raises) {
			callback.invoke(value);
		}
	}
	
	public void foreachMonster(InvokeCallback<MapMonster> callback) {
		for (MapMonster value : this.monsters.values()) {
			callback.invoke(value);
		}
	}
	
	public void foreachGameObject(InvokeCallback<MapGameobject> callback) {
		for (MapGameobject value : this.gameobjects) {
			callback.invoke(value);
		}
	}
}
