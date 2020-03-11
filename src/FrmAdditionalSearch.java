import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class FrmAdditionalSearch extends JInternalFrame {
	JLabel lblField,lblValue;
	JComboBox<String> jcbField;
	JTextField jtfValue;
	JButton btnSearch,btnClose;
	FrmAdditionalSearch(FrmAdditional frmAdditional){
		super("Additional charges Search",true,true,true,true);
		setLayout(new GridLayout(3,2,20,20));
		lblField=new JLabel("Field");
		lblField.setForeground(new Color(128,0,255));
		lblField.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));

		lblValue=new JLabel("Value");
		lblValue.setForeground(new Color(128,0,255));
		lblValue.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));

		jcbField=new JComboBox();
		jcbField.setForeground(new Color(128,0,255));
		jcbField.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));

		jcbField.addItem("ID");
		jcbField.addItem("date");
		jcbField.addItem("Roomnumber");
		jtfValue=new JTextField();
		jtfValue.setForeground(new Color(128,0,255));
		jtfValue.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,18));

		btnSearch=new JButton("Search");
		btnSearch.setForeground(Color.white);
		btnSearch.setBackground(new Color(128,0,255));	
		btnSearch.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String cname=(String)jcbField.getSelectedItem();
				String cvalue=jtfValue.getText();
				frmAdditional.condition=" where "+cname+" = '"+cvalue+"'";
				frmAdditional.showTable();
				dispose();
			}
		});
		
		btnClose=new JButton("Close");
		btnClose.setForeground(Color.white);
		btnClose.setBackground(new Color(128,0,255));	
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
