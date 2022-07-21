import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DropMode;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicButtonUI;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author diannerobbi
 */
public class App {

	private static int progress;
	private JFrame frame;
	private static JTextArea topLabel;
	private static JButton btnRun;
	private static JButton btnImport;
	private static JButton btnSource;
	private static JButton btnHow;
	private static JScrollPane textAreaPane;
	private static JTextArea textArea;
	private static String DESTINATION_PATH;
	final static String DESKTOP_PATH = System.getProperty("user.home") + "\\Desktop\\";
	final static String FILE_EXTENSION = ".xlsx";
	final static int HEADER_SIZE = 22;
	private static JTextArea lblStatus;
	private static JPanel buttonsPanel;
	private static JPanel bigBoxPanel;
	private static JPanel gridPanel;
	private static JPanel actionPanel;
	private static JPanel bigPanel;
	private static JPanel topLabelPanel;
	private static JPanel mainPanel;
	private static JPanel howToPanel;
	private static JPanel rightPanel;
	private static FileInputStream fis;
	private static App window;
	private static File inputFile;
	private static boolean disselect = false;
	private static String[] listOfDesigns;
	private static int[] listOfIndeces;

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
		frame.setBounds(100, 100, 520, 305);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		
		// GridLayout for buttons
		buttonsPanel = new JPanel(new GridLayout(3,1,10,12));
		btnSource = new JButton("Source");
		btnSource.setToolTipText("Choose master sheet of item IDs (might be labeled 'Customer Style List Master Sheet').");
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
		
		// How-To Button
		Icon icon = new ImageIcon("C:\\Users\\safavieh\\Pictures\\how-to-icon.png"); // TO-DO: Update image source
		btnHow = new JButton(icon);
		btnHow.setForeground(SystemColor.menu);
		btnHow.setUI(new BasicButtonUI());
		btnHow.setToolTipText("How do I use this program?");
		btnHow.setMargin(new Insets(0, 0, 0, 0));
		howToPanel = new JPanel(new BorderLayout());
		howToPanel.add(btnHow, BorderLayout.LINE_END);
		
		// GridLayout for buttons and area
		actionPanel = new JPanel(new GridLayout(2, 1, 10, 10));
		actionPanel.add(buttonsPanel);
		actionPanel.add(lblStatus);
		
		// BorderLayout for GridLayout and How-To Button
		rightPanel = new JPanel(new BorderLayout());
		rightPanel.add(actionPanel, BorderLayout.CENTER);
		rightPanel.add(howToPanel, BorderLayout.PAGE_END);
		
		// Input Text Area
		textArea = new JTextArea();
		textAreaPane = new JScrollPane(textArea);
		textArea.setPreferredSize(new Dimension(300, 175));
		textArea.setDropMode(DropMode.INSERT);
		textArea.setFont(new Font("Calibri Light", Font.PLAIN, 13));
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		
		// BoxLayout for the input text area and hint label
		bigPanel = new JPanel();
		bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.Y_AXIS));
		bigPanel.add(textAreaPane);
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
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 1.0;
		c.gridx = 1;
		c.gridy = 0;
		gridPanel.add(rightPanel, c);
		
		// BoxLayout for top label and main components
		bigBoxPanel = new JPanel();
		bigBoxPanel.setLayout(new BoxLayout(bigBoxPanel, BoxLayout.Y_AXIS));
		topLabelPanel = new JPanel();
		topLabel = new JTextArea("List out designs or item IDs to check which customers have them built out. Place IDs on separate lines or separate by comma (,).");
		topLabel.setSize(435, 50);
		topLabel.setEditable(false);
		topLabel.setBackground(SystemColor.menu);
		topLabel.setFont(new Font("Arial", Font.PLAIN, 11));
		topLabel.setWrapStyleWord(true);
		topLabel.setLineWrap(true);
		topLabelPanel.setLayout(new BorderLayout());
		topLabelPanel.add(topLabel, BorderLayout.WEST);
		topLabelPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		bigBoxPanel.add(topLabelPanel);
		bigBoxPanel.add(gridPanel);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(bigBoxPanel, BorderLayout.CENTER);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 0));

		frame.getContentPane().add(mainPanel);
		
		// BUTTONS FUNCTIONALITIES
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
					try {
						if(file != null) fis = new FileInputStream(file);
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
				} else {
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
						btnImport.setText("Disselect File");
					}
				}
				disselect = !disselect;
			}
		});
		
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Verify input file, then input text
				if(inputFile != null) { // Imported file takes precedence
					readInputFile();
					everythingelse("Processing imported textfile...");
				} else if(!textArea.getText().equals(null) && !textArea.getText().equals("")) {
					readInputText();
					everythingelse("Processing list of text input...");
				} else {
					JOptionPane.showMessageDialog(null, "There is no input to be processed.\nPlease import a textfile or list out the designs you would like to check for in the input field.","No Input", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		btnHow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		})
	}
	
	public static void everythingelse(String message) {
		lblStatus.setText("Processing, please wait for a confirmation message for the results file.");
		JOptionPane.showMessageDialog(null, message);
		generateExcelFile();
		JOptionPane.showMessageDialog(null, "Your search results are printed out. Please find it on the following path:\n"+DESTINATION_PATH);
		lblStatus.setText("Process Completed. You may run another search.");
	}
	
	public static void generateExcelFile() {
		try {
			Set<Integer> indexList = new HashSet<Integer>();
			indexList.add(0); // Add the header index for copying over
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			Sheet sheet = wb.getSheetAt(0);;
			for(int i = 0; i < listOfDesigns.length; i++) {
				progress = i/listOfDesigns.length;
				int length = listOfDesigns[i].length();
				if(length == 0) continue;
				boolean turnedTrue = false;
				Row row;
				for(int r = 1; r < sheet.getPhysicalNumberOfRows(); r++) { // Iterate Workbook rows
					row = sheet.getRow(r);
					Cell cell = row.getCell(0);
					if(cell == null) continue;
					else if(cell.getStringCellValue().length() >= length) {
						if(cell.getStringCellValue().substring(0,length).equals(listOfDesigns[i])) {
							turnedTrue = true;
							indexList.add(r);
						} else if(turnedTrue) break; // moves on to the next design
					}
				}
			}
			
			listOfIndeces = new int[indexList.size()];
			listOfIndeces = convertSetToArray(indexList);
			Arrays.sort(listOfIndeces);
			
			// GET DESIRED DESTINATION
			JFileChooser directoryChooser = new JFileChooser();
			directoryChooser.setDialogTitle("Select a Destination for the Output Excel File (*.xlsx)");
			directoryChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			directoryChooser.setFileFilter(new FileNameExtensionFilter("xlsx files (*.xlsx)", "xlsx"));
			int response = directoryChooser.showSaveDialog(null);
			if (response == JFileChooser.APPROVE_OPTION) {
				DESTINATION_PATH = directoryChooser.getSelectedFile().getAbsolutePath();
				if (DESTINATION_PATH.length() < FILE_EXTENSION.length()) {
					DESTINATION_PATH += FILE_EXTENSION;
				} else if (!(DESTINATION_PATH.substring(DESTINATION_PATH.length() - FILE_EXTENSION.length()))
						.equals(".xlsx")) {
					DESTINATION_PATH += FILE_EXTENSION;
				} 
			}
			
			XSSFWorkbook outb = new XSSFWorkbook();
			FileOutputStream fos = new FileOutputStream(DESTINATION_PATH);
						
			XSSFFont headerFont = outb.createFont();
			Sheet sh = outb.createSheet();
			headerFont.setBold(true);
			for (int r = 0; r < listOfIndeces.length; r++) {
				Row row = sh.createRow(r);
				Row rowin = sheet.getRow(listOfIndeces[r]);
				for (int col = 0; col < HEADER_SIZE; col++) {
					Cell cell = row.createCell(col);
					Cell cell2 = rowin.getCell(col);
					if(cell2==null) cell.setBlank();
					else cell.setCellValue(rowin.getCell(col).getStringCellValue());
				}
			}
			
			outb.write(fos);
			fos.close();
			wb.close();
			outb.close();
		} catch (IOException ioe) {
			// TODO Auto-generated catch block
			ioe.printStackTrace();
		}
	}
	
	public static void readInputText() {
		// read and save into list
		Set<String> designList = new HashSet<String>();
		String line = textArea.getText();
		String[] temp;
		temp = line.split("[\\s,]+");
		designList.addAll(convertArrayToSet(temp));
		Iterator<String> it = designList.iterator();
		listOfDesigns = new String[designList.size()];
		designList.toArray(listOfDesigns); // SAVE INQUIRY LIST
		Arrays.sort(listOfDesigns);
	}
	
	public static void readInputFile() {
		try {
			// read and save into list
			Set<String> designList = new HashSet<String>();
			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			String line = br.readLine();
			String[] temp;
			do {
				temp = line.split("[\\s,]+");
				designList.addAll(convertArrayToSet(temp));
				Iterator<String> it = designList.iterator();
				line = br.readLine();
			} while(line != null);
			br.close();
			listOfDesigns = new String[designList.size()];
			designList.toArray(listOfDesigns); // SAVE INQUIRY LIST
			Arrays.sort(listOfDesigns);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static Set<String> convertArrayToSet(String[] array) {
		Set<String> set = new HashSet<String>();
		for (String elem : array) {
			set.add(elem.toUpperCase());
		}
		return set;
	}
	
	public static int[] convertSetToArray(Set<Integer> set) {
		int[] array = new int[set.size()];
		Iterator<Integer> it = set.iterator();
		int counter = 0;
		while(it.hasNext()) {
			array[counter++] = it.next();
		}
		return array;
	}
	
	public static void out(String stringToPrint) {
		System.out.println(stringToPrint);
	}
}
