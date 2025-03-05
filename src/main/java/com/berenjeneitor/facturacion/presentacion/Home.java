package com.berenjeneitor.facturacion.presentacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Home extends JPanel {
    public Home() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // Declaración de componentes
        JLabel lblEmpresa = new JLabel("Mi empresa");
        lblEmpresa.setFont(new Font("Arial", Font.BOLD, 20));
        ImageIcon imgEmpresa = new ImageIcon("src/main/resources/empresa.png");
        imgEmpresa.setImage(imgEmpresa.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        JLabel lblImgEmpresa = new JLabel(imgEmpresa);
        JTable tblFacturas = createStatisticsTable();



        // Añadir componentes al layout
        add(lblEmpresa, gbc);
        add(lblImgEmpresa, gbc);
        add(new JScrollPane(tblFacturas), gbc);
    }
    private JTable createStatisticsTable(){
        DefaultTableModel dtm = new DefaultTableModel(new Object[]{"Tipo", "Cantidad"}, 0);
        //TODO: Here will be the logic to get the statistics from the database, the statisistics will be the number of each entity stores by the moment
        return new JTable(dtm);
    }
}
