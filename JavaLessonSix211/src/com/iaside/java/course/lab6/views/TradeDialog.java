package com.iaside.java.course.lab6.views;

import com.iaside.java.course.lab6.controllers.DatabaseController;
import com.iaside.java.course.lab6.models.Custom;
import com.iaside.java.course.lab6.models.Item;
import com.iaside.java.course.lab6.models.Trade;
import com.iaside.java.course.lab6.models.mappers.GetAllMapper;
import org.apache.ibatis.session.SqlSession;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Vector;

public class TradeDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox<String> customComboBox;
    private JComboBox<String> itemComboBox;
    private JTextField amountTextField;

    private DatabaseController databaseController;

    private Vector<Item> items;
    private Vector<Custom> customs;

    private long id = -1;

    private Trade result;

    private void showDatabaseErrorAndExit(){
        showDatabaseErrorAndExit("数据库读取失败！");
    }
    private void showDatabaseErrorAndExit(String msg){
        JOptionPane.showMessageDialog(this, msg);
        dispose();
    }

    public TradeDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        try {
            databaseController = DatabaseController.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
            showDatabaseErrorAndExit();
        }

        loadItems();
        loadCustoms();

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        result = trans2Trade();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void loadItems(){
        SqlSession session = databaseController.createSession();
        try{
            GetAllMapper mapper = session.getMapper(GetAllMapper.class);
            items = mapper.selectItems();
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            itemComboBox.setModel(model);
            if(items.size() == 0){
                showDatabaseErrorAndExit("请先添加商品！");
                return;
            }
            items.forEach(i -> {
                model.addElement(String.valueOf(i.getId()) + ": " + i.getName());
            });
            itemComboBox.setSelectedIndex(0);
        }
        catch (Exception e){
            e.printStackTrace();
            showDatabaseErrorAndExit();
        }
        finally {
            session.close();
        }
    }

    private void loadCustoms(){
        SqlSession session = databaseController.createSession();
        try{
            GetAllMapper mapper = session.getMapper(GetAllMapper.class);
            customs = mapper.selectCustoms();
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
            customComboBox.setModel(model);
            if(customs.size() == 0){
                showDatabaseErrorAndExit("请先添加会员！");
                return;
            }
            customs.forEach(c -> {
                model.addElement(String.valueOf(c.getId()) + ": " + c.getName());
            });
            customComboBox.setSelectedIndex(0);
        }
        catch (Exception e){
            e.printStackTrace();
            showDatabaseErrorAndExit();
        }
        finally {
            session.close();
        }
    }

    public static void main(String[] args) {
        TradeDialog dialog = new TradeDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private Trade trans2Trade(){
        float amount = Float.valueOf(amountTextField.getText());
        int itemIndex = itemComboBox.getSelectedIndex();
        long itemId = items.get(itemIndex).getId();
        int customIndex = customComboBox.getSelectedIndex();
        long customId = customs.get(customIndex).getId();
        if(id >= 0)
            return new Trade(id, customId, itemId, amount);
        else
            return new Trade(customId, itemId, amount);
    }

    private void setTrade(Trade trade){
        int itemIndex = -1;
        for(int i = 0; i < items.size(); i++){
            if(items.get(i).getId() == trade.getItemBoughtId())
                itemIndex = i;
        }
        if(itemIndex < 0)
            itemComboBox.setSelectedIndex(0);
        else
            itemComboBox.setSelectedIndex(itemIndex);

        id = trade.getId();

        int customIndex = -1;
        for(int i = 0; i < customs.size(); i++){
            if(customs.get(i).getId() == trade.getBuyerId())
                customIndex = i;
        }
        if(customIndex < 0)
            customComboBox.setSelectedIndex(0);
        else
            customComboBox.setSelectedIndex(customIndex);

        amountTextField.setText(String.format("%.2f", trade.getAmount()));
    }

    public static Trade getNew(){
        TradeDialog dialog = new TradeDialog();
        dialog.pack();
        dialog.setVisible(true);
        return dialog.result;
    }

    public static Trade update(Trade trade){
        TradeDialog dialog = new TradeDialog();
        dialog.pack();
        dialog.setTrade(trade);
        dialog.setVisible(true);
        if(dialog.result == null)
            return trade;
        else
            return dialog.result;
    }
}
