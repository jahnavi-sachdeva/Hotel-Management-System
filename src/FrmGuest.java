import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.*;
public class FrmGuest extends JInternalFrame{
	JTable jtb;
	JScrollPane jsp;
	JPanel panButtons;
	JButton btnAdd,btnUpdate,btnDelete,btnSearch,btnShowAll;
	String heads[]= {"GuestId","Name","Address","Date of birth","City","State","Contact"};
	Object data[][];
	String condition="";
	FrmGuest(JDesktopPane jdp){
		super("Guest Details",true,true,true,true);
		
		btnAdd=new JButton("Add");
		btnAdd.setForeground(Color.white);
		btnAdd.setBackground(Color.gray);
		btnAdd.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				FrmAddEditGuest cf2=new FrmAddEditGuest(FrmGuest.this,"");
				jdp.add(cf2);
				jdp.setComponentZOrder(cf2, 0);
				jdp.setComponentZOrder(FrmGuest.this, 1);
			}
		});
		
		btnUpdate=new JButton("Update");
		btnUpdate.setForeground(Color.white);
		btnUpdate.setBackground(Color.gray);
		btnUpdate.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int row=jtb.getSelectedRow();
				if(row==-1) {
					JOptionPane.showMessageDialog(FrmGuest.this, "No record selected");
					return;
				}
				int GuestId=Integer.parseInt((String)jtb.getValueAt(row, 0));
				FrmAddEditGuest cf2=new FrmAddEditGuest(FrmGuest.this,"select * from guest where GuestId="+GuestId);
				jdp.add(cf2);
				jdp.setComponentZOrder(cf2, 0);
				jdp.setComponentZOrder(FrmGuest.this, 1);
			}
		});
		
		btnDelete=new JButton("Delete");
		btnDelete.setForeground(Color.white);
		btnDelete.setBackground(Color.gray);
		btnDelete.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int row=jtb.getSelectedRow();
				if(row==-1) {
					JOptionPane.showMessageDialog(FrmGuest.this, "No record selected");
					return;
				}
				int GuestId=Integer.parseInt((String)jtb.getValueAt(row, 0));
				DConnection dc=new DConnection();
				dc.executeOtherQuery("delete from Guest where GuestId="+GuestId);
				showTable();
			}
		});
		
		btnSearch=new JButton("Search");
		btnSearch.setForeground(Color.white);
		btnSearch.setBackground(Color.gray);
		btnSearch.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				FrmGuestSearch cf2=new FrmGuestSearch(FrmGuest.this);
				jdp.add(cf2);
				jdp.setComponentZOrder(cf2, 0);
				jdp.setComponentZOrder(FrmGuest.this, 1);
			}
		});
		
		btnShowAll=new JButton("Show All");
		btnShowAll.setForeground(Color.white);
		btnShowAll.setBackground(Color.gray);	
		btnShowAll.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
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
		
		setSize(700,400);
		Point p1=CommonMethods.getCenter(getSize());
		setLocation(p1.x,p1.y-50);
		setVisible(true);
	}
	void showTable() {
		try {
			if(jsp!=null)
				remove(jsp);
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select count(*) from Guest"+condition);
			rst.next();
			int n=rst.getInt(1);
			dc.close();
			data=new Object[n][7];
			rst=dc.executeSelectQuery("select * from Guest "+condition);
			for(int i=0;rst.next();i++) {
				for(int j=0;j<7;j++) {
					if(j==3)
						data[i][j]=CommonMethods.convertDateToReadableFormat(rst.getString(j+1));
					else if(j==4)
						data[i][j]=CommonMethods.getCityName(rst.getInt(j+1));
					else if(j==5)
						data[i][j]=CommonMethods.getStateName(rst.getInt(j+1));
				    else
				    	data[i][j]=rst.getString(j+1);
				}
			}
			jtb=new JTable(data,heads);
			jtb.setRowHeight(25);
		    jtb.setRowMargin(5);
		    Dimension d1=new Dimension(5,5);
		    jtb.setIntercellSpacing(d1);
		    jtb.setGridColor(Color.black);
		    jtb.setShowGrid(true);
		    jtb.setForeground(Color.red);
			jtb.setBackground(new Color(255,255,255));
			jtb.setFont(new Font(Font.SERIF,Font.BOLD+Font.ITALIC,15));
			JTableHeader header=jtb.getTableHeader();
			header.setForeground(Color.white);
			header.setBackground(new Color(64,0,128));
			header.setFont(new Font(Font.SERIF,Font.BOLD,17));
			jtb.setSelectionForeground(Color.black);
			jtb.setSelectionBackground(Color.orange);
			jsp=new JScrollPane(jtb);
			add(jsp);
			revalidate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
