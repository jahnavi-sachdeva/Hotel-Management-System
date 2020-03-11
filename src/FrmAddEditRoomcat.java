import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
public class FrmAddEditRoomcat extends JInternalFrame {
	JLabel lblcatid,lblcatname,lbltv,lblfridge,lblgyser,lblac;
	JTextField jtfcatid,jtfcatname;
	JButton btnSave,btnClose;
		JComboBox<String> jcbtv,jcbfridge,jcbgyser,jcbac;
	String btnLabel="Save";
	FrmAddEditRoomcat(FrmRoomcat frmRoomcat,String query){
		super("Add/Edit Room Category",true,true,true,true);
		setLayout(new GridLayout(8,2));

		lblcatid=new JLabel("Category Id");
		lblcatid.setForeground(new Color(10,10,10));
		lblcatid.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		lblcatname=new JLabel("Category Name");
		lblcatname.setForeground(new Color(10,10,10));
		lblcatname.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		jtfcatid=new JTextField();
		jtfcatid.setForeground(new Color(10,10,10));
		jtfcatid.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		jtfcatname=new JTextField();
		jtfcatname.setForeground(new Color(10,10,10));
		jtfcatname.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		lbltv=new JLabel("Tv");
		lbltv.setForeground(new Color(10,10,10));
		lbltv.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		lblfridge=new JLabel("Fridge");
		lblfridge.setForeground(new Color(10,10,10));
		lblfridge.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		lblgyser=new JLabel("gyser");
		lblgyser.setForeground(new Color(10,10,10));
		lblgyser.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		lblac=new JLabel("ac");
		lblac.setForeground(new Color(10,10,10));
		lblac.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));

		jcbtv=new JComboBox<String>();
		jcbtv.addItem("yes");
		jcbtv.addItem("no");
		jcbfridge=new JComboBox<String>();
		jcbfridge.addItem("yes");
		jcbfridge.addItem("no");
		jcbgyser=new JComboBox<String>();
		jcbgyser.addItem("yes");
		jcbgyser.addItem("no");
		jcbac=new JComboBox<String>();
		jcbac.addItem("yes");
		jcbac.addItem("no");
		jtfcatid.setEnabled(false);
		if(query.equals("")) {
			jtfcatid.setText(maxcatid()+1+"");
		}
		else {
			try {
				DConnection dc=new DConnection();
				ResultSet rst=dc.executeSelectQuery(query);
				rst.next();
				jtfcatid.setText(rst.getString(1));
				jtfcatname.setText(rst.getString(2));
				jcbtv.setSelectedItem(rst.getString(3));
				jcbfridge.setSelectedItem(rst.getString(4));
				jcbgyser.setSelectedItem(rst.getString(5));
				jcbac.setSelectedItem(rst.getString(6));
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
				String s1=jtfcatid.getText();
				String s2=jtfcatname.getText();
				String s3=(String)jcbtv.getSelectedItem();
				String s4=(String)jcbfridge.getSelectedItem();
				String s5=(String)jcbgyser.getSelectedItem();
				String s6=(String)jcbac.getSelectedItem();
				DConnection dc=new DConnection();
				if(query.equals(""))
					dc.executeOtherQuery("insert into roomcategory values("+s1+",'"+s2+"','"+s3+"','"+s4+"','"+s5+"','"+s6+"')");
				else
					dc.executeOtherQuery("update roomcategory set catname='"+s2+"',tv='"+s3+"',fridge='"+s4+"',gyser='"+s5+"',ac='"+s6+"'where catid="+s1);
				frmRoomcat.showTable();
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
	

		add(lblcatid);add(jtfcatid);
		add(lblcatname);add(jtfcatname);
		add(lbltv);add(jcbtv);
		add(lblfridge);add(jcbfridge);
			add(lblgyser);add(jcbgyser);
				add(lblac);add(jcbac);
		add(btnSave);add(btnClose);
		setSize(400,300);
		setLocation(CommonMethods.getCenter(getSize()));
		setVisible(true);
	}
		//	public void paintComponent(Graphics g) {
					//super.paint(g);
		//ImageIcon ii=new ImageIcon("images/hotel.jpg");
		//g.drawImage(ii.getImage(),0,0,getSize().width,getSize().height,null);
	//	}
	int maxcatid() {
		int max=0;
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select max(catid) from Roomcategory");
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
