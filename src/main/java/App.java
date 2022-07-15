import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;

import javax.swing.DropMode;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * token 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
 * @author diannerobbi
 *
 */
public class App {

	private JFrame frame;
	private static JLabel topLabel;
	private static JLabel bottomLabel;
	private static JButton btnRun;
	private static JButton btnImport;
	private static JButton btnSource;
	private static JScrollPane textAreaPane;
	private static JTextArea textArea;
	private static String DESTINATION_PATH;
	final static String DESKTOP_PATH = System.getProperty("user.home") + "\\Desktop\\";
	final static String FILE_EXTENSION = ".xlsx";
	private JLabel lblStatus;
	
	private static XSSFSheet sheet;
	private static XSSFWorkbook workbook;

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
		
		topLabel = new JLabel("List out designs or item IDs to check which customers have them built out.");
		frame.getContentPane().add(topLabel);
		
		Panel panel_1 = new Panel();
		frame.getContentPane().add(panel_1);
		textAreaPane = new JScrollPane();
		textArea = new JTextArea();
		textArea.setDropMode(DropMode.INSERT);
		textArea.setFont(new Font("Calibri Light", Font.PLAIN, 13));
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		
		btnSource = new JButton("Source");
		btnSource.setToolTipText("Choose master sheet of item IDs (might be labeled 'Customer Builds.xlsx').");
		btnImport = new JButton("Import");
		btnImport.setToolTipText("Import a textfile with the list of designs and/or item IDs for checking.");
		btnRun = new JButton("Run");
		btnRun.setToolTipText("Process the input list of designs and/or item IDs.");
		btnRun.setEnabled(false);
		
		bottomLabel = new JLabel("Separate IDs from each other by a comma (,).");
		bottomLabel.setFont(new Font("Calibri Light", Font.PLAIN, 12));
		
		lblStatus = new JLabel("");
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
								.addComponent(btnImport, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
								.addComponent(lblStatus, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
								.addGroup(Alignment.TRAILING, gl_panel_1.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(btnSource, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
									.addComponent(btnRun, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))))
						.addComponent(bottomLabel))
					.addGap(341))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(textAreaPane, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addComponent(btnSource)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnImport)
									.addGap(4)
									.addComponent(btnRun)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblStatus, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addComponent(textArea, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(bottomLabel)))
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		
		btnSource.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileFilter() {
	            	@Override
	            	public boolean accept(File f) {
	            		return f.isDirectory() || f.getName().toLowerCase().endsWith(".xlsx");
	            	}
	            	public String getDescription() {
	            		return "*.xlsx files";
	            	}
	            });
				int response = fileChooser.showOpenDialog(null);
				if (response == JFileChooser.APPROVE_OPTION) {
					File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
					try {
						if(file != null) {
							BufferedReader br = new BufferedReader(new FileReader(file));
							// read in the source
							FileInputStream fis = new FileInputStream(file);
							workbook = new XSSFWorkbook(fis);
							sheet = workbook.getSheetAt(0);
							lblStatus.setText("Source selected: "+file.getAbsolutePath());
						}
					} catch (Exception err) {
						err.getStackTrace();
					}
				}
			}
		});
		
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		textArea.addFocusListener(new FocusAdapter() {
			@Override
		    public void focusLost(FocusEvent arg0) {
				out("focused?" + arg0);
		    }
		});
			
	}
	
	public static void out(String stringToPrint) {
		System.out.println(stringToPrint);
	}
}
