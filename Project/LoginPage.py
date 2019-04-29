#!"C:\\Users\\M1037726\\AppData\\Local\\Programs\\Python\\Python36\\python.exe"

import cgi, cgitb
import _mysql
import logging

print ("Content-Type: text/html")
print ("""
	<TITLE>Login-Status</TITLE>
""")

#Configure the log file
log_fname = "my_account_app.log"

logging.basicConfig(filename =log_fname,
                    filemode ='a',
                    level=logging.DEBUG,
                    format= '%(asctime)s :%(levelname)s =>%(message)s',  #THE FORMAT IN WHICH IT SHOULD PRINT INSIDE THE LOG FILE
                    #asctime and levelname are keywords
                    datefmt ='%Y-%m-%d %H:%M:%S'
                    )



# Establish the database connection
conn = _mysql.connect("localhost",
                      "root",
                      "",
                      "bankaccount")

print ("<h3>Connection Established</h3><br>")

# Create the form object
frmEmp = cgi.FieldStorage()

#5 Records are already created and retreiving those account details only
#The account numbers of the records are 100001,100002,100003,100004,100005
# Retrieve the account number and printing the details(account number,accoutname,balance,bankname)
accno = frmEmp.getvalue("account number")
password =frmEmp.getvalue("password")
#print("password is"+password)


#print("Account number is"+accno)
#print("select * from account where accno"+accno)
#print(s"select * from account where accno={0}".format(accno))


#Retrieve the values based on the account number from database
sel_query="select * from account where accno={0}".format(accno)
conn.query(sel_query)
#Store the result set
all_recs= conn.store_result()


num_rows = all_recs.num_rows()
#print(num_rows)
if(num_rows==0):
    print("Please register first to login")
    logging.info("Please register first to login")

else:
    recs =all_recs.fetch_row()
    pass_query="select password from account where password=MD5('"+password+"')"
    conn.query(pass_query)
    result = conn.store_result()
    rows = result.num_rows()
    results =result.fetch_row()
    for rec in results:
        value=rec[0]
        
    if(rows ==0):
        print("enter valid password")
        logging.info("enter valid password")
    else:    
        for rec in recs:
            #print(rec[0])
            #print(rec[1])
            print ("<h3><u>ACCOUNT DETAILS</u></h3><br/>")
            print ("Account Number => "+str(rec[0])+"<br/>")
            print ("Account Name =>"+str(rec[1])[1:]+"<br/>")
            print ("Bank Name => " +str(rec[2])[1:]+"<br/>" )
            print ("Balance=> "+str(rec[3])+"<br/>" )
            logging.info("login successful")


        #Adding Payee and Removing Payee Links
 
        #print("<b><input type='hidden' name='accountnum' value='"+accno+"' /></b>")
        print("<a href='AddPayee.html'>AddPayee</a><br/>")
        print("<a href='RemovePayee.html'>RemovePayee</a><br/>")
        print("<a href='TransferMoney.html'>TransferMoney</a><br/>")
        print("<a href='DepositMoney.html'>DepositMoney</a><br/>")
        print("<style>")
        print(".container{ "+
            	"position: relative;" +
            "}"
            ".topright{ "+
            	 "position :absolute;" +
            	 "top: -260px;" +
                 "right: 16px;" +
                 "font-size: 18px;" +
            "}")
        print("</style>")

        print("<div class='container'>")
        print("<div class='topright'><a href='Login.html'><button>Logout</button></a></div>")
        print("</div>")






    
        
