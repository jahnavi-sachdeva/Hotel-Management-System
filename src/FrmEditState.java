
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
public class FrmEditState extends JInternalFrame {
	JLabel lblStateId,lblStateName;
	JTextField jtfStateId,jtfStateName;
	JButton btnSave,btnClose;
	String btnLabel="Save";
	FrmEditState(FrmState frmState,String query){
		super("Add/Edit State",true,true,true,true);
		setLayout(new GridLayout(3,2));

		lblStateId=new JLabel("State Id");
		lblStateId.setForeground(new Color(10,10,10));
		lblStateId.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		lblStateName=new JLabel("State Name");
		lblStateName.setForeground(new Color(10,10,10));
		lblStateName.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));
		
		jtfStateId=new JTextField();
		jtfStateId.setForeground(new Color(255,0,0));
		jtfStateId.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		jtfStateName=new JTextField();
		jtfStateName.setForeground(new Color(255,0,0));
		jtfStateName.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		jtfStateId.setEnabled(false);
		if(query.equals("")) {
			jtfStateId.setText(maxStateId()+1+"");
		}
		else {
			try {
				DConnection dc=new DConnection();
				ResultSet rst=dc.executeSelectQuery(query);
				rst.next();
				jtfStateId.setText(rst.getString(1));
				jtfStateName.setText(rst.getString(2));
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
				String s1=jtfStateId.getText();
				String s2=jtfStateName.getText();
				DConnection dc=new DConnection();
				if(query.equals(""))
					dc.executeOtherQuery("insert into state values('"+s1+"','"+s2+"')");
				else
					dc.executeOtherQuery("update state set statename='"+s2+"' where stateid="+s1);
				frmState.showTable();
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

		add(lblStateId);add(jtfStateId);
		add(lblStateName);add(jtfStateName);
		add(btnSave);add(btnClose);		
		setSize(500,300);
		setLocation(CommonMethods.getCenter(getSize()));
		setVisible(true);
	}
	int maxStateId() {
		int max=0;
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select max(stateid) from state");
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
