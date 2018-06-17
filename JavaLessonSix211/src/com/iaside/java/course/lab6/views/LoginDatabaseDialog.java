package com.iaside.java.course.lab6.views;

import javax.swing.*;
import java.awt.event.*;

public class LoginDatabaseDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField addressTextField;
    private JTextField userTextField;
    private JPasswordField passwordField;

    private boolean isSet = false;
    private String address;
    private String user;

    public boolean isSet() {
        return isSet;
    }

    public String getUrl() {
        return address;
    }

    public String getUser() {
        return user;
    }

    public String getPwd() {
        return pwd;
    }

    private String pwd;

    public LoginDatabaseDialog() {
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
        isSet = true;
        address = addressTextField.getText();
        user = userTextField.getText();
        pwd = String.valueOf(passwordField.getPassword());
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        LoginDatabaseDialog dialog = new LoginDatabaseDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
