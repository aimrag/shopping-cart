package carttest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import azimcemimrag.codingcase.cart.Category;
import azimcemimrag.codingcase.cart.Product;
import azimcemimrag.codingcase.cart.ShoppingCart;
import azimcemimrag.codingcase.costcalculator.impl.DeliveryCostCalculator;
import azimcemimrag.codingcase.discount.DiscountType;
import azimcemimrag.codingcase.discount.factory.DiscountProducer;
import azimcemimrag.codingcase.discount.intf.ICampaign;
import azimcemimrag.codingcase.discount.intf.ICoupon;

public class CartTest {
	Category food;
	Category fruit;
	Category electronic;
	
	Product apple;
	Product pc;
	Product banana;
	
	ICampaign campaign1;
	ICampaign campaign2;
	ICampaign campaign3;

	ICoupon coupon1;
	ICoupon coupon2;
	ICoupon coupon3;

	ShoppingCart cart;

	@Before
	public void setUp() {
		food = new Category("food");
		fruit = new Category("fruit", food);

		apple = new Product("apple", 100.0, fruit);
		banana = new Product("banana", 200, fruit);

		electronic = new Category("electronic");
		pc = new Product("pc", 1000, electronic);

		campaign1 = DiscountProducer.getCampignFactory(DiscountType.RATE, fruit, 20.0, 3);
		campaign2 = DiscountProducer.getCampignFactory(DiscountType.AMOUNT, fruit, 90.0, 3);
		campaign3 = DiscountProducer.getCampignFactory(DiscountType.AMOUNT, food, 90.0, 3);
		
		coupon1 = DiscountProducer.getCouponFactory(DiscountType.RATE, 20.0, 20.0);
		coupon2 = DiscountProducer.getCouponFactory(DiscountType.AMOUNT, 100.0, 20.0);
		coupon3 = DiscountProducer.getCouponFactory(DiscountType.AMOUNT, 100.0, 2000.0);
	}

	@Test
	public void addSingleProductToCart() {
		cart = new ShoppingCart(new DeliveryCostCalculator(1.0, 1.0));
		cart.addItem(apple, 5);

		Map<Product, Integer> itemsMap = new HashMap<Product, Integer>();
		itemsMap.put(apple, 5);

		assertEquals(itemsMap, cart.getItems());
	}

	@Test
	public void addManyProductsToCart() {
		cart = new ShoppingCart(new DeliveryCostCalculator(1.0, 1.0));
		cart.addItem(apple, 5);
		cart.addItem(banana, 15);

		Map<Product, Integer> itemsMap = new HashMap<Product, Integer>();
		itemsMap.put(apple, 5);
		itemsMap.put(banana, 15);

		assertEquals(itemsMap, cart.getItems());
	}

	@Test
	public void addSameProductWithDifferentQuantity() {
		cart = new ShoppingCart(new DeliveryCostCalculator(1.0, 1.0));
		cart.addItem(apple, 5);
		cart.addItem(apple, 15);

		Map<Product, Integer> itemsMap = new HashMap<Product, Integer>();
		itemsMap.put(apple, 20);

		assertEquals(itemsMap, cart.getItems());
	}

	@Test
	public void applyCampaigns() {
		cart = new ShoppingCart(new DeliveryCostCalculator(1.0, 1.0));
		cart.applyCampaigns(campaign1);

		assertEquals(cart.getCampaignList().get(0), campaign1);
	}

	@Test
	public void applyCoupons() {
		cart = new ShoppingCart(new DeliveryCostCalculator(1.0, 1.0));
		cart.addItem(pc, 5);
		cart.applyCoupon(coupon1);

		assertEquals(cart.getCouponList().get(0), coupon1);
	}

	@Test
	public void applyCouponWhenCartTotalIsMoreThanCoupon() {
		cart = new ShoppingCart(new DeliveryCostCalculator(1.0, 1.0));
		cart.addItem(pc, 5);
		cart.applyCoupon(coupon1);

		assertEquals(cart.getCouponList().get(0), coupon1);
	}

	@Test
	public void applyCouponWhenCartTotalIsLessThanCoupon() {
		cart = new ShoppingCart(new DeliveryCostCalculator(1.0, 1.0));
		cart.addItem(apple, 1);
		cart.applyCoupon(coupon3);

		assertEquals(cart.getCouponList(), new ArrayList<ICoupon>());
	}

	@Test
	public void applyCouponAfterCampignApplied() {
		cart = new ShoppingCart(new DeliveryCostCalculator(1.0, 1.0));
		cart.applyCampaigns(campaign1);
		cart.addItem(apple, 5);
		cart.applyCoupon(coupon2);

		assertEquals(cart.getCouponList().get(0), coupon2);
	}

	@Test
	public void checkCampaignDiscountByRate() {
		cart = new ShoppingCart(new DeliveryCostCalculator(1.0, 1.0));
		cart.addItem(apple, 4);

		cart.applyCampaigns(campaign1);

		assertEquals(cart.getCampaignDiscount(), 80.0, 0.001);
	}
	
	@Test
	public void checkCampaignDiscountByAmount() {
		cart = new ShoppingCart(new DeliveryCostCalculator(1.0, 1.0));
		cart.addItem(apple, 4);

		cart.applyCampaigns(campaign2);

		assertEquals(cart.getCampaignDiscount(), 90.0, 0.001);
	}
	
	@Test
	public void checkCouponDiscountByRate() {
		cart = new ShoppingCart(new DeliveryCostCalculator(1.0, 1.0));
		cart.addItem(apple, 4);

		cart.applyCoupon(coupon1);
		
		assertEquals(cart.getCouponDiscount(), 80.0, 0.001);
	}
	
	@Test
	public void checkCouponDiscountByAmount() {
		cart = new ShoppingCart(new DeliveryCostCalculator(1.0, 1.0));
		cart.addItem(apple, 4);

		cart.applyCoupon(coupon2);
		
		assertEquals(cart.getCouponDiscount(), 100.0, 0.001);
	}	
	
	@Test
	public void checkCampaignDiscountWhenQuantityIsNotSatisfied() {
		cart = new ShoppingCart(new DeliveryCostCalculator(1.0, 1.0));
		cart.addItem(apple, 1);

		cart.applyCampaigns(campaign2);

		assertEquals(cart.getCampaignDiscount(), 0.0, 0.001);
	}
	
	@Test
	public void checkCouponDiscountWhenTotalAmountIsNotSatisfied() {
		cart = new ShoppingCart(new DeliveryCostCalculator(1.0, 1.0));
		cart.addItem(apple, 1);

		cart.applyCoupon(coupon3);

		assertEquals(cart.getCouponDiscount(), 0.0, 0.001);
	}
	
	@Test
	public void checkCouponDiscountWhenTotalAmountIsSatisfied() {
		cart = new ShoppingCart(new DeliveryCostCalculator(1.0, 1.0));
		cart.addItem(pc, 5);

		cart.applyCoupon(coupon3);

		assertEquals(cart.getCouponDiscount(), 100.0, 0.001);
	}

	@Test
	public void checkTotalAmountAferACampaign() {
		cart = new ShoppingCart(new DeliveryCostCalculator(1.0, 1.0));
		cart.addItem(apple, 4);

		cart.applyCampaigns(campaign1);

		assertEquals(cart.getTotalAmountAfterDiscounts(), 320.0, 0.001);
	}

	@Test
	public void checkTotalAmountAferACampaignWhenAItemCountIsNotSatisfied() {
		cart = new ShoppingCart(new DeliveryCostCalculator(1.0, 1.0));
		cart.addItem(apple, 4);
		cart.addItem(banana, 1);

		cart.applyCampaigns(campaign1);

		assertEquals(cart.getTotalAmountAfterDiscounts(), 520.0, 0.001);
	}

	@Test
	public void checkTotalAmountAferACampaignWhenItemCountsAreSatisfied() {
		cart = new ShoppingCart(new DeliveryCostCalculator(1.0, 1.0));
		cart.addItem(apple, 4);
		cart.addItem(banana, 5);
		
		cart.applyCampaigns(campaign1);

		assertEquals(cart.getTotalAmountAfterDiscounts(), 1120, 0.001);
	}
	
	@Test
	public void checkTotalAmountAferACoupon() {
		cart = new ShoppingCart(new DeliveryCostCalculator(1.0, 1.0));
		cart.addItem(pc, 5);

		cart.applyCoupon(coupon3);

		assertEquals(cart.getCouponDiscount(), 100.0, 0.001);
	}
	
	@Test
	public void checkTotalAmountAferACouponWhenTotalAmountIsSatisfied() {
		cart = new ShoppingCart(new DeliveryCostCalculator(1.0, 1.0));
		cart.addItem(apple, 4);

		cart.applyCoupon(coupon2);

		assertEquals(cart.getTotalAmountAfterDiscounts(), 300.0, 0.001);
	}
	
	@Test
	public void checkTotalAmountAferACouponWhenTotalAmountIsNotSatisfied() {
		cart = new ShoppingCart(new DeliveryCostCalculator(1.0, 1.0));
		cart.addItem(apple, 4);

		cart.applyCoupon(coupon3);

		assertEquals(cart.getTotalAmountAfterDiscounts(), 400.0, 0.001);
	}
	
	@Test
	public void checkTotalAmountAfterManyCampaignsApplied() {
		cart = new ShoppingCart(new DeliveryCostCalculator(1.0, 1.0));
		cart.addItem(apple, 4);
		
		cart.applyCampaigns(campaign1, campaign2);
		//cart.applyCoupon(coupon3);

		assertEquals(cart.getTotalAmountAfterDiscounts(), 310.0, 0.001);
	}
	
	@Test
	public void checkTotalAmountAfterManyCampaignsThenCouponApplied() {
		cart = new ShoppingCart(new DeliveryCostCalculator(1.0, 1.0));
		cart.addItem(apple, 4);
		
		cart.applyCampaigns(campaign1, campaign2);
		cart.applyCoupon(coupon2);

		assertEquals(cart.getTotalAmountAfterDiscounts(), 210.0, 0.001);
	}
	
	@Test
	public void checkTotalAmountAfterCampaignForParentCategoryApplied() {
		cart = new ShoppingCart(new DeliveryCostCalculator(1.0, 1.0));
		cart.addItem(apple, 4);
		
		cart.applyCampaigns(campaign3);

		assertEquals(cart.getTotalAmountAfterDiscounts(), 310.0, 0.001);
	}
	
	@Test
	public void checkTotalAmountAfterCampaignForParentCategoryAndActualCategoryCampaignApplied() {
		cart = new ShoppingCart(new DeliveryCostCalculator(1.0, 1.0));
		cart.addItem(apple, 4);
		
		cart.applyCampaigns(campaign1);
		cart.applyCampaigns(campaign3);

		assertEquals(cart.getTotalAmountAfterDiscounts(), 310.0, 0.001);
	}
	
	@Test
	public void getNumberOfDeliveries() {
		cart = new ShoppingCart(new DeliveryCostCalculator(1.0, 1.0));
		cart.addItem(apple, 4);
		cart.addItem(banana, 1);
		cart.addItem(pc, 10);

		assertEquals(cart.getNumberOfDeliveries(), 2);
	}
	
	@Test
	public void getNumberOfProducts() {
		cart = new ShoppingCart(new DeliveryCostCalculator(1.0, 1.0));
		cart.addItem(apple, 4);
		cart.addItem(banana, 1);
		cart.addItem(pc, 10);

		assertEquals(cart.getNumberOfProducts(), 3);
	}
	
	
	@Test
	public void getDeliveryCost() {
		cart = new ShoppingCart(new DeliveryCostCalculator(1.0, 1.0));
		cart.addItem(apple, 4);
		cart.addItem(banana, 1);
		cart.addItem(pc, 10);

		assertEquals(cart.getDeliveryCost(), 7.99, 0.01);
	}
}
