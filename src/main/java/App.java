import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Panel;

import javax.swing.DropMode;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;

/**
 * token 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
 * @author safavieh
 *
 */
public class App {

	private JFrame frame;
	private JPanel panel;
	private static JTextArea inputTextArea;
	private static JLabel topLabel;
	private static JLabel bottomLabel;
	private static JButton btnCheck;
	private static JButton btnRun;
	private static JScrollPane textAreaPane;
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
		try {
    		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	} catch (Throwable ex) {
    		ex.printStackTrace();
    	}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 227);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		topLabel = new JLabel("List out desings or item IDs to check which customers have them built out.");
		frame.getContentPane().add(topLabel);
		
		Panel panel_1 = new Panel();
		frame.getContentPane().add(panel_1);
		textAreaPane = new JScrollPane();
		
		JTextArea txtrListOfDesings = new JTextArea();
		txtrListOfDesings.setDropMode(DropMode.INSERT);
		txtrListOfDesings.setFont(new Font("Calibri Light", Font.PLAIN, 13));
		txtrListOfDesings.setText("List of desings or item IDs separated by a comma (,).\r\n");
		txtrListOfDesings.setWrapStyleWord(true);
		txtrListOfDesings.setLineWrap(true);
		
		btnRun = new JButton("Run");
		bottomLabel = new JLabel("separate by comma (,)");
		JButton btnImport = new JButton("Import");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(txtrListOfDesings, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textAreaPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnRun, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnImport, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)))
						.addComponent(bottomLabel, GroupLayout.PREFERRED_SIZE, 252, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(textAreaPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(txtrListOfDesings, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(btnRun)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnImport)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(bottomLabel)
					.addContainerGap(221, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		
		panel = new JPanel();
		inputTextArea = new JTextArea();
		topLabel = new JLabel("List out designs or itemIDs");
		bottomLabel = new JLabel("separate each style or item IDs by comma \",\" ");
		btnCheck = new JButton("Run");
		
		middlePanel = new JPanel();
		reloadBtn = new JButton("Reload");
	}
}
