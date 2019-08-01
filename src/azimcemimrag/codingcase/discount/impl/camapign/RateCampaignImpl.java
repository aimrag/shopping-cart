package azimcemimrag.codingcase.discount.impl.camapign;

import azimcemimrag.codingcase.cart.Category;
import azimcemimrag.codingcase.discount.intf.ICampaign;

/**
 * Rate Campaign Implementation
 * @author azimcem
 *
 */
public class RateCampaignImpl implements ICampaign {
	private Category category;
	private double discount;
	private int minimumApplicableQuantity;

	public RateCampaignImpl(Category category, double discount, int minimumApplicableQuantity) {
		super();
		this.category = category;
		this.discount = discount;
		this.minimumApplicableQuantity = minimumApplicableQuantity;
	}

	@Override
	public double calculateDiscount(double amount) {
		return discount*amount*0.01;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public int getMinimumApplicableQuantity() {
		return minimumApplicableQuantity;
	}
}
