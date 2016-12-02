package model;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Alex on 02.12.2016.
 */
public class Product {

    private General generalTabData;
    private Information informationTabData;
    private Prices pricesTabData;


    public class General {
        private boolean enabled;
        private String name;
        private String code;

        public boolean isEnabled() {
            return enabled;
        }

        public General withEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public String getName() {
            return name;
        }

        public General withName(String name) {
            this.name = name;
            return this;
        }

        public String getCode() {
            return code;
        }

        public General withCode(String code) {
            this.code = code;
            return this;
        }

        public ArrayList<String> getCategories() {
            return categories;
        }

        public General withCategories(ArrayList<String> categories) {
            this.categories = categories;
            return this;
        }

        public String getDefaultCategory() {
            return defaultCategory;
        }

        public General withDefaultCategory(String defaultCategory) {
            this.defaultCategory = defaultCategory;
            return this;
        }

        public ArrayList<String> getProductGroups() {
            return productGroups;
        }

        public General withProductGroups(ArrayList<String> productGroups) {
            this.productGroups = productGroups;
            return this;
        }

        public int getQuantity() {
            return quantity;
        }

        public General withQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public String getSoldOutStatus() {
            return soldOutStatus;
        }

        public General withSoldOutStatus(String soldOutStatus) {
            this.soldOutStatus = soldOutStatus;
            return this;
        }

        public File getImage() {
            return image;
        }

        public General withImage(File image) {
            this.image = image;
            return this;
        }

        public String getDateValidFrom() {
            return dateValidFrom;
        }

        public General withDateValidFrom(String dateValidFrom) {
            this.dateValidFrom = dateValidFrom;
            return this;
        }

        public String getDateValidTo() {
            return dateValidTo;
        }

        public General withDateValidTo(String dateValidTo) {
            this.dateValidTo = dateValidTo;
            return this;
        }

        private ArrayList<String> categories;
        private String defaultCategory;
        private ArrayList<String> productGroups;
        private int quantity;
        private String soldOutStatus;
        private File image;
        private String dateValidFrom;
        private String dateValidTo;


    }

    public class Information {

    }

    public class Prices {

    }

    public General getGeneralTabData() {
        return generalTabData;
    }

    public Product withGeneralTabData(General generalTabData) {
        this.generalTabData = generalTabData;
        return this;
    }

    public Information getInformationTabData() {
        return informationTabData;
    }

    public Product withInformationTabData(Information informationTabData) {
        this.informationTabData = informationTabData;
        return this;
    }

    public Prices getPricesTabData() {
        return pricesTabData;
    }

    public Product withPricesTabData(Prices pricesTabData) {
        this.pricesTabData = pricesTabData;
        return this;
    }


}

