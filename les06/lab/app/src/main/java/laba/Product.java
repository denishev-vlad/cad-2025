package laba;

public class Product {
    private final int id;
    private final String name;
    private final String description;
    private final int categoryId;
    private final double price;
    private final int stockQuantity;

    public Product(int id, String name, String description, int categoryId, double price, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
    
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getCategoryId() { return categoryId; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }
}