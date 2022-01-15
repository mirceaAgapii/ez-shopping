#include <WiFi.h>
#include <HTTPClient.h>
#include <SPI.h>
#include <MFRC522.h>


#define SS_PIN 21
#define RST_PIN 22
MFRC522 mfrc522(SS_PIN, RST_PIN);   // Create MFRC522 instance.
HTTPClient http;


const char* ssid = "BioShok";
const char* password = "07121991";

//const char*  ssid = "Inther";
//const char*  password = "inth3rmoldova";

//Your Domain name with URL path or IP address with path
//String serverName = "http://172.17.44.81:8080/api/arduino";
String serverName = "http://192.168.10.110:8080/api/arduino";

//security:
const char *security_token = "ac92c0cf-417e-4915-8520-0a8f2fb2b4ef";
//workstation id
const char *wsid = "WS01";


String lastRead;

void setup() {
  Serial.begin(9600); 
  WiFi.begin(ssid, password);
  Serial.println("Connecting");
  while(WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.print("Connected to WiFi network with IP Address: ");
  Serial.println(WiFi.localIP());

  SPI.begin();      // Initiate  SPI bus
  mfrc522.PCD_Init();   // Initiate MFRC522
  Serial.println("Approximate your card to the reader...");
  Serial.println();

}

void loop() {
  // Look for new cards
  if ( ! mfrc522.PICC_IsNewCardPresent()) 
  {
    return;
  }
  // Select one of the cards
  if ( ! mfrc522.PICC_ReadCardSerial()) 
  {
    return;
  }

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
    sendData(readData);
  } else {
    Serial.println("The same id read");
  }
  
}

void sendData(String rfId) {
   //Check WiFi connection status before sending HTTP request
   if(WiFi.status()== WL_CONNECTED){

      // Your Domain name with URL path or IP address with path
      http.begin(serverName.c_str());
     
      String httpRequestData = String(security_token) + String(";") + String(wsid) + String(";") + String(rfId) + String(";");           
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
