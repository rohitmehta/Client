import java.awt.Robot;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;


public class BlockingKeysThread extends Thread {

	private static boolean working = true;
	
	private JFrame frame;
	
	public BlockingKeysThread(JFrame frame){
		this.frame = frame;
	}
	
	@Override
	public void run() {
		try {
			Robot robot = new Robot();
			while (working) {
				robot.keyRelease(KeyEvent.VK_ALT);
				robot.keyRelease(KeyEvent.VK_TAB);
				frame.requestFocus();
				Thread.sleep(10);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isWorking() {
		return working;
	}

	public static void setWorking(boolean working) {
		BlockingKeysThread.working = working;
	}
	
	


}
