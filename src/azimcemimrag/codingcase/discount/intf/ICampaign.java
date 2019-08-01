package azimcemimrag.codingcase.discount.intf;

import azimcemimrag.codingcase.cart.Category;
/**
 * Campaign Interface
 * @author azimcem
 *
 */
public interface ICampaign {
	public double calculateDiscount(double amount);

	public Category getCategory();
	
	public int getMinimumApplicableQuantity();

}
