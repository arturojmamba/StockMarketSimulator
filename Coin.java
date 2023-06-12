import java.util.TimerTask;
import java.util.Timer;

//a subclass of Stock, both are assets.
public class Coin extends Stock
{
    //has a blockchain attribute.
    String blockChain;
    int coinOwned;
    double value;
    int counter;
    boolean staked;

    public Coin(String coinName, double coinPrice)
    {
        super(coinName, coinPrice);
        this.blockChain="";
        this.coinOwned=0;
        this.value=0.00;
        this.counter=0;
        this.staked = false;
    }

    public double getValue()
    {
        return this.value;
    }

    public void setBlockChain(String block)
    {
        this.blockChain=block;
    }

    public String getBlockChain()
    {
        return blockChain;
    }

    public String getCoinName()
    {
        return this.getStockName();
    }

    public int getCounter()
    {
        return this.counter;
    }

    //an incentive to make users hold their crypto coins.
    //the longer they hold it, the more dividends they get.
    //every 30 seconds.
    public void stakeCoin(Coin coin, int amount)
    {
            double coinPrice = coin.getStockPrice();
            coinPrice = (coinPrice * amount) * 1.01;
            coinPrice -= coin.getStockPrice()*amount;
            value=coinPrice;
            counter++;
            staked=true;
    }


}
