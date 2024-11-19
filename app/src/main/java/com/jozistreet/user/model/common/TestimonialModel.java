package com.jozistreet.user.model.common;

// Testimonial.java
public class TestimonialModel {
    private int imageResource;  // Resource ID for the profile image
    private String description; // Testimonial text
    private String name;        // Name of the person

//    private int rating;         // Rating out of 5 (number of stars)

    // Constructor
    public TestimonialModel(int imageResource, String description, String name) {
        this.imageResource = imageResource;
        this.description = description;
        this.name = name;
//        this.rating = rating;
    }

    // Getters
    public int getImageResource() {
        return imageResource;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

//    public int getRating() {
//        return rating;
//    }
}
