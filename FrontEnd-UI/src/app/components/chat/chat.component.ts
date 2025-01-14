import {Component, inject, OnInit} from '@angular/core';
import {ChatService} from '../../services/chat.service';
import {NgForOf, NgIf} from '@angular/common';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {Message} from '../../models/Message';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ToastrService} from 'ngx-toastr';
import {environment} from '../../../environments/environment.development';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.css'
})
export class ChatComponent implements OnInit{
  chatService: ChatService = inject(ChatService);
  private _http: HttpClient = inject(HttpClient);
  private _modalService: NgbModal = inject(NgbModal);
  private _formBuilder: FormBuilder = inject(FormBuilder);
  private _toastr: ToastrService = inject(ToastrService);
  formGroup!: FormGroup;

  userMessage: string = "";
  isLoading: boolean = false;
  isUploading: boolean = false;

  ngOnInit(): void {
    console.table(this.chatService.messages)
    this.initForm()
  }

  send(): void{
    this.isLoading = true
    if (this.userMessage != null && this.userMessage.length > 5){
      let userRequest: Message = {
        sender: 'user',
        message: this.userMessage
      }
      this.chatService.messages.push(userRequest)

      let request = this._http.post(`${environment.ragUrl}/chat`, this.userMessage, {responseType: "text"});
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

  showUploadModal(uploadPDF: any) {
    this.initForm()
    this._modalService.open(uploadPDF, { size: 'lg' })
  }

  initForm(){
    this.formGroup = this._formBuilder.group({
      pdf_file: ['']
    })
  }

  onSelectPDF($event: any){
    if($event.target.files.length > 0){
      const file = $event.target.files[0];
      this.formGroup.get('pdf_file')?.setValue(file);
      console.log(this.formGroup.get('pdf_file')?.value)
    }
  }

  onSubmit(){
    this.isUploading = true
    let formData = new FormData();
    formData.append('pdf_file', this.formGroup.get('pdf_file')?.value);
    console.log(formData)
    let _url = `${environment.ragUrl}/load-pdf`
    this._http.post<any>(_url, formData).subscribe({
      next: () => {
        this.initForm()
        this.isUploading = false
        this._modalService.dismissAll()
        this._toastr.success('PDF has been successfully uploaded')
      }, error: err => {
        this.isUploading = false
        console.error("Oops! Uploading PDF failed.", err)
        this._toastr.error('Oops! Uploading PDF failed.')
      }
    })
  }
}
