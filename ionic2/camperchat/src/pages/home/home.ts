import { Component, ViewChild } from '@angular/core';
import { Data } from '../../providers/data';

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {

  @ViewChild('chat') chat: any;

  chatMessage: string = '';
  messages: any = [];

  constructor(public dataService: Data){

    this.dataService.getDocuments().then((data) => {

      this.messages = data;
      this.chat.scrollToBottom();

    });

  }

  sendMessage(): void {

    let message = {
      '_id': new Date().toJSON(),
      'fbid': this.dataService.fbid,
      'username': this.dataService.username,
      'picture': this.dataService.picture,
      'message': this.chatMessage
    };

    this.dataService.addDocument(message);
    this.chatMessage = '';
    
  }

}
