package azimcemimrag.codingcase.cart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import azimcemimrag.codingcase.costcalculator.intf.ICostCalculator;
import azimcemimrag.codingcase.discount.intf.ICampaign;
import azimcemimrag.codingcase.discount.intf.ICoupon;

/**
 * ShoppingCart for products
 * 
 * @author azimcem
 *
 */
public class ShoppingCart {
	private Map<Product, Integer> items;
	private List<ICampaign> campaignList;
	private List<ICoupon> couponList;
	private ICostCalculator costCalculator;	

	public ShoppingCart(ICostCalculator costCalculator) {
		items = new HashMap<Product, Integer>();
		this.costCalculator=costCalculator;
	}

	/**
	 * adds product with quantity to chart.
	 * if a product is added again then quantity of this product is updated.
	 * 
	 * @param product
	 * @param quantity
	 */	
	public void addItem(Product product, int quantity) {
		int initalQuantity = 0;
		if (items.get(product) != null) {
			initalQuantity = items.get(product);
		}
		items.put(product, quantity + initalQuantity);
	}

	/**
	 * applies campaigns to chart.
	 * 
	 * @param campaigns
	 */
	public void applyCampaigns(ICampaign... campaigns) {
		if (campaignList == null) {
			campaignList = new ArrayList<ICampaign>();
		}
		for (ICampaign campaign : campaigns) {
			campaignList.add(campaign);
		}
	}

	/**
	 * applies coupon to chart.
	 * 
	 * @param coupon
	 */
	public void applyCoupon(ICoupon coupon) {
		if (couponList == null) {
			couponList = new ArrayList<ICoupon>();
		}
		if (getTotalAmountAfterCampaign() >= coupon.getMinimumApplicableCartAmount()) {
			couponList.add(coupon);
		}
	}

	/**
	 * gets total discount for only campaigns.
	 * 
	 * @return discount
	 */
	public double getCampaignDiscount() {
		double totalDiscount = 0;
		for (Entry<Product, Integer> productMap : items.entrySet()) {
			Product product = productMap.getKey();
			int quantity = productMap.getValue();
			totalDiscount = totalDiscount + getCampaignDiscountByProduct(product, quantity);

		}
		return totalDiscount;
	}
	
	/**
	 * gets total campaign discount of a product.
	 * 
	 * @param product
	 * @param quantity
	 * @return discount
	 */
	public double getCampaignDiscountByProduct(Product product, int quantity) {
		if (campaignList == null || campaignList.isEmpty()) {
			return 0;
		}
		double maximumDiscountAmount = 0;
		for (ICampaign campaign : campaignList) {
			if (product.isProductCategory(campaign.getCategory())
					&& quantity > campaign.getMinimumApplicableQuantity()) {
				double currentDiscount = campaign.calculateDiscount(quantity * product.getPrice());
				if (currentDiscount > maximumDiscountAmount) {
					maximumDiscountAmount = currentDiscount;
				}
			}
		}
		return maximumDiscountAmount;
	}

	/**
	 * get total coupon discount of a cart.
	 * 
	 * @return discount
	 */
	public double getCouponDiscount() {
		if (couponList == null || couponList.isEmpty()) {
			return 0;
		}
		double totalCouponDiscount = 0;

		for (ICoupon coupon : couponList) {
			totalCouponDiscount = totalCouponDiscount + coupon.calculateDiscount(getTotalAmountAfterCampaign());
		}
		return totalCouponDiscount;
	}

	/**
	 * get remaining amount of products after campaigns and coupons
	 * 
	 * @return discount
	 */
	public double getTotalAmountAfterDiscounts() {
		return getTotalAmount() - getCampaignDiscount() - getCouponDiscount();
	}

	/**
	 * get delivery cost.
	 * 
	 * @return delivery cost
	 */
	public double getDeliveryCost() {
		return costCalculator.calculateFor(this);
	}
	
	/**
	 * get number of deliveries.
	 * 
	 * @return numberOfDeliveries
	 */
	public int getNumberOfDeliveries() {
		Set<Category> category = new HashSet<Category>();
		for (Product product : items.keySet()) {
			category.add(product.getCategory());
		}
		return category.size();
	}
	
	/**
	 * get the number of product.
	 * 
	 * @return number of product.
	 */
	public int getNumberOfProducts() {
		return items.size();
	}
	
	/**
	 * Groups products by categories 
	 * and prints specific information about the cart.
	 * 
	 */
	public void print() {		
		printInfoByCategories();
		System.out.println("-->coupun discount:" + getCouponDiscount());
		System.out.println("-->total amount:" + getTotalAmount());
		System.out.println("-->total amount after discounts:" + getTotalAmountAfterDiscounts());
		System.out.println("-->delivery cost:" + getDeliveryCost());
	}
	
	public List<ICampaign> getCampaignList() {
		return campaignList;
	}

	public List<ICoupon> getCouponList() {
		return couponList;
	}
	
	public Map<Product, Integer> getItems() {
		return items;
	}
	
	private void printInfoByCategories() {
		System.out.println("<Categories>");		
		Map<Category, List<Product>> categoryProductMap = getProductsMapByCategory();
		for (Entry<Category, List<Product>> productMap : categoryProductMap.entrySet()) {
			Category category = productMap.getKey();
			System.out.println("  -->category:" + category.getTitle());
			for(Product product : productMap.getValue()) {
				System.out.println("    -->products:"            + product.getTitle());
				System.out.println("      -->quantity:"          + items.get(product));
				System.out.println("      -->unit price:"        + product.getPrice());
				System.out.println("      -->total price:"       + product.getPrice() * items.get(product));
				System.out.println("      -->campaign discount:" + getCampaignDiscountByProduct(product, items.get(product)));
			}			
		}
		System.out.println("<Categories>");
	}
	
	private Map<Category, List<Product>>  getProductsMapByCategory() {
		Map<Category, List<Product>> categoryProductMap = new HashMap<Category, List<Product>>();
		List<Product> productList = null;
		for (Product product : items.keySet()) {
			if(categoryProductMap.containsKey(product.getCategory())) {
				productList = categoryProductMap.get(product.getCategory());
			} else {
				productList = new ArrayList<Product>();
			}		
			for (Category category : product.getCategoryList()) {
				if (category == product.getCategory()) {
					productList.add(product);
					categoryProductMap.put(category, productList);
				} else {
					categoryProductMap.put(category, new ArrayList<Product>());
				}				
			}
		}
		return categoryProductMap;
	}
	
	
	private double getTotalAmountAfterCampaign() {
		return getTotalAmount() - getCampaignDiscount();
	}

	private double getTotalAmount() {
		double cartTotal = 0;
		for (Entry<Product, Integer> productMap : items.entrySet()) {
			Product product = productMap.getKey();
			int quantity = productMap.getValue();
			cartTotal = cartTotal + (product.getPrice() * quantity);
		}
		return cartTotal;
	}
}
