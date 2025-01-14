import {Component, inject, OnInit} from '@angular/core';
import {NgbModal, NgbPaginationModule} from '@ng-bootstrap/ng-bootstrap';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {CryptoService} from '../../services/crypto.service';
import {NgForOf} from '@angular/common';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-cryptos',
  standalone: true,
  imports: [
    NgbPaginationModule,
    ReactiveFormsModule,
    FormsModule,
    NgForOf,
  ],
  templateUrl: './cryptos.component.html',
  styleUrl: './cryptos.component.css'
})
export class CryptosComponent implements OnInit{
  cryptoService: CryptoService = inject(CryptoService);
  private _toastr: ToastrService = inject(ToastrService);
  private _modalService: NgbModal = inject(NgbModal)
  private _fromBuilder: FormBuilder = inject(FormBuilder)

  formGroup!: FormGroup;

  cryptoName: string = '';
  cryptoType: string = '';
  cryptoUnit: string = '';
  cryptoPlatform: string = '';
  page: number = 0;
  size: number = 10;

  initForm(){
    this.formGroup = this._fromBuilder.group({
      name: this._fromBuilder.control('', [Validators.required, Validators.minLength(3)]),
      type: this._fromBuilder.control('', [Validators.required, Validators.minLength(3)]),
      unit: this._fromBuilder.control('', [Validators.required, Validators.minLength(3)]),
      emission: this._fromBuilder.control(0, [Validators.required, Validators.min(0)]),
      consensusRule: this._fromBuilder.control('', [Validators.required, Validators.minLength(3)]),
      algorithm: this._fromBuilder.control('', [Validators.required, Validators.minLength(3)]),
      protocol: this._fromBuilder.control('', [Validators.required, Validators.minLength(3)]),
      platform: this._fromBuilder.control('', [Validators.required, Validators.minLength(3)]),
      webSite: this._fromBuilder.control('', [Validators.required, Validators.minLength(3)]),
      community: this._fromBuilder.control('', [Validators.required, Validators.minLength(3)])
    })
  }

    ngOnInit(): void {
      this.initForm()
      if (this.cryptoService.pageCryptos == undefined){
        this.cryptoService.getCryptos()
      }

      this.page = this.cryptoService.pageCryptos.number + 1
      this.size = this.cryptoService.pageCryptos.size

    }

  changePage($event: number) {
    console.log(this.page)
    this.cryptoService.getCryptos(this.cryptoName, this.cryptoType, this.cryptoUnit, this.cryptoPlatform, $event - 1, this.size)
  }

  changeSize($event: number) {
    this.size = $event
    this.search()
  }

  deleteCrypto(id: string) {
    this.cryptoService.deleteCrypto(id).subscribe({
      next: response => {
        this._toastr.success(response)
        this.cryptoService.pageCryptos.content = this.cryptoService.pageCryptos.content.filter(c => c.id != id)
        this.cryptoService.pageCryptos.totalElements = this.cryptoService.pageCryptos.content.length
      }, error: () => {
        this._toastr.error('Oops! Something went wrong...')
      }
    })
  }

  search() {
    this.cryptoService.getCryptos(this.cryptoName, this.cryptoType, this.cryptoUnit, this.cryptoPlatform, this.page - 1, this.size)
  }

  newCryptoModal(content: any) {
    this._modalService.open(content);
  }

  saveCrypto() {
    this.cryptoService.createCrypto(this.formGroup.value).subscribe({
      next: value => {
        console.table(value)
        this._modalService.dismissAll()
        this.initForm()
        this.search()
        this._toastr.success('New Crypto saved successfully.')
      },
      error: () => this._toastr.error("Oops! Saving failed....")
    })
  }
}
