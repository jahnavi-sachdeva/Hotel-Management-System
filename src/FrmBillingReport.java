import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import javax.swing.event.*;
import javax.swing.table.JTableHeader;
import java.sql.*;
import java.text.SimpleDateFormat;

class FrmBillingReport extends JInternalFrame {
	JTable jtb;
	JScrollPane jsp;
	JLabel lblDateFrom,lblDateTo;
	JButton btnShowAll,btnSearch;;
	JPanel p1;
	String heads[]= {"Bill no","Billing Date","Guest Id","Total Amount"};
	JTextField jtfDateFrom,jtfDateTo;
	SimpleDateFormat stf;
	Object data[][];
	String condition="";
	
	
	FrmBillingReport(JDesktopPane jdp){
		super("Billing Details",true,true,true,true);
		setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
	  
		
		lblDateFrom=new JLabel("Date From");
		lblDateFrom.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDateTo=new JLabel("Date To");
		lblDateTo.setHorizontalAlignment(SwingConstants.RIGHT);
		
		jtfDateFrom=new JTextField();
		jtfDateTo=new JTextField();
		stf=new SimpleDateFormat("dd-MM-yyyy");
		Date date=new Date();
		jtfDateFrom.setText(stf.format(date));
		jtfDateTo.setText(stf.format(date));

		btnShowAll=new JButton("Show All");
		btnShowAll.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				condition="";
				showTable();
			}
		});
		btnShowAll.setForeground(Color.white);
		btnShowAll.setBackground(Color.gray);	
		btnShowAll.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		
		btnSearch=new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String s1=CommonMethods.convertDateToDatabaseFormat(jtfDateFrom.getText());
				String s2=CommonMethods.convertDateToDatabaseFormat(jtfDateTo.getText());
				condition= " where checkoutdate between '"+s1+"' and '"+s2+"'";
				showTable();
			}
		});	
		
		add(btnShowAll,BorderLayout.SOUTH);
		JPanel p1=new JPanel();
		p1.setLayout(new GridLayout(1,5));
		p1.add(lblDateFrom);
		p1.add(jtfDateFrom);
		p1.add(lblDateTo);
		p1.add(jtfDateTo);
		p1.add(btnSearch);
		add(p1,BorderLayout.NORTH);
		
		setSize(700,400);
		Point p2=CommonMethods.getCenter(getSize());
		setLocation(p2.x,p2.y-50);
		setVisible(true);
		btnShowAll.doClick();
	}
	void showTable() {
		try {
			if(jsp!=null)
				remove(jsp);
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select count(*) from CheckIn"+condition);
			rst.next();
			int n=rst.getInt(1);
			dc.close();
			data=new Object[n][4];
			rst=dc.executeSelectQuery("select bookingid,checkoutdate,guestid,estimate from CheckIn "+condition+";");
			for(int i=0;rst.next();i++) {
				for(int j=0;j<4;j++)
					data[i][j]=rst.getString(j+1);
			}
			jtb=new JTable(data,heads);
			jtb.setRowHeight(25);
		    jtb.setRowMargin(5);
		    Dimension d1=new Dimension(5,5);
		    jtb.setIntercellSpacing(d1);
		    jtb.setGridColor(Color.black);
		    jtb.setShowGrid(true);
		    jtb.setForeground(Color.red);
			jtb.setBackground(new Color(255,255,255));
			jtb.setFont(new Font(Font.SERIF,Font.BOLD+Font.ITALIC,15));
			JTableHeader header=jtb.getTableHeader();
			header.setForeground(Color.white);
			header.setBackground(new Color(64,0,128));
			header.setFont(new Font(Font.SERIF,Font.BOLD,17));
			jtb.setSelectionForeground(Color.black);
			jtb.setSelectionBackground(Color.orange);
			jsp=new JScrollPane(jtb);
			add(jsp);
			revalidate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
}