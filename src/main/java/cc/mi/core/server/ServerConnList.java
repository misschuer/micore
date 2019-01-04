package cc.mi.core.server;

import java.util.ArrayList;
import java.util.List;

import cc.mi.core.callback.InvokeCallback;

public enum ServerConnList {
	INSTANCE;
	
	private int loginConn;
	private int appConn;
	private int recordConn;
	private List<Integer> sceneConns;
	
	private ServerConnList() {}

	public int getLoginConn() {
		return loginConn;
	}

	public void setLoginConn(int loginConn) {
		this.loginConn = loginConn;
	}

	public int getAppConn() {
		return appConn;
	}

	public void setAppConn(int appConn) {
		this.appConn = appConn;
	}

	public int getRecordConn() {
		return recordConn;
	}

	public void setRecordConn(int recordConn) {
		this.recordConn = recordConn;
	}
	
	public void addSceneConns(List<Integer> conns) {
		if (this.sceneConns == null) {
			this.sceneConns = new ArrayList<>();
		}
		this.sceneConns.addAll(conns);
	}
	
	public void addSceneConn(int conn) {
		if (this.sceneConns == null) {
			this.sceneConns = new ArrayList<>();
		}
		this.sceneConns.add(conn);
	}
	
	public int getSceneConnByMapId(int mapId) {
		if (this.sceneConns == null) {
			return 0;
		}
		// TODO: 需要配表
		return this.sceneConns.get(0);
	}
	
	public void foreach(InvokeCallback<Integer> callback) {
		if (this.sceneConns != null) {
			for (int conn : this.sceneConns) {
				callback.invoke(conn);
			}
		}
	}
	
	public int getSceneCnt() {
		if (this.sceneConns == null) {
			return 0;
		}
		return this.sceneConns.size();
	}
}
