import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.*;
public class FrmEmployee extends JInternalFrame{
	JTable jtb;
	JScrollPane jsp;
	JPanel panButtons;
	JButton btnAdd,btnUpdate,btnDelete,btnSearch,btnShowAll;
	String heads[]= {"Employee Id","Employee Name","Address"};
	Object data[][];
	String condition="";
	FrmEmployee(JDesktopPane jdp){
		super("Employee Details",true,true,true,true);
		
		btnAdd=new JButton("Add");
		btnAdd.setForeground(Color.white);
		btnAdd.setBackground(Color.blue);
		btnAdd.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				FrmAddEditEmployee cf2=new FrmAddEditEmployee(FrmEmployee.this,"");
				jdp.add(cf2);
				jdp.setComponentZOrder(cf2, 0);
				jdp.setComponentZOrder(FrmEmployee.this, 1);
			}
		});
		
		btnUpdate=new JButton("Update");
		btnUpdate.setForeground(Color.white);
		btnUpdate.setBackground(Color.blue);
		btnUpdate.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int row=jtb.getSelectedRow();
				if(row==-1) {
					JOptionPane.showMessageDialog(FrmEmployee.this, "No record selected");
					return;
				}
				int eid=Integer.parseInt((String)jtb.getValueAt(row, 0));
				FrmAddEditEmployee cf2=new FrmAddEditEmployee(FrmEmployee.this,"select * from employee where eid="+eid);
				jdp.add(cf2);
				jdp.setComponentZOrder(cf2, 0);
				jdp.setComponentZOrder(FrmEmployee.this, 1);
			}
		});
		
		btnDelete=new JButton("Delete");
		btnDelete.setForeground(Color.white);
		btnDelete.setBackground(Color.blue);
		btnDelete.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int row=jtb.getSelectedRow();
				if(row==-1) {
					JOptionPane.showMessageDialog(FrmEmployee.this, "No record selected");
					return;
				}
				int eid=Integer.parseInt((String)jtb.getValueAt(row, 0));
				DConnection dc=new DConnection();
				dc.executeOtherQuery("delete from employee where eid="+eid);
				showTable();
			}
		});
		
		btnSearch=new JButton("Search");
		btnSearch.setForeground(Color.white);
		btnSearch.setBackground(new Color(128,0,255));
		btnSearch.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				FrmEmployeeSearch cf2=new FrmEmployeeSearch(FrmEmployee.this);
				jdp.add(cf2);
				jdp.setComponentZOrder(cf2, 0);
				jdp.setComponentZOrder(FrmEmployee.this, 1);
			}
		});
		
		btnShowAll=new JButton("Show All");
		btnShowAll.setForeground(Color.white);
		btnShowAll.setBackground(new Color(128,0,255));	
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
		
		setSize(500,400);
		Point p1=CommonMethods.getCenter(getSize());
		setLocation(p1.x,p1.y-50);
		setVisible(true);
	}
	void showTable() {
		try {
			if(jsp!=null)
				remove(jsp);
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select count(*) from employee"+condition);
			rst.next();
			int n=rst.getInt(1);
			dc.close();
			data=new Object[n][3];
			rst=dc.executeSelectQuery("select * from employee "+condition);
			for(int i=0;rst.next();i++) {
				for(int j=0;j<3;j++)
					data[i][j]=rst.getString(j+1);
			}
			jtb=new JTable(data,heads);
			jtb.setRowHeight(25);
		    jtb.setRowMargin(5);
		    Dimension d1=new Dimension(5,5);
		    jtb.setIntercellSpacing(d1);
		    jtb.setGridColor(Color.black);
		    jtb.setShowGrid(true);
		    jtb.setForeground(Color.blue);
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
