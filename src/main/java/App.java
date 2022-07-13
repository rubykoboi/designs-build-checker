import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class App {

	private JFrame frame;
	private JPanel panel;
	private static JTextArea inputTextArea;
	private static JLabel topLabel;
	private static JLabel bottomSubtitle;
	private static JButton btnCheck;
	
	private JPanel middlePanel;
	private static JButton reloadBtn;
	private static String DESTINATION_PATH;
	final static String DESKTOP_PATH = System.getProperty("user.home") + "\\Desktop\\";
	final static String FILE_EXTENSION = ".xlsx";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		inputTextArea = new JTextArea();
		topLabel = new JLabel("List out designs or itemIDs");
		bottomSubtitle = new JLabel("separate each style or item IDs by comma \",\" ");
		btnCheck = new JButton("Run");
		
		middlePanel = new JPanel();
		reloadBtn = new JButton("Reload");
	}
}
