import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import 'rxjs/add/operator/map';
import {Utilities} from "./utilities";
import {SQLite} from "ionic-native";

@Injectable()
export class Database {

  public data: any = null;
  public storage: any = null;
  public dbName: string = "supadupa.db";
  public purchases: any = [];

  constructor(public http: Http,
              public UTIL: Utilities) {
    this.data = null;
  }

  createDatabase() {
    this.storage = new SQLite();
    this.storage.openDatabase({
      name: this.dbName,
      location: 'default'
    }).then((data) => {
        console.log("Opened database");
        this.createDatabaseTables();
      },
      (err) => {
        console.error('Unable to open database: ', err);
      });
  }

  createDatabaseTables() {
    this.createPurchasesTable();
  }

  createPurchasesTable() {
    this.storage.executeSql('CREATE TABLE IF NOT EXISTS appPurchases (id INTEGER PRIMARY KEY AUTOINCREMENT, productID TEXT, isPurchased TEXT NOT NULL, date TEXT NOT NULL, timestamp INTEGER NOT NULL)', {})
      .then((data) => {
          console.log("appPurchases TABLE CREATED: " + JSON.stringify(data));
        },
        (error) => {
          console.log("Error: " + JSON.stringify(error.err));
          console.dir(error);
        });
  }

  retrievePurchases() {
    return new Promise(resolve => {
      this.storage.executeSql("SELECT * FROM appPurchases", {})
        .then((data) => {
            this.purchases = [];
            if (data.rows.length > 0) {
              var k;
              for (k = 0; k < data.rows.length; k++) {
                this.purchases.push({
                  productId: data.rows.item(k).productID
                });
              }
            }
            resolve(this.purchases);
          },
          (error) => {
            console.log("Error retrieving purchases from retrievePurchases: " + JSON.stringify(error));

          });
    });
  }


  doesPurchaseExistInTable(productID) {
    this.storage.executeSql("SELECT * FROM appPurchases WHERE productID = '" + productID + "'", {})
      .then((data) => {
          let isPurchased = 'Y',
            currDate = new Date(),
            year = currDate.getFullYear(),
            month = this.UTIL.prefixWithZeros(currDate.getMonth()),
            day = this.UTIL.prefixWithZeros(currDate.getDay()),
            hour = this.UTIL.prefixWithZeros(currDate.getHours()),
            mins = this.UTIL.prefixWithZeros(currDate.getMinutes()),
            secs = this.UTIL.prefixWithZeros(currDate.getSeconds()),
            dateIs = year + '-' + month + '-' + day + ' ' + hour + ':' + mins + ':' + secs,
            timestampIs = Math.floor(Date.now() / 1000);

          if (data.rows.length === 0) {
            console.log(`NO record exists for ${productID} inside appPurchases DB table`);
            this.insertPurchasesToTable(productID, isPurchased, dateIs, timestampIs);
          }
          else {
            console.log(`Record DOES exist for ${productID} inside appPurchases DB table`);
          }

          //resolve(addedToDB);
        },
        (error) => {
          console.log("Error determining if purchase exists in appPurchases table: " + JSON.stringify(error));

        });
  }


  insertPurchasesToTable(productID, isPurchased, date, timestamp) {
    let sql = "INSERT INTO appPurchases(productID, isPurchased, date, timestamp) VALUES('" + productID + "', '" + isPurchased + "', '" + date + "', " + timestamp + ")";
    this.storage.executeSql(sql, {})
      .then((data) => {
          console.log(`appPurchases TABLE INSERTED RECORD for product ID: ${productID}`);
        },
        (error) => {
          console.dir(error);
          console.log("Error inserting " + productID + " record for table insertPurchasesToTable: " + JSON.stringify(error.err));
        });
  }

}
