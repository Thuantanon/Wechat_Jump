package com.cxh.adbtest;

public interface ICmdParams {
	
	public static final String SCREEN_SHOT_IMAGE = "D:\\screenshot.png";
	
	
	// 安卓版本
	public static final String CMD_DEVICE_LEVEL = "adb shell getprop ro.build.version.release";
	
	// 获取屏幕大小
	public static final String CMD_SCREEN_SIZE = "adb shell wm size";
	
	// 获取所有设备型号
	public static final String CMD_DEVICE_INFO = "adb shell getprop ro.product.model";
	
	// 模拟长按事件
	public static final String CMD_SCREEN_PRESS = "adb shell input swipe ";
	
	// 截屏到手机内存卡
	public static final String CMD_SCREENSHOT_TO_PHONE = "adb shell /system/bin/screencap -p /sdcard/screenshot.png";
	// 拉取截屏到电脑
	public static final String CMD_SCREENSHOT_TO_PC = "adb pull /sdcard/screenshot.png " + SCREEN_SHOT_IMAGE;
	
	
	
}
