import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { SystemCPU } from 'src/app/Model/system-cpu';
import { DashboardService } from 'src/app/services/admin/dashboard.service';

@Injectable({
  providedIn: 'root'
})
export class PrefetchDashboardResolver implements Resolve<SystemCPU> {

  constructor(private dashboardService: DashboardService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<SystemCPU> | Promise<SystemCPU> | SystemCPU {
    return this.dashboardService.getSystemCPU();
  }
}
