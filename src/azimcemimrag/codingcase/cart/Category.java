package azimcemimrag.codingcase.cart;
/**
 * Category class for a product.
 * 
 * @author azimcem
 *
 */
public class Category {
	private String title;
	private Category parentCategory;

	public Category(String title) {
		super();
		this.title = title;
	}

	public Category(String title, Category parentCategory) {
		super();
		this.title = title;
		this.parentCategory = parentCategory;
	}

	public boolean hasParent() {
		return parentCategory != null ? true : false;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}
}
