package cc.mi.core.server;

import java.util.List;

public class ServerConnList {
	private int loginConn;
	private int appConn;
	private int recordConn;
	private List<Integer> sceneConns;
	
	public ServerConnList() {}

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

	public List<Integer> getSceneConns() {
		return sceneConns;
	}

	public void setSceneConns(List<Integer> sceneConns) {
		this.sceneConns = sceneConns;
	}
}
