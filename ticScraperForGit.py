import requests
import os
import datetime
from pytz import timezone
import sys
import time
import socks
import socket
import smtplib
from twilio.rest import Client
from stem.control import Controller
from stem import Signal

# I have replaced sensitive or private information with "*****"

print "new!"
tooEarlyTimes = 0
fullTime = ""
avgTime = 0
filein = ""

lastX = 0
lastY = 0

sendTextTimes = 0

# sends text when called
def sendText(sid, auth, message):
	global sendTextTimes
	sendTextTimes += 1
	message = message + "--Times Sent: " + str(sendTextTimes)
	if sendTextTimes % 15 == 0:
		client = Client(sid, auth)
		message = client.messages.create(
		    to="*******",
		    from_="********",
		    body=message)
		print(message.sid)

# translates the hour of the day into a code that
# the other functions can easily read
def translateHour(currentDay, currentTime):
	ti = int(currentTime)
	if not getLastTradeDay(currentDay) in currentDay:
		return 13
	if ti > 1630: return 13
	if ti > 1600: return 12
	if ti > 1530: return 11
	if ti > 1500: return 10
	if ti > 1430: return 9
	if ti > 1400: return 8
	if ti > 1350: return 7
	if ti > 1300: return 6
	if ti > 1230: return 5
	if ti > 1200: return 4
	if ti > 1130: return 3
	if ti > 1100: return 2
	if ti > 1030: return 1
	if ti > 930: return 0
	return 13

# function to change IP address
# outdated with tor proxies
def changeIP():
	with Controller.from_port(port = 9051) as controller:
	    controller.authenticate()
	    socks.setdefaultproxy(socks.PROXY_TYPE_SOCKS5, "127.0.0.1", 9050)
	    socket.socket = socks.socksocket
	    print "HERERERE"
	    #visiting url in infinite loop
	    #while True:
	    x = 0
	    while x < 2:
    		print requests.get("http://icanhazip.com").text
        #wait till next identity will be available
        	x += 1
        	try:
        		controller.signal(Signal.NEWNYM)
        		time.sleep(controller.get_newnym_wait())
        	except Exception as inst:
        		print inst
        	print "IP changed"
        	start(filein)
        print "done"

# tests to see if the url just produces empty values
def justEmpty(ticker, indx, indy):
	# check if it is not a trading hour (it is not a trading day)
	# if google exists but this doesn't, return x + 1
	# otherwise go on to too early
	url = '**********'
	page = requests.get(url)
	contents = page.content
	#if len(contents) > 151000:

# restarts the program while changing the IP address
def restart():
	ti = datetime.datetime.now(timezone('US/Eastern')).strftime ("%H%M")
	socks.setdefaultproxy(proxy_type=socks.PROXY_TYPE_SOCKS5, addr="127.0.0.1", port=9050)
	socket.socket = socks.socksocket
	print requests.get("http://icanhazip.com").text
	time.sleep(20)
	socks.setdefaultproxy(proxy_type=socks.PROXY_TYPE_SOCKS5, addr="127.0.0.1", port=9050)
	socket.socket = socks.socksocket
	print requests.get("http://icanhazip.com").text
	sendText("*****" + str(ti))
	start(filein)

# check if it is too early, in which case program should stop
def tooEarly():
	fullDate = datetime.datetime.now(timezone('US/Eastern')).strftime ("%Y%m%d")
	if not getLastTradeDay(fullDate) in fullDate:
		#restart()
		return 10
	tempo = datetime.datetime.now(timezone('US/Eastern')).strftime ("%H%M")
	if tempo > 430 and tempo < 1030:
		sys.exit()
	print "it's too early here"
	global tooEarlyTimes
	tooEarlyTimes += 1
	ti = datetime.datetime.now(timezone('US/Eastern')).strftime ("%H%M")
	if int(ti) > 1030:
		# you just got shut out
		return 10
	# either locked out or one page
	else:
		url = '************'
		page = requests.get(url)
		contents = page.content
		if "have access" in str(contents):
			return 10
		if len(contents) < 151000:
			print len(contents)
			me = datetime.datetime.now(timezone('US/Eastern')).strftime ("%H%M%S")
			sendText("******" + str(me))
			print "too early " + str(me)
			sys.exit()
	return 1


#get the index of last scraped
def getInd(indFilename, ticker):
	#print ticker
	#print "getting ind now"
	if not os.path.exists(indFilename):
		file = open(indFilename, "w+")
		return 0
	file = open(indFilename, "r")
	lastOne = "0"
	curr = ""
	for line in file:
		if not  len(line) < 3 :
			if line.endswith('\n'):
				curr = line[:-1]
			else:
				curr = line
			#get the ticker from the line
			parsedTicker = curr[1:curr.index('-')]
			parsedXInd = curr[curr.index('-') + 1:curr.index(',')]
			if parsedTicker == ticker:
				lastOne = parsedXInd
	return int(lastOne)




def updateInd(indFilename, ticker, indX, indY):
	file = open(indFilename, "r")
	lastOne = "0"
	curr = ""
	all = ""
	for line in file:
		if not len(line) < 3:
			if line.endswith('\n'):
				curr = line[:-1]
			else:
				curr = line
			#get the ticker from the line
			parsedTicker = curr[1:curr.index('-')]
			parsedXInd = curr[curr.index('-') + 1:curr.index(',')]
			if not parsedTicker == ticker:
				all += curr + '\n'
	indexS = '^%s-%d,%d;\n' % (ticker, indX, indY)
	all += indexS
	file.close()
	file = open(indFilename, "w+")
	file.write(all)
	file.close()

def getLastTradeDay(currentDay):
	fullTimeMins = int(datetime.datetime.now(timezone('US/Eastern')).strftime ("%H%M"))
	print str(fullTimeMins) + " full time mins"
	curr = ""
	prev = ""
	file = open("/root/allTicksRoot/daysOpen.txt", "r")
	for line in file:
		if line.endswith('\n'):
			line = line[:-1]
		if currentDay == line and fullTimeMins > 800:
			return currentDay
		if currentDay == line and fullTimeMins <= 800:
			print 'here it works'
			return prev
		if int(currentDay) < int(line):
			print prev + " prev"
			print currentDay + " currDay"
			print line + " line"
			return prev
		prev = line



def findLastPage(pageSource):
	if "last" not in pageSource:
		return 0
	if len(pageSource) < 500:
		return 0
	indexL = pageSource.rindex("last")
	pS = pageSource[indexL - 200:indexL]
	if "pageno=" not in pS:
		return 1
	noInd = pS.rindex("pageno=")
	quoInd = pS.rindex("id=")
	pS = pS[noInd + 7:quoInd - 2]
	return int(pS)

# make function to go over when to scrape
# if not a trade day-- scrape whenever
#if a trade day-- scrape day before up to 2 am?
# go hourly if durring trading hours

#find out if today is a trading day

def callStart(stringTre):
	start(stringTre)

#function to iterate until everything is captured
def goAgain(indFile):
	#compare tickers that you must scrape to 13 to what you have
	if not os.path.exists(indFile): return False
	#check if there are any under 13 xes
	file = open(indFile, "r")
	lastOne = "0"
	curr = ""
	for line in file:
		if not  len(line) < 3 :
			if line.endswith('\n'):
				curr = line[:-1]
			else:
				curr = line
			#get the ticker from the line
			parsedTicker = curr[1:curr.index('-')]
			parsedXInd = curr[curr.index('-') + 1:curr.index(',')]
			if int(parsedXInd) < 13: return True
	return False

def start(fileIn):
	finalS = ""
	ticker = ""
	filename = ""
	filein = fileIn
	file = open(filein, "r")
	completeName = ""
	completeInd = ""
	#usually tic
	checkToGo = True
	while (checkToGo == True) :
		for line in file:
			global tooEarlyTimes
			if tooEarlyTimes > 1:
				# go to next line
				tooEarlyTimes = 0
			if (len(line) > 0):
				if line.endswith('\n'):
					ticker = line[:-1]
				else:
					ticker = line
				ticker = ticker.replace(" ","")
				print "hi:"+ticker+":hi"
				contents = ""
				fullDate = datetime.datetime.now(timezone('US/Eastern')).strftime ("%Y%m%d")
				fullTime = datetime.datetime.now(timezone('US/Eastern')).strftime ("%H%M")
				lastTradeDay = getLastTradeDay(fullDate)
				print "***** " + lastTradeDay
				filename = '%s-%s' % (ticker, lastTradeDay)
				ind_name = 'tickerInd-%s' % lastTradeDay
				hour = translateHour(fullDate, fullTime)
				save_path = '/root/allTicksRoot/%s' % ticker
				ind_path = '/root/allTicksRoot/AAAAtickerInd/'
				if not os.path.exists(save_path):
					os.makedirs(save_path)
				completeName = os.path.join(save_path, filename)
				completeInd = os.path.join(ind_path, ind_name)
				moreFinal = ""
				currentIP = ""
				und = "/root/allTicksRoot/AAAAtickerInd/tickerInd-%s" % lastTradeDay
				xInd = getInd(und,ticker)
				if (int(xInd) < 13):
					if not int(xInd) == hour:
						for x in range(int(xInd) + 1, hour + 1):
							# do this every 5
							if (x == 7 or x == 1 or x == 13):
								currentIP = requests.get("http://icanhazip.com").text
								currentIP = currentIP[:-1]
								print currentIP
							y = 1
							start = time.time()
							yTop = 1000
							while y <= yTop:
								#151000
								url = '********' % (ticker, x, y)
								print url
								try:
									page = requests.get(url)
								except:
									endTime = time.time()
									totTime = endTime - startTime
									sendText("********", "middle of start, time ran: " + str(totTime))
									return True
								contents = page.content
								print str(len(contents)) + " length"
								if "This stock is currently not trading" in str(contents):
									x = 14
									break
								if y == 1:
									yTop = findLastPage(contents)
								if len(contents) < 500:
									ture = tooEarly()
									if ture == 10:
										return True
								if "unknown symbol" in contents:
									x = 14
									break
								if x == 1 and y == 1 and yTop == 0:# and len(contents) < 151000:
									tee = tooEarly()
									if tee == 10:
										return True
								print str(yTop) + "YTOP"
								if not "<th>NLS Time (ET)</th>" in contents:
									sendText('**********", "doesnt contain the right things: " + ticker + " " + str(len(contents)) + ":L:" + contents[0:200])
									yee = tooEarly()
									if yee == 10:
										return True
								begInd = contents.index("<th>NLS Time (ET)</th>")
								endInd = contents.index('<div id="quotes_content_left__panelLowerNav">')
								html = contents[begInd:endInd]

								if not ':' in html:
									fullDate = datetime.datetime.now(timezone('US/Eastern')).strftime ("%Y%m%d")
									ti = datetime.datetime.now(timezone('US/Eastern')).strftime ("%H%M")
									print "this is empty"
									if not getLastTradeDay(fullDate) in fullDate:
										y += 1
										break
									if int(ti) > 300:
										y += 1
										break
									gee = tooEarly()
									if gee == 10:
										return True

								allInfo = ""

								for line in html.splitlines():
									if ':' in line:
										ti = line
										ti = ti[ti.index("<td>") + 4:ti.index("</td>")]
										ti = ti.replace(":", "").replace(" ", "")
									if '$&nbsp;' in line:
										price = line[line.index('<td>$&nbsp;') + 11:line.rindex('&nbsp')]
										price = price.replace(" ", "").replace(",", "")
										allInfo = "%s,%s,???,%s,%s,%d,%d\n" % (price,ti,ticker,lastTradeDay,x,y) + allInfo


								finalS = allInfo + '\n' + finalS
								y += 1

							moreFinal = moreFinal + finalS
							finalS = ""
							end = time.time()
							sumTime = end - start
							avgTime = sumTime/y
							print avgTime
							secTime = datetime.datetime.now(timezone('US/Eastern')).strftime ("%H%M%S")
							dataTime = currentIP + "," + str(avgTime) + "," + ticker + "," + str(x) + "," + str(secTime) + '\n'
							ful = open("IPSpeed", "a+")
							print dataTime
							ful.write(dataTime)
							ful.close

							#make file for seeing last index
							#filename = "%s$%s-%s.txt" % (ticker, x, y)
							# TODO do an a+ after
							f= open(completeName, "a+")
							print completeName
							f.write(moreFinal)
							f.close()
							updateInd(completeInd, ticker, x, (y - 1))
							moreFinal = ""
							if avgTime > 2.3:
								sendText("********", "too slow")
								return True
		checkToGo = False
		return False

filein = sys.argv[1]
startTime = 0
endTime = 0
totalTime = 0
startTime = time.time()
with Controller.from_port(port = 9051) as controller:
	socks.setdefaultproxy(proxy_type=socks.PROXY_TYPE_SOCKS5, addr="127.0.0.1", port=9050)
	socket.socket = socks.socksocket
	controller.authenticate()
	agn = True
	while agn:
		sendText("**********", "refreshing IP")
		print requests.get("http://icanhazip.com").text + ":old IP"
		controller.signal(Signal.NEWNYM)
		time.sleep(controller.get_newnym_wait())
		print requests.get("http://icanhazip.com").text + ":new IP"
		agn = start(filein)

endTime = time.time()
totalTime = endTime - startTime
print totalTime
sendText("*********", "finished running: total time: " + str(totalTime))
