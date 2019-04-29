#!"C:\\Users\\M1037726\\AppData\\Local\\Programs\\Python\\Python36\\python.exe"

import cgi, cgitb
import _mysql
import re
import logging


print ("Content-Type: text/html")
print ("""
	<TITLE>ForgotPassword</TITLE>
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
accno = frmEmp.getvalue("account number")
newpassword = frmEmp.getvalue("newpassword")
confirmpassword = frmEmp.getvalue("confirmpassword")


#print("Account number is"+accno)
#print("select * from account where accno"+accno)
#print("select * from account where accno={0}".format(accno))


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
    if(re.search("^"+newpassword+"$",confirmpassword)):
        upd_query="update account set password=MD5('"+newpassword+"') where accno='"+accno+"'"
        #print("before conn")
        #print(upd_query)
        conn.query(upd_query)
        print("Password is changed successfully")
        logging.info("Password is changed successfully")
    else:
       print("Please re-enter the password")
       logging.info("Please re-enter the password")



 
  
