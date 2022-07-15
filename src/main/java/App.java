import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;

import javax.swing.BoxLayout;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
	private JTextArea lblStatus;
	private static JPanel buttonsPanel;
	private static JPanel mainPanel;
	private static JPanel actionPanel;
	private static JPanel bigPanel;
	private static XSSFSheet sheet;
	private static XSSFWorkbook workbook;
	private static App window;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new App();
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
		frame.setBounds(100, 100, 500, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		
		// organize buttons in a Grid
		buttonsPanel = new JPanel(new GridLayout(3,1,10,12));
		buttonsPanel.setBackground(Color.blue);
		btnSource = new JButton("Source");
		btnSource.setToolTipText("Choose master sheet of item IDs (might be labeled 'Customer Builds.xlsx').");
		btnImport = new JButton("Import");
		btnImport.setToolTipText("Import a textfile with the list of designs and/or item IDs for checking.");
		btnRun = new JButton("Run");
		btnRun.setToolTipText("Process the input list of designs and/or item IDs.");
		btnRun.setEnabled(false);
		
		buttonsPanel.setSize(250,25);
		buttonsPanel.add(btnSource);
		buttonsPanel.add(btnImport);
		buttonsPanel.add(btnRun);

		// set source area
		lblStatus = new JTextArea();
		lblStatus.setSize(250, 250);
		lblStatus.setEditable(false);
		lblStatus.setBackground(SystemColor.menu);
		lblStatus.setFont(new Font("Arial", Font.PLAIN, 10));
		lblStatus.setLineWrap(true);
		
		// set buttons and area in a grid
		actionPanel = new JPanel(new GridLayout(2, 1, 10, 10));
		actionPanel.setBackground(Color.pink);
		actionPanel.add(buttonsPanel);
		actionPanel.add(lblStatus);
		
		// set text area for input
		textAreaPane = new JScrollPane();
		textAreaPane.setSize(600,600);
		textArea = new JTextArea(10,30);
		
		textArea.setDropMode(DropMode.INSERT);
		textArea.setFont(new Font("Calibri Light", Font.PLAIN, 13));
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setBackground(Color.yellow);
//		textAreaPane.add(textArea);
//		textAreaPane.setBackground(Color.green);
		
		// set label for hint
		bottomLabel = new JLabel("Separate IDs from each other by a comma (,).");
		bottomLabel.setFont(new Font("Calibri Light", Font.PLAIN, 12));
		bottomLabel.setBackground(Color.magenta);
		bottomLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		// set text area and label with flow layout
		bigPanel = new JPanel();
		bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.Y_AXIS));
		bigPanel.add(textArea);
		bigPanel.add(bottomLabel);
		bigPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		// set flowlayout and grid layout into a gridbag
		
		// put top label in main panel
		topLabel = new JLabel("List out designs or item IDs to check which customers have them built out.");
		
		// put gridbag layout into main panel
		

		
				
		

		frame.add(bigPanel);
//		panel_1.add(buttonsPanel, BorderLayout.EAST);
		
		
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
							window.frame.update(null);
							
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

		
		Panel panel_1 = new Panel();
		panel_1.setSize(frame.size());
//		frame.getContentPane().add(panel_1);
		
		
		
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
