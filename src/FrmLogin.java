import java.awt.event.*;
import java.sql.*;
import java.awt.*;
import javax.swing.*;
public class FrmLogin extends JFrame{
	JLabel lblUser,lblPass;
	JTextField jtfUser;
	JPasswordField jpfPass;
	JButton btnLogin,btnClose;
	JCheckBox jcbShowPass;
	FrmLogin(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(4,2,20,20));
		lblUser=new JLabel("User");
		lblPass=new JLabel("Password");
		jtfUser=new JTextField();
		jpfPass=new JPasswordField();
		jpfPass.setEchoChar('*');
		btnLogin=new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					DConnection dc=new DConnection();
					String s1=CommonMethods.textValidation(jtfUser.getText());
					String s2=CommonMethods.textValidation(new String(jpfPass.getPassword()));
					ResultSet rst=dc.executeSelectQuery("select * from users where user='"+s1+"' and pass='"+s2+"'");
					if(rst.next()) {
						String userType=rst.getString(3);
						dc.close();
						dispose();
						new FrmMainFrame(s1,userType);
					}
					else {
						dc.close();
						JOptionPane.showMessageDialog(FrmLogin.this, "Inavalid user or password Try Again");
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
		});

		btnClose=new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				dispose();
			}
		});
		jcbShowPass=new JCheckBox("Show password");
		jcbShowPass.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ie) {
				if(jcbShowPass.isSelected()) {
					jpfPass.setEchoChar((char)0);
				}
				else {
					jpfPass.setEchoChar('*');
				}
			}
		});
		add(lblUser);add(jtfUser);
		add(lblPass);add(jpfPass);
		add(btnLogin);add(btnClose);
		add(jcbShowPass);
		setTitle("Login");
		setSize(400,300);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
