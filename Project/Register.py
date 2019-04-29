#!"C:\\Users\\M1037726\\AppData\\Local\\Programs\\Python\\Python36\\python.exe"

import cgi, cgitb
import _mysql
import logging

print ("Content-Type: text/html")
print ("""
	<TITLE>Register-Status</TITLE>
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


#print("entering the loop")
#print("entering the loop")

try:
    accno =frmEmp.getvalue("account number")
    newpassword =frmEmp.getvalue("newpassword")
    #print(newpassword)
    #print(accno)
    #print("select * from account where accno={0}".format(accno))
        
except Exception as e:
    print("exception is"+e)


try:
    #Retrieve the details from the database and validate the account number
    sel_query="select count(*) as count from account where accno={0}".format(accno)
    conn.query(sel_query)
    
    #store the result set
    res =conn.store_result()

    recs = res.fetch_row()
    for count in recs:
        value=count[0]

    #print("value is "+value)
    #print(value[0])
    #print("update account set password=MD5('"+newpassword+"') where accno='"+accno+"')")
    #Display the details on the page
    
        
    if(int(value[0])==0):
        #print("not entering here")
        #print(value)
        print("<b>Account Number does not exist....</b><br/><br/>")
        print("<b>please enter a valid account Number</b><br/><br/>")
        logging.info("Incorrect Account Number")
        
    else:
        #print("entered here")
        pass_query ="select password from account where accno="+accno
        conn.query(pass_query)
        result = conn.store_result()
        password = result.fetch_row()
        for res in password:
            val= res[0]
        try:
            if(res[0] is not None):
                print("Already registered")
                logging.info("Already registered")
            else:
                upd_query ="update account set password=MD5('"+newpassword+"')"+" where accno="+accno
                conn.query(upd_query)
                print("<b>Registered Successfully</b><br/><br/>")
                logging.info("Registered Successfully")
        except Exception as e:
            print(e)
            
except Exception as e:
    print("Exception is "+e)

    

