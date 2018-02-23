package com.cxh.adbtest;

public class ADBMain implements ICmdParams {

	public ADBMain() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// ��ȡ����̨����

		StreamCaptureThread errorStream = null;
		StreamCaptureThread outputStream = null;
		Process process = null;
		String title = "";
		
		try {

			// �豸��
			process = Runtime.getRuntime().exec(CMD_DEVICE_INFO);
			outputStream = new StreamCaptureThread(process.getInputStream());
			new Thread(outputStream).start();
			process.waitFor();
			
			title += outputStream.output.toString();
			
			// �豸Android�汾
			process = Runtime.getRuntime().exec(CMD_DEVICE_LEVEL);
			outputStream = new StreamCaptureThread(process.getInputStream());
			new Thread(outputStream).start();
			process.waitFor();
			
			title +=  " - " + outputStream.output.toString();
			
			// �豸��Ļ��С
			process = Runtime.getRuntime().exec(CMD_SCREEN_SIZE);
			errorStream = new StreamCaptureThread(process.getErrorStream());
			outputStream = new StreamCaptureThread(process.getInputStream());
			new Thread(errorStream).start();
			new Thread(outputStream).start();
			process.waitFor();

			String error = errorStream.output.toString();
			String success = outputStream.output.toString();
			if (null != success && success.length() > 0) {

				// ������Ļ���
				if (success.indexOf(":") > 0) {
					success = success.substring(success.indexOf(":") + 1);
					success = success.trim();
						String size[] = success.split("x");
					if(size.length == 2){
						int width = Integer.parseInt(size[0]) / 2;
						int height = Integer.parseInt(size[1]) / 2;
						MainFrame frame = new MainFrame(title, width, height);
						frame.setVisible(true);
					}
				}else{
					System.out.println("��ȡ�豸��Ϣʧ��");
				}

			} else {
				System.out.println(error);
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		
	}

}
