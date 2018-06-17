package com.iaside.java.course.lab6.views;

import com.iaside.java.course.lab6.models.Item;

import javax.swing.*;
import java.awt.event.*;

public class ItemDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nameTextField;
    private JTextField priceTextField;

    private long id = -1;

    private Item result;

    public ItemDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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
        result = trans2Item();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private Item trans2Item(){
        String name = nameTextField.getText();
        float price = Float.valueOf(priceTextField.getText());
        if(id >= 0)
            return new Item(id, name, price);
        else
            return new Item(name, price);
    }

    private void setItem(Item item){
        id = item.getId();
        nameTextField.setText(item.getName());
        priceTextField.setText(String.format("%.2f", item.getPrice()));
    }

    public static Item getNew(){
        ItemDialog dialog = new ItemDialog();
        dialog.pack();
        dialog.setVisible(true);
        return dialog.result;
    }

    public static Item update(Item item){
        ItemDialog dialog = new ItemDialog();
        dialog.pack();
        dialog.setItem(item);
        dialog.setVisible(true);
        if(dialog.result == null)
            return item;
        else
            return dialog.result;
    }

    public static void main(String[] args) {
        ItemDialog dialog = new ItemDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
