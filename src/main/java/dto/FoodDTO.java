package dto;

import model.FoodDAO;

public class FoodDTO {

	private int id;
	private String nameFood;
	private Double priceFood;
	private int inventoryFood;
	private int category_id;
	private String image;
	private String description;
	
	public FoodDTO() {
		
	}
	
	public FoodDTO(int category_id, String image, String description, FoodDAO entity) {
		this.category_id = category_id;
		this.image = image;
		this.description = description;
	}
	
}
