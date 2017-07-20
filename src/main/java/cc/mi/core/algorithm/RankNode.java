package cc.mi.core.algorithm;

import java.util.List;

public class RankNode {
	private RankNode lt = null;
	private RankNode rt = null;
	private RankNode parent = null;
	private int key = 0;
	private int ltCnt = 0;
	private int rtCnt = 0;
	private int height = 0;
	private List<?> list = null;

	public RankNode(int key, List<?> list) {
		this.key = key;
		this.list = list;
	}

	public void ModifyLtCnt(int addition) {
		this.ltCnt += addition;
	}

	public void ModifyRtCnt(int addition) {
		this.rtCnt += addition;
	}

	public RankNode getLt() {
		return lt;
	}

	public void setLt(RankNode lt) {
		this.lt = lt;
	}

	public RankNode getRt() {
		return rt;
	}

	public void setRt(RankNode rt) {
		this.rt = rt;
	}

	public RankNode getParent() {
		return parent;
	}

	public void setParent(RankNode parent) {
		this.parent = parent;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public int getLtCnt() {
		return ltCnt;
	}

	public void setLtCnt(int ltCnt) {
		this.ltCnt = ltCnt;
	}

	public int getRtCnt() {
		return rtCnt;
	}

	public void setRtCnt(int rtCnt) {
		this.rtCnt = rtCnt;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return list.toString();
	}
}