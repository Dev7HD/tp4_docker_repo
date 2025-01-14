import {Component, inject, OnInit} from '@angular/core';
import {ChatService} from '../../services/chat.service';
import {NgForOf, NgIf} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {Message} from '../../models/Message';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    FormsModule
  ],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.css'
})
export class ChatComponent implements OnInit{
  chatService: ChatService = inject(ChatService);
  private _http: HttpClient = inject(HttpClient);

  userMessage: string = "";
  isLoading: boolean = false;

  ngOnInit(): void {
    console.table(this.chatService.messages)
  }

  send(): void{
    this.isLoading = true
    if (this.userMessage != null && this.userMessage.length > 5){
      let userRequest: Message = {
        sender: 'user',
        message: this.userMessage
      }
      this.chatService.messages.push(userRequest)

      let request = this._http.post('http://localhost:8888/rag-service/chat', this.userMessage, {responseType: "text"});
      request.subscribe({
        next: response => {
          let aiResponse: Message = {
            sender: 'bot',
            message: response
          }
          this.chatService.messages.push(aiResponse)
          this.isLoading = false
          this.userMessage = ''
        }, error: err => {
          this.isLoading = false
          console.error("Failed sending msg to AI-Bot", err)
        }
      })

    }
  }

  clearChatHistory() {
    this.chatService.initChat()
  }
}
