package azimcemimrag.codingcase.discount.factory;

import azimcemimrag.codingcase.discount.DiscountType;
import azimcemimrag.codingcase.discount.impl.coupon.AmountCouponImpl;
import azimcemimrag.codingcase.discount.impl.coupon.RateCouponImpl;
import azimcemimrag.codingcase.discount.intf.ICoupon;

/**
 * Factroy class to create different kind of coupons
 * 
 * @author azimcem
 *
 */
public class CouponFactory extends AbstractFactory {
	private double minimumPurchaseAmount;
	private double discount;

	public CouponFactory(double discount, double minimumPurchaseAmount) {
		super();
		this.minimumPurchaseAmount = minimumPurchaseAmount;
		this.discount = discount;
	}

	@Override
	ICoupon getDiscount(DiscountType coice) {
		switch (coice) {
		case AMOUNT:
			return new AmountCouponImpl(discount, minimumPurchaseAmount);
		case RATE:
			return new RateCouponImpl(discount, minimumPurchaseAmount);
		default:
			return null;
		}
	}


}
