package requests;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the payload structure for creating or updating a product
 * in the DummyJSON API. This class is used for serializing and deserializing
 * product-related fields in API requests and responses.
 */
public class NewProductDetails {

	@JsonProperty("id")
	private int id;
	@JsonProperty("title")
	private String title;
	@JsonProperty("price")
	private int price;
	@JsonProperty("rating")
	private int rating;
	@JsonProperty("stock")
	private int stock;
	@JsonProperty("brand")
	private String brand;
	@JsonProperty("category")
	private String category;
	
	//Constructor to initialize all fields of the product.
	public NewProductDetails(int id, String title, int price, int rating, int stock, String brand, String category) {
		super();
		this.id = id;
		this.title = title;
		this.price = price;
		this.rating = rating;
		this.stock = stock;
		this.brand = brand;
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	

}
