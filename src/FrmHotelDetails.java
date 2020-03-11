import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.*;
public class FrmHotelDetails extends JInternalFrame{
	JButton btnUpdate,btnClose;
	JLabel lblName,lblAddress,lblEmail,lblContact,lblRooms,lblFloors,lblGuestRating,lblAmenities,lblStar,lblGstOnRent,lblGstOnAdditionalCharge;
	JTextField jtfName,jtfAddress,jtfEmail,jtfContact,jtfRooms,jtfFloors,jtfGuestRating,jtfAmenities,jtfStar,jtfGstOnRent,jtfGstOnAdditionalCharge;
	FrmHotelDetails(JDesktopPane jdp){
		super("Hotel Details",true,true,true,true);
		lblName=new JLabel("Hotel Name");
		lblAddress=new JLabel("Address");
		lblEmail=new JLabel("E-Mail");
		lblContact=new JLabel("Contact");
		lblRooms=new JLabel("No. of Rooms");
		lblFloors=new JLabel("No. of Floors");
		lblGuestRating=new JLabel("GuestRating");
		lblAmenities=new JLabel("Amenities");
		lblStar=new JLabel("Star");
		lblGstOnRent=new JLabel("Gst On Rent(%)");
		lblGstOnAdditionalCharge=new JLabel("Gst On Additional Charges(%)");
		
		jtfName=new JTextField();
		jtfAddress=new JTextField();
		jtfEmail=new JTextField();
		jtfContact=new JTextField();
		jtfRooms=new JTextField();
		jtfFloors=new JTextField();
		jtfGuestRating=new JTextField();
		jtfAmenities=new JTextField();
		jtfStar=new JTextField();
		btnUpdate=new JButton("update");
		jtfGstOnRent=new JTextField();
		jtfGstOnAdditionalCharge=new JTextField();
		
		
		try{
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select * from hoteldetails");
			if(rst.next()){
				jtfName.setText(rst.getString(1));
				jtfAddress.setText(rst.getString(2));
				jtfEmail.setText(rst.getString(3));
				jtfContact.setText(rst.getString(4));
				jtfRooms.setText(rst.getString(5));
				jtfFloors.setText(rst.getString(6));
				jtfAmenities.setText(rst.getString(7));
				jtfGuestRating.setText(rst.getString(8));
				jtfStar.setText(rst.getString(9));
				jtfGstOnRent.setText(rst.getString(10));
				jtfGstOnAdditionalCharge.setText(rst.getString(11));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				DConnection dc=new DConnection();
				String s1=jtfName.getText();
				String s2=jtfAddress.getText();
				String s3=jtfEmail.getText();
				String s4=jtfContact.getText();
				String s5=jtfRooms.getText();
				String s6=jtfFloors.getText();
				String s7=jtfAmenities.getText();
				String s8=jtfGuestRating.getText();
				String s9=jtfStar.getText();
				String s10=jtfGstOnRent.getText();
				String s11=jtfGstOnAdditionalCharge.getText();
				dc.executeOtherQuery("update hoteldetails set name='"+s1+"',address='"+s2+"',email='"+s3+"',contact='"+s4+"',rooms="+s5+",floors="+s6+",amenities='"+s7+"',guestrating="+s8+",star='"+s9+"',gstonrent="+s10+",gstonadditionalCharge="+s11+"");
				dispose();
			}				
		});
		btnClose=new JButton("Close");
		btnClose.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		});
		
		setLayout(new GridLayout(12,2));
		add(lblName);add(jtfName);
		add(lblAddress);add(jtfAddress);
		add(lblEmail);add(jtfEmail);
		add(lblContact);add(jtfContact);
		add(lblRooms);add(jtfRooms);
		add(lblFloors);add(jtfFloors);
		add(lblStar);add(jtfStar);
		add(lblAmenities);add(jtfAmenities);
		add(lblGuestRating);add(jtfGuestRating);
		add(lblGstOnRent);add(jtfGstOnRent);
		add(lblGstOnAdditionalCharge);add(jtfGstOnAdditionalCharge);  
		add(btnUpdate);add(btnClose);
		setSize(600,400);
		setLocation(300,100);
		setVisible(true);
	}
}

