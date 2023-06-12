//blueprint for how all the stocks should be set up.

public class Stock
{
    //mainly self-explanatory methods.
    private double stockPrice;
    private String stockName;
    private int stockID;
    //stockID will be increased by every instance.
    private int stockOwned;
    private static int counter;

    public Stock(String stockName, double stockPrice)
    {
        this.stockName=stockName;
        this.stockPrice=stockPrice;
        this.stockOwned=0;
        this.stockID = counter++;
    }

    public Stock()
    {
        this.stockPrice=0.0;
        this.stockName ="";
        this.stockOwned=0;
        this.stockID=counter++;
    }

    public int getStockID()
    {
        return this.stockID;
    }

    public String getStockName()
    {
        return stockName;
    }

    public int getStockOwned()
    {
        return stockOwned;
    }

    public void setStockOwned(int amount)
    {
        this.stockOwned= amount;
    }

    public void addStockOwned(int stocksBought)
    {
        this.stockOwned += stocksBought;
    }

    public void lessStockOwned(int stockSold)
    {
        this.stockOwned -= stockSold;
    }

    //gets price of the stock
    public double getStockPrice()
    {
        String price = String.format("%.2f",stockPrice);
        stockPrice = Double.parseDouble(price);
        return stockPrice;
    }

    public void setStockPrice(double price)
    {
        this.stockPrice=price;
    }

}