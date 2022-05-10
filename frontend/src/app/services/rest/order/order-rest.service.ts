import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class OrderRestService {

  constructor(private http: HttpClient) { }

  removeOrderLine(olId: string) {
    return this.http.delete(environment.restUrl + environment.api + '/orderlines/delete/ol/' + olId, {});
  }
}
