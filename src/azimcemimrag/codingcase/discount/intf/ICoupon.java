package azimcemimrag.codingcase.discount.intf;
/**
 * Coupon Interface
 * @author azimcem
 *
 */
public interface ICoupon {
	public double calculateDiscount(double amount);

	public double getMinimumApplicableCartAmount();

}
