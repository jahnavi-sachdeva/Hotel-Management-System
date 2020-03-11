
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
public class FrmEditCity extends JInternalFrame {
	JLabel lblCityId,lblCityName,lblStateName;
	JTextField jtfCityId,jtfCityName;
	JComboBox<String> jcbStateName;
	JButton btnSave,btnClose;
	String btnLabel="Save";
	FrmEditCity(FrmCity frmCity,String query){
		super("Add/Edit City",true,true,true,true);
		setLayout(new GridLayout(4,2));

		lblCityId=new JLabel("City Id");
		lblCityId.setForeground(new Color(10,10,10));
		lblCityId.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		lblCityName=new JLabel("City Name");
		lblCityName.setForeground(new Color(10,10,10));
		lblCityName.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		lblStateName=new JLabel("State Name");
		lblStateName.setForeground(new Color(10,10,10));
		lblStateName.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		
		jtfCityId=new JTextField();
		jtfCityId.setForeground(new Color(255,0,0));
		jtfCityId.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		jtfCityName=new JTextField();
		jtfCityName.setForeground(new Color(255,0,0));
		jtfCityName.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		jcbStateName=new JComboBox<>(CommonMethods.getAllstateName());
		jcbStateName.setForeground(new Color(255,0,0));
		jcbStateName.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		jtfCityId.setEnabled(false);
		if(query.equals("")) {
			jtfCityId.setText(maxCityId()+1+"");
		}
		else {
			try {
				DConnection dc=new DConnection();
				ResultSet rst=dc.executeSelectQuery(query);
				rst.next();
				jtfCityId.setText(rst.getString(1));
				jtfCityName.setText(rst.getString(2));
				jcbStateName.setSelectedItem(CommonMethods.getStateName(rst.getInt(3)));
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
				String s1=jtfCityId.getText();
				String s2=jtfCityName.getText();
				int s3=CommonMethods.getStateId((String)jcbStateName.getSelectedItem());
				DConnection dc=new DConnection();
				if(query.equals(""))
					dc.executeOtherQuery("insert into city values('"+s1+"','"+s2+"',"+s3+")");
				else
					dc.executeOtherQuery("update city set cityname='"+s2+"',stateid="+s3+" where cityid="+s1);
				frmCity.showTable();
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

		add(lblCityId);add(jtfCityId);
		add(lblCityName);add(jtfCityName);
		add(lblStateName);add(jcbStateName);
		add(btnSave);add(btnClose);		
		setSize(500,300);
		setLocation(CommonMethods.getCenter(getSize()));
		setVisible(true);
	}
	int maxCityId() {
		int max=0;
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select max(cityid) from city");
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
