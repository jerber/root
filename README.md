# root

This project has two parts.  The first is a tic-data scraper built in Python that can run 24/7 in the cloud collecting data.  The second part is the high frequency trading program built in Java, which takes the data mined from the scraper and allows the user to run back tests on the data using a custom algorithm they input into the program.

Tic-Data Scraper:

ticScraperForGit.py operates in the cloud 24/7, scraping tic data for all stocks on the Nasdaq.  This program creates and saves the data each day in custom directories.  For example, if the program ran for 09/17/2017 and you wanted to find GOOG tic data, you would go to root/allTicksRoot/GOOG/ and find the file GOOG-20170917 which contains the day's data.  The data is organized by columns as follows: price, time executed (down to centisecond), number of shares purchased, ticker, date (in form of YYYYMMDD), hours scraped so far, and pages for that hour scraped.  If you would like to see all of the stocks collected during a certain day (i.e. 09/17/2017), you can go to root/allTicksRoot/AAAAtickerInd/ and find the file tickerInd-20170917 which has all of the stocks collected.

Additionally, if you would like to see the various IP speeds that Tor used you can open root/IPSpeed, which records the speed of each connection, organized by columns as follows: IP address, average time it takes to scrape a page (in seconds), ticker it scraped, time at which the connection went live.

Using this data collected one can then back test high frequency trading algorithms.


High Frequency Trading Program:

Reads in data mined from ticScraperForGit.py and back tests data with a given algorithm.  Users can input their own algorithms in root/HighFrequencyTradingProgramForGit/src/trade.java, then select their desired latency, stocks, timeframe, and printing options (to display the results of the algorithm) in root/HighFrequencyTradingProgramForGit/src/Start.java.  Running Start.java will then start the simulation with the desired specification.
