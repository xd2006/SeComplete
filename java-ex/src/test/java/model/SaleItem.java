package model;

/**
 * Created by Alex on 27.11.2016.
 */
public class SaleItem {

    private String name;
    private String manufacturer;
    private String regularPrice;
    private String campaignPrice;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SaleItem saleItem = (SaleItem) o;

        if (name != null ? !name.equals(saleItem.name) : saleItem.name != null) return false;
        if (manufacturer != null ? !manufacturer.equals(saleItem.manufacturer) : saleItem.manufacturer != null)
            return false;
        if (regularPrice != null ? !regularPrice.equals(saleItem.regularPrice) : saleItem.regularPrice != null)
            return false;
        return campaignPrice != null ? campaignPrice.equals(saleItem.campaignPrice) : saleItem.campaignPrice == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (manufacturer != null ? manufacturer.hashCode() : 0);
        result = 31 * result + (regularPrice != null ? regularPrice.hashCode() : 0);
        result = 31 * result + (campaignPrice != null ? campaignPrice.hashCode() : 0);
        return result;
    }

    public String getManufacturer() {
        return manufacturer;

    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(String regularPrice) {
        this.regularPrice = regularPrice;
    }

    public String getCampaignPrice() {
        return campaignPrice;
    }

    public void setCampaignPrice(String campaignPrice) {
        this.campaignPrice = campaignPrice;
    }
}
