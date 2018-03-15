import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Label;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Function;

public class CustomWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldName;
	private JTable table;
	private DefaultTableModel defaultTableModel;
	private JLabel labelTotal;
	
	public Custom custom;
	public Function<Custom, Void> callback;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CustomWindow dialog = new CustomWindow();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CustomWindow() {
		CustomWindow that = this;
		
		setTitle("\u987E\u5BA2\u7BA1\u7406");
		setResizable(false);
		setBounds(100, 100, 453, 289);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 432, 216);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		JLabel label = new JLabel("\u5BA2\u6237\u59D3\u540D\uFF1A");
		label.setBounds(14, 13, 85, 18);
		contentPanel.add(label);
		
		textFieldName = new JTextField();
		textFieldName.getDocument().addDocumentListener(new DocumentListener() {
			// 监听文本框修改
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				that.custom.name = textFieldName.getText();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				that.custom.name = textFieldName.getText();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				that.custom.name = textFieldName.getText();
			}
		});
		textFieldName.setColumns(10);
		textFieldName.setBounds(100, 10, 222, 24);
		contentPanel.add(textFieldName);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 44, 418, 159);
		contentPanel.add(scrollPane);
		
		table = new JTable() {
			// 表格不可编辑
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		defaultTableModel = new DefaultTableModel(
				new Object[][] {
					
				},
				new String[] {
					"\u5546\u54C1", "\u5355\u4EF7", "\u6570\u91CF", "\u603B\u4EF7"
				}
			) {
				Class[] columnTypes = new Class[] {
					Object.class, Float.class, Integer.class, Float.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			};
		table.setModel(defaultTableModel);
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		
		labelTotal = new JLabel("\u603B\u8BA1\uFF1A");
		labelTotal.setBounds(336, 13, 96, 18);
		contentPanel.add(labelTotal);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(24, 210, 404, 37);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			
			JButton addButton = new JButton("\u589E\u52A0\u5546\u54C1");
			addButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//增加物品
					AddItemWindow.addItem((Item i)->{
						that.custom.items.add(i);
						that.updateCustom();
						return null;
					});
				}
			});
			buttonPane.add(addButton);
			addButton.setActionCommand("add");
			{
				JButton modifyButton = new JButton("\u4FEE\u6539\u5546\u54C1");
				modifyButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//修改物品
						int row = table.getSelectedRow();
						if(row>=0) {
							Item select = custom.items.get(row);
							AddItemWindow.editItem(select, (Item i)->{
								that.updateCustom();
								return null;
							});
						}
						else {
							JOptionPane.showMessageDialog(null, "未选择项目", "请选择", JOptionPane.WARNING_MESSAGE); 
						}
					}
				});
				modifyButton.setActionCommand("modify");
				buttonPane.add(modifyButton);
			}
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						// 退出
						dispose();
						if (custom != null)
							callback.apply(custom);
					}
				});
				
				JButton deleteButton = new JButton("\u5220\u9664\u5546\u54C1");
				deleteButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						// 删除物品
						int row = table.getSelectedRow();
						if(row>=0) {
							custom.items.remove(row);
							that.updateCustom();
						}
						else {
							JOptionPane.showMessageDialog(null, "未选择项目", "请选择", JOptionPane.WARNING_MESSAGE); 
						}
					}
				});
				deleteButton.setActionCommand("delete");
				buttonPane.add(deleteButton);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		custom = new Custom();
		custom.name = "顾客";
		updateCustom();
	}
	
	public void updateCustom(){
		textFieldName.setText(custom.name);		
		float pTotal = custom.getTotalPrice();
		labelTotal.setText("总计：" + String.valueOf(pTotal));
		//循环删除所有行
        int numrow = defaultTableModel.getRowCount();
        for (int i=0; i<numrow; i++){
            defaultTableModel.removeRow(0);
        }
        //重新添加行
		for (Item i : custom.items) {
			defaultTableModel.addRow(new Object[] {
					i.name, i.price, i.amount, i.getTotalPrice()
				});
		}
	}
	
	public  static void addCustom(Function<Custom, Void> callback) {
		Custom custom = new Custom();
		editCustom(custom, callback);
	}
	
	public  static void editCustom(Custom custom) {
		editCustom(custom, null);
	}
	
	public  static void editCustom(Custom custom, Function<Custom, Void> callback) {
		CustomWindow frame = new CustomWindow();
		frame.callback = callback;
		frame.custom = custom;
		frame.updateCustom();
		frame.setVisible(true);
	}
}
