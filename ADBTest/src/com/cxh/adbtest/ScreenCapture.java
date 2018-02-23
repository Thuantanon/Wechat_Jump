package com.cxh.adbtest;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;

import javax.swing.ImageIcon;

public class ScreenCapture implements ICmdParams {

	/**
	 *  ��ȡʵʱ����Ļ���ݣ������д�ļ���Ч������
	 * @return
	 */
	public static Image getImageFromDevice() {
		ImageIcon icon = null;
		try {

			String adbCmd = "adb shell screencap -p";
			Process process = Runtime.getRuntime().exec(adbCmd);
			BufferedInputStream inputStream = new BufferedInputStream(process.getInputStream());
			ByteArrayOutputStream bStream = new ByteArrayOutputStream();
			// 4M����
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
	 * ���ַ�ʽ��ȡ���ֽ���������ABD������ӵ�0D�ַ�����Ҫ�Խ������һ�£��������е�0D0D0A ת���� 0A��ע�⣺����ת��ֻ������Windowsϵͳ��
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