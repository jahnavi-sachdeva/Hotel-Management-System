import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.*;
public class FrmAdditional extends JInternalFrame{
	JTable jtb;
	JScrollPane jsp;
	JPanel panButtons;
	JButton btnAdd,btnUpdate,btnDelete,btnSearch,btnShowAll;
	String heads[]= {"ID","Date","Roomnumber","Particulars","Description","Billno","Amount"};
	Object data[][];
	String condition="";
	FrmAdditional(JDesktopPane jdp){
		super("Additional Charges",true,true,true,true);
		
		btnAdd=new JButton("Add");
		btnAdd.setForeground(Color.white);
		btnAdd.setBackground(Color.blue);
		btnAdd.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				FrmAddEditAdditional cf2=new FrmAddEditAdditional(FrmAdditional.this,"");
				jdp.add(cf2);
				jdp.setComponentZOrder(cf2, 0);
				jdp.setComponentZOrder(FrmAdditional.this, 1);
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
					JOptionPane.showMessageDialog(FrmAdditional.this, "No record selected");
					return;
				}
				int id=Integer.parseInt((String)jtb.getValueAt(row, 0));
				FrmAddEditAdditional cf2=new FrmAddEditAdditional(FrmAdditional.this,"select * from addbill where id="+id);
				jdp.add(cf2);
				jdp.setComponentZOrder(cf2, 0);
				jdp.setComponentZOrder(FrmAdditional.this, 1);
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
					JOptionPane.showMessageDialog(FrmAdditional.this, "No record selected");
					return;
				}
				int id=Integer.parseInt((String)jtb.getValueAt(row, 0));
				DConnection dc=new DConnection();
				dc.executeOtherQuery("delete from addbill where id="+id);
				showTable();
			}
		});
		
		btnSearch=new JButton("Search");
		btnSearch.setForeground(Color.white);
		btnSearch.setBackground(new Color(128,0,255));
		btnSearch.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				FrmAdditionalSearch cf2=new FrmAdditionalSearch(FrmAdditional.this);
				jdp.add(cf2);
				jdp.setComponentZOrder(cf2, 0);
				jdp.setComponentZOrder(FrmAdditional.this, 1);
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
			ResultSet rst=dc.executeSelectQuery("select count(*) from addbill"+condition);
			rst.next();
			int n=rst.getInt(1);
			dc.close();
			data=new Object[n][7];
			rst=dc.executeSelectQuery("select * from addbill "+condition);
			for(int i=0;rst.next();i++) {
				for(int j=0;j<7;j++) {
					if(j==1)
						data[i][j]=CommonMethods.convertDateToReadableFormat(rst.getString(j+1));
					else
					   data[i][j]=rst.getString(j+1);
				}	
			}
			jtb=new MyJTable(data,heads);
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
class MyJTable extends JTable{
	MyJTable(Object [][]data,String heads[]){
		super(data,heads);
	}
	public boolean isCellEditable(int r,int c) {
		return false;
	}
}
