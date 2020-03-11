import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.*;


class FrmGuestList extends JInternalFrame {
	JTable jtb;
	JScrollPane jsp;
	JPanel panButtons;
	JButton btnSearch,btnShowAll;
	String heads[]= {"GuestId","Name","Address","Date of birth","city","State","Contact"};
	Object data[][];
	String condition="";
	FrmGuestList(JDesktopPane jdp){
		super("Guest Details",true,true,true,true);
		setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		btnSearch=new JButton("Search");
		btnSearch.setForeground(Color.white);
		btnSearch.setBackground(Color.gray);
		btnSearch.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				FrmGuestListSearch cf2=new FrmGuestListSearch(FrmGuestList.this);
				jdp.add(cf2);
				jdp.setComponentZOrder(cf2, 0);
				jdp.setComponentZOrder(FrmGuestList.this, 1);
				
			}
		});
	
		btnShowAll=new JButton("Show All");
		btnShowAll.setForeground(Color.white);
		btnShowAll.setBackground(Color.gray);	
		btnShowAll.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		btnShowAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				condition="";
				showTable();
			}
		});
		panButtons=new JPanel();
		panButtons.setLayout(new GridLayout(1,2));
		panButtons.add(btnSearch);
		panButtons.add(btnShowAll);
	
		add(panButtons,"South");
		//Table
		showTable();
	
		setSize(700,400);
		Point p1=CommonMethods.getCenter(getSize());
		setLocation(p1.x,p1.y-50);
		setVisible(true);
	}
	void showTable() {
		try {
			if(jsp!=null)
				remove(jsp);
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select count(*) from checkin"+condition);
			rst.next();
			int n=rst.getInt(1);
			dc.close();
			data=new Object[n][7];
			rst=dc.executeSelectQuery("select guestid from checkin "+condition);
	
			for(int i=0;rst.next();i++) {
				String guestid=rst.getString(1);
				String dt[]=CommonMethods.getGuest(guestid);
				for(int j=0;j<7;j++) {
					if(j==5)
						data[i][j]=CommonMethods.getStateName(Integer.parseInt(dt[j]));
					else if(j==4)
						data[i][j]=CommonMethods.getCityName(Integer.parseInt(dt[j]));
					else
						data[i][j]=dt[j];
				}
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