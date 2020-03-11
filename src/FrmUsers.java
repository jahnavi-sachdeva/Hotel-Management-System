
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.*;
public class FrmUsers extends JInternalFrame{
	JTable jtb;
	JScrollPane jsp;
	JPanel panButtons;
	JButton btnAdd,btnUpdate,btnDelete,btnSearch,btnShowAll;
	//bookingid int primary key,roomnumber int,guestid int,date date,mobileno varchar(10),checkin date,checkout date,name varchar(10)
	String heads[]= {"userName","Password","UserType"};
	Object data[][];
	String condition="";
	FrmUsers(JDesktopPane jdp){
		super("users Details",true,true,true,true);
		//System.out.println("in bb");
		btnAdd=new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				FrmAddEditUsers cf2=new FrmAddEditUsers(FrmUsers.this,"");
				jdp.add(cf2);
				jdp.setComponentZOrder(cf2, 0);
				jdp.setComponentZOrder(FrmUsers.this, 1);
			}
		});
		
		btnUpdate=new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int row=jtb.getSelectedRow();
				if(row==-1) {
					JOptionPane.showMessageDialog(FrmUsers.this, "No record selected");
					return;
				}
				String user=(String)jtb.getValueAt(row, 0);
				FrmAddEditUsers cf2=new FrmAddEditUsers(FrmUsers.this,"select * from users where user='"+user+"'");
				jdp.add(cf2);
				jdp.setComponentZOrder(cf2, 0);
				jdp.setComponentZOrder(FrmUsers.this, 1);
			}
		});
		
		btnDelete=new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int row=jtb.getSelectedRow();
				if(row==-1) {
					JOptionPane.showMessageDialog(FrmUsers.this, "No record selected");
					return;
				}
				String user =(String)jtb.getValueAt(row, 0);
				DConnection dc=new DConnection();
				dc.executeOtherQuery("delete from users where user='"+user+"'");
				showTable();
			}
		});
		
		btnSearch=new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				FrmSearchUsers cf2=new FrmSearchUsers(FrmUsers.this);
				jdp.add(cf2);
				jdp.setComponentZOrder(cf2, 0);
				jdp.setComponentZOrder(FrmUsers.this, 1);
			}
		});
		
		btnShowAll=new JButton("Show All");
		btnShowAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				condition="";
				showTable();
			}
		});
		
		panButtons=new JPanel();
		panButtons.setLayout(new GridLayout(1,5));
		panButtons.add(btnAdd);
		panButtons.add(btnUpdate);
		panButtons.add(btnDelete);
		panButtons.add(btnSearch);
		panButtons.add(btnShowAll);
		
		add(panButtons,"South");
		
		//Table
		showTable();
		
		setSize(600,400);
		//Point p1=CommonMethods.getCenter(getSize());
		//setLocation(p1.x,p1.y-50);
		setLocation(300,100);
		setVisible(true);
	}
	 void showTable() {
		try {
			if(jsp!=null)
				remove(jsp);
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select count(*) from Users"+condition);
			rst.next();
			int n=rst.getInt(1);
			dc.close();
			data=new Object[n][3];
			rst=dc.executeSelectQuery("select * from Users"+condition);
			for(int i=0;rst.next();i++) {
				for(int j=0;j<3;j++)
					data[i][j]=rst.getString(j+1);
			}
			jtb=new JTable(data,heads);
			jsp=new JScrollPane(jtb);
			add(jsp);
			revalidate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
}


