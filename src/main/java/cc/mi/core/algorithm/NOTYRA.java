package cc.mi.core.algorithm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.Scanner;

import javax.swing.JFrame;

/** 
 * 九曲黄河阵
 * 
 **/
public class NOTYRA extends JFrame {
	private static final long serialVersionUID = 6145935131176117455L;
	/** 灯关半径 */
	private static final int CIRCLE_RADIUS = 6;
	/** 格子大小 */
	private static final int BLOCK_SIZE = 34;
	/** 轮廓点宽度个数 */
	private static final int WIDTH = 19;
	/** 轮廓点高度个数 */
	private static final int HEIGHT = 19;
	/** 图画起始点位置 */
	private static final int startX = 20;
	private static final int startY = 20;
	
	/** 线段宽度 */
	private static final int SEG_WIDTH = 2;
	/** 联通 */
	private static final int LINE = 1;
//	private static final int NO_LINE = 0;
	
	/** 多个tab */
	private static final String TABS = "		";
	
	public NOTYRA() {
		this.setTitle("Paint");
		this.setVisible(true);
		int width = (WIDTH + 3) * BLOCK_SIZE;
		int height = (HEIGHT + 3) * BLOCK_SIZE;
		this.setSize(width, height);
		this.setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int) (dim.getWidth () / 2);
		int centerY = (int) (dim.getHeight() / 2);
		int midW = width  >> 1;
		int midH = height >> 1;
		this.setLocation(centerX - midW, centerY - midH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		int[][][] hui = this.getNOTYRAAdjacent();
		this.drawHuiArray(g, hui);
		this.drawLight(g);
	}
	
	private void drawLight(Graphics g) {
		g.setColor(Color.BLACK);
		for (int i = 1; i <= HEIGHT; ++ i) {
			for (int j = 1; j <= WIDTH; ++ j) {
				int x = j * BLOCK_SIZE + startX;
				int y = i * BLOCK_SIZE + startY;
				int w = CIRCLE_RADIUS;
				int h = CIRCLE_RADIUS;
				if (j == i && (i + j) == HEIGHT + 1) {
					w <<= 1;
					h <<= 1;
				}
				x -= w >> 1;
				y -= h >> 1;
				g.fillOval(x, y, w, h);
			}
		}
	}
	
	private int[][][] getNOTYRAAdjacent() {
		int[][][] hui = new int[HEIGHT+1][WIDTH+1][ 2 ];
		hui[ 1 ][ 1 ][ 0 ] = LINE;
		hui[ 1 ][ 1 ][ 1 ] = LINE;
		hui[ 1 ][ 2 ][ 0 ] = LINE;
		hui[ 1 ][ 3 ][ 0 ] = LINE;
		hui[ 1 ][ 4 ][ 0 ] = LINE;
		hui[ 1 ][ 5 ][ 0 ] = LINE;
		hui[ 1 ][ 6 ][ 0 ] = LINE;
		hui[ 1 ][ 7 ][ 0 ] = LINE;
		hui[ 1 ][ 8 ][ 0 ] = LINE;
		hui[ 1 ][ 9 ][ 0 ] = LINE;
		hui[ 1 ][ 10 ][ 0 ] = LINE;
		hui[ 1 ][ 11 ][ 0 ] = LINE;
		hui[ 1 ][ 12 ][ 0 ] = LINE;
		hui[ 1 ][ 13 ][ 0 ] = LINE;
		hui[ 1 ][ 14 ][ 0 ] = LINE;
		hui[ 1 ][ 15 ][ 0 ] = LINE;
		hui[ 1 ][ 16 ][ 0 ] = LINE;
		hui[ 1 ][ 17 ][ 0 ] = LINE;
		hui[ 1 ][ 18 ][ 0 ] = LINE;
		hui[ 1 ][ 19 ][ 1 ] = LINE;
		hui[ 2 ][ 1 ][ 1 ] = LINE;
		hui[ 2 ][ 2 ][ 0 ] = LINE;
		hui[ 2 ][ 2 ][ 1 ] = LINE;
		hui[ 2 ][ 3 ][ 0 ] = LINE;
		hui[ 2 ][ 4 ][ 0 ] = LINE;
		hui[ 2 ][ 5 ][ 0 ] = LINE;
		hui[ 2 ][ 6 ][ 0 ] = LINE;
		hui[ 2 ][ 7 ][ 0 ] = LINE;
		hui[ 2 ][ 8 ][ 0 ] = LINE;
		hui[ 2 ][ 9 ][ 0 ] = LINE;
		hui[ 2 ][ 10 ][ 0 ] = LINE;
		hui[ 2 ][ 11 ][ 0 ] = LINE;
		hui[ 2 ][ 12 ][ 0 ] = LINE;
		hui[ 2 ][ 13 ][ 0 ] = LINE;
		hui[ 2 ][ 14 ][ 0 ] = LINE;
		hui[ 2 ][ 15 ][ 0 ] = LINE;
		hui[ 2 ][ 16 ][ 0 ] = LINE;
		hui[ 2 ][ 17 ][ 0 ] = LINE;
		hui[ 2 ][ 18 ][ 1 ] = LINE;
		hui[ 2 ][ 19 ][ 1 ] = LINE;
		hui[ 3 ][ 1 ][ 1 ] = LINE;
		hui[ 3 ][ 2 ][ 1 ] = LINE;
		hui[ 3 ][ 3 ][ 0 ] = LINE;
		hui[ 3 ][ 3 ][ 1 ] = LINE;
		hui[ 3 ][ 4 ][ 0 ] = LINE;
		hui[ 3 ][ 5 ][ 0 ] = LINE;
		hui[ 3 ][ 6 ][ 0 ] = LINE;
		hui[ 3 ][ 7 ][ 0 ] = LINE;
		hui[ 3 ][ 7 ][ 1 ] = LINE;
		hui[ 3 ][ 8 ][ 0 ] = LINE;
		hui[ 3 ][ 9 ][ 0 ] = LINE;
		hui[ 3 ][ 10 ][ 0 ] = LINE;
		hui[ 3 ][ 11 ][ 0 ] = LINE;
		hui[ 3 ][ 12 ][ 0 ] = LINE;
		hui[ 3 ][ 13 ][ 0 ] = LINE;
		hui[ 3 ][ 13 ][ 1 ] = LINE;
		hui[ 3 ][ 14 ][ 0 ] = LINE;
		hui[ 3 ][ 15 ][ 0 ] = LINE;
		hui[ 3 ][ 16 ][ 0 ] = LINE;
		hui[ 3 ][ 17 ][ 1 ] = LINE;
		hui[ 3 ][ 18 ][ 1 ] = LINE;
		hui[ 3 ][ 19 ][ 1 ] = LINE;
		hui[ 4 ][ 1 ][ 1 ] = LINE;
		hui[ 4 ][ 2 ][ 1 ] = LINE;
		hui[ 4 ][ 3 ][ 1 ] = LINE;
		hui[ 4 ][ 4 ][ 0 ] = LINE;
		hui[ 4 ][ 5 ][ 0 ] = LINE;
		hui[ 4 ][ 6 ][ 1 ] = LINE;
		hui[ 4 ][ 7 ][ 1 ] = LINE;
		hui[ 4 ][ 8 ][ 0 ] = LINE;
		hui[ 4 ][ 8 ][ 1 ] = LINE;
		hui[ 4 ][ 9 ][ 0 ] = LINE;
		hui[ 4 ][ 10 ][ 0 ] = LINE;
		hui[ 4 ][ 11 ][ 0 ] = LINE;
		hui[ 4 ][ 12 ][ 1 ] = LINE;
		hui[ 4 ][ 13 ][ 1 ] = LINE;
		hui[ 4 ][ 14 ][ 0 ] = LINE;
		hui[ 4 ][ 14 ][ 1 ] = LINE;
		hui[ 4 ][ 15 ][ 0 ] = LINE;
		hui[ 4 ][ 17 ][ 1 ] = LINE;
		hui[ 4 ][ 18 ][ 1 ] = LINE;
		hui[ 4 ][ 19 ][ 1 ] = LINE;
		hui[ 5 ][ 1 ][ 1 ] = LINE;
		hui[ 5 ][ 2 ][ 1 ] = LINE;
		hui[ 5 ][ 3 ][ 0 ] = LINE;
		hui[ 5 ][ 4 ][ 0 ] = LINE;
		hui[ 5 ][ 6 ][ 1 ] = LINE;
		hui[ 5 ][ 7 ][ 1 ] = LINE;
		hui[ 5 ][ 8 ][ 1 ] = LINE;
		hui[ 5 ][ 9 ][ 0 ] = LINE;
		hui[ 5 ][ 10 ][ 0 ] = LINE;
		hui[ 5 ][ 11 ][ 1 ] = LINE;
		hui[ 5 ][ 12 ][ 1 ] = LINE;
		hui[ 5 ][ 13 ][ 1 ] = LINE;
		hui[ 5 ][ 14 ][ 1 ] = LINE;
		hui[ 5 ][ 15 ][ 0 ] = LINE;
		hui[ 5 ][ 16 ][ 0 ] = LINE;
		hui[ 5 ][ 18 ][ 1 ] = LINE;
		hui[ 5 ][ 19 ][ 1 ] = LINE;
		hui[ 6 ][ 1 ][ 1 ] = LINE;
		hui[ 6 ][ 2 ][ 0 ] = LINE;
		hui[ 6 ][ 2 ][ 1 ] = LINE;
		hui[ 6 ][ 3 ][ 0 ] = LINE;
		hui[ 6 ][ 4 ][ 0 ] = LINE;
		hui[ 6 ][ 5 ][ 0 ] = LINE;
		hui[ 6 ][ 7 ][ 1 ] = LINE;
		hui[ 6 ][ 8 ][ 0 ] = LINE;
		hui[ 6 ][ 9 ][ 0 ] = LINE;
		hui[ 6 ][ 11 ][ 1 ] = LINE;
		hui[ 6 ][ 12 ][ 1 ] = LINE;
		hui[ 6 ][ 13 ][ 1 ] = LINE;
		hui[ 6 ][ 14 ][ 0 ] = LINE;
		hui[ 6 ][ 15 ][ 0 ] = LINE;
		hui[ 6 ][ 16 ][ 0 ] = LINE;
		hui[ 6 ][ 17 ][ 0 ] = LINE;
		hui[ 6 ][ 18 ][ 1 ] = LINE;
		hui[ 6 ][ 19 ][ 1 ] = LINE;
		hui[ 7 ][ 1 ][ 1 ] = LINE;
		hui[ 7 ][ 2 ][ 1 ] = LINE;
		hui[ 7 ][ 3 ][ 0 ] = LINE;
		hui[ 7 ][ 3 ][ 1 ] = LINE;
		hui[ 7 ][ 4 ][ 0 ] = LINE;
		hui[ 7 ][ 5 ][ 0 ] = LINE;
		hui[ 7 ][ 6 ][ 0 ] = LINE;
		hui[ 7 ][ 7 ][ 0 ] = LINE;
		hui[ 7 ][ 8 ][ 0 ] = LINE;
		hui[ 7 ][ 9 ][ 0 ] = LINE;
		hui[ 7 ][ 10 ][ 0 ] = LINE;
		hui[ 7 ][ 12 ][ 1 ] = LINE;
		hui[ 7 ][ 13 ][ 0 ] = LINE;
		hui[ 7 ][ 13 ][ 1 ] = LINE;
		hui[ 7 ][ 14 ][ 0 ] = LINE;
		hui[ 7 ][ 15 ][ 0 ] = LINE;
		hui[ 7 ][ 16 ][ 0 ] = LINE;
		hui[ 7 ][ 17 ][ 1 ] = LINE;
		hui[ 7 ][ 18 ][ 1 ] = LINE;
		hui[ 7 ][ 19 ][ 1 ] = LINE;
		hui[ 8 ][ 1 ][ 1 ] = LINE;
		hui[ 8 ][ 2 ][ 1 ] = LINE;
		hui[ 8 ][ 3 ][ 1 ] = LINE;
		hui[ 8 ][ 4 ][ 0 ] = LINE;
		hui[ 8 ][ 4 ][ 1 ] = LINE;
		hui[ 8 ][ 5 ][ 0 ] = LINE;
		hui[ 8 ][ 6 ][ 0 ] = LINE;
		hui[ 8 ][ 7 ][ 0 ] = LINE;
		hui[ 8 ][ 8 ][ 0 ] = LINE;
		hui[ 8 ][ 8 ][ 1 ] = LINE;
		hui[ 8 ][ 9 ][ 0 ] = LINE;
		hui[ 8 ][ 10 ][ 0 ] = LINE;
		hui[ 8 ][ 11 ][ 0 ] = LINE;
		hui[ 8 ][ 12 ][ 1 ] = LINE;
		hui[ 8 ][ 13 ][ 1 ] = LINE;
		hui[ 8 ][ 14 ][ 0 ] = LINE;
		hui[ 8 ][ 14 ][ 1 ] = LINE;
		hui[ 8 ][ 15 ][ 0 ] = LINE;
		hui[ 8 ][ 16 ][ 1 ] = LINE;
		hui[ 8 ][ 17 ][ 1 ] = LINE;
		hui[ 8 ][ 18 ][ 1 ] = LINE;
		hui[ 8 ][ 19 ][ 1 ] = LINE;
		hui[ 9 ][ 1 ][ 1 ] = LINE;
		hui[ 9 ][ 2 ][ 1 ] = LINE;
		hui[ 9 ][ 3 ][ 1 ] = LINE;
		hui[ 9 ][ 4 ][ 1 ] = LINE;
		hui[ 9 ][ 5 ][ 0 ] = LINE;
		hui[ 9 ][ 5 ][ 1 ] = LINE;
		hui[ 9 ][ 6 ][ 0 ] = LINE;
		hui[ 9 ][ 7 ][ 1 ] = LINE;
		hui[ 9 ][ 8 ][ 1 ] = LINE;
		hui[ 9 ][ 9 ][ 0 ] = LINE;
		hui[ 9 ][ 9 ][ 1 ] = LINE;
		hui[ 9 ][ 10 ][ 0 ] = LINE;
		hui[ 9 ][ 11 ][ 1 ] = LINE;
		hui[ 9 ][ 12 ][ 1 ] = LINE;
		hui[ 9 ][ 13 ][ 1 ] = LINE;
		hui[ 9 ][ 14 ][ 1 ] = LINE;
		hui[ 9 ][ 15 ][ 1 ] = LINE;
		hui[ 9 ][ 16 ][ 1 ] = LINE;
		hui[ 9 ][ 17 ][ 1 ] = LINE;
		hui[ 9 ][ 18 ][ 1 ] = LINE;
		hui[ 9 ][ 19 ][ 1 ] = LINE;
		hui[ 10 ][ 1 ][ 1 ] = LINE;
		hui[ 10 ][ 2 ][ 1 ] = LINE;
		hui[ 10 ][ 3 ][ 1 ] = LINE;
		hui[ 10 ][ 4 ][ 1 ] = LINE;
		hui[ 10 ][ 5 ][ 1 ] = LINE;
		hui[ 10 ][ 6 ][ 1 ] = LINE;
		hui[ 10 ][ 7 ][ 1 ] = LINE;
		hui[ 10 ][ 8 ][ 1 ] = LINE;
		hui[ 10 ][ 9 ][ 1 ] = LINE;
		hui[ 10 ][ 10 ][ 1 ] = LINE;
		hui[ 10 ][ 11 ][ 1 ] = LINE;
		hui[ 10 ][ 12 ][ 1 ] = LINE;
		hui[ 10 ][ 13 ][ 1 ] = LINE;
		hui[ 10 ][ 15 ][ 1 ] = LINE;
		hui[ 10 ][ 16 ][ 1 ] = LINE;
		hui[ 10 ][ 17 ][ 1 ] = LINE;
		hui[ 10 ][ 18 ][ 1 ] = LINE;
		hui[ 10 ][ 19 ][ 1 ] = LINE;
		hui[ 11 ][ 1 ][ 1 ] = LINE;
		hui[ 11 ][ 2 ][ 1 ] = LINE;
		hui[ 11 ][ 3 ][ 1 ] = LINE;
		hui[ 11 ][ 4 ][ 1 ] = LINE;
		hui[ 11 ][ 6 ][ 1 ] = LINE;
		hui[ 11 ][ 7 ][ 1 ] = LINE;
		hui[ 11 ][ 8 ][ 1 ] = LINE;
		hui[ 11 ][ 10 ][ 1 ] = LINE;
		hui[ 11 ][ 11 ][ 1 ] = LINE;
		hui[ 11 ][ 12 ][ 1 ] = LINE;
		hui[ 11 ][ 13 ][ 0 ] = LINE;
		hui[ 11 ][ 14 ][ 0 ] = LINE;
		hui[ 11 ][ 16 ][ 1 ] = LINE;
		hui[ 11 ][ 17 ][ 1 ] = LINE;
		hui[ 11 ][ 18 ][ 1 ] = LINE;
		hui[ 11 ][ 19 ][ 1 ] = LINE;
		hui[ 12 ][ 1 ][ 1 ] = LINE;
		hui[ 12 ][ 2 ][ 1 ] = LINE;
		hui[ 12 ][ 3 ][ 1 ] = LINE;
		hui[ 12 ][ 4 ][ 0 ] = LINE;
		hui[ 12 ][ 5 ][ 0 ] = LINE;
		hui[ 12 ][ 7 ][ 1 ] = LINE;
		hui[ 12 ][ 8 ][ 0 ] = LINE;
		hui[ 12 ][ 9 ][ 0 ] = LINE;
		hui[ 12 ][ 11 ][ 1 ] = LINE;
		hui[ 12 ][ 12 ][ 0 ] = LINE;
		hui[ 12 ][ 12 ][ 1 ] = LINE;
		hui[ 12 ][ 13 ][ 0 ] = LINE;
		hui[ 12 ][ 14 ][ 0 ] = LINE;
		hui[ 12 ][ 15 ][ 0 ] = LINE;
		hui[ 12 ][ 17 ][ 1 ] = LINE;
		hui[ 12 ][ 18 ][ 1 ] = LINE;
		hui[ 12 ][ 19 ][ 1 ] = LINE;
		hui[ 13 ][ 1 ][ 1 ] = LINE;
		hui[ 13 ][ 2 ][ 1 ] = LINE;
		hui[ 13 ][ 3 ][ 0 ] = LINE;
		hui[ 13 ][ 3 ][ 1 ] = LINE;
		hui[ 13 ][ 4 ][ 0 ] = LINE;
		hui[ 13 ][ 5 ][ 0 ] = LINE;
		hui[ 13 ][ 6 ][ 0 ] = LINE;
		hui[ 13 ][ 7 ][ 0 ] = LINE;
		hui[ 13 ][ 7 ][ 1 ] = LINE;
		hui[ 13 ][ 8 ][ 0 ] = LINE;
		hui[ 13 ][ 9 ][ 0 ] = LINE;
		hui[ 13 ][ 10 ][ 0 ] = LINE;
		hui[ 13 ][ 12 ][ 1 ] = LINE;
		hui[ 13 ][ 13 ][ 0 ] = LINE;
		hui[ 13 ][ 13 ][ 1 ] = LINE;
		hui[ 13 ][ 14 ][ 0 ] = LINE;
		hui[ 13 ][ 15 ][ 0 ] = LINE;
		hui[ 13 ][ 16 ][ 0 ] = LINE;
		hui[ 13 ][ 17 ][ 1 ] = LINE;
		hui[ 13 ][ 18 ][ 1 ] = LINE;
		hui[ 13 ][ 19 ][ 1 ] = LINE;
		hui[ 14 ][ 1 ][ 1 ] = LINE;
		hui[ 14 ][ 2 ][ 1 ] = LINE;
		hui[ 14 ][ 3 ][ 1 ] = LINE;
		hui[ 14 ][ 4 ][ 0 ] = LINE;
		hui[ 14 ][ 4 ][ 1 ] = LINE;
		hui[ 14 ][ 5 ][ 0 ] = LINE;
		hui[ 14 ][ 6 ][ 1 ] = LINE;
		hui[ 14 ][ 7 ][ 1 ] = LINE;
		hui[ 14 ][ 8 ][ 0 ] = LINE;
		hui[ 14 ][ 8 ][ 1 ] = LINE;
		hui[ 14 ][ 9 ][ 0 ] = LINE;
		hui[ 14 ][ 10 ][ 0 ] = LINE;
		hui[ 14 ][ 11 ][ 0 ] = LINE;
		hui[ 14 ][ 12 ][ 1 ] = LINE;
		hui[ 14 ][ 13 ][ 1 ] = LINE;
		hui[ 14 ][ 14 ][ 0 ] = LINE;
		hui[ 14 ][ 14 ][ 1 ] = LINE;
		hui[ 14 ][ 15 ][ 0 ] = LINE;
		hui[ 14 ][ 16 ][ 1 ] = LINE;
		hui[ 14 ][ 17 ][ 1 ] = LINE;
		hui[ 14 ][ 18 ][ 1 ] = LINE;
		hui[ 14 ][ 19 ][ 1 ] = LINE;
		hui[ 15 ][ 1 ][ 1 ] = LINE;
		hui[ 15 ][ 2 ][ 1 ] = LINE;
		hui[ 15 ][ 3 ][ 1 ] = LINE;
		hui[ 15 ][ 4 ][ 1 ] = LINE;
		hui[ 15 ][ 5 ][ 1 ] = LINE;
		hui[ 15 ][ 6 ][ 1 ] = LINE;
		hui[ 15 ][ 7 ][ 1 ] = LINE;
		hui[ 15 ][ 8 ][ 1 ] = LINE;
		hui[ 15 ][ 9 ][ 0 ] = LINE;
		hui[ 15 ][ 9 ][ 1 ] = LINE;
		hui[ 15 ][ 10 ][ 0 ] = LINE;
		hui[ 15 ][ 12 ][ 1 ] = LINE;
		hui[ 15 ][ 13 ][ 1 ] = LINE;
		hui[ 15 ][ 14 ][ 1 ] = LINE;
		hui[ 15 ][ 15 ][ 1 ] = LINE;
		hui[ 15 ][ 16 ][ 1 ] = LINE;
		hui[ 15 ][ 17 ][ 1 ] = LINE;
		hui[ 15 ][ 18 ][ 1 ] = LINE;
		hui[ 15 ][ 19 ][ 1 ] = LINE;
		hui[ 16 ][ 1 ][ 1 ] = LINE;
		hui[ 16 ][ 2 ][ 1 ] = LINE;
		hui[ 16 ][ 3 ][ 1 ] = LINE;
		hui[ 16 ][ 5 ][ 1 ] = LINE;
		hui[ 16 ][ 6 ][ 1 ] = LINE;
		hui[ 16 ][ 7 ][ 1 ] = LINE;
		hui[ 16 ][ 8 ][ 1 ] = LINE;
		hui[ 16 ][ 9 ][ 1 ] = LINE;
		hui[ 16 ][ 10 ][ 0 ] = LINE;
		hui[ 16 ][ 11 ][ 0 ] = LINE;
		hui[ 16 ][ 13 ][ 1 ] = LINE;
		hui[ 16 ][ 14 ][ 1 ] = LINE;
		hui[ 16 ][ 15 ][ 1 ] = LINE;
		hui[ 16 ][ 17 ][ 1 ] = LINE;
		hui[ 16 ][ 18 ][ 1 ] = LINE;
		hui[ 16 ][ 19 ][ 1 ] = LINE;
		hui[ 17 ][ 1 ][ 1 ] = LINE;
		hui[ 17 ][ 2 ][ 1 ] = LINE;
		hui[ 17 ][ 3 ][ 0 ] = LINE;
		hui[ 17 ][ 4 ][ 0 ] = LINE;
		hui[ 17 ][ 6 ][ 1 ] = LINE;
		hui[ 17 ][ 7 ][ 1 ] = LINE;
		hui[ 17 ][ 8 ][ 1 ] = LINE;
		hui[ 17 ][ 9 ][ 0 ] = LINE;
		hui[ 17 ][ 10 ][ 0 ] = LINE;
		hui[ 17 ][ 11 ][ 0 ] = LINE;
		hui[ 17 ][ 12 ][ 0 ] = LINE;
		hui[ 17 ][ 14 ][ 1 ] = LINE;
		hui[ 17 ][ 15 ][ 0 ] = LINE;
		hui[ 17 ][ 16 ][ 0 ] = LINE;
		hui[ 17 ][ 18 ][ 1 ] = LINE;
		hui[ 17 ][ 19 ][ 1 ] = LINE;
		hui[ 18 ][ 1 ][ 1 ] = LINE;
		hui[ 18 ][ 2 ][ 0 ] = LINE;
		hui[ 18 ][ 3 ][ 0 ] = LINE;
		hui[ 18 ][ 4 ][ 0 ] = LINE;
		hui[ 18 ][ 5 ][ 0 ] = LINE;
		hui[ 18 ][ 7 ][ 1 ] = LINE;
		hui[ 18 ][ 8 ][ 0 ] = LINE;
		hui[ 18 ][ 9 ][ 0 ] = LINE;
		hui[ 18 ][ 10 ][ 0 ] = LINE;
		hui[ 18 ][ 10 ][ 1 ] = LINE;
		hui[ 18 ][ 11 ][ 0 ] = LINE;
		hui[ 18 ][ 12 ][ 0 ] = LINE;
		hui[ 18 ][ 13 ][ 0 ] = LINE;
		hui[ 18 ][ 14 ][ 0 ] = LINE;
		hui[ 18 ][ 15 ][ 0 ] = LINE;
		hui[ 18 ][ 16 ][ 0 ] = LINE;
		hui[ 18 ][ 17 ][ 0 ] = LINE;
		hui[ 18 ][ 19 ][ 1 ] = LINE;
		hui[ 19 ][ 1 ][ 0 ] = LINE;
		hui[ 19 ][ 2 ][ 0 ] = LINE;
		hui[ 19 ][ 3 ][ 0 ] = LINE;
		hui[ 19 ][ 4 ][ 0 ] = LINE;
		hui[ 19 ][ 5 ][ 0 ] = LINE;
		hui[ 19 ][ 6 ][ 0 ] = LINE;
		hui[ 19 ][ 7 ][ 0 ] = LINE;
		hui[ 19 ][ 8 ][ 0 ] = LINE;
		hui[ 19 ][ 9 ][ 1 ] = LINE;
		hui[ 19 ][ 10 ][ 1 ] = LINE;
		hui[ 19 ][ 11 ][ 0 ] = LINE;
		hui[ 19 ][ 11 ][ 1 ] = LINE;
		hui[ 19 ][ 12 ][ 0 ] = LINE;
		hui[ 19 ][ 13 ][ 0 ] = LINE;
		hui[ 19 ][ 14 ][ 0 ] = LINE;
		hui[ 19 ][ 15 ][ 0 ] = LINE;
		hui[ 19 ][ 16 ][ 0 ] = LINE;
		hui[ 19 ][ 17 ][ 0 ] = LINE;
		hui[ 19 ][ 18 ][ 0 ] = LINE;		
		
		return hui;
	}
	
//	private int[][][] getHuiAdjacent() {
//		int[][][] hui = new int[HEIGHT+1][WIDTH+1][ 2 ];
//		
//		for (int i = 1; i <= HEIGHT; ++ i) {
//			for (int j = 1; j <= WIDTH; ++ j) {
//				if (i <= j && i + j <= WIDTH) {
//					hui[ i ][ j ][ 0 ] = LINE;
//				}
//				if (i >= j && i + j <= HEIGHT) {
//					hui[ i ][ j ][ 1 ] = LINE;
//				}
//				
//				if (i > j && i + j > HEIGHT) {
//					hui[ i ][ j ][ 0 ] = LINE;
//				}
//				if (i < j && i + j > WIDTH) {
//					hui[ i ][ j ][ 1 ] = LINE;
//				}
//			}
//		}
//		
//		return hui;
//	}
	
	
//	private void drawRound(Graphics g) {
//		int[][][] hui = getHuiAdjacent();
//		drawHuiArray(g, hui);
//	}
	
	private void drawHuiArray(Graphics g, int[][][] hui) {
		g.setColor(Color.BLACK);
		for (int i = 1; i <= HEIGHT; ++ i) {
			for (int j = 1; j <= WIDTH; ++ j) {
				int x = j * BLOCK_SIZE + startX;
				int y = i * BLOCK_SIZE + startY;
				int offset = SEG_WIDTH >> 1;
				if (hui[ i ][ j ][ 0 ] == LINE) {
					g.fillRect(x-offset, y-offset, BLOCK_SIZE, SEG_WIDTH);
				}
				if (hui[ i ][ j ][ 1 ] == LINE) {
					g.fillRect(x-offset, y-offset, SEG_WIDTH, BLOCK_SIZE);
				}
			}
		}
		g.setColor(Color.MAGENTA);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 23));
		g.drawString("九曲黄河阵", startX + BLOCK_SIZE, (HEIGHT + 2) * BLOCK_SIZE);
	}	
	
	public static void main(String[] args) {
		new NOTYRA();
	}
	
	public int[][][] Input(Scanner cin) {
		int[][][] hui = new int[HEIGHT+1][WIDTH+1][ 2 ];
		String[] lines = new String[HEIGHT];
		for (int i = 0; i < HEIGHT; ++ i) {
			lines[ i ] = cin.nextLine();
		}
		for (int i = 1; i <= HEIGHT; ++ i) {
			int x = 0;
			String line = lines[i-1];
			for (int j = 0; j < line.length(); ++ j) {
				char ch = line.charAt(j);
				if (ch != ' ') {
					x ++;
					if (ch == 'C') {
						hui[ i ][ x ][ 0 ] = LINE;
						hui[ i ][ x ][ 1 ] = LINE;
						System.out.printf(TABS + "hui[ %d ][ %d ][ 0 ] = LINE;\n", i, x);
						System.out.printf(TABS + "hui[ %d ][ %d ][ 1 ] = LINE;\n", i, x);
					}
					else if (ch == '─') {
						hui[ i ][ x ][ 0 ] = LINE;
						System.out.printf(TABS + "hui[ %d ][ %d ][ 0 ] = LINE;\n", i, x);
					}
					else if (ch == '︳') {
						hui[ i ][ x ][ 1 ] = LINE;
						System.out.printf(TABS + "hui[ %d ][ %d ][ 1 ] = LINE;\n", i, x);
					}
				}
			}
		}
		
		return hui;
	}
}

/**
C─────────────────︳  
︳C───────────────︳︳ 
︳︳C───C─────C───︳︳︳
︳︳︳ ──︳︳C───︳︳C ─#︳︳︳
︳︳  ──#︳︳︳ ──︳︳︳︳ ──#︳︳
︳C───#︳   ──#︳︳︳  ────︳︳
︳︳C───────# ︳C───︳ ︳︳
︳︳︳C───C───︳ ︳C─︳︳ ︳︳
︳︳︳︳C─︳︳C─︳︳ ︳︳︳︳︳︳︳
︳︳︳︳︳︳︳︳︳︳︳︳ ︳# ︳︳︳︳︳
︳︳︳︳ #︳︳︳# ︳︳︳──#︳︳︳︳
︳︳︳  ──#︳  ──#︳C───#︳︳︳
︳︳C───C───#︳C───︳︳︳
︳︳︳C─︳︳C───︳︳C─︳︳︳︳
︳︳︳︳︳︳︳︳C─# ︳︳︳︳︳︳︳︳
︳︳︳ #︳︳︳︳︳  ──#︳︳︳ # ︳︳︳
︳︳ ──#︳︳︳  ────#︳  ──#︳︳
︳ ────#︳── C ───────#︳
────────︳︳C───────
 **/