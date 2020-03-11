import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
public class FrmAddEditEmployee extends JInternalFrame {
	JLabel lblEid,lblName,lblAddress;
	JTextField jtfEid,jtfName,jtfAddress;
	JButton btnSave,btnClose;
	String btnLabel="Save";
	FrmAddEditEmployee(FrmEmployee frmEmployee,String query){
		super("Add/Edit Employee",true,true,true,true);
		setLayout(new GridLayout(4,2));

		lblEid=new JLabel("Eid");
		lblEid.setForeground(new Color(128,0,255));
		lblEid.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));
		lblName=new JLabel("Name");
		lblName.setForeground(new Color(128,0,255));
		lblName.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));
		lblAddress=new JLabel("Address");
		lblAddress.setForeground(new Color(128,0,255));
		lblAddress.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));
		jtfEid=new JTextField();
		jtfEid.setForeground(new Color(128,0,255));
		jtfEid.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));
		jtfName=new JTextField();
		jtfName.setForeground(new Color(128,0,255));
		jtfName.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));
		jtfAddress=new JTextField();
		jtfAddress.setForeground(new Color(128,0,255));
		jtfAddress.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));
		jtfEid.setEnabled(false);
		if(query.equals("")) {
			jtfEid.setText(maxEmployeeId()+1+"");
		}
		else {
			try {
				DConnection dc=new DConnection();
				ResultSet rst=dc.executeSelectQuery(query);
				rst.next();
				jtfEid.setText(rst.getString(1));
				jtfName.setText(rst.getString(2));
				jtfAddress.setText(rst.getString(3));
				dc.close();
				btnLabel="Update";
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		btnSave=new JButton(btnLabel);
		btnSave.setForeground(Color.white);
		btnSave.setBackground(new Color(128,0,255));	
		btnSave.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String s1=jtfEid.getText();
				String s2=jtfName.getText();
				String s3=jtfAddress.getText();
				DConnection dc=new DConnection();
				if(query.equals(""))
					dc.executeOtherQuery("insert into employee values("+s1+",'"+s2+"','"+s3+"')");
				else
					dc.executeOtherQuery("update employee set ename='"+s2+"',address='"+s3+"' where eid="+s1);
				frmEmployee.showTable();
				dispose();
			}
		});

		btnClose=new JButton("Close");
		btnClose.setForeground(Color.white);
		btnClose.setBackground(new Color(128,0,255));	
		btnClose.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				dispose();
			}
		});

		add(lblEid);add(jtfEid);
		add(lblName);add(jtfName);
		add(lblAddress);add(jtfAddress);
		add(btnSave);add(btnClose);
		
		setSize(400,300);
		setLocation(CommonMethods.getCenter(getSize()));
		setVisible(true);
	}
	int maxEmployeeId() {
		int max=0;
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select max(eid) from employee");
			rst.next();
			max=rst.getInt(1);
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return max;
	}

}
