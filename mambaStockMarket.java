import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class mambaStockMarket extends Frame
{
    private ArrayList<Client> investmentUser;
    private int arraySize;
    private static TextArea infoArea = new TextArea("Click Stock list");
    private static TextArea consoleArea = new TextArea("ArJo's Stock Market Simulator");
    private Panel clientButtonsPanel;
    private static StockArrayList availableStock = new StockArrayList();

    public static void main (String [] a) throws Exception
    {
        //Initialises new stocks and coins that the user is able to buy
        Stock apple = new Stock("Apple", 129.30);
        Stock amazon = new Stock("Amazon", 2360.90);
        Stock tesla = new Stock("Tesla", 770.02);
        Stock microsoft = new Stock("Microsoft", 219.78);
        Coin btc = new Coin("Bitcoin", 30964.79);
        Coin eth = new Coin("Ethereum", 2350.52);
        Coin sol = new Coin("Solana", 80.61);

        sol.setBlockChain("SOL");
        btc.setBlockChain("BTC");
        eth.setBlockChain("ETH");

        //adds them to the available stock arraylist that will be printed in infoArea.
        availableStock.addStock(apple);
        availableStock.addStock(amazon);
        availableStock.addStock(tesla);
        availableStock.addStock(microsoft);
        availableStock.addStock(btc);
        availableStock.addStock(eth);
        availableStock.addStock(sol);

        new mambaStockMarket();
    }

    //allows us to display text on text area
    //used for displaying available stocks
    public static void print(String text)
    {
        infoArea.setText(text);
    }

    //reacts as an update text area, which will say when the user buys or sells an item, creates an account, and error messages.
    public static void printConsole(String text)
    {
        consoleArea.setText(text);
    }

    //prints the available stock to infoArea
    public static void printStocks()
    {
        print(availableStock.allStocks());
    }

    //Uses an asynchronous method in order to update prices constantly and print them to infoArea

    public static void printPriceUpdater()
    {
        Timer t = new Timer();

        t.scheduleAtFixedRate(
                new TimerTask()
                {
                    public void run()
                    {
                        availableStock.priceUpdater();
                        printStocks();
                    }
                },
                0, //this should start as soon as the method is called
                1000); //it should call whatever is in run(), every second
    }

    //Allows us to add a user, a user has to be added before being able to buy and sell.
    //When clicking this button, it will print out the users info, including their balance and their investments, updating it every second.
    public void addClient(Client c)
    {

        Button btn = new Button("Main Account");
        this.clientButtonsPanel.add(btn);
        this.add(btn);

        btn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                //prompt allows us to create a new window.
                Prompt acp = new Prompt();
                acp.add(new Label("User Information: "));

                TextArea infoAreaUser = new TextArea(10,40);
                acp.setLayout(new GridLayout(0, 1));
                infoAreaUser.setEditable(false);
                acp.add(infoAreaUser);

                Timer t = new Timer();

                t.scheduleAtFixedRate(
                        new TimerTask()
                        {
                            public void run()
                            {
                                infoAreaUser.setText(c.getUserInfo(c));
                            }
                        },
                        0,      // run first occurrence immediately
                        1000); //refresh user data every second.

                acp.activate();

            }
        });


        this.setVisible(true); // Just to refresh the frame, so that the button shows up

    }

    //When this button is pressed, it will set the users balance back to 5000, and remove all their investments.
    public void resetButton()
    {
        Button resetBalanceButton=new Button("Reset");
        this.add(resetBalanceButton);
        resetBalanceButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                Client user = investmentUser.get(0);

                Account balance = user.getAccount();

                balance.balanceReset();
                user.resetInvestment();

                printConsole("Account Successfully Reset");

            }
        });
        this.setVisible(true);
    }

    //When this button is clicked, a user's portfolio is printed and saved on to a text file. It can be clicked multiple times.
    public void fileWriteButton() throws IOException
    {
        PrintWriter outputStream = new PrintWriter(new FileWriter("users.txt"));

        Button fileWrite=new Button("File Writer");
        this.add(fileWrite);
        fileWrite.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                Client currentUser = investmentUser.get(0);
                outputStream.println(currentUser.getUserInfo(currentUser) + "\n");
                printConsole("Successfully Written Portfolio");
                outputStream.flush();
            }
        });
        this.setVisible(true);
    }

    //the text file is printed onto the consoleArea
    public void fileReadButton() throws IOException
    {
        BufferedReader inputStream = new BufferedReader(new FileReader("users.txt"));

        Button fileWrite=new Button("File Reader");
        this.add(fileWrite);
        fileWrite.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                StringBuilder builder = new StringBuilder();
                try
                {
                 String line;
                 while((line = inputStream.readLine()) !=null)
                 {
                     builder.append(line).append("\n");
                     printConsole(String.valueOf(builder));
                 }
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
                inputStream.lines();
            }
        });
        this.setVisible(true);
    }

    //Main app and GUI with the buttons.
    //User has not been given a deposit button to make it seem more real.
    //A deposit button would make it too easy for the user.
    public mambaStockMarket() throws IOException
    {
        this.setLayout(new FlowLayout());
        this.arraySize=0;
        //will be used to make sure that only 1 user can be made.
        this.investmentUser = new ArrayList<Client>(arraySize);

        //When this button is clicked, it will prompt the user for a name that will be used for their creation of their account.
        Button addClientButton = new Button("Add User");
        addClientButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                Prompt acp = new Prompt();
                TextField textField = new TextField(30);

                acp.add(textField);
                acp.setLayout(new GridLayout(4, 2));
                acp.add(new Label("Enter Client Name to add"));

                acp.addSubmitListener(new ActionListener()
                {

                    public void actionPerformed(ActionEvent e)
                    {
                        //makes sure only 1 user is allowed by using an arraylist.
                        if(investmentUser.size()==0)
                        {
                            Client c = new Client(textField.getText());
                            investmentUser.add(c);
                            //Creates button's
                            addClient(c);
                            //welcomes user
                            printConsole("User Successfully Made! Welcome: " + c.getUsername());
                            resetButton();
                            try
                            {
                                fileWriteButton();
                            }
                            catch (IOException ex)
                            {
                                ex.printStackTrace();
                            }
                        }
                        else
                        {
                            //if user tries to add 1 more account.
                            printConsole("Error! Only 1 user allowed per instance");
                        }

                    }

                });

                acp.activate();

            }

        });

        //Allows the user to buy stocks/coins by entering their ID and the amount they want to buy.
        Button buyButton = new Button("Buy");
        this.add(buyButton);
        buyButton.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                Prompt acp = new Prompt();
                TextField textField = new TextField(30);
                TextField textField2 = new TextField(30);

                acp.add(textField);
                acp.add(textField2);
                acp.setLayout(new GridLayout(4, 2));
                acp.add(new Label("Enter Asset ID and amount below it!"));

                acp.addSubmitListener(new ActionListener()
                {

                    public void actionPerformed(ActionEvent e)
                    {
                        String stockID = textField.getText();
                        String amount = textField2.getText();

                        int stockInt = Integer.parseInt(stockID);
                        int amountInt = Integer.parseInt(amount);

                        if(investmentUser.size()==1)
                        {
                            Client currentClient =  investmentUser.get(0);
                            //allows us to get that specific asset using the inputted ID.
                            Stock item = availableStock.containsID(stockInt);

                                try
                                {
                                    //If successful, it will print to the console the asset name, amount bought and price they bought at.
                                    currentClient.buyStocks(item, amountInt);
                                    printConsole("Success!" + "\n" + "Asset Name: " + item.getStockName() + "\n" + "Amount bought: " + amountInt + "\n" +"Price: £" + String.format("%.2f",item.getStockPrice()*amountInt));
                                }
                                catch(Exception ex)
                                {
                                    //if users balance is too low, it will print this to consoleArea
                                    String text = "";
                                    text += "Balance: " + currentClient.getAccount().getBalance() + "\n" ;
                                    text += "Value of asset(s) wanted: " + item.getStockPrice()*amountInt + "\n";
                                    text += "Transaction could not be completed!" + "\n";
                                    printConsole(text);
                                }
                            }
                            else
                            {
                                //if user try's to buy without an account
                                printConsole("Please Make an Account");
                            }

                    }

                });
                acp.activate();
            }
        });

        //allows user to sell an amount of an asset they own.
        Button sellButton = new Button("Sell");
        this.add(sellButton);
        sellButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Prompt acp = new Prompt();
                TextField textField = new TextField(30);
                TextField textField2 = new TextField(30);

                acp.add(textField);
                acp.add(textField2);
                acp.setLayout(new GridLayout(4, 2));
                acp.add(new Label("Enter a stock ID and the amount you want to sell"));

                acp.addSubmitListener(new ActionListener()
                {

                    public void actionPerformed(ActionEvent e)
                    {

                        String stockID = textField.getText();
                        String amount = textField2.getText();

                        int stockInt = Integer.parseInt(stockID);
                        int amountInt = Integer.parseInt(amount);

                        Client currentClient =  investmentUser.get(0);


                        Stock item = availableStock.containsID(stockInt);
                        try
                        {
                            printConsole("Success!" +"\n" + "Asset Sold: "+ item.getStockName() + "\n" + "Amount Sold: " +amountInt +"\n" + "Sell Price: £" + String.format("%.2f",item.getStockPrice()*amountInt));
                            currentClient.sellStocks(item, amountInt);
                        }
                        catch(Exception ex)
                        {
                            //if user enters a stock they don't own or don't have enough off, this error message will show up.
                            String text = "";
                            text += "Stock Owned: " + item.getStockOwned() + "\n";
                            text +="Stock Selling: " + amount + "\n";
                            text += "Transaction could not be completed!" + "\n";
                            printConsole(text);
                        }
                    }
                });
                acp.activate();
            }
        });

        //allows the user to stake their crypto coins which will give them an incentive to hold it.
        Button stakeButton = new Button("Stake Coin");
        this.add(stakeButton);
        stakeButton.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                Prompt acp = new Prompt();
                TextField textField = new TextField(30);
                TextField textField2 = new TextField(30);

                acp.add(textField);
                acp.add(textField2);
                acp.setLayout(new GridLayout(4, 2));
                acp.add(new Label("Enter Coin ID and amount below it!"));

                acp.addSubmitListener(new ActionListener()
                {

                    public void actionPerformed(ActionEvent e)
                    {
                        String coinID = textField.getText();
                        String amount = textField2.getText();

                        int coinInt = Integer.parseInt(coinID);
                        int amountInt = Integer.parseInt(amount);

                        Client currentUser = investmentUser.get(0);
                        //if the inputted stockID is more than 3, it will call the stake coin method.
                        if(coinInt>3)
                        {
                            //gets current coin
                            Coin item = (Coin) availableStock.containsID(coinInt);
                            if(item.getStockOwned()>=amountInt)
                            {

                                //if amount owned is more than 0, it will deposit the items value.
                                if(item.getStockOwned()>0 && item.staked == false)
                                {

                                    Timer t = new Timer();

                                    t.scheduleAtFixedRate(
                                            new TimerTask()
                                            {
                                                public void run()
                                                {
                                                    if(item.getStockOwned()>0)
                                                    {
                                                        item.stakeCoin(item,amountInt);
                                                        Account user = currentUser.getAccount();

                                                        user.deposit(item.value);
                                                        printConsole("Dividends Sent Out! : £" + String.format("%.2f",item.value) + "\n" + "Blockchain: " + item.getBlockChain() + "\n" + "Payouts Sent: " + item.getCounter());
                                                    }
                                                }
                                            },
                                            0,
                                            30000);
                                    printConsole("Successfully Staked Coin!" + "\n" + "Blockchain: " + item.getBlockChain());//dividends given out every 30 seconds from when item is staked
                                }
                                else
                                {
                                    printConsole("Coin not found or coin already staked! ");
                                }
                            }
                            else
                            {
                                printConsole("Not enough owned!");
                            }
                            //if they don't, it will print coin not found in inventory.

                        }
                        else
                        {
                            //error message if the item they input is a stock.
                            String text="";
                            text += "Not Successful"+ "\n";
                            text += "Item selected is not a coin" + "\n";
                            printConsole(text);
                        }
                    }
                });
                acp.activate();
            }
        });

        //creates a new panel for buttons
        clientButtonsPanel = new Panel();
        clientButtonsPanel.setLayout(new GridLayout(0,1));
        clientButtonsPanel.setVisible(true);
        this.add(clientButtonsPanel);

        this.add(addClientButton);

        try
        {
            fileReadButton();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        infoArea.setEditable(false);
        consoleArea.setEditable(false);
        this.add(infoArea);
        this.add(consoleArea);

        //will automatically print the prices of assets automatically when launched.
        printPriceUpdater();

        //allows the window to be closed by clicking x button.
        WindowCloser wc = new WindowCloser();
        this.addWindowListener(wc);

        //self-explanatory
        this.setSize(550, 500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}