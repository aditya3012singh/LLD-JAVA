import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class Book{
    private final String isbn;
    private final String title;
    private final String author;

    Book(String isbn, String title, String author){
        if(isbn == null || isbn.isEmpty()){
            throw new IllegalArgumentException("Error: ISBN cannot be null or empty.");
        }
        if(title == null || title.isEmpty()){
            throw new IllegalArgumentException("Error: Title cannot be null or empty.");
        }
        if(author == null || author.isEmpty()){
            throw new IllegalArgumentException("Error: Author cannot be null or empty.");
        }
        this.isbn= isbn;
        this.title= title;
        this.author= author;
    }

    String getIsbn(){
        return isbn;
    }

    String getTitle(){
        return title;
    }

    String getAuthor(){
        return author;
    }
}

class BookCopy{
    private final String copyId;
    private final Book book;
    private BorrowingRecord borrowingRecord;

    BookCopy(String copyId, Book book){
        if(copyId == null || copyId.isEmpty()){
            throw new IllegalArgumentException("Error: Copy ID cannot be null or empty.");
        }
        if(book == null){
            throw new IllegalArgumentException("Error: Book cannot be null.");
        }
        this.copyId= copyId;
        this.book= book;
        this.borrowingRecord= null;
    }

    boolean isAvailable(){
        return borrowingRecord == null;
    }

    Book getBook(){
        return book;
    }

    String getCopyId(){
        return copyId;
    }

    void assignBorrowingRecord(BorrowingRecord borrowingRecord){
        if(borrowingRecord == null){
            throw new IllegalArgumentException("Error: Borrowing record cannot be null.");
        }
        if(!isAvailable()){
            throw new IllegalStateException("Error: Book copy is already borrowed.");
        }
        this.borrowingRecord= borrowingRecord;
    }

    void clearBorrowingRecord(String recordId){
        if(recordId == null || recordId.isEmpty()){
            throw new IllegalArgumentException("Error: Record ID cannot be null or empty.");
        }
        if(this.borrowingRecord != null && this.borrowingRecord.getRecordId().equals(recordId)){
            this.borrowingRecord= null;
        }
    }
}

class BorrowingRecord{
    private final String recordId;
    private final BookCopy bookCopy;
    private final Member member;
    private final LocalDate borrowDate;
    private LocalDate returnDate;

    BorrowingRecord(String recordId, BookCopy bookCopy, Member member, LocalDate borrowDate){
        this.recordId= recordId;
        this.bookCopy= bookCopy;
        this.member= member;
        this.borrowDate= borrowDate;
        this.returnDate= null;
    }

    void returnBook(LocalDate returnDate){
        if(returnDate == null){
            throw new IllegalArgumentException("Error: Return date cannot be null.");
        }
        if(returnDate.isBefore(borrowDate)){
            throw new IllegalArgumentException("Error: Return date cannot be before borrow date.");
        } 
        if(isReturned()){
            throw new IllegalStateException("Error: Book has already been returned.");
        }
        this.returnDate= returnDate;
    }

    boolean isReturned(){
        return returnDate != null;
    }

    String getRecordId(){
        return recordId;
    }

    BookCopy getBookCopy(){
        return bookCopy;
    }

    Member getMember(){
        return member;
    }

    LocalDate getBorrowDate(){
        return borrowDate;
    }

    LocalDate getReturnDate(){
        return returnDate;
    }

    // BorrowingRecord findBorrowingRecord(String recordId){
    //     if(recordId == null || recordId.isEmpty()){
    //         throw new IllegalArgumentException("Error: Record ID cannot be null or empty.");
    //     }
    //     if(this.recordId.equals(recordId)){
    //         return this;
    //     }
    //     return null;
    // }
}

class Member{
    private final String memberId;
    private final String name;
    private final int maxBooksAllowed;

    Member(String memberId, String name, int maxBooksAllowed){
        if(memberId == null || memberId.isEmpty()){
            throw new IllegalArgumentException("Error: Member ID cannot be null or empty.");
        }
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Error: Member name cannot be null or empty.");
        }
        if(maxBooksAllowed <= 0){
            throw new IllegalArgumentException("Error: Max books allowed must be greater than zero.");
        }
        this.memberId= memberId;
        this.name= name;
        this.maxBooksAllowed= maxBooksAllowed;
    }

    String getMemberId(){
        return memberId;
    }

    String getName(){
        return name;
    }

    int getMaxBooksAllowed(){
        return maxBooksAllowed;
    }

    boolean canBorrow(List<BorrowingRecord> records){
        if(records == null){
            throw new IllegalArgumentException("Error: Records list cannot be null.");
        }
        int count= 0;
        for(BorrowingRecord record: records){
            if(record == null){
                throw new IllegalArgumentException("Error: Borrowing record cannot be null.");
            }
            if(!record.isReturned() && record.getMember().getMemberId().equals(memberId)){
                count++;
            }
        }
        return count < maxBooksAllowed;
    }
}


class Library{
    private final List<Member> members;
    private final List<BorrowingRecord> borrowingRecords;
    private final List<BookCopy> bookCopies;

    Library(List<Member> members, List<BorrowingRecord> borrowingRecords, List<BookCopy> bookCopies){
        if(members == null){
            throw new IllegalArgumentException("Error: Members list cannot be null.");
        }
        if(borrowingRecords == null){
            throw new IllegalArgumentException("Error: Borrowing records list cannot be null.");
        }
        if(bookCopies == null){
            throw new IllegalArgumentException("Error: Book copies list cannot be null.");
        }

        this.members= new ArrayList<>(members);
        this.borrowingRecords= new ArrayList<>(borrowingRecords);
        this.bookCopies= new ArrayList<>(bookCopies);
    }

    BorrowingRecord borrowBook(String memberId, String copyId, LocalDate borrowDate){
        Member member = findMember(memberId);
        if(member == null){
            throw new IllegalArgumentException("Error: Member is not registered in the library.");
        }
        BookCopy bookCopy = findBookCopy(copyId);
        if(bookCopy == null){
            throw new IllegalArgumentException("Error: Book copy is not available in the library.");
        }
        if(borrowDate == null){
            throw new IllegalArgumentException("Error: Borrow date cannot be null.");
        }
        if(!bookCopy.isAvailable()){
            throw new IllegalStateException("Error: Book copy is not available for borrowing.");
        }
        if(!member.canBorrow(borrowingRecords)){
            throw new IllegalStateException("Error: Member has reached the maximum number of borrowed books.");
        }
        if(borrowDate.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("Error: Borrow date cannot be in the future.");
        }

        String recordId= "R" + (borrowingRecords.size() + 1);
        BorrowingRecord record= new BorrowingRecord(recordId, bookCopy, member, borrowDate);
        bookCopy.assignBorrowingRecord(record);
        borrowingRecords.add(record);
        return record;
    }

    private BorrowingRecord findBorrowingRecord(String recordId){
        if(recordId == null || recordId.isEmpty()){
            throw new IllegalArgumentException("Error: Record ID cannot be null or empty.");
        }
        for(BorrowingRecord record: borrowingRecords){
            if(record.getRecordId().equals(recordId)){
                return record;
            }
        }
        return null;
    }

    void returnBook(String recordId, LocalDate returnDate){
        BorrowingRecord record= findBorrowingRecord(recordId);
        if(record == null){
            throw new IllegalArgumentException("Error: Borrowing record is not found.");
        }
        if(returnDate == null){
            throw new IllegalArgumentException("Error: Return date cannot be null.");
        }
        if(returnDate.isBefore(record.getBorrowDate())){
            throw new IllegalArgumentException("Error: Return date cannot be before borrow date.");
        }
        if(returnDate.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("Error: Return date cannot be in the future.");
        }
        record.returnBook(returnDate);
        record.getBookCopy().clearBorrowingRecord(recordId);
    }

    private Member findMember(String memberId){
        if(memberId == null || memberId.isEmpty()){
            throw new IllegalArgumentException("Error: Member ID cannot be null or empty.");
        }
        for(Member member: members){
            if(member.getMemberId().equals(memberId)){
                return member;
            }
        }
        return null;
    }

    private BookCopy findBookCopy(String copyId){
        if(copyId == null || copyId.isEmpty()){
            throw new IllegalArgumentException("Error: Copy ID cannot be null or empty.");
        }
        for(BookCopy copy: bookCopies){
            if(copy.getCopyId().equals(copyId)){
                return copy;
            }
        }
        return null;
    }

    List<BookCopy> searchByTitle(String title){
        if(title == null || title.isEmpty()){
            throw new IllegalArgumentException("Error: Title cannot be null or empty.");
        }
        title=title.trim();
        if(title.isEmpty()){
            throw new IllegalArgumentException("Error: Title cannot be empty after trimming.");
        }
        List<BookCopy> result= new ArrayList<>();
        for(BookCopy copy: bookCopies){
            if(copy.getBook().getTitle().equalsIgnoreCase(title)){
                result.add(copy);
            }
        }
        return result;
    }

    List<BookCopy> searchByAuthor(String author){
        if(author == null || author.isEmpty()){
            throw new IllegalArgumentException("Error: Author cannot be null or empty.");
        }
        author.trim();
        if(author.isEmpty()){
            throw new IllegalArgumentException("Error: Author cannot be empty after trimming.");
        }
        List<BookCopy> result= new ArrayList<>();
        for(BookCopy copy: bookCopies){
            if(copy.getBook().getAuthor().equalsIgnoreCase(author)){
                result.add(copy);
            }
        }
        return result;
    }

    List<BookCopy> searchByIsbn(String isbn){
        if(isbn == null || isbn.isEmpty()){
            throw new IllegalArgumentException("Error: ISBN cannot be null or empty.");
        }
        isbn=isbn.trim();
        if(isbn.isEmpty()){
            throw new IllegalArgumentException("Error: ISBN cannot be empty after trimming.");
        }
        List<BookCopy> result= new ArrayList<>();
        for(BookCopy copy: bookCopies){
            if(copy.getBook().getIsbn().equals(isbn)){
                result.add(copy);
            }
        }
        return result;
    }
}