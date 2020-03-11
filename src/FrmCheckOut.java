
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.*;
public class FrmCheckOut extends JInternalFrame{
	JTable jtb;
	JScrollPane jsp;
	JPanel panButtons;
	JButton btnAdd,btnDelete,btnSearch,btnShowAll;
	String heads[]= {"BillNo","Date","Room No.","Total Rent","Guest","Final Bill"};
	Object data[][];
	String condition="";
	FrmCheckOut(JDesktopPane jdp){
		super("Check Out",true,true,true,true);
		btnAdd=new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				FrmAddEditCheckOut cf2=new FrmAddEditCheckOut(FrmCheckOut.this,"",jdp);
				jdp.add(cf2);
				jdp.setComponentZOrder(cf2, 0);
				jdp.setComponentZOrder(FrmCheckOut.this, 1);

			}
		});
		
		btnDelete=new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int row=jtb.getSelectedRow();
				if(row==-1) {
					JOptionPane.showMessageDialog(FrmCheckOut.this, "No record selected");
					return;
				}
				int bid=Integer.parseInt((String)jtb.getValueAt(row, 0));
				DConnection dc=new DConnection();
				dc.executeOtherQuery("delete from bill where billnumber="+bid);
				showTable();
			}
		});
		
		btnSearch=new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				FrmCheckOutSearch cf2=new FrmCheckOutSearch(FrmCheckOut.this);
				jdp.add(cf2);
				jdp.setComponentZOrder(cf2, 0);
				jdp.setComponentZOrder(FrmCheckOut.this, 1);

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
		panButtons.add(btnDelete);
		panButtons.add(btnSearch);
		panButtons.add(btnShowAll);
		
		add(panButtons,"South");
		
		//Table
		showTable();
		
		setSize(1000,400);
		setLocation(250,100);
		setVisible(true);
	}
	void showTable() {
		try {
			if(jsp!=null)
				remove(jsp);
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select count(*) from bill"+condition);
			rst.next();
			int n=rst.getInt(1);
			dc.close();
			data=new Object[n][6];
			rst=dc.executeSelectQuery("select * from bill"+condition);
			for(int i=0;rst.next();i++) {
				for(int j=0;j<6;j++) {
					if(j==1)
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

