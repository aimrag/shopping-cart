package azimcemimrag.codingcase.app;

import azimcemimrag.codingcase.cart.Category;
import azimcemimrag.codingcase.cart.Product;
import azimcemimrag.codingcase.cart.ShoppingCart;
import azimcemimrag.codingcase.costcalculator.impl.DeliveryCostCalculator;
import azimcemimrag.codingcase.discount.DiscountType;
import azimcemimrag.codingcase.discount.factory.DiscountProducer;
import azimcemimrag.codingcase.discount.intf.ICampaign;
import azimcemimrag.codingcase.discount.intf.ICoupon;

/**
 * Main apllication of the shopping cart.
 * @author azimcem
 *
 */
public class Main {
	public static void main(String[] args) {
		Category food = new Category("food");
		Category fruit = new Category("fruit", food);

		Product apple = new Product("apple", 100.0, fruit);
		Product banana = new Product("banana", 200, fruit);

		Category electronic = new Category("electronic");
		Product pc = new Product("pc", 1000, electronic);

		ShoppingCart cart = new ShoppingCart(new DeliveryCostCalculator(3, 4));
		cart.addItem(apple, 4);
		cart.addItem(banana, 2);
		cart.addItem(pc, 2);

		ICampaign campaign1 = DiscountProducer.getCampignFactory(DiscountType.RATE, food, 20.0, 3);
		ICampaign campaign2 = DiscountProducer.getCampignFactory(DiscountType.AMOUNT, food, 90.0, 3);		
	
		cart.applyCampaigns(campaign1);
		cart.applyCampaigns(campaign2);

		ICoupon coupon1 = DiscountProducer.getCouponFactory(DiscountType.AMOUNT, 200.0, 2000.0);
		ICoupon coupon2 = DiscountProducer.getCouponFactory(DiscountType.AMOUNT, 200.0, 2000.0);

		cart.applyCoupon(coupon1);
		cart.applyCoupon(coupon2);
		cart.print();
	}
}
