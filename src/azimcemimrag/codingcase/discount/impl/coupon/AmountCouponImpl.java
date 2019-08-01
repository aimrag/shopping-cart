package azimcemimrag.codingcase.discount.impl.coupon;

import azimcemimrag.codingcase.discount.intf.ICoupon;

/**
 * Amount Coupon Impl
 * @author azimcem
 *
 */
public class AmountCouponImpl implements ICoupon {
	private double discount;
	private double minimumApplicableCartAmount;

	public AmountCouponImpl(double discount, double minimumApplicableCartAmount) {
		super();
		this.discount = discount;
		this.minimumApplicableCartAmount = minimumApplicableCartAmount;
	}

	@Override
	public double calculateDiscount(double amount) {
		return discount;
	}

	public double getMinimumApplicableCartAmount() {
		return minimumApplicableCartAmount;
	}
}
