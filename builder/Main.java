package builder;

class User {
    private final String name;
    private final String email;
    private final String phone;
    private final int age;

    private User(String name, String email, String phone, int age){
        this.name=name;
        this.email= email;
        this.phone=phone;
        this.age= age;
    }

    String getName(){
        return this.name;
    }

    public static class Builder{
        String name;
        String email;
        String phone;
        int age;

        public Builder(String name, String email){
            this.name=name;
            this.email=email;
        }

        public Builder phone(String phone){
            this.phone=phone;
            return this;
        }

        public Builder age(int age){
            this.age=age;
            return this;
        }

        public User build(){
            return new User(this.name, this.email, this.phone, this.age);
        }
    }
}

public class Main{
    public static void main(String[] args) {
        User user= new User.Builder("aditya", "aditya@gmail.com").age(10).phone("7905361332").build();
        System.out.println(user.getName());
    }
}
