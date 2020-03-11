import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;
public class FrmAddEditAdditional extends JInternalFrame {
	JLabel lblID,lbldate,lblRoomnumber,lblParticulars,lblDescription,lblBillno,lblAmount;
	JTextField jtfID,jtfdate,jtfRoomnumber,jtfParticulars,jtfDescription,jtfBillno,jtfAmount;
	JButton btnSave,btnClose;
		JComboBox<String> jcbRoomnumber,jcbParticulars;
	String btnLabel="Save";
	FrmAddEditAdditional(FrmAdditional frmAdditional,String query){
		super("Add/Edit Additional Charges",true,true,true,true);
		setLayout(new GridLayout(8,2));

		lblID=new JLabel("ID");
		lblID.setForeground(new Color(10,10,10));
		lblID.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		lbldate=new JLabel("Date");
		lbldate.setForeground(new Color(10,10,10));
		lbldate.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		lblRoomnumber=new JLabel("Room Number");
		lblRoomnumber.setForeground(new Color(10,10,10));
		lblRoomnumber.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		lblParticulars=new JLabel("Particulars");
		lblParticulars.setForeground(new Color(10,10,10));
		lblParticulars.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		lblDescription=new JLabel("Description");
		lblDescription.setForeground(new Color(10,10,10));
		lblDescription.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		lblBillno=new JLabel("Bill No");
		lblBillno.setForeground(new Color(10,10,10));
		lblBillno.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		lblAmount=new JLabel("Total Amount");
		lblAmount.setForeground(new Color(10,10,10));
		lblAmount.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		jtfID=new JTextField();
		jtfID.setForeground(new Color(10,10,10));
		jtfID.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		jtfdate=new JTextField();
		jtfdate.setForeground(new Color(10,10,10));
		jtfdate.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		jtfRoomnumber=new JTextField();
		jtfRoomnumber.setForeground(new Color(10,10,10));
		jtfRoomnumber.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		jtfParticulars=new JTextField();
		jtfParticulars.setForeground(new Color(10,10,10));
		jtfParticulars.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		jtfDescription=new JTextField();
		jtfDescription.setForeground(new Color(10,10,10));
		jtfDescription.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		jtfBillno=new JTextField();
		jtfBillno.setForeground(new Color(10,10,10));
		jtfBillno.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		jtfAmount=new JTextField();
		jtfAmount.setForeground(new Color(10,10,10));
		jtfAmount.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		jcbRoomnumber=new JComboBox<>(CommonMethods.getAllcheckinroomnumber());
		jcbRoomnumber.setForeground(new Color(10,10,10));
		jcbRoomnumber.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		//jcbRoomnumber.addItem(
		jcbParticulars=new JComboBox();
		jcbParticulars.setForeground(new Color(10,10,10));
		jcbParticulars.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		jcbParticulars.addItem("Breakfast");
		jcbParticulars.addItem("Lunch");
		jcbParticulars.addItem("Dinner");
		jcbParticulars.addItem("Laundry");
		jcbParticulars.addItem("Doctor");
		jcbParticulars.addItem("Medicines");
		jcbParticulars.addItem("Water");
		jcbParticulars.addItem("Others");
		jtfID.setEnabled(false);
		if(query.equals("")) {
			jtfID.setText(maxID()+1+"");
			jtfdate.setText(getCurrentDate());
		}
		else{
			try {
				DConnection dc=new DConnection();
				ResultSet rst=dc.executeSelectQuery(query);
				rst.next();
				jtfID.setText(rst.getString(1));
				jtfdate.setText(CommonMethods.convertDateToReadableFormat(rst.getString(2)));
				jcbRoomnumber.setSelectedItem(rst.getString(3));
				jcbParticulars.setSelectedItem(rst.getString(4));
				jtfDescription.setText(rst.getString(5));
				jtfBillno.setText(rst.getString(6));
				jtfAmount.setText(rst.getString(7));
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
				String s1=jtfID.getText();
				String s2=CommonMethods.convertDateToDatabaseFormat(jtfdate.getText());
				String s3=(String)jcbRoomnumber.getSelectedItem();
				String s4=(String)jcbParticulars.getSelectedItem();
				String s5=jtfDescription.getText();
				String s6=jtfBillno.getText();
				String s7=jtfAmount.getText();
				DConnection dc=new DConnection();
				if(query.equals(""))
					dc.executeOtherQuery("insert into addbill values('"+s1+"','"+s2+"','"+s3+"','"+s4+"','"+s5+"','"+s6+"','"+s7+"')");
				else
					dc.executeOtherQuery("update addbill set date='"+s2+"',Roomnumber='"+s3+"',Particulars='"+s4+"',Description='"+s5+"',Billno='"+s6+"',Amount='"+s7+"'where ID="+s1);
				frmAdditional.showTable();
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
	
		add(lblID);add(jtfID);
		add(lbldate);add(jtfdate);
		add(lblRoomnumber);add(jcbRoomnumber);
		add(lblParticulars);add(jcbParticulars);
		add(lblDescription);add(jtfDescription);
		add(lblBillno);add(jtfBillno);
		add(lblAmount);add(jtfAmount);
		add(btnSave);add(btnClose);
		setSize(400,300);
		setLocation(CommonMethods.getCenter(getSize()));
		setVisible(true);
	}
	int maxID() {
		int max=0;
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select max(ID) from addbill");
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
