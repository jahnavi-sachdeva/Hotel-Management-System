
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class FrmCheckInSearch extends JInternalFrame {
	JLabel lblField,lblValue;
	JComboBox<String> jcbField;
	JTextField jtfValue;
	JButton btnSearch,btnClose;
	FrmCheckInSearch(FrmCheckIn frmCheckIn){
		super("CheckIn Search",true,true,true,true);
		setLayout(new GridLayout(3,2,20,20));
		lblField=new JLabel("Field");
		
		lblValue=new JLabel("Value");
		
		jcbField=new JComboBox();
	
		jcbField.addItem("checkid");
		jcbField.addItem("name");
		jcbField.addItem("guestid");
		jtfValue=new JTextField();
	
		btnSearch=new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String cname=(String)jcbField.getSelectedItem();
				String cvalue=jtfValue.getText();
				frmCheckIn.condition=" where "+cname+" = '"+cvalue+"'";
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
		add(lblField);add(jcbField);
		add(lblValue);add(jtfValue);
		add(btnSearch);add(btnClose);
		setSize(400,300);
		setLocation(100,100);
		setVisible(true);
	}
}

