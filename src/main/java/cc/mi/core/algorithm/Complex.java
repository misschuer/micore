package cc.mi.core.algorithm;

public class Complex implements Cloneable {
	private double real;
	private double image;
	private static final double EPS = 1e-4;

	public double getMoldSquare() {
		return this.real * this.real + this.image * this.image;
	}

	public static Complex getOriginal() {
		return new Complex(0, 0);
	}

	public Complex(double real, double image) {
		this.real = real;
		this.image = image;
	}

	public Complex clone() {
		return new Complex(real, image);
	}

	public Complex add(Complex comp) {
		return new Complex(this.real + comp.getReal(), this.image + comp.getImage());
	}

	public Complex substract(Complex comp) {
		return new Complex(this.real - comp.getReal(), this.image - comp.getImage());
	}

	public Complex multiply(Complex comp) {
		/** (a + bI)(c + dI) = (ac - bd) + i(bc + ad) */
		double a = this.getReal();
		double b = this.getImage();
		double c = comp.getReal();
		double d = comp.getImage();
		double nReal = a * c - b * d;
		double nImage = b * c + a * d;

		return new Complex(nReal, nImage);
	}

	public Complex divide(Complex comp) {
		/** (a + bI)/(c + dI) = (ac + bd)/(c^2 + d^2) + i(bc - ad)/(c^2 + d^2) */
		double a = this.getReal();
		double b = this.getImage();
		double c = comp.getReal();
		double d = comp.getImage();
		double deno = c * c + d * d;
		double nReal = (a * c + b * d) / deno;
		double nImage = (b * c - a * d) / deno;

		return new Complex(nReal, nImage);
	}

	@Override
	public String toString() {
		double cmpReal = Math.abs(this.getReal());
		double cmpImage = Math.abs(this.getImage());
		String str = "";

		if (cmpReal <= EPS || cmpImage <= EPS) {
			boolean srcFlag = true;
			if (cmpReal > EPS) {
				srcFlag = false;
				str += this.getReal() + "";
			}
			if (cmpImage > EPS) {
				srcFlag = false;
				str += this.getImage() + "i";
			}
			if (srcFlag) {
				str = "0";
			}
		} else {
			String oper = "+";
			if (this.getImage() < 0)
				oper = "-";
			str = this.getReal() + " " + oper + " " + Math.abs(this.getImage()) + "i";
		}

		return str;
	}

	public double getReal() {
		return real;
	}

	public void setReal(double real) {
		this.real = real;
	}

	public double getImage() {
		return image;
	}

	public void setImage(double image) {
		this.image = image;
	}
}