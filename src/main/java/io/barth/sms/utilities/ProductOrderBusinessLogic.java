package io.barth.sms.utilities;

import io.barth.sms.exception.GeneralApplicationException;

public class ProductOrderBusinessLogic {

    static int quantity = 0;

    public static int quantityLogic(int productQuantity, int orderQuantity){

        if (orderQuantity == 0)
            throw new GeneralApplicationException("Order can not be less than 1");

        if (productQuantity == 0){
            throw new GeneralApplicationException("We have limited product for now. Come back later");

        } else if (productQuantity < orderQuantity) {
            throw new GeneralApplicationException("We have limited product for now. Please reduce the order " +
                    "quantity or come back later");
        } else {
            quantity = productQuantity - orderQuantity;
        }
        return quantity;
    }

    public static int quantityLogic(
            int productQuantity, int oldOrderQuantity, int newOrderQuantity
    ){
        if (newOrderQuantity == 0)
            throw new GeneralApplicationException("Order can not be less than 1");

        int newProductQuantity = productQuantity + oldOrderQuantity;
        if (newProductQuantity == 0){
            throw new GeneralApplicationException("We have limited product for now. Come back later");

        } else if (newProductQuantity < newOrderQuantity) {
            throw new GeneralApplicationException("We have limited product for now. Please reduce the order " +
                    "quantity or come back later");
        } else {
            quantity = newProductQuantity - newOrderQuantity;
        }
        return quantity;
    }

    public static int cancellationQuantity(int productQuantity, int orderQuantity){
        return quantity = productQuantity + orderQuantity;
    }
}
