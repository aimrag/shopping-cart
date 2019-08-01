package azimcemimrag.codingcase.discount.impl.camapign;

import azimcemimrag.codingcase.cart.Category;
import azimcemimrag.codingcase.discount.intf.ICampaign;
/**
 * Amount Campaign Implementation
 * @author azimcem
 *
 */
public class AmountCampaignImpl implements ICampaign {
	private Category category;
	private double discount;
	private int minimumApplicableQuantity;

	public AmountCampaignImpl(Category category, double discount, int minimumApplicableQuantity) {
		super();
		this.category = category;
		this.discount = discount;
		this.minimumApplicableQuantity = minimumApplicableQuantity;
	}

	@Override
	public double calculateDiscount(double amount) {
		return discount;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public int getMinimumApplicableQuantity() {
		return minimumApplicableQuantity;
	}
}
