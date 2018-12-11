package cc.mi.core.utils;

import java.util.List;

public class Floyd extends ShortestPath {
	protected final float dist[][];
	
	public Floyd(int n) {
		this.dist = new float[ n ][ n ];
	}

	public void copyDist(float dis[][]) {
//		this.dist = null;
	}
	
	@Override
	public List<Point2D<Float>> getPath(int a, int b) {
		
		return null;
	}
}
