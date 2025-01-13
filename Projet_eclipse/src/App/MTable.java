package App;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.DatePickerSettings.DateArea;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeArea;
import com.github.lgooddatepicker.tableeditors.DateTimeTableEditor;

public class MTable extends JTable {
    private static final long serialVersionUID = 1L;
    private String[] fullColumnNames;

    // Constructeur acceptant un DefaultTableModel
    public MTable(DefaultTableModel model) {
        super(model);
        super.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        int count = model.getColumnCount();
        fullColumnNames = new  String[count];
        for(int i=0; i< count;i++)
        {
        	fullColumnNames[i]= model.getColumnName(i);
        }

        setupCopyAction();
        setupHeaderTooltips();
    }

    // Empêcher l'édition des cellules
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
    
    @Override
    public void setModel(TableModel dataModel) {
    	super.setModel(dataModel);
    	
    	int count = dataModel.getColumnCount();
        fullColumnNames = new  String[count];
        for(int i=0; i< count;i++)
        {
        	fullColumnNames[i]= dataModel.getColumnName(i);
        }
    }

    // Préparer le rendu des cellules

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component returnComp = super.prepareRenderer(renderer, row, column);
        Color alternateColor = new Color(241, 241, 241);
        Color whiteColor = Color.WHITE;

        // Appliquer une alternance de couleurs
        if (!returnComp.getBackground().equals(getSelectionBackground())) {
            Color bg = (row % 2 == 0) ? alternateColor : whiteColor;
            returnComp.setBackground(bg);

            // Si le composant est un DateTimePicker, mettre à jour ses couleurs
            if (returnComp instanceof DateTimePicker) {
                DateTimePicker dateTimePicker = (DateTimePicker) returnComp;

                DatePickerSettings dateSettings = dateTimePicker.getDatePicker().getSettings();
                dateSettings.setColor(DateArea.TextFieldBackgroundValidDate, bg);
                dateSettings.setColor(DateArea.TextFieldBackgroundInvalidDate, bg);

                dateTimePicker.getTimePicker().getSettings().setColor(TimeArea.TextFieldBackgroundValidTime, bg);
                dateTimePicker.getTimePicker().getSettings().setColor(TimeArea.TextFieldBackgroundInvalidTime, bg);
            }
        }

        return returnComp;
    }
    
    public void style (int[] localDateTimeIndexTable)
    {
    	 setPreferredScrollableViewportSize(new Dimension(500, 70));
	     setFillsViewportHeight(true);
	
	     TableColumnModel columnModel = getColumnModel();
	     int nbColumn=columnModel.getColumnCount();         
	     int width=130;
	     for(int i=1; i< nbColumn;i++)
	     {
	    	 columnModel.getColumn(i).setPreferredWidth(width);
	     }
	     
	     setDefaultRenderer(LocalDateTime.class, new DateTimeTableEditor());
	 	
	 	for(int i : localDateTimeIndexTable )
	 	{
		    	TableColumn column = getColumnModel().getColumn(i-1);
		    	column.setPreferredWidth(250);
		    	column.setCellRenderer(getDefaultRenderer(LocalDateTime.class));
	 	}
    }
    
    private void setupHeaderTooltips() {
    	JTableHeader header = getTableHeader();
    	header.setDefaultRenderer(new TableCellRenderer() {
    		private final TableCellRenderer delegate = header.getDefaultRenderer(); // Use existing renderer
    		
    		@Override
    		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    			Component comp = delegate.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    			
    			if (comp instanceof JComponent)
    			{
    				JComponent jComp = (JComponent) comp;
    				
    				if (column >= 0 && column < fullColumnNames.length)
    				{
    					jComp.setToolTipText(fullColumnNames[column]);
    				} 
    				else
    				{
    					jComp.setToolTipText(null);
    				}
    			}
    			return comp;
    		}
    	});
    }
    
    private void setupCopyAction() {
    	getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("ctrl C"), "copy");
    	getActionMap().put("copy", new AbstractAction()
    	{
			private static final long serialVersionUID = 1L;

			@Override
    		public void actionPerformed(ActionEvent e)
    		{
    			int selectedRow = getSelectedRow();
    			int selectedColumn = getSelectedColumn();
    			
    			if (selectedRow != -1 && selectedColumn != -1)
    			{
    				Object cellValue = getValueAt(selectedRow, selectedColumn);
    				if (cellValue != null)
    				{
    					String cellText = cellValue.toString();
    					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    					StringSelection selection = new StringSelection(cellText);
    					clipboard.setContents(selection, null);
    				}
    			}
    		}
    	});
    }
}
