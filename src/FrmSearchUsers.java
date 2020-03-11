import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class FrmSearchUsers extends JInternalFrame {
	JLabel lblField,lblValue;
	JComboBox<String> jcbField;
	JTextField jtfValue;
	JButton btnSearch,btnClose;
	FrmSearchUsers(FrmUsers frmUsers){
		super("Users Search",true,true,true,true);
		setLayout(new GridLayout(3,2,20,20));
		lblValue=new JLabel("Username");
		lblField=new JLabel("Field");
		jcbField=new JComboBox();
		jcbField.addItem("user");
		jcbField.addItem("pass");
		jcbField.addItem("usertype");
		jtfValue=new JTextField();
	
		btnSearch=new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String field=(String)jcbField.getSelectedItem();
				String value=jtfValue.getText();
				frmUsers.condition=" where "+field+"= '"+value+"'";
				frmUsers.showTable();
				dispose();
			}
		});
		
		btnClose=new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				dispose();
			}
		});
		add(lblField);add(jcbField);
		add(lblValue);add(jtfValue);
		add(btnSearch);add(btnClose);
		setSize(400,300);
		setLocation(CommonMethods.getCenter(getSize()));
		setVisible(true);
	}
}

