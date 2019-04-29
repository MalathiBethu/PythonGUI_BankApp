#!"C:\\Users\\M1037726\\AppData\\Local\\Programs\\Python\\Python36\\python.exe"

import cgi, cgitb
import _mysql
import sys
import logging

print ("Content-Type: text/html")
print ("""
	<TITLE>Deposit-Status</TITLE>
""")

# Establish the database connection
conn = _mysql.connect("localhost",
                      "root",
                      "",
                      "bankaccount")

log_fname ="my_account_app.log"

logging.basicConfig(filename =log_fname,
                    filemode ='a',
                    level=logging.DEBUG,
                    format= '%(asctime)s :%(levelname)s =>%(message)s',  #THE FORMAT IN WHICH IT SHOULD PRINT INSIDE THE LOG FILE
                    #asctime and levelname are keywords
                    datefmt ='%Y-%m-%d %H:%M:%S'
                    )


print ("<h3>Connection Established</h3><br>")

# Create the form object
frmEmp = cgi.FieldStorage()


#5 Records are already created and retreiving those account details only
# Retrieve the account number and printing the details(account number,accoutname,balance,bankname)
accno = frmEmp.getvalue("account number")
deposit =frmEmp.getvalue("deposit")

acc_query="select balance,deposit from account where accno='"+accno+"'"
conn.query(acc_query)
result =conn.store_result()
recs = result.fetch_row()
rows = result.num_rows()
#print(rows)
#print("rows are getting printed")

if(rows==0):
    print("Enter valid account number")
    logging.info("Enter valid account number")
else:
    for rec in recs:
        #print(rec[0])
        #print(rec[1])
        try:
            if(int(rec[0])>=int(deposit)):
                #print("entering here")
                bal = int(rec[0]) - int(deposit)
                deposit = int(rec[1]) + int(deposit)
                #print(bal)
                upd_query="update account set deposit='"+str(deposit)+"'," +"balance='"+str(bal)+"'"+"where accno='"+accno+"'"
                conn.query(upd_query)
                #conn.store_result()
                print("Deposited Successfully")
                logging.info("Deposited Successfully")
                #upd_query = "update account set balance='"
            else:
                print("Insufficient balance")
                logging.info("Insufficient balance")
        except Exception as e:
            print(e)

