package com.shuo747.ilnpu.utils.rc;

import com.shuo747.ilnpu.utils.rc.constant.Match;
import com.shuo747.ilnpu.utils.rc.constant.Template;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;


public class RCYKT {

	static {
		System.setProperty("com.sun.media.jai.disableMediaLib", "true");
	}

	public static Match match1 = new Match();
	public static Match match2 = new Match();
	private static int differ = 1000;
	private static int posTemplate = -1;
	// private static int[] Num2Pos= {};

	public static int recognize(byte[] byteArray) throws Exception {
		InputStream is = new ByteArrayInputStream(byteArray);
		BufferedImage image = ImageIO.read(is);
		return recognize(image);
	}
	/*
	 * public static String recognize(BufferedImage image) throws Exception { //
	 * image=C2(image); // ImageIO.write(image, "jpg",new File("d:/10.jpg"));
	 * 
	 * StringBuffer sb = new StringBuffer(""); BufferedImage newim[] = new
	 * BufferedImage[4]; if (null == image) { throw new
	 * RuntimeException("image为null"); } // 将图像分成四块，因为要处理的文件有四个数字。 int width =
	 * image.getWidth(); int height = image.getHeight(); int digitWidth =7; int
	 * operatorWidth=8; int hzWidth =14; int hOffset=31; int xh=-1; newim[0] =
	 * generateSingleColorBitMap(image.getSubimage(hOffset, 0, hzWidth, height));
	 * newim[1] = generateSingleColorBitMap(image.getSubimage(hOffset, 0,
	 * digitWidth, height)); newim[2] =
	 * generateSingleColorBitMap(image.getSubimage(15, 0, operatorWidth, height));
	 * newim[3] = generateSingleColorBitMap(image.getSubimage(22, 0, operatorWidth,
	 * height));
	 * 
	 * // newim[3] = generateSingleColorBitMap(image.getSubimage(subWidth * 3, 0, //
	 * subWidth, height)); // // BufferedImage newim2=
	 * generateSingleColorBitMap(image.getSubimage(0, 0, width, // height)); //
	 * newim[0] = generateSingleColorBitMap(image.getSubimage(0, 0, subWidth, //
	 * height)); // System.out.println("result="+matchDigit(pix,subWidth,height));
	 * 
	 * // match.init(); //
	 * System.out.println("result="+matchHz(newim[0],hzWidth,height)); //
	 * System.out.println("result="+matchDigit(newim[1],digitWidth,height)); //
	 * System.out.println("The result is "+match.getResult()); //
	 * if(match.isDigit()) { // pos=23; // } // else { // pos=34; // } // // //
	 * match.init(); // newim[0] = generateSingleColorBitMap(image.getSubimage(pos,
	 * 0, hzWidth, // height)); // newim[1] =
	 * generateSingleColorBitMap(image.getSubimage(pos, 0, // digitWidth, height));
	 * // // System.out.println("result="+matchHz(newim[0],hzWidth,height)); //
	 * System.out.println("result="+matchDigit(newim[1],digitWidth,height)); //
	 * System.out.println("The result is "+match.getResult());
	 * 
	 * // System.out.println("DIFF="+match.getDiffer()); //
	 * System.out.println("type="+match.getType());
	 * 
	 * Match m1=match(newim[2],operatorWidth,height,Template.symbol,7); //
	 * System.out.println("The result is "+match1.getResult()+",differ="+match1.
	 * getDiffer()); Match
	 * m2=match(newim[3],operatorWidth,height,Template.symbol,7);
	 * if(m1.getDiffer()<m2.getDiffer()) { xh=m1.getPos(); } else { xh=m2.getPos();
	 * } System.out.println("m1="+m1); System.out.println("m2="+m2); return
	 * sb.toString(); }
	 */

	public static int recognize(BufferedImage image) throws Exception {

		BufferedImage newim[] = new BufferedImage[4];
		if (null == image) {
			throw new RuntimeException("image为null");
		}

		// int width = image.getWidth();
		int height = image.getHeight();
		int digitWidth = 7;
		int operatorWidth = 8;
		int equalWidth = 7;
		int hzWidth = 14;
		int hOffset = 31;
		int xh = -1;// 保存两次比较
		int num1Type = -1; // 第1个操作数类型，0为数字，1为汉字
		int num2Type = -1; // 第1个操作数类型，0为数字，1为汉字
		int num1;
		int num2 = -1;
		int operator;// 运算符，0:减号,1:加号
		
		//求操作符
		newim[0] = generateSingleColorBitMap(image.getSubimage(15, 0, operatorWidth, height)); //+
		newim[1] = generateSingleColorBitMap(image.getSubimage(22, 0, operatorWidth, height));//-

		Match m1 = match(newim[0], operatorWidth, height, Template.operator, 7);
		Match m2 = match(newim[1], operatorWidth, height, Template.operator, 7);
		
		
		if (m1.getDiffer() < m2.getDiffer()) { // 第一个操作数为数字
			operator = m1.getPos();
			num1Type = 0;
			BufferedImage img = generateSingleColorBitMap(image.getSubimage(2, 0, digitWidth, height));
			Match m = match(img, digitWidth, height, Template.digit, 6);
			num1 = m.getPos(); // 第一个操作数
		} else { // 第一个操作数为汉字
			operator = m2.getPos();
			num1Type = 1;
			BufferedImage img = generateSingleColorBitMap(image.getSubimage(2, 0, hzWidth, height));
			Match m = match(img, hzWidth, height, Template.hz, 4);
			num1 = m.getPos(); // 第一个操作数
		}
//		System.out.println("operator="+operator+",num1Type="+num1Type+",num1="+num1);
		// 求等号位置
//		int pos = 1000;
//		int differ = 10000;
//		if (operator == 0) {// +
//
//			int[] offset = { 42, 45, 49 };
//			for (int i = 0; i < offset.length; i++) {
//				BufferedImage img = generateSingleColorBitMap(image.getSubimage(offset[i], 0, equalWidth, height));
//				m1 = match(img, equalWidth, height, Template.equals, 8);
//				if (m1.getDiffer() < differ) {
//					differ = m1.getDiffer();
//					pos = i;
//				}
//			}
//			System.out.println("加法,等号位置：" + offset[pos]);
//		} else {// -
//
//			int[] offset = { 32, 38, 46 };
//			for (int i = 0; i < offset.length; i++) {
//				BufferedImage img = generateSingleColorBitMap(image.getSubimage(offset[i], 0, equalWidth, height));
//				m1 = match(img, equalWidth, height, Template.equals, 8);
//				if (m1.getDiffer() < differ) {
//					differ = m1.getDiffer();
//					pos = i;
//				}
//			}
//			System.out.println("减法,等号位置：" + offset[pos]);
//		}
		
//		int pos=1000;
//		int differ=10000;
		if(operator==0) {//+
			
			if(num1Type==0) { //操作数1为数字，加法
				newim[0] = generateSingleColorBitMap(image.getSubimage(26, 0,
						digitWidth, height));
				newim[1] = generateSingleColorBitMap(image.getSubimage(26, 0,
						hzWidth, height));
				m1=match(newim[0],digitWidth,height,Template.digit,7);
				m2=match(newim[1],hzWidth,height,Template.hz,4);
				
				if(m1.getDiffer()<m2.getDiffer()) { //第一个操作数是数字
					num2=m1.getPos();
					num2Type=0;
				}
				else { //第一个操作数是汉字
					num2=m2.getPos();
					num2Type=1;
				}
			}
			else {//操作数1为汉字，加法
				newim[0] = generateSingleColorBitMap(image.getSubimage(33, 0,
						digitWidth, height));
				newim[1] = generateSingleColorBitMap(image.getSubimage(33, 0,
						hzWidth, height));
				m1=match(newim[0],digitWidth,height,Template.digit,7);
				m2=match(newim[1],hzWidth,height,Template.hz,4);
				
				if(m1.getDiffer()<m2.getDiffer()) { //第一个操作数是数字
					num2=m1.getPos();
					num2Type=0;
				}
				else { //第一个操作数是汉字
					num2=m2.getPos();
					num2Type=1;
				}
			}			
		}
		else {//-
			
			if(num1Type==0) { //操作数1为数字，减法
				newim[0] = generateSingleColorBitMap(image.getSubimage(23, 0,
						digitWidth, height));
				newim[1] = generateSingleColorBitMap(image.getSubimage(23, 0,
						hzWidth, height));
				m1=match(newim[0],digitWidth,height,Template.digit,7);
				m2=match(newim[1],hzWidth,height,Template.hz,4);
				
				if(m1.getDiffer()<m2.getDiffer()) { //第一个操作数是数字
					num2=m1.getPos();
					num2Type=0;
				}
				else { //第一个操作数是汉字
					num2=m2.getPos();
					num2Type=1;
				}
			}
			else {//操作数1为汉字，减法
				newim[0] = generateSingleColorBitMap(image.getSubimage(30, 0,
						digitWidth, height));
				newim[1] = generateSingleColorBitMap(image.getSubimage(30, 0,
						hzWidth, height));
				m1=match(newim[0],digitWidth,height,Template.digit,7);
				m2=match(newim[1],hzWidth,height,Template.hz,4);
				
				if(m1.getDiffer()<m2.getDiffer()) { //第一个操作数是数字
					num2=m1.getPos();
					num2Type=0;
				}
				else { //第一个操作数是汉字
					num2=m2.getPos();
					num2Type=1;
				}
			}
		}
//		System.out.println("type="+num2Type+",num2=" + num2);
		if(operator==0)
			return num1+num2;
		else
			return num1-num2;
	}

	private static Match match(BufferedImage img, int width, int height, int[][] template, int offset) {
		int pos = -1;
		int differ = 100;
		int x;

		// int[] pix=removeOrphan(img,width,height);
		int[] pix = new int[width * height];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// System.out.println(j+","+width);
				pix[i * (width) + j] = img.getRGB(j, i);
				// 清边界
				if (i < 4 || i > height - 2 || j < 2 || j > width - 4)
					pix[i * (width) + j] = 0;

				if (isWhite(img.getRGB(j, i)) == 1 || i < 4 || i > height - 2)
					pix[i * (width) + j] = 0;
				else
					pix[i * (width) + j] = 1;

			}

		}

		// System.out.println("pix.length="+pix.length);

		for (int k = 0; k < template.length; k++) {
			x = 0;

			for (int i = 0; i < pix.length; i++) {
				if (i < offset * width) // 越过offset行
					continue;
				x = x + Math.abs(pix[i] - template[k][i]);
			}
			if (x == 0) {
				pos = k;
				break;
			} else if (x < differ) {
				differ = x;
				pos = k;
			}

			// System.out.println("diff="+temp+",k="+k);
		}

		return new Match(differ, pos);
	}

	private static int[] removeOrphan(BufferedImage img, int width, int height) {
		// System.out.println("-------------------------------------------------");
		int[] pix = new int[width * height];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// System.out.println(j+","+width);
				pix[i * (width) + j] = img.getRGB(j, i);
				// 清边界
				if (i < 4 || i > height - 2 || j < 2 || j > width - 4)
					pix[i * (width) + j] = 0;

				if (isWhite(img.getRGB(j, i)) == 1 || i < 4 || i > height - 2)
					pix[i * (width) + j] = 0;
				else
					pix[i * (width) + j] = 1;

			}

		}
		for (int i = 1; i < height - 1; i++)
			for (int j = 1; j < width - 1; j++) {
				int pos = i * (width) + j;
				if (pix[pos] == 1) {
					int left = pos - 1;
					int right = pos + 1;
					int top = pos - width;
					int bottom = pos + width;
					if (pix[left] == 0 && pix[right] == 0 && pix[top] == 0 && pix[bottom] == 0 && pix[top - 1] == 0
							&& pix[top + 1] == 0 && pix[bottom - 1] == 0 && pix[bottom + 1] == 0) {
						pix[pos] = 0;
					}
				}

			}
		return pix;
	}


	private static BufferedImage generateSingleColorBitMap(Image colorImage) throws IOException {
		BufferedImage image = new BufferedImage(colorImage.getWidth(null), colorImage.getHeight(null),
				BufferedImage.TYPE_BYTE_GRAY);
		Graphics g = image.getGraphics();
		g.drawImage(colorImage, 0, 0, null);
		g.dispose();
		RenderedOp ro = JAI.create("binarize", image, new Double(100));
		BufferedImage bi = ro.getAsBufferedImage();

		return bi;
	}

	static int count = 0;

	public static int isWhite(int colorInt) {
		Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() > 100) {
			return 1;
		}
		return 0;

	}

	public static BufferedImage C2(BufferedImage img) throws IOException {
		// File file = new File(picFile);
		// BufferedImage img = ImageIO.read(file);
		BufferedImage outBinary = img;
		int width = img.getWidth();
		int height = img.getHeight();
		double subWidth = (double) width / 4.0;
		for (int ii = 0; ii < 4; ii++) {

			int area = width * height;
			int gray[][] = new int[width][height];
			int u = 0;// 灰度平均值
			int graysum = 0;
			int graymean = 0;
			int grayfrontmean = 0;
			int graybackmean = 0;
			Color color;
			int pixl[][] = new int[width][height];
			int pixelsR;
			int pixelsG;
			int pixelsB;
			int pixelGray;
			int front = 0;
			int back = 0;
			for (int i = (int) (ii * subWidth); i < (ii + 1) * subWidth && i < width; i++) { // 不算边界行和列，为避免越界
				for (int j = 1; j < height; j++) {
					pixl[i][j] = img.getRGB(i, j);
					color = new Color(pixl[i][j]);
					pixelsR = color.getRed();// R空间
					pixelsB = color.getBlue();// G空间
					pixelsG = color.getGreen();// B空间
					pixelGray = (int) (0.3 * pixelsR + 0.59 * pixelsG + 0.11 * pixelsB);// 计算每个坐标点的灰度
					gray[i][j] = (pixelGray << 16) + (pixelGray << 8) + (pixelGray);
					graysum += pixelGray;

				}
			}
			graymean = (int) (graysum / area);// 整个图的灰度平均值
			u = graymean;
			for (int i = (int) (ii * subWidth); i < (ii + 1) * subWidth && i < width; i++) // 计算整个图的二值化阈值
			{
				for (int j = 0; j < height; j++) {
					if (((gray[i][j]) & (0x0000ff)) < graymean) {
						graybackmean += ((gray[i][j]) & (0x0000ff));
						back++;
					} else {
						grayfrontmean += ((gray[i][j]) & (0x0000ff));
						front++;
					}
				}
			}
			int frontvalue = (int) (grayfrontmean / front);// 前景中心
			int backvalue = (int) (graybackmean / back);// 背景中心
			float G[] = new float[frontvalue - backvalue + 1];// 方差数组
			int s = 0;
			// System.out.println(front);
			// System.out.println(frontvalue);
			// System.out.println(backvalue);
			for (int i1 = backvalue; i1 < frontvalue + 1; i1++)// 以前景中心和背景中心为区间采用大津法算法
			{
				back = 0;
				front = 0;
				grayfrontmean = 0;
				graybackmean = 0;
				for (int i = (int) (ii * subWidth); i < (ii + 1) * subWidth && i < width; i++) {
					for (int j = 0; j < height; j++) {
						if (((gray[i][j]) & (0x0000ff)) < (i1 + 1)) {
							graybackmean += ((gray[i][j]) & (0x0000ff));
							back++;
						} else {
							grayfrontmean += ((gray[i][j]) & (0x0000ff));
							front++;
						}
					}
				}
				grayfrontmean = (int) (grayfrontmean / front);
				graybackmean = (int) (graybackmean / back);
				G[s] = (((float) back / area) * (graybackmean - u) * (graybackmean - u)
						+ ((float) front / area) * (grayfrontmean - u) * (grayfrontmean - u));
				s++;
			}
			float max = G[0];
			int index = 0;
			for (int i = 1; i < frontvalue - backvalue + 1; i++) {
				if (max < G[i]) {
					max = G[i];
					index = i;
				}
			}
			// System.out.println(G[index]);
			// System.out.println(index);
			for (int i = (int) (ii * subWidth); i < (ii + 1) * subWidth && i < width; i++) {
				for (int j = 0; j < height; j++) {
					if (((gray[i][j]) & (0x0000ff)) < (index + backvalue)) {
						outBinary.setRGB(i, j, 0x000000);
					} else {
						outBinary.setRGB(i, j, 0xffffff);
					}
				}
			}

		}
		// return removeEdge(outBinary);
		return shift(removeEdge(outBinary));
	}

	public static BufferedImage removeEdge(BufferedImage img) throws IOException {
		BufferedImage outImg = img;
		int width = outImg.getWidth();
		int height = outImg.getHeight();

		for (int i = 0; i < width; i++) { // 清除边界
			for (int j = 0; j < height; j++) {
				if (i < 2 || j < 2 || i > width - 2 || j > height - 2) {
					outImg.setRGB(i, j, 0xffffff);
				}
			}
		}

		// //////////////////////
		for (int i = 1; i < width - 1; i++) {
			for (int j = 1; j < height - 1; j++) {
				if (isWhite(outImg.getRGB(i, j - 1)) == 0 && isWhite(outImg.getRGB(i, j + 1)) == 0) {// 补点
					outImg.setRGB(i, j, 1);
				}
				if (isWhite(outImg.getRGB(i - 1, j)) == 0 && isWhite(outImg.getRGB(i + 1, j)) == 0) {// 补点
					outImg.setRGB(i, j, 1);
				}

				if (isWhite(outImg.getRGB(i, j)) == 0 && isWhite(outImg.getRGB(i - 1, j)) == 1
						&& isWhite(outImg.getRGB(i + 1, j)) == 1 && isWhite(outImg.getRGB(i, j - 1)) == 1
						&& isWhite(outImg.getRGB(i, j + 1)) == 1 && isWhite(outImg.getRGB(i - 1, j - 1)) == 1
						&& isWhite(outImg.getRGB(i - 1, j + 1)) == 1 && isWhite(outImg.getRGB(i + 1, j - 1)) == 1
						&& isWhite(outImg.getRGB(i + 1, j + 1)) == 1) {// 黑点且上下左右斜方向全是白点，染成白色

					outImg.setRGB(i, j, 0xffffff);
				}
			}
		}

		return outImg;
	}

	public static BufferedImage shift(BufferedImage img) throws IOException {

		int width = img.getWidth();
		int height = img.getHeight();

		int subWidth = width / 4;

		for (int i = 0; i < 4; i++) {
			int row = 0;
			int found = 0;
			for (int x = (i * subWidth); x < (i + 1) * subWidth && x < width && found == 0; ++x) { // 纵向扫描，查找第一个1所在的行号，记录在row中
				for (int y = 0; y < height; ++y) {
					if (isWhite(img.getRGB(x, y)) == 0) {
						row = x - (i * subWidth);
						found = 1;
						break;
					}
				}

			}

			for (int y = 0; y < height; ++y) {
				for (int x = (int) (i * subWidth); x < (i + 1) * subWidth && x < width; ++x) {
					int rgb = 0xffffff;
					if (x + row < (i + 1) * subWidth && x + row < width)
						rgb = img.getRGB(x + row, y);

					img.setRGB(x, y, rgb);
				}
			}

		}

		// print(img);
		return img;
	}

	private static void print(BufferedImage img) {
		int width = img.getWidth();
		int height = img.getHeight();

		int subWidth = width / 4;
		for (int i = 0; i < 4; i++) {
			for (int y = 0; y < height; ++y) {
				for (int x = (int) (i * subWidth); x < (i + 1) * subWidth && x < width; ++x) {
					if (isWhite(img.getRGB(x, y)) == 1)
						System.out.print("0,");
					else
						System.out.print("1,");

					count++;
				}
				System.out.println();
			}
			System.out.println("--------------------------------------");
		}
	}

	public static void printSymbol() {
		System.out.println(Template.hz.length);
		for (int i = 0; i < Template.operator.length; i++) {
			for (int j = 0; j < 160; j++) {
				if (j % 8 == 0)
					System.out.println();
				if (Template.operator[i][j] == 0)
					System.out.print(" ");
				else
					System.out.print(Template.operator[i][j]);
			}
			System.out.println("-----------------------=" + i);
		}
	}

	public static void main(String args[]) throws Exception {
		String filename="jpg/1/5.jpg";
		System.out.println("filename="+filename);
		File file = new File(filename);
		BufferedImage img = ImageIO.read(file);
		int result = recognize(img);
		System.out.println();
		System.out.println("recognize result:" + result);

	}
}