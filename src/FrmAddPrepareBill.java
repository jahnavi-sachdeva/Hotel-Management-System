import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
public class FrmAddPrepareBill extends JInternalFrame{
	JLabel lblBillNumber,lblDate,lblRoomNumber,lblTotalAddCharge,
	lblRoomRent,lblGrossTotal,lblDummy;
	JTextField jtfBillNumber,jtfDate,jtfTotalAddCharge,
	jtfRoomRent,jtfGrossTotal;
	JTable jtbAdd;
	JScrollPane jsp;
	JPanel pTop,pBottom;
//	JPanel panButtons;
//	JButton btnSave,btnClose;
//	JButton btnAdd,btnUpdate,btnDelete,btnSearch,btnShowAll;
	String heads[]= {"Id","Date","Room Number","Particulars","Description",
			"Bill Number","Amount"};
	Object data[][];
	JComboBox<String> jcbRoomNumber;
	JButton btnShowDetail,btnSave,btnClose;
	FrmAddPrepareBill(FrmPrepareBill frmPrepareBill){
		super("Add/Edit PrepareBill",true,true,true,true);
//	    setLayout(new GridLayout(3,1));
        
		pTop=new JPanel();
		pTop.setLayout(new GridLayout(4,2));
		pBottom=new JPanel();
		pBottom.setLayout(new GridLayout(4,2));
		
		lblBillNumber=new JLabel("Bill Number");
		lblBillNumber.setForeground(new Color(128,0,255));
		lblBillNumber.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));
		lblDate=new JLabel("Date");
		lblDate.setForeground(new Color(128,0,255));
		lblDate.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));
		lblRoomNumber=new JLabel("Room Number");
		lblRoomNumber.setForeground(new Color(128,0,255));
		lblRoomNumber.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));
		lblTotalAddCharge=new JLabel("Total Additional Charge");
		lblTotalAddCharge.setForeground(new Color(128,0,255));
		lblTotalAddCharge.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));
		lblRoomRent=new JLabel("Room Rent");
		lblRoomRent.setForeground(new Color(128,0,255));
		lblRoomRent.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));
		lblGrossTotal=new JLabel("Gross Total");
		lblGrossTotal.setForeground(new Color(128,0,255));
		lblGrossTotal.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));
		lblDummy=new JLabel(" ");
		
		jtfBillNumber=new JTextField();
		jtfBillNumber.setForeground(new Color(128,0,255));
		jtfBillNumber.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));
		jtfBillNumber.setText(Integer.toString(frmPrepareBill.maxPrepareBillId()));
		jtfBillNumber.setEnabled(false);
		jtfDate=new JTextField();
		jtfDate.setForeground(new Color(128,0,255));
		jtfDate.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));
		jtfTotalAddCharge=new JTextField();
		jtfTotalAddCharge.setForeground(new Color(128,0,255));
		jtfTotalAddCharge.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));
		jtfRoomRent=new JTextField();
		jtfRoomRent.setForeground(new Color(128,0,255));
		jtfRoomRent.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));
		jtfGrossTotal=new JTextField();
		jtfGrossTotal.setForeground(new Color(128,0,255));
		jtfGrossTotal.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));
		
		
		
		jcbRoomNumber=new JComboBox<>();
		jcbRoomNumber.setForeground(new Color(128,0,255));
		jcbRoomNumber.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));
        try {
        	DConnection dc=new DConnection();
        	ResultSet rst=dc.executeSelectQuery("select room.roomnumber "
        	+ "from room left outer join checkin on "
        	+ "room.roomnumber=checkin.roomnumber "
        	+ "where checkin.roomnumber is not null");
        	while(rst.next()) {
        		String f=rst.getString(1);
        		jcbRoomNumber.addItem(f);
        	}
        	rst.close();
        	dc.close();
        }
        catch(SQLException e) {
        	e.printStackTrace();
        }
		
        btnShowDetail=new JButton("Show Details");
        btnShowDetail.setForeground(Color.white);
        btnShowDetail.setBackground(new Color(128,0,255));	
        btnShowDetail.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
        btnShowDetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				if(jcbRoomNumber.getSelectedItem().toString()!=null)
			   showAddTable(jcbRoomNumber.getSelectedItem().toString());
			}
		});
        
        
		btnSave=new JButton("Save");
		btnSave.setForeground(Color.white);
		btnSave.setBackground(new Color(128,0,255));	
		btnSave.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
		       String billno=jtfBillNumber.getText();
		       String date=jtfDate.getText();
		       String roomno=jcbRoomNumber.getSelectedItem().toString();
		       DConnection dc=new DConnection();
		       int cnt=dc.executeOtherQuery("insert into prepbill"
		       +"(billnumber,date,roomnumber) values('"+billno+"',"
		       		+ "'"+date+"','"+roomno+"')");
		       dc.close();
		       frmPrepareBill.showTable();
		       dispose();
			}
		});
		JRootPane rootPane=getRootPane();
		rootPane.setDefaultButton(btnSave);
		
		/*
		addKeyListener(new KeyAdapter(){
 		   public void keyPressed(KeyEvent ke){
 			   char ch=ke.getKeyChar();
 			   if(ch==KeyEvent.VK_ENTER)
 				   btnSave.doClick();
 			   }
 		   });
 		*/
		btnClose=new JButton("Close");
		btnClose.setForeground(Color.white);
		btnClose.setBackground(new Color(128,0,255));	
		btnClose.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				dispose();
			}
		});
//		showAddTable(jcbRoomNumber.getSelectedItem().toString());
		
//		add(jsp);
		
		pTop.add(lblBillNumber);pTop.add(jtfBillNumber);
		pTop.add(lblDate);pTop.add(jtfDate);
		pTop.add(lblRoomNumber);pTop.add(jcbRoomNumber);
		pTop.add(btnShowDetail);pTop.add(lblDummy);
	    
//		add(jsp);add(lblDummy);
		pBottom.add(lblTotalAddCharge);pBottom.add(jtfTotalAddCharge);
		pBottom.add(lblRoomRent);pBottom.add(jtfRoomRent);
		pBottom.add(lblGrossTotal);pBottom.add(jtfGrossTotal);
		pBottom.add(btnSave);pBottom.add(btnClose);
		
		add(pTop,BorderLayout.NORTH);
//		showAddTable(jcbRoomNumber.getSelectedItem().toString());
		jtbAdd=new JTable(showAddTable(jcbRoomNumber.getSelectedItem().toString()),heads);
		jtbAdd.setRowHeight(25);
	    jtbAdd.setRowMargin(5);
	    Dimension d1=new Dimension(5,5);
	    jtbAdd.setIntercellSpacing(d1);
	    jtbAdd.setGridColor(Color.black);
	    jtbAdd.setShowGrid(true);
	    jtbAdd.setForeground(Color.blue);
		jtbAdd.setBackground(new Color(255,255,255));
		jtbAdd.setFont(new Font(Font.SERIF,Font.BOLD+Font.ITALIC,15));
		JTableHeader header=jtbAdd.getTableHeader();
		header.setForeground(Color.white);
		header.setBackground(new Color(64,0,128));
		header.setFont(new Font(Font.SERIF,Font.BOLD,17));
		jtbAdd.setSelectionForeground(Color.black);
		jtbAdd.setSelectionBackground(Color.orange);
		jsp=new JScrollPane(jtbAdd);
		add(jsp);
		revalidate();
		add(pBottom,BorderLayout.SOUTH);
		
		setSize(420,300);
		setLocation(CommonMethods.getCenter(getSize()));
		setVisible(true);
	}
     
	Object[][] showAddTable(String roomno){	
		 try{
				if(jsp!=null)
				remove(jsp);
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select * "
					+ "from addbill where roomnumber='"+roomno+"'");
			if(rst.next()) {
			int id=rst.getInt(1);
			String idnew=id+"";
			String date=rst.getString(2);
			int roomnumber=rst.getInt(3);
			String roomnumbernew=roomnumber+"";
			String particulars=rst.getString(4);
			String description=rst.getString(5);
			int billno=rst.getInt(6);
			String billnonew=billno+"";
			int amount=rst.getInt(7);
			String amountnew=amount+"";
			
//			rst=dc.executeSelectQuery("select * from prepbill "
//					+condition);
//			rst.next();
			Object data[][]=new Object[1][7];
			for(int i=0;i<1;i++){
					data[i][0]=idnew;
					data[i][1]=date;
					data[i][2]=roomnumbernew;
					data[i][3]=particulars;
					data[i][4]=description;
					data[i][5]=billnonew;
					data[i][6]=amountnew;
			}
		}
	}
		 catch(SQLException e){
			 e.printStackTrace();
		  }
		 catch(NullPointerException e){
			 e.printStackTrace();
//			 System.out.println("Invalid");
		  }
		 return data;
    }
}