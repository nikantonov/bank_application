package expenseTracker.app.model.transactions;

public enum CategoryEnum {

    Home("Home"),
    Food("Food"),
    Education("Education"),
    Health("Health"),
    Transport("Transport"),
    Travel("Travel"),
    Salary("Salary"),
    Dividend("Dividend"),
    Freelance("Freelance"),
    Other("Other");
    private String category;

    CategoryEnum(String category) {
        this.category = category;
    }
    public static CategoryEnum fromString(String category) {
        for (CategoryEnum b : CategoryEnum.values()) {
            if (b.category.equalsIgnoreCase(category)) {
                return b;
            }
        }
        return null;
    }
}
