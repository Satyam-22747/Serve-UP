package com.satdroid.serveup.DonorPanel;

public class FoodItemDetails
{
    public String Foods,Quantity, Description, ImageUrl, RandomUid, DonorId;

    public FoodItemDetails(String foods, String quantity, String description, String imageUrl, String randomUid, String donorId) {
        Foods = foods;
        Quantity = quantity;
        Description = description;
        ImageUrl = imageUrl;
        RandomUid = randomUid;
        DonorId = donorId;
    }
}
