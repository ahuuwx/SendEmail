# SendEmail
The company X wants to develop a console application to send out marketing email to its
potential customers. The marketing team has already prepared an email template for the
campaign. The email template has some placeholders like {{TITLE}},
{{FIRST_NAME}}, {{LAST_NAME}} that need to be filled with customer information.
The email template is stored in a JSON file (email_template.json) which has the
following format:
![image](https://user-images.githubusercontent.com/59599077/164012622-b30a7c19-51d2-453b-bb9c-ed5d4d371f88.png)

Notes: There is a special placeholder {{TODAY}} in the email template. The application
should automatically replace it with the date on which it runs. The format is “31 Dec
2020”
Customers are stored in a CSV file (customers.csv), which has the following format:
![image](https://user-images.githubusercontent.com/59599077/164012593-edc51556-180a-4a0a-8716-e30aa4eed546.png)

The console application should do:
- Receive path to email template file and customers file as argument
- For each customer, merge customer information with the email template and save
the output email into a JSON file similar to this example
- Write the output email files to a folder where the path is provided as an argument
to the application
- If a customer doesn’t have an email address, append that customer to the
errors.csv file (the format should be the same as the customers.csv file)

Result when running the program:
![image](https://user-images.githubusercontent.com/59599077/164012937-dcd61896-5355-4020-8246-996851038c0e.png)

Error file:
![image](https://user-images.githubusercontent.com/59599077/164014351-eee58134-828c-4227-99f1-4d979b8c9cbe.png)

Output file:
![image](https://user-images.githubusercontent.com/59599077/164014884-679dda37-da77-4c51-876b-5d7d10b365fc.png)
