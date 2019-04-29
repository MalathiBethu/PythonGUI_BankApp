#!"C:\\Users\\M1037726\\AppData\\Local\\Programs\\Python\\Python36\\python.exe"

import cgi, cgitb
import _mysql

   
print ("Content-Type: text/html")
print ("""
	<TITLE>AddPayee-Status</TITLE>
""")

# Create the form object
frmEmpp = cgi.FieldStorage()
accnum = frmEmpp.getvalue("accountnum")

print(accnum)
print("<form action='AddPayee.py' method='post'>")
print("<input type='hidden' name='acnum' value='"+accnum+"' />")
print("Payee Account Number:" +
      "<input type='number' name='payee_accountnumber' maxlength='6' required /><br/><br/>"+
      "Payee Name: <input type='text' name='payeename' required /><br/><br/>"+
      "Payee Bank Name: <input type='text' name='payeebankname' required /><br/><br/>")
print("<input type='submit' value='add' />")
print("</form>")



      
### Establish the database connection
##conn = _mysql.connect("localhost",
##                      "root",
##                      "",
##                      "bankaccount")
##
##print ("<h3>Connection Established</h3><br>")
##
##
##
###Insert the values into the database if
