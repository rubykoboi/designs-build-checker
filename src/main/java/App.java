import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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

import javax.swing.BorderFactory;
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
	private static JPanel bigBoxPanel;
	private static JPanel gridPanel;
	private static JPanel actionPanel;
	private static JPanel bigPanel;
	private static JPanel topLabelPanel;
	private static JPanel bottomLabelPanel;
	private static JPanel mainPanel;
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
					window.frame.setResizable(false);
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
		frame = new JFrame("Style Lists Checker");
		frame.setBounds(100, 100, 520, 275);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		
		// GridLayout for buttons
		buttonsPanel = new JPanel(new GridLayout(3,1,10,12));
		btnSource = new JButton("Source");
		btnSource.setToolTipText("Choose master sheet of item IDs (might be labeled 'Customer Builds.xlsx').");
		btnImport = new JButton("Import");
		btnImport.setToolTipText("Import a textfile with the list of designs and/or item IDs for checking.");
		btnRun = new JButton("Run");
		btnRun.setToolTipText("Process the input list of designs and/or item IDs.");
		btnRun.setEnabled(false);
		
		buttonsPanel.add(btnSource);
		buttonsPanel.add(btnImport);
		buttonsPanel.add(btnRun);

		// Status Update Area
		lblStatus = new JTextArea();
		lblStatus.setSize(150, 250);
		lblStatus.setEditable(false);
		lblStatus.setBackground(SystemColor.menu);
		lblStatus.setFont(new Font("Arial", Font.PLAIN, 10));
		lblStatus.setWrapStyleWord(true);
		lblStatus.setLineWrap(true);
		
		// GridLayout for buttons and area
		actionPanel = new JPanel(new GridLayout(2, 1, 10, 10));
		actionPanel.add(buttonsPanel);
		actionPanel.add(lblStatus);
		
		// Input Text Area
		textArea = new JTextArea();
		textAreaPane = new JScrollPane(textArea);
//		textAreaPane.setPreferredSize(new Dimension(585, 200));
		textArea.setPreferredSize(new Dimension(300, 175));
		textArea.setDropMode(DropMode.INSERT);
		textArea.setFont(new Font("Calibri Light", Font.PLAIN, 13));
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
//		textAreaPane.add(textArea);
//		textAreaPane.setBackground(Color.green);
		
		// Hint Label
		bottomLabel = new JLabel("Separate IDs from each other by a comma (,).");
		bottomLabel.setFont(new Font("Calibri Light", Font.PLAIN, 12));
		bottomLabelPanel = new JPanel();
		bottomLabelPanel.setLayout(new BorderLayout());
		bottomLabelPanel.add(bottomLabel, BorderLayout.WEST);
		
		// BoxLayout for the input text area and hint label
		bigPanel = new JPanel();
		bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.Y_AXIS));
		bigPanel.add(textAreaPane);
		bigPanel.add(bottomLabelPanel);
		bigPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		// GridBagLayout for the BoxLayout and GridLayout
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		gridPanel = new JPanel();
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 0.0;
		c.gridx = 0;
		c.gridy = 0;
		gridPanel.setLayout(gridbag);
		gridPanel.add(bigPanel, c);
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 1.0;
		c.gridx = 1;
		c.gridy = 0;
		gridPanel.add(actionPanel, c);
		
		// BoxLayout for top label and main components
		bigBoxPanel = new JPanel();
		bigBoxPanel.setLayout(new BoxLayout(bigBoxPanel, BoxLayout.Y_AXIS));
		topLabelPanel = new JPanel();
		topLabel = new JLabel("List out designs or item IDs to check which customers have them built out.");
		topLabel.setHorizontalAlignment(JLabel.LEFT);
		topLabelPanel.setLayout(new BorderLayout());
		topLabelPanel.add(topLabel, BorderLayout.WEST);
		topLabelPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		bigBoxPanel.add(topLabelPanel);
		bigBoxPanel.add(gridPanel);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(bigBoxPanel, BorderLayout.CENTER);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 0));

		frame.add(mainPanel);
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
