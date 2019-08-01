package azimcemimrag.codingcase.costcalculator.impl;

import azimcemimrag.codingcase.cart.ShoppingCart;
import azimcemimrag.codingcase.costcalculator.CostConstant;
import azimcemimrag.codingcase.costcalculator.intf.ICostCalculator;

/**
 * DeliveryCostCalculator
 * @author azimcem
 *
 */
public class DeliveryCostCalculator implements ICostCalculator {
	private double costPerDelivery;
	private double costPerProduct;

	public DeliveryCostCalculator(double costPerDelivery, double costPerProduct) {
		super();
		this.costPerDelivery = costPerDelivery;
		this.costPerProduct = costPerProduct;
	}

	@Override
	public double calculateFor(ShoppingCart cart) {
		return Math.ceil((costPerDelivery * cart.getNumberOfDeliveries()) + (costPerProduct * cart.getNumberOfProducts())
				+ CostConstant.FIXED_DELIVERY_COST);
	}

	public double getCostPerDelivery() {
		return costPerDelivery;
	}

	public void setCostPerDelivery(double costPerDelivery) {
		this.costPerDelivery = costPerDelivery;
	}

	public double getCostPerProduct() {
		return costPerProduct;
	}

	public void setCostPerProduct(double costPerProduct) {
		this.costPerProduct = costPerProduct;
	}
}
