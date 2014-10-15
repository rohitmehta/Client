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

	/// TODO : Close the thread when window loses focus, so that Alt + Tab should work after that.
	// TODO : Timer thread should span & notify again the main thread to bring in front.
	
	private static final long serialVersionUID = 6370399237714983851L;
	public JFrame frame;
	public static BlockingKeysThread blockingKeysThread;
	
	public ClientInterface() {
		
		super(new GridBagLayout());

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
		// 1. Create the frame.
		frame = new JFrame("Client");

		// 2. Optional: What happens when the frame closes?
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		// frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setResizable(false);
		frame.setUndecorated(true);
		frame.add(this);
		// 5. Show it.
		frame.setVisible(true);
		if(blockingKeysThread == null || !blockingKeysThread.isAlive()){
			System.out.println("---- Blockign Keys Thread enabled-----");
			blockingKeysThread = new BlockingKeysThread(frame);
			blockingKeysThread.start();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("close".equalsIgnoreCase(e.getActionCommand())) {
			frame.setState(JFrame.ICONIFIED);
			BlockingKeysThread.setWorking(false);
		}
	}

}
