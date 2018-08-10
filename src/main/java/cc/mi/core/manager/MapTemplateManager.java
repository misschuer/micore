package cc.mi.core.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.mi.core.log.CustomLogger;
import cc.mi.core.utils.FileUtils;
import cc.mi.core.xlsxData.MapBaseinfo;
import cc.mi.core.xlsxData.MapGameobject;
import cc.mi.core.xlsxData.MapMonster;
import cc.mi.core.xlsxData.MapRaise;
import cc.mi.core.xlsxData.MapTeleport;
import cc.mi.core.xlsxData.MapTemplate;

public enum MapTemplateManager {
	INSTANCE;
	static final CustomLogger logger = CustomLogger.getLogger(MapTemplateManager.class);
	
	private final Map<Integer, MapTemplate> mapTemplateHash= new HashMap<>();
	
	public MapTemplate getTemplate(int mapId) {
		return mapTemplateHash.get(mapId);
	}
	
	public boolean containsTemplate(int mapId) {
		return mapTemplateHash.containsKey(mapId);
	}
	
	public void loads(boolean baseInfoOnly) {
		URL url = MapTemplate.class.getResource("/maps");
		List<File> fileList = FileUtils.INSTANCE.listFilesInCurrentDirectory(url.getPath());
		try {
			for (File file : fileList) {
				MapTemplate mt = this.parse(file, baseInfoOnly);
				if (mt != null) {
					int mapId = mt.getBaseInfo().getMapId();
					this.mapTemplateHash.put(mapId, mt);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	private MapTemplate parse(File file, boolean baseInfoOnly) throws IOException {
		MapTemplate mt = new MapTemplate();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		
		//先载入基本信息
	    if (!this.loadBaseinfo(bufferedReader, mt)) {
	        return null;
	    }

		//如果只载入基本信息则直接返回
		if (baseInfoOnly) {
			return mt;
		}

	    //载入障碍信息
	    if (!this.loadObstacle(bufferedReader, mt)) {
	        return null;
	    }

	    //载入复活点信息
	    if (!this.loadRaise(bufferedReader, mt)) {
	        return null;
	    }
	    
	    //载入传送点信息
	    if (!this.loadTeleport(bufferedReader, mt)) {
	        return null;
	    }

	    //载入怪物信息
	    if (!this.loadCreatrue(bufferedReader, mt)) {
	        return null;
	    }

	    //载入游戏对象
	    if (!this.loadGameObject(bufferedReader, mt)) {
	        return null;
	    }

	    return mt;
	}
	
	
	//载入基本信息
	private boolean loadBaseinfo(BufferedReader bufferedReader, MapTemplate mt) throws IOException {
		String str = bufferedReader.readLine();
		// 已经读到文件末
		if (str == null) {
			return false;
		}
		
		logger.devLog("loadBaseinfo");
		MapBaseinfo baseInfo = new MapBaseinfo();
		String[] params = str.split(",");
//		1,上玄宗,2018-01-04,8460,9540,30,30,282,318,1,0
		try {
			baseInfo.setMapId(Integer.parseInt(params[ 0 ]));
			baseInfo.setName(params[ 1 ]);
			baseInfo.setDate(params[ 2 ]);
			baseInfo.setGridWidth(Integer.parseInt(params[ 3 ]));
			baseInfo.setGridHeight(Integer.parseInt(params[ 4 ]));
			baseInfo.setWidth(Integer.parseInt(params[ 5 ]));
			baseInfo.setHeight(Integer.parseInt(params[ 6 ]));
			baseInfo.setParentId(Integer.parseInt(params[ 7 ]));
			baseInfo.setType(Integer.parseInt(params[ 8 ]));
			mt.setBaseInfo(baseInfo);
		} catch (Throwable e) {
			return false;
		}

	    return true;
	}

	private boolean loadObstacle(BufferedReader bufferedReader, MapTemplate mt) throws IOException {
		String str = bufferedReader.readLine();
		// 已经读到文件末
		if (str == null) {
			return false;
		}
		
		logger.devLog("loadObstacle");
		String[] params = str.split(",");
		int[] obstacleMask = new int[params.length];
		try {
			for (int i = 0; i < params.length; ++ i) {
				obstacleMask[ i ] = Integer.parseInt(params[ i ]);
			}
			mt.setObstacleMask(obstacleMask);
		} catch (Throwable e) {
			return false;
		}
	    return true;
	}
	
	private boolean loadRaise(BufferedReader bufferedReader, MapTemplate mt) throws IOException {
		String str = bufferedReader.readLine();
		// 已经读到文件末
		if (str == null) {
			return false;
		}
		
		logger.devLog("loadRaise");
		if (str.isEmpty()) {
			return true;
		}
		String[] params = str.split(",");
		try {
			for (int i = 0; i < params.length; i += 3) {
				MapRaise mr = new MapRaise();
				mr.setX(Integer.parseInt(params[ i ]));
				mr.setY(Integer.parseInt(params[i+1]));
				mr.setRadius(Integer.parseInt(params[i+2]));
				mt.addRaise(mr);
			}
		} catch (Throwable e) {
			return false;
		}
	    return true;
	}

	private boolean loadTeleport(BufferedReader bufferedReader, MapTemplate mt) throws IOException {
		String str = bufferedReader.readLine();
		// 已经读到文件末
		if (str == null) {
			return false;
		}
		
		logger.devLog("loadTeleport");
		if (str.isEmpty()) {
			return true;
		}
		String[] params = str.split(",");
		try {
			for (int i = 0; i < params.length; i += 7) {
				MapTeleport tele = new MapTeleport();
				tele.setX(Integer.parseInt(params[ i ]));
				tele.setY(Integer.parseInt(params[i+1]));
				tele.setTemplateId(Integer.parseInt(params[i+2]));
				tele.setToName(params[i+3]);
				tele.setToMapId(Integer.parseInt(params[i+4]));
				tele.setToX(Integer.parseInt(params[i+5]));
				tele.setToY(Integer.parseInt(params[i+6]));
				mt.addTeleport(tele);
			}
		} catch (Throwable e) {
			return false;
		}
	    return true;
	}

	private boolean loadCreatrue(BufferedReader bufferedReader, MapTemplate mt) throws IOException {
		String str = bufferedReader.readLine();
		// 已经读到文件末
		if (str == null) {
			return false;
		}
		
		logger.devLog("loadCreatrue");
		if (str.isEmpty()) {
			return true;
		}
		String[] params = str.split(",");
		try {
			//6120,126,177,0,5,0,0,120
			for (int i = 0; i < params.length; i += 8) {
				MapMonster monster = new MapMonster();
				monster.setTemplateid(Integer.parseInt(params[ i ]));
				monster.setX(Integer.parseInt(params[i+1]));
				monster.setY(Integer.parseInt(params[i+2]));
				monster.setSpawnType(Integer.parseInt(params[i+3]));
				monster.setRespawnTime(Integer.parseInt(params[i+4]));
				monster.setMoveType(Integer.parseInt(params[i+5]));
				monster.setFlag(Integer.parseInt(params[i+6]));
				monster.setToward((float) (Integer.parseInt(params[i+7]) * Math.PI));
				int pos = monster.getX() + mt.getBaseInfo().getWidth() * monster.getY();
				mt.addMonster(pos, monster);
			}
		} catch (Throwable e) {
			return false;
		}

	    return true;
	}

	private boolean loadGameObject(BufferedReader bufferedReader, MapTemplate mt) throws IOException {
		String str = bufferedReader.readLine();
		// 已经读到文件末
		if (str == null) {
			return false;
		}
		
		logger.devLog("loadGameObject");
		if (str.isEmpty()) {
			return true;
		}
		String[] params = str.split(",");
		try {
			//6120,126,177,0,5,0,0,120
			for (int i = 0; i < params.length; i += 5) {
				MapGameobject gameObject = new MapGameobject();
				gameObject.setTemplateid(Integer.parseInt(params[ i ]));
				gameObject.setX(Integer.parseInt(params[i+1]));
				gameObject.setY(Integer.parseInt(params[i+2]));
				gameObject.setRespawnTime(Integer.parseInt(params[i+3]));
				gameObject.setToward((float) (Integer.parseInt(params[i+4]) * Math.PI));
				mt.addGameObject(gameObject);
			}
		} catch (Throwable e) {
			return false;
		}

	    return true;
	}

}
