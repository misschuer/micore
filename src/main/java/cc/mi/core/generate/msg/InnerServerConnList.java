package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.PacketImpl;
import java.util.List;
import java.util.ArrayList;

/**
 * 内部服务器列表
 **/
public class InnerServerConnList extends PacketImpl  {
	//登录服连接的fd
	private int loginConn;
	//应用服连接的fd
	private int appConn;
	//日志服连接的fd
	private int recordConn;
	//场景服连接的fd
	private List<Integer> sceneConns;

	public InnerServerConnList() {
		super(30);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		buffer.writeInt(this.loginConn);
		buffer.writeInt(this.appConn);
		buffer.writeInt(this.recordConn);
		buffer.writeShort(this.sceneConns.size());
		for (int element : this.sceneConns) {
			buffer.writeInt(element);
		}
	}

	@Override
	public void decode(ByteBuf buffer) {
		this.loginConn = buffer.readInt(); 
		this.appConn = buffer.readInt(); 
		this.recordConn = buffer.readInt(); 
		int sceneConnsSize = buffer.readUnsignedShort();
		this.sceneConns = new ArrayList<>(sceneConnsSize);
		for (int i = 0; i < sceneConnsSize; ++ i) {
			int element = buffer.readInt();
			this.sceneConns.add(element);
		}
	}
	
	public int getLoginConn() {
		return this.loginConn;
	}
	
	public void setLoginConn(int loginConn) {
		this.loginConn = loginConn;
	}
		
	public int getAppConn() {
		return this.appConn;
	}
	
	public void setAppConn(int appConn) {
		this.appConn = appConn;
	}
		
	public int getRecordConn() {
		return this.recordConn;
	}
	
	public void setRecordConn(int recordConn) {
		this.recordConn = recordConn;
	}
		
	public List<Integer> getSceneConns() {
		return this.sceneConns;
	}
	
	public void setSceneConns(List<Integer> sceneConns) {
		this.sceneConns = sceneConns;
	}
	

	public PacketImpl newInstance() {
		return new InnerServerConnList();
	}
}