package com.example.helth_frag;

public class ModelInventoryItem {


    String itemName,itemDescription,quantity, addedBy,key;
    Boolean isEditing;
    public ModelInventoryItem(String key,String itemName,String itemDescription,String quantity,Boolean isEditing,String addedBy) {
        this.key = key;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.quantity = quantity;
        this.isEditing = isEditing;
        this.addedBy = addedBy;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ModelInventoryItem(){

    }
    public String getItemName() {
        return itemName;
    }

    public Boolean getEditing() {
        return isEditing;
    }

    public void setEditing(Boolean editing) {
        isEditing = editing;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
