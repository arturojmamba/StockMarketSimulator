//Allows us to access a clients balance, deposit and withdraw.
//User will not be allowed to manually deposit any amount of money to make it seem more real.
//Only way to increase their balance is to make profits from the app.
public class Account implements resetBalance
{
    private double balance;

    public Account()
    {
        //balance will be set to 5000 to start with.
        this.balance=5000;
    }

    public double getBalance()
    {
        //returns the users balance by 2 d.p
        String balance2 = String.format("%.2f",balance);
        balance = Double.parseDouble(balance2);
        return this.balance;
    }

    public void balanceReset()
    {
        //resets balance back to 5000.
        this.balance=5000.00;
    }

    public double setBalance(double amount)
    {
        return this.balance;
    }

    //remove money from balance
    public boolean withdraw(double amount)
    {
        //rounds balance by 2 d.p
        String amount2 = String.format("%.2f",amount);
        boolean enoughBalance=false;
        //checks if the amount being withdrawn is enough
        if (amount > this.balance)
        {
            //will print this if balance is not enough
            System.out.println("Not enough balance.");
            return enoughBalance=false;
        }
        else
        {
            //if enough, it will remove the amount from the balance.
            this.balance -= Double.parseDouble(amount2);
            return enoughBalance=true;
        }
    }

    //allows us to add money to balance
    public void deposit(double amount)
    {
        //rounds it by 2 d.p
        String amount2 = String.format("%.2f",amount);
        this.balance += Double.parseDouble(amount2);
    }

}
