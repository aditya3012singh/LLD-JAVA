interface Viewable {
    void view();
}

interface Printable{
    void print();
}

interface Exportable{
    void export();
}

class RegularDocument implements Viewable, Printable, Exportable{
    @Override
    public void view() {
        System.out.println("Viewing Regular Document");
    }

    @Override
    public void print() {
        System.out.println("Printing Regular Document");
    }

    @Override
    public void export() {
        System.out.println("Exporting Regular Document");
    }
}

class ConfidentialDocument implements Viewable{
    @Override 
    public void view(){
        System.out.println("Viewing confidential document");
    }
}

class Main{
    public static void main(String[] args) {
        RegularDocument doc= new RegularDocument();
        doc.view();
        doc.print();
        doc.export();

        ConfidentialDocument confidentialDoc = new ConfidentialDocument();
        confidentialDoc.view();
    }
}