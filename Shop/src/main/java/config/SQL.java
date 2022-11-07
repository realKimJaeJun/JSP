package config;

public class SQL {
	
		// customer
		public static final String SELECT_CUSTOMER = "select * from `customer`";
		
		
		// order
		public static final String SELECT_ORDER = "SELECT a.*, b.`name`, c.`prodName` FROM `order` AS a "
												+ "JOIN `customer` AS b ON a.orderId = b.custId "
												+ "JOIN `product` AS c ON a.orderProduct = c.prodNo";
		
		public static final String INSERT_ORDER = "insert into `order` (`orderId`, `orderProduct`, `orderCount`, `orderDate`) "
												+ "values (?,?,?,NOW())";
													
		
		// product
		public static final String SELECT_PRODUCT = "select * from `product`";
		
		
	}