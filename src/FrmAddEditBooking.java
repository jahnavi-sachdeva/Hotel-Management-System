//copy pasting the date function???
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.text.*;
public class FrmAddEditBooking extends JInternalFrame {
	JLabel lblBid, lblDate,lblMobile, lblRoomNo, lblCin, lblCout,lblDays,lblAdults,lblKids,lblCategory,lblRent,lblGuestId,lblEstimate,lblAdvance;
	JTextField jtfBid, jtfDate, jtfCin, jtfCout, jtfDays,jtfMobile, jtfGuestId,jtfAdults,jtfKids,jtfRent,jtfEstimate,jtfAdvance;
	JButton btnSave, btnClose,btnSearch;
	String btnLabel="Save";
	JComboBox<String> jcbCategory,jcbRoomNo;
	JDesktopPane jdp;
	FrmAddEditBooking(FrmBooking frmBooking,String query,JDesktopPane jdp){
		super("Add/Edit Employee",true,true,true,true);
		this.jdp=jdp;
		setLayout(new GridLayout(15,2,5,5));
		lblBid=new JLabel("Booking id");
		lblDate=new JLabel("Date");
		lblMobile=new JLabel("Mobile");
		lblCin=new JLabel("Check In");
		lblRoomNo=new JLabel("Room No");
		lblDays=new JLabel("Days");
		lblCout=new JLabel("Check Out");
		lblAdults=new JLabel("Adults Count");
		lblKids=new JLabel("Kids Count");
		lblGuestId=new JLabel("GuestId");
		lblRent=new JLabel("Rent");
		lblAdvance=new JLabel("Advance");
		lblEstimate=new JLabel("Estimate");
		lblCategory=new JLabel("Catogory");
		
		jtfAdults=new JTextField();
		jtfMobile=new JTextField();
		jtfKids=new JTextField();
		jcbCategory=new JComboBox<>(CommonMethods.getAllRoomCategoryName());
		jtfRent=new JTextField();
		jtfEstimate=new JTextField();
		jtfAdvance=new JTextField();
		jtfBid=new JTextField();
		jcbRoomNo=new JComboBox<>(CommonMethods.getAllRoomNos(" where catid=1"));
		jtfCin=new JTextField();
		jtfCout=new JTextField();
		jtfCout.setEditable(false);
		jtfDays=new JTextField();
		jtfGuestId=new JTextField();
		jtfGuestId.setEditable(false);
		jtfDate=new JTextField();
		jtfBid.setEnabled(false);
		
		jtfDays.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent fe) {
				if(jtfDays.getText().isEmpty())
					return;
				try {
					int n=Integer.parseInt(jtfDays.getText());
					Calendar c1=Calendar.getInstance();
					String s1=jtfCin.getText();
					SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
					Date d1=sdf.parse(s1);
					c1.setTime(d1);
					c1.add(Calendar.DATE, n);
					jtfCout.setText(sdf.format(c1.getTime()));
					if(!jtfRent.getText().isEmpty())
						jtfEstimate.setText(Integer.toString(Integer.parseInt(jtfRent.getText()) * Integer.parseInt(jtfDays.getText())));
				}
				catch(ParseException e) {
					e.printStackTrace();
				}
				catch(NumberFormatException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Invalid input");
				}
			}
		});
		btnSearch=new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String mobile=jtfMobile.getText();
				int guestId=CommonMethods.getGuestId(mobile);
				if(guestId==-1) {
					jtfGuestId.setText("");
					JOptionPane.showMessageDialog(null,"Guest Not Found Please add the details");
					FrmGuest fg=new FrmGuest(jdp);
					jdp.add(fg);
					jdp.setComponentZOrder(fg, 0);
					jdp.setComponentZOrder(FrmAddEditBooking.this, 1);					
				}
				else {
					JOptionPane.showMessageDialog(null,"Guest Found");
					jtfGuestId.setText(guestId+"");
				}	
			}
		});

		if(query.equals("")){
			jtfBid.setText(maxBookingId()+1+"");
			jtfDate.setText(getCurrentDate());
			jtfCin.setText(getCurrentDate());
			jtfCout.setText(getCurrentDate());
		}
		else{
			try{
				DConnection dc=new DConnection();
				ResultSet rst=dc.executeSelectQuery(query);
				rst.next();
			
				jtfBid.setText(rst.getString(1));
				jtfDate.setText(CommonMethods.convertDateToReadableFormat(rst.getString(2)));
				jtfCin.setText(CommonMethods.convertDateToReadableFormat(rst.getString(3)));
				jtfDays.setText(rst.getString(4));
				jtfCout.setText(CommonMethods.convertDateToReadableFormat(rst.getString(5)));
				jtfAdults.setText(rst.getString(6));
				jtfKids.setText(rst.getString(7));
				jcbCategory.setSelectedItem(CommonMethods.getRoomCategoryName(rst.getInt(8)));
				jcbRoomNo.removeAllItems();
				DConnection dc2=new DConnection();
				ResultSet rst2=dc2.executeSelectQuery("select roomnumber from room where catid="+rst.getInt(8)+"");
				while(rst2.next()) {
					jcbRoomNo.addItem(rst2.getString(1));
				}
				dc2.close();
				jcbRoomNo.setSelectedItem(rst.getString(9));
				jtfRent.setText(rst.getString(10));
				jtfGuestId.setText(rst.getString(11));
				jtfEstimate.setText(rst.getString(12));
				jtfAdvance.setText(rst.getString(13));
				dc.close();
				btnLabel="Update";
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
		jcbCategory.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				String s1=(String)jcbCategory.getSelectedItem();
				int id=CommonMethods.getRoomCategoryId(s1);
				String roomNo[]=CommonMethods.getAllRoomNos(" where catid="+id);
				jcbRoomNo.removeAllItems();
				for(int i=0;i<roomNo.length;i++)
					jcbRoomNo.addItem(roomNo[i]);
			}
		});
		
		jcbRoomNo.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				String s1=(String)jcbRoomNo.getSelectedItem();
				if(s1==null || s1.isEmpty())
					return;
				try {
					DConnection d=new DConnection();
					ResultSet rst=d.executeSelectQuery("select rent from room where roomnumber="+s1+"");
					if(rst.next()) {
						jtfRent.setText(rst.getString(1));
						jtfEstimate.setText(Integer.toString(Integer.parseInt(jtfRent.getText()) * Integer.parseInt(jtfDays.getText())));
					}
				}
				catch(SQLException e1){
					e1.printStackTrace();
				}
			}
		});
		btnSave=new JButton(btnLabel);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				String s1=jtfBid.getText();
				String s2=CommonMethods.convertDateToDatabaseFormat(jtfDate.getText());
				String s3=CommonMethods.convertDateToDatabaseFormat(jtfCin.getText());
				String s4=jtfDays.getText();
				String s5=CommonMethods.convertDateToDatabaseFormat(jtfCout.getText());
				String s6=jtfAdults.getText();
				String s7=jtfKids.getText();
				String s8=(String)jcbCategory.getSelectedItem();
				int catid=CommonMethods.getRoomCategoryId(s8);
				String s9=(String)jcbRoomNo.getSelectedItem();
				String s10=jtfRent.getText();
				String s11=jtfGuestId.getText();
				String s12=	jtfEstimate.getText();
				String s13=jtfAdvance.getText();
				DConnection dc=new DConnection();
				if(query.equals(""))
					dc.executeOtherQuery("insert into bookings values("+s1+",'"+s2+"','"+s3+"',"+s4+",'"+s5+"',"+s6+","+s7+","+catid+","+s9+","+s10+","+s11+","+s12+","+s13+",'no',0"+")");
				else
					dc.executeOtherQuery("update bookings set checkindate='"+s3+"',days="+s4+",checkoutdate='"+s5+"',adults="+s6+",kids="+s7+",catid="+catid+",roomnumber="+s9+",rent="+s10+",guestid="+s11+",estimate="+s12+",advance="+s13+", staffid=0 where bookingid="+s1);
				frmBooking.showTable();
				dispose();
			}
		});

		btnClose=new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				dispose();
			}
		});

		add(lblBid);add(jtfBid);
		add(lblDate);add(jtfDate);
		JPanel p1=new JPanel();
		p1.setLayout(new GridLayout(1,2));
		p1.add(jtfMobile);p1.add(btnSearch);
		add(lblMobile);add(p1);
		add(lblGuestId);add(jtfGuestId);
		add(lblCin);add(jtfCin);
		add(lblDays);add(jtfDays);
		add(lblCout);add(jtfCout);
		add(lblAdults);add(jtfAdults);
		add(lblKids);add(jtfKids);
		add(lblCategory);add(jcbCategory);
		add(lblRoomNo);add(jcbRoomNo);
		add(lblRoomNo);add(jcbRoomNo);
		add(lblRent);add(jtfRent);
		add(lblAdvance);add(jtfAdvance);
		add(lblEstimate);add(jtfEstimate);
		add(btnSave);add(btnClose);
		setSize(500,450);
		setLocation(CommonMethods.getCenter(getSize()));		
		setVisible(true);
	}
	int maxBookingId() {
		int max=0;
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select max(bookingid) from bookings");
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
}

