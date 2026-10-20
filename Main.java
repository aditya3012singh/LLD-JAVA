interface PaymentMethod {
    void pay(double amount);
}

interface Refundable {
    void refund(double amount);
}

class CreditCardPayment implements PaymentMethod, Refundable {
    @Override
    public void pay(double amount) {
        System.out.println("Processing Credit Card payment of $" + amount);
    }

    @Override
    public void refund(double amount) {
        System.out.println("Refunding Credit Card payment of $" + amount);
    }
}

class GiftCardPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.println("Processing Gift Card payment of $" + amount);
    }
}

public class Main{
    public static void main(String[] args) {
        PaymentMethod creditCardPayment = new CreditCardPayment();
        creditCardPayment.pay(100.0);

        Refundable refundablePayment = (Refundable) creditCardPayment;
        refundablePayment.refund(50.0);

        PaymentMethod giftCardPayment = new GiftCardPayment();
        giftCardPayment.pay(30.0);
    }
}