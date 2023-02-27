import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ingrso_fechas extends JDialog{
    PreparedStatement ps;
    private JButton OKButton;
    private JComboBox dia_box;
    private JComboBox mes_box;
    private JComboBox anio_box;
    private JPanel Vetana;


    public ingrso_fechas(){
        llenarComboBox_meses();
        llenarComboBox_dia();
        llenarComboBox_anios();

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection con;
                try {
                    con = getConection();

                    int dia = dia_box.getSelectedIndex();
                    int anio = anio_box.getSelectedIndex();
                    Date fecha = new Date(anio,1,dia);
                    ps = con.prepareStatement("Insert into fecha(fecha ) values (?)");
                    ps.setString(1, fecha.toString());
                    //ps.setString(1, dia_box.getSelectedItem().toString());
                    //ps.setString(2, "1"); //mes_box.getSelectedItem().toString()
                    //ps.setString(3, "1980"); //anio_box.getSelectedItem().toString()

                    System.out.println(ps);
                    int res = ps.executeUpdate();

                    if (res > 0) {
                        JOptionPane.showMessageDialog(null, "Mes guardado");
                    } else {
                        JOptionPane.showMessageDialog(null, "Guardar Mes");
                    }

                    con.close();

                } catch (HeadlessException | SQLException f) {
                    System.err.println(f);
                }
            }
        });

    }
    private void llenarComboBox_dia(){
        Connection con;
        try {
            con = getConection();
            String query = "SELECT * FROM dias_table";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                dia_box.addItem(rs.getInt(1));
            }
            con.close();

        } catch (HeadlessException | SQLException f) {
            System.err.println(f);
        }
    }

    private void llenarComboBox_meses(){
        Connection con;
        try {
            con = getConection();
            String query = "SELECT * FROM meses_table";
            System.out.println("xd");

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                mes_box.addItem(rs.getString(1));
            }
            con.close();

        } catch (HeadlessException | SQLException f) {
            System.err.println(f);
        }
    }

    private void llenarComboBox_anios(){
        Connection con;
        try {
            con = getConection();
            String query = "SELECT * FROM anios_table";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                anio_box.addItem(rs.getInt(1));
            }
            con.close();

        } catch (HeadlessException | SQLException f) {
            System.err.println(f);
        }
    }
    public static Connection getConection(){
        Connection con = null;
        String url = "jdbc:mysql://localhost/recuperacion",
                user = "root",
                password = "UGPCUGR2002";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e);
        }
        return con;
    }


    public static void main(String[] args) {
        JFrame frame =new JFrame("Néstor Chumania");

        frame.setContentPane(new ingrso_fechas().Vetana);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,1000);
        frame.pack();
        frame.setVisible(true);
    }
}
