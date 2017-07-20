package cc.mi.core.algorithm;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

public class Fractal extends JFrame {
	private static final long serialVersionUID = 1L;
	private int screenWidth = 0;
	private int screenHeight = 0;
	private static Map<Integer, Integer> hash = null;

	public Fractal(int width, int height) {
		this.screenHeight = height;
		this.screenWidth = width;
		this.setTitle("Paint");
		this.setVisible(true);
		this.setSize(width, height);
		this.setResizable(false);
		this.setLocation(640, 480);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		init();
		new Fractal(640, 500);
	}

	public static int color24to8(int rgb) {
		int b = rgb & 0xFF;
		int g = (rgb >> 8) & 0xFF;
		int r = (rgb >> 16) & 0xFF;

		return ((77 * r + 151 * g + 28 * b) >> 8) & 0xFF;
	}

	public static void init() {
		hash = new HashMap<Integer, Integer>(256);

		for (int rgb = 0; rgb < (1 << 24); ++rgb) {
			int gray = color24to8(rgb);
			if (!hash.containsKey(gray))
				hash.put(gray, rgb);
		}
	}

	public static int color8to24Gray(int gray) {
		return (gray << 16) + (gray << 8) + gray;
	}

	public static int color8to24(int gray) {
		return hash.get(gray);
	}

	private Complex f(Complex x, Complex C) {
		int n = 1;

		for (int i = 0; i < n; ++i) {
			x = x.multiply(x).add(C);
		}

		return x;
	}

	public void paint(Graphics g) {
		super.paint(g);
		juliaSet(g);
	}

	public void demo(Graphics g) {
		BufferedImage b = new BufferedImage(256, 10, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < 256; ++i) {
			int rgb = color8to24(i);
			// System.out.println(i + " " + rgb);
			for (int j = 0; j < 10; ++j) {
				b.setRGB(i, j, rgb);
			}
		}
		g.drawImage(b, 30, 30, null);
	}

	public void juliaSet(Graphics g) {
		int width = 620;
		int height = 420;
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Complex C = new Complex(-0.75, -0.125);

		for (int x = -300; x < 300; ++x) {
			for (int y = -200; y < 200; ++y) {
				double dx = 1.0 * x / 200;
				double dy = 1.0 * y / 200;
				Complex z = new Complex(dx, dy);
				int gray = 0;
				for (gray = 0; gray < 200; ++gray) {
					if (z.getMoldSquare() > 4)
						break;
					z = f(z, C);
				}
				bi.setRGB(x + 310, y + 210, color8to24(gray));
			}
		}
		g.drawImage(bi, 10, 30, null);
	}

	public void mandelbrotSet(Graphics g) {
		int width = 620;
		int height = 420;
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		for (int x = -300; x < 300; ++x) {
			for (int y = -200; y < 200; ++y) {
				Complex z = new Complex(0, 0);
				double dx = 1.0 * x / 200;
				double dy = 1.0 * y / 200;
				Complex C = new Complex(dx, dy);
				int gray = 0;
				for (gray = 0; gray < 200; ++gray) {
					if (z.getMoldSquare() > 4)
						break;
					z = f(z, C);
				}
				bi.setRGB(x + 310, y + 210, color8to24(gray));
			}
		}
		g.drawImage(bi, 30, 30, null);
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}
}
