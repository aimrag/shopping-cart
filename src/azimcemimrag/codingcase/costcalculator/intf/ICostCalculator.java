package azimcemimrag.codingcase.costcalculator.intf;

import azimcemimrag.codingcase.cart.ShoppingCart;
/**
 * 
 * @author azimcem
 *
 */
public interface ICostCalculator {
	double calculateFor(ShoppingCart cart);
}
