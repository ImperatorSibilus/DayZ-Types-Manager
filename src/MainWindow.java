import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.util.regex.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import javax.swing.JFileChooser;
import java.io.*;
import java.nio.file.StandardOpenOption;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import javax.swing.ScrollPaneConstants;
import java.awt.Cursor;
import javax.swing.JList;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.AbstractListModel;
import javax.swing.UIManager;
import java.awt.Choice;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;

public class MainWindow {

	private JFrame frmTypesManager;
	private File originalFile;
	private String header;
	private List<ItemClass> items = new ArrayList<ItemClass>();
	private List<ItemClass> oldItems = new ArrayList<ItemClass>();
	private JTable tbTypes;
	private JComboBox cbCategoryFilter;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmTypesManager.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTypesManager = new JFrame();
		frmTypesManager.setMinimumSize(new Dimension(800, 600));
		frmTypesManager.setTitle("Types Manager");
		frmTypesManager.setBounds(100, 100, 1200, 600);
		frmTypesManager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnOpen = new JButton("Open types.xml");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOpenClick();
			}
		});
		
		JButton btnInsert = new JButton("Insert types.xml");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnInsertClick();
			}
		});
		
		JButton btnSave = new JButton("Save to File");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSaveClick();
			}
		});
		
		JScrollPane spTypesTable = new JScrollPane();
		spTypesTable.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		spTypesTable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		spTypesTable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		tbTypes = new JTable();
		tbTypes.setAutoCreateRowSorter(true);
		tbTypes.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		tbTypes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbTypes.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Index", "Name", "Nominal", "Lifetime", "Restock", "Min", "Quantmin", "Quantmax", "Cost", "Count in cargo", "Count in hoarder", "Count in map", "Count in player", "Crafted", "Deloot", "Category", "Tag", "Usage", "Value"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class, String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tbTypes.getColumnModel().getColumn(Column.ID).setPreferredWidth(50);
		tbTypes.getColumnModel().getColumn(Column.ID).setMinWidth(50);
		tbTypes.getColumnModel().getColumn(Column.ID).setMaxWidth(50);
		tbTypes.getColumnModel().getColumn(Column.NAME).setPreferredWidth(100);
		tbTypes.getColumnModel().getColumn(Column.NAME).setMinWidth(100);
		tbTypes.getColumnModel().getColumn(Column.NOMINAL).setPreferredWidth(50);
		tbTypes.getColumnModel().getColumn(Column.NOMINAL).setMinWidth(50);
		tbTypes.getColumnModel().getColumn(Column.LIFETIME).setMinWidth(75);
		tbTypes.getColumnModel().getColumn(Column.RESTOCK).setMinWidth(75);
		tbTypes.getColumnModel().getColumn(Column.MIN).setPreferredWidth(50);
		tbTypes.getColumnModel().getColumn(Column.MIN).setMinWidth(50);
		tbTypes.getColumnModel().getColumn(Column.QUANTMIN).setPreferredWidth(60);
		tbTypes.getColumnModel().getColumn(Column.QUANTMIN).setMinWidth(60);
		tbTypes.getColumnModel().getColumn(Column.QUANTMAX).setPreferredWidth(60);
		tbTypes.getColumnModel().getColumn(Column.QUANTMAX).setMinWidth(60);
		tbTypes.getColumnModel().getColumn(Column.COST).setPreferredWidth(50);
		tbTypes.getColumnModel().getColumn(Column.COST).setMinWidth(50);
		tbTypes.getColumnModel().getColumn(Column.COUNTINCARGO).setPreferredWidth(100);
		tbTypes.getColumnModel().getColumn(Column.COUNTINCARGO).setMinWidth(100);
		tbTypes.getColumnModel().getColumn(Column.COUNTINCARGO).setMaxWidth(100);
		tbTypes.getColumnModel().getColumn(Column.COUNTINHOARDER).setPreferredWidth(100);
		tbTypes.getColumnModel().getColumn(Column.COUNTINHOARDER).setMinWidth(100);
		tbTypes.getColumnModel().getColumn(Column.COUNTINHOARDER).setMaxWidth(100);
		tbTypes.getColumnModel().getColumn(Column.COUNTINMAP).setPreferredWidth(100);
		tbTypes.getColumnModel().getColumn(Column.COUNTINMAP).setMinWidth(100);
		tbTypes.getColumnModel().getColumn(Column.COUNTINMAP).setMaxWidth(100);
		tbTypes.getColumnModel().getColumn(Column.COUNTINPLAYER).setPreferredWidth(100);
		tbTypes.getColumnModel().getColumn(Column.COUNTINPLAYER).setMinWidth(100);
		tbTypes.getColumnModel().getColumn(Column.COUNTINPLAYER).setMaxWidth(100);
		tbTypes.getColumnModel().getColumn(Column.CRAFTED).setPreferredWidth(50);
		tbTypes.getColumnModel().getColumn(Column.CRAFTED).setMinWidth(50);
		tbTypes.getColumnModel().getColumn(Column.CRAFTED).setMaxWidth(50);
		tbTypes.getColumnModel().getColumn(Column.DELOOT).setPreferredWidth(50);
		tbTypes.getColumnModel().getColumn(Column.DELOOT).setMinWidth(50);
		tbTypes.getColumnModel().getColumn(Column.DELOOT).setMaxWidth(50);
		tbTypes.getColumnModel().getColumn(Column.CATEGORY).setMinWidth(75);
		tbTypes.getColumnModel().getColumn(Column.TAG).setMinWidth(75);
		tbTypes.getColumnModel().getColumn(Column.USAGE).setPreferredWidth(150);
		tbTypes.getColumnModel().getColumn(Column.USAGE).setMinWidth(150);
		tbTypes.getColumnModel().getColumn(Column.VALUE).setPreferredWidth(150);
		tbTypes.getColumnModel().getColumn(Column.VALUE).setMinWidth(150);
		spTypesTable.setViewportView(tbTypes);
		
		JButton btnReset = new JButton("Reset to last temporary Save");
		btnReset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnResetToLastSave();
			}
		});
		
		JButton btnTemporarySave = new JButton("Temporary Save");
		btnTemporarySave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnTempSave();
			}
		});
		
		cbCategoryFilter = new JComboBox();
		cbCategoryFilter.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				btnFilterCategory();
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}
		});
		cbCategoryFilter.setModel(new DefaultComboBoxModel(new String[] {"Select a Category to filter by", "weapons", "food", "tools", "clothes", "vehiclesparts", "explosives", "containers"}));
		
		JButton btnFullReset = new JButton("Full Reset");
		btnFullReset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnFullReset();
			}
		});
		GroupLayout groupLayout = new GroupLayout(frmTypesManager.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(spTypesTable, GroupLayout.DEFAULT_SIZE, 1164, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnInsert, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
									.addGap(10)
									.addComponent(btnReset, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnOpen, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
									.addGap(10)
									.addComponent(btnTemporarySave, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE)))
							.addGap(10)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnFullReset, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnSave, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED, 416, Short.MAX_VALUE)
							.addComponent(cbCategoryFilter, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGap(10))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(14)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnOpen)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnTemporarySave)
							.addComponent(btnSave)
							.addComponent(cbCategoryFilter, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)))
					.addGap(6)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnInsert)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnReset)
							.addComponent(btnFullReset)))
					.addGap(11)
					.addComponent(spTypesTable, GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
					.addGap(11))
		);
		frmTypesManager.getContentPane().setLayout(groupLayout);
	}
	
	private void btnOpenClick() {
		clearTable();
		items = new ArrayList<ItemClass>();
		oldItems = new ArrayList<ItemClass>(); 
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(frmTypesManager);
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				originalFile = fileChooser.getSelectedFile();
				String text = open(originalFile);
				header = getHeader(text);
				List<String> itemsInText = searchForItems(text);
				for (int i=0; i<itemsInText.size(); i++) {
					items.add(new ItemClass(itemsInText.get(i), i+1));
					oldItems.add(new ItemClass(itemsInText.get(i), i+1));
				}
				String filter = cbCategoryFilter.getSelectedItem().toString();
				if (filter.equals("Select a Category to filter by")) {
					addToTable(items);
				} else {
					addToTable(filterByCategory(filter));
				}
			} catch (IOException e1) {
				System.out.println("Faggot!");
			}
		}
	}
	
	private void btnInsertClick() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(frmTypesManager);
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				String text = open(fileChooser.getSelectedFile());
				List<String> itemsInText = new ArrayList<String>(searchForItems(text));
				List<ItemClass> newItems = new ArrayList<ItemClass>();
				for (int i=0; i<itemsInText.size(); i++) {
					newItems.add(new ItemClass(itemsInText.get(i), i));
				}
				String attentionText = "";
				int matchIndex = -1;
				for (int i=0; i<newItems.size(); i++){
					ItemClass newItem = newItems.get(i);
					if (newItem != null) {
						int newMatchIndex = searchForMatch(newItem.getName());
						if (newMatchIndex != -1) {
							matchIndex = newMatchIndex;
						} else {
							attentionText = attentionText + newItem.getName() + "\n";
							if (matchIndex == -1) {
								newItem.index = 1;
								matchIndex = newItem.index;
							} else {
								newItem.index = matchIndex + 1;
								matchIndex = newItem.index;
							}
							items.add(newItem.index, newItem);
							String filter = cbCategoryFilter.getSelectedItem().toString(); 
							if (filter.equals("Select a Category to filter by")) {
								DefaultTableModel model = (DefaultTableModel)tbTypes.getModel();
								Object[] newRow = newItem.exportContentsForTable();
								model.insertRow(newItem.index, newRow);
								updateIndex(i+1);
							} else {
								if (filter.equals(newItem.category)) {
									DefaultTableModel model = (DefaultTableModel)tbTypes.getModel();
									Object[] newRow = newItem.exportContentsForTable();
									model.insertRow(newItem.index, newRow);
									updateIndex(i+1);
								}
							}
						}
					}
				}
				if (!attentionText.equals("")) {
					attentionText = "A few new items were added:" + "\n" + attentionText;
					JOptionPane.showMessageDialog(frmTypesManager, attentionText);
				}
			} catch (IOException e1) {
				System.out.println("Faggot!");
			}
		}
		
	}
	
	private void btnSaveClick() {
		DefaultTableModel model = (DefaultTableModel)tbTypes.getModel();
		if (model.getRowCount() > 0) {
			saveFromTable();
			String text = header + "\n<types>\n";
			for (int i = 0; i<items.size(); i++) {
				if (items.get(i) != null) {
					text = text + items.get(i).exportAsXML();
				}
			}
			text = text + "\n" + "</types>";
			Path filePath = Paths.get(originalFile.getPath());
			try {
	
				Files.writeString(filePath, text, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
			}
			catch (IOException e) 
	        {
	            System.out.println("Faggot!");
	        }
		}
	}
	
	private void btnFilterCategory() {
		saveFromTable();
		clearTable();
		String filter = cbCategoryFilter.getSelectedItem().toString();
		if (filter.equals("Select a Category to filter by")) {
			addToTable(items);
		} else {
			addToTable(filterByCategory(filter));
		}
	}
	
	private void btnResetToLastSave() {
		clearTable();
		addToTable(items);
	}
	
	private void btnTempSave() {
		saveFromTable();
	}

	private void btnFullReset() {
		clearTable();
		addToTable(oldItems);
	}
	
	private List<ItemClass> filterByCategory(String filter){
		List<ItemClass> filteredItems = new ArrayList<ItemClass>();
		if (items != null) {
			for (int i = 0; i<items.size(); i++) {
				if (items.get(i) != null && items.get(i).category != null && items.get(i).category.equals(filter)) {
					filteredItems.add(items.get(i));
				}
			}
		}
		return filteredItems;
	}
	
	private int searchForMatch(String name) {
		if (name instanceof String) {
			for (int i=0; i<items.size(); i++) {
				ItemClass itemMatch = items.get(i);
				if (itemMatch instanceof ItemClass && itemMatch.getName() instanceof String) {
					if (itemMatch.getName().equals(name)) {
						return i;
					}
				}
			}
		}
		return -1;
	}
	
	private String open(File openFile) throws IOException{
		if (openFile.isFile()) {
			return Files.readString(openFile.toPath());
		}
		else {
			return "";
		}
	}
	
	private List<String> searchForItems(String code) {
		List<String> returnItems = new ArrayList<String>();
		Pattern pattern = Pattern.compile("[^\\n]*<type name=\"(\\S*)\">[\\s\\S]*?\\s*<\\/type>");
		Matcher matcher = pattern.matcher(code);
		while (matcher.find()) {
			returnItems.add(code.substring(matcher.start(), matcher.end()));
		}
		return returnItems;
	}
	
	private String getHeader(String text) {
		String[] firstLine =  text.split("\n", 2);
		return firstLine[0];
	}
	
	private void addToTable(List<ItemClass> newItems) {
		DefaultTableModel model = (DefaultTableModel)tbTypes.getModel();
		for (int i = 0; i<newItems.size(); i++) {
			if (newItems.get(i) != null) {
				Object[] newRow = newItems.get(i).exportContentsForTable();
				model.addRow(newRow);
			}
		}
	}
	
	private void saveFromTable(){
		DefaultTableModel model = (DefaultTableModel)tbTypes.getModel();
		for (int i = 0; i<model.getRowCount(); i++) {
			Object temp = model.getValueAt(i, Column.NAME);
			int matchIndex = searchForMatch(temp.toString());
			ItemClass tempItem = items.get(matchIndex);
			updateItem(tempItem, model, i);
		}
	}
	
	private void updateIndex(int start) {
		DefaultTableModel model = (DefaultTableModel)tbTypes.getModel();
		for (int i = start; i<model.getRowCount(); i++) {
			model.setValueAt(
				Integer.parseInt(model.getValueAt(i, Column.ID).toString())+1, 
				i, 
				Column.ID
			);
		}
	}
	
	private void updateItem(ItemClass tempItem, DefaultTableModel model, int row) {
		if (tempItem != null) {
			
			if (model.getValueAt(row, Column.NOMINAL) != null || tempItem.nominal != -2) tempItem.nominal = Integer.parseInt(model.getValueAt(row, Column.NOMINAL).toString());
			if (model.getValueAt(row, Column.LIFETIME) != null || tempItem.lifetime != -2) tempItem.lifetime = Integer.parseInt(model.getValueAt(row, Column.LIFETIME).toString());
			if (model.getValueAt(row, Column.RESTOCK) != null || tempItem.restock != -2) tempItem.restock = Integer.parseInt(model.getValueAt(row, Column.RESTOCK).toString());
			if (model.getValueAt(row, Column.MIN) != null || tempItem.min != -2) tempItem.min = Integer.parseInt(model.getValueAt(row, Column.MIN).toString());
			if (model.getValueAt(row, Column.QUANTMIN) != null || tempItem.quantmin != -2) tempItem.quantmin = Integer.parseInt(model.getValueAt(row, Column.QUANTMIN).toString());
			if (model.getValueAt(row, Column.QUANTMAX) != null || tempItem.quantmax != -2) tempItem.quantmax = Integer.parseInt(model.getValueAt(row, Column.QUANTMAX).toString());
			if (model.getValueAt(row, Column.COST) != null || tempItem.cost != -2) tempItem.cost = Integer.parseInt(model.getValueAt(row, Column.COST).toString());
			
			boolean hasFlags = false;
			boolean[] newFlags = new boolean[6];
			for (int i = 0; i<6; i++) {
				if (model.getValueAt(row, i+Column.COUNTINCARGO) != null) {
					newFlags[i] = Boolean.parseBoolean(model.getValueAt(row, i+Column.COUNTINCARGO).toString());
					if (newFlags[i]) {
						hasFlags = true;
					}
				}
			}
			
			if (hasFlags) tempItem.flags = newFlags; else tempItem.flags = null;
			if (model.getValueAt(row, Column.CATEGORY) != null || tempItem.category != null) tempItem.category = model.getValueAt(row, Column.CATEGORY).toString();
			if (model.getValueAt(row, Column.TAG) != null || tempItem.tag != null) tempItem.tag = model.getValueAt(row, Column.TAG).toString();
			
			if (model.getValueAt(row, Column.USAGE) != null){
				tempItem.usage = new ArrayList<String>();
				String[] usageArray = model.getValueAt(row, Column.USAGE).toString().split(", ");
				for (String singleUsage: usageArray) {
					tempItem.usage.add(singleUsage.trim());
				}
			}
			
			if (model.getValueAt(row, Column.VALUE) != null){
				tempItem.value = new ArrayList<String>();
				String[] valueArray = model.getValueAt(row, Column.VALUE).toString().split(", ");
				for (String singleValue: valueArray) {
					tempItem.value.add(singleValue.trim());
				}
			}
		}
	}
	
	private void clearTable() {
		DefaultTableModel model = (DefaultTableModel)tbTypes.getModel();
		for (int i = 0; i<model.getRowCount(); ) {
			model.removeRow(i);
		}
	}
}
