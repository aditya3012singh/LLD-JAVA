package template;

public class Main {
    public static void main(String[] args) {
        ReportGenerator generator= new CsvReportGenerator();
        generator.generateReport();
    }
}

abstract class ReportGenerator {

    public final void generateReport() {
        fetchData();
        processData();
        formatReport();
        saveReport();
    }

    private void fetchData() {
        System.out.println("Fetching data");
    }

    private void processData() {
        System.out.println("Processing data");
    }

    protected abstract void formatReport();

    protected abstract void saveReport();
}

class CsvReportGenerator extends ReportGenerator{
    public void formatReport(){
        System.out.println("Formatting as csv");
    }

    public void saveReport(){
        System.out.println("Saving csv file ");
    }
}

class PdfReportGenerator extends ReportGenerator{
    public void formatReport(){
        System.out.println("Formatting as Pdf");
    }

    public void saveReport(){
        System.out.println("Saving Pdf file ");
    }
}