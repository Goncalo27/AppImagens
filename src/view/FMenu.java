package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FMenu {
    private JButton adicionarButton;
    private JButton consultarButton;
    private JPanel panelMenu;

    public static void main(String[] args) {
        JFrame frame=new JFrame("Gestão de Empregados");
        frame.setContentPane(new FMenu().panelMenu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public FMenu() {
        adicionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FEmpregado().setVisible(true);

            }
        });
        consultarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormConsulta().setVisible(true);

            }
        });
    }
}
