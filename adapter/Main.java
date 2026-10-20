package adapter;

public class Main {
    public static void main(String[] args) {

        PaymentGateway gate= new RazorpayAdapter(new RazorpaySDK());
        gate.pay(100.0);
    }
}

interface PaymentGateway{
    void pay(double amount);
}


class RazorpaySDK {
    public void makePayment(double amount) {
        System.out.println("Razorpay processing: ₹" + amount);
    }
}

class RazorpayAdapter implements PaymentGateway{
    private final RazorpaySDK razorpaySDK;
    RazorpayAdapter(RazorpaySDK razorpaySDK){
        this.razorpaySDK= razorpaySDK;
    }

    @Override 
    public void pay(double amount){
        razorpaySDK.makePayment(amount);
    }
}

class StripeSDK {
    public void chargeCard(double amount) {
        System.out.println("Stripe processing: ₹" + amount);
    }
}

class StripeAdapter implements PaymentGateway {
    private final StripeSDK stripeSDK;

    public StripeAdapter(StripeSDK stripeSDK) {
        this.stripeSDK = stripeSDK;
    }

    @Override
    public void pay(double amount) {
        stripeSDK.chargeCard(amount);
    }
}