import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.table.*;
class FrmAddEditCheckOut extends JInternalFrame{
	JPanel p1,p2,p21,p3,p4,p5,p6,p7,p8,p9,p10;
	JComboBox RoomNo;
	JLabel lblRoomNo,lblBillNo,lblDate,lblName,lblAddress,lblContactNo,lblNoOfDays,lblAdvance;
	JLabel lblRent,lblAdditionalCharge,lblGstOnRent,lblGstOnAdditionalCharge;
	JLabel lblTotal,lblTotalAdditional,lblTotalBill,lblNetBill;
	JTextField jtfBillNo,jtfDate,jtfName,jtfAddress,jtfContactNo,jtfNoOfDays;
	JTextField jtfRent,jtfAdditionalCharge,jtfGstOnRent,jtfGstOnAdditionalCharge,jtfGstOnPerRent,jtfGstOnPerAddtionalCharge;
	JTextField jtfTotal,jtfTotalAdditional,jtfTotalBill,jtfNetBill,jtfAdvance;
	JTable jtb;
	JButton btnCalculate;
	JScrollPane jsp;
	JButton btnSave,btnClose;
	String s1[]={"BillNo","Description","Amount"};
	String data[][];
	DefaultTableModel model;
	FrmAddEditCheckOut(FrmCheckOut Frmcheckout,String query,JDesktopPane jdp)
	{
		super("Add/Edit CheckOut",true,true,true,true);
		p1=new JPanel();
		p2=new JPanel();
		p3=new JPanel();
     	p4=new JPanel();
     	p5=new JPanel();
        p6=new JPanel();
        p7=new JPanel();
        p8=new JPanel();
        p9=new JPanel();
        p10=new JPanel();
        
        Font f1=new Font(Font.SERIF,Font.BOLD+Font.ITALIC,13);
     	
     	model=new DefaultTableModel(data,s1);
		jtb=new JTable();
		jtb.setModel(model);
		jsp=new JScrollPane(jtb);
		
		RoomNo=new JComboBox(CommonMethods.getAllcheckinroomnumber());
		
		lblBillNo=new JLabel("Bill No");
     	lblDate=new JLabel("Date");	    
     	lblRoomNo=new JLabel("Room No");
	    lblName=new JLabel("Name");
	    lblAddress=new JLabel("Address");
	    lblContactNo=new JLabel("ContactNo");
	    lblNoOfDays=new JLabel("No Of Days");
	    lblRent=new JLabel("Rent");
	    lblAdditionalCharge=new JLabel("Additional Charges");
	    lblGstOnRent=new JLabel("Gst On Rent");
	    lblGstOnAdditionalCharge=new JLabel("Gst On Additional Charge");
	    lblTotal=new JLabel("Total Rent");
	    lblTotalAdditional=new JLabel("Total Additional Charge");
	    lblTotalBill=new JLabel("Total Bill");
	    lblNetBill=new JLabel("Net Bill");
	    lblAdvance=new JLabel("Advance Pay");
	    btnCalculate=new   JButton("Calculate");

	    jtfBillNo=new JTextField(10);
	    jtfBillNo.setEditable(false);
	    jtfDate=new JTextField(10);
	    jtfDate.setEditable(false);
	    jtfName=new JTextField();
	    jtfAddress=new JTextField();
	    jtfContactNo=new JTextField();
	    jtfNoOfDays=new JTextField();
	    jtfRent=new JTextField();
	    jtfAdditionalCharge=new JTextField();
	    jtfGstOnRent=new JTextField();
	    jtfGstOnAdditionalCharge=new JTextField();
	    jtfTotal=new JTextField();
	    jtfTotalAdditional=new JTextField();
	    jtfTotalBill=new JTextField();
	    jtfNetBill=new JTextField();
	    jtfGstOnPerRent=new JTextField();
	    jtfAdvance=new JTextField();
	    jtfGstOnPerAddtionalCharge=new JTextField();
	    
	    

	     
	   lblBillNo.setFont(f1);
     	lblDate.setFont(f1);	    
     	lblRoomNo.setFont(f1);
	    lblName.setFont(f1);
	    lblAddress.setFont(f1);
	    lblContactNo.setFont(f1);
	    lblNoOfDays.setFont(f1);
	    lblRent.setFont(f1);
	    lblAdditionalCharge.setFont(f1);
	    lblGstOnRent.setFont(f1);
	    lblGstOnAdditionalCharge.setFont(f1);
	    lblTotal.setFont(f1);
	    lblTotalAdditional.setFont(f1);
	    lblTotalBill.setFont(f1);
	    lblNetBill.setFont(f1);
	    btnCalculate.setFont(f1);
	    lblAdvance.setFont(f1);
	    
	    jtfBillNo.setFont(f1);
     	jtfDate.setFont(f1);	    
     	jtfName.setFont(f1);
	    jtfAddress.setFont(f1);
	    jtfContactNo.setFont(f1);
	    jtfNoOfDays.setFont(f1);
	    jtfRent.setFont(f1);
	    jtfAdditionalCharge.setFont(f1);
	    jtfGstOnRent.setFont(f1);
	    jtfGstOnAdditionalCharge.setFont(f1);
	    jtfTotal.setFont(f1);
	    jtfTotalAdditional.setFont(f1);
	    jtfTotalBill.setFont(f1);
	    jtfNetBill.setFont(f1);
	    jtfAdvance.setFont(f1);
		if(query.equals("")) {
			jtfBillNo.setText(maxID()+1+"");
			jtfDate.setText(getCurrentDate());			
		}		
		btnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String roomno=(String)RoomNo.getSelectedItem();
				String data2[]=CommonMethods.getCheckIn(roomno);
				int gst1=CommonMethods.getGstOnRent(roomno);
				int gst2=CommonMethods.getGstOnaddtionalCharge(roomno);
				jtfRent.setText(data2[10]);
				jtfAdvance.setText(data2[13]);
				jtfGstOnRent.setText(gst1+"");
				jtfGstOnAdditionalCharge.setText(gst2+"");
				jtfNoOfDays.setText(data2[4]);
				String data3[]=CommonMethods.getGuest(data2[11]);
				jtfName.setText(data3[1]);
				jtfAddress.setText(data3[2]);
				jtfContactNo.setText(data3[6]);
				String add[][]=CommonMethods.getAllCharges(Integer.parseInt(roomno));
				int sum=0;
				for(int i=0;i<add.length;i++) {
					model.addRow(new Object[] {add[i][0],add[i][1],add[i][2]});
					sum+=Integer.parseInt(add[i][2]);
				}
				jtfAdditionalCharge.setText(sum+"");
				int rent=Integer.parseInt(jtfRent.getText());
				int days=Integer.parseInt(jtfNoOfDays.getText());
				int gst=Integer.parseInt(jtfGstOnRent.getText());
				int Additional=Integer.parseInt(jtfAdditionalCharge.getText());
				int gstAdditional=Integer.parseInt(jtfGstOnAdditionalCharge.getText());
				jtfGstOnPerRent.setText(calculateGstRent(rent, gst, days));
				jtfGstOnPerAddtionalCharge.setText(calculateGstAddtionalCharge(Additional, gstAdditional));
				int gstrent=Integer.parseInt(jtfGstOnPerRent.getText());
				int gstAdditionalCharge=Integer.parseInt(jtfGstOnPerAddtionalCharge.getText());
				jtfTotal.setText(calculateTotalRent(rent, gstrent, days));
				jtfTotalAdditional.setText(calculateTotalAddtionalChargeRent(Additional, gstAdditionalCharge));
				int total=Integer.parseInt(jtfTotal.getText());
				int totalAdditional=Integer.parseInt(jtfTotalAdditional.getText());
				jtfNetBill.setText(calculateNetBill(total, totalAdditional));
	        	int advance=Integer.parseInt(jtfAdvance.getText());
		 		int net=Integer.parseInt(jtfNetBill.getText());
		 		int finalPay=net-advance;
		 		jtfTotalBill.setText(finalPay+"");
			}
		});
		btnSave=new JButton("Save"); 
		btnSave.setForeground(Color.white);
		btnSave.setBackground(Color.gray);	
		btnSave.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String s1=jtfBillNo.getText();
				String s2=CommonMethods.convertDateToDatabaseFormat(jtfDate.getText());
				String s3=(String)RoomNo.getSelectedItem();
				String s4=jtfTotal.getText();
				String s5=jtfTotalAdditional.getText();
				String s6=jtfTotalBill.getText();
			    DConnection dc=new DConnection();
				if(query.equals("")) {
					dc.executeOtherQuery("insert into bill values("+s1+",'"+s2+"',"+s3+","+s4+","+s5+","+s6+")");
					try {
						ResultSet rst=dc.executeSelectQuery("select bookingid from checkin where roomnumber="+s3);
						rst.next();
						int bid=rst.getInt(1);
						dc.executeOtherQuery("delete from checkin where roomnumber="+s3);
						dc.executeOtherQuery("delete from bookings where bookingid="+bid);
						dc.executeOtherQuery("delete from addbill where roomnumber="+s3);
					}
					catch(SQLException e) {
						e.printStackTrace();
					}
				}
				Frmcheckout.showTable();
				dispose();				
			}
		});
	    btnClose=new JButton("Close");
		btnClose.setForeground(Color.white);
		btnClose.setBackground(Color.gray);	
		btnClose.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				dispose();
			}
		});

		p2.setLayout(new GridLayout(1,2));
	    p2.add(RoomNo);
	    p2.add(btnCalculate); 
	   
	    p1.setLayout(new GridLayout(7,2));
	    p1.add(lblBillNo);
	    p1.add(jtfBillNo);
	    p1.add(lblDate);
	    p1.add(jtfDate);
	   
	    p1.add(lblRoomNo);
	    p1.add(p2);
	    p1.add(lblName);
	    p1.add(jtfName);
	    p1.add(lblAddress);
	    p1.add(jtfAddress);
	    p1.add(lblContactNo);
	    p1.add(jtfContactNo);
	    p1.add(lblNoOfDays);
	    p1.add(jtfNoOfDays);
	    	    
	   
	    p3.add(jsp);
	    p8.setLayout(new GridLayout(1,2));
	    p8.add(jtfGstOnRent);p8.add(jtfGstOnPerRent);
	    p9.setLayout(new GridLayout(1,2));
	    p9.add(jtfGstOnAdditionalCharge);p9.add(jtfGstOnPerAddtionalCharge);

	    p4.setLayout(new GridLayout(10,18));
	    p4.add(lblRent);  p4.add(jtfRent);
	    p4.add(lblGstOnRent);  p4.add(p8);
	    p4.add(lblTotal);  p4.add(jtfTotal);
	    p4.add(lblAdditionalCharge); p4.add(jtfAdditionalCharge);
	    p4.add(lblGstOnAdditionalCharge);  p4.add(p9);
	    p4.add(lblTotalAdditional);  p4.add(jtfTotalAdditional);
	    p4.add(lblNetBill); p4.add(jtfNetBill);
	    p4.add(lblAdvance); p4.add(jtfAdvance);
	    p4.add(lblTotalBill); p4.add(jtfTotalBill);
	    //p5.setLayout(new GridLayout(1,2));
	    p4.add(btnSave);
	    p4.add(btnClose);
	    p6.setLayout(new GridLayout(1,1));
	    p6.add(p4);
	    //p6.add(p5);
	    
	   
	  p10.setLayout(new GridLayout(3,1));  
	  p10.add(p1);
	  p10.add(p3);
	  p10.add(p6);
	    setResizable(false);
	    add(p10);
	    setBounds(100,100,500,560);
	    setVisible(true);
	    
	}
	int maxID() {
		int max=0;
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select max(billnumber) from bill");
			rst.next();
			max=rst.getInt(1);
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return max;
	}
	 String getCurrentDate(){
		Date d=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(d);
	}	
   String calculateGstRent(int rent,int gstRent,int days) {
	   int totaldayRent,totalrent;
	   totaldayRent=days*rent;
	   totalrent=totaldayRent*gstRent/100;
	   return totalrent+"";
   }
   String calculateGstAddtionalCharge(int rent,int gst) {
	   int totaldayRent,totalrent;
	   totalrent=rent*gst/100;
	   return totalrent+"";
   }
   String calculateTotalRent(int rent,int gstRent,int days) {
	   int totaldayRent,totalrent;
	   totaldayRent=days*rent;
	   totalrent=totaldayRent+gstRent;
	   return totalrent+"";
   }
   String calculateTotalAddtionalChargeRent(int rent,int gstAdditional) {
	   int totalrent;
	   totalrent=rent+gstAdditional;
	   return totalrent+"";
   }
   String calculateNetBill(int rent,int Additional) {
	   int netbill;
	   netbill=rent+Additional;
	   return netbill+"";
   }
}