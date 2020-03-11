import java.sql.*;
import java.awt.*;
import java.util.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
public class CommonMethods {
	static void createAllTables() {
		try {
			DConnection dc=new DConnection();
			dc.executeOtherQuery("create table if not exists users(user varchar(20) primary key,pass varchar(20),usertype enum('admin','manager','staff'))");
			dc.executeOtherQuery("create table if not exists state(stateid int primary key,statename varchar(255))");
			dc.executeOtherQuery("create table if not exists city(cityid int primary key,cityname varchar(255),stateid int)");
			dc.executeOtherQuery("create table if not exists employee(eid int primary key,ename varchar(20),address varchar(255))");
			dc.executeOtherQuery("create table if not exists roomcategory(catid int primary key,catname varchar(20),tv enum('yes','no'),fridge enum('yes','no'),gyser enum('yes','no'),ac enum('yes','no'))");		
			dc.executeOtherQuery("create table if not exists room(roomid int primary key,roomnumber int unique key,roomname varchar(255),catid int,rent int)");
			dc.executeOtherQuery("create table if not exists Guest(GuestId int primary key,name varchar(35),GuestAddress varchar(30),Dob date,city int,state int,GuestContact varchar(15) unique key)");
			dc.executeOtherQuery("create table if not exists bookings(bookingid int primary key,dateofbooking date,checkindate date,days int,checkoutdate date,adults int,kids int,catid int,roomnumber int,rent int,guestid int,estimate int,advance int,checkedin enum('yes','no'),staffid int)");
			dc.executeOtherQuery("create table if not exists checkin(checkinid int primary key,bookingid int,dateofbooking date,checkindate date,days int,checkoutdate date,adults int,kids int,catid int,roomnumber int,rent int,guestid int,estimate int,advance int)");
			dc.executeOtherQuery("create table if not exists addbill(id int primary key,date date,roomnumber int,Particulars varchar(255),description varchar(20),billno int(4),amount int(4))");
			dc.executeOtherQuery("create table if not exists bill(billnumber int primary key,date date,roomno int,totalrent int,totaladd int,finalbill int)");
			dc.executeOtherQuery("create table if not exists hoteldetails(name varchar(255),address varchar(255),email varchar(255),contact varchar(255),rooms int,floors int,amenities varchar(255),guestrating decimal(5,2),star varchar(8), gstonrent int(20),gstonadditionalCharge int(20))");
			//dc.executeOtherQuery("create table if not exists bill(billnumber int primary key,date date,roomnumber int,totaladditional decimal(8,2),gst_on_charges_per decimal(5,2) , noofdays int,rentperday int,totalrent int,discountper decimal(5,2),gst_on_rent_per decimal(5,2),finalbill decimal(8,2))");
			ResultSet rst=dc.executeSelectQuery("select count(*) from hoteldetails");
			rst.next();
			int cnt=rst.getInt(1);
			dc.close();
			if(cnt==0)
				dc.executeOtherQuery("insert into hoteldetails values('','','','',0,0,'',0.0,'',0,0)");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	static Point getCenter(Dimension frame) {
		Dimension desktop=Toolkit.getDefaultToolkit().getScreenSize();
		int x=(desktop.width-frame.width)/2;
		int y=(desktop.height-frame.height)/2;
		return new Point(x,y);
	}
	static String textValidation(String s1) {
		String s11="";
		for(int i=0;i<s1.length();i++){
			if(s1.charAt(i)=='\'')
				s11+="\\'";
			else
				s11+=s1.charAt(i);
		}
		s11=s11.trim();
		return s11;
	}
	static int getRoomCategoryId(String categoryName) {
		int catid=0;
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select catid from roomcategory where catname='"+categoryName+"'");
			rst.next();
			catid=rst.getInt(1);
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return catid;
	}
	static String getRoomCategoryName(int categoryId) {
		String catName="";
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select catname from roomcategory where catid="+categoryId);
			rst.next();
			catName=rst.getString(1);
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return catName;		
	}
	static String[] getAllRoomCategoryName() {
		String catName[]=null;
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select count(*) from roomcategory");
			rst.next();
			int cnt=rst.getInt(1);
			catName=new String[cnt];
			
			rst=dc.executeSelectQuery("select catname from roomcategory");
			int i=0;
			while(rst.next()) {
				catName[i++]=rst.getString(1);
			}
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return catName;		
	}	
	static String[] getAllcheckinroomnumber() {
		String roomnumber[]=null;
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select count(*) from checkin");
			rst.next();
			int cnt=rst.getInt(1);
			roomnumber=new String[cnt];
			
			rst=dc.executeSelectQuery("select roomnumber from checkin");
			int i=0;
			while(rst.next()) {
				roomnumber[i++]=rst.getString(1);
			}
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return roomnumber;		
	}	
	
	static String[] getAllRoomNos(String query) {
		String roomNos[]=null;
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select count(*) from room "+query);
			rst.next();
			int cnt=rst.getInt(1);
			roomNos=new String[cnt];
			
			rst=dc.executeSelectQuery("select roomnumber from room"+query);
			int i=0;
			while(rst.next()) {
				roomNos[i++]=rst.getString(1);
			}
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return roomNos;		
	}	
	static String[] getCheckIn(String roomno) {
		String data2[]=new String[14];
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select * from checkin where roomnumber="+roomno);
			rst.next();
			for(int i=0;i<14;i++)
				data2[i]=rst.getString(i+1);
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return data2;		
	}	
	
	static String[][] getAllCharges(int roomno) {
		String dt[][]=null;
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select count(*) from addbill where roomnumber="+roomno);
			rst.next();
			int cnt=rst.getInt(1);
			dt=new String[cnt][4];
			
			rst=dc.executeSelectQuery("select billno,particulars,amount from addbill where roomnumber="+roomno);
			
			for(int i=0;rst.next();i++) {
				for(int j=0;j<3;j++)
					dt[i][j]=rst.getString(j+1);
			}
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dt;		
	}	
	
	static int getGuestId(String mobile) {
		int guestId=0;
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select guestid from guest where GuestContact='"+mobile+"'");
			if(rst.next())
				guestId=rst.getInt(1);
			else
				guestId=-1;
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return guestId;
	}
	static String getGuestMobile(String gid) {
		String mobile="";
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select GuestContact from guest where guestid="+gid);
			if(rst.next())
				mobile=rst.getString(1);
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return mobile;
	}

	static String convertDateToDatabaseFormat(String s1) {
		String s2="";
		try {
			SimpleDateFormat sdf1=new SimpleDateFormat("dd-MM-yyyy");
			Date d1=sdf1.parse(s1);
			SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
			s2=sdf2.format(d1);
		}
		catch(ParseException e) {
			e.printStackTrace();
		}
		return s2;
	}
	static String convertDateToReadableFormat(String s1) {
		String s2="";
		try {
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
			Date d1=sdf1.parse(s1);
			SimpleDateFormat sdf2=new SimpleDateFormat("dd-MM-yyyy");
			s2=sdf2.format(d1);
		}
		catch(ParseException e) {
			e.printStackTrace();
		}
		return s2;
	}
	static String[] getAllstateName() {
		String stateName[]=null;
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select count(*) from state");
			rst.next();
			int cnt=rst.getInt(1);
			stateName=new String[cnt];
			
			rst=dc.executeSelectQuery("select statename from state");
			int i=0;
			while(rst.next()) {
				stateName[i++]=rst.getString(1);
			}
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return stateName;		
	}
	static String[] getAllCityName(String stateName) {
		String cityName[]=null;
		try {
			DConnection dc=new DConnection();
			ResultSet rst;
			if(stateName==null || stateName.equals(""))
				rst=dc.executeSelectQuery("select count(*) from city");
			else {
				int id=CommonMethods.getStateId(stateName);
				rst=dc.executeSelectQuery("select count(*) from city where stateid="+id);
			}
			rst.next();
			int cnt=rst.getInt(1);
			cityName=new String[cnt];
		
			if(stateName==null || stateName.equals(""))
				rst=dc.executeSelectQuery("SELECT cityname FROM city");//compare
			else {
				int id=CommonMethods.getStateId(stateName);
				rst=dc.executeSelectQuery("SELECT cityname FROM city where stateid="+id);
			}			
			
			int i=0;
			while(rst.next()) {
				cityName[i++]=rst.getString(1);
			}
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return cityName;		
	}
	static int getStateId(String stateName) {
		int stateId=0;
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select stateid from state where statename='"+stateName+"'");
			rst.next();
			stateId=rst.getInt(1);
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return stateId;
	}
	static String getStateName(int stateId) {
		String stateName="";
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select statename from state where stateid="+stateId);
			rst.next();
			stateName=rst.getString(1);
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return stateName;		
	}
	static String getRoomCategory(int roomno) {
		int catid=0;
		String catname="";
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select catid from room where roomnumber="+roomno);
			rst.next();
			catid=rst.getInt(1);
			dc.close();
			catname=getRoomCategoryName(catid);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return catname;		
	}
	static int getCityId(String cityName) {
		int cityId=0;
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select cityid from city where cityname='"+cityName+"'");
			rst.next();
			cityId=rst.getInt(1);
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return cityId;
	}
	static String getCityName(int cityId) {
		String cityName="";
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select cityname from city where cityid="+cityId);
			rst.next();
			cityName=rst.getString(1);
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return cityName;		
	}
	static boolean checkBid(int bid) {
		boolean found=false; 
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select * from bookings where bookingid="+bid);
			if(rst.next())
				found=true;
			else
				found=false;
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return found;		
	}	
	static String[] getBooking(int bid) {
		String data[]=new String[15];
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select * from bookings where bookingid="+bid);
			rst.next();
			for(int i=0;i<data.length;i++)
				data[i]=rst.getString(i+1);
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return data;		
	}
	static String[] getAllroomnumber() {
		String roomnumber[]=null;
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select count(*) from room");
			rst.next();
			int cnt=rst.getInt(1);
			roomnumber=new String[cnt];
			
			rst=dc.executeSelectQuery("select roomnumber from room");
			int i=0;
			while(rst.next()) {
				roomnumber[i++]=rst.getString(1);
			}
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return roomnumber;		
	}
	
	static int getRoom(String Room) {
		int roomno=0;
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select rent from bookings where roomnumber='"+Room+"'");
			if(rst.next())
				roomno=rst.getInt(1);
			else
				roomno=-1;
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return roomno;
	}
	static int getGstOnRent(String Room) {
		int roomno=0;
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select gstonrent from hoteldetails ");
			if(rst.next())
				roomno=rst.getInt(1);
			else
				roomno=-1;
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return roomno;
	}
	static int getGstOnaddtionalCharge(String Room) {
		int roomno=0;
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select gstonadditionalCharge from hoteldetails ");
			if(rst.next())
				roomno=rst.getInt(1);
			else
				roomno=-1;
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return roomno;
	}
	static int getAdvancePay(String Room) {
		int roomno=0;
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select advance from bookings where roomnumber='"+Room+"'");
			if(rst.next())
				roomno=rst.getInt(1);
			else
				roomno=-1;
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return roomno;
	}
	static int getNoOfdays(String Room) {
		int roomno=0;
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select days from bookings where roomnumber='"+Room+"'");
			if(rst.next())
				roomno=rst.getInt(1);
			else
				roomno=-1;
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return roomno;
	}
	static int getGuestName(String Room) {
		int roomno=0;
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select guestid from bookings where roomnumber='"+Room+"'");
			if(rst.next())
				roomno=rst.getInt(1);
			else
				roomno=0;
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return roomno;
	}
	static String getName(int guestid) {
		String name=null;
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select name from guest where GuestId='"+guestid+"'");
			if(rst.next())
				name=rst.getString(1);
			else
				name=null;
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return name;
	}
	static String getAddress(int guestid) {
		String name=null;
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select guestAddress from guest where GuestId='"+guestid+"'");
			if(rst.next())
				name=rst.getString(1);
			else
				name=null;
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return name;
	}
	static String[] getGuest(String guestid) {
		String data3[]=new String[7];
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select * from guest where GuestId='"+guestid+"'");
			rst.next();
			for(int i=0;i<7;i++)
				data3[i]=rst.getString(i+1);
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return data3;
	}
	
	static String[] getAddtionalCharge(String Query) {
		String id[]=null;
		try {
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select count(*) from addbill"+Query);
			rst.next();
			int cnt=rst.getInt(1);
			id=new String[cnt];
			
			rst=dc.executeSelectQuery("select id from addbill"+Query);
			int i=0;
			while(rst.next()) {
				id[i++]=rst.getString(1);
			}
			dc.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return id;		
	}
}
