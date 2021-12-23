import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { SystemlHealth } from '../Model/system-health';
import { SystemCPU } from '../Model/system-cpu';
import { DashboardService } from '../services/admin/dashboard.service';

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

  ngOnInit(): void {
    
  }

  viewDashboard() {
    this.showDashboard = true;
    this.showUsers = this.showProducts = this.showStores = false;
  }

  viewUsers() {
    this.showUsers = true;
    this.showDashboard = this.showProducts = this.showStores = false;
  }

  viewProducts() {
    this.showProducts = true;
    this.showDashboard = this.showUsers = this.showStores = false;
  }

  viewStores() {
    this.showStores = true;
    this.showDashboard = this.showUsers = this.showProducts = false;
  }


}
