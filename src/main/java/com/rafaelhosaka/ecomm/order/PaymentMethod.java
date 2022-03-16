package com.rafaelhosaka.ecomm.order;

public enum PaymentMethod {
    CREDIT("Credit Card"),
    DEBIT("Debit Card");

    private String text;

    PaymentMethod(final String text){
        this.text = text;
    }

    public static PaymentMethod findByName(String name){
        for(PaymentMethod v : values()){
            if( v.text.equals(name)){
                return v;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return text;
    }
}
