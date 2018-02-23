package com.cxh.adbtest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame implements MouseListener, ICmdParams {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean isValid = false;
	private boolean isRuning = true;
	private double pressX = 0;
	private double pressY = 0;

	private int width;
	private int height;

	private MyPanel myPanel;

	private int SCREENT_OFFSET_W = 8;
	private int SCREENT_OFFSET_TITLE = 30;
	private int SCREENT_OFFSET_H = 10;

	public MainFrame(int w, int h) throws HeadlessException {
		// TODO Auto-generated constructor stub
		this("默认", w, h);
	}

	public MainFrame(String title, int w, int h) {
		width = w;
		height = h;
		setTitle(title);
		// 这里要除去窗口偏移量
		setSize(width + 2 * SCREENT_OFFSET_W, height + SCREENT_OFFSET_TITLE + SCREENT_OFFSET_H);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(300, 200);
		setBackground(Color.BLACK);

		myPanel = new MyPanel();
		myPanel.addMouseListener(this);
		setLayout(new BorderLayout());
		add(myPanel, BorderLayout.CENTER);

		// 如果没有文件，先创建缓存文件
		File file = new File(SCREEN_SHOT_IMAGE);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 让线程不停的截图显示画面
		mThread.start();
	}

	private Thread mThread = new Thread(new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (isRuning) {
				// 截图
				try {

					Process process = Runtime.getRuntime().exec(CMD_SCREENSHOT_TO_PHONE);
					process.waitFor();
					process = Runtime.getRuntime().exec(CMD_SCREENSHOT_TO_PC);
					process.waitFor();

					// 显示截屏图片
					myPanel.repaint();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	});

	/**
	 * 画出屏幕
	 * 
	 * @author Administrator
	 *
	 */
	private class MyPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MyPanel() {
			// TODO Auto-generated constructor stub
			setBounds(0, 0, width, height);
		}

		@Override
		public void paint(Graphics g) {
			// TODO Auto-generated method stub
			super.paint(g);
			g.setColor(Color.black);
			g.fillRect(0, 0, width, height);
			// g.drawImage(ScreenCapture.getImageFromDevice(), 0, 0, width,
			// height, null);
			File file = new File(SCREEN_SHOT_IMAGE);
			if (file.exists()) {
				try {
					FileInputStream in = new FileInputStream(file);
					byte[] data = new byte[1024 * 1024 * 4];
					in.read(data);
					in.close();
					ImageIcon icon = new ImageIcon(data);
					System.out.println("hashCode : " + icon.hashCode());
					g.drawImage(icon.getImage(), 0, 0, width, height, null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
		isRuning = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int clickX = e.getX() * 2;
		int clickY = e.getY() * 2;
		String adbShell = "adb shell input tap " + clickX + " " + clickY;
		try {
			Runtime.getRuntime().exec(adbShell);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("mousePressed : " + e.getX() + " , " + e.getY());
		if (isValid) {
			pressX = e.getX() * 2;
			pressY = e.getY() * 2;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("mouseReleased : " + e.getX() + " , " + e.getY());
		double cuurX = e.getX() * 2;
		double cuurY = e.getY() * 2;
		if (isValid) {
			double dis = getDistance(pressX, pressY, cuurX, cuurY);
			System.out.println("距离 : " + dis);
			if (dis > 0) {

				int time = (int) (dis * 2.0);
				String adbShell = CMD_SCREEN_PRESS + width / 2 + " " + height / 2 + " " + width / 2 + " " + height / 2
						+ " " + time;
				try {
					Runtime.getRuntime().exec(adbShell);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * 求距离
	 * 
	 * @return
	 */
	private double getDistance(double x1, double y1, double x2, double y2) {
		double f = (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
		return Math.sqrt(f);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		isValid = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		isValid = false;
	}
}
