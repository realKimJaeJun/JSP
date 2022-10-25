package config;

public class SQL {
	
		// customer
		public static final String SELECT_CUSTOMER = "select * from `customer`";
		
		
		// order
		public static final String SELECT_ORDER = "SELECT o.orderNo, c.name, p.prodName, o.orderCount, o.orderDate"
												+ "from `customer` AS c INNER JOIN `order` AS o ON c.custId = o.orderId"
												+ "INNER JOIN `product` AS p ON o.orderProduct = p.prodNo ORDER BY `orderNo` ASC";
		public static final String INSERT_ORDER = "insert into `order` values(?,?,?,now())";
													
		
		// product
		public static final String SELECT_PRODUCT = "select * from `product`";
		
		
	}