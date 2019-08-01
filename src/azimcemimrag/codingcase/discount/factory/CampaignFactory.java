package azimcemimrag.codingcase.discount.factory;

import azimcemimrag.codingcase.cart.Category;
import azimcemimrag.codingcase.discount.DiscountType;
import azimcemimrag.codingcase.discount.impl.camapign.AmountCampaignImpl;
import azimcemimrag.codingcase.discount.impl.camapign.RateCampaignImpl;
import azimcemimrag.codingcase.discount.intf.ICampaign;

/**
 * Factroy class to create different kind of campaigns
 * 
 * @author azimcem
 *
 */
public class CampaignFactory extends AbstractFactory {
	private Category category;
	private int minimumApplicableQuantity;
	private double discount;

	public CampaignFactory(Category category, double discount, int minimumApplicableQuantity) {
		super();
		this.category = category;
		this.minimumApplicableQuantity = minimumApplicableQuantity;
		this.discount = discount;
	}

	@Override
	ICampaign getDiscount(DiscountType type) {
		switch (type) {
		case AMOUNT:
			return new AmountCampaignImpl(category, discount, minimumApplicableQuantity);
		case RATE:
			return new RateCampaignImpl(category, discount, minimumApplicableQuantity);
		default:
			return null;
		}
	}

}
