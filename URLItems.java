/*
   Scott Campbell
   CSE383
   Fall 2014

   Obtain information from AWS Dynamo DB

   Connects to aws
   gets data and stores as arraylist of urlItems
 */

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import java.util.*;


public class URLItems {
	private String AWSEndpoint="dynamodb.us-east-1.amazonaws.com";
	private String AWSTable="cse383-f14-hw2";


	static AmazonDynamoDBClient client;

	public URLItems() {
	}

	public  ArrayList<URLItem> getAllItems() throws IOException{
		//connect to aws
		createClient();

		ArrayList<URLItem> allItems = null;
		try{
			//get list of Items
			allItems = GetAllItems();
			return allItems;

		}  catch (AmazonServiceException ase) {
			System.err.println(ase.getMessage());
			throw new IOException("Could not retrieve data");
		}  
	}

	/**
	  client used to connect to aws
	 **/
	private void createClient() throws IOException {

		AWSCredentials credentials = new PropertiesCredentials(
				URLItems.class.getResourceAsStream("AwsCredentials.properties"));

		client = new AmazonDynamoDBClient(credentials);
		client.setEndpoint(AWSEndpoint);
	}

	/**
	  get list .
	  Return as a list of values
	 */
	private ArrayList<URLItem> GetAllItems() throws AmazonServiceException {
		ScanRequest scan = new ScanRequest();	//create a scan to get ALL results
		scan.setTableName(AWSTable);	//set name of table

		ScanResult result =  client.scan(scan);

		ArrayList<URLItem> itemList = new ArrayList<URLItem>();
		URLItem i;

		for (Map<String,AttributeValue> itemSet:result.getItems()) 
		{
			i=new URLItem();
			for (Map.Entry<String, AttributeValue> item : itemSet.entrySet()) {
				String attributeName = item.getKey();
				AttributeValue aValue = item.getValue();
				String attributeValue = 
						(aValue.getS() == null ? "" : aValue.getS())
						+ (aValue.getN() == null ? "" : "N=[" + aValue.getN() + "]")
						+ (aValue.getB() == null ? "" : "B=[" + aValue.getB() + "]")
						+ (aValue.getSS() == null ? "" : "SS=[" + aValue.getSS() + "]")
						+ (aValue.getNS() == null ? "" : "NS=[" + aValue.getNS() + "]")
						+ (aValue.getBS() == null ? "" : "BS=[" + aValue.getBS() + "] \n");
				i.set(attributeName,attributeValue);
			}
			itemList.add(i);
		}
		return itemList;

	}
}
