import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SystemCPU } from 'src/app/Model/system-cpu';
import { SystemlHealth } from 'src/app/Model/system-health';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private baseUrl = 'http://localhost:8080/actuator';
  
  constructor(private http:HttpClient) { }

  getHttpTraces():Observable<any>{
    return this.http.get<any>(`${this.baseUrl}/httptrace`);
  }

  getSystemHealth():Observable<SystemlHealth>{
    return this.http.get<SystemlHealth>(`${this.baseUrl}/health`);
  }

  getSystemCPU():Observable<SystemCPU>{
    return this.http.get<SystemCPU>(`${this.baseUrl}/metrics/system.cpu.count`);
  }

  getProcessUptime():Observable<any>{
    return this.http.get<any>(`${this.baseUrl}/metrics/process.uptime`);
  } 
}
