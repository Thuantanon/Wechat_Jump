package com.cxh.adbtest;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;

import javax.swing.ImageIcon;

public class ScreenCapture implements ICmdParams {

	/**
	 *  获取实时的屏幕数据，避免读写文件的效率问题
	 * @return
	 */
	public static Image getImageFromDevice() {
		ImageIcon icon = null;
		try {

			String adbCmd = "adb shell screencap -p";
			Process process = Runtime.getRuntime().exec(adbCmd);
			BufferedInputStream inputStream = new BufferedInputStream(process.getInputStream());
			ByteArrayOutputStream bStream = new ByteArrayOutputStream();
			// 4M数据
			byte[] data = new byte[1024 * 1024 * 4];
			int len = inputStream.read(data);
			while(len != -1)
			{
				bStream.write(clearExtra(data, len));
				len = inputStream.read(data);
			}
			inputStream.close();
			icon = new ImageIcon(bStream.toByteArray());
			bStream.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return icon.getImage();
	}
	
	
	/*
	 * 这种方式读取的字节码里面有ABD额外添加的0D字符，需要对结果处理一下，即把所有的0D0D0A 转换成 0A。注意：这种转换只适用于Windows系统。
	 */
	private static byte[] clearExtra(byte[] data, int len)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for(int i=0; i < len; i++)
		{
			if(data[i] == 0x0d)
			{
				continue;
			}
			baos.write(data[i]);
		}
		return baos.toByteArray();
	}
}