import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.GridLayout;
import java.util.function.Function;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddItemWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldName;
	private JTextField textFieldPrice;
	
	private Item item;
	private Function<Item, Void> callback;
	private JTextField textFieldAmount;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddItemWindow frame = new AddItemWindow();
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
	public AddItemWindow() {
		final AddItemWindow that = this;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("\u6DFB\u52A0\u5546\u54C1");
		setBounds(100, 100, 189, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JPanel panelTable = new JPanel();
		panelTable.setBounds(0, 0, 173, 112);
		panelTable.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.add(panelTable);
		panelTable.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel label = new JLabel("\u5546\u54C1\u540D");
		panelTable.add(label, "2, 2, right, default");
		
		textFieldName = new JTextField();
		panelTable.add(textFieldName, "4, 2, fill, default");
		textFieldName.setColumns(10);
		
		JLabel label_1 = new JLabel("\u4EF7\u683C");
		panelTable.add(label_1, "2, 4, right, default");
		
		textFieldPrice = new JTextField();
		panelTable.add(textFieldPrice, "4, 4, fill, default");
		textFieldPrice.setColumns(10);
		
		JLabel label_2 = new JLabel("\u6570\u91CF");
		panelTable.add(label_2, "2, 6, right, default");
		
		textFieldAmount = new JTextField();
		panelTable.add(textFieldAmount, "4, 6, fill, default");
		textFieldAmount.setColumns(10);
		
		JPanel panelBtn = new JPanel();
		panelBtn.setBounds(0, 115, 173, 27);
		panel.add(panelBtn);
		panelBtn.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton buttonComfirm = new JButton("\u786E\u8BA4");
		buttonComfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// 修改按钮回调
				that.item.name = textFieldName.getText();
				that.item.price = Float.valueOf(textFieldPrice.getText());
				that.item.amount = Integer.valueOf(textFieldAmount.getText());

				dispose();
				
				if(that.callback != null) {
					that.callback.apply(that.item);
				}
			}
		});
		panelBtn.add(buttonComfirm);
		
		JButton buttonCancel = new JButton("\u53D6\u6D88");
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//取消按钮回调
				dispose();
			}
		});
		panelBtn.add(buttonCancel);
		
		
		// 给item创建初值
		item = new Item();
		updateItem();
	}
	
	// 更新物品信息到显示框
	private void updateItem() {
		textFieldName.setText(item.name);
		textFieldPrice.setText(String.valueOf(item.price));
		textFieldAmount.setText(String.valueOf(item.amount));
	}

	public  static void addItem(Function<Item, Void> callback) {
		Item item = new Item();
		editItem(item, callback);
	}
	
	public  static void editItem(Item item) {
		editItem(item, null);
	}
	
	public  static void editItem(Item item, Function<Item, Void> callback) {
		AddItemWindow frame = new AddItemWindow();
		frame.callback = callback;
		frame.item = item;
		frame.updateItem();
		frame.setVisible(true);
	}
}
