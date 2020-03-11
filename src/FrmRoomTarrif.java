import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class FrmRoomTarrif extends JInternalFrame{
	int cnt,cnts,flag=0;
	String roomnos[],checkinroomnos[];
	JTable jtb;
	JScrollPane jsp;
	JPanel panButtons,panRoom,panDisplay;
	JButton btnSearch,btnShowAll,btn[];
	String heads[]= {"Room Id","Room Number","Room Name","Room Category","Per Day Rent"};
	Object data[][];
	String condition="";
	FrmRoomTarrif(JDesktopPane jdp){
		super("Room Details",true,true,true,true);
		btnSearch=new JButton("Search");
		btnSearch.setForeground(Color.white);
		btnSearch.setBackground(Color.gray);
		btnSearch.setFont(new Font(Font.SERIF,Font.ITALIC+Font.BOLD,20));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				FrmRoomTarrifSearch cf2=new FrmRoomTarrifSearch(FrmRoomTarrif.this);
				jdp.add(cf2);
				jdp.setComponentZOrder(cf2, 0);
				jdp.setComponentZOrder(FrmRoomTarrif.this, 1);
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
		
		//Check in rooms		
		checkinroomnos=CommonMethods.getAllcheckinroomnumber();		
		//total rooms
		roomnos=CommonMethods.getAllRoomNos("");
		btn=new JButton[roomnos.length];
		for(int i=0;i<roomnos.length;i++) {
			btn[i]=new JButton(roomnos[i]);
		}
		//compare check in rooms with total rooms 
		
		for(int i=0;i<roomnos.length;i++) {
			boolean found=false;
			for(int j=0;j<checkinroomnos.length;j++) {
				if(roomnos[i].equals(checkinroomnos[j])) {
					found=true;
					break;
				}
			}
			if(found==false)
				btn[i].setBackground(Color.GREEN);
			else
				btn[i].setBackground(Color.RED);
		}
		
		
		panRoom=new JPanel();
		for(int i=0;i<btn.length;i++) {
			panRoom.add(btn[i]);
		}
		panRoom.setLayout(new FlowLayout());
		
		panDisplay=new JPanel();
		panDisplay.setLayout(new GridLayout(2,1));
		panDisplay.add(panRoom);
		panDisplay.add(panButtons);
		add(panDisplay,"South");
		
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
			ResultSet rst=dc.executeSelectQuery("select count(*) from room"+condition);
			rst.next();
			int n=rst.getInt(1);
			dc.close();
			data=new Object[n][5];
			rst=dc.executeSelectQuery("select * from room "+condition);
			for(int i=0;rst.next();i++) {
				data[i][0]=rst.getString(1);
				data[i][1]=rst.getString(2);
				data[i][2]=rst.getString(3);
				data[i][3]=CommonMethods.getRoomCategoryName(rst.getInt(4));
				data[i][4]=rst.getString(5);
				
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
