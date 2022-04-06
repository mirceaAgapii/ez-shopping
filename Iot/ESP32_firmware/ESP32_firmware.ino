#include <WiFi.h>
#include <HTTPClient.h>
#include <SPI.h>
#include <MFRC522.h>

#define SS_PIN 21
#define RST_PIN 22
#define GREEN_LED_PIN 25
#define RED_LED_PIN 26
#define RXD2 16
#define TXD2 17

// Create MFRC522 instance.
MFRC522 mfrc522(SS_PIN, RST_PIN);   

// Create HTTP instance.
HTTPClient http;

//define WiFi connection credentials
//const char* ssid = "BioShok";
//const char* password = "07121991";

const char*  ssid = "Inther";
const char*  password = "inth3rmoldova";

//Your Domain name with URL path or IP address with path
String serverName = "http://172.17.41.51:8080/api/arduino";
//String serverName = "http://192.168.10.105:8080/api/arduino";

//security:
const char *security_token = "ac92c0cf-417e-4915-8520-0a8f2fb2b4ef";
//workstation id
const char *wsid = "WS01";

String lastRead;
String barcode;

void setup() {
  //Initialize Serial port
  Serial.begin(9600); 
  //Initialize Serial port 2 for GM67 barcode scanner
  Serial2.begin(9600, SERIAL_8N1, RXD2, TXD2);
  Serial.println();
  Serial.println("Serial Txd is on pin: "+String(TXD2));
  Serial.println("Serial Rxd is on pin: "+String(RXD2));

  //initialize led
  pinMode(GREEN_LED_PIN, OUTPUT);
  pinMode(RED_LED_PIN, OUTPUT);
  //test led
  digitalWrite(GREEN_LED_PIN, HIGH); 
  digitalWrite(RED_LED_PIN, HIGH); 
  delay(500);   
  digitalWrite(GREEN_LED_PIN, LOW); 
  digitalWrite(RED_LED_PIN, LOW); 

  //Connect to WiFi
  WiFi.begin(ssid, password);
  Serial.println("Connecting");
  while(WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.print("Connected to WiFi network with IP Address: ");
  Serial.println(WiFi.localIP());

  // Initiate  SPI bus
  SPI.begin();     
  // Initiate MFRC522 
  mfrc522.PCD_Init();   

  Serial.println("Approximate your card to the reader...");
  Serial.println("Or scan a QR code...");
  Serial.println();

}

void loop() {
  handleRfRead();
  handleQrScan();
}

/*
* Method to handle scans from Barcode scanner
* 
*/
void handleQrScan() {
  //Check for any barcode scans
  while (Serial2.available()) {
    barcode += char(Serial2.read());
  }
  if (barcode.length() > 0) {
    digitalWrite(RED_LED_PIN, HIGH);
    //Show scanned barcode/QR on serial monitor
    Serial.print("Received QR scan: ");
    Serial.println(barcode);
    barcode.trim();
    //Send data to server
    sendData(barcode, "/qr");
    barcode = "";
    digitalWrite(RED_LED_PIN, LOW);
  }
}

/*
* Method to handle reads from RF reader
*/
void handleRfRead() {
  // Look for new cards
  if (!(mfrc522.PICC_IsNewCardPresent() && mfrc522.PICC_ReadCardSerial())) {
    return;
  }

  digitalWrite(GREEN_LED_PIN, HIGH);
  //Show UID on serial monitor
  Serial.print("UID tag :");
  String content= "";
  byte letter;
  for (byte i = 0; i < mfrc522.uid.size; i++) 
  {
     Serial.print(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : " ");
     Serial.print(mfrc522.uid.uidByte[i], HEX);
     content.concat(String(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : " "));
     content.concat(String(mfrc522.uid.uidByte[i], HEX));
  }
  Serial.println();
  
  content.toUpperCase();
  String readData = content.substring(1);
  if(readData != lastRead) {
    lastRead = readData;
    Serial.println("Send request for new id");
  } else {
    //The last UID was scanned again. Add a delay to prevent accidental double scan
    Serial.println("The same id read");
    delay(500);
    digitalWrite(GREEN_LED_PIN, LOW);
    return;
  }
  //Send data to server
  sendData(readData, "/rf");

  digitalWrite(GREEN_LED_PIN, LOW);
}

/*
* Methond for sending data to server by using REST API
* 
* dataStr  data to be sent
* mapping  enpoint suffix
*/
void sendData(String dataStr, String mapping) {
   //Check WiFi connection status before sending HTTP request
   if(WiFi.status()== WL_CONNECTED){
      
      // Your Domain name with URL path or IP address with path
      String endpoint = serverName + String(mapping);
      Serial.print("POST request will be sent to path: ");
      Serial.println(endpoint);
      http.begin(endpoint.c_str());
     
      String httpRequestData = String(security_token) + String(";") + String(wsid) + String(";") + String(dataStr) + String(";");           
      // Send HTTP POST request
      int httpResponseCode = http.POST(httpRequestData);
      Serial.print("HTTP Response code after POST: ");
      Serial.println(httpResponseCode);

      // Free resources
      http.end();
    }
    else {
      Serial.println("WiFi Disconnected");
    }
}
