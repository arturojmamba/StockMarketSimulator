import java.util.ArrayList;

//parent class to create abstraction, for client subclass.
public abstract class Trader extends Name
{
    private Account account;
    private ArrayList<Stock> investments;
    private int arraySize;

    //constructor
    public Trader(String name)
    {
        super(name);
        this.arraySize=0;
        this.account = new Account();
        this.investments = new ArrayList<Stock>(arraySize);
    }

    //gets created account
    public abstract Account getAccount();

    //will be used to print out their investments, using an arrayList.
    public abstract String allInvestments();

    //allows us to check if the asset is in their investments.
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

    //will make a new arraylist if user wants to reset account.
    public void resetInvestment()
    {
        for(int i = 0; i < investments.size(); i++)
        {
            investments.get(i).setStockOwned(0);
        }
        this.investments = new ArrayList<Stock>(arraySize);
    }
}
