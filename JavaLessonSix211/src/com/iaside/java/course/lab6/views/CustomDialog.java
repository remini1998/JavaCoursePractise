package com.iaside.java.course.lab6.views;

import com.iaside.java.course.lab6.models.Custom;

import javax.swing.*;
import java.awt.event.*;

public class CustomDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nameTextField;

    private long id = -1;

    private Custom result;

    public CustomDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                result = trans2Custom();
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
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        CustomDialog dialog = new CustomDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }


    private Custom trans2Custom(){
        String name = nameTextField.getText();
        if(id >= 0)
            return new Custom(name);
        else
            return new Custom(name);
    }

    private void setCustom(Custom custom){
        id = custom.getId();
        nameTextField.setText(custom.getName());
    }

    public static Custom getNew(){
        CustomDialog dialog = new CustomDialog();
        dialog.pack();
        dialog.setVisible(true);
        return dialog.result;
    }

    public static Custom update(Custom custom){
        CustomDialog dialog = new CustomDialog();
        dialog.pack();
        dialog.setCustom(custom);
        dialog.setVisible(true);
        if(dialog.result == null)
            return custom;
        else
            return dialog.result;
    }

}
