package azimcemimrag.codingcase.discount.factory;

import azimcemimrag.codingcase.cart.Category;
import azimcemimrag.codingcase.discount.DiscountType;
import azimcemimrag.codingcase.discount.intf.ICampaign;
import azimcemimrag.codingcase.discount.intf.ICoupon;

/**
 * Producer class for discounts
 * @author azimcem
 *
 */
public class DiscountProducer {
	public static ICampaign getCampignFactory(DiscountType type, Category category, double discount, int minimumApplicableQuantity) {
		return new CampaignFactory(category, discount, minimumApplicableQuantity).getDiscount(type);
	}

	public static ICoupon getCouponFactory(DiscountType type, double discount, double minimumApplicableCartAmount) {
		return new CouponFactory(discount, minimumApplicableCartAmount).getDiscount(type);

	}
}
