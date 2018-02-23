package com.cxh.adbtest;

public interface ICmdParams {
	
	public static final String SCREEN_SHOT_IMAGE = "D:\\screenshot.png";
	
	
	// ��׿�汾
	public static final String CMD_DEVICE_LEVEL = "adb shell getprop ro.build.version.release";
	
	// ��ȡ��Ļ��С
	public static final String CMD_SCREEN_SIZE = "adb shell wm size";
	
	// ��ȡ�����豸�ͺ�
	public static final String CMD_DEVICE_INFO = "adb shell getprop ro.product.model";
	
	// ģ�ⳤ���¼�
	public static final String CMD_SCREEN_PRESS = "adb shell input swipe ";
	
	// �������ֻ��ڴ濨
	public static final String CMD_SCREENSHOT_TO_PHONE = "adb shell /system/bin/screencap -p /sdcard/screenshot.png";
	// ��ȡ����������
	public static final String CMD_SCREENSHOT_TO_PC = "adb pull /sdcard/screenshot.png " + SCREEN_SHOT_IMAGE;
	
	
	
}
