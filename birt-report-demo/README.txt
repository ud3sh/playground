==This project demonstrates few use cases with Birt Reports==

1)sql_report.rptdesign => a simple report that demonstrates how to connect populate a birt table from a database and pass parameters to nested table that executes a subsequent query

2)json_report.rtpdesign => a failed attempt at getting data from a REST api using a scripted data source that doesn't use any Java Code.  At least with birt 3.5, this doesn't seem possible as birt does not understand the XMLHTTPRequest object. 

3)java_report_stackoverflow.rtpdesign => Demonstrates the use of a scripted datasource that uses a java client library to obtain data from a REST api and use that data as parameters to obtain more data to be used in nested table (a second java call + sql query).  In this case we get the latest answers from on the stackoverflow website, use that info to get the link to the associated question using a second api (where parameters are obtained from the first web call).
  
