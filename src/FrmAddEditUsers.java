import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.Date;
import java.text.*;

public class FrmAddEditUsers extends JInternalFrame {
	// Usersid int primary key,roomnumber int,guestid int,dob
	// date,mobileno.,checkin date,checkout date
	JLabel lblUser,lblPassword,lblRetype,lblUserType;
	JComboBox<String> jcbUserType;
	JTextField jtfUser,jtfPassword,jtfRetype;
	JButton btnSave, btnClose;
	String btnLabel="Save";

	FrmAddEditUsers(FrmUsers frmUsers,String query){
		super("Add/Edit Users",true,true,true,true);
		setLayout(new GridLayout(5,2));
		lblUser=new JLabel("Users id");
		lblPassword=new JLabel("Password");
		lblRetype=new JLabel("Retype Password");
		lblUserType=new JLabel("User Type");
		jtfUser=new JTextField();
		if(!query.equals("")) {
			jtfUser.setEditable(false);	
		}
		jtfPassword=new JTextField();
		jtfRetype=new JTextField();
		jcbUserType=new JComboBox<String>();
		jcbUserType.addItem("admin");
		jcbUserType.addItem("staff");
		jcbUserType.addItem("manager");
		if(!query.equals("")) {
			try {
				DConnection dc=new DConnection();
				ResultSet rst=dc.executeSelectQuery(query);
				rst.next();
				jtfUser.setText(rst.getString(1));
				jtfPassword.setText(rst.getString(2));
				jtfRetype.setText(rst.getString(2));
				jcbUserType.setSelectedItem(rst.getString(3));
				dc.close();
				btnLabel="Update";
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		//bid int primary key,name varchar(10),roomnumber int,guestid int unique key,mobileno varchar(11),date date,checkin date,checkout date)
		btnSave=new JButton(btnLabel);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				String s1=jtfUser.getText();
				String s2=jtfPassword.getText();
				String s3=(String)jcbUserType.getSelectedItem();
				String s4=jtfRetype.getText();
				try{
					DConnection d=new DConnection();
					ResultSet r=d.executeSelectQuery("select * from users where user='"+s1+"'");
					if(query.equals("") && r.next()){
						d.close();
						JOptionPane.showMessageDialog(FrmAddEditUsers.this,"User already exists."+"\n"+"try with some other userid or password","Info",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					else if(!s2.equals(s4)){
						d.close();
						JOptionPane.showMessageDialog(FrmAddEditUsers.this,"Password and Retype mismatch","Info",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					else{
						d.close();
						if(query.equals(""))
							d.executeOtherQuery("insert into users values('"+s1+"','"+s2+"','"+s3+"')");
						else
							d.executeOtherQuery("update users set pass='"+s2+"',usertype='"+s3+"' where user='"+s1+"'" );
						frmUsers.showTable();
						dispose();
					}
				}
				catch(SQLException e){
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

		add(lblUser);add(jtfUser);
		add(lblPassword);add(jtfPassword);
		add(lblRetype);add(jtfRetype);
		add(lblUserType);add(jcbUserType);
		add(btnSave);add(btnClose);
		
		setSize(400,300);
		setLocation(CommonMethods.getCenter(getSize()));
		setVisible(true);
	}
}
	

