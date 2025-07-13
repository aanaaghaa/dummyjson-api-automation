package requests;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the JSON payload structure for updating product details,
 * specifically the product's price and title.
 * Used in API requests for PUT or PATCH operations.
 */
public class UpdateDetails {
	
	@JsonProperty("price")
	private int updatedPrice;
	
	@JsonProperty("title")
	private String updatedTitle;

public UpdateDetails(int updatedPrice, String updatedTitle)
{
	this.updatedPrice=updatedPrice;
	this.updatedTitle=updatedTitle;
}

public int getUpdatedPrice() {
	return updatedPrice;
}

public void setUpdatedPrice(int updatedPrice) {
	this.updatedPrice = updatedPrice;
}

public String getUpdatedTitle() {
	return updatedTitle;
}

public void setUpdatedTitle(String updatedTitle) {
	this.updatedTitle = updatedTitle;
}

	
}
