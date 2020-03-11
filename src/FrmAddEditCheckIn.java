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
public class FrmAddEditCheckIn extends JInternalFrame {
	JLabel lblCid,lblBid, lblDate,lblMobile, lblRoomNo, lblCin, lblCout,lblDays,lblAdults,lblKids,lblCategory,lblRent,lblGuestId,lblEstimate,lblAdvance;
	JTextField jtfCid,jtfBid, jtfDate, jtfCin, jtfCout, jtfDays,jtfMobile, jtfGuestId,jtfAdults,jtfKids,jtfRent,jtfEstimate,jtfAdvance;
	JButton btnSave, btnClose,btnSearch;
	String btnLabel="Save";
	JComboBox<String> jcbCategory,jcbRoomNo;
	JDesktopPane jdp;
	FrmAddEditCheckIn(FrmCheckIn frmCheckIn,String query,JDesktopPane jdp){
		super("Add/Edit Employee",true,true,true,true);
		this.jdp=jdp;
		setLayout(new GridLayout(16,2,5,5));
		lblCid=new JLabel("CheckIn id");
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
		
		jtfCid=new JTextField();
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
		jtfCid.setEnabled(false);
		
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
				int bid=Integer.parseInt(jtfBid.getText());
				boolean b=CommonMethods.checkBid(bid);
				if(b==false) {
					jtfGuestId.setText("");
					JOptionPane.showMessageDialog(null,"Invalid Bookin ID");
				}
				else {
					JOptionPane.showMessageDialog(null,"Booking Id Found");
					String data[]=CommonMethods.getBooking(bid);
					jtfGuestId.setText(data[10]);
					jtfMobile.setText(CommonMethods.getGuestMobile(data[10]));
					jtfDays.setText(data[3]);
					jtfAdults.setText(data[5]);
					jtfKids.setText(data[6]);
					jtfRent.setText(data[9]);
					jtfAdvance.setText(data[12]);
					jtfEstimate.setText(data[11]);
					jcbCategory.setSelectedItem(CommonMethods.getRoomCategory(Integer.parseInt(data[8])));
					jcbRoomNo.setSelectedItem(data[8]);
				}	
			}
		});

		if(query.equals("")){
			jtfCid.setText(maxCheckInId()+1+"");
			jtfDate.setText(getCurrentDate());
			jtfCin.setText(getCurrentDate());
			jtfCout.setText(getCurrentDate());
		}
		else{
			try{
				DConnection dc=new DConnection();
				ResultSet rst=dc.executeSelectQuery(query);
				rst.next();
				jtfCid.setText(rst.getString(1));
				jtfBid.setText(rst.getString(2));
				jtfDate.setText(CommonMethods.convertDateToReadableFormat(rst.getString(3)));
				jtfCin.setText(CommonMethods.convertDateToReadableFormat(rst.getString(4)));
				jtfDays.setText(rst.getString(5));
				jtfCout.setText(CommonMethods.convertDateToReadableFormat(rst.getString(6)));
				jtfAdults.setText(rst.getString(7));
				jtfKids.setText(rst.getString(8));
				jcbCategory.setSelectedItem(rst.getString(9));
				jcbRoomNo.setSelectedItem(rst.getString(10));
				jtfRent.setText(rst.getString(11));
				jtfGuestId.setText(rst.getString(12));
				jtfEstimate.setText(rst.getString(13));
				jtfAdvance.setText(rst.getString(14));
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
				String s0=jtfCid.getText();
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
					dc.executeOtherQuery("insert into checkin values("+s0+","+s1+",'"+s2+"','"+s3+"',"+s4+",'"+s5+"',"+s6+","+s7+","+catid+","+s9+","+s10+","+s11+","+s12+","+s13+")");
				else
					dc.executeOtherQuery("update checkin set bookingid="+s1+"checkindate='"+s3+"',days="+s4+",checkoutdate='"+s5+"',adults="+s6+",kids="+s7+",catid="+catid+",roomnumber="+s9+",rent="+s10+",guestid="+s11+",estimate="+s12+",advance="+s13+" where checkinid="+s0);
				frmCheckIn.showTable();
				dispose();
			}
		});

		btnClose=new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				dispose();
			}
		});

		add(lblCid);add(jtfCid);
		add(lblBid);
		JPanel p1=new JPanel();
		p1.setLayout(new GridLayout(1,2));
		p1.add(jtfBid);p1.add(btnSearch);
		add(p1);
		add(lblDate);add(jtfDate);
		add(lblMobile);add(jtfMobile);
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
	int maxCheckInId() {
		int max=0;
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select max(checkinid) from checkin");
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

