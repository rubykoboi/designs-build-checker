import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
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
	private static FileInputStream fis;
	private static App window;
	private static File inputFile;
	private static boolean disselect = false;

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
		lblStatus = new JTextArea("Select a source file to run inquiry.");
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
		textArea.setPreferredSize(new Dimension(300, 175));
		textArea.setDropMode(DropMode.INSERT);
		textArea.setFont(new Font("Calibri Light", Font.PLAIN, 13));
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		
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
		
		btnSource.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
					lblStatus.setText("Source selected: "+file.getAbsolutePath());
					btnRun.setEnabled(true);
					out("File has been chosen");
					try {
						out("inside try");
						if(file != null) {
							out("checked that file is not null");
							BufferedReader br = new BufferedReader(new FileReader(file));
							out("created bufferedreader");
							// read in the source
							fis = new FileInputStream(file);
							out("created fileinputstream");
							out("just set text of label status. This should have already changed.");
						}
					} catch (Exception err) {
						err.getStackTrace();
					}
				}
			}
		});
		
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(disselect) {
					inputFile = null;
					btnImport.setText("Import");
					lblStatus.setText("Input file removed.");
				}
				else {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setFileFilter(new FileFilter() {
		            	@Override
		            	public boolean accept(File f) {
		            		return f.isDirectory() || f.getName().toLowerCase().endsWith(".txt");
		            	}
		            	public String getDescription() {
		            		return "*.txt files";
		            	}
		            });
					int response = fileChooser.showOpenDialog(null);
					if (response == JFileChooser.APPROVE_OPTION) {
						inputFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
						lblStatus.setText("Input file is selected: "+inputFile.getAbsolutePath());
						out("textfile has been chosen");
						try {
							out("inside try");
							if(inputFile != null) {
								out("checked that file is not null");
								BufferedReader br = new BufferedReader(new FileReader(inputFile));
								out("created bufferedreader");
								// read in the source
								FileInputStream tis = new FileInputStream(inputFile);
								out("created textfile input stream");
								out("just set text of label status. This should have already changed.");
							}
							btnImport.setText("Disselect File");
						} catch (Exception err) {
							err.getStackTrace();
						}
					}
				}
				disselect = !disselect;
			}
		});
		
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Verify input file, then input text
				if(inputFile != null) { // Imported file takes precedence
					out("textfile was imported");
					lblStatus.setText("Processing, please wait for a confirmation message for the results file.");
					JOptionPane.showMessageDialog(null, "Processing imported textfile...");
				} else if(!textArea.getText().equals(null) && !textArea.getText().equals("")) {
					out("Text Area has this text ++"+textArea.getText()+"++");
					lblStatus.setText("Processing, please wait for a confirmation message for the results file.");
					JOptionPane.showMessageDialog(null, "Processing input...");
				} else {
					// create pop-up
					JOptionPane.showMessageDialog(null, "There is no input to be processed.\nPlease import a textfile or list out the designs you would like to check for in the input field.","No Input", JOptionPane.WARNING_MESSAGE);
					out("There is no input in the text area");
				}
			}
		});

		
		
		
	}
	
	public static void out(String stringToPrint) {
		System.out.println(stringToPrint);
	}
}
