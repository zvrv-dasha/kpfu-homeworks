package com.company;

import javax.swing.*;
import java.awt.*;

class Frame extends JFrame {
    private JPanel bPanel, tPanel;
    private JTextField jtField;
    private String[] fonts;

    public Frame() {
        setSize(500, 500);
        jtField = new JTextField(40);
        tPanel = new JPanel(new FlowLayout());
        bPanel = new JPanel(new FlowLayout());
        jtField.addActionListener(e -> JOptionPane.showMessageDialog(Frame.this,
                "Your text: " + jtField.getText()));
        tPanel.add(jtField);
        JMenuBar mBar = new JMenuBar();
        fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames(); //вытаскивает из
        // системы все установленные шрифты
        setJMenuBar(mBar);
        mBar.add(FontMenu());
        mBar.add(ColorMenu());
        ButtonStyle("Plain");
        ButtonStyle("Italic");
        ButtonStyle("Bold");
        String[] fontItems = {"8px", "10px", "12px", "50px"};
        JComboBox comboBox1 = new JComboBox(fontItems);
        comboBox1.addActionListener(e -> {
            JComboBox comboBox2 = (JComboBox) e.getSource();
            String item = (String) comboBox2.getSelectedItem();
            switch (item) {
                case "8px" -> jtField.setFont(new Font(jtField.getFont().getName(), Font.PLAIN, 8));
                case "10px" -> jtField.setFont(new Font(jtField.getFont().getName(), Font.PLAIN, 10));
                case "12px" -> jtField.setFont(new Font(jtField.getFont().getName(), Font.PLAIN, 12));
                case "50px" -> jtField.setFont(new Font(jtField.getFont().getName(), Font.PLAIN, 50));
                default -> {}
            }
        });
        getContentPane().add("Center", tPanel);
        getContentPane().add("North", bPanel);
        getContentPane().add("South", comboBox1);
    }

    private JMenu FontMenu() {
        JMenu fMenu = new JMenu("Fonts");
        JMenuItem[] font = new JMenuItem[fonts.length];
        for (int i = 0; i < fonts.length; i++) {
            font[i] = new JMenuItem(fonts[i]);
            fMenu.add(font[i]);
            font[i].addActionListener(e -> {
                try {
                    for (int j = 0; j < fonts.length; j++) {
                        if (e.getSource() == font[j]) {
                            jtField.setFont(new Font(font[j].getText(), jtField.getFont().getStyle(), jtField.getFont().getSize()));
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
        return fMenu;
    }

    private JMenu ColorMenu() {
        JMenu colorsMenu = new JMenu("Colors");
        JMenuItem[] color = new JMenuItem[3];
        String[] colors = new String[]{"Red", "Green", "Blue"};
        for (int i = 0; i < 3; i++) {
            color[i] = new JMenuItem(colors[i]);
            colorsMenu.add(color[i]);
            color[i].addActionListener(e -> {
                try {
                    for (int j = 0; j < 3; j++) {
                        if (e.getSource() == color[j]) {
                            switch (color[j].getText()) {
                                case "Red" -> jtField.setForeground(new Color(255, 0, 0));
                                case "Green" -> jtField.setForeground(new Color(0, 255, 0));
                                case "Blue" -> jtField.setForeground(new Color(0, 0, 255));
                                default -> {
                                }
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
        return colorsMenu;
    }

    void ButtonStyle(String style) {
        JButton button = new JButton(style);
        bPanel.add(button);
        button.addActionListener(e -> {
            try {
                if (e.getSource() == button) {
                    switch (button.getText()) {
                        case "Plain" -> jtField.setFont(new Font(jtField.getFont().getName(), Font.PLAIN, jtField.getFont().getSize()));
                        case "Bold" -> jtField.setFont(new Font(jtField.getFont().getName(), Font.BOLD, jtField.getFont().getSize()));
                        case "Italic" -> jtField.setFont(new Font(jtField.getFont().getName(), Font.ITALIC, jtField.getFont().getSize()));
                        default -> {
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Frame frame = new Frame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}