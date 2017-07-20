package cc.mi.core.algorithm;

/**
 * for hdu 2807 由于矩阵满足综合律: (A*B)*C = A*(B*C) 判断 A*B = D
 * 只需要添加变量C(所有列值不为0的任何一个列向量, 也就把2维的转成1维的, 减少运算量)
 **/
public class Matrix {
	private int[][] data = null;
	private int[][] vector = null;

	private final Matrix get1D(int n) {
		int[][] data = new int[n][1];
		for (int i = 0; i < n; ++i) {
			data[i][0] = 1;
		}
		return new Matrix(data, false);
	}

	public Matrix(int[][] data, boolean vectorable) {
		this.data = data;
		if (vectorable) {
			vector = this.multiData(get1D(this.getRows()));
		}
	}

	public Matrix getVectorMatrix() {
		if (vector == null)
			return null;
		return new Matrix(vector, false);
	}

	public int getRows() {
		return this.data.length;
	}

	public int getCols() {
		return this.data[0].length;
	}

	public int getValue(int i, int j) {
		return this.data[i][j];
	}

	public void print() {
		int n = this.getRows();
		int m = this.getCols();

		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < m; ++j) {
				System.out.print(this.getValue(i, j) + " ");
			}
			System.out.println();
		}
		System.out.println("-------------------");
	}

	private int[][] multiData(Matrix b) {
		int[][] value = null;
		try {
			int n = this.getRows();
			int m = this.getCols();
			int w = b.getRows();
			int t = b.getCols();

			if (m != w) {
				throw new Exception("这2个矩阵不能相乘");
			}
			value = new int[n][t];
			for (int i = 0; i < n; ++i) {
				for (int k = 0; k < t; ++k) {
					value[i][k] = 0;
					for (int j = 0; j < m; ++j) {
						value[i][k] += this.getValue(i, j) * b.getValue(j, k);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return value;
	}

	public Matrix multiply(Matrix b, boolean vectorable) {
		return new Matrix(multiData(b), vectorable);
	}

	public boolean compare(Matrix b) {
		int n = this.getRows();
		int m = this.getCols();
		int w = b.getRows();
		int t = b.getCols();

		if (n != w || m != t)
			return false;
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < m; ++j) {
				if (this.getValue(i, j) != b.getValue(i, j))
					return false;
			}
		}

		return true;
	}
}
