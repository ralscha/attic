import { Injectable } from '@angular/core';
import PouchDB from 'pouchdb';

@Injectable()
export class Data {

  fbid: number;
  username: string;
  picture: string;
  db: any;
  data: any;
  cloudantUsername: string;
  cloudantPassword: string;
  remote: string;

  constructor(){

    this.db = new PouchDB('camperchat');
    this.cloudantUsername = 'YourAPIUsernameHere';
    this.cloudantPassword = 'YourAPIPasswordHere';
    this.remote = 'https://YOUR-URL-HERE-bluemix.cloudant.com/camperchat';

    //Set up PouchDB
    let options = {
      live: true,
      retry: true,
      continuous: true,
      auth: {
        username: this.cloudantUsername,
        password: this.cloudantPassword
      }
    };

    this.db.sync(this.remote, options);

  }

  addDocument(message) {
    this.db.put(message);
  }

  getDocuments(){

    return new Promise(resolve => {

      this.db.allDocs({

        include_docs: true,
        limit: 30,
        descending: true

      }).then((result) => {

        this.data = [];

        let docs = result.rows.map((row) => {
          this.data.push(row.doc);
        });

        this.data.reverse();

        resolve(this.data);

        this.db.changes({live: true, since: 'now', include_docs: true}).on('change', (change) => {
          this.handleChange(change);
        });

      }).catch((error) => {

        console.log(error);

      }); 

    });

  }

  handleChange(change) {

    let changedDoc = null;
    let changedIndex = null;

    this.data.forEach((doc, index) => {

      if(doc._id === change.id){
        changedDoc = doc;
        changedIndex = index;
      }

    });

    //A document was deleted
    if(change.deleted){
      this.data.splice(changedIndex, 1);
    } 
    else {

      //A document was updated
      if(changedDoc){
        this.data[changedIndex] = change.doc;
      } 

      //A document was added
      else {
        this.data.push(change.doc); 
      }

    }

  }

}
