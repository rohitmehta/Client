import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientInterface extends JPanel implements ActionListener {

	private static final long serialVersionUID = 6370399237714983851L;
	public JFrame frame;
	public static BlockingKeysThread blockingKeysThread;

	public ClientInterface() {

		super(new GridBagLayout());
		prepareUI();

		// Create Frame to display UI & block the navigations.
		frame = new JFrame("Client");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		frame.setResizable(false);
		frame.setUndecorated(true);
		frame.add(this);
		frame.setVisible(true);
		startBlockingThread();
	}

	/**
	 * This method captures the action events from UI Components & then perform
	 * the corresponding actions.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("close".equalsIgnoreCase(e.getActionCommand())) {
			frame.setState(JFrame.ICONIFIED);
			BlockingKeysThread.setWorking(false);
			try {
				Thread.sleep(10000);
				frame.setState(JFrame.NORMAL);
				BlockingKeysThread.setWorking(true);
				startBlockingThread();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

		}
	}

	/**
	 * this method prepares the UI for the first blocking screen.
	 */
	private void prepareUI() {

		JTextField textField = new JTextField(20);
		textField.addActionListener(this);

		JButton button = new JButton("Close");
		button.addActionListener(this);
		button.setActionCommand("close");

		// Add Components to this panel.
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;

		c.fill = GridBagConstraints.HORIZONTAL;
		add(textField, c);

		add(button, c);
	}

	/**
	 * This method will create a new blocking thread which will not allow
	 * alt+tab to work to navigate to other window.
	 */
	private void startBlockingThread() {
		if (blockingKeysThread == null || !blockingKeysThread.isAlive()) {
			System.out.println("---- Blockign Keys Thread enabled-----");
			blockingKeysThread = new BlockingKeysThread(frame);
			blockingKeysThread.start();
		}
	}

}
