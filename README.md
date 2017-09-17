# root

Tic-Data Scraper:

ticScraperForGit.py operates in the cloud 24/7, scraping tic data for all stocks on the Nasdaq.  This program creates and saves the data each day in custom directories.  For example, if the program ran for 09/17/2017 and you wanted to find GOOG tic data, you would go to root/allTicksRoot/GOOG/ and find the file GOOG-20170917 which contains the day's data.  The data is organized by collumns as follows: price, time executed (down to centisecond), number of shares purchased, ticker, date (in form of YYYYMMDD), hours scraped so far, and pages for that hour scraped.  If you would like to see all of the stocks collected durring a certain day (i.e. 09/17/2017), you can go to root/allTicksRoot/AAAAtickerInd/ and find the file tickerInd-20170917 which has all of the stocks collected.

Additionally, if you would like to see the various IP speeds that Tor used you can open root/IPSpeed, which records the speed of each connection, organized by collumns as follows: IP address, average time it takes to scrape a page (in seconds), ticker it scraped, time at which the connection went live.

Using this data collected one can then backtest high frequency trading algorithms.


High Frequency Trading Program:

Reads in data mined from ticScraperForGit.py and backtests data with a given algorithm.
