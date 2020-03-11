import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
public class FrmSignUp extends JFrame{
	JLabel lblUser,lblPass,lblRetype;
	JTextField jtfUser;
	JPasswordField jpfPass,jpfRetype;
	JButton btnCreate,btnClose;
	JCheckBox jcbShowPass;
	FrmSignUp(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(5,2,20,20));
		lblUser=new JLabel("User");
		lblPass=new JLabel("Password");
		lblRetype=new JLabel("Retype Password");
		jtfUser=new JTextField();
		jpfPass=new JPasswordField();
		jpfRetype=new JPasswordField();
		jpfPass.setEchoChar('*');
		jpfRetype.setEchoChar('*');
		btnCreate=new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				DConnection dc=new DConnection();
				String s1=jtfUser.getText();
				String s2=new String(jpfPass.getPassword());
				String s3=new String(jpfRetype.getPassword());
				if(!s2.equals(s3)) {
					JOptionPane.showMessageDialog(FrmSignUp.this, "Password and Retype Password Mismatch");
					return;
				}
				int cnt=dc.executeOtherQuery("insert into users(user,pass,usertype) values('"+s1+"','"+s2+"','admin')");
				if(cnt==1)
					JOptionPane.showMessageDialog(FrmSignUp.this, "User created sucessfully");
				dispose();
				new FrmLogin();
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
					jpfRetype.setEchoChar((char)0);
				}
				else {
					jpfPass.setEchoChar('*');
					jpfRetype.setEchoChar('*');
				}
			}
		});
		add(lblUser);add(jtfUser);
		add(lblPass);add(jpfPass);
		add(lblRetype);add(jpfRetype);
		add(btnCreate);add(btnClose);
		add(jcbShowPass);
		setTitle("Sign Up");
		setSize(400,300);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
