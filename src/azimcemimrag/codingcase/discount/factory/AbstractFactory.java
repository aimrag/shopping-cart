package azimcemimrag.codingcase.discount.factory;

import azimcemimrag.codingcase.discount.DiscountType;

/**
 * AbstractFactory Class
 * 
 * @author azimcem
 *
 */
public abstract class AbstractFactory<T> {
	 abstract T getDiscount(DiscountType coice);
}
