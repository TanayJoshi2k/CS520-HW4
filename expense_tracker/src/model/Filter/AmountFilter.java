package model.Filter;

import java.util.ArrayList;
import java.util.List;

import model.Transaction;
import controller.InputValidation;

/**
 * A filter implementation for transactions based on a specified amount value.
 */
public class AmountFilter implements TransactionFilter{
    /**
     * The amount value to be used as a filter criterion.
     */
    private double amountFilter;

    /**
     * Constructs an AmountFilter with the specified amount value.
     *
     * @param amountFilter The amount value to be used as a filter criterion.
     * @throws IllegalArgumentException If the provided amount value is invalid.
     */
    public AmountFilter(double amountFilter){
        // Since the AmountFilter constructor is public, 
        // the input validation needs to be performed again.
        if(!InputValidation.isValidAmount(amountFilter)){
            throw new IllegalArgumentException("Invalid amount filter");
        } else {
            this.amountFilter = amountFilter;
        }
    }

    /**
     * Filters a list of transactions based on the specified amount value.
     *
     * @param transactions The list of transactions to be filtered.
     * @return A new list containing transactions that match the specified amount value.
     */
    @Override
    public List<Transaction> filter(List<Transaction> transactions){
        List<Transaction> filteredTransactions = new ArrayList<>();
        for(Transaction transaction : transactions){
            // Your solution could use a different comparison here.
            if(transaction.getAmount() == amountFilter){
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }
    
}
