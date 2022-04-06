import { Component, OnInit } from '@angular/core';
import { LocalStorageService } from '../services/auth/storage/local-storage.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {

  value = this.localStorageService.get('userId');

  constructor(private localStorageService: LocalStorageService) { }

  ngOnInit(): void {
    
  }

}
