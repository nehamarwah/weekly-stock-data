# Weekly stock data for Dow Jones Index powered by Spring Boot

## Introduction
This project is a simple REST application for the stock data information. It uses Spring Boot, Spring Data JPA and some other helper libraries.

## Problem Statement

To build an aplication which serves the below mentioned purposes - 

- upload a bulk data set

- query for data by stock ticker

- add a new record

## Soluton

We have build an application using Spring Boot, Spring Data JPA. 


The application will do the below mentioned tasks - 

1. Upload the Bulk Data Set by executing POST request at `/stocks/upload` . The input accepted is the CSV file . We have taken the dow_jones_index.csv for testing purpose .Copied in /src/main/resources .
2. Fetch the stock data set by executing GET request at `/stocks/{tickerId}`, where `{tickerId}` is a stock ticker identifier.
3. Add the Stock Data Set by executing POST request at `/stocks/stockInfo'` . Inpur required is the json Object of Stock .

There is a service class `StockService` that we have implemented. For each method, there is a javadoc comment that describes expected behavior.


We have written test cases(Both Service and Controller) to cover the functional correctness of the above functionality .
Unit Testing done via Postman , captured in the Weekly_Stock_Test_Coverage.docx present in the /src/main/resources .
# weekly-stock-data
