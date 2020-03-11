import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.*;
public class FrmPrepareBill extends JInternalFrame{
	JTable jtb;
	JScrollPane jsp;
	JPanel panButtons;
	JButton btnAdd,btnClose;
	String heads[]= {"Bill Number","Date","Room Number"};
	Object data[][];
	String condition="";
	FrmPrepareBill(JDesktopPane jdp){
		super("Prepare Bill Details",true,true,true,true);
		
		btnAdd=new JButton("Add");
		btnAdd.setForeground(Color.white);
		btnAdd.setBackground(Color.blue);
		btnAdd.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		btnAdd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
                 FrmAddPrepareBill cf1=new FrmAddPrepareBill(FrmPrepareBill.this);
			     jdp.add(cf1);
			     jdp.setComponentZOrder(cf1,0);
			     jdp.setComponentZOrder(FrmPrepareBill.this,1);
			}
		});
		
  /*		
		btnShowBill=new JButton("Show Bill");
		btnShowBill.setForeground(Color.white);
		btnShowBill.setBackground(Color.blue);
		btnShowBill.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		btnShowBill.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
//                 jdp.add(new FrmAdditional(jdp));
//			     jdp.add(cf1);
			jdp.setComponentZOrder(jdp.add(new FrmAdditional(jdp)),0);
//			     jdp.setComponentZOrder(FrmPrepareBill.this,1);
			}
		});
	*/	
		
		btnClose=new JButton("Close");
		btnClose.setForeground(Color.white);
		btnClose.setBackground(Color.blue);
		btnClose.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				dispose();
			}
		});
		
		
		panButtons=new JPanel();
		panButtons.setLayout(new GridLayout(1,2));
		panButtons.add(btnAdd);
//		panButtons.add(btnShowBill);
		panButtons.add(btnClose);
		
		add(panButtons,"South");
		
		//Table
		showTable();
		
		setSize(500,400);
		Point p1=CommonMethods.getCenter(getSize());
		setLocation(p1.x,p1.y-50);
//		jtb.isCellEditable(0,1);
//		jtb.isCellEditable(0,2);
//		jtb.isCellEditable(0,3);
		setVisible(true);
	 }
	/* 
	boolean isCellEditable(int row,int col){
		if(col==1 || col==2 || col==3)
			return false;
		else
			return true;
	}
    */	 
	 int maxPrepareBillId(){
		 int n=0;	
		 try{
		    DConnection dc=new DConnection();
	    	ResultSet rst=dc.executeSelectQuery("select count(*) from "
	    			+ "prepbill");
	    	rst.next();
	        n=rst.getInt(1);
	    	n=n+1;
	    	rst.close();
//	    	String a=Integer.toString(n);
	    	dc.close();
	    	}
	    	catch(SQLException e) {
	    		e.printStackTrace();
	    	}
	    	return n;
	}
      	
	 
	 
		void showTable(){
			try{
				if(jsp!=null)
				remove(jsp);
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select count(*) "
					+ "from prepbill "+condition);
			rst.next();
			int n=rst.getInt(1);
			rst=dc.executeSelectQuery("select * from prepbill "
					+condition);
//			rst.next();
			Object data[][]=new Object[n][3];
			for(int i=0;rst.next();i++){
				for(int j=0;j<=2;j++){
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
