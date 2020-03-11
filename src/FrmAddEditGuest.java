import javax.crypto.AEADBadTagException;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
public class FrmAddEditGuest extends JInternalFrame {
	JLabel lblGuestId,lblGuestName,lblGuestAddress,lblGuestContact,lblGuestDob,lblstate,lbldistrict;
	JTextField jtfGuestId,jtfGuestName,jtfGuestAddress,jtfGuestContact,jtfGuestDob;
	 JComboBox<String> jcbstate,jcbdistrict;
	JButton btnSave,btnClose;
	String btnLabel="Save";
	FrmAddEditGuest(FrmGuest frmGuest,String query){
		super("Add/Edit Guest",true,true,true,true);
		setLayout(new GridLayout(8,2));

		lblGuestId=new JLabel("Guest Id");
		lblGuestId.setForeground(new Color(10,10,10));
		lblGuestId.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		lblGuestName=new JLabel("Guest Name");
		lblGuestName.setForeground(new Color(10,10,10));
		lblGuestName.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		lblGuestAddress=new JLabel("Guest Address");
		lblGuestAddress.setForeground(new Color(10,10,10));
		lblGuestAddress.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		lblGuestContact=new JLabel("Guest Contact");
		lblGuestContact.setForeground(new Color(10,10,10));
		lblGuestContact.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		lblGuestDob=new JLabel("Date of birth(dd-mm-yyyy)");
		lblGuestDob.setForeground(new Color(10,10,10));
		lblGuestDob.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		lblstate=new JLabel("State");
		lblstate.setForeground(new Color(10,10,10));
		lblstate.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		lbldistrict=new JLabel("City");
		lbldistrict.setForeground(new Color(10,10,10));
		lbldistrict.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		
		jtfGuestId=new JTextField();
		jtfGuestId.setForeground(new Color(255,0,0));
		jtfGuestId.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		jtfGuestName=new JTextField();
		jtfGuestName.setForeground(new Color(255,0,0));
		jtfGuestName.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		jtfGuestAddress=new JTextField();
		jtfGuestAddress.setForeground(new Color(255,0,0));
		jtfGuestAddress.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		jtfGuestContact=new JTextField();
		jtfGuestContact.setForeground(new Color(255,0,0));
		jtfGuestContact.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		jtfGuestDob=new JTextField();
		jtfGuestDob.setForeground(new Color(255,0,0));
		jtfGuestDob.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		jcbstate=new JComboBox<>(CommonMethods.getAllstateName());//change here
		jcbstate.setForeground(new Color(255,0,0));
		jcbstate.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		jcbdistrict=new JComboBox<>(CommonMethods.getAllCityName(""));
		jcbdistrict.setForeground(new Color(255,0,0));
		jcbdistrict.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		
		jcbstate.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				String s1=(String)jcbstate.getSelectedItem();
				jcbdistrict.removeAllItems();
				String s2[]=CommonMethods.getAllCityName(s1);
				for(int i=0;i<s2.length;i++)
					jcbdistrict.addItem(s2[i]);
			}
			
		});
		
		jtfGuestId.setEnabled(false);
		if(query.equals("")) {
			jtfGuestId.setText(maxGuestId()+1+"");
		}
		else {
			try {
				DConnection dc=new DConnection();
				ResultSet rst=dc.executeSelectQuery(query);
				rst.next();
				jtfGuestId.setText(rst.getString(1));
				jtfGuestName.setText(rst.getString(2));
				jtfGuestAddress.setText(rst.getString(3));
				jtfGuestDob.setText(CommonMethods.convertDateToReadableFormat(rst.getString(4)));
				jcbstate.setSelectedItem(CommonMethods.getStateName(rst.getInt(6)));
				jcbdistrict.setSelectedItem(CommonMethods.getCityName(rst.getInt(5))); 
				jtfGuestContact.setText(rst.getString(7));
				dc.close();
				btnLabel="Update";
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		btnSave=new JButton(btnLabel);
		btnSave.setForeground(Color.white);
		btnSave.setBackground(Color.gray);	
		btnSave.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String s1=jtfGuestId.getText();
				String s2=jtfGuestName.getText();
				String s3=jtfGuestAddress.getText();
				String s4=CommonMethods.convertDateToDatabaseFormat(jtfGuestDob.getText());
				int s5=CommonMethods.getStateId((String)jcbstate.getSelectedItem());
				int s6=CommonMethods.getCityId((String)jcbdistrict.getSelectedItem());
				String s7=jtfGuestContact.getText();
				DConnection dc=new DConnection();
				if(query.equals(""))
					dc.executeOtherQuery("insert into Guest values("+s1+",'"+s2+"','"+s3+"','"+s4+"',"+s6+","+s5+",'"+s7+"')");
				else
					dc.executeOtherQuery("update Guest set Name='"+s2+"', guestaddress='"+s3+"', dob='"+s4+"', state="+s5+",city="+s6+",guestcontact='"+s7+"' where GuestId="+s1);
				frmGuest.showTable();
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

		add(lblGuestId);add(jtfGuestId);
		add(lblGuestName);add(jtfGuestName);
		add(lblGuestAddress);add(jtfGuestAddress);
		add(lblGuestDob); add(jtfGuestDob);
		add(lblstate);add(jcbstate);
		add(lbldistrict);add(jcbdistrict);
		add(lblGuestContact);add(jtfGuestContact);
		add(btnSave);add(btnClose);
		
		setSize(500,300);
		setLocation(CommonMethods.getCenter(getSize()));
		setVisible(true);
	}
	int maxGuestId() {
		int max=0;
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select max(GuestId) from Guest");
			rst.next();
			max=rst.getInt(1);
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return max;
	}

}
