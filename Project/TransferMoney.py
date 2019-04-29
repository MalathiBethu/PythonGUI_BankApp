#!"C:\\Users\\M1037726\\AppData\\Local\\Programs\\Python\\Python36\\python.exe"

import cgi, cgitb
import _mysql
import logging

print ("Content-Type: text/html")
print ("""
	<TITLE>Login-Status</TITLE>
""")

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
transferamount = frmEmp.getvalue("transferamount")

try:
    print("<b>aacount number</b>"+accno)
except Exception as e:
    print(e)

#print("<h>Account Number</h>"+LoginPage.accno)
print("<b>Payee Account Number: </b>"+payee_accno+"<br/>")
print("<b>Payee_name: </b>"+payee_name+"<br/>")
print("<b>payee_bankname</b>" + payee_bankname+"<br/>")
print("<b>transferamount</b>" + transferamount+"<br/>")


#print("select payeename from payee where accno='{0}'".format(accno)+"and payeeacc_no='{0}'".format(payee_accno))
sel_query ="select balance from payee where accno='{0}'".format(accno)+"and payeeacc_no='{0}'".format(payee_accno)
conn.query(sel_query)
res =conn.store_result()
payee= res.fetch_row()
try:
    for payeebal in payee:
            #print(payeebal[0])
            pay = payeebal[0]
            
except Exception as e:
    print(e)


rows=res.num_rows()
#print(rows)


bal_query="select balance from account where accno='"+accno+"'"
conn.query(bal_query)
res = conn.store_result()
rec = res.fetch_row()
for recs in rec:
    #print(recs[0])
    val = recs[0]
    

if(rows==0):
    print("Please enter valid account number")
    logging.info("Please enter valid account number")
else:
    #print("Details are valid")
    #print(recs[0])
    #print(transferamount)
    bal =int(recs[0])-int(transferamount)
    if(payeebal[0] is None):
        payeebalance =int(transferamount)
    else:
        payeebalance = int(payeebal[0]) + int(transferamount)
    #print(bal)
    if(bal>=0):       
        try:
            upd_query="update account set balance='"+str(bal)+"' where accno='"+accno+"'"
            #print("not entering here")
            conn.query(upd_query)
            result = conn.store_result()
            payee_upd="update payee set balance='"+str(payeebalance)+"' where accno='"+accno+"'"
            conn.query(payee_upd)
            conn.store_result()
            print("Transferred succesfully")
            logging.info("Transferred succesfully")
        except Exception as e:
            print(e)
    else:
        print("You have insufficient balance")
        logging.info("Transferred succesfullyYou have insufficient balance")
   
    


