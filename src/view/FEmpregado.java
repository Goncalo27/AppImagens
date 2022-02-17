package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public class FEmpregado {
    private JPanel panelEmpregado;
    private JTextField textFieldIDEmp;
    private JTextField textFieldNomeEmp;
    private JTextField textFieldSalarioEmp;
    private JButton saveButton;
    private JButton browseButton;
    private JLabel labelImagem;

    private String path=null;
    private byte[] userImage;
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    public void setVisible(boolean b)
    {
        JFrame frame=new JFrame("Ficha Empregado");
        frame.setContentPane(new FEmpregado().panelEmpregado);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.pack();
        frame.setSize(800,700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(b);
    }

    public FEmpregado() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nome=textFieldNomeEmp.getText();
                double salario=Double.valueOf(textFieldSalarioEmp.getText());
                try
                {
                    Class.forName("com.mysql.jdbc.Driver");
                    con= DriverManager.getConnection("jdbc:mysql://localhost:3306/bdempregados", "root", "1234");
                    ps=con.prepareStatement("insert into empregados(nome, salario, foto) values(?,?,?)");
                    ps.setString(1,nome);
                    ps.setDouble(2,salario);
                    ps.setBytes(3,userImage);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Empregado guardado com sucesso");
                    textFieldIDEmp.setText("");
                    textFieldNomeEmp.setText("");
                    textFieldSalarioEmp.setText("");
                    labelImagem.setIcon(null);

                }catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });


        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser imgChoosed=new JFileChooser();
                imgChoosed.showOpenDialog(null);
                File file=imgChoosed.getSelectedFile();

                path=file.getAbsolutePath();
                BufferedImage img;
                try{
                    //prepara a imagem para apresentar na label (icon)
                    img= ImageIO.read(imgChoosed.getSelectedFile());
                    ImageIcon imageIcon=new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT));
                    labelImagem.setIcon(imageIcon);
                    //guardar a imagem na variavel byte[] userImage para
                    //ser depois guardada na base de dados
                    File image=new File (path);
                    FileInputStream fs=new FileInputStream(image);
                    ByteArrayOutputStream bos=new ByteArrayOutputStream();
                    byte[] buff=new byte[1024];
                    int nBytesRead=0;
                    while((nBytesRead=fs.read(buff)) !=-1)

                        bos.write(buff, 0, nBytesRead);
                    userImage =bos.toByteArray();
                }catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
