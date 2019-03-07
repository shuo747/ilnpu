package com.shuo747.ilnpu.utils.rc;

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


public class RecognizeCode {

	static {
		System.setProperty("com.sun.media.jai.disableMediaLib", "true");
	}

	// 数字模板 0-9
	static int[][] value = {
			// num 0;
			{ 	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,
				0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,
				0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,
				1,1,0,0,0,1,1,0,0,0,0,0,0,0,0,
				1,1,0,0,0,1,1,0,0,0,0,0,0,0,0,
				1,1,0,0,0,1,1,0,0,0,0,0,0,0,0,
				1,1,0,0,0,1,1,0,0,0,0,0,0,0,0,
				1,1,0,0,0,1,1,0,0,0,0,0,0,0,0,
				1,1,0,0,0,1,1,0,0,0,0,0,0,0,0,
				0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,
				0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,
				0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
			// num 1
				{ 	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
					0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
					0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
					0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
					0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,
					1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,
					0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,
					0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,
					0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,
					0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,
					0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,
					0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,
					0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,
					0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,
					0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,
					1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,
					0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
					0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
					0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
					0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
			// num2
					{ 	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,
						0,1,0,0,1,1,1,0,0,0,0,0,0,0,0,
						1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,
						0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,
						0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,
						0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,
						0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,
						0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,
						0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,
						0,0,1,1,0,0,0,1,0,0,0,0,0,0,0,
						0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,
						0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
			// num3
					{ 	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,
						0,1,0,0,0,1,1,0,0,0,0,0,0,0,0,
						1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,
						0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,
						0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,
						0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,
						0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,
						0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,
						0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,
						0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,
						1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,
						1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
			// num4
					{ 	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,
						0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,
						0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,
						0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,
						0,0,1,0,0,1,1,0,0,0,0,0,0,0,0,
						0,0,1,0,0,1,1,0,0,0,0,0,0,0,0,
						0,1,0,0,0,1,1,0,0,0,0,0,0,0,0,
						1,1,0,0,0,1,1,0,0,0,0,0,0,0,0,
						1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,
						0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,
						0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,
						0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
			// num5
					{ 	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,
						0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,
						0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,
						0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,
						1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,
						1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,
						1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
			// num6
					{   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,
						0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,
						0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,
						0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,
						0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,
						1,1,1,0,0,1,1,0,0,0,0,0,0,0,0,
						1,1,0,0,0,0,1,1,0,0,0,0,0,0,0,
						1,1,0,0,0,0,1,1,0,0,0,0,0,0,0,
						1,1,0,0,0,0,1,1,0,0,0,0,0,0,0,
						1,1,0,0,0,0,1,1,0,0,0,0,0,0,0,
						0,1,1,0,0,1,1,0,0,0,0,0,0,0,0,
						0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
			// num7
					{ 	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,
						0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,
						1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,
						0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,
						0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,
						0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,
						0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
			// num8
					{ 	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,
						0,1,1,0,0,0,1,1,0,0,0,0,0,0,0,
						1,1,0,0,0,0,1,1,0,0,0,0,0,0,0,
						1,1,0,0,0,0,1,1,0,0,0,0,0,0,0,
						0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,
						0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,
						0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,
						0,1,0,0,0,1,1,0,0,0,0,0,0,0,0,
						1,1,0,0,0,0,1,1,0,0,0,0,0,0,0,
						1,1,0,0,0,0,1,1,0,0,0,0,0,0,0,
						0,1,1,0,0,1,1,0,0,0,0,0,0,0,0,
						0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
			// num9
					{ 	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,
						0,1,1,0,0,1,1,0,0,0,0,0,0,0,0,
						1,1,0,0,0,0,1,1,0,0,0,0,0,0,0,
						1,1,0,0,0,0,1,1,0,0,0,0,0,0,0,
						1,1,0,0,0,0,1,1,0,0,0,0,0,0,0,
						1,1,0,0,0,0,1,1,0,0,0,0,0,0,0,
						0,1,1,0,0,0,1,1,0,0,0,0,0,0,0,
						0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,
						0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,
						0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,
						0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,
						1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 } };

	public static String recognize(byte[] byteArray) throws Exception {
		InputStream is = new ByteArrayInputStream(byteArray);
		BufferedImage image = ImageIO.read(is);
		return recognize(image);
	}

	public static String recognize(BufferedImage image) throws Exception {
		image=C2(image);
//		ImageIO.write(image, "jpg",new File("d:/10.jpg"));
		
		StringBuffer sb = new StringBuffer("");
		BufferedImage newim[] = new BufferedImage[4];
		if (null == image) {
			throw new RuntimeException("image为null");
		}
		// 将图像分成四块，因为要处理的文件有四个数字。
		int width = image.getWidth();
		int height = image.getHeight();
		int subWidth = width / 4;
		newim[0] = generateSingleColorBitMap(image.getSubimage(0, 0, subWidth,
				height));
		newim[1] = generateSingleColorBitMap(image.getSubimage(subWidth, 0,
				subWidth, height));
		newim[2] = generateSingleColorBitMap(image.getSubimage(subWidth * 2, 0,
				subWidth, height));
		newim[3] = generateSingleColorBitMap(image.getSubimage(subWidth * 3, 0,
				subWidth, height));

		for (int k = 0; k < 4; k++) {
			int iw =subWidth;
			int ih =height;
			int[] pix = new int[iw * ih];
			for (int i = 0; i < ih; i++) {
				for (int j = 0; j < iw; j++) {
					pix[i * (iw) + j] = newim[k].getRGB(j, i);
					if (isWhite(newim[k].getRGB(j, i))==1)
						pix[i * (iw) + j] = 0;
					else
						pix[i * (iw) + j] = 1;
					
//					System.out.print(pix[i * (iw) + j]+",");
				}
				
//				System.out.println();
			}
			
//			System.out.println("=============================");
			int r = getMatchNum(pix);
			
			sb.append(r);
		}
		return sb.toString();
	}


	
	private static int getMatchNum(int[] pix) {
		int result = -1;
		int temp = 100;
		int x;
		// System.out.println("pix.length="+pix.length);
		for (int k = 0; k <= 9; k++) {
			x = 0;

			for (int i = 0; i < pix.length; i++) {
				x = x + Math.abs(pix[i] - value[k][i]);
			}
			if (x == 0) {
				result = k;
				break;
			} else if (x < temp) {
				temp = x;
				result = k;
			}
		}
		return result;
	}

	private static BufferedImage generateSingleColorBitMap(Image colorImage)
			throws IOException {
		BufferedImage image = new BufferedImage(colorImage.getWidth(null),
				colorImage.getHeight(null), BufferedImage.TYPE_BYTE_GRAY);
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
//		File file = new File(picFile);
//		BufferedImage img = ImageIO.read(file);
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
			for (int i = (int) (ii * subWidth); i < (ii + 1) * subWidth
					&& i < width; i++) { // 不算边界行和列，为避免越界
				for (int j = 1; j < height; j++) {
					pixl[i][j] = img.getRGB(i, j);
					color = new Color(pixl[i][j]);
					pixelsR = color.getRed();// R空间
					pixelsB = color.getBlue();// G空间
					pixelsG = color.getGreen();// B空间
					pixelGray = (int) (0.3 * pixelsR + 0.59 * pixelsG + 0.11 * pixelsB);// 计算每个坐标点的灰度
					gray[i][j] = (pixelGray << 16) + (pixelGray << 8)
							+ (pixelGray);
					graysum += pixelGray;

				}
			}
			graymean = (int) (graysum / area);// 整个图的灰度平均值
			u = graymean;
			for (int i = (int) (ii * subWidth); i < (ii + 1) * subWidth
					&& i < width; i++) // 计算整个图的二值化阈值
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
				for (int i = (int) (ii * subWidth); i < (ii + 1) * subWidth
						&& i < width; i++) {
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
				G[s] = (((float) back / area) * (graybackmean - u)
						* (graybackmean - u) + ((float) front / area)
						* (grayfrontmean - u) * (grayfrontmean - u));
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
			for (int i = (int) (ii * subWidth); i < (ii + 1) * subWidth
					&& i < width; i++) {
				for (int j = 0; j < height; j++) {
					if (((gray[i][j]) & (0x0000ff)) < (index + backvalue)) {
						outBinary.setRGB(i, j, 0x000000);
					} else {
						outBinary.setRGB(i, j, 0xffffff);
					}
				}
			}

		}
//		return removeEdge(outBinary);
		return shift(removeEdge(outBinary));
	}

	public static BufferedImage removeEdge(BufferedImage img)
			throws IOException {
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
				if (isWhite(outImg.getRGB(i, j - 1)) == 0
						&& isWhite(outImg.getRGB(i, j + 1)) == 0) {// 补点
					outImg.setRGB(i, j, 1);
				}
				if (isWhite(outImg.getRGB(i - 1, j)) == 0
						&& isWhite(outImg.getRGB(i + 1, j)) == 0) {// 补点
					outImg.setRGB(i, j, 1);
				}

				
				if (isWhite(outImg.getRGB(i, j)) == 0
						&& isWhite(outImg.getRGB(i - 1, j)) == 1
						&& isWhite(outImg.getRGB(i + 1, j)) == 1
						&& isWhite(outImg.getRGB(i, j - 1)) == 1
						&& isWhite(outImg.getRGB(i, j + 1)) == 1
						&& isWhite(outImg.getRGB(i - 1, j - 1)) == 1
						&& isWhite(outImg.getRGB(i - 1, j + 1)) == 1
						&& isWhite(outImg.getRGB(i + 1, j - 1)) == 1
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
			int found=0;
			for (int x = (i * subWidth); x < (i + 1) * subWidth
					&& x < width && found == 0; ++x) { // 纵向扫描，查找第一个1所在的行号，记录在row中
				for (int y = 0; y < height; ++y) {
					if (isWhite(img.getRGB(x, y)) == 0) {
						row = x-(i * subWidth);
						found=1;
						break;
					}
				}

			}

			for (int y = 0; y < height; ++y) {
				for (int x = (int) (i * subWidth); x < (i + 1) * subWidth
						&& x < width; ++x) {
					int rgb=0xffffff;
					if(x+row<(i + 1) * subWidth	&& x+row < width)
						rgb=img.getRGB(x+row, y);
					
					img.setRGB(x, y, rgb);
				}
			}

		}

//		print(img);
		return img;
	}

	private static void print(BufferedImage img){
		int width = img.getWidth();
		int height = img.getHeight();

		int subWidth = width / 4;
		for (int i = 0; i < 4; i++) {
			for (int y = 0; y < height; ++y) {
				for (int x = (int) (i * subWidth); x < (i + 1) * subWidth
						&& x < width; ++x) {
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

		// ///////////////////////////////////
//		ImageIO.write(img, "jpg", new File("d:/8.jpg"));
//
//		BufferedImage img1 = new BufferedImage(15, 20,
//				BufferedImage.TYPE_3BYTE_BGR);
//		for (int i = 0; i < 15; i++)
//			for (int j = 0; j < 20; j++) {
//				img1.setRGB(i, j, img.getRGB(i + 15, j));
//			}
//		ImageIO.write(img1, "jpg", new File("d:/7.jpg"));
	}

}