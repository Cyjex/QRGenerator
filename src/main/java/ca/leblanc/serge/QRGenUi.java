package ca.leblanc.serge;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

/**
 * Created by Gigadelic on 12/18/2015.
 */
public class QRGenUi extends JFrame {
    private JPanel rootPanel;
    private JTextField txtURL;
    private JButton btnGenerate;
    private JRadioButton rdoJPG;
    private JRadioButton rdoPNG;
    private JRadioButton rdoGIF;
    private JComboBox cmbColor;
    private JSlider slider1;
    private JLabel lblSize;


    public QRGenUi() {

        setTitle("QR Generator");
        getRootPane().setDefaultButton(btnGenerate);
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        btnGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!txtURL.getText().isEmpty()){
                    Color color;
                    String selectedFileType = getFileType();

                    try {
                        Field field = Class.forName("java.awt.Color").getField(cmbColor.getSelectedItem().toString().toUpperCase());
                        color = (Color)field.get(null);
                    } catch (Exception ex) {
                        color = null; // Not defined
                    }

                    QrGenerator.generateQR(txtURL.getText(),selectedFileType,color,slider1.getValue());

                } else
                    JOptionPane.showMessageDialog(QRGenUi.this, "Please enter a URL","Warning",JOptionPane.OK_OPTION);

            }
        });

        slider1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                String value = "Size: ";
                lblSize.setText(value+slider1.getValue());
            }
        });
    }

    private String getFileType(){
        if(rdoJPG.isSelected())
        {
            return rdoJPG.getText();
        }
        else if(rdoPNG.isSelected())
        {
            return rdoPNG.getText();
        }
        else
        {
            return rdoGIF.getText();
        }
    }
}
