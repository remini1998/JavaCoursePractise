/*
 * 此文件为项目主入口！
 * 
 * Github地址：https://github.com/remini1998/JavaCoursePractise
 * 
 * 
 * 
 */

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel defaultTableModel;
	private JLabel labelTotal;
	
	private ArrayList<Custom> customs;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		MainWindow that = this;
		
		setTitle("\u8D85\u5E02\u7ED3\u7B97\u7CFB\u7EDF");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 458, 295);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelTable = new JPanel();
		panelTable.setLayout(null);
		panelTable.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelTable.setBounds(0, 0, 432, 216);
		contentPane.add(panelTable);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 44, 418, 159);
		panelTable.add(scrollPane);
		
		table = new JTable() {
			// 表格不可编辑
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		defaultTableModel = new DefaultTableModel(
				new Object[][] {
			
				},
				new String[] {
					"\u987E\u5BA2\u59D3\u540D", "\u7269\u54C1\u79CD\u7C7B", "\u7269\u54C1\u603B\u6570", "\u603B\u4EF7"
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
		table.setBounds(0, 0, 416, 126);
		scrollPane.setViewportView(table);
		
		labelTotal = new JLabel("\u603B\u8BA1\uFF1A0.0");
		labelTotal.setBounds(113, 13, 96, 18);
		panelTable.add(labelTotal);
		
		JPanel panelBtn = new JPanel();
		panelBtn.setBounds(14, 211, 417, 37);
		contentPane.add(panelBtn);
		panelBtn.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JButton addButton = new JButton("\u65B0\u987E\u5BA2");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 新顾客
				CustomWindow.addCustom(c->{
					that.customs.add(c);
					that.updateCustoms();
					return null;
				});
			}
		});
		addButton.setActionCommand("add");
		panelBtn.add(addButton);
		
		JButton modifyButton = new JButton("\u4FEE\u6539\u987E\u5BA2");
		modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 修改顾客
				int row = table.getSelectedRow();
				if(row>=0) {
					Custom select = that.customs.get(row);
					CustomWindow.editCustom(select, c ->{
						that.updateCustoms();
						return null;
					});
				}
				else {
					JOptionPane.showMessageDialog(null, "未选择项目", "请选择", JOptionPane.WARNING_MESSAGE); 
				}
			}
		});
		modifyButton.setActionCommand("modify");
		panelBtn.add(modifyButton);
		
		JButton deleteButton = new JButton("\u5220\u9664\u987E\u5BA2");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 删除顾客
				int row = table.getSelectedRow();
				if(row>=0) {
					that.customs.remove(row);
					that.updateCustoms();
				}
				else {
					JOptionPane.showMessageDialog(null, "未选择项目", "请选择", JOptionPane.WARNING_MESSAGE); 
				}
			}
		});
		deleteButton.setActionCommand("delete");
		panelBtn.add(deleteButton);
		
		customs = new ArrayList<Custom>();
	}
	
	public float getTotalPrice() {
		float all = 0;
		for (Custom custom : customs) {
			all += custom.getTotalPrice();
		}
		return all;
	}

	public void updateCustoms(){
		float pTotal = getTotalPrice();
		labelTotal.setText("总计：" + String.valueOf(pTotal));
		//循环删除所有行
        int numrow = defaultTableModel.getRowCount();
        for (int i=0; i<numrow; i++){
            defaultTableModel.removeRow(0);
        }
        //重新添加行
		for (Custom c : customs) {
			defaultTableModel.addRow(new Object[] {
					c.name, c.getTotalKinds(), c.getTotalAmount(), c.getTotalPrice()
				});
		}
	}

}
