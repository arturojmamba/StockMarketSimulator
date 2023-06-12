import java.util.*;
import java.util.TimerTask;
import java.util.Timer;
import java.util.Random;

//will be used to hold the available stock to be bought.
public class StockArrayList
{
    private ArrayList<Stock> stocks;
    private int arraySize;

    public StockArrayList()
    {
        this.arraySize=0;
        this.stocks = new ArrayList<Stock>(arraySize);
    }

    //checks if that asset is in the arraylist, using a loop.
    public Stock containsID(int ID)
    {
        for(int i=0; i<stocks.size(); i++)
        {
            Stock currentStock = stocks.get(i);
            if(currentStock.getStockID() == ID)
            {
                return currentStock;
            }

        }
        return null;
    }

    //same method but returns a boolean
    public boolean contains(Stock value)
    {
        for(int i=0; i<stocks.size(); i++)
        {
            Stock currentItem = stocks.get(i);

            if(currentItem.equals(value))
            {
                return true;
            }
        }
        return false;
    }

    //allows us to add asset to the arraylist
    public void addStock(Stock value)
    {
        if(!contains(value))
        {
            this.stocks.add(value);
        }
    }

    //prints all available stock, with their information including their name, ID, price, and the amount the user owns.
    public String allStocks()
    {

        String text = "";

        for(int i=0; i<stocks.size(); i++)
        {
            Stock currentStock = stocks.get(i);

            text += "\n";
            text += "Asset Name: " +currentStock.getStockName() + "\n";
            text += "Asset ID: " +currentStock.getStockID() + "\n";
            text += "Asset Price: £" +currentStock.getStockPrice() + "\n";
            text += "Amount Owned: " + currentStock.getStockOwned() + "\n";

        }
        return text;
    }

    //asynchronous timer which will loop at a certain time, in order to update prices.
    public void priceUpdater()
    {
        Timer t = new Timer();

        t.scheduleAtFixedRate(
                new TimerTask()
                {
                    public void run()
                    {
                        //will go through all the available stocks for sale.
                        for(int i=0; i<stocks.size(); i++)
                        {
                            Stock currentItem = stocks.get(i);
                            //random number generator which has a max of 30
                            Random rand = new Random();
                            int upperbound = 30;
                            int int_random = rand.nextInt(upperbound);

                            double increasePrice = currentItem.getStockPrice()*1.0005;
                            double decreasePrice = currentItem.getStockPrice()/1.0005;

                            //will round the prices up by 2 d.p
                            String priceUp2 = String.format("%.2f",increasePrice);
                            String priceDown2 = String.format("%.2f",decreasePrice);

                            //if number generated is more or equal to 15, it will increase the price up
                            if(int_random >= 15)
                            {
                                currentItem.setStockPrice(Double.parseDouble(priceUp2));
                            }
                            //if number generated is less than 15, it will decrease the price.
                            else
                            {
                                currentItem.setStockPrice(Double.parseDouble(priceDown2));
                            }
                        }
                    }
                },
                0,
                1000); //runs this loop every second.
    }

}
