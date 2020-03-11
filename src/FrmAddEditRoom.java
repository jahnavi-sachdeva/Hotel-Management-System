import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
public class FrmAddEditRoom extends JInternalFrame {
	JLabel lblRoomId,lblRoomNumber,lblRoomDescription,lblCatName,lblRent;
	JTextField jtfRoomId,jtfRoomNumber,jtfRoomDescription,jtfRent;
	JButton btnSave,btnClose;
	JComboBox<String> jcbCatName;
	String btnLabel="Save";
	FrmAddEditRoom(FrmRoom frmRoom,String query){
		super("Add/Edit Room",true,true,true,true);
		setLayout(new GridLayout(6,2));

		lblRoomId=new JLabel("Room Id");
		lblRoomId.setForeground(new Color(10,10,10));
		lblRoomId.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));
		lblRoomNumber=new JLabel("Room Number");
		lblRoomNumber.setForeground(new Color(10,10,10));
		lblRoomNumber.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));
		lblRoomDescription=new JLabel("Room Description");
		lblRoomDescription.setForeground(new Color(10,10,10));
		lblRoomDescription.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));
		lblCatName=new JLabel("Category Name");
		lblCatName.setForeground(new Color(10,10,10));
		lblCatName.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));
		lblRent=new JLabel("Per Day Rent");
		lblRent.setForeground(new Color(10,10,10));
		lblRent.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));
	
		jtfRoomId=new JTextField();
		jtfRoomId.setForeground(new Color(255,0,0));
		jtfRoomId.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));
		jtfRoomNumber=new JTextField();
		jtfRoomNumber.setForeground(new Color(128,0,255));
		jtfRoomNumber.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));
		jtfRoomDescription=new JTextField();
		jtfRoomDescription.setForeground(new Color(128,0,255));
		jtfRoomDescription.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));
		jcbCatName=new JComboBox<>(CommonMethods.getAllRoomCategoryName());
		jcbCatName.setForeground(new Color(10,10,10));
		jcbCatName.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		jtfRent=new JTextField();
		jtfRent.setForeground(new Color(128,0,255));
		jtfRent.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));
		jtfRoomId.setEnabled(false);
		if(query.equals("")) {
			jtfRoomId.setText(maxRoomid()+1+"");
		}
		else {
			try {
				DConnection dc=new DConnection();
				ResultSet rst=dc.executeSelectQuery(query);
				rst.next();
				jtfRoomId.setText(rst.getString(1));
				jtfRoomNumber.setText(rst.getString(2));
				jtfRoomDescription.setText(rst.getString(3));
				jcbCatName.setSelectedItem(CommonMethods.getRoomCategoryName(rst.getInt(4)));
				jtfRent.setText(rst.getString(5));				
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
				String s1=jtfRoomId.getText();
				String s2=jtfRoomNumber.getText();
				String s3=jtfRoomDescription.getText();
				int s4=CommonMethods.getRoomCategoryId((String)jcbCatName.getSelectedItem());
				String s5=jtfRent.getText();
				DConnection dc=new DConnection();
				if(query.equals(""))
					dc.executeOtherQuery("insert into room values("+s1+",'"+s2+"','"+s3+"',"+s4+","+s5+")");
				else
					dc.executeOtherQuery("update room set roomnumber='"+s2+"',roomname='"+s3+"',catid="+s4+", rent="+s5+" where roomid="+s1);
				frmRoom.showTable();
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

		add(lblRoomId);add(jtfRoomId);
		add(lblRoomNumber);add(jtfRoomNumber);
		add(lblRoomDescription);add(jtfRoomDescription);
		add(lblCatName);add(jcbCatName);
		add(lblRent);add(jtfRent);
		add(btnSave);add(btnClose);
		
		setSize(400,300);
		setLocation(CommonMethods.getCenter(getSize()));
		setVisible(true);
	}
	int maxRoomid() {
		int max=0;
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select max(Roomid) from Room");
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
