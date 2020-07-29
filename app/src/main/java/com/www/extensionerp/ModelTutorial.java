package com.www.extensionerp;

public class ModelTutorial {
    String Product_id,Product_name,Category,Quantity,Amount;

    public ModelTutorial() {

    }

    public ModelTutorial(String product_id, String product_name, String category, String quantity, String amount) {
        Product_id = product_id;
        Product_name = product_name;
        Category = category;
        Quantity = quantity;
        Amount = amount;
    }

    public String getProduct_id() {
        return Product_id;
    }

    public void setProduct_id(String product_id) {
        Product_id = product_id;
    }

    public String getProduct_name() {
        return Product_name;
    }

    public void setProduct_name(String product_name) {
        Product_name = product_name;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
