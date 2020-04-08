package com.mini.studyservice.core.util.image;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.util.StringUtils;

public class ImageUtils {
	public static void createThumbnail(String load, String save, String type, int w, int h) {
		try {
			BufferedInputStream stream_file = new BufferedInputStream(new FileInputStream(load));
			createThumbnail(stream_file, save, type, w, h);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static void createThumbnail(BufferedInputStream stream_file,String save, String type, int w, int h) {
		getImageThumbnail(stream_file, save, type, w, h);
//		if(StringUtils.pathEquals(type.toLowerCase(), "gif")) {
//			
//		} else {
//			getImageThumbnail(stream_file, save, type, w, h);
//		}
	}
	public static void getImageThumbnail(BufferedInputStream stream_file, String save, String type, int w, int h) {
		
		try {
			File file = new File(save);
			BufferedImage bi = ImageIO.read(stream_file);
			int width = bi.getWidth();
			int height = bi.getHeight();
			if(w<width)width = w;
			if(h<height)height = h;
			
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			
			Image img = bi.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
			
			Graphics2D g2D = bufferedImage.createGraphics();
			g2D.drawImage(img, 0, 0, width, height, null);
			ImageIO.write(bufferedImage, type, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void getGitImageThumnail(BufferedInputStream stream_file, String save, String type, int w, int h ) {
		
	}
}
