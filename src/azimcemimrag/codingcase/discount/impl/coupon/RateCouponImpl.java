package azimcemimrag.codingcase.discount.impl.coupon;

import azimcemimrag.codingcase.discount.intf.ICoupon;

/**
 * Rate Coupon Impl
 * @author azimcem
 *
 */
public class RateCouponImpl implements ICoupon {
	private double discount;
	private double minimumApplicableCartAmount;

	public RateCouponImpl(double discount, double minimumApplicableCartAmount) {
		super();
		this.discount = discount;
		this.minimumApplicableCartAmount = minimumApplicableCartAmount;
	}

	@Override
	public double calculateDiscount(double amount) {
		return discount * amount * 0.01;
	}

	public double getMinimumApplicableCartAmount() {
		return minimumApplicableCartAmount;
	}
}
