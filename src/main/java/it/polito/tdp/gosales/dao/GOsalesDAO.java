package it.polito.tdp.gosales.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.gosales.model.Arco;
import it.polito.tdp.gosales.model.DailySale;
import it.polito.tdp.gosales.model.Products;
import it.polito.tdp.gosales.model.Retailers;

public class GOsalesDAO {
	/**
	 * Metodo per leggere la lista di tutti i rivenditori dal database
	 * 
	 * 
	 * @return
	 */

	public List<Retailers> getAllRetailers(){
		String query = "SELECT * from go_retailers";
		List<Retailers> result = new ArrayList<Retailers>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Retailers(rs.getInt("Retailer_code"), 
						rs.getString("Retailer_name"),
						rs.getString("Type"), 
						rs.getString("Country")));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}
	
	
	/**
	 * Metodo per leggere la lista di tutti i prodotti dal database
	 * @return
	 */
	public Map<Integer, Products> getAllProducts(){
		String query = "SELECT * from go_products";
		Map<Integer, Products> result = new HashMap<Integer, Products>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Products p = new Products(rs.getInt("Product_number"), 
						rs.getString("Product_line"), 
						rs.getString("Product_type"), 
						rs.getString("Product"), 
						rs.getString("Product_brand"), 
						rs.getString("Product_color"),
						rs.getDouble("Unit_cost"), 
						rs.getDouble("Unit_price"));
				if (!result.containsKey(rs.getInt("Product_number"))) {
					result.put((rs.getInt("Product_number")), p);
				}
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}

	
	/**
	 * Metodo per leggere la lista di tutte le vendite nel database
	 * @return
	 */
	public List<DailySale> getAllSales(){
		String query = "SELECT * from go_daily_sales";
		List<DailySale> result = new ArrayList<DailySale>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new DailySale(rs.getInt("retailer_code"),
				rs.getInt("product_number"),
				rs.getInt("order_method_code"),
				rs.getTimestamp("date").toLocalDateTime().toLocalDate(),
				rs.getInt("quantity"),
				rs.getDouble("unit_price"),
				rs.getDouble("unit_sale_price")  ));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<Integer> getAnno(){
		String sql = "select distinct year(Date) "
				+ "from go_daily_sales "
				+ "where year(Date)<=2018 and year(Date) >=2015 ";
		List<Integer> result = new ArrayList<Integer>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(rs.getInt("year(Date)")) ;
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<String> getColore(){
		String sql = "select distinct product_color "
				+ "from go_products "
				+ "order by product_color asc ";
		List<String> result = new ArrayList<String>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(rs.getString("product_color")) ;
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<Products> getVertici(String colore){
		String sql = "select distinct go_products.* "
				+ "from go_products "
				+ "where product_color = ? ";
		List<Products> result = new ArrayList<Products>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, colore);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Products(rs.getInt("Product_number"), 
						rs.getString("Product_line"), 
						rs.getString("Product_type"), 
						rs.getString("Product"), 
						rs.getString("Product_brand"), 
						rs.getString("Product_color"),
						rs.getDouble("Unit_cost"), 
						rs.getDouble("Unit_price")));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<Arco> getArchi(int anno,String colore,Map<Integer, Products> idMap){
		String sql = "select distinct gp1.Product_number as p1, gp2.Product_number as p2, COUNT(distinct gds1.Date) as peso "
				+ "from go_products gp1, go_daily_sales gds1, go_daily_sales gds2, go_products gp2 "
				+ "where Year(gds1.Date) = ? and "
				+ "Year(gds1.Date) = Year(gds2.Date) "
				+ "and gp1.Product_color = ? and "
				+ "gp1.Product_color = gp2.Product_color and "
				+ "gp1.Product_number = gds1.Product_number and "
				+ "gp2.Product_number = gds2.Product_number and "
				+ "gds1.Date = gds2.Date and "
				+ "gds1.Retailer_code = gds2.Retailer_code and "
				+ "gds1.Product_number > gds2.Product_number "
				+ "group by gp1.Product_number , gp2.Product_number "
				+ "order by peso desc ";
		List<Arco> result = new ArrayList<Arco>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setString(2, colore);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Arco(idMap.get(rs.getInt("p1")),idMap.get(rs.getInt("p2")),rs.getInt("peso")));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}
}
