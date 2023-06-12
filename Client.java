import java.util.ArrayList;
//Subclass of trader, and completes all the abstract methods.
//has an account, used for balance, an arraylist of Stock which holds their investments.
public class Client extends Trader implements resetBalance
{
    private Account account;
    private ArrayList<Stock> investments;
    private int arraySize;

    public Client(String name)
    {
        super(name);
        this.arraySize=0;
        this.account = new Account();
        this.investments = new ArrayList<Stock>(arraySize);
    }

    public Account getAccount()
    {
        return account;
    }

    //works as a loop to get user information, including their name, balance, net-worth and all investments
    public String getUserInfo(Client c)
    {
        double allPrices=0.00;

        //used to calculate their net-worth.
        for(int i=0; i<investments.size(); i++)
        {
            Stock currentStock = investments.get(i);
            double currentTotal = currentStock.getStockPrice()*currentStock.getStockOwned();
            allPrices = allPrices + currentTotal;
        }

        double netWorth = account.getBalance()+allPrices;

        String text = "";
        text += "Name: " + c.getUsername()  + "\n";
        text += "Balance: £" + account.getBalance() + "\n";
        text += "Net worth: £" + String.format("%.2f",netWorth) + "\n";
        text = text + "All investments: " + "\n" + c.allInvestments()  + "\n";

        return text;
    }

    //prints the users investments using the arraylist.
    //has asset name, amount they own and their total asset value.
    public String allInvestments()
    {

        String text = "";

        for(int i=0; i<investments.size(); i++)
        {
            Stock currentStock = investments.get(i);


            text += "\n";
            text += "Asset Name: " + currentStock.getStockName() + "\n";
            text += "Amount Owned: " + currentStock.getStockOwned() + "\n";
            text += "Total Asset Value: £" + String.format("%.2f",currentStock.getStockPrice()*currentStock.getStockOwned())+ "\n";
        }

        return text;
    }

    //checks if the asset is in their investment.
    public boolean contains(Stock stock)
    {
        for(int i=0; i<investments.size(); i++)
        {
            Stock currentItem = investments.get(i);

            if(currentItem.equals(stock))
            {
                return true;
            }
        }
        return false;
    }

    //Allows the user to buy stocks.
    public String buyStocks(Stock stock, int amount) throws Exception
    {
        String text="";

        //if users balance is more than the price of the asset amount they want, it will continue to the next if statement, otherwise it will show an error.
        if(account.getBalance() > stock.getStockPrice()*amount)
        {
            //checks if stock is in their investments list or not.
            if(!contains(stock))
            {
                //will remove the amount from the balance.
                //adds the amount to their asset owned.
                //adds it to the investment's arraylist.
                account.withdraw(stock.getStockPrice() * amount);
                stock.addStockOwned(amount);
                investments.add(stock);
                return text += "Transaction was a success!";
            }
            //if asset is already in the arraylist, it will not add it again, only increase the amount owned.
            else if(contains(stock))
            {
                account.withdraw(stock.getStockPrice()*amount);
                stock.addStockOwned(amount);
                return text += "Transaction was a success!";
            }
        }
        //if balance is too low, it will print an error.
        else if (account.getBalance() < stock.getStockPrice()*amount)
        {
            throw new Exception("Balance: "+ account.getBalance() + "\n" + "Value of asset(s) wanted: " + stock.getStockPrice()*amount + "\n" + "Transaction could not be completed!" + "\n");
        }

        return "";
    }

    //allows the user to sell their assets.
    public void sellStocks(Stock stock, int amount) throws Exception
    {
        //if the amount they own is more than they want to sell, it will deposit the value to users account, and remove the amount to the amount they own.
        if(stock.getStockOwned()>amount)
        {
                System.out.println("Sell Price: " + stock.getStockPrice()*amount);
                account.deposit(stock.getStockPrice()*amount);
                stock.lessStockOwned(amount);
        }
        //if the amount they own is less than the amount, an error message will occur.
        else if(stock.getStockOwned()<amount)
        {
            throw new Exception("Stock Owned: " + stock.getStockOwned() + "\n" + "Stock Selling: " + amount + "\n" + "Transaction could not be completed!");
        }
        //if the amount they want to sell is the same as they own, it will make stock owned to 0.
        //deposit the value to the users account.
        //removes the investment from their arraylist so that it won't get displayed anymore.
        else if(stock.getStockOwned()==amount)
        {
            System.out.println("Sell Price: " + stock.getStockPrice()*amount);
            account.deposit(stock.getStockPrice()*amount);
            stock.lessStockOwned(amount);
            investments.remove(stock);
        }
    }

    //resets the users investment
    public void resetInvestment()
    {
        for(int i = 0; i < investments.size(); i++)
        {
            investments.get(i).setStockOwned(0);
        }
        this.investments = new ArrayList<Stock>(arraySize);
    }

    //resets user balance back to 5000
    public void balanceReset()
    {
        account.setBalance(5000.00);
    }

}
