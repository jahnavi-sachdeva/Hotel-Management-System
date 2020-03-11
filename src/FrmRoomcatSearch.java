import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class FrmRoomcatSearch extends JInternalFrame {
	JLabel lblField,lblValue;
	JComboBox<String> jcbField;
	JTextField jtfValue;
	JButton btnSearch,btnClose;
	FrmRoomcatSearch(FrmRoomcat frmRoomcat){
		super("Roomcategory Search",true,true,true,true);
		setLayout(new GridLayout(3,2,20,20));
		lblField=new JLabel("Field");
		lblField.setForeground(new Color(10,10,10));
		lblField.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));

		lblValue=new JLabel("Value");
		lblValue.setForeground(new Color(10,10,10));
		lblValue.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));

		jcbField=new JComboBox();
		jcbField.setForeground(new Color(10,10,10));
		jcbField.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,22));

		jcbField.addItem("catid");
		jcbField.addItem("catname");
		jcbField.addItem("tv");
		jcbField.addItem("fridge");
		jcbField.addItem("gyser");
		jcbField.addItem("ac");
		
		jtfValue=new JTextField();
		jtfValue.setForeground(new Color(255,0,0));
		jtfValue.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));

		btnSearch=new JButton("Search");
		btnSearch.setForeground(Color.white);
		btnSearch.setBackground(Color.gray);	
		btnSearch.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String cname=(String)jcbField.getSelectedItem();
				String cvalue=jtfValue.getText();
				frmRoomcat.condition=" where "+cname+" = '"+cvalue+"'";
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
		add(lblField);add(jcbField);
		add(lblValue);add(jtfValue);
		add(btnSearch);add(btnClose);
		setSize(400,300);
		setLocation(CommonMethods.getCenter(getSize()));
		setVisible(true);
	}
}
