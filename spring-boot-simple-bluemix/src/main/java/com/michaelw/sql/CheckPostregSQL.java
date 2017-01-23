package com.michaelw.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CheckPostregSQL {
	private void process() throws Throwable {
		System.out.println(" === CheckPostregSQL ");
		String timeStr = "UA";
		try {
			String[] serviceInfo = getServiceInfo();

			Class.forName("org.postgresql.Driver").newInstance();
			Connection conn = DriverManager.getConnection(serviceInfo[0], serviceInfo[1], serviceInfo[2]);
			Statement st = conn.createStatement();
			String sql = "SELECT NOW() AS pgTime ";
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			// System.out.println( "PG Time: " + rs.getTime(1) );
			timeStr = rs.getTime(1).toString();
			rs.close();
			st.close();
			conn.close();
			System.out.println("Success!!");
		} catch (Exception e) {
			System.out.println("Failed: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			new CheckPostregSQL().process();
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	public String[] getServiceInfo() throws Exception {
		String[] info = new String[3];
		info[0] = "jdbc:postgresql://sl-us-dal-9-portal.0.dblayer.com:18084/compose";

		info[1] = "admin"; // username;
		info[2] = "CLOCHEBPQANRJXHV"; // password;
		System.out.println(" i info[0]=" + info[0]);
		System.out.println(" i info[1]=" + info[1]);
		System.out.println(" i info[2]=" + info[2]);

		return info;
	}
}
