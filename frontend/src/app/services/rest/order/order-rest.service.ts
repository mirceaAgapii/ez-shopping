import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.prod';

@Injectable({
  providedIn: 'root'
})
export class OrderRestService {

  constructor(private http: HttpClient) { }

  finishOrder(orderId: string) {
    return this.http.post(environment.restUrl + environment.api + '/orders/finish/' + orderId, null);
  }
}
