#!"C:\\Users\\M1037726\\AppData\\Local\\Programs\\Python\\Python36\\python.exe"

import cgi, cgitb
import _mysql
import logging



    
print ("Content-Type: text/html")
print ("""
	<TITLE>AddPayee-Status</TITLE>
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
# Retrieve the account number and printing the details(account number,accoutname,balance,bankname)
payee_accno = frmEmp.getvalue("payee_accountnumber")
payee_name = frmEmp.getvalue("payeename")
payee_bankname = frmEmp.getvalue("payeebankname")
accno = frmEmp.getvalue("account number")

try:
    print("<b>Your acount number</b>"+accno+"<br/>")
except Exception as e:
    print(e)

#print("<h>Account Number</h>"+LoginPage.accno)
print("<b>Payee Account Number: </b>"+payee_accno+"<br/>")
print("<b>Payee_name: </b>"+payee_name+"<br/>")
print("<b>payee_bankname</b>" + payee_bankname+"<br/>")


#insert into payee(accno,payeeacc_no,payeename,payeebank)values('100001','100003','jack,'sbh');

##try:
##    print("Account number is"+accno)
##    print("select * from account where accno"+accno)
##    print("select * from account where accno={0}".format(accno))
##except Exception as e:
##    print("Exception as ",e)
    
#print("insert into payee(accno,payeeacc_no,payeename,payeebank)values('"+accno+"','"+payee_accno+"','"+payee_name+"','"+payee_bankname+"')")
#print("entering")

#Retrieve the values based on the account number from database
try:
    sel_query="insert into payee(accno,payeeacc_no,payeename,payeebank)values('"+ accno + "','" + payee_accno + "','" + payee_name + "','"+payee_bankname+"')"
    conn.query(sel_query)
    print("<b>Payee added successfully</b><br/>")
    logging.info("Payee added successfully")
    
except Exception as e:
    print("Payee already exists with the same account number")
    print("Please enter proper details")
    logging.info("Payee already added")
    #print("Exception is ",e)


