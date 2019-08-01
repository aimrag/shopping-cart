package azimcemimrag.codingcase.cart;

import java.util.ArrayList;
import java.util.List;

/**
 * Product
 * 
 * @author azimcem
 *
 */
public class Product {
	private String title;
	private double price;
	private Category category;

	public Product(String title, double price, Category category) {
		super();
		this.title = title;
		this.price = price;
		this.category = category;
	}

	public List<Category> getCategoryList() {
		List<Category> parentCategoryList = new ArrayList<Category>();
		Category currentCategory = category;
		parentCategoryList.add(currentCategory);
		while (currentCategory.hasParent()) {
			parentCategoryList.add(category.getParentCategory());
			currentCategory = currentCategory.getParentCategory();
		}
		return parentCategoryList;
	}

	public boolean isProductCategory(Category category) {
		if (getCategoryList().contains(category)) {
			return true;

		}
		return false;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
