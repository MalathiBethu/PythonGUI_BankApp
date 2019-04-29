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


try:
    #print("entering the loop")
    # Retrieve the account number and printing the details(account number,accoutname,balance,bankname)
    payee_accno = frmEmp.getvalue("payee_accountnumber")
    payee_name = frmEmp.getvalue("payeename")
    payee_bankname = frmEmp.getvalue("payeebankname")
    accno = frmEmp.getvalue("account number")
    #print("delete * from payee where payeeacc_no={0}".format(payee_accno))

    chk_query ="select payeename from payee where payeeacc_no={0}".format(payee_accno)
    conn.query(chk_query)
    result = conn.store_result()
    rows = result.num_rows()
    if(rows>0):
        #Deleting the payee list from the database
        sql_query = "delete from payee where payeeacc_no={0}".format(payee_accno)
        conn.query(sql_query)
        print("Payee Removed Successfully")
        logging.info("Payee Removed Successfully")
    else:
        print("Payee does not exist")
        logging.info("Payee does not exist")
        
except Exception as e:
    print("Payee does not exist")
    logging.info("Payee does not exist")
    print("Excetion is"+e)
