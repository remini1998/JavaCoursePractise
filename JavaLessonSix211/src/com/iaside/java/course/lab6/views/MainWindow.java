package com.iaside.java.course.lab6.views;

import com.iaside.java.course.lab6.controllers.DatabaseController;
import com.iaside.java.course.lab6.models.Custom;
import com.iaside.java.course.lab6.models.Item;
import com.iaside.java.course.lab6.models.Trade;
import com.iaside.java.course.lab6.models.mappers.CustomMapper;
import com.iaside.java.course.lab6.models.mappers.ItemMapper;
import com.iaside.java.course.lab6.models.mappers.TradeMapper;
import org.apache.ibatis.session.SqlSession;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.SliderUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Vector;

public class MainWindow {
    private JTabbedPane tabbedPane;
    private JButton addTradeButton;
    private JButton refreshTradesButton;
    private JTextField searchTradesTextField;
    private JPanel mainPanel;
    private JPanel toolsBar;
    private JTable tradesTable;
    private DefaultTableModel tradesTableModel;
    private JButton addCustomButton;
    private JTextField searchCustomsTextField;
    private JButton refreshCustomsButton;
    private JTable customsTable;
    private DefaultTableModel customsTableModel;
    private JTable itemsTable;
    private DefaultTableModel itemsTableModel;
    private JButton addItemButton;
    private JTextField searchItemsTextField;
    private JButton refreshItemsButton;
    private JScrollPane tradeScrollPane;


    private JTable rightMouseSelectedTable = null;

    private MainWindow that = this;

    private DatabaseController databaseController = DatabaseController.getInstance();

    public static void main(String[] args) throws IOException {
        loadPrettyUI();
        JFrame frame = new JFrame("超市管理系统");
        MainWindow mainWindow = new MainWindow();
        frame.setContentPane(mainWindow.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static String OS = System.getProperty("os.name").toLowerCase();
    private static String OSVer = System.getProperty("os.version").toLowerCase();

    private JPopupMenu popupMenu;

    /** UIManager中UI字体相关的key */
    public static String[] DEFAULT_FONT  = new String[]{
            "Table.font"
            ,"TableHeader.font"
            ,"CheckBox.font"
            ,"Tree.font"
            ,"Viewport.font"
            ,"ProgressBar.font"
            ,"RadioButtonMenuItem.font"
            ,"ToolBar.font"
            ,"ColorChooser.font"
            ,"ToggleButton.font"
            ,"Panel.font"
            ,"TextArea.font"
            ,"Menu.font"
            ,"TableHeader.font"
            // ,"TextField.font"
            ,"OptionPane.font"
            ,"MenuBar.font"
            ,"Button.font"
            ,"Label.font"
            ,"PasswordField.font"
            ,"ScrollPane.font"
            ,"MenuItem.font"
            ,"ToolTip.font"
            ,"List.font"
            ,"EditorPane.font"
            ,"Table.font"
            ,"TabbedPane.font"
            ,"RadioButton.font"
            ,"CheckBoxMenuItem.font"
            ,"TextPane.font"
            ,"PopupMenu.font"
            ,"TitledBorder.font"
            ,"ComboBox.font"
    };

    private static void loadPrettyUI(){
        try
        {
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
            UIManager.put("RootPane.setupButtonVisible", false);
            // 调整默认字体
            if(isWindows7()){
                for (int i = 0; i < DEFAULT_FONT.length; i++)
                    UIManager.put(DEFAULT_FONT[i],new Font("微软雅黑", Font.PLAIN,14));
            }
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
        }
        catch(Exception e)
        {
            System.out.println("加载皮肤失败");
        }
    }


    private static boolean isWindows7(){
        return OS.contains("windows") && !OSVer.startsWith("10");
    }


    public MainWindow() throws IOException {
        createPopupMenu();

        connect();
        refreshTables();

        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                refreshTables();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                refreshTables();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                refreshTables();
            }
        };

        searchCustomsTextField.getDocument().addDocumentListener(documentListener);
        searchTradesTextField.getDocument().addDocumentListener(documentListener);
        searchItemsTextField.getDocument().addDocumentListener(documentListener);
        addTradeButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        addItemButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });
        itemsTable.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                mouseRightButtonClick(e);
                mouseDoubleClick(e);
            }
        });
        addCustomButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                addCustom();
            }
        });
        customsTable.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                mouseRightButtonClick(e);
                mouseDoubleClick(e);
            }
        });
        addTradeButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                addTrade();
            }
        });
        tradesTable.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                mouseRightButtonClick(e);
                mouseDoubleClick(e);
            }
        });
    }

    private void addItem(){
        Item i = ItemDialog.getNew();
        if(i == null) return;
        SqlSession session = databaseController.createSession();
        try{
            ItemMapper mapper = session.getMapper(ItemMapper.class);
            mapper.insert(i);
            session.commit();
//                    JOptionPane.showMessageDialog(that.mainPanel, "添加成功");
            refreshTables();
        }
        catch (Exception exception){
            JOptionPane.showMessageDialog(that.mainPanel, "添加失败");
            exception.printStackTrace();
        }
        finally {
            session.close();
        }
    }

    private void addCustom(){
        Custom c = CustomDialog.getNew();
        if(c == null) return;
        SqlSession session = databaseController.createSession();
        try{
            CustomMapper mapper = session.getMapper(CustomMapper.class);
            mapper.insert(c);
            session.commit();
//                    JOptionPane.showMessageDialog(that.mainPanel, "添加成功");
            refreshTables();
        }
        catch (Exception exception){
            JOptionPane.showMessageDialog(that.mainPanel, "添加失败");
            exception.printStackTrace();
        }
        finally {
            session.close();
        }
    }

    private void addTrade(){
        Trade c = TradeDialog.getNew();
        if(c == null) return;
        SqlSession session = databaseController.createSession();
        try{
            TradeMapper mapper = session.getMapper(TradeMapper.class);
            mapper.insert(c);
            session.commit();
//                    JOptionPane.showMessageDialog(that.mainPanel, "添加成功");
            refreshTables();
        }
        catch (Exception exception){
            JOptionPane.showMessageDialog(that.mainPanel, "添加失败");
            exception.printStackTrace();
        }
        finally {
            session.close();
        }
    }

    private JTable tableBuilder(String[] headers, Class[] types){
        JTable table = new JTable() {
            // 表格不可编辑
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultTableModel defaultTableModel = new DefaultTableModel(
                new Object[][] {  },
                headers
        ) {
            Class[] columnTypes = types;
            public Class getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
        };
        table.setModel(defaultTableModel);
        table.setFillsViewportHeight(true);
        return table;
    }

    private void connect() {
        LoginDatabaseDialog dialog = new LoginDatabaseDialog();
        dialog.pack();
        dialog.setVisible(true);
        try {
            System.out.println(dialog.getPwd());
            databaseController.connect(dialog.getUrl(), dialog.getUser(), dialog.getPwd());
//            JOptionPane.showMessageDialog(this.mainPanel, "数据库连接成功");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.mainPanel, "数据库连接失败");
            e.printStackTrace();
        }
    }

    private void refreshTables(){
        refreshTradesTable();
        refreshCustomsTable();
        refreshItemsTable();
    }

    private void refreshTradesTable(){
        String text = this.searchTradesTextField.getText();
        SqlSession session = databaseController.createSession();
        try{
            TradeMapper mapper = session.getMapper(TradeMapper.class);
            Vector<Trade> trades = (text.equals("") || text.toLowerCase().equals("all")) ? mapper.selectAll() : mapper.search(text);
            setTradesTable(trades);
        }
        finally {
            session.close();
        }
    }

    private void refreshItemsTable(){
        String text = this.searchItemsTextField.getText();
        SqlSession session = databaseController.createSession();
        try{
            ItemMapper mapper = session.getMapper(ItemMapper.class);
            Vector<Item> items = (text.equals("") || text.toLowerCase().equals("all")) ? mapper.selectAll() : mapper.search(text);
            setItemsTable(items);
        }
        finally {
            session.close();
        }
    }

    private void refreshCustomsTable(){
        String text = this.searchCustomsTextField.getText();
        SqlSession session = databaseController.createSession();
        try{
            CustomMapper mapper = session.getMapper(CustomMapper.class);
            Vector<Custom> customs = (text.equals("") || text.toLowerCase().equals("all")) ? mapper.selectAll() : mapper.search(text);
            setCustomsTable(customs);
        }
        finally {
            session.close();
        }
    }

    private void setTradesTable(Vector<Trade> trades){
        tradesTableModel.setRowCount(0);
        trades.forEach(t -> {
            Vector<Object> v = new Vector<>();
            v.add(t.getId());
            v.add(t.getBuyerName());
            v.add(t.getItemBoughtName());
            v.add(t.getPrice());
            v.add(t.getAmount());
            v.add(t.getPrice());
            tradesTableModel.addRow(v);
        });
    }

    private void setItemsTable(Vector<Item> items){
        itemsTableModel.setRowCount(0);
        items.forEach(i -> {
            Vector<Object> v = new Vector<>();
            v.add(i.getId());
            v.add(i.getName());
            v.add(i.getPrice());
            v.add(i.getTotalSold());
            itemsTableModel.addRow(v);
        });
    }
    private void setCustomsTable(Vector<Custom> customs){
        customsTableModel.setRowCount(0);
        customs.forEach(c -> {
            Vector<Object> v = new Vector<>();
            v.add(c.getId());
            v.add(c.getName());
            v.add(c.getTotalSpent());
            customsTableModel.addRow(v);
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        tradesTable = tableBuilder(new String[]{
                "id", "商品", "会员名", "单价", "数量", "总价"
        }, new Class[]{
                Long.class, Object.class, Object.class, Float.class, Float.class, Float.class
        });
        tradesTableModel = (DefaultTableModel) tradesTable.getModel();

        itemsTable = tableBuilder(new String[]{
                "id", "物品名", "单价", "总销量"
        }, new Class[]{
                Long.class, Object.class, Float.class, Float.class
        });
        itemsTableModel = (DefaultTableModel) itemsTable.getModel();

        customsTable = tableBuilder(new String[]{
                "id", "会员名", "总消费"
        }, new Class[]{
                Long.class, Object.class, Float.class
        });
        customsTableModel = (DefaultTableModel) customsTable.getModel();
    }

    private void createPopupMenu() {
        popupMenu = new JPopupMenu();

        JMenuItem updateMenItem = new JMenuItem();
        updateMenItem.setText("修改");
        updateMenItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                //该操作需要做的事
                update(rightMouseSelectedTable);
                rightMouseSelectedTable = null;
            }
        });

        JMenuItem delMenItem = new JMenuItem();
        delMenItem.setText("删除");
        delMenItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JTable table = rightMouseSelectedTable;
                int index = table.getSelectedRow();
                long id = (long) table.getModel().getValueAt(index, 0);
                if(table == customsTable){
                    deleteCustom(id);
                }
                else if(table == tradesTable){
                    deleteTrade(id);
                }
                else if(table == itemsTable){
                    deleteItem(id);
                }
                rightMouseSelectedTable = null;
            }
        });

        popupMenu.add(updateMenItem);
        popupMenu.add(delMenItem);
    }

    //鼠标右键点击事件
    private void mouseRightButtonClick(MouseEvent event) {
        //判断是否为鼠标的BUTTON3按钮，BUTTON3为鼠标右键
        if (event.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            //通过点击位置找到点击为表格中的行
            rightMouseSelectedTable = (JTable) event.getSource();
//            int focusedRowIndex = rightMouseSelectedTable.rowAtPoint(evt.getPoint());
//            if (focusedRowIndex == -1) {
//                return;
//            }
            //将表格所选项设为当前右键点击的行
//            jTable1.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
            //弹出菜单
            popupMenu.show(rightMouseSelectedTable, event.getX(), event.getY());
        }
    }

    //鼠标双击事件
    private void mouseDoubleClick(MouseEvent event){
        if(event.getButton() == java.awt.event.MouseEvent.BUTTON1 && event.getClickCount() > 1){
            JTable table = (JTable) event.getSource();
            update(table);
        }
    }

    private void update(JTable table){
        int index = table.getSelectedRow();
        long id = (long) table.getModel().getValueAt(index, 0);
        if(table == customsTable){
            SqlSession session = databaseController.createSession();
            try{
                CustomMapper mapper = session.getMapper(CustomMapper.class);
                Custom c = mapper.select(id);
                c = CustomDialog.update(c);
                if(c == null) return;
                update(c);
                refreshTables();
            }
            catch (Exception exception){
                JOptionPane.showMessageDialog(that.mainPanel, "修改失败");
                exception.printStackTrace();
            }
            finally {
                session.close();
            }
        }
        else if(table == tradesTable){
            SqlSession session = databaseController.createSession();
            try{
                TradeMapper mapper = session.getMapper(TradeMapper.class);
                Trade t = mapper.select(id);
                t = TradeDialog.update(t);
                if(t == null) return;
                update(t);
                refreshTables();
            }
            catch (Exception exception){
                JOptionPane.showMessageDialog(that.mainPanel, "修改失败");
                exception.printStackTrace();
            }
            finally {
                session.close();
            }
        }
        else if(table == itemsTable){
            SqlSession session = databaseController.createSession();
            try{
                ItemMapper mapper = session.getMapper(ItemMapper.class);
                Item i = mapper.select(id);
                i = ItemDialog.update(i);
                if(i == null) return;
                update(i);
                refreshTables();
            }
            catch (Exception exception){
                JOptionPane.showMessageDialog(that.mainPanel, "修改失败");
                exception.printStackTrace();
            }
            finally {
                session.close();
            }
        }
    }
    private void update(Custom custom){
        try (SqlSession session = databaseController.createSession()) {
            CustomMapper mapper = session.getMapper(CustomMapper.class);
            mapper.update(custom);
            session.commit();
            refreshTables();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(that.mainPanel, "修改失败");
            exception.printStackTrace();
        }
    }
    private void update(Trade trade){
        try (SqlSession session = databaseController.createSession()) {
            TradeMapper mapper = session.getMapper(TradeMapper.class);
            mapper.update(trade);
            session.commit();
            refreshTables();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(that.mainPanel, "修改失败");
            exception.printStackTrace();
        }
    }
    private void update(Item item){
        try (SqlSession session = databaseController.createSession()) {
            ItemMapper mapper = session.getMapper(ItemMapper.class);
            mapper.update(item);
            session.commit();
            refreshTables();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(that.mainPanel, "修改失败");
            exception.printStackTrace();
        }
    }

    private void deleteCustom(long custom){
        try (SqlSession session = databaseController.createSession()) {
            CustomMapper mapper = session.getMapper(CustomMapper.class);
            mapper.delete(custom);
            session.commit();
            refreshTables();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(that.mainPanel, "删除失败");
            exception.printStackTrace();
        }
    }
    private void deleteTrade(long trade){
        try (SqlSession session = databaseController.createSession()) {
            TradeMapper mapper = session.getMapper(TradeMapper.class);
            mapper.delete(trade);
            session.commit();
            refreshTables();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(that.mainPanel, "删除失败");
            exception.printStackTrace();
        }
    }
    private void deleteItem(long item){
        SqlSession session = databaseController.createSession();
        try{
            ItemMapper mapper = session.getMapper(ItemMapper.class);
            mapper.delete(item);
            session.commit();
            refreshTables();
        }
        catch (Exception exception){
            JOptionPane.showMessageDialog(that.mainPanel, "删除失败");
            exception.printStackTrace();
        }
        finally {
            session.close();
        }
    }
}
