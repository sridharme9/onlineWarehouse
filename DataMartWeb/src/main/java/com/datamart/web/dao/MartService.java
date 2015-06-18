package com.datamart.web.dao;
import com.datamart.web.utilities.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MartService {
	private Connection connection;

	public MartService() {
		connection = DBUtility.getConnection();
	}
	
	public String CreateDimension(Map<String, String> params){
		System.out.println(params);
		String message = "{ \"status\":\"200\"}";
		try{
			String dimName = params.remove("dimName");
			String mart = params.remove("mart");
			String type = params.remove("type");
			String table = "facts";
			if(type.equals("dim")){
				table = "dimensions";
			}
			Statement statement = connection.createStatement();
			String query = "insert into "+ table +" values (null,'" + dimName + "'," + mart+")";
			statement.executeUpdate(query);
			query = "select max(" + type + "_id) as maxid from " + table;
			System.out.println(query);
			ResultSet rs = statement.executeQuery(query);
			rs.next();
			int dim_id = rs.getInt("maxid");
			System.out.println(dim_id);
			query = "create table " + dimName + "(table_id int, mart_id int,";
			for(Map.Entry<String, String> entry : params.entrySet()){
				query = query + entry.getValue();
				if(type.equals("dim")){
					query += " varchar(255),";
				}else{
					query += " int,";
				}
				
			}
			//query = query.substring(0, query.length()-1);
			query = query + "constraint fk_"+dimName +" foreign key(table_id) references " + table + "(" + type +"_id)" + ")";
			System.out.println(query);
			statement.executeUpdate(query);
		}catch(SQLException ex){
			ex.printStackTrace();
			message = "{ \"status\":\"201\"}";
		}
		return message;
		
	}
	
	public Map<String, List<String>> GetMartDetails(String mart_id){
		
		Map<String, List<String>> marts = new HashMap<String,List<String>>();
		try{
			Statement statement = connection.createStatement();
			String query = "select dim_name from dimensions where mart_id=" + mart_id;
			ResultSet rs = statement.executeQuery(query);
			List<String> dims = new ArrayList<String>();
			while(rs.next()){
				dims.add(rs.getString(1));
			}
			marts.put("dims", dims);
			query = "select fact_name from facts where mart_id=" + mart_id;
			rs = statement.executeQuery(query);
			dims = new ArrayList<String>();
			while(rs.next()){
				dims.add(rs.getString(1));
			}
			marts.put("facts", dims);
		}catch(SQLException ex){
			ex.printStackTrace();
			//marts.add("No Table exists");
		}
		return marts;
		
	}
	
	public List<String> GetDimColumns(String dimName){
		List<String> marts = new ArrayList<String>();
		try{
			Statement statement = connection.createStatement();
			String query = "select * from " + dimName + " limit 1";
			ResultSet rs = statement.executeQuery(query);
			ResultSetMetaData rmd = rs.getMetaData();
			int count = rmd.getColumnCount();
			for(int i=0; i<count; i++){
				String col = rmd.getColumnName(i+1);
				if(!col.contains("id")){
					marts.add(col);
				}
			}
		}catch(SQLException ex){
			ex.printStackTrace();
			marts.add("No Table exists");
		}
		return marts;
	}
	
	public String InsertDim(Map<String, String> params){
		String message = "{ \"status\":\"200\"}";
		try{
			String dimTable = params.remove("dimTable");
			Statement statement = connection.createStatement();
			String query = "insert into "+ dimTable +"(";
			String values = ") values('";
			for(Map.Entry<String, String> entry : params.entrySet()){
				query = query + entry.getKey() + ",";
				values = values + entry.getValue() + "','";
			}
			
			values = values.substring(0, values.length()-2) + ")";
			query = query.substring(0,query.length()-1) + values;
			System.out.println(query);
			statement.executeUpdate(query);
		}catch(SQLException ex){
			ex.printStackTrace();
			message = "{ \"status\":\"201\"}";
		}
		return message;
	}
	
	public List<Map<String, Object>> GetMarts(){
		List<Map<String, Object>> marts = new ArrayList<Map<String,Object>>();
		try{
			Statement statement = connection.createStatement();
			String query = "select * from datamart";
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				marts.add(fetchRow(rs));
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return marts;
	}
	
	public List<Map<String, Object>> GetDims(String mart_id){
		List<Map<String, Object>> dims = new ArrayList<Map<String,Object>>();
		try{
			Statement statement = connection.createStatement();
			String query = "select * from dimensions where mart_id=" + mart_id;
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				dims.add(fetchRow(rs));
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return dims;
	}
	
	public List<Map<String, Object>> GetFacts(String mart_id){
		List<Map<String, Object>> dims = new ArrayList<Map<String,Object>>();
		try{
			Statement statement = connection.createStatement();
			String query = "select * from facts where mart_id=" + mart_id;
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				dims.add(fetchRow(rs));
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return dims;
	}
	
	public String CreateMart(String mart){
		String message = "{ \"status\":\"200\"}";
		try{
			Statement statement = connection.createStatement();
			String query = "insert into datamart values (null,'" + mart + "')";
			statement.executeUpdate(query);
		}catch(SQLException ex){
			ex.printStackTrace();
			message = "{ \"status\":\"201\"}";
		}
		return message;
	}
	
	 public Map<String, Object> fetchRow(ResultSet rs) throws SQLException {
	        ResultSetMetaData meta = rs.getMetaData();
	        List<String> columnList = new ArrayList<>();

	        int columnCount = meta.getColumnCount();
	        for (int i = 0; i < columnCount; i++) {
	            columnList.add(meta.getColumnName(i + 1));
	        }
	        Map<String, Object> row = new HashMap<>();
	        for (String columnName : columnList) {

	            row.put(columnName, rs.getObject(columnName));

	        }
	        return row;

	    }
	
}
