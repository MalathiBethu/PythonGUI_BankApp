#!"C:\\Users\\M1037726\\AppData\\Local\\Programs\\Python\\Python36\\python.exe"

import cgi, cgitb
import _mysql
import re
import logging

print ("Content-Type: text/html")
print ("""
	<TITLE>ForgotPassword</TITLE>
""")


log_fname ="my_account_app.log"

logging.basicConfig(filename =log_fname,
                    filemode ='a',
                    level=logging.DEBUG,
                    format= '%(asctime)s :%(levelname)s =>%(message)s',  #THE FORMAT IN WHICH IT SHOULD PRINT INSIDE THE LOG FILE
                    #asctime and levelname are keywords
                    datefmt ='%Y-%m-%d %H:%M:%S'
                    )



print("<form action='ResetPassword.py' method='post' >")
print("Account Number: <input type='number' name='account number'  maxlength='6' pattern='/d{4}' required /><br/><br>")
print("New Password: <input type='password' name='newpassword' min='8'/><br/><br/>")
print("Confirm Password:<input type='password' name='confirmpassword' min='8'/><br/><br/>")
print("<input type='submit' value='Reset Password'/>")
print("</form>")

##
### Establish the database connection
##conn = _mysql.connect("localhost",
##                      "root",
##                      "",
##                      "bankaccount")
##
##print ("<h3>Connection Established</h3><br>")
##
### Create the form object
##frmEmp = cgi.FieldStorage()
##
###5 Records are already created and retreiving those account details only
### Retrieve the account number and printing the details(account number,accoutname,balance,bankname)
##accno = frmEmp.getvalue("account number")
##newpassword = frmEmp.getvalue("newpassword")
##confirmpassword = frmEmp.getvalue("confirmpassword")
##
##
##print("Account number is"+accno)
###print("select * from account where accno"+accno)
###print("select * from account where accno={0}".format(accno))
##
##
###Retrieve the values based on the account number from database
##sel_query="select * from account where accno={0}".format(accno)
##conn.query(sel_query)
##
###Store the result set
##all_recs= conn.store_result()
##
##num_rows = all_recs.num_rows()
##print(num_rows)
##if(num_rows==0):
##    print("Please register first to login")
##else:
##    if(re.search("^"+newpassword+"$",confirmpassword):
##       print("<form action='
##
## 
##        
