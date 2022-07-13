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
	private static JButton btnImport;
	private static JButton btnRun;
	private static JButton btnSource;
	private static JScrollPane textAreaPane;
	private JPanel middlePanel;
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
		frame = new JFrame("Designs Build Checker");
		frame.setBounds(100, 100, 450, 227);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		topLabel = new JLabel("List out desings or item IDs to check which customers have them built out.");
		frame.getContentPane().add(topLabel);
		
		Panel panel_1 = new Panel();
		frame.getContentPane().add(panel_1);
		textAreaPane = new JScrollPane();
		JTextArea textArea = new JTextArea();
		textArea.setDropMode(DropMode.INSERT);
		textArea.setFont(new Font("Calibri Light", Font.PLAIN, 13));
		textArea.setText("List of desings or item IDs separated by a comma (,).\r\n");
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		
		btnSource = new JButton("Source");
		btnRun = new JButton("Run");
		btnRun.setToolTipText("Tooltip");
		btnImport = new JButton("Import");
		
		JLabel lblNewLabel = new JLabel("separate by comma (,)");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(textAreaPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 333, Short.MAX_VALUE)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(btnSource, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnRun, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnImport, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)))
						.addComponent(lblNewLabel))
					.addGap(341))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(textAreaPane, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(btnSource)
							.addGap(4)
							.addComponent(btnRun)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnImport))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel)))
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		
		panel = new JPanel();
		inputTextArea = new JTextArea();
		topLabel = new JLabel("List out designs or itemIDs");
		bottomLabel = new JLabel("separate each style or item IDs by comma \",\" ");
		btnImport = new JButton("Run");
		
		middlePanel = new JPanel();
	}
}
