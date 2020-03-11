
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.*;
public class FrmCheckIn extends JInternalFrame{
	JTable jtb;
	JScrollPane jsp;
	JPanel panButtons;
	JButton btnAdd,btnUpdate,btnDelete,btnSearch,btnShowAll;
	//bookingid int primary key,dateofbooking date,checkindate date,days int,
	//checkoutdate date,adults int,kids int,catid int,roomnumber int,rent int,guestid int,estimate int,advance int
	String heads[]= {"CheckIn Id","Bookin Id","Date","CheckIn","no. of days","CheckOut","Adults Count","Kids Count","Category","Room no.","Rent","GuestId","Estimate","Advance"};
	Object data[][];
	String condition="";
	FrmCheckIn(JDesktopPane jdp){
		super("Check In",true,true,true,true);
		btnAdd=new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				FrmAddEditCheckIn cf2=new FrmAddEditCheckIn(FrmCheckIn.this,"",jdp);
				jdp.add(cf2);
				jdp.setComponentZOrder(cf2, 0);
				jdp.setComponentZOrder(FrmCheckIn.this, 1);
			}
		});
		
		btnUpdate=new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int row=jtb.getSelectedRow();
				if(row==-1) {
					JOptionPane.showMessageDialog(FrmCheckIn.this, "No record selected");
					return;
				}
				int bid=Integer.parseInt((String)jtb.getValueAt(row, 0));
				FrmAddEditCheckIn cf2=new FrmAddEditCheckIn(FrmCheckIn.this,"select * from checkin where checkinid="+bid,jdp);
				jdp.add(cf2);
				jdp.setComponentZOrder(cf2, 0);
				jdp.setComponentZOrder(FrmCheckIn.this, 1);
			}
		});
		
		btnDelete=new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int row=jtb.getSelectedRow();
				if(row==-1) {
					JOptionPane.showMessageDialog(FrmCheckIn.this, "No record selected");
					return;
				}
				int bid=Integer.parseInt((String)jtb.getValueAt(row, 0));
				DConnection dc=new DConnection();
				dc.executeOtherQuery("delete from checkin where checkinid="+bid);
				showTable();
			}
		});
		
		btnSearch=new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				FrmCheckInSearch cf2=new FrmCheckInSearch(FrmCheckIn.this);
				jdp.add(cf2);
				jdp.setComponentZOrder(cf2, 0);
				jdp.setComponentZOrder(FrmCheckIn.this, 1);
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
		
		setSize(1000,400);
		//Point p1=CommonMethods.getCenter(getSize());
		//setLocation(p1.x,p1.y-50);
		setLocation(250,100);
		setVisible(true);
	}
	void showTable() {
		try {
			if(jsp!=null)
				remove(jsp);
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select count(*) from checkin"+condition);
			rst.next();
			int n=rst.getInt(1);
			dc.close();
			data=new Object[n][14];
			rst=dc.executeSelectQuery("select * from checkin"+condition);
			for(int i=0;rst.next();i++) {
				for(int j=0;j<14;j++) {
					if(j==2 || j==3 || j==5)
						data[i][j]=CommonMethods.convertDateToReadableFormat(rst.getString(j+1));
					else
						data[i][j]=rst.getString(j+1);
				}
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

