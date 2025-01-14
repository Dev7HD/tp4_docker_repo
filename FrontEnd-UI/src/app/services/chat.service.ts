import {Injectable} from '@angular/core';
import {Message} from '../models/Message';

@Injectable({
  providedIn: 'root'
})
export class ChatService{

  messages: Message[] = [
    {
      sender: "bot",
      message: "Hello, how can I help you?"
    }
  ]

  constructor() { }

  initChat(){
    this.messages = [
      {
        sender: "bot",
        message: "Hello, how can I help you?"
      }
    ]
  }
}
