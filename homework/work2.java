package work2;

import work1.UserLogin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class work2 {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        new DataQuery();
    }

}
class AccessDatabase{
    Connection connection;
    public boolean link() throws ClassNotFoundException, SQLException {
        Class.forName("com.hxtt.sql.access.AccessDriver");//加载驱动
        connection = DriverManager.getConnection("jdbc:Access:///student.accdb");
        if(connection!=null)
            return true;
        else
            return false;
    }
    public boolean login(String userName,String passWord) throws SQLException {
        String sql = "select * from studentTable";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            String userTemp = rs.getString("number");
            String passTemp = rs.getString("password");
            if(userTemp.equals(userName) && passTemp.equals(passWord)){
                return true;
            }
        }
        rs.close();
        stmt.close();
        return false;
    }
}
class DataQuery extends JFrame implements ActionListener {
    AccessDatabase DataBase;
    JTextField username = new JTextField();
    JPasswordField password = new JPasswordField();
    JButton login = new JButton("登陆");
    JButton exit = new JButton("退出");

    public UserLogin() throws SQLException, ClassNotFoundException {
        initJFrame();
        initmodule();
        this.setVisible(true);
    }

    private void initmodule() {
        JLabel jlb1 = new JLabel("用户");
        JLabel jlb2 = new JLabel("口令");
        jlb1.setBounds(30,20,30,20);
        jlb2.setBounds(30,50,30,20);
        username.setBounds(65,20,170,25);
        password.setBounds(65,50,170,25);
        login.setBounds(50,90,60,30);
        exit.setBounds(170,90,60,30);
        this.getLayeredPane().add(jlb1);
        this.getLayeredPane().add(jlb2);
        this.getLayeredPane().add(username);
        this.getLayeredPane().add(password);
        this.getLayeredPane().add(login);
        this.getLayeredPane().add(exit);
        login.addActionListener(this);
        exit.addActionListener(this);
    }

    private void initJFrame() {
        this.setSize(300,200);
        this.setTitle("用户登录");
        this.setLocationRelativeTo(null);//居中
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);//关闭模式设置
        this.setLayout(null);//清除格式

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DataBase = new AccessDatabase();
        Object o = e.getSource();
        if(o==exit){
            System.exit(0);
        }else if(o==login){
            String user = username.getText();
            String pass = new String(password.getPassword());
            if(user.isEmpty() || pass.isEmpty()){
                JOptionPane.showMessageDialog(null,"用户或口令不能为空！");
                return;
            }
            try {
                if(!DataBase.link()){
                    JOptionPane.showMessageDialog(null,"数据库连接失败！");
                    return;
                }
            } catch (ClassNotFoundException | SQLException ex) {
                throw new RuntimeException(ex);

            }
            try {
                if(DataBase.login(user,pass)){
                    JOptionPane.showMessageDialog(null,"登录成功！");
                }else{
                    JOptionPane.showMessageDialog(null,"用户或口令错误！");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"SQL语句语法错误！");
            }

        }

    }
}
