import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { SystemlHealth } from '../Model/system-health';
import { SystemCPU } from '../Model/system-cpu';
import { DashboardService } from '../services/admin/dashboard.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  showDashboard = true;
  showUsers = false;
  showProducts = false;
  showStores = false;

  constructor(private router: Router){}

  ngOnInit(): void {
    
  }

  viewDashboard() {
    this.showDashboard = true;
    this.showUsers = this.showProducts = this.showStores = false;
    this.router.navigate(['/admin/dashboard']);
  }

  viewUsers() {
    this.showUsers = true;
    this.showDashboard = this.showProducts = this.showStores = false;
    this.router.navigate(['/admin/users']);
  }

  viewProducts() {
    this.showProducts = true;
    this.showDashboard = this.showUsers = this.showStores = false;
    this.router.navigate(['/admin/products']);
  }

  viewStores() {
    this.showStores = true;
    this.showDashboard = this.showUsers = this.showProducts = false;
    this.router.navigate(['/admin/stores']);
  }


}
