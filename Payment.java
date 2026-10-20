abstract class Payment {
    protected double amount;
    Payment(double amount){
        this.amount = amount;
    }
    abstract void processPayment();
    void validateAmount(){
        if(amount <= 0){
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
    }
    void printReceipt(){
        System.out.println("Payment of $" + amount + " has been processed.");
    }
}

class CreditCardPayment extends Payment{
    
    CreditCardPayment(double amount){
        super(amount);
        this.amount = amount;
    }
    @Override
    public void processPayment(){
        System.out.println("Processing Credit Card payment of $" + amount);
    }
}

class UpiPayment extends Payment{
    
    UpiPayment(double amount){
        super(amount);
        this.amount = amount;
    }
    @Override
    public void processPayment(){
        System.out.println("Processing UPI payment of $" + amount);
    }
}

class Main{
    public static void main(String[] args){
        Payment payment1 = new CreditCardPayment(100.0);
        payment1.validateAmount();
        payment1.processPayment();
        payment1.printReceipt();

        Payment payment2 = new UpiPayment(200.0);
        payment2.validateAmount();
        payment2.processPayment();
        payment2.printReceipt();
    }
}