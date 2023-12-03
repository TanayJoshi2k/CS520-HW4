package model.Filter;

import java.util.ArrayList;
import java.util.List;

import model.Transaction;
import controller.InputValidation;

/**
 * A filter implementation for transactions based on a specified category.
 */
public class CategoryFilter implements TransactionFilter {

    /**
     * The category to be used as a filter criterion.
     */
    private String categoryFilter;

    /**
     * Constructs a CategoryFilter with the specified category.
     *
     * @param categoryFilter The category to be used as a filter criterion.
     * @throws IllegalArgumentException If the provided category is invalid.
     */
    public CategoryFilter(String categoryFilter) {
        // Since the CategoryFilter constructor is public,
        // the input validation needs to be performed again.
        if (!InputValidation.isValidCategory(categoryFilter)) {
            throw new IllegalArgumentException("Invalid category filter");
        } else {
            this.categoryFilter = categoryFilter;
        }
    }

    /**
     * Filters a list of transactions based on the specified category.
     *
     * @param transactions The list of transactions to be filtered.
     * @return A new list containing transactions that match the specified category.
     */
    @Override
    public List<Transaction> filter(List<Transaction> transactions) {

        List<Transaction> filteredTransactions = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (transaction.getCategory().equalsIgnoreCase(categoryFilter)) {
                filteredTransactions.add(transaction);
            }
        }

        return filteredTransactions;
    }
}
